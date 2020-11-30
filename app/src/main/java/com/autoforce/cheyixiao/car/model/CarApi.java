package com.autoforce.cheyixiao.car.model;

import com.autoforce.cheyixiao.common.data.remote.bean.*;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xialihao on 2018/11/15.
 *
 */
public interface CarApi {


    @GET("v5/sms/car/sell/query")
    Flowable<CarSourceListResult> getCarSourceList(@Query("format_id") Integer formatId, @Query("words") String words,
                                                   @Query("car_location") String carLocationId, @Query("page") Integer page,
                                                   @Query("page_size") Integer pageSize);

    @GET("v4/sms/car/search/all")
    Flowable<SearchCarListResult> getSearchCarList(@Query("brand_id") Integer brandId, @Query("series_id") Integer seriesId,
                                                   @Query("page") Integer page, @Query("page_size") Integer pageSize);

    @GET("v4/sms/car/search/mine")
    Flowable<SearchCarListResult> getMySearchCarList(@Query("page") Integer page, @Query("page_size") Integer pageSize);

    @GET("v4/sms/car/quote/receive")
    Flowable<SearchCarListResult> getReceiveCarList(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("v4/sms/car/quote/release")
    Flowable<SearchCarListResult> getReleaseCarList(@Query("page") int page, @Query("page_size") int pageSize);

    @GET("v5/sms/sell/format")
    Flowable<CarTypeResult> getCarTypes();

    @GET("v3/sms/user/area")
    Flowable<CarAreaResult> getCarArea();

    @GET("v4/sms/car/brands")
    Flowable<CarBrandsResult> getCarBrands();

    @GET("v5/sms/user/series")
    Flowable<CarSeriesResult> getCarSeriesByBrand(@Query("brand_id") Integer brandId);

    @GET("api/v5E/source/query")
    Flowable<CarSourceListResult> getCarSourceListByPrice(@Query("format_id") Integer formatId, @Query("words") String words,
                                                          @Query("car_location") String carLocationId, @Query("page") Integer page,
                                                          @Query("page_size") Integer pageSize, @Query("price_max") Integer priceMax,
                                                          @Query("price_min") Integer priceMin);
}
