package com.autoforce.cheyixiao.mine.web2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity;
import com.autoforce.cheyixiao.common.data.local.Bean;
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.common.utils.PermissionHelper;
import com.autoforce.cheyixiao.common.utils.PermissionInterface;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

public class ApproveInfoWebActivity extends BaseX5WebViewActivity implements PermissionInterface, ApproveInfoWebActivityView {


    private static final String METHOND_NAME = "goMain";
    private PermissionHelper permissionHelper;
    private Context context;

    private ApproveInfoActivityPresenterImpl approveInfoActivityPresenter;
    private Bean bea;

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, ApproveInfoWebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
        context = this;
        approveInfoActivityPresenter = new ApproveInfoActivityPresenterImpl(this);
    }

    @Override
    protected void setOther() {

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

            }
        });
    }

    @Override
    protected void childUse(Bean bean) {
        super.childUse(bean);
        bea = bean;
        if (METHOND_NAME.equals(bean.getMethod())) {
            ToastUtil.showToast(getResources().getString(R.string.change_success));
            finish();
        }
    }



    @Override
    public void postBase64File(HashMap<String, Object> takePhotoMap, Context context, boolean isShowLoading) {

        approveInfoActivityPresenter.postApprove(takePhotoMap, context, isShowLoading);
    }

    @Override
    protected void postDefaild() {
        if (Build.VERSION.SDK_INT >= 18) {
            webView.evaluateJavascript("javascript:interfaceCalledByApp(\"" + getKey() + "\"" + "," + "\"" + "" + "\"" + "," + "\"" + bea.getIdentifier() + "\")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                }
            });
        }else {
            webView.loadUrl("javascript:interfaceCalledByApp(\"" + getKey() + "\"" + "," + "\"" + "" + "\"" + "," + "\"" + bea.getIdentifier() + "\")");
        }
    }

    /*上传后的操作展示或者提示失败*/
    @Override
    public void showNext(Object data, boolean isSuccess) {
        if (isSuccess) {
            ApproveInfo approveInfo = (ApproveInfo) data;
            if (Build.VERSION.SDK_INT >= 18) {
                webView.evaluateJavascript("javascript:interfaceCalledByApp(\"" + getKey() + "\"" + "," + "\"" + ((ApproveInfo) data).getUrl() + "\"" + "," + "\"" + bea.getIdentifier() + "\")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }else {
                webView.loadUrl("javascript:interfaceCalledByApp(\"" + getKey() + "\"" + "," + "\"" + ((ApproveInfo) data).getUrl() + "\"" + "," + "\"" + bea.getIdentifier() + "\")");
            }

        }else {

        }
    }


    @Override
    protected void addParams(HashMap<String, String> map) {

    }

    @Override
    protected String getUrl() {
        return getIntent().getStringExtra("url");
    }


    protected void initPermission() {
        permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermission();
    }
    //权限接口实现的方法

    @Override
    public int getPermissionRequestCode() {
        return 1000;
    }

    @Override
    public String[] getPermission() {
        return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    public void requestPermissionSuccess() {

    }

    @Override
    public void requestPermissionFaile() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionHelper.requestPermissionResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
