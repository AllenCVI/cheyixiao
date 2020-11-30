package com.autoforce.cheyixiao.common;

import android.app.Activity
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.common.data.remote.bean.UserInfoBean
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber
import com.autoforce.cheyixiao.common.utils.RxDisposeManager
import com.autoforce.cheyixiao.common.utils.ToastUtil
import com.autoforce.cheyixiao.login.InfoCertificateAct
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository
import java.lang.ref.WeakReference

/**
 * Created by xialihao on 2018/12/17.
 * description: 校验用户审核是否通过
 */
class UserInfoCheckManager(activity: Activity) {

    private var mWeakReference = WeakReference(activity)

    companion object {
        private const val CERT_CHECK_TAG = "info_check_manager_check"
    }

    fun checkUserInfo(listener: OnCheckResultListener) {

        RxDisposeManager.get().add(
            CERT_CHECK_TAG, MineRemoteRepository.getInstance().userInfo
                .subscribeWith(object : DefaultDisposableSubscriber<UserInfoBean>() {
                    override fun success(data: UserInfoBean?) {

                        val results = data?.results
                        results?.let {
                            if (results.isPass) {
                                listener.onCertPass()
                            } else {
                                listener.onCertFail(mWeakReference.get())
                            }
                        }
                    }
                })
        )
    }

    fun dispose() {
        RxDisposeManager.get().cancel(CERT_CHECK_TAG)
    }

    abstract class OnCheckResultListener {
        abstract fun onCertPass()
        fun onCertFail(activity: Activity?) {
            ToastUtil.showToast(R.string.please_pass_cert_first)
            activity?.let {
                InfoCertificateAct.start(activity)
            }
        }
    }
}
