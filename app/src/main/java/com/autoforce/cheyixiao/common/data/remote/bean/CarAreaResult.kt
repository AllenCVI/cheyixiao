package com.autoforce.cheyixiao.common.data.remote.bean

/**
 * Created by xialihao on 2018/11/24.
 */
class CarAreaResult : SimpleResult() {


    var result: ResultBean? = null

    class ResultBean {
        var areas: List<AreasBean>? = null

        class AreasBean(var province: ProvinceBean?) {
            /**
             * province : {"name":"北京","id":110000,"citys":[{"name":"北京","id":110100}]}
             */

            class ProvinceBean( var name: String?, var id: String?) {
                /**
                 * name : 北京
                 * id : 110000
                 * citys : [{"name":"北京","id":110100}]
                 */
                var citys: List<CitysBean>? = null

                class CitysBean {
                    /**
                     * name : 北京
                     * id : 110100
                     */

                    var name: String? = null
                    var id: String? = null
                }
            }
        }
    }
}
