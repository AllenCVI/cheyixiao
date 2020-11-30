package com.autoforce.cheyixiao.customer.customersecondh5;

import com.autoforce.cheyixiao.base.BaseConstant;
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.Bean;
import com.autoforce.cheyixiao.customer.mycommon.CustomerConstant;

import java.util.HashMap;

/**
 * Created by liujialei on 2018/11/29
 */
public class CustomerSecondH5Activity extends BaseX5WebViewActivity {



    @Override
    protected void addParams(HashMap<String, String> map) {
        String user_id = getIntent().getStringExtra(CustomerConstant.CUSTOMER_USER_ID);

        if (user_id == null) {
            user_id = "";
        }
        map.put(BaseConstant.USERID, user_id);
    }

    @Override
    protected void setOther() {


    }

    @Override
    protected String getUrl() {
//        return "http://192.168.10.25:8080/saler/customerDetail.html#/customerDetail";
      return CustomerConstant.CUSTOMERURL;
    }





    @Override
    protected void childUse(Bean bean) {
        super.childUse(bean);

        if(CustomerConstant.EVENT_SUPPLEMENT.equals(bean.getMethod())){

            UMengStatistics.statisticEventNumber(CustomerConstant.EVENT_SUPPLEMENT);

        }else if(CustomerConstant.EVENT_CAR_DETAIL.equals(bean.getMethod())) {

            UMengStatistics.statisticEventNumber(CustomerConstant.EVENT_CAR_DETAIL);

        }

    }
}
