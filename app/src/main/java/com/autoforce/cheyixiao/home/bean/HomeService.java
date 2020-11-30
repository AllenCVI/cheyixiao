package com.autoforce.cheyixiao.home.bean;

/**
 * Created by liusilong on 2018/11/23.
 * version:1.0
 * Describe: 首页服务
 */
public class HomeService {
    private String icon;
    private String name;
    private String url;

    public HomeService(String icon, String name, String url) {
        this.icon = icon;
        this.name = name;
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }
}
