package com.autoforce.cheyixiao.home.bean;

/**
 * Created by liusilong on 2018/11/19.
 * version:1.0
 * Describe: 首页最外层实体类
 */
public class HomeRoot {
    private String code;
    private HomeResult results;

    public HomeRoot() {
    }

    public HomeRoot(String code, HomeResult results) {
        this.code = code;
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HomeResult getResults() {
        return results;
    }

    public void setResults(HomeResult results) {
        this.results = results;
    }
}
