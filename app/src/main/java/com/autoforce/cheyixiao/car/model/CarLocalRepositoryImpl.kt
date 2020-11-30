package com.autoforce.cheyixiao.car.model

import com.autoforce.cheyixiao.common.data.local.utils.SpUtils
import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult
import com.autoforce.cheyixiao.common.utils.GsonProvider

/**
 *  Created by xialihao on 2018/12/3.
 */
class CarLocalRepositoryImpl : CarLocalRepository {

    companion object {
        const val CACHE_SEARCH_FILE = "cache_search_file"
        const val CACHE_ALL_SEARCH = "all_search"
        const val CACHE_MY_SEARCH = "my_search"
        const val CACHE_RECEIVE = "receive"
        const val CACHE_PUBLISH = "publish"
    }

    override fun saveAllSearchList(result: SearchCarListResult) {
        SpUtils.getInstance(CACHE_SEARCH_FILE)
            .put(CACHE_ALL_SEARCH, GsonProvider.gson().toJson(result))
    }

    override fun getAllSearchList(): String {
        return SpUtils.getInstance(CACHE_SEARCH_FILE).getString(CACHE_ALL_SEARCH)
    }

    override fun saveMySearchList(result: SearchCarListResult) {
        SpUtils.getInstance(CACHE_SEARCH_FILE)
            .put(CACHE_MY_SEARCH, GsonProvider.gson().toJson(result))
    }

    override fun getMySearchList(): String {
        return SpUtils.getInstance(CACHE_SEARCH_FILE)
            .getString(CACHE_MY_SEARCH)
    }

    override fun saveReceiveList(result: SearchCarListResult) {

        SpUtils.getInstance(CACHE_SEARCH_FILE)
            .put(CACHE_RECEIVE, GsonProvider.gson().toJson(result))
    }

    override fun getReceiveList(): String {
        return SpUtils.getInstance(CACHE_SEARCH_FILE).getString(CACHE_RECEIVE)
    }

    override fun savePublishList(result: SearchCarListResult) {

        SpUtils.getInstance(CACHE_SEARCH_FILE)
            .put(CACHE_PUBLISH, GsonProvider.gson().toJson(result))
    }

    override fun getPublishList(): String {
        return SpUtils.getInstance(CACHE_SEARCH_FILE).getString(CACHE_PUBLISH)
    }


}