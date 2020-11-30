package com.autoforce.cheyixiao.car.search

import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult
import com.autoforce.cheyixiao.mvp.IPresenter
import com.autoforce.cheyixiao.mvp.IView

/**
 *  Created by xialihao on 2018/11/19.
 */

interface SearchChildContract {

    interface Presenter : IPresenter {
        fun loadAllData(isLoadMore: Boolean = false)
        fun loadMyData(isLoadMore: Boolean = false)
        fun loadReceiveData(isLoadMore: Boolean = false)
        fun loadPublishData(isLoadMore: Boolean = false)
        fun loadCacheData(position: Int)
        fun loadCarBrands()
        fun loadSeriesByBrand()
    }

    interface View : IView<Presenter> {
        fun onDataGot(
            results: List<SearchCarListResult.ResultsBean>?,
            isLoadMore: Boolean
        )

        fun onDataError(isLoadMore: Boolean)
        fun onCacheDataLoad(results: List<SearchCarListResult.ResultsBean>?)
        fun onBrandsGot(brandArray: Array<String?>)
        fun getBrandPos(): Int
        fun onBrandNotSelected(msg: String)
        fun onSeriesGot(t: Array<String?>)
        fun getSeriesPos(): Int
    }
}