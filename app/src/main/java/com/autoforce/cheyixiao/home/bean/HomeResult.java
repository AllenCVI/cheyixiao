package com.autoforce.cheyixiao.home.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liusilong on 2018/11/19.
 * version:1.0
 * Describe:
 */
public class HomeResult {

    private String baseUrl;
    private List<HomeBanner> banner;
    private List<HomeService> services;
    @SerializedName("hot_cars")
    private List<HomeHotCar> hotCars;
    private List<HomeBrand> brands;

    public HomeResult(String baseUrl, List<HomeBanner> banner, List<HomeService> services, List<HomeHotCar> hotCars, List<HomeBrand> brands) {
        this.baseUrl = baseUrl;
        this.banner = banner;
        this.services = services;
        this.hotCars = hotCars;
        this.brands = brands;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<HomeBanner> getBanner() {
        return banner;
    }

    public void setBanner(List<HomeBanner> banner) {
        this.banner = banner;
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

    public List<HomeBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<HomeBrand> brands) {
        this.brands = brands;
    }
}
