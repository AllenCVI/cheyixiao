package com.autoforce.cheyixiao.home.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.autoforce.cheyixiao.base.BaseX5WebViewActivity;
import com.autoforce.cheyixiao.base.CommonBarWebActivity;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.Bean;
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.home.base.HomeBaseWebActivity;
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository;
import com.tencent.smtt.sdk.ValueCallback;

import java.util.HashMap;

/**
 * Created by liusilong on 2018/11/30.
 * version:1.0
 * Describe:
 */
public class HomeWebActivity extends BaseX5WebViewActivity {

    private static final String TAG = "HomeWebActivity";
    private static final String URL = "url";
    private Bean bea = null;


    @Override
    protected void setOther() {
        webView.addJavascriptInterface(new Insurance(), "android");

    }

    class Insurance {

        @JavascriptInterface
        public void loadExternalUrl(String url) {
            //webView.loadUrl(url);
            CommonBarWebActivity.getInstance(HomeWebActivity.this, url);
        }
    }

    @Override
    protected String getUrl() {
        if (getIntent() != null) {
            return getIntent().getExtras().getString(URL);
        }
        return null;
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {
    }

    @Override
    protected boolean isInterceptRequest() {

        return false;
    }

   /* @NonNull
    @Override
    protected String concatUrl() {
        return getUrl();
    }*/

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, HomeWebActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void childUse(Bean bean) {
        super.childUse(bean);
        this.bea = bean;
    }

    @Override
    protected void postBase64File(HashMap<String, Object> takePhotoMap, Context context, boolean isShowLoading) {
        DefaultDisposableSubscriber<ApproveInfo> defaultDisposableSubscriber = MineRemoteRepository.getInstance().postApproveInfo(takePhotoMap)
                .subscribeWith(new DefaultDisposableSubscriber<ApproveInfo>((Activity) context, isShowLoading) {
                    @SuppressLint("ObsoleteSdkInt")
                    @Override
                    protected void success(ApproveInfo data) {
                        Log.e(TAG, "success: " + data.getUrl());
                        ApproveInfo approveInfo = (ApproveInfo) data;
                        if (Build.VERSION.SDK_INT >= 18) {
                            webView.evaluateJavascript("javascript:interfaceCalledByApp(\"" + getKey() + "\"" + "," + "\"" + ((ApproveInfo) data).getUrl() + "\"" + "," + "\"" + bea.getIdentifier() + "\")", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                }
                            });
                        } else {
                            webView.loadUrl("javascript:interfaceCalledByApp(" + getKey() + "," + ((ApproveInfo) data).getUrl() + ")");
                        }

                    }

                    @Override
                    protected void failure(String errMsg) {
                        Log.e(TAG, "failure: " + errMsg);
                        super.failure(errMsg);
                    }
                });
    }

}
