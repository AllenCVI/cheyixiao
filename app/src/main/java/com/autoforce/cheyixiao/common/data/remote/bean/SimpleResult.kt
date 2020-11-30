package com.autoforce.cheyixiao.common.data.remote.bean

/**
 * Created by xialihao on 2018/11/20.
 */
open class SimpleResult {

    val code: Int = 0
    val msg: String? = null
    val message: String? = null

    val isError: Boolean
        get() = code != RESULT_OK

    val isLoginOtherDevice: Boolean
        get() = code == LOGIN_OTHER_DEVICE

    val isAccountReject: Boolean
        get() = code == ACCOUNT_REJECT

    val isNotLogin: Boolean
        get() = code == NOT_LOGIN

    val isAccountLogout: Boolean
        get() = code == ACCOUNT_LOGOUT

    companion object {
        private const val RESULT_OK = 200
        // 其他设备登录
        private const val LOGIN_OTHER_DEVICE = 302
        //账号驳回
        private const val ACCOUNT_REJECT = 303
        // 未登录
        private const val NOT_LOGIN = 403
        // 账号被停用
        private const val ACCOUNT_LOGOUT = 306
    }
}
