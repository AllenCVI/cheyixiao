package com.autoforce.cheyixiao.home.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe:
 */
public class HomeBrand {
    private String key;
    @SerializedName("lists")
    private List<HomeBrandInfo> brandInfos;

    public HomeBrand(String key, List<HomeBrandInfo> brandInfos) {
        this.key = key;
        this.brandInfos = brandInfos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<HomeBrandInfo> getBrandInfos() {
        return brandInfos;
    }

    public void setBrandInfos(List<HomeBrandInfo> brandInfos) {
        this.brandInfos = brandInfos;
    }
}
