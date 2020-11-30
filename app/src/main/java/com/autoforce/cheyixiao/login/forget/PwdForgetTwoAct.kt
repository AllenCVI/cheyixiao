package com.autoforce.cheyixiao.login.forget

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseToolbarActivity

/**
 *  Created by xialihao on 2018/12/4.
 */
class PwdForgetTwoAct : BaseToolbarActivity() {

    companion object {

        const val KEY_PHONE = "phone"
        const val KEY_TOKEN = "token"
        /**
         * @param phone 手机号码
         * @param verifyCode 验证码
         */
        fun start(activity: Activity, phone: String, token: String?) {
            val intent = Intent(activity, PwdForgetTwoAct::class.java)
            intent.putExtra(KEY_PHONE, phone)
            intent.putExtra(KEY_TOKEN, token)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle() = R.string.setting_new_pwd

    override fun userFragment(): Fragment {
        return PwdForgetTwoFragment.newInstance(intent.getStringExtra(KEY_PHONE), intent.getStringExtra(KEY_TOKEN))
    }
}