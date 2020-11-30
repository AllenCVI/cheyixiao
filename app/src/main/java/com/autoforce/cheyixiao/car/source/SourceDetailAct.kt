package com.autoforce.cheyixiao.car.source

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity
import com.autoforce.cheyixiao.common.data.local.Bean
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo
import com.autoforce.cheyixiao.common.upload.UploadManager
import com.autoforce.cheyixiao.common.utils.Network
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import java.util.*


/**
 *  Created by xialihao on 2018/11/30.
 */
open class SourceDetailAct : BaseX5WebViewActivity() {

    private var mUploadManager: UploadManager? = null
    private var mKey: String? = null
    private var mIdentifier: String? = null
    private var mProgressDialog: ProgressDialog? = null
    private var rootView: ViewGroup? = null
    private var payWebView: WebView? = null

    companion object {

        private const val CALL_PHONE = "callPhone"
        private const val WECHAT_PAY = "WeChatPay"
        private const val PAY_VIEW_TAG = "payWebView"
        private const val GO_MINE = "goMine"

        fun start(activity: Activity, id: String, code: Int) {
            val intent = Intent(activity, SourceDetailAct::class.java)
            intent.putExtra("id", id)
            activity.startActivityForResult(intent, code)
        }
    }

    override fun setOther() {
        mUploadManager = UploadManager(this)
    }

    override fun getUrl() = Network.SOURCE_DETAIL_PAGE_URL

    override fun addParams(map: HashMap<String, String>) {
        map["id"] = intent.getStringExtra("id")
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

    override fun childUse(bean: Bean?) {

        if (CALL_PHONE == bean?.method) {
            doDial(bean.param?.phoneNumber)
        } else if (WECHAT_PAY == bean?.method) {

            rootView = findViewById(android.R.id.content)

            if (null != rootView) {
                payWebView = WebView(this)
                payWebView?.visibility = View.GONE
                payWebView?.tag = PAY_VIEW_TAG
                payWebView?.setBackgroundColor(0)
                setWebViewClient()
                setWebViewProperty()
                rootView?.addView(payWebView)

                val payUrl = bean.parame[0].url
                if (!TextUtils.isEmpty(payUrl)) {
                    mProgressDialog = showProgressDialog(this, "", "正在启动...");
                    payWebView?.loadUrl(payUrl);
                }

            }
        } else if (GO_MINE == bean?.method) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    //配置webview
    @SuppressLint("SetJavaScriptEnabled")
    protected fun setWebViewProperty() {
        val settings = payWebView?.settings
        // 支持JavaScript
        settings?.javaScriptEnabled = true
        // 支持通过js打开新的窗口
        settings?.javaScriptCanOpenWindowsAutomatically = true
        settings?.domStorageEnabled = true;
    }


    private fun setWebViewClient() {
        payWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("weixin:") || url.startsWith("alipayqr:") || url.startsWith("alipays:") || url.startsWith(
                        "qq"
                    ) || url.startsWith("mqqapi:")
                ) {
                    try {
                        hideProgressDialog()
                        startActivity(Intent("android.intent.action.VIEW", Uri.parse(url)))
                    } catch (localActivityNotFoundException: ActivityNotFoundException) {
                        Toast.makeText(this@SourceDetailAct, "请检查是否安装客户端", Toast.LENGTH_SHORT).show()
                    }

                    true
                } else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        mUploadManager?.onActivityResult(
            requestCode,
            resultCode,
            intent,
            mKey,
            object : UploadManager.UploadCallback {
                override fun onSuccess(data: ApproveInfo) {
                    webView.loadUrl("javascript:interfaceCalledByApp('$mKey','${data.url}','$mIdentifier')")
                }

                override fun onError() {

                }
            })
    }

    override fun onResume() {
        super.onResume()

        if (payWebView != null) {
            removePayWebView()
            hideProgressDialog()
            //Toast.makeText(context, "通知前端查询...", Toast.LENGTH_SHORT).show();
            webView.loadUrl("javascript:payCallback()")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mUploadManager?.clear()
    }


    private fun removePayWebView() {
        if (rootView != null) {
            val childCount = rootView?.childCount

            childCount?.let {
                val view = rootView?.getChildAt(childCount - 1)
                if (view?.tag == PAY_VIEW_TAG) {
                    rootView?.removeView(view)
                    payWebView = null
                }
            }

        }
    }


    private fun doDial(phoneNum: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

    private fun showProgressDialog(context: Context, title: String, message: String): ProgressDialog? {
        val dialog = ProgressDialog.show(context, title, message, false, true)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setOnCancelListener(mCanListener)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        return dialog
    }

    private var mCanListener: DialogInterface.OnCancelListener =
        DialogInterface.OnCancelListener { dlg -> dlg.dismiss() }


    private fun hideProgressDialog() {
        val dialog = mProgressDialog
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }

}