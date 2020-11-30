package com.autoforce.cheyixiao.car.source

import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.car.model.CarRepository
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils
import com.autoforce.cheyixiao.common.data.remote.bean.CarAreaResult
import com.autoforce.cheyixiao.common.data.remote.bean.CarSourceListResult
import com.autoforce.cheyixiao.common.data.remote.bean.CarTypeResult
import com.autoforce.cheyixiao.common.utils.*
import com.autoforce.cheyixiao.mvp.BasePresenter
import io.reactivex.Flowable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 *  Created by xialihao on 2018/11/16.
 */
class SourceChildPresenter(view: SourceChildContract.View) : BasePresenter<SourceChildContract.View>(view),
    SourceChildContract.Presenter {

    companion object {
        const val PAGE_SIZE = 20
        const val PAGE_START = 1
        const val CACHE_CAR_SOURCE = "cache_car_source"
    }

    private var page: Int = PAGE_START

    /*车辆类型相关*/
    private val mCarTypes: MutableList<CarTypeResult.ResultsBean> =
        mutableListOf(CarTypeResult.ResultsBean(StringUtils.getString(R.string.all), null))
    private var mCarTypeArray: Array<String?> = arrayOf()

    private var mMaxPrice: Int? = 0
    private var mMinPrice: Int? = 0

    /*车辆所在地*/
    private val mAreaList: MutableList<CarAreaResult.ResultBean.AreasBean> =
        mutableListOf(
            CarAreaResult.ResultBean.AreasBean(
                CarAreaResult.ResultBean.AreasBean.ProvinceBean(
                    StringUtils.getString(
                        R.string.all
                    ), null
                )
            )
        )
    private var mAreaArray: Array<String?> = arrayOf()

    override fun loadData(isLoadMore: Boolean) {

        if (isLoadMore) {
            page++
        } else {
            page = PAGE_START
        }

        val formatId = mCarTypes[mRootView.get()?.getCarTypePos()!!].id
        val words = mRootView.get()?.getSearchWords()
        val carLocationId = mAreaList[mRootView.get()?.getAreaPos()!!].province?.id

        addDispose(
            CarRepository.getInstance().getCarSourceList(
                formatId,
                words,
                carLocationId,
                page,
                PAGE_SIZE,
                mMaxPrice,
                mMinPrice
            )
                .map {
                    if (page == PAGE_START && it.results != null && it.results!!.isNotEmpty()) {
                        SpUtils.getInstance(CACHE_CAR_SOURCE).put(CACHE_CAR_SOURCE, GsonProvider.gson().toJson(it))
                    }
                    it
                }
                .subscribeWith(object : DefaultDisposableSubscriber<CarSourceListResult>() {
                    override fun success(data: CarSourceListResult?) {
                        mRootView.get()?.onDataGot(data?.results, isLoadMore)
                    }

                    override fun failure(errMsg: String?) {
                        mRootView.get()?.onDataError(isLoadMore)
                    }
                })
        )
    }


    override fun loadCarTypeData() {

        if (mCarTypeArray.isNotEmpty()) {
            showCarTypes(mCarTypeArray)
        } else {
            addDispose(
                CarRepository.getInstance().carTypes
                    .subscribeWith(object : DefaultDisposableSubscriber<CarTypeResult>() {
                        override fun success(data: CarTypeResult?) {

                            val results = data?.results
                            results?.let {
                                mCarTypes.clear()
                                mCarTypes.add(CarTypeResult.ResultsBean(StringUtils.getString(R.string.all), null))
                                mCarTypes.addAll(results)
                                mCarTypeArray = mCarTypes.map {
                                    it.name
                                }.toTypedArray()
                                showCarTypes(mCarTypeArray)
                            }
                        }
                    })
            )
        }
    }

    override fun loadCarArea() {

        if (mAreaArray.isNotEmpty()) {
            mRootView.get()?.onAreaInfoGot(mAreaArray)
        } else {
            CarRepository.getInstance().carArea
                .map {
                    it.result
                }
                .filter {
                    it.areas != null
                }
                .flatMapIterable {

                    this.mAreaList.clear()
                    this.mAreaList.add(
                        CarAreaResult.ResultBean.AreasBean(
                            CarAreaResult.ResultBean.AreasBean.ProvinceBean(
                                StringUtils.getString(
                                    R.string.all
                                ), null
                            )
                        )
                    )
                    this.mAreaList.addAll(it.areas!!)
                    this.mAreaList
                }
                .map {
                    it.province?.name
                }
                .toList()
                .subscribe(object : SingleObserver<List<String?>> {
                    override fun onSuccess(t: List<String?>) {
                        mAreaArray = t.toTypedArray()
                        mRootView.get()?.onAreaInfoGot(mAreaArray)
                    }

                    override fun onSubscribe(d: Disposable) {
                        addDispose(d)
                    }

                    override fun onError(e: Throwable) {
                        ErrorHandler.handleError(e)
                    }
                })
        }
    }

    override fun loadCacheData() {

        val json = SpUtils.getInstance(CACHE_CAR_SOURCE).getString(CACHE_CAR_SOURCE)

        if (json != null && json.isNotEmpty()) {
            val subscribe = Flowable.just(json)
                .map {
                    GsonProvider.gson().fromJson(json, CarSourceListResult::class.java)
                }
                .compose(ScheduleCompat.apply())
                .subscribe {
                    mRootView.get()?.onCacheDataLoad(it.results)
                }

            addDispose(subscribe)
        } else {
            mRootView.get()?.onCacheDataLoad(null)
        }
    }

    override fun cachePrice(minPrice: Int?, maxPrice: Int?) {
        this.mMaxPrice = maxPrice
        this.mMinPrice = minPrice

        loadData()
    }

    override fun cachePriceByPos(position: Int) {

        when (position) {
            0 -> {
                mMaxPrice = 10
                mMinPrice = 0
            }
            1 -> {
                mMinPrice = 10
                mMaxPrice = 20
            }
            2 -> {
                mMinPrice = 20
                mMaxPrice = 30
            }
            3 -> {
                mMinPrice = 30
                mMaxPrice = 0
            }
        }

        loadData()
    }

    private fun showCarTypes(carTypeArray: Array<String?>) {
        mRootView.get()?.onCarTypesGot(carTypeArray)
    }

}