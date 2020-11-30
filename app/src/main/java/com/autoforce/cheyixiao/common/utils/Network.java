package com.autoforce.cheyixiao.common.utils;

/**
 * Created by xialihao on 2018/11/15.
 * 配置常量
 *
 * 打包注意点：
 *    1.
 */
public interface Network {


    String BASE_URL = "https://cheyixiao.autoforce.net/";

    String PAGE_BASE_URL = "https://cdn.autoforce.net/ixiao/app/cyx-h5/v1.0/saler/";
    // for pay test
//    String BASE_URL = "http://testapi.autoforce.net:3001/";

//        String BASE_210 = "http://192.168.10.25:8080/saler/";
//    String BASE_210 = BASE_URL + STATIC_APP;
    String BASE_210 = PAGE_BASE_URL;
//        String BASE_208 = "http://192.168.10.69:8081/";
    String BASE_208 = PAGE_BASE_URL;
//    String BASE_208 = BASE_210;
//    String BASE_208 = BASE_URL + STATIC_APP;
//    String BASE_208 = "http://192.168.10.69:8083/";

    //发布车源页面
    String ADD_SOURCE_PAGE_URL = BASE_208 + "addSource.html#/addSource";

    //发布寻车页面
    String ADD_SEARCH_PAGE_URL = BASE_210 + "addSearch.html#/addSearchMobile";
//    String ADD_SEARCH_PAGE_URL = BASE_210 + "saler/addSearch.html#/addSearchMobile";

    // 寻车详情
    String SEARCH_DETAIL_PAGE_URL = BASE_210 + "searchDetail.html#/searchDetail";
//    String SEARCH_DETAIL_PAGE_URL = BASE_210 + "saler/searchDetail.html#/searchDetail";

    // 车源详情
    String SOURCE_DETAIL_PAGE_URL = BASE_208 + "sourceDetail.html#/sourceDetail";

    // 注册协议
    String AGREEMENT_INFO_PAGE_URL = BASE_210 + "agreementInfo.html#/agreementInfo";
//    String AGREEMENT_INFO_PAGE_URL = BASE_210 + "saler/agreementInfo.html#/agreementInfo";+

    // 我的车源
    String MY_SOURCE_PAGE_URL = BASE_208 + "mySource.html#/mySource";

    // 信息认证页
//    String INFO_CERTIFICATE_PAGE_URL = BASE_210 + "saler/mobileIndex.html#/userMobile/identify";
    String INFO_CERTIFICATE_PAGE_URL = BASE_210 + "mobileIndex.html#/userMobile/identify";
}
