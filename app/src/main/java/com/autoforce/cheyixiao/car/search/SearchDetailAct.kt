package com.autoforce.cheyixiao.car.search

import android.app.Activity
import android.content.Intent
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity
import com.autoforce.cheyixiao.common.data.local.Bean
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo
import com.autoforce.cheyixiao.common.upload.UploadManager
import com.autoforce.cheyixiao.common.utils.Network


/**
 *  Created by xialihao on 2018/11/30.
 */
class SearchDetailAct : BaseX5WebViewActivity() {
    private var mUploadManager: UploadManager? = null
    private var mKey: String? = null
    private var mIdentifier: String? = null

    companion object {

        fun start(activity: Activity, id: String?) {
            val intent = Intent(activity, SearchDetailAct::class.java)
            intent.putExtra("find_id", id)
            activity.startActivity(intent)
        }
    }

    override fun setOther() {
        mUploadManager = UploadManager(this)
    }

    override fun getUrl() = Network.SEARCH_DETAIL_PAGE_URL

    override fun addParams(map: java.util.HashMap<String, String>) {
        map["find_id"] = intent.getStringExtra("find_id")
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

    override fun onDestroy() {
        super.onDestroy()

        mUploadManager?.clear()
    }

}