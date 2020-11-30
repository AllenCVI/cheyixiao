package com.autoforce.cheyixiao.home.view.banner.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by liusilong on 2018/11/22.
 * version:1.0
 * Describe:
 */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
//            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            //
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
//            page.setScaleY(scale);
            /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        } else {
//            page.setScaleY(MIN_SCALE);
        }
    }
}
