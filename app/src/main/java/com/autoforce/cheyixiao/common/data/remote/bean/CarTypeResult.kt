package com.autoforce.cheyixiao.common.data.remote.bean

/**
 * Created by xialihao on 2018/11/24.
 */
class CarTypeResult : SimpleResult() {

    var results: List<ResultsBean>? = null

    /**
     * name : 国产-现车
     * id : 1
     */
    class ResultsBean(var name: String? = null, var id: Int? = 0) {

    }
}
