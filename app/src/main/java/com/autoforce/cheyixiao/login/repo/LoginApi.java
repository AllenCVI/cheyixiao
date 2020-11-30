package com.autoforce.cheyixiao.login.repo;

import com.autoforce.cheyixiao.common.data.remote.bean.CheckVerifyCodeResult;
import com.autoforce.cheyixiao.common.data.remote.bean.LoginResult;
import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult;
import io.reactivex.Flowable;
import retrofit2.http.*;

/**
 * Created by xialihao on 2018/11/15.
 */
public interface LoginApi {

    // 
    @POST("v5D/sms/saler/login")
    @FormUrlEncoded
    Flowable<LoginResult> login(@Field("username") String username, @Field("passwd") String password);

    @FormUrlEncoded
    @POST("v5D/verifycode")
    Flowable<SimpleResult> getVerifyCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("v5D/verifycode/vms")
    Flowable<SimpleResult> getAudioVerifyCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("v5D/sms/saler/register")
    Flowable<LoginResult> postRegister(@Field("phone") String phone, @Field("passwd") String password, @Field("verify_code") String verifyCode,
                                       @Field("Cyx_ref") int cyxRef, @Field("reference") String reference);

    @FormUrlEncoded
    @POST("v5D/sms/saler/passwd/modify")
    Flowable<SimpleResult> postModifyPwd(@Field("phone") String phone, @Field("passwd") String passwd, @Field("token") String code);

    @GET("v5D/verifycode")
    Flowable<CheckVerifyCodeResult> postCheckVerifyCode(@Query("phone") String phone, @Query("code") String code);

}
