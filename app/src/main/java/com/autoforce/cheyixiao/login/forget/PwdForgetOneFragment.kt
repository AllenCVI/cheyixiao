package com.autoforce.cheyixiao.login.forget

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.common.utils.ToastUtil
import com.autoforce.cheyixiao.mvp.BaseMvpFragment
import kotlinx.android.synthetic.main.frag_pwd_forget_one.*

/**
 *  Created by xialihao on 2018/12/4.
 */
class PwdForgetOneFragment : BaseMvpFragment<PwdForgetContract.Presenter>(), PwdForgetContract.View {

    companion object {
        fun newInstance(): PwdForgetOneFragment {
            val frag = PwdForgetOneFragment()
            return frag
        }
    }

    override fun provideContentViewId() = R.layout.frag_pwd_forget_one

    override fun initView(savedInstanceState: Bundle?) {

        val watcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                setButtonStatus()
            }
        }

        input_verify_code.addTextChangedListener(watcher)
        input_mobile.addTextChangedListener(watcher)


        tv_next.setOnClickListener {

            if (activity != null) {
                mPresenter?.checkVerifyCode(input_mobile.text.toString(), input_verify_code.text.toString(), activity!!)
            }
        }

        tv_code.setOnClickListener {
            mPresenter?.getVerifyCode(input_mobile.text.toString())
        }
    }

    override fun initData() {
        super.initData()

        PwdForgetPresenter(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tv_code?.clearCountDown()
    }

    override fun onVerifyCodeGot() {
        ToastUtil.showToast(R.string.send_success)
        startCountDown()
    }

    override fun onCheckComplete(token: String?) {
        if (activity != null) {
            PwdForgetTwoAct.start(activity!!, input_mobile.text.toString(), token)
        }
    }


    override fun setCodeButtonEnabled(enabled: Boolean) {
        tv_code.isEnabled = true
    }

    private fun setButtonStatus() {

        tv_next.isEnabled = !TextUtils.isEmpty(input_mobile.text.toString()) &&
                !TextUtils.isEmpty(input_verify_code.text.toString())
    }

    private fun startCountDown() {
        tv_code.startCountDown()
    }
}