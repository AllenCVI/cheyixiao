package com.autoforce.cheyixiao.home.bean;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by liusilong on 2018/11/20.
 * version:1.0
 * Describe: 品牌信息
 */
public class HomeBrandInfo implements IndexableEntity {
    private String ico;
    private String name;
    private int id;
    private int quanjing;
    private int tupian;
    private int h5;

    public HomeBrandInfo(String ico, String name, int id) {
        this.ico = ico;
        this.name = name;
        this.id = id;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }

    public int getQuanjing() {
        return quanjing;
    }

    public void setQuanjing(int quanjing) {
        this.quanjing = quanjing;
    }

    public int getTupian() {
        return tupian;
    }

    public void setTupian(int tupian) {
        this.tupian = tupian;
    }

    public int getH5() {
        return h5;
    }

    public void setH5(int h5) {
        this.h5 = h5;
    }


}
