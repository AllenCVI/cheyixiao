package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by xialihao on 2018/11/15.
 * 自定义WebView ,便于后期替换
 */
public class AutoForceWebView extends WebView {

    public AutoForceWebView(Context context) {
        super(context);
    }

    public AutoForceWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
