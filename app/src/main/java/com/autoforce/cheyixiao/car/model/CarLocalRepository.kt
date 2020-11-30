package com.autoforce.cheyixiao.car.model

import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult

/**
 *  Created by xialihao on 2018/12/3.
 */
interface CarLocalRepository {

    /**
     * 缓存全部寻车列表
     */
    fun saveAllSearchList(result: SearchCarListResult)

    /**
     * 获取本地缓存的全部寻车列表
     */
    fun getAllSearchList(): String

    /**
     * 缓存我的寻车列表
     */
    fun saveMySearchList(result: SearchCarListResult)

    /**
     * 获取本地缓存的我的寻车列表
     */
    fun getMySearchList(): String

    /**
     * 缓存我收到的寻车列表
     */
    fun saveReceiveList(result: SearchCarListResult)

    /**
     * 获取本地缓存的我的收到的寻车列表
     */
    fun getReceiveList(): String


    /**
     * 缓存我发布的寻车列表
     */
    fun savePublishList(result: SearchCarListResult)

    /**
     * 获取我发布的寻车列表
     */
    fun getPublishList(): String
}