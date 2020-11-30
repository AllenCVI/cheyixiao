package com.autoforce.cheyixiao.home.adapter;

import com.autoforce.cheyixiao.home.bean.*;

/**
 * Created by liusilong on 2018/11/23.
 * version:1.0
 * Describe:
 */
public interface ItemClickListener {

    /**
     * banner 点击事件
     *
     * @param homeBanner banner 跳转链接
     */
    void bannerOnclick(HomeBanner homeBanner);

    /**
     * 服务点击事件如：购车，物流等...
     *
     * @param homeService 服务实体
     */
    void serviceOnClick(HomeService homeService);

    /**
     * 热门车型点击事件
     *
     * @param car 车辆
     */
    void hotCarOnClick(HomeHotCar car);

    /**
     * 品牌点击事件
     *
     * @param homeBrandInfo 品牌信息
     */
    void brandonClick(HomeBrandInfo homeBrandInfo);
}
