package com.autoforce.cheyixiao.mine.minenet;

import com.autoforce.cheyixiao.common.data.remote.bean.*;
import io.reactivex.Flowable;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by xialihao on 2018/11/15.
 * Retrofit请求接口
 */
public interface MineServerApi {
    /*可提现*/
    @GET("v5/balance")
    Flowable<CanMoneyBean> getCanMoney();
    /*提现中*/
    @GET("v5/balance/process")
    Flowable<CanMoneyBean> getCaingMoney();
    /*已提现*/
    @GET("v5/balance/finish")
    Flowable<CanMoneyBean> getCaedMoney();
    /*提现失败*/
    @GET("v5/balance/reject")
    Flowable<CanMoneyBean> getFrostCanMoney();
    /*提现*/
    @FormUrlEncoded
    @POST("v5/balance")
    Flowable<CanMoneyBean> postCanMoney(@Field("bill_ids") String sillId);

    /*用户信息接口*/
    @GET("v5/sms/pro/user/mine")
    Flowable<UserInfoBean> getUserInfo();

    /*用户银行卡信息*/
    @GET("v5/sms/saler/info/verification")
    Flowable<BlankCardInfo> getUserBlankCardInfo();

    /*认证信息提交*/
    @FormUrlEncoded
    @POST("v3/sms/user/img")
    Flowable<ApproveInfo> postApproveInfo(@FieldMap Map<String , Object> map);




}
