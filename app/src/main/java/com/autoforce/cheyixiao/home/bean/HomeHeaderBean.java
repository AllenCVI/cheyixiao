package com.autoforce.cheyixiao.home.bean;

import java.util.List;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe: home 页面头部实体
 */
public class HomeHeaderBean {
    private List<HomeBanner> banners;
    private List<HomeService> services;
    private List<HomeHotCar> hotCars;

    public HomeHeaderBean(List<HomeBanner> banners, List<HomeService> services, List<HomeHotCar> hotCars) {
        this.banners = banners;
        this.services = services;
        this.hotCars = hotCars;
    }

    public List<HomeBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<HomeBanner> banners) {
        this.banners = banners;
    }

    public List<HomeService> getServices() {
        return services;
    }

    public void setServices(List<HomeService> services) {
        this.services = services;
    }

    public List<HomeHotCar> getHotCars() {
        return hotCars;
    }

    public void setHotCars(List<HomeHotCar> hotCars) {
        this.hotCars = hotCars;
    }
}
