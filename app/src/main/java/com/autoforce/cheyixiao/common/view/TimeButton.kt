package com.autoforce.cheyixiao.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.common.utils.RxDisposeManager
import com.autoforce.cheyixiao.login.RegisterAct
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 *  Created by xialihao on 2018/12/4.
 */
class TimeButton(context: Context, attributeSet: AttributeSet? = null) : AppCompatTextView(context, attributeSet) {

    @SuppressLint("CheckResult")
    fun startCountDown() {
        Observable.intervalRange(1L, 61L, 0L, 1L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<Long> {
                override fun onComplete() {
                    isEnabled = true
                    setText(R.string.get_verify_code)

                    listener?.onComplete()
                }

                override fun onSubscribe(d: Disposable) {
                    RxDisposeManager.get().add(RegisterAct.COUNT_DOWN_TAG, d)
                }

                override fun onNext(t: Long) {

                    text = "${60 - t}s"
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    fun clearCountDown() {
        RxDisposeManager.get().cancel(RegisterAct.COUNT_DOWN_TAG)
    }

    var listener: OnFinishListener? = null

    interface OnFinishListener {

        fun onComplete()
    }

}