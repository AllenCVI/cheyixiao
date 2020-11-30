package com.autoforce.cheyixiao.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

public class CommonBarWebActivity extends BaseX5WebViewActivity {

    ImageView barBack;
    TextView title;

    public static void getInstance(Activity activity , String url){
        Intent intent = new Intent(activity, CommonBarWebActivity.class);
        intent.putExtra("url" , url);
        activity.startActivity(intent);
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_commen_bar;
    }

    @Override
    protected void setOther() {
        barBack = findViewById(R.id.bar_back);
        title = findViewById(R.id.title);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                title.setText(s);
            }
        });

        barBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
