package com.autoforce.cheyixiao.home;

import com.autoforce.cheyixiao.base.BaseConstant;

/**
 * Created by liusilong on 2018/12/10.
 * version:1.0
 * Describe: Home 模块的常量
 * 属于图中所圈住的模块下，均需要这样配置https://cheyixiao.autoforce.net/static/app/saler
 * <p>
 */
public class HomeConstant {

    private static final String BASE_URL = BaseConstant.BASEURL;
    private static final String BASE_URL_35_8090 = "http://192.168.10.35:8090";
    private static final String BASE_URL_69_8081 = "http://192.168.10.69:8081";
    private static final String BASE_URL_25_8080 = "http://192.168.10.25:8080";
    public static final String CAR_DOWNLOAD_RECORD = "car_download_record";
    public static final String CAR_UPDATE_RECORD = "car_update_record";
    public static final String CAR_DOWNLOAD_LIST_RECORD = "car_download_list_record";

    public static final String UPDATE = "update";
    public static final String DOWNLOAD = "download";

    //检查更新
//    public static final String UPDATE_TEST_URL = "http://192.168.3.233:8080/server.json";
    public static final String UPDATE_TEST_URL = "https://cdn.autoforce.net/ixiao/version/version.json";

    // 保险
    public static final String INSURANCE = "保险";
    // 购车
    public static final String GIVE_ORDER = "购车";
    // 物流
    public static final String LOGISTICS = "物流";
    // 金融
    public static final String FINANCIAL = "金融";

    // 保险
    public static final String INSURANCE_URL = BASE_URL + "/insurance.html#/insuranceMobile";
    // 购车
    public static final String GIVE_ORDER_URL = BASE_URL_35_8090 + "/giveOrder.html#/giveordermobile";
    // 物流
    public static final String LOGISTICS_RUL = BASE_URL + "/saler/logistics.html#/logistics";
    // 金融
    public static final String FINANCIAL_URL = BASE_URL + "/saler/financialManager.html#/financialManager";

    // 首页品牌点击触发跳转的 url
    public static final String BRAND_URL = BASE_URL_35_8090 + "/series.html#/layout/series";

    // 热销车
    public static final String HOT_CAR_URL = BASE_URL_35_8090 + "/car.html#/layout/carbefore";

    // WebView 和 JS 交互 key
    public static final String JS_BRIDGE_KEY = "home_module";

}
