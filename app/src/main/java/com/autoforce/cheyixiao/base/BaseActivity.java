package com.autoforce.cheyixiao.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.amap.api.location.AMapLocationListener;
import com.autoforce.cheyixiao.App;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.utils.*;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import flyn.Eyes;

import java.io.File;
import java.util.HashMap;

/**
 * Created by xialihao on 2018/11/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    private File file;
    private Uri imageUri;

    protected abstract int provideContentViewId();

    private Unbinder unBinder;

    private RxPermissions rxPermissions;

    private LocationUtil locationUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());

        initUMengPush();

        unBinder = ButterKnife.bind(this);

        initLocation();


        ActivityManager.getInstance().addActivity(this);

        if (isLightMode()) {
            Eyes.setStatusBarLightMode(this, Color.WHITE);
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(flyn.eyes.library.R.color.greyf0));
        }

        initView(savedInstanceState);
        initData();
    }

    private void initLocation() {
        locationUtil = new LocationUtil(App.getInstance());
    }

    protected void initUMengPush() {
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMengStatistics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMengStatistics.onPause(this);
    }

    protected void initData() {

    }

    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
            unBinder = null;
        }
        ToastUtil.reset();

        if (locationUtil != null) {
            locationUtil.clear();
        }

        ActivityManager.getInstance().removeActivity(this);
    }

    private boolean isLightMode() {
        return !(this instanceof ITranslucentAct);
    }


    //以下两个重写方法是为了设置app内字体不随系统字体变化而变化
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1f) {
            getResources();

        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();

        if (resources.getConfiguration().fontScale != 1f) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }

        return resources;
    }

    /*拍照*/
    protected void takePhoto() {
        AutoForcePermissionUtils.requestPermissions(this, new AutoForcePermissionUtils.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
                    if (file.exists()) {
                        file.delete();
                        file.mkdirs();
                    }
                    imageUri = Uri.fromFile(file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(BaseActivity.this, getPackageName() + ".fileprovider", file);
                    }
                    PhotoUtil.takePhoto(BaseActivity.this, imageUri, getTakePhotoRequestCode());
                }
            }

            @Override
            public void onPermissionDenied() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }


    /*选择照片*/
    protected void chosePhoto() {
        AutoForcePermissionUtils.requestPermissions(this, new AutoForcePermissionUtils.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                PhotoUtil.openAlbum(BaseActivity.this, getChosePhotoRequestCode());
            }

            @Override
            public void onPermissionDenied() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getTakePhotoRequestCode()) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmapFromUri = PhotoUtil.getBitmapFromUri(this, imageUri);
                File zipFile = PhotoUtil.getFile(this.file.getPath(), bitmapFromUri);
                HashMap<String, Object> takePhotoMap = new HashMap<>();
                String s = Base64Util.encodeBase64File(zipFile.getAbsolutePath());
                s = "data:img/jpg;base64," + s;
                takePhotoMap.put(getKey(), s);
                PhotoUtil.savePhoto2CDIM(this, zipFile);
                postBase64File(takePhotoMap, this, true);
            } else {
                postDefaild();
            }
        } else if (requestCode == getChosePhotoRequestCode()) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri chosePhotoUri = data.getData();
                    String chosePhotoPath = PhotoUtil.getPath(this, chosePhotoUri);
                    Bitmap bitmapFromUri = PhotoUtil.getBitmapFromUri(this, chosePhotoUri);
                    File file = PhotoUtil.getFile(chosePhotoPath, bitmapFromUri);
                    String s = Base64Util.encodeBase64File(file.getAbsolutePath());
                    s = "data:img/jpg;base64," + s;
                    HashMap<String, Object> chosePhotoMap = new HashMap<>();
                    chosePhotoMap.put(getKey(), s);
                    postBase64File(chosePhotoMap, this, true);
                }
            } else {
                postDefaild();
            }
        }
    }

    /**
     * 在 Fragment 中获取位置信息
     *
     * @param fragment
     * @param listener
     */
    @SuppressLint("CheckResult")
    public void getLocationInfo(Fragment fragment, AMapLocationListener listener) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(fragment);
        }
        startLocation(listener);
    }


    /**
     * 在 FragmentActivity 中获取位置信息
     *
     * @param activity
     * @param listener
     */
    public void getLocationInfo(FragmentActivity activity, AMapLocationListener listener) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(activity);
        }
        startLocation(listener);

    }

    @SuppressLint("CheckResult")
    private void startLocation(AMapLocationListener listener) {
        if (listener == null || rxPermissions == null) {
            return;
        }
        rxPermissions.request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(isAllow -> {
                    if (isAllow)
                        locationUtil.startLocation(listener);
                });
    }


    /*需要传照片的时候必须实现的方法  可仿照ApproveInfoWebActivity*/
    protected void postBase64File(HashMap<String, Object> takePhotoMap, Context context, boolean isShowLoading) {

    }

    /*设置拍照的请求码*/
    protected int getTakePhotoRequestCode() {
        return 0;
    }

    /*设置选照片的请求码*/
    protected int getChosePhotoRequestCode() {
        return 0;
    }

    /*设置上传照片对应key*/
    protected String getKey() {
        return "";
    }

    /*照片或者拍照取消*/
    protected void postDefaild() {

    }

}
