package com.autoforce.cheyixiao.home.act;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.home.HomeConstant;
import com.autoforce.cheyixiao.home.base.HomeBaseWebActivity;
import com.autoforce.cheyixiao.home.bean.HomeBrandInfo;

import java.util.HashMap;

/**
 * Created by liusilong on 2018/11/30.
 * version:1.0
 * Describe:
 */
public class HomeBrandActivity extends HomeBaseWebActivity {

    private static final String TAG = "HomeWebActivity";
    private static final String BRAND_ID = "brand_ID";
    private static final String BRAND_NAME = "brandName";


    @Override
    protected String getUrl() {
        return HomeConstant.BRAND_URL;
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {
        if (getIntent() != null) {
            String brandName = getIntent().getStringExtra(BRAND_NAME);
            int brandId = getIntent().getIntExtra(BRAND_ID, -1);
            map.put(BRAND_ID, String.valueOf(brandId));
            map.put(BRAND_NAME, brandName);
        }
    }

    @Override
    protected boolean isInterceptRequest() {
        return true;
    }

    public static void start(Context context, HomeBrandInfo brandInfo) {
        if (brandInfo == null) return;
        Intent intent = new Intent(context, HomeBrandActivity.class);
        intent.putExtra(BRAND_ID, brandInfo.getId());
        intent.putExtra(BRAND_NAME, brandInfo.getName());
        context.startActivity(intent);
    }

}

