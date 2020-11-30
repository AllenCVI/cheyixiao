package com.autoforce.cheyixiao.home.homenet;

import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.home.bean.HomeRoot;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by liusilong on 2018/11/29.
 * version:1.0
 * Describe:
 */
public interface HomeServerApi {
    @GET("v5E/saler/brands/phone")
    Flowable<HomeRoot> getHomeData();

    /*认证信息提交*/
    @FormUrlEncoded
    @POST("v3/sms/user/img")
    Flowable<ApproveInfo> postApproveInfo(@FieldMap Map<String, Object> map);
}
