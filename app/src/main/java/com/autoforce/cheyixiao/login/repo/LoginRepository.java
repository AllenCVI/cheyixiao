package com.autoforce.cheyixiao.login.repo;

import com.autoforce.cheyixiao.common.data.remote.NetFactory;
import com.autoforce.cheyixiao.common.data.remote.bean.CheckVerifyCodeResult;
import com.autoforce.cheyixiao.common.data.remote.bean.LoginResult;
import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult;
import io.reactivex.Flowable;

/**
 * @author xlh
 * @date 2018/9/25.
 * 网络数据仓库，对外统一代理类
 */
public class LoginRepository {

    private final LoginApi serverApi;
    private static LoginRepository INSTANCE = new LoginRepository();

    public static LoginRepository getInstance() {
        return INSTANCE;
    }

    private LoginRepository() {
        serverApi = NetFactory.getRetrofit().create(LoginApi.class);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public Flowable<LoginResult> postLogin(String username, String password) {
        return serverApi.login(username, password);
    }

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    public Flowable<SimpleResult> getVerifyCode(String phone) {
        return serverApi.getVerifyCode(phone);
    }

    /**
     * 获取语音验证码
     * @param phone
     * @return
     */
    public Flowable<SimpleResult> getAudioVerifyCode(String phone) {
        return serverApi.getAudioVerifyCode(phone);
    }

    /**
     * 提交注册信息
     * @param phone
     * @param password
     * @param verifyCode
     * @param reference
     * @param cyxRef
     * @return
     */
    public Flowable<LoginResult> postRegister(String phone, String password, String verifyCode, String reference, int cyxRef) {
        return serverApi.postRegister(phone, password, verifyCode, cyxRef, reference);
    }

    /**
     * 修改密码
     * @param phone
     * @param password
     * @param code
     * @return
     */
    public Flowable<SimpleResult> postModifyPwd(String phone, String password, String code) {
        return serverApi.postModifyPwd(phone, password, code);
    }

    public Flowable<CheckVerifyCodeResult> postCheckVerifyCode(String phone, String code) {
        return serverApi.postCheckVerifyCode(phone, code);
    }
}
