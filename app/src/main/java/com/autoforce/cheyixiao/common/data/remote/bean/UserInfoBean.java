package com.autoforce.cheyixiao.common.data.remote.bean;

public class UserInfoBean extends SimpleResult {


    /**
     * results : {"process_cash":"0.00","name":null,"can_cash":"0.00","id":52614,"is_pass":0,"source_process_cash":"0.00","source_can_cash":"0.00","phone":18513294059,"avatar":null}
     */

    private ResultsBean results;

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * process_cash : 0.00
         * name : null
         * can_cash : 0.00
         * id : 52614
         * is_pass : 0
         * source_process_cash : 0.00
         * source_can_cash : 0.00
         * phone : 18513294059
         * avatar : null
         */

        private String process_cash;
        private String name;
        private String can_cash;
        private int id;
        private int is_pass;
        private String source_process_cash;
        private String source_can_cash;
        private long phone;
        private String avatar;


        public boolean getIsPass(){
            return getIs_pass() != 0;//0 代表未通过审核
        }
        public String getProcess_cash() {
            return process_cash;
        }

        public void setProcess_cash(String process_cash) {
            this.process_cash = process_cash;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCan_cash() {
            return can_cash;
        }

        public void setCan_cash(String can_cash) {
            this.can_cash = can_cash;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(int is_pass) {
            this.is_pass = is_pass;
        }

        public String getSource_process_cash() {
            return source_process_cash;
        }

        public void setSource_process_cash(String source_process_cash) {
            this.source_process_cash = source_process_cash;
        }

        public String getSource_can_cash() {
            return source_can_cash;
        }

        public void setSource_can_cash(String source_can_cash) {
            this.source_can_cash = source_can_cash;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
