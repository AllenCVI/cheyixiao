package com.autoforce.cheyixiao.home.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liusilong on 2018/11/19.
 * version:1.0
 * Describe: 轮播实体
 */
public class HomeBanner {
    private int id;
    @SerializedName("img_url")
    private String imageUrl;
    private String url;
    @SerializedName("brand_url")
    private String brandUrl;
    @SerializedName("car")
    private HomeBannerCar carInfo;

    public HomeBanner(int id, String imageUrl, String url, String brandUrl, HomeBannerCar carInfos) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.url = url;
        this.brandUrl = brandUrl;
        this.carInfo = carInfos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(String brandUrl) {
        this.brandUrl = brandUrl;
    }

    public HomeBannerCar getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(HomeBannerCar carInfo) {
        this.carInfo = carInfo;
    }
}
