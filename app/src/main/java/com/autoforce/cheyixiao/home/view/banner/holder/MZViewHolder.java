package com.autoforce.cheyixiao.home.view.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by liusilong on 2018/11/22.
 * version:1.0
 * Describe:
 */
public interface MZViewHolder<T> {
    View createView(Context context);

    void onBind(Context context, int position, T data);
}
