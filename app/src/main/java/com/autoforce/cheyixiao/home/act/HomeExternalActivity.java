package com.autoforce.cheyixiao.home.act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.home.base.HomeBaseWebActivity;

import java.util.HashMap;

/**
 * Created by liusilong on 2018/12/14.
 * version:1.0
 * Describe:
 */
@SuppressLint("Registered")
public class HomeExternalActivity extends HomeBaseWebActivity {
    private static final String TAG = "HomeExternalActivity";

    @Override
    protected String getUrl() {
        return null;
    }

    @NonNull
    @Override
    protected String concatUrl() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("url");
        }
        return null;
    }

    @Override
    protected void setOther() {
        super.setOther();
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {

    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, HomeExternalActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
