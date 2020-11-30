package com.autoforce.cheyixiao.base;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.utils.StringUtils;

import java.util.HashMap;

/*
 * 公共web类  只做页面展示没有交互的时候可以使用  只需调用start方法传入URL即可
 * */
public class CommonX5WebViewInterceptActivity extends BaseX5WebViewActivity {

    public static void start(Activity activity, String url,String bindPhone) {
        Intent intent = new Intent(activity, CommonX5WebViewInterceptActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("bindPhone" , bindPhone);
        activity.startActivity(intent);
    }


    @Override
    protected void setOther() {
        webView.addJavascriptInterface(new InsuranceDeposit() , "Android");
    }

    class InsuranceDeposit{

        @JavascriptInterface
        public void returnDeposit(String url) {
            //webView.loadUrl(url);
            CommonBarWebActivity.getInstance(CommonX5WebViewInterceptActivity.this , url);
        }

    }

    @Override
    public String getUrl() {
        return getIntent().getStringExtra("url");
    }

    @Override
    protected void addParams(HashMap<String, String> map) {
        String bindPhone = getIntent().getStringExtra("bindPhone");
        if(!StringUtils.isEmpty(bindPhone)) {
            map.put("bindPhone", bindPhone);
        }
    }


}
