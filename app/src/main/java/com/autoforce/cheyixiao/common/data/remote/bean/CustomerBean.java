package com.autoforce.cheyixiao.common.data.remote.bean;

import java.util.List;

/**
 * Created by liujialei on 2018/11/22
 */
public class CustomerBean {

    int total;
    List<Result> results;
    int code;

    public class Result {
        String uname;
        int isfocus;
        String car;
        String latime;
        String loc;
        int state;
        String img;
        String saler_id;
        long phone;
        String remark;
        String dealer;
        String saler;
        int user_id;
        List<Attention> attentions;


        public class Attention {

            String img;
            String name;

            @Override
            public String toString() {
                return "Attention{" +
                        "img='" + img + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }


        @Override
        public String toString() {
            return "Result{" +
                    "uname='" + uname + '\'' +
                    ", isfocus=" + isfocus +
                    ", car='" + car + '\'' +
                    ", latime='" + latime + '\'' +
                    ", loc='" + loc + '\'' +
                    ", state=" + state +
                    ", img='" + img + '\'' +
                    ", saler_id='" + saler_id + '\'' +
                    ", phone=" + phone +
                    ", remark='" + remark + '\'' +
                    ", dealer='" + dealer + '\'' +
                    ", saler='" + saler + '\'' +
                    ", user_id=" + user_id +
                    ", attentions=" + attentions +
                    '}';
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getIsfocus() {
            return isfocus;
        }

        public void setIsfocus(int isfocus) {
            this.isfocus = isfocus;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getLatime() {
            return latime;
        }

        public void setLatime(String latime) {
            this.latime = latime;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSaler_id() {
            return saler_id;
        }

        public void setSaler_id(String saler_id) {
            this.saler_id = saler_id;
        }

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDealer() {
            return dealer;
        }

        public void setDealer(String dealer) {
            this.dealer = dealer;
        }

        public String getSaler() {
            return saler;
        }

        public void setSaler(String saler) {
            this.saler = saler;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public List<Attention> getAttentions() {
            return attentions;
        }

        public void setAttentions(List<Attention> attentions) {
            this.attentions = attentions;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


     /*
    {
    "total": 4,
    "results": [
        {
            "uname": null,
            "isfocus": 1,
            "car": "",
            "latime": "2018-11-22 14:14",
            "loc": "",
            "attentions": [
                {
                    "img": "https://cheyixiao.autoforce.net/static/series/car_33310.png",
                    "name": "宝马X5"
                }
            ],
            "state": 1,
            "img": "",
            "saler_id": "52615",
            "phone": null,
            "remark": null,
            "dealer": "",
            "saler": "六六六",
            "user_id": 5056705
        }
    ],
    "code": 200
}
     */


}
