package com.autoforce.cheyixiao.common.data.remote.bean;

public class BlankCardInfo extends SimpleResult{


    /**
     * result : {"bank_name":"?????????????????????????????????","city":"??????????????? ?????????","type":"????????????","card_owner":"?????????","card_num":"6214830110867838","status":0}
     * status : 2
     */

    private ResultBean result;
    private int status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * bank_name : ?????????????????????????????????
         * city : ??????????????? ?????????
         * type : ????????????
         * card_owner : ?????????
         * card_num : 6214830110867838
         * status : 0
         */

        private String bank_name;
        private String city;
        private String type;
        private String card_owner;
        private String card_num;
        private int status;

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCard_owner() {
            return card_owner;
        }

        public void setCard_owner(String card_owner) {
            this.card_owner = card_owner;
        }

        public String getCard_num() {
            return card_num;
        }

        public void setCard_num(String card_num) {
            this.card_num = card_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
