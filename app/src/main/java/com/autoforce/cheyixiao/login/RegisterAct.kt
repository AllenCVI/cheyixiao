package com.autoforce.cheyixiao.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseActivity
import com.autoforce.cheyixiao.base.BaseConstant
import com.autoforce.cheyixiao.base.CommonX5WebViewInterceptActivity
import com.autoforce.cheyixiao.common.data.local.LocalRepository
import com.autoforce.cheyixiao.common.data.remote.OkHttpClientProvider
import com.autoforce.cheyixiao.common.data.remote.bean.LoginResult
import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult
import com.autoforce.cheyixiao.common.utils.*
import com.autoforce.cheyixiao.common.view.TimeButton
import com.autoforce.cheyixiao.login.repo.LoginRepository
import kotlinx.android.synthetic.main.activity_register.*

/**
 *  Created by xialihao on 2018/12/3.
 *  万能验证码：901029
 *
 */
class RegisterAct : BaseActivity() {

    private var isSmsCountDown: Boolean = false

    companion object {

        const val SEND_CODE_TAG = "send_code_register"
        const val SEND_AUDIO_CODE_TAG = "send_audio_code_register"
        const val COUNT_DOWN_TAG = "count_down_register"
        const val POST_REGISTER_TAG = "post_register"


        fun start(activity: Activity) {
            val intent = Intent(activity, RegisterAct::class.java)
            activity.startActivity(intent)
        }
    }

    override fun provideContentViewId(): Int {
        return R.layout.activity_register
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            tv_register.isEnabled = isChecked
        }

        tv_code.setOnClickListener {
            getVerifyCode()
        }

        tv_code?.listener = object : TimeButton.OnFinishListener {
            override fun onComplete() {
                isSmsCountDown = true
                tv_code?.setText(R.string.audio_verify_code)
            }
        }

        tv_login.setOnClickListener {
            finish()
        }

        tv_register.setOnClickListener {

            val isVerify = checkRegisterInput()

            if (isVerify) {
                commitInfo()
            }
        }

        fl_check.setOnClickListener {
            checkbox.isChecked = !checkbox.isChecked
        }


        initAgreementText()
        checkbox.isChecked = true
    }

    override fun onDestroy() {
        super.onDestroy()

        RxDisposeManager.get().cancel(SEND_CODE_TAG)
        RxDisposeManager.get().cancel(POST_REGISTER_TAG)

        tv_code?.clearCountDown()
    }

    private fun initAgreementText() {
        val spannableString = SpannableString(StringUtils.getString(R.string.register_agreement))
        val end = spannableString.length;
        val start = end - 4;
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View?) {

                CommonX5WebViewInterceptActivity.start(this@RegisterAct, Network.AGREEMENT_INFO_PAGE_URL,null)
            }

            override fun updateDrawState(ds: TextPaint?) {
                //                super.updateDrawState(ds)
                ds?.color = ContextCompat.getColor(this@RegisterAct, R.color.blue39)
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tv_agreement.text = spannableString
        tv_agreement.movementMethod = LinkMovementMethod.getInstance()
        tv_agreement.highlightColor = Color.TRANSPARENT
    }

    private fun getVerifyCode() {
        val phone = input_mobile.text.toString()

        // check input
        if (!ValidateUtils.isMobileNO(phone)) {
            ToastUtil.showToast(R.string.mobile_invalidate)
            return
        }

        tv_code.isEnabled = false

        if (isSmsCountDown) {
            RxDisposeManager.get().add(
                SEND_AUDIO_CODE_TAG, LoginRepository.getInstance().getAudioVerifyCode(phone)
                    .subscribeWith(object : DefaultDisposableSubscriber<SimpleResult>() {
                        override fun success(data: SimpleResult?) {
                            ToastUtil.showToast(R.string.send_success)
                            startCountDown()
                        }

                        override fun onError(t: Throwable?) {
                            super.onError(t)
                            tv_code.isEnabled = true
                        }
                    })
            )
        } else {
            RxDisposeManager.get().add(
                SEND_CODE_TAG, LoginRepository.getInstance().getVerifyCode(phone)
                    .subscribeWith(object : DefaultDisposableSubscriber<SimpleResult>() {
                        override fun success(data: SimpleResult?) {
                            ToastUtil.showToast(R.string.send_success)
                            startCountDown()
                        }

                        override fun onError(t: Throwable?) {
                            super.onError(t)
                            tv_code.isEnabled = true
                        }
                    })
            )
        }

    }


    private fun startCountDown() {
        tv_code.startCountDown()
    }

    /**
     * 检查点击注册按钮前输入合法性
     */
    private fun checkRegisterInput(): Boolean {

        if (input_mobile.text.toString().isEmpty()) {
            ToastUtil.showToast(R.string.please_input_mobile)
            return false
        }

        if (input_verify_code.text.toString().isEmpty()) {
            ToastUtil.showToast(R.string.please_input_verify_code)
            return false
        }

        if (input_pwd.text.toString().isEmpty()) {
            ToastUtil.showToast(R.string.please_input_pwd)
            return false
        }

        if (input_pwd_repeat.text.toString().isEmpty()) {
            ToastUtil.showToast(R.string.please_input_pwd_repeat)
            return false
        }

        if (input_pwd.text.toString() != input_pwd_repeat.text.toString()) {
            ToastUtil.showToast(R.string.password_not_consistent)
            return false
        }

        return true
    }

    /**
     * 联网提交注册信息
     */
    private fun commitInfo() {

        val pwd = MD5Util.md5("${BaseConstant.PWD_PREFIX}${input_pwd.text}")
        RxDisposeManager.get().add(
            POST_REGISTER_TAG, LoginRepository.getInstance().postRegister(
                input_mobile.text.toString(),
                pwd,
                input_verify_code.text.toString(),
                input_referee.text.toString(),
                Integer.valueOf(OkHttpClientProvider.IS_MOBILE)
            )
                .subscribeWith(object : DefaultDisposableSubscriber<LoginResult>(this, true) {
                    override fun success(data: LoginResult?) {
                        LocalRepository.getInstance().saveUserInfo(data)
                        // 跳转到认证信息页面
                        InfoCertificateAct.start(this@RegisterAct)

                        ToastUtil.showToast(R.string.register_success)

                        finish()
                    }
                })
        )

    }


}