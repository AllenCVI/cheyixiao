package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class MineIntegralActivity extends BaseX5WebViewActivity {


    private String url = "http://192.168.1.207:8090/saler/credit.html#/userMobile/myCredit";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOther();

    }


    @Override
    protected void setOther() {

        webView.loadUrl(url);

        webView.addJavascriptInterface(new android2Js(), "android");//交互映射

    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void addParams(HashMap<String, String> map) {

    }

    class android2Js {

    }
}
