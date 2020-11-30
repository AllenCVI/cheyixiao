package com.autoforce.cheyixiao.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

/*
* 请求权限辅助类
* @autor  fox
* creat at 2018/11/30 14:55
*
*/
public class PermissionHelper {

    private Activity activity;
    private PermissionInterface permissionInterface;

    public PermissionHelper(Activity activity, PermissionInterface permissionInterface) {
        this.activity = activity;
        this.permissionInterface = permissionInterface;
    }

    /*
    * 开始请求权限  在PermissionUtil中对SDK版本已做了判断 如果是低于M版本 也会回调requestPermissionSuccess方法
    * @partm
    */
    
    public void requestPermission(){
        String[] denidPermissions = PermissionUtil.getDenidPermissions(activity, permissionInterface.getPermission());
        if(denidPermissions!=null && denidPermissions.length>0){
            PermissionUtil.requestPermission(activity ,denidPermissions ,permissionInterface.getPermissionRequestCode());
        }else {
            permissionInterface.requestPermissionSuccess();
        }
    }

    /*
    *
    * */
    public boolean requestPermissionResult(int requsetCode ,String[] permissions , int[] gradResults){
        if(requsetCode == permissionInterface.getPermissionRequestCode()){
            boolean isAllGranted = true;//是否全部权限已授权
            for(int result : gradResults){
                if(result == PackageManager.PERMISSION_DENIED){
                    isAllGranted = false;
                    break;
                }
            }
            if(isAllGranted){
                //已全部授权
                permissionInterface.requestPermissionSuccess();
            }else{
                //权限有缺失
                permissionInterface.requestPermissionFaile();
            }
            return true;
        }
        return false;

    }
}
