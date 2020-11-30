package com.autoforce.cheyixiao.common.data.remote.bean

import com.autoforce.cheyixiao.car.base.refresh.StatusTypeInterfaceImpl
import com.google.gson.annotations.SerializedName

/**
 * Created by xialihao on 2018/11/22.
 */
class SearchCarListResult : SimpleResult() {


    /**
     * count : 9
     * results : [{"find_id":162,"order_fee":4000,"status":1,"color":"白/灰","created_at":"2018-11-22 10:36:11","expected_price":"42.68","spec":"Stelvio 2017款 2.0T 200HP 豪华版","car_format":"美规","quote_num":0,"plate_city":"北京"},{"find_id":161,"order_fee":4000,"status":1,"color":"白/灰","created_at":"2018-11-22 10:17:07","expected_price":"3.12","spec":"奥迪A8 2016款 A8L 45 TFSI quattro舒适型","car_format":"美规","quote_num":0,"plate_city":"北京"},{"find_id":160,"order_fee":4000,"status":1,"color":"白/灰","created_at":"2018-11-22 10:14:42","expected_price":"200","spec":"V8 Vantage 2016款 4.7L Coupe Edition 5","car_format":"美规","quote_num":0,"plate_city":"北京"},{"find_id":155,"order_fee":45888,"status":1,"color":"哈哈","created_at":"2018-11-20 19:52:52","expected_price":"4555555","spec":"Giulia 2017款 2.0T 200HP 精英版","car_format":"美规","quote_num":0,"plate_city":"天津"},{"find_id":154,"order_fee":14444,"status":1,"color":"你","created_at":"2018-11-20 19:52:41","expected_price":"145411","spec":"Giulia 2017款 2.0T 200HP 精英版","car_format":"美规","quote_num":0,"plate_city":"山西"},{"find_id":153,"order_fee":4000,"status":1,"color":"1","created_at":"2018-11-20 15:50:36","expected_price":"12345","spec":"Stelvio 2017款 2.0T 200HP 豪华版","car_format":"中规/国产","quote_num":0,"plate_city":"北京"},{"find_id":152,"order_fee":4000,"status":1,"color":"我","created_at":"2018-11-20 15:44:42","expected_price":"4268000","spec":"Stelvio 2017款 2.0T 200HP 豪华版","car_format":"中规/国产","quote_num":0,"plate_city":"北京"},{"find_id":151,"order_fee":155555,"status":1,"color":"吧","created_at":"2018-11-20 15:17:39","expected_price":"12555555","spec":"V8 Vantage 2016款 4.7L Roadster","car_format":"加规","quote_num":1,"plate_city":"河北"},{"find_id":150,"order_fee":10000,"status":1,"color":"的","created_at":"2018-11-20 14:48:09","expected_price":"1333333","spec":"Stelvio 2017款 2.0T 200HP 豪华版","car_format":"中东","quote_num":0,"plate_city":"山西"}]
     * code : 200
     */

    var count: Int = 0
    var results: List<ResultsBean>? = null

    class ResultsBean : StatusTypeInterfaceImpl(){
        /**
         * find_id : 162
         * order_fee : 4000
         * status : 1
         * color : 白/灰
         * created_at : 2018-11-22 10:36:11
         * expected_price : 42.68
         * spec : Stelvio 2017款 2.0T 200HP 豪华版
         * car_format : 美规
         * quote_num : 0
         * plate_city : 北京
         */
        @SerializedName("find_id")
        var findId: Int = 0
        @SerializedName("order_fee")
        var orderFee: Int = 0
        var status: Int = 0
        var color: String? = null
        @SerializedName("created_at")
        var createdAt: String? = null
        @SerializedName("expected_price")
        var expectedPrice: String? = null
        var spec: String? = null
        @SerializedName("car_format")
        var carFormat: String? = null
        @SerializedName("quote_num")
        var quoteNum: Int = 0
        @SerializedName("plate_city")
        var plateCity: String? = null
    }
}
