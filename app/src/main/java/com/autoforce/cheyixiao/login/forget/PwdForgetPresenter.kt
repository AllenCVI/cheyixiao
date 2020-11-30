package com.autoforce.cheyixiao.login.forget

import android.support.v4.app.FragmentActivity
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.common.data.remote.bean.CheckVerifyCodeResult
import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber
import com.autoforce.cheyixiao.common.utils.MD5Util
import com.autoforce.cheyixiao.common.utils.ToastUtil
import com.autoforce.cheyixiao.common.utils.ValidateUtils
import com.autoforce.cheyixiao.base.BaseConstant.PWD_PREFIX
import com.autoforce.cheyixiao.login.repo.LoginRepository
import com.autoforce.cheyixiao.mvp.BasePresenter

/**
 *  Created by xialihao on 2018/12/4.
 */
class PwdForgetPresenter(view: PwdForgetContract.View) : BasePresenter<PwdForgetContract.View>(view),
    PwdForgetContract.Presenter {


    override fun getVerifyCode(phone: String) {
        // check input
        if (!ValidateUtils.isMobileNO(phone)) {
            ToastUtil.showToast(R.string.mobile_invalidate)
            return
        }

        mRootView.get()?.setCodeButtonEnabled(false)

        addDispose(
            LoginRepository.getInstance().getVerifyCode(phone)
                .subscribeWith(object : DefaultDisposableSubscriber<SimpleResult>() {
                    override fun success(data: SimpleResult?) {
                        mRootView.get()?.onVerifyCodeGot()
                    }

                    override fun onError(t: Throwable?) {
                        super.onError(t)
                        mRootView.get()?.setCodeButtonEnabled(true)
                    }
                })
        )
    }

    override fun modifyPwd(
        phone: String?,
        verifyCode: String?,
        password: String,
        passwordRepeat: String,
        activity: FragmentActivity
    ) {

        if (password != passwordRepeat) {
            ToastUtil.showToast(R.string.password_not_consistent)
            return
        }

        val encryptPwd = MD5Util.md5("$PWD_PREFIX$password")
        addDispose(
            LoginRepository.getInstance().postModifyPwd(phone, encryptPwd, verifyCode)
                .subscribeWith(object : DefaultDisposableSubscriber<SimpleResult>(activity, true) {
                    override fun success(data: SimpleResult?) {
                        mRootView.get()?.onUpdateComplete()
                    }
                })
        )
    }

    override fun checkVerifyCode(
        phone: String,
        verifyCode: String,
        activity: FragmentActivity
    ) {

        addDispose(
            LoginRepository.getInstance().postCheckVerifyCode(phone, verifyCode)
                .subscribeWith(object : DefaultDisposableSubscriber<CheckVerifyCodeResult>(activity, true){
                    override fun success(data: CheckVerifyCodeResult?) {
                        mRootView.get()?.onCheckComplete(data?.token)
                    }
                })
        )
    }
}