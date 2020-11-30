package com.autoforce.cheyixiao.home.mvp;

import com.autoforce.cheyixiao.home.bean.HomeBrandInfo;
import com.autoforce.cheyixiao.home.bean.HomeHeaderBean;

import java.util.List;

/**
 * Created by liusilong on 2018/11/21.
 * version:1.0
 * Describe:
 */
public interface HomeView {

    /**
     * 更新 首页 adapter
     *
     * @param headerBean    头部数据
     * @param brandInfoList 列表数据
     */
    void setAdapter(HomeHeaderBean headerBean, List<HomeBrandInfo> brandInfoList);

    void showNoDataState();

    void showErrorState();

    void showNormalState();

    void showLoadingView();
}
