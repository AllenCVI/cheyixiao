package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.PopupWindow;
import com.autoforce.cheyixiao.R;

/**
 * Created by liujialei on 2018/11/26
 */
public class MyPopupWindow{


    /**
     * 配置好的PopupWindow先写一版之后再改进
     *
     */


    private MyPopupWindow() {}

    private static class SingletonInstance {
        private static final PopupWindow INSTANCE = new PopupWindow();
    }

    public static PopupWindow getInstance(Context context, View contentView) {

        PopupWindow myPopupWindow = SingletonInstance.INSTANCE;
        myPopupWindow.setContentView(contentView);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myPopupWindow.dismiss();

            }
        });


        myPopupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.half_transparent_05)));
        myPopupWindow.setOutsideTouchable(true);
        myPopupWindow.setTouchable(true);
        myPopupWindow.setFocusable(true);
        myPopupWindow.update();
        return myPopupWindow;
    }





}
