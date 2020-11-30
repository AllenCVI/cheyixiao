package com.autoforce.cheyixiao.common.data.remote.bean

import com.autoforce.cheyixiao.car.base.refresh.StatusTypeInterfaceImpl
import com.google.gson.annotations.SerializedName

/**
 * Created by xialihao on 2018/11/22.
 */
class CarSourceListResult : SimpleResult() {


    /**
     * count : 2631
     * results : [{"procedures":"手续齐全，15天内发","brand_name":"别克","pro_id":110000,"guide_price":"13.39","car_format_id":1,"spec":"英朗 18款 18T 自动精英","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"8.54","expire_time_str":"","series_name":"英朗","updated_at":"2018-11-19 18:36:00","expire_time":"2018-11-26","created_at":"2018-11-19","inner_color":"白","sell_area":"全国","id":65024,"car_location":"北京"},{"procedures":"手续齐全，7天内发","brand_name":"别克","pro_id":110000,"guide_price":"11.99","car_format_id":1,"spec":"英朗 18款 15T 双离合进取","car_format":"国产-现车","is_test":1,"outer_color":"灰","is_onsell":1,"quote":"7.19","expire_time_str":"","series_name":"英朗","updated_at":"2018-11-19 18:36:00","expire_time":"2018-11-24","created_at":"2018-11-19","inner_color":"白金黑","sell_area":"全国","id":64574,"car_location":"北京"},{"procedures":"手续齐全，15天内发","brand_name":"别克","pro_id":110000,"guide_price":"13.39","car_format_id":1,"spec":"英朗 18款 18T 自动精英","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"8.54","expire_time_str":"","series_name":"英朗","updated_at":"2018-11-19 18:36:00","expire_time":"2018-11-25","created_at":"2018-11-19","inner_color":"白","sell_area":"全国","id":65054,"car_location":"北京"},{"procedures":"手续齐全，随车发","brand_name":"本田","pro_id":120000,"guide_price":"8.98","car_format_id":1,"spec":"锋范 18款 1.5L CVT舒适","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"7.88","expire_time_str":"","series_name":"锋范","updated_at":"2018-11-19 18:34:00","expire_time":"2018-11-25","created_at":"2018-11-19","inner_color":"银","sell_area":"全国","id":64869,"car_location":"天津"},{"procedures":"手续齐全，15天内发","brand_name":"本田","pro_id":120000,"guide_price":"8.98","car_format_id":1,"spec":"锋范 18款 1.5L CVT舒适","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"7.88","expire_time_str":"","series_name":"锋范","updated_at":"2018-11-19 18:34:00","expire_time":"2018-11-24","created_at":"2018-11-19","inner_color":"白","sell_area":"全国","id":64709,"car_location":"天津"},{"procedures":"手续齐全，15天内发","brand_name":"丰田","pro_id":120000,"guide_price":"8.78","car_format_id":1,"spec":"致炫 16款 改款 1.5E CVT魅动","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"6.68","expire_time_str":"","series_name":"YARiS L 致炫","updated_at":"2018-11-19 18:34:00","expire_time":"2018-11-25","created_at":"2018-11-19","inner_color":"天际白","sell_area":"全国","id":64834,"car_location":"天津"},{"procedures":"手续齐全，随车发","brand_name":"本田","pro_id":120000,"guide_price":"16.28","car_format_id":1,"spec":"本田XR-V 17款 1.8L VTi CVT豪华","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"14.88","expire_time_str":"","series_name":"本田XR-V","updated_at":"2018-11-19 18:34:00","expire_time":"2018-11-26","created_at":"2018-11-19","inner_color":"塔夫绸白","sell_area":"全国","id":64881,"car_location":"天津"},{"procedures":"手续齐全，30天内发","brand_name":"本田","pro_id":120000,"guide_price":"14.98","car_format_id":1,"spec":"本田XR-V 17款 1.8L EXi CVT舒适","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"13.58","expire_time_str":"","series_name":"本田XR-V","updated_at":"2018-11-19 18:34:00","expire_time":"2018-11-28","created_at":"2018-11-19","inner_color":"塔夫绸白","sell_area":"全国","id":64897,"car_location":"天津"},{"procedures":"手续齐全，3天内发","brand_name":"日产","pro_id":110000,"guide_price":"15.78","car_format_id":1,"spec":"逍客 17款 2.0L CVT智享","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"13.08","expire_time_str":"","series_name":"逍客","updated_at":"2018-11-19 18:33:00","expire_time":"2018-11-27","created_at":"2018-11-19","inner_color":"珠光白","sell_area":"全国","id":64950,"car_location":"北京"},{"procedures":"手续齐全，随车发","brand_name":"福特","pro_id":110000,"guide_price":"12.68","car_format_id":1,"spec":"福克斯 18款 两厢 1.6L 自动舒适 智行","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"9.08","expire_time_str":"","series_name":"福克斯","updated_at":"2018-11-19 18:33:00","expire_time":"2018-11-24","created_at":"2018-11-19","inner_color":"白","sell_area":"全国","id":64493,"car_location":"北京"},{"procedures":"手续齐全，随车发","brand_name":"别克","pro_id":110000,"guide_price":"13.39","car_format_id":1,"spec":"英朗 18款 18T 自动精英","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"8.74","expire_time_str":"","series_name":"英朗","updated_at":"2018-11-19 18:33:00","expire_time":"2018-11-27","created_at":"2018-11-19","inner_color":"白","sell_area":"全国","id":64999,"car_location":"北京"},{"procedures":"手续齐全，30天内发","brand_name":"大众","pro_id":110000,"guide_price":"17.64","car_format_id":1,"spec":"高尔夫 18款 280 自动R-Line 选装包","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"14.04","expire_time_str":"","series_name":"高尔夫","updated_at":"2018-11-19 18:32:00","expire_time":"2018-11-29","created_at":"2018-11-19","inner_color":"白蓝","sell_area":"全国","id":64407,"car_location":"北京"},{"procedures":"手续齐全，15天内发","brand_name":"日产","pro_id":110000,"guide_price":"15.38","car_format_id":1,"spec":"逍客 17款 2.0L CVT精英","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"12.28","expire_time_str":"","series_name":"逍客","updated_at":"2018-11-19 18:30:00","expire_time":"2018-11-24","created_at":"2018-11-19","inner_color":"炫雅红","sell_area":"全国","id":64812,"car_location":"北京"},{"procedures":"手续齐全，15天内发","brand_name":"本田","pro_id":130000,"guide_price":"29.28","car_format_id":1,"spec":"冠道 17款 370T 四驱尊享","car_format":"国产-现车","is_test":1,"outer_color":"全","is_onsell":1,"quote":"27.48","expire_time_str":"","series_name":"冠道","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-29","created_at":"2018-11-19","inner_color":"全","sell_area":"全国","id":64690,"car_location":"河北"},{"procedures":"手续齐全，7天内发","brand_name":"日产","pro_id":110000,"guide_price":"23.98","car_format_id":1,"spec":"奇骏 17款 2.5L CVT豪华 4WD","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"20.33","expire_time_str":"","series_name":"奇骏","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-25","created_at":"2018-11-19","inner_color":"珠光白","sell_area":"全国","id":64839,"car_location":"北京"},{"procedures":"手续齐全，3天内发","brand_name":"大众","pro_id":110000,"guide_price":"23.78","car_format_id":1,"spec":"途观 17款 300 自动两驱丝绸之路舒适","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"18.18","expire_time_str":"","series_name":"途观","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-28","created_at":"2018-11-19","inner_color":"极光白","sell_area":"全国","id":64748,"car_location":"北京"},{"procedures":"手续齐全，3天内发","brand_name":"本田","pro_id":130000,"guide_price":"29.28","car_format_id":1,"spec":"冠道 17款 370T 四驱尊享","car_format":"国产-现车","is_test":1,"outer_color":"全","is_onsell":1,"quote":"27.48","expire_time_str":"","series_name":"冠道","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-25","created_at":"2018-11-19","inner_color":"全","sell_area":"全国","id":64382,"car_location":"河北"},{"procedures":"手续齐全，30天内发","brand_name":"本田","pro_id":110000,"guide_price":"8.98","car_format_id":1,"spec":"哥瑞 16款 1.5L CVT经典","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"7.58","expire_time_str":"","series_name":"哥瑞","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-24","created_at":"2018-11-19","inner_color":"银，白","sell_area":"全国","id":64432,"car_location":"北京"},{"procedures":"手续齐全，3天内发","brand_name":"本田","pro_id":130000,"guide_price":"29.28","car_format_id":1,"spec":"冠道 17款 370T 四驱尊享","car_format":"国产-现车","is_test":1,"outer_color":"全","is_onsell":1,"quote":"27.48","expire_time_str":"","series_name":"冠道","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-28","created_at":"2018-11-19","inner_color":"全","sell_area":"全国","id":64738,"car_location":"河北"},{"procedures":"手续齐全，随车发","brand_name":"大众","pro_id":110000,"guide_price":"15.49","car_format_id":1,"spec":"高尔夫 18款 230 自动舒适","car_format":"国产-现车","is_test":1,"outer_color":"黑","is_onsell":1,"quote":"12.79","expire_time_str":"","series_name":"高尔夫","updated_at":"2018-11-19 18:27:00","expire_time":"2018-11-26","created_at":"2018-11-19","inner_color":"白 金 红","sell_area":"全国","id":64978,"car_location":"北京"}]
     */

