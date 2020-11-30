package com.autoforce.cheyixiao.login.forget

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.common.utils.ActivityManager
import com.autoforce.cheyixiao.common.utils.ToastUtil
import com.autoforce.cheyixiao.login.LoginActivity
import com.autoforce.cheyixiao.mvp.BaseMvpFragment
import kotlinx.android.synthetic.main.frag_pwd_forget_two.*

/**
 *  Created by xialihao on 2018/12/4.
 */
class PwdForgetTwoFragment : BaseMvpFragment<PwdForgetContract.Presenter>(), PwdForgetContract.View {

    companion object {
        fun newInstance(phone: String, token: String): PwdForgetTwoFragment {
            val frag = PwdForgetTwoFragment()
            val args = Bundle()
            args.putString(PwdForgetTwoAct.KEY_PHONE, phone)
            args.putString(PwdForgetTwoAct.KEY_TOKEN, token)
            frag.arguments = args
            return frag
        }
    }

    override fun provideContentViewId(): Int {
        return R.layout.frag_pwd_forget_two
    }

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

        input_pwd.addTextChangedListener(watcher)
        input_pwd_repeat.addTextChangedListener(watcher)

        tv_complete.setOnClickListener {

            mPresenter?.modifyPwd(
                arguments?.getString(PwdForgetTwoAct.KEY_PHONE), arguments?.getString(PwdForgetTwoAct.KEY_TOKEN),
                input_pwd.text.toString(), input_pwd_repeat.text.toString(), activity!!
            )
        }
    }

    override fun initData() {
        super.initData()

        PwdForgetPresenter(this)
    }

    override fun onUpdateComplete() {
        ToastUtil.showToast(R.string.pwd_setting_success)
        ActivityManager.getInstance().removeAboveActivities(LoginActivity::class.java)
    }

    private fun setButtonStatus() {

        tv_complete.isEnabled = !TextUtils.isEmpty(input_pwd.text.toString()) &&
                !TextUtils.isEmpty(input_pwd_repeat.text.toString())
    }

}