package com.autoforce.cheyixiao.customer;

/**
 * Created by liujialei on 2018/11/27
 */
class CustomerStateBean {

    private String stateText;
    private int stateNum;

    public CustomerStateBean() {
    }

    public CustomerStateBean(String stateText, int stateNum) {
        this.stateText = stateText;
        this.stateNum = stateNum;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
    }
}
