package com.autoforce.cheyixiao.home.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by liusilong on 2018/11/19.
 * version:1.0
 * Describe:
 */
public class HomeBannerCar implements Serializable {
    @SerializedName("car_id")
    private int carId;
    @SerializedName("car_name")
    private String carName;
    @SerializedName("look_way")
    private int lookWay;
    private String background;
    @SerializedName("brand_id")
    private int brandId;
    private String source;

    public HomeBannerCar(int carId, String carName, int lookWay, String background, int brandId, String source) {
        this.carId = carId;
        this.carName = carName;
        this.lookWay = lookWay;
        this.background = background;
        this.brandId = brandId;
        this.source = source;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getLookWay() {
        return lookWay;
    }

    public void setLookWay(int lookWay) {
        this.lookWay = lookWay;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
