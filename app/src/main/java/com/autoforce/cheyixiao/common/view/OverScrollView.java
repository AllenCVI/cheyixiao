package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import com.orhanobut.logger.Logger;

public class OverScrollView extends ScrollView {

    private ViewGroup childView;
    private Rect normal = new Rect();//用于记录临界状态的左、上、右、下
    private int startY;
    private int startX;
    private int dy = 0;
    private boolean isFirst = true;
    private int measuredHeight;

    public OverScrollView(Context context) {
        super(context);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageView imageView;
    public void setImageView(ImageView imageView){
        this.imageView = imageView;

    }


    //获取子视图
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            childView = (ViewGroup) getChildAt(0);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(isFirst) {
            if(imageView!=null) {
                measuredHeight = imageView.getMeasuredHeight();
            }
        }
        normal.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
        isFirst = false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                startX = (int) ev.getX();
                dy = startY;
                break;

            case MotionEvent.ACTION_UP:
                //还原位置，回弹动画， 可以自己定于需要的动画
                TranslateAnimation animation = new TranslateAnimation(0, 0, normal.top, normal.top);
                animation.setDuration(200);
                childView.setAnimation(animation);
                ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
                layoutParams2.height = measuredHeight;
                imageView.setLayoutParams(layoutParams2);

                break;

            case MotionEvent.ACTION_MOVE:

                int detalY = (int) (ev.getY() - startY);
                int detalX = (int) (ev.getX() - startX);
                 dy = (int) (ev.getY() - dy);
                if (Math.abs(detalX) < Math.abs(detalY)) {
                    //detalY 乘以0.2 使得很难的效果
                    if(detalY<0) {
                    childView.layout(normal.left, (int) (normal.top + detalY * 0.2),
                            normal.right, (int) (normal.bottom + detalY * 0.2));
                    }
                    Logger.e("参数==%s" ,getScrollY() + "===" + detalY +"-0000000-"+ normal.top+detalY*0.2 + "---"+normal.bottom + detalY*0.2) ;
                    if(getScrollY() == 0) {
                        if(imageView!=null) {
                            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                            layoutParams.width = normal.right - normal.left + Math.abs(detalX) / 2;
                            layoutParams.height = (int) (imageView.getHeight() + dy / 4);
                            imageView.setLayoutParams(layoutParams);
                        }
                    }

                }
                dy = (int) ev.getY();
                break;

        }

        return super.dispatchTouchEvent(ev);
    }
}
