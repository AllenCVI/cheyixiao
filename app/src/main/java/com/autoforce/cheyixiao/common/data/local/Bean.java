package com.autoforce.cheyixiao.common.data.local;

import java.util.List;

public class Bean {
    private String method;//相机或者相册
    private String type;//点击的控件
    private String identifier;//原生无用
    private ParamBean param;
    private List<ParamBean> parame;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ParamBean getParam() {
        return param;
    }

    public String getIdentifier() {
        return identifier;
    }


    // for dial phone
    public static class ParamBean {

        private String phoneNumber;

        //for pay
        private String url;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUrl() {
            return url;
        }
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<ParamBean> getParame() {
        return parame;
    }
}
