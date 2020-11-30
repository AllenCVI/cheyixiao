package com.autoforce.cheyixiao.car.source

import com.autoforce.cheyixiao.common.data.remote.bean.CarSourceListResult
import com.autoforce.cheyixiao.mvp.IPresenter
import com.autoforce.cheyixiao.mvp.IView

/**
 *  Created by xialihao on 2018/11/16.
 */
interface SourceChildContract {

    interface Presenter : IPresenter {
        /**
         * 获取列表数据
         */
        fun loadData(isLoadMore: Boolean = false)

        /**
         * 获取车系筛选数据
         */
        fun loadCarTypeData()

        /**
         * 获取车辆所在地筛选数据
         */
        fun loadCarArea()

        /**
         * 加载本地缓存的数据
         */
        fun loadCacheData()

        fun cachePrice(minPrice: Int?, maxPrice: Int?)
        fun cachePriceByPos(position: Int)
    }

    interface View : IView<Presenter> {
        fun onDataGot(results: List<CarSourceListResult.ResultsBean>?, isLoadMore: Boolean)
        fun onCarTypesGot(showList: Array<String?>)
        fun getCarTypePos(): Int
        fun getSearchWords(): String?
        fun onAreaInfoGot(t: Array<String?>)
        fun getAreaPos(): Int
        fun onDataError(isLoadMore: Boolean)
        fun onCacheDataLoad(results: List<CarSourceListResult.ResultsBean>?)
    }
}