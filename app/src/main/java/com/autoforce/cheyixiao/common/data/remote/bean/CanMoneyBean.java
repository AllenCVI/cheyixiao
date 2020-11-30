package com.autoforce.cheyixiao.common.data.remote.bean;

import com.autoforce.cheyixiao.common.data.remote.bean.SimpleResult;

import java.util.List;

public class CanMoneyBean extends SimpleResult {


    /**
     * code : 200
     * message : ok
     * results : [{"id":"","pay_type":"","car_name":"","pay_at":"","buyer":"","phone":"","pay_money":"","cash_money":"","serv_fee":"","serv_fee_reduced":""}]
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id :
         * pay_type :
         * car_name :
         * pay_at :
         * buyer :
         * phone :
         * pay_money :
         * cash_money :
         * serv_fee :
         * serv_fee_reduced :
         */

        private String id;
        private String pay_type;//支付类型
        private String car_name;//支付车型
        private String pay_at;//支付时间
        private String buyer;//付款人
        private String phone;
        private String pay_money;//用户支付金额
        private String cash_money;//可提现金额
        private String serv_fee;//平台服务费
        private String serv_fee_reduced;//服务费减免
        private String cash_at;//提现时间
        private String reach_at;//到账时间
        private String reason;//失败原因



        private boolean click_enable = true;//本地字段 提现按钮点击去重
        public boolean isClick_enable() {
            return click_enable;
        }

        public void setClick_enable(boolean click_enable) {
            this.click_enable = click_enable;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getPay_at() {
            return pay_at;
        }

        public void setPay_at(String pay_at) {
            this.pay_at = pay_at;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getCash_money() {
            return cash_money;
        }

        public void setCash_money(String cash_money) {
            this.cash_money = cash_money;
        }

        public String getServ_fee() {
            return serv_fee;
        }

        public void setServ_fee(String serv_fee) {
            this.serv_fee = serv_fee;
        }

        public String getServ_fee_reduced() {
            return serv_fee_reduced;
        }

        public void setServ_fee_reduced(String serv_fee_reduced) {
            this.serv_fee_reduced = serv_fee_reduced;
        }

        public String getCash_at() {
            return cash_at;
        }

        public void setCash_at(String cash_at) {
            this.cash_at = cash_at;
        }

        public String getReach_at() {
            return reach_at;
        }

        public void setReach_at(String reach_at) {
            this.reach_at = reach_at;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
