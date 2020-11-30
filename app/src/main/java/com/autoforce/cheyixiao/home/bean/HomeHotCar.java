package com.autoforce.cheyixiao.home.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe:
 */
public class HomeHotCar implements Serializable {
    @SerializedName("car")
    private HomeBannerCar carInfos;
    @SerializedName("img_url")
    private String imageUrl;
    @SerializedName("brand_url")
    private String brandUrl;

    public HomeHotCar(HomeBannerCar carInfos, String imageUrl, String brandUrl) {
        this.carInfos = carInfos;
        this.imageUrl = imageUrl;
        this.brandUrl = brandUrl;
    }

    public HomeBannerCar getCarInfos() {
        return carInfos;
    }

    public void setCarInfos(HomeBannerCar carInfos) {
        this.carInfos = carInfos;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        this.brandUrl = brandUrl;
    }
}
