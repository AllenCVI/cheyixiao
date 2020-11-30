package com.autoforce.cheyixiao.common.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Created by liusilong on 2018/11/27.
 * version:1.0
 * Describe:
 */
public class FileProvider7 {
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }


    public static Uri getUriForFile24(Context context, File file) {
//        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
//                context.getPackageName() + ".android7.fileprovider",
//                file);

        Uri fileUri = null;
        try {
            fileUri = FileProvider.getUriForFile(context,"com.autoforce.cheyixiao.fileprovider",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUri;
    }


    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {

        Uri uri = getUriForFile(context, file);

        if(uri==null){
            return;
        }

        if (Build.VERSION.SDK_INT >= 24) {


            intent.setDataAndType(uri, type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(uri, type);
        }
    }


    public static void setIntentData(Context context,
                                     Intent intent,
                                     File file,
                                     boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {

            Uri uri = getUriForFile(context, file);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setData(Uri.fromFile(file));
        }
    }


    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {

        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, flag);
        }
    }

}
