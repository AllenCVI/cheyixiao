package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.autoforce.cheyixiao.customer.mycommon.MyOnclickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujialei on 2018/11/21
 */
public class MyLinearLayout extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    MyOnclickListener myOnclickListener;

    public MyLinearLayout(Context context) {
        this(context,null);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    public void clear(){

        this.removeAllViews();

    }



    public View setOnePiToCenter(int viewId){

         FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        if(layoutParams.gravity!=Gravity.CENTER){
            layoutParams.gravity = Gravity.CENTER;
            this.setLayoutParams(layoutParams);
        }
        View view = LayoutInflater.from(mContext).inflate(viewId,this,false);
        this.addView(view);
        return view;
    }



    public void setOnePiToCenter(View view){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        if(layoutParams.gravity!=Gravity.CENTER){
            layoutParams.gravity = Gravity.CENTER;
            this.setLayoutParams(layoutParams);
        }
        this.addView(view);
    }


    /**
     * 不再scrollview中
     * @param view
     */
    public void setNoScrollOnePiToCenter(View view){
        this.setGravity(Gravity.CENTER);
        this.addView(view);
    }




    public List<View> setListPicList_LayoutId(List<Integer> listId) {
        List<View> list = new ArrayList<>();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        if (layoutParams.gravity != Gravity.LEFT) {
            layoutParams.gravity = Gravity.LEFT;
            this.setLayoutParams(layoutParams);
        }
        for (int i = 0; i < listId.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(listId.get(i), this, false);
            list.add(view);
            this.addView(view);
        }
         return list;
    }







    public void setListPicList_View(List<View> listV) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        if (layoutParams.gravity != Gravity.LEFT) {
            layoutParams.gravity = Gravity.LEFT;
            this.setLayoutParams(layoutParams);
        }
        for (int i = 0; i < listV.size(); i++) {

            this.addView(listV.get(i));
        }
    }


    /**
     * 不再scrollview中
     * @param listV
     */
    public void setNoScrollListPicList_View(List<View> listV) {
        this.setGravity(Gravity.LEFT);
        for (int i = 0; i < listV.size(); i++) {
            this.addView(listV.get(i));
        }
    }



    public void setAllOnclickLstener(MyOnclickListener myOnclickListener){

        int childCount = getChildCount();
        if(childCount<=0){
            return;
        }

        for (int i=0;i<childCount;i++){
            View v = getChildAt(i);
            v.setTag(i);
            v.setOnClickListener(this);
        }
        this.myOnclickListener = myOnclickListener;
    }



    @Override
    public void onClick(View v) {
        myOnclickListener.myOnclick(v);
    }




}
