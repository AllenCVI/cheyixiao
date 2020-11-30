package com.autoforce.cheyixiao.login

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import com.autoforce.cheyixiao.MainActivity
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseConstant
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity
import com.autoforce.cheyixiao.common.data.local.Bean
import com.autoforce.cheyixiao.common.data.local.LocalRepository
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo
import com.autoforce.cheyixiao.common.upload.UploadManager
import com.autoforce.cheyixiao.common.utils.ActivityManager
import com.autoforce.cheyixiao.common.utils.Network
import com.autoforce.cheyixiao.common.utils.ToastUtil
import java.util.*

/**
 *  Created by xialihao on 2018/12/4.
 */
class InfoCertificateAct : BaseX5WebViewActivity() {

    private var mUploadManager: UploadManager? = null
    private var mKey: String? = null
    private var mIdentifier: String? = null
    private var exitTime: Long = 0

    companion object {

        private const val GO_MAIN = "goMain"
        private const val IS_CLOSE_ALL = "isCloseAll"

        fun start(activity: Context, isCloseAll: Boolean = false) {
            val intent = Intent(activity, InfoCertificateAct::class.java)
            if (isCloseAll) {
                LocalRepository.getInstance().setCertReject()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra(IS_CLOSE_ALL, isCloseAll)
            }

            activity.startActivity(intent)
        }
    }

    override fun setOther() {
        mUploadManager = UploadManager(this)
    }

    override fun getUrl() = Network.INFO_CERTIFICATE_PAGE_URL

    override fun addParams(map: HashMap<String, String>) {
//        val result = intent.getParcelableExtra<LoginResult>("result")
        map[BaseConstant.TOKEN] = LocalRepository.getInstance().token
        map[BaseConstant.CERT_CODE] = LocalRepository.getInstance().certCode
        map[BaseConstant.SALER] = LocalRepository.getInstance().salerId
        map[BaseConstant.ROLE] = LocalRepository.getInstance().role
    }

    override fun childUse(bean: Bean?) {

        when (GO_MAIN) {
            bean?.method -> {
                LocalRepository.getInstance().setCertPass()
                MainActivity.start(this)
                ActivityManager.getInstance().clearActivities()
            }
        }
    }

    override fun dealWithUpload(bean: Bean?) {
        mKey = bean?.type
        mIdentifier = bean?.identifier
        if (BaseX5WebViewActivity.PHOTO_TAKE == bean?.method) {
            mUploadManager?.takePhoto()
        } else if (BaseX5WebViewActivity.ALBUM_OPEN == bean?.method) {
            mUploadManager?.chosePhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        mUploadManager?.onActivityResult(requestCode, resultCode, intent, mKey, object : UploadManager.UploadCallback {
            override fun onSuccess(data: ApproveInfo) {
                webView.loadUrl("javascript:interfaceCalledByApp('$mKey','${data.url}','$mIdentifier')")
            }

            override fun onError() {

            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (intent.getBooleanExtra(IS_CLOSE_ALL, false)) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

                if (System.currentTimeMillis() - exitTime > 3000) {
                    ToastUtil.showToast(R.string.double_click_quit)
                    exitTime = System.currentTimeMillis()
                } else {
                    doFinish()
                }

                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun doBack() {
        if (intent.getBooleanExtra(IS_CLOSE_ALL, false)) {
            doFinish()
        } else {
            super.doBack()
        }
    }

    private fun doFinish() {
        ActivityManager.getInstance().clearActivities()
    }

    override fun onDestroy() {
        super.onDestroy()

        mUploadManager?.clear()
    }

}