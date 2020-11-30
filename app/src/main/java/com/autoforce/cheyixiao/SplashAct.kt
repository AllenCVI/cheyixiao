package com.autoforce.cheyixiao

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import com.autoforce.cheyixiao.base.BaseActivity
import com.autoforce.cheyixiao.base.ITranslucentAct
import com.autoforce.cheyixiao.common.data.local.LocalRepository
import com.autoforce.cheyixiao.login.InfoCertificateAct
import com.autoforce.cheyixiao.login.LoginActivity
import flyn.Eyes

/**
 *  Created by xialihao on 2018/11/20.
 */
class SplashAct : BaseActivity(), ITranslucentAct {

    private var mHandler: Handler? = null

    companion object {
        const val DELAY_TIME = 1500L
    }

    override fun provideContentViewId() = R.layout.activity_splash


    override fun initView(savedInstanceState: Bundle?) {

//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        Eyes.translucentStatusBar(this)
//        window.setBackgroundDrawableResource(R.drawable.bg_splash_theme)
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.redD5))
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.clearFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
////            window.statusBarColor = Color.TRANSPARENT
////            window.navigationBarColor = Color.TRANSPARENT
//        }


        mHandler = Handler()
        mHandler?.postDelayed({
            if (!LocalRepository.getInstance().isCertPass) {
                LocalRepository.getInstance().clearUserInfo()
                gotoLogin()
            } else {
                gotoMain()
            }
        }, DELAY_TIME)
    }

    private fun goCertificate() {
        InfoCertificateAct.start(this)
        finish()
    }


    private fun gotoLogin() {
        LoginActivity.startNoFlags(this)
        finish()
    }

    private fun gotoMain() {
        MainActivity.start(this)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
        mHandler = null
    }

}