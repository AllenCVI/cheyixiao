package com.autoforce.cheyixiao.car.search

import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.car.model.CarLocalRepository
import com.autoforce.cheyixiao.car.model.CarLocalRepositoryImpl
import com.autoforce.cheyixiao.car.model.CarRepository
import com.autoforce.cheyixiao.common.data.remote.bean.CarBrandsResult
import com.autoforce.cheyixiao.common.data.remote.bean.CarSeriesResult
import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult
import com.autoforce.cheyixiao.common.utils.*
import com.autoforce.cheyixiao.mvp.BasePresenter
import io.reactivex.Flowable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 *  Created by xialihao on 2018/11/19.
 */

class SearchChildPresenter(view: SearchChildContract.View) : BasePresenter<SearchChildContract.View>(view),
    SearchChildContract.Presenter {

    private val mLocalRepository: CarLocalRepository = CarLocalRepositoryImpl()

    private var allPage = PAGE_START
    private var myPage = PAGE_START
    private var receivePage = PAGE_START
    private var publishPage = PAGE_START

    // 品牌相关
    private val mBrands = mutableListOf<CarBrandsResult.ResultsBean>()
    private var mBrandArray: Array<String?> = arrayOf()

    // 车系相关
    private val mSeriesList = mutableListOf<CarSeriesResult.ResultBean>()

    companion object {
        const val PAGE_SIZE = 20
        const val PAGE_START = 1
    }

    override fun loadAllData(isLoadMore: Boolean) {

        if (isLoadMore) {
            allPage++
        } else {
            allPage = PAGE_START
        }

        val brandId = getBrandId()
        val seriesId = if (mSeriesList.isEmpty()) {
            null
        } else {
            mSeriesList[mRootView.get()?.getSeriesPos()!!].id
        }
        addDispose(
            CarRepository.getInstance().getSearchCarList(brandId, seriesId, allPage, PAGE_SIZE)
                .map {
                    if (allPage == PAGE_START && it.results != null && it.results!!.isNotEmpty()) {
                        mLocalRepository.saveAllSearchList(it)
                    }
                    it
                }
                .subscribeWith(object : DefaultDisposableSubscriber<SearchCarListResult>() {
                    override fun success(data: SearchCarListResult?) {
                        mRootView.get()?.onDataGot(data?.results, isLoadMore)
                    }

                    override fun failure(errMsg: String?) {
                        mRootView.get()?.onDataError(isLoadMore)
                    }
                })
        )
    }


    override fun loadMyData(isLoadMore: Boolean) {

        if (isLoadMore) {
            myPage++
        } else {
            myPage = PAGE_START
        }

        addDispose(
            CarRepository.getInstance().getMySearchCarList(myPage, PAGE_SIZE)
                .map {
                    if (myPage == PAGE_START && it.results != null && it.results!!.isNotEmpty()) {
                        mLocalRepository.saveMySearchList(it)
                    }
                    it
                }
                .subscribeWith(object : DefaultDisposableSubscriber<SearchCarListResult>() {
                    override fun success(data: SearchCarListResult?) {
                        mRootView.get()?.onDataGot(data?.results, isLoadMore)
                    }

                    override fun failure(errMsg: String?) {
                        mRootView.get()?.onDataError(isLoadMore)
                    }
                })
        )
    }

    override fun loadReceiveData(isLoadMore: Boolean) {

        if (isLoadMore) {
            receivePage++
        } else {
            receivePage = PAGE_START
        }

        addDispose(
            CarRepository.getInstance().getReceiveCarList(receivePage, PAGE_SIZE)
                .map {
                    if (receivePage == PAGE_START && it.results != null && it.results!!.isNotEmpty()) {
                        mLocalRepository.saveReceiveList(it)
                    }
                    it
                }
                .subscribeWith(object : DefaultDisposableSubscriber<SearchCarListResult>() {
                    override fun success(data: SearchCarListResult?) {
                        mRootView.get()?.onDataGot(data?.results, isLoadMore)
                    }

                    override fun failure(errMsg: String?) {
                        mRootView.get()?.onDataError(isLoadMore)
                    }
                })
        )
    }

    override fun loadPublishData(isLoadMore: Boolean) {

        if (isLoadMore) {
            publishPage++
        } else {
            publishPage = PAGE_START
        }

        addDispose(
            CarRepository.getInstance().getReleaseCarList(publishPage, PAGE_SIZE)
                .map {
                    if (publishPage == PAGE_START && it.results != null && it.results!!.isNotEmpty()) {
                        mLocalRepository.savePublishList(it)
                    }
                    it
                }
                .subscribeWith(object : DefaultDisposableSubscriber<SearchCarListResult>() {
                    override fun success(data: SearchCarListResult?) {
                        mRootView.get()?.onDataGot(data?.results, isLoadMore)
                    }

                    override fun failure(errMsg: String?) {
                        mRootView.get()?.onDataError(isLoadMore)
                    }
                })
        )
    }

    override fun loadCacheData(position: Int) {

        val json =
            when (position) {
                0 -> mLocalRepository.getAllSearchList()
                1 -> mLocalRepository.getMySearchList()
                2 -> mLocalRepository.getPublishList()
                else -> mLocalRepository.getReceiveList()
            }

        if (json.isNotEmpty()) {
            val subscribe = Flowable.just(json)
                .map {
                    GsonProvider.gson().fromJson(json, SearchCarListResult::class.java)
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

    override fun loadCarBrands() {

        if (mBrandArray.isNotEmpty()) {
            mRootView.get()?.onBrandsGot(mBrandArray)
        } else {
            CarRepository.getInstance().carBrands
                .filter {
                    it.results != null
                }
                .flatMapIterable {

                    this.mBrands.clear()
                    this.mBrands.addAll(it.results)

                    this.mBrands
                }
                .map {
                    it.bname
                }
                .toList()
                .subscribe(object : SingleObserver<List<String?>> {
                    override fun onSuccess(t: List<String?>) {
                        mBrandArray = t.toTypedArray()
                        mRootView.get()?.onBrandsGot(mBrandArray)
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

    override fun loadSeriesByBrand() {

        val brandId = getBrandId()

        if (brandId == null) {
            mRootView.get()?.onBrandNotSelected(StringUtils.getString(R.string.please_select_brand_first))
            return
        }

        CarRepository.getInstance()
            .getCarSeriesByBrand(brandId)
            .filter {
                it.result != null
            }
            .flatMapIterable {
                this.mSeriesList.clear()
                this.mSeriesList.add(
                    CarSeriesResult.ResultBean(StringUtils.getString(R.string.all), null)
                )
                this.mSeriesList.addAll(it.result)

                this.mSeriesList
            }
            .map {
                it.name
            }
            .toList()
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(t: List<String>) {
                    mRootView.get()?.onSeriesGot(t.toTypedArray())
                }

                override fun onSubscribe(d: Disposable) {
                    addDispose(d)
                }

                override fun onError(e: Throwable) {
                    ErrorHandler.handleError(e)
                }

            })
    }

    private fun getBrandId(): Int? {
        val brandPos = mRootView.get()?.getBrandPos()
        return if (brandPos != null && brandPos < mBrands.size) {
            mBrands[brandPos].bid
        } else {
            null
        }
    }

}