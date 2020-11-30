package com.autoforce.cheyixiao.login.forget

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseToolbarActivity

/**
 *  Created by xialihao on 2018/12/4.
 */
class PwdForgetOneAct : BaseToolbarActivity() {

    companion object {

        fun start(activity: Activity) {
            val intent = Intent(activity, PwdForgetOneAct::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle() = R.string.verify_mobile

    override fun userFragment(): Fragment {
        return PwdForgetOneFragment.newInstance()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }


}