    var count: Int = 0
    var results: List<ResultsBean>? = null

    class ResultsBean : StatusTypeInterfaceImpl() {
        /**
         * procedures : 手续齐全，15天内发
         * brand_name : 别克
         * pro_id : 110000
         * guide_price : 13.39
         * car_format_id : 1
         * spec : 英朗 18款 18T 自动精英
         * car_format : 国产-现车
         * is_test : 1
         * outer_color : 黑
         * is_onsell : 1
         * quote : 8.54   报价
         * expire_time_str :
         * series_name : 英朗
         * updated_at : 2018-11-19 18:36:00
         * expire_time : 2018-11-26
         * created_at : 2018-11-19
         * inner_color : 白
         * sell_area : 全国
         * id : 65024
         * car_location : 北京
         */

        var procedures: String? = null
        @SerializedName("brand_name")
        var brandName: String? = null
        @SerializedName("pro_id")
        var proId: Int = 0
        @SerializedName("guide_price")
        var guidePrice: String? = null
        @SerializedName("car_format_id")
        var carFormatId: Int = 0
        var spec: String? = null
        @SerializedName("car_format")
        var carFormat: String? = null
        @SerializedName("is_test")
        var isTest: Int = 0
        @SerializedName("outer_color")
        var outerColor: String? = null
        @SerializedName("is_onsell")
        var isOnsell: Int = 0
        var quote: String? = null
        @SerializedName("expire_time_str")
        var expireTimeStr: String? = null
        var series_name: String? = null
        @SerializedName("update_at")
        var updatedAt: String? = null
        @SerializedName("expire_time")
        var expireTime: String? = null
        @SerializedName("created_at")
        var createdAt: String? = null
        @SerializedName("inner_color")
        var innerColor: String? = null
        @SerializedName("sell_area")
        var sellArea: String? = null
        var id: Int = 0
        @SerializedName("car_location")
        var carLocation: String? = null

    }
}
