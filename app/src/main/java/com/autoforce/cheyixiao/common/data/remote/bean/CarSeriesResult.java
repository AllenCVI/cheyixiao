package com.autoforce.cheyixiao.common.data.remote.bean;

import java.util.List;

/**
 * Created by xialihao on 2018/11/28.
 */
public class CarSeriesResult extends SimpleResult {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 宝马5系
         * id : 65
         */

        private String name;
        private Integer id;

        public ResultBean() {
        }

        public ResultBean(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
