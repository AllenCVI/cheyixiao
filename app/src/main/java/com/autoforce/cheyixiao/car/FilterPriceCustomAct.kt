package com.autoforce.cheyixiao.car

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseToolbarActivity
import com.autoforce.cheyixiao.common.isEmpty
import com.autoforce.cheyixiao.common.toInt
import com.autoforce.cheyixiao.common.utils.StringUtils
import com.autoforce.cheyixiao.common.utils.ToastUtil
import kotlinx.android.synthetic.main.act_filter_price.*

/**
 *  Created by xialihao on 2018/11/28.
 */

class FilterPriceCustomAct : BaseToolbarActivity() {

    companion object {

        const val INTENT_PRICE_MAX = "price_max"
        const val INTENT_PRICE_MIN = "price_min"

        fun start(fragment: Fragment, code: Int) {
            val intent = Intent(fragment.context, FilterPriceCustomAct::class.java)
            fragment.startActivityForResult(intent, code)
        }

        fun start(activity: Activity, code: Int) {
            val intent = Intent(activity, FilterPriceCustomAct::class.java)
            activity.startActivityForResult(intent, code)
        }
    }

    override fun getToolbarTitle() = R.string.custome_price

    override fun provideContentViewId() = R.layout.act_filter_price

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        showRightButton(StringUtils.getString(R.string.OK))
        btToolbarRight.setOnClickListener {

            if (checkInput()) {
                val data = Intent()
                data.putExtra(INTENT_PRICE_MAX, et_upper.toInt())
                data.putExtra(INTENT_PRICE_MIN, et_lower.toInt())
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }

    private fun checkInput(): Boolean {
        if (et_lower.isEmpty() || et_upper.isEmpty()) {
            ToastUtil.showToast(R.string.input_not_empty)
            return false
        }

        if (et_upper.toInt() < et_lower.toInt()) {
            ToastUtil.showToast(R.string.max_price_more_than_min_price)
            return false
        }

        if (et_upper.toInt() == 0 && et_lower.toInt() == 0) {
            ToastUtil.showToast(R.string.max_min_equals_zero_not_allowed)
            return false
        }

        return true
    }
}