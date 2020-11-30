package com.autoforce.cheyixiao.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import com.autoforce.cheyixiao.App;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.data.remote.ResponseException;
import com.autoforce.cheyixiao.login.InfoCertificateAct;
import com.autoforce.cheyixiao.login.LoginActivity;
import com.orhanobut.logger.Logger;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by xialihao on 2018/11/21.
 * 默认的DisposableSubscriber，封装了错误Toast以及等待loading的显示控制
 */
public abstract class DefaultDisposableSubscriber<T> extends DisposableSubscriber<T> {

    private boolean needLoading = false;
    private Activity mContext;
    private Dialog mDialog;
    private boolean autoDismiss = true;

    public DefaultDisposableSubscriber() {

    }


    public DefaultDisposableSubscriber(Activity context, boolean needLoading) {
        this.needLoading = needLoading;
        mContext = context;
    }

    abstract protected void success(T data);

    protected void failure(String errMsg) {
        ToastUtil.showToast(errMsg);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (needLoading && mContext != null) {
            mDialog = new ProgressDialog(mContext);
            mDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        done(true);
        success(t);
    }

    @Override
    public void onError(Throwable t) {
        done(false);
        if (t instanceof ResponseException) {
            ResponseException e = (ResponseException) t;
            if (e.isLoginOtherDevice()) {
                ToastUtil.showToast(R.string.login_again);
                LoginActivity.start(App.getInstance());
            } else if (e.isNotLogin()) {
                ToastUtil.showToast(R.string.please_login_again);
                LoginActivity.start(App.getInstance());
            } else if (e.isAccountReject()) {
                ToastUtil.showToast(R.string.account_rejected);
                InfoCertificateAct.Companion.start(App.getInstance(), true);
            } else if (e.isAccountLogout()) {
                ToastUtil.showToast(R.string.account_logout);
                LoginActivity.start(App.getInstance());
            } else {
                doFailure(t.getMessage(), false);
            }
        } else {
            doFailure(t.getMessage(), true);
        }
    }

    private void doFailure(String errMsg, boolean isNetError) {
        Logger.e("Failure -> " + errMsg);

        if (isNetError) {
            errMsg = StringUtils.getString(R.string.net_error);
        }
        failure(errMsg);
    }

    @Override
    public void onComplete() {

    }

    protected void done(boolean isSuccess) {
        if (autoDismiss) {
            dismiss();
        }

        mContext = null;
        mDialog = null;
    }

    private void dismiss() {
        if (mDialog != null) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
