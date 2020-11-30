package com.autoforce.cheyixiao.customer.network;

import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerCarSystemBean;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liujialei on 2018/11/23
 */
public interface CustomerServerApi {


    @GET("v5/sms/users")
    Flowable<CustomerBean> getCustomerList(@Query("brand") int brand,@Query("series") int series,@Query("state") int state,@Query("page") int page);

    @GET("/v5/sms/pro/user/brands")
    Flowable<CustomerBrandBean> getBrandList();


    @GET("/v5/sms/pro/user/series")
    Flowable<CustomerCarSystemBean> getCarSysytemList(@Query("brand_id") int bid);



}
