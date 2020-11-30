package com.autoforce.cheyixiao.car.source.me

import android.content.Context
import android.content.Intent
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity
import com.autoforce.cheyixiao.common.data.local.Bean
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo
import com.autoforce.cheyixiao.common.upload.UploadManager
import com.autoforce.cheyixiao.common.utils.Network
import java.util.*

/**
 *  Created by xialihao on 2018/11/28.
 */
class MyCarSourceAct : BaseX5WebViewActivity() {

    private var mUploadManager: UploadManager? = null
    private var mKey: String? = null
    private var mIdentifier: String? = null

    //    BaseTabActivity<MyCarSourceFragment>() {
//
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyCarSourceAct::class.java)
            context.startActivity(intent)
        }
    }

//    override fun getPageTitles(): Array<CharSequence> {
//        return arrayOf(*resources.getStringArray(R.array.my_car_source_tab))
//    }
//
//    override fun getChildFragment(index: Int): MyCarSourceFragment {
//        return MyCarSourceFragment.newInstance(index)
//    }

    override fun setOther() {
        mUploadManager = UploadManager(this)
    }

    override fun getUrl() = Network.MY_SOURCE_PAGE_URL

    override fun addParams(map: HashMap<String, String>) {

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