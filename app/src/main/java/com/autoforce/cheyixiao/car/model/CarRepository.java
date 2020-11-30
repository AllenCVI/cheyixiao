package com.autoforce.cheyixiao.car.model;

import android.support.annotation.Nullable;
import com.autoforce.cheyixiao.common.data.remote.NetFactory;
import com.autoforce.cheyixiao.common.data.remote.bean.*;
import io.reactivex.Flowable;

/**
 * @author xlh
 * @date 2018/9/25.
 * 网络数据仓库，对外统一代理类
 */
public class CarRepository {

    private final CarApi serverApi;
    private static CarRepository INSTANCE = new CarRepository();

    public static CarRepository getInstance() {
        return INSTANCE;
    }

    private CarRepository() {
        serverApi = NetFactory.getRetrofit().create(CarApi.class);
    }

    /**
     * 获取车源列表
     *
     * @param formatId
     * @param words
     * @param carLocationId
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<CarSourceListResult> getCarSourceList(@Nullable Integer formatId, String words, String carLocationId, Integer page, Integer pageSize) {
        return serverApi.getCarSourceList(formatId, words, carLocationId, page, pageSize);
    }

    public Flowable<CarSourceListResult> getCarSourceList(Integer formatId, String words, String carLocationId, Integer page,
                                                                 Integer pageSize, Integer priceMax, Integer priceMin) {
//        if (priceMax != null) {
//            priceMax *= 10000;
//        }
//
//        if (priceMin != null) {
//            priceMin *= 10000;
//        }

        return serverApi.getCarSourceListByPrice(formatId, words, carLocationId, page, pageSize, priceMax, priceMin);
    }

    /*
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SearchCarListResult> getMySearchCarList(Integer page, Integer pageSize) {
        return serverApi.getMySearchCarList(page, pageSize);
    }

    /**
     * 获取我收到的报价的列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SearchCarListResult> getReceiveCarList(int page, int pageSize) {
        return serverApi.getReceiveCarList(page, pageSize);
    }

    /**
     * 我发布的车源
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SearchCarListResult> getReleaseCarList(int page, int pageSize) {
        return serverApi.getReleaseCarList(page, pageSize);
    }


    /**
     * 获取寻车列表
     *
     * @param brandId
     * @param seriesId
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SearchCarListResult> getSearchCarList(Integer brandId, Integer seriesId, Integer page, Integer
            pageSize) {
        return serverApi.getSearchCarList(brandId, seriesId, page, pageSize);
    }

    /**
     * 获取车源类型
     *
     * @return
     */
    public Flowable<CarTypeResult> getCarTypes() {
        return serverApi.getCarTypes();
    }

    /**
     * 获取车源所在地集合
     *
     * @return
     */
    public Flowable<CarAreaResult> getCarArea() {
        return serverApi.getCarArea();
    }

    /**
     * 获取寻车品牌
     *
     * @return
     */
    public Flowable<CarBrandsResult> getCarBrands() {
        return serverApi.getCarBrands();
    }

    /**
     * 获取寻车对应品牌车系
     *
     * @return
     */
    public Flowable<CarSeriesResult> getCarSeriesByBrand(Integer brandId) {
        return serverApi.getCarSeriesByBrand(brandId);
    }

}
