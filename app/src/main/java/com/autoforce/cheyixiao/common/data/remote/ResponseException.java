package com.autoforce.cheyixiao.common.data.remote;

import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult;

/**
 * Created by xialihao on 2018/11/22.
 */
public class ResponseException extends RuntimeException {

    private SimpleResult simpleResult;

    public ResponseException(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public String getMessage() {
        return simpleResult.getMsg();
    }

    public boolean isLoginOtherDevice() {
        return simpleResult.isLoginOtherDevice();
    }

    public boolean isAccountReject() {
        return simpleResult.isAccountReject();
    }

    public boolean isAccountLogout() {
        return simpleResult.isAccountLogout();
    }

    public boolean isNotLogin() {
        return simpleResult.isNotLogin();
    }
}
