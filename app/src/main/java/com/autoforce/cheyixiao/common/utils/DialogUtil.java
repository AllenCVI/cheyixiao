package com.autoforce.cheyixiao.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import com.autoforce.cheyixiao.R;

public class DialogUtil {

    private  AlertDialog alertDialog;
    private  AlertDialog.Builder builder;

    public DialogUtil(Context context){
        builder = new AlertDialog.Builder(context);
    }


    /*提示对话框*/
    public  void hintDialog(String msg){
        builder.setCancelable(true);
        builder.setIcon(R.drawable.icon_approve_info);
        builder.setTitle(R.string.hint);
        builder.setMessage((String)msg);
        builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dissmiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        show();
    }

    /*确认取消dialog
    * t 要展示的数据 必传
    * view 传入dialog的view
    * layoutid 传入dialog的布局id    此参数和view必须穿一个  建议传view
    * title 标题 必传
    * onSureClickListenre 确认按钮点击的接收接口   有确认按钮式必须实现
    * isShowCacle    isShowsure  是否显示取消或者确认
    *
    * */
    public void sureOrcacleDialog(View view  ,String title,  boolean isShowCacle ,boolean isShowsure ,OnSureClickListener onSureClickListener ){

        if(view == null){
            throw new IllegalArgumentException("view不能为空");
        }else {
            builder.setView(view);
        }

        builder.setTitle(title).setIcon(R.drawable.icon_approve_info).setCancelable(false);

        if(isShowCacle) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dissmiss();
                }
            }).setCancelable(true);
        }
        if(isShowsure) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (onSureClickListener != null) {
                        onSureClickListener.sureClick();
                        dissmiss();
                    }
                }
            }).setCancelable(false);
        }
        alertDialog =builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        show();

    }


    public void show(){
        if(alertDialog!=null ){
            alertDialog.show();
        }
    }

    public void dissmiss(){
        if(alertDialog!=null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    private OnSureClickListener onSureClickListener;
    public interface OnSureClickListener{
        void sureClick();
    }
    public void setOnSureClickListener(OnSureClickListener onSureClickListener){
        this.onSureClickListener = onSureClickListener;
    }
}
