package com.autoforce.cheyixiao.login;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;

import butterknife.BindView;

import com.autoforce.cheyixiao.MainActivity;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseActivity;
import com.autoforce.cheyixiao.base.ITranslucentAct;
import com.autoforce.cheyixiao.common.ExtensionCollectionKt;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.common.data.remote.bean.LoginResult;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.common.view.popup.ListPopupWindowManager;
import com.autoforce.cheyixiao.login.forget.PwdForgetOneAct;
import com.autoforce.cheyixiao.login.repo.LoginRepository;
import com.orhanobut.logger.Logger;

import flyn.Eyes;
import io.reactivex.subscribers.DisposableSubscriber;

import java.util.List;

/**
 * Created by xialihao on 2018/11/19.
 * <p>
 * 16601169211
 * 123456
 */
public class LoginActivity extends BaseActivity implements ITranslucentAct {


    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.rl_account)
    RelativeLayout rlAccount;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    private DisposableSubscriber<LoginResult> disposableSubscriber;

    private ListPopupWindowManager mManager;
    private List<String> savedAccounts;
    private long exitTime = 0;
    public static int keyboardHeight = 0;
    boolean isVisibleForLast = false;
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;
    private boolean isRecordClick = false;

    public static void start(Context context) {
        LocalRepository.getInstance().clearUserInfo();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startNoFlags(Context context) {
        LocalRepository.getInstance().clearUserInfo();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        Eyes.translucentStatusBar(this, true);

        btnLogin.setOnClickListener(v -> doLogin());

        btnRegister.setOnClickListener(v -> RegisterAct.Companion.start(this));

        tvForget.setOnClickListener(v -> PwdForgetOneAct.Companion.start(this));

        ivRecord.setVisibility(LocalRepository.getInstance().getSavedAccounts().isEmpty() ? View.GONE : View.VISIBLE);
        ivRecord.setOnClickListener(v -> {
            final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            if (DeviceUtil.isKeyboardShown(rootView)) {
                isRecordClick = true;
                KeyboardUtil.hideSoftInput(this);
            } else {
                showRecords();
            }
        });

        tvVersion.setText(String.format("v%s", AppMessageUtils.getAppVersionName(this)));

        setListenerToRootView();
        addOnSoftKeyBoardVisibleListener();
    }

    @Override
    protected void initData() {
        super.initData();

        savedAccounts = LocalRepository.getInstance().getSavedAccounts();
        if (!savedAccounts.isEmpty()) {
            etAccount.setText(savedAccounts.get(0));
            ExtensionCollectionKt.moveCursorLast(etAccount);
        }
    }

    private void doLogin() {
        // check input
        String account = etAccount.getText().toString().trim();
        String password = etPwd.getText().toString().trim();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, R.string.input_not_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        doLoginRequest(account, password);
    }


    private void showRecords() {

        savedAccounts = LocalRepository.getInstance().getSavedAccounts();
        mManager = new ListPopupWindowManager.Builder(this)
                .setData(savedAccounts)
                .setSelectedData(etAccount.getText().toString())
                .setOnItemClickListener((parent, view, position, id) -> {
                    etAccount.setText(savedAccounts.get(position));
                    etAccount.setSelection(savedAccounts.get(position).length());
                    mManager.dismiss();
                })
                .setOnDismissListener(() -> isRecordClick = false)
                .show(rlAccount);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (System.currentTimeMillis() - exitTime > 3000) {
                ToastUtil.showToast(R.string.double_click_quit);
                exitTime = System.currentTimeMillis();
            } else {
//                 这种实现方式在ApplicationText启动后，无法完全退出app，故采用自定义ActivityStack的方式来退出
//                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
//                System.exit(0);
                ActivityManager.getInstance().clearActivities();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableSubscriber != null) {
            disposableSubscriber.dispose();
        }

        if (mManager != null) {
            mManager.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (llContainer != null) {
            llContainer.animate().translationY(0).start();
        }
    }

    private void doLoginRequest(String account, String password) {
        disposableSubscriber = LoginRepository.getInstance().postLogin(account, password)
                .subscribeWith(new DefaultDisposableSubscriber<LoginResult>(this, true) {
                    @Override
                    protected void success(LoginResult data) {
                        onLoginSuccess(data, account);
                    }
                });
    }

    private void onLoginSuccess(LoginResult loginResult, String account) {

        LocalRepository.getInstance().saveUserInfo(loginResult);
        LocalRepository.getInstance().saveAccount(account);
        KeyboardUtil.hideSoftInput(this);
        if (loginResult.isVerified()) {
            Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
            ActivityManager.getInstance().clearActivities();
            MainActivity.start(LoginActivity.this);
        } else {
//             跳转到认证信息页
            InfoCertificateAct.Companion.start(this, false);
        }
    }

    private void setListenerToRootView() {
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            boolean mKeyboardUp = DeviceUtil.isKeyboardShown(rootView);
            if (mKeyboardUp) {
                //键盘弹出
//                    Log.e(TAG, "onGlobalLayout: " + keyboardHeight);
//                    tvTopic.animate().translationY(-(keyboardHeight + DeviceUtil.getNavigationBarHeight(PublishActivity.this.getApplicationContext()) + tvTopic.getHeight())).start();
//                    tvTopic.animate().translationY(-(keyboardHeight)).start();
//                    if (DeviceUtil.checkDeviceHasNavigationBar(PublishActivity.this)) {
//                        Log.e(TAG, "onGlobalLayout: 存在虚拟键盘");
//
                int loginHeight = getLoginHeight();
                int distanceY = loginHeight - keyboardHeight;
                int defaultDistance = DeviceUtil.dip2px(this, 20);
                Logger.e("distanceY = " + distanceY + ", defaultDistance = " + defaultDistance);
                if (distanceY < defaultDistance) {
                    llContainer.animate().translationY(distanceY - defaultDistance).start();
                }
//                    } else {
////                        tvTopic.animate().translationY(-keyboardHeight - DeviceUtil.getStatusBarHeight(PublishActivity.this.getApplicationContext())).start();
//                        tvTopic.animate().translationY(-keyboardHeight).start();
//                        Log.e(TAG, "onGlobalLayout: 不存在虚拟键盘");
//                    }
            } else {
                //键盘收起
//                    tvTopic.animate().translationY(0).start();
                llContainer.animate().translationY(0).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isRecordClick) {
                            if (mManager != null) {
                                mManager.dismiss();
                            }
                            showRecords();
                            isRecordClick = false;
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();


            }
        });
    }

    private int getLoginHeight() {

        if (btnLogin != null) {
            int bottom = btnLogin.getBottom();
            Logger.w("loginBottom = " + bottom);
            int screenHeight = DeviceUtil.getScreenHeight(this);
            int loginHeight = screenHeight - bottom;
//            Logger.w("screenHeight = " + screenHeight + "loginHeight = " + loginHeight);
            return loginHeight;
        }

        return 0;
    }


    public void addOnSoftKeyBoardVisibleListener() {
        if (keyboardHeight > 0) {
            return;
        }
        final View decorView = getWindow().getDecorView();
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHeight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int height = decorView.getHeight();
                boolean visible = (double) displayHeight / height < 0.8;
                int navigatorHeight = 0;
                try {
                    navigatorHeight = DeviceUtil.getNavigationBarHeight(LoginActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (visible && visible != isVisibleForLast) {

                    //获得键盘高度
                    keyboardHeight = height - displayHeight - navigatorHeight - DeviceUtil.getStatusBarHeight(LoginActivity.this);
                    Logger.i("keyboardHeight==1213==" + keyboardHeight);

                }
                isVisibleForLast = visible;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }


}
