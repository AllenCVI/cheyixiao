package com.autoforce.cheyixiao.login.forget

import android.support.v4.app.FragmentActivity
import com.autoforce.cheyixiao.mvp.IPresenter
import com.autoforce.cheyixiao.mvp.IView

/**
 *  Created by xialihao on 2018/12/4.
 */
interface PwdForgetContract {

    interface Presenter : IPresenter {
        fun getVerifyCode(phone: String)
        fun modifyPwd(
            phone: String?,
            verifyCode: String?,
            password: String,
            passwordRepeat: String,
            activity: FragmentActivity
        )

        fun checkVerifyCode(
            phone: String,
            verifyCode: String,
            activity: FragmentActivity
        )
    }

    interface View : IView<Presenter> {
        fun onVerifyCodeGot() {

        }

        fun onUpdateComplete(){

        }

        fun setCodeButtonEnabled(enabled: Boolean){

        }

        fun onCheckComplete(token: String?) {

        }
    }
}