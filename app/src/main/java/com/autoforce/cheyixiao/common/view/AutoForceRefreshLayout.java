package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.util.AttributeSet;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by xialihao on 2018/11/26.
 */
public class AutoForceRefreshLayout extends SmartRefreshLayout {


    public AutoForceRefreshLayout(Context context) {
        super(context);
    }

    public AutoForceRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAutoForceRefreshListener(OnAutoForceRefreshListener refreshListener) {

        setOnRefreshListener(v -> {
            if (refreshListener != null) {
                refreshListener.onRefresh(this);
            }
        });

        setOnLoadMoreListener(v -> {
            if (refreshListener != null) {
                refreshListener.onLoadMore(this);
            }
        });
    }

    public void setAutoForceRefreshEnabled(boolean isEnabled) {
        setEnableRefresh(isEnabled);
    }

    public void setAutoForceLoadMoreEnabled(boolean isEnabled) {
        setEnableLoadMore(isEnabled);
    }

    public boolean isAutoForceRefreshing() {
        return isRefreshing();
    }

    public void autoForceRefreshingAutomatic() {
        autoRefresh();
    }

    public void autoForceRefreshingAutomatic(int delay) {
        autoRefresh(delay);
    }

    public void autoForceLoadMoreAutomatic() {
        autoLoadMore();
    }

    public void finishAutoForceRefresh(boolean success) {
        finishRefresh(success);
    }

    public void finishAutoForceRefresh() {
        finishRefresh();
    }


    public void setAutoForceNoMoreData(boolean noMoreData){
        setNoMoreData(noMoreData);
    }


    public void finishAutoForceLoadMore() {
        finishLoadMore();
    }

    public void finishAutoForceLoadMoreNodelayTime() {
        finishLoadMore(0);
    }

    public void finishAutoForceRefreshNodelayTime() {
        finishRefresh(0);
    }


    public void finishAutoForceLoadMoreWithNoMoreData(){
        finishLoadMoreWithNoMoreData();
    }

    public boolean isAutoForceLoading(){
        return isLoading();
    }


    public static abstract class OnAutoForceRefreshListener {

        /**
         * 下拉刷新
         * @param autoForceRefreshLayout
         */
        public abstract void onRefresh(AutoForceRefreshLayout autoForceRefreshLayout);

        /**
         * 下拉加载更多
         * @param autoForceRefreshLayout
         */
        public void onLoadMore(AutoForceRefreshLayout autoForceRefreshLayout) {

        }
    }
}

