package com.autoforce.cheyixiao.home.act;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.FilesDownload;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.home.HomeConstant;
import com.autoforce.cheyixiao.home.base.HomeBaseWebActivity;

import java.util.HashMap;

/**
 * Created by liusilong on 2018/12/6.
 * version:1.0
 * Describe:
 */
public class HomeBrandPanoramaActivity extends HomeBaseWebActivity {

    private static final String TAG = "HomeBrandPanoramaActivi";

    @Override
    protected void setOther() {
        super.setOther();
        String url = String.format(getResources().getString(R.string.offline_download_url), getCarId());
        filesDownload.isFilesNeedUpdate(url, getCarId(), new FilesDownload.OnUpdateListener() {
            @Override
            public void onUpdate(boolean isNeedUpdate) {
                SpUtils.getInstance(HomeConstant.CAR_UPDATE_RECORD).put(getCarId(), isNeedUpdate);
            }
        });
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {
    }

    @NonNull
    @Override
    protected String concatUrl() {
        if (getIntent() != null) {
            String url = getIntent().getStringExtra("url");
            String update = String.valueOf(SpUtils.getInstance(HomeConstant.CAR_UPDATE_RECORD).getBoolean(getCarId(), false));
            String download = String.valueOf(SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_RECORD).getBoolean(getCarId(), false));
            if (!url.isEmpty() && url.contains("?")) {
                url = url + "&update=" + update + "&download=" + download;
            } else {
                url = url + "?&update=" + update + "&download=" + download;
            }
            return url;
        }
        return "";
    }

    @Override
    protected boolean isInterceptRequest() {
        return true;
    }

    public static void start(Context context, String url, String carId) {
        Intent intent = new Intent(context, HomeBrandPanoramaActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("carId", carId);
        context.startActivity(intent);
    }

    @Override
    protected String getCarId() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("carId");
        }
        return super.getCarId();
    }

}
