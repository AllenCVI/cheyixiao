package com.autoforce.cheyixiao.common.utils;

import com.autoforce.cheyixiao.App;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.data.remote.ResponseException;
import com.autoforce.cheyixiao.login.InfoCertificateAct;
import com.autoforce.cheyixiao.login.LoginActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by xialihao on 2018/11/27.
 */
public class ErrorHandler {

    public static void handleError(Throwable t) {
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

    private static void doFailure(String errMsg, boolean isNetError) {
        Logger.e("Failure -> " + errMsg);

        if (isNetError) {
            errMsg = StringUtils.getString(R.string.net_error);
        }

        ToastUtil.showToast(errMsg);
    }
}
