package com.autoforce.cheyixiao.home.act;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.FilesDownload;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.home.HomeConstant;
import com.autoforce.cheyixiao.home.base.HomeBaseWebActivity;
import com.autoforce.cheyixiao.home.bean.HomeBannerCar;
import com.autoforce.cheyixiao.home.bean.HomeHotCar;

import java.util.HashMap;

/**
 * Created by liusilong on 2018/12/4.
 * version:1.0
 * Describe: http://192.168.1.207:8090/car.html#/layout/carbefore?token=Ly0y2ienk8YK&saler=41362&role=1&cert_code=3&isLink=1&car=30268,5系,1,sedan,2,https://cdn.autoforce.net/ixiao/,0&brandUrl=https://cheyixiao.autoforce.net/static/series/car_30268.png
 * <p>
 * 这里需要通过 url 传给前端两个字段 "update" 和 "download"
 */
public class HomeHotCarWebActivity extends HomeBaseWebActivity {

    private static final String TAG = "HomeHotCarWebActivity";

    @Override
    protected void setOther() {
        super.setOther();
        if (getCarId() != null) {
            String url = String.format(getResources().getString(R.string.offline_download_url), getCarId());
            filesDownload.isFilesNeedUpdate(url, getCarId(), new FilesDownload.OnUpdateListener() {
                @Override
                public void onUpdate(boolean isNeedUpdate) {
                    SpUtils.getInstance(HomeConstant.CAR_UPDATE_RECORD).put(getCarId(), isNeedUpdate);
                }
            });
        }
    }

    @Override
    protected String getUrl() {
        return HomeConstant.HOT_CAR_URL;
    }

    @Override
    protected void addParams(@NonNull HashMap<String, String> map) {
        if (getIntent() != null) {
            HomeHotCar hotCar = (HomeHotCar) getIntent().getSerializableExtra("car");
            if (hotCar != null) {
                HomeBannerCar car = hotCar.getCarInfos();
                if (car != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(car.getCarId()).append(",").append(car.getCarName()).append(",").append(car.getLookWay())
                            .append(",").append(car.getBackground()).append(",").append(car.getBrandId())
                            .append(",").append(car.getSource()).append(",").append("0");
                    map.put("car", sb.toString());
                    map.put("brandUrl", hotCar.getImageUrl());
                    map.put(HomeConstant.DOWNLOAD, String.valueOf(SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_RECORD).getBoolean(String.valueOf(car.getCarId()), false)));
                    map.put(HomeConstant.UPDATE, String.valueOf(SpUtils.getInstance(HomeConstant.CAR_UPDATE_RECORD).getBoolean(String.valueOf(car.getCarId()), false)));
                }
            }
        }
    }


    public static void start(Context context, HomeHotCar car) {
        Intent intent = new Intent(context, HomeHotCarWebActivity.class);
        intent.putExtra("car", car);
        context.startActivity(intent);
    }

    @Override
    protected boolean isInterceptRequest() {
        return true;
    }

    @Override
    protected String getCarId() {
        if (getIntent() != null) {
            HomeHotCar hotCar = (HomeHotCar) getIntent().getSerializableExtra("car");
            if (hotCar != null) {
                if (hotCar.getCarInfos() != null) {
                    String carId = String.valueOf(hotCar.getCarInfos().getCarId());
                    if (carId != null) {
                        return carId;
                    }
                    return super.getCarId();
                }
            }
        }
        return super.getCarId();
    }

}


