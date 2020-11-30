package com.autoforce.cheyixiao.common.upload;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xialihao on 2018/12/5.
 */
public class UploadManager {

    private File file;
    private Uri imageUri;
    private FragmentActivity activity;

    private static final int PHOTO_TAKE = 200;
    private static final int ALBUM_OPEN = 300;

//    public static final String KEY = "pic";

    public UploadManager(FragmentActivity activity) {
        this.activity = activity;
    }

    /*拍照*/
    public void takePhoto() {

        AutoForcePermissionUtils.requestPermissions(activity, new AutoForcePermissionUtils.PermissionCallback() {
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
                        imageUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
                    }
                    PhotoUtil.takePhoto(activity, imageUri, PHOTO_TAKE);
                }
            }

            @Override
            public void onPermissionDenied() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }


    /*选择照片*/
    public void chosePhoto() {
        AutoForcePermissionUtils.requestPermissions(activity, new AutoForcePermissionUtils.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                PhotoUtil.openAlbum(activity, ALBUM_OPEN);
            }

            @Override
            public void onPermissionDenied() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, String key, UploadCallback callback) {

        if (resultCode == RESULT_OK) {
            String path = "";
            if (requestCode == PHOTO_TAKE) {
                path = file.getAbsolutePath();
            } else if (requestCode == ALBUM_OPEN) {
                if (data != null) {
                    Uri chosePhotoUri = data.getData();
                    path = PhotoUtil.getPath(activity, chosePhotoUri);
                }
            }

            if (!TextUtils.isEmpty(path)) {
                String base64File = Base64Util.encodeBase64File(path);
                base64File = "data:img/jpg;base64," + base64File;
                HashMap<String, Object> map = new HashMap<>();
                map.put(key, base64File);
                postBase64File(map, callback);
            }

        }
    }

    public void clear() {
        RxDisposeManager.get().cancel("uploadManager");
    }

    private void postBase64File(HashMap<String, Object> map, UploadCallback callback) {

        Logger.e("start upload");
        DefaultDisposableSubscriber<ApproveInfo> subscriber = MineRemoteRepository.getInstance().postApproveInfo(map)
                .subscribeWith(new DefaultDisposableSubscriber<ApproveInfo>(activity, true) {
                    @Override
                    protected void success(ApproveInfo data) {

                        Logger.e("upload complete");

                        if (callback != null) {
                            callback.onSuccess(data);
                        }

                    }

                    @Override
                    protected void failure(String errMsg) {
                        super.failure(errMsg);
                        Logger.e("upload error");
                        if (callback != null) {
                            callback.onError();
                        }
                    }
                });
        RxDisposeManager.get().add("uploadManager", subscriber);
    }

    public interface UploadCallback {
        void onSuccess(ApproveInfo data);

        void onError();
    }

}
