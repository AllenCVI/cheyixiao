package com.autoforce.cheyixiao.common.data.remote.bean;

import java.util.List;

/**
 * Created by liujialei on 2018/11/23
 */
public class CustomerBrandBean {

    int code;
    List<Result> result;

    public class Result{
        /*
        	"bid": 33,
			"bname_pinyin": "aodi",
			"bname": "奥迪"
         */


            int bid;
            String bname_pinyin;
            String bname;

            public int getBid() {
                return bid;
            }

            public void setBid(int bid) {
                this.bid = bid;
            }

            public String getBname_pinyin() {
                return bname_pinyin;
            }

            public void setBname_pinyin(String bname_pinyin) {
                this.bname_pinyin = bname_pinyin;
            }

            public String getBname() {
                return bname;
            }

            public void setBname(String bname) {
                this.bname = bname;
            }


    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}


/*
{
	"code": 200,
	"result": [{
			"a": [{
					"bid": 33,
					"bname_pinyin": "aodi",
					"bname": "奥迪"
				},
				{
					"bid": 117,
					"bname_pinyin": "ac schnitzer",
					"bname": "AC Schnitzer"
				},
				{
					"bid": 35,
					"bname_pinyin": "asidun·mading",
					"bname": "阿斯顿·马丁"
				},
				{
					"bid": 34,
					"bname_pinyin": "aerfaluomiou",
					"bname": "阿尔法罗密欧"
				},
				{
					"bid": 276,
					"bname_pinyin": "alpina",
					"bname": "ALPINA"
				},
				{
					"bid": 272,
					"bname_pinyin": "arcfox",
					"bname": "ARCFOX"
				}
			]
		},
		{
			"b": [{
					"bid": 154,
					"bname_pinyin": "beiqizhizao",
					"bname": "北汽制造"
				},
				{
					"bid": 36,
					"bname_pinyin": "benchi",
					"bname": "奔驰"
				},
				{
					"bid": 14,
					"bname_pinyin": "bentian",
					"bname": "本田"
				},
				{
					"bid": 140,
					"bname_pinyin": "babosi",
					"bname": "巴博斯"
				},
				{
					"bid": 75,
					"bname_pinyin": "biyadi",
					"bname": "比亚迪"
				},
				{
					"bid": 120,
					"bname_pinyin": "baojun",
					"bname": "宝骏"
				},
				{
					"bid": 13,
					"bname_pinyin": "biaozhi",
					"bname": "标致"
				},
				{
					"bid": 40,
					"bname_pinyin": "baoshijie",
					"bname": "保时捷"
				},
				{
					"bid": 173,
					"bname_pinyin": "beiqishenbao",
					"bname": "北汽绅宝"
				},
				{
					"bid": 95,
					"bname_pinyin": "benteng",
					"bname": "奔腾"
				},
				{
					"bid": 15,
					"bname_pinyin": "baoma",
					"bname": "宝马"
				},
				{
					"bid": 38,
					"bname_pinyin": "bieke",
					"bname": "别克"
				},
				{
					"bid": 208,
					"bname_pinyin": "beiqixinnengyuan",
					"bname": "北汽新能源"
				},
				{
					"bid": 143,
					"bname_pinyin": "beiqiweiwang",
					"bname": "北汽威旺"
				},
				{
					"bid": 203,
					"bname_pinyin": "beiqihuansu",
					"bname": "北汽幻速"
				},
				{
					"bid": 231,
					"bname_pinyin": "baowo",
					"bname": "宝沃"
				},
				{
					"bid": 27,
					"bname_pinyin": "beijing",
					"bname": "北京"
				},
				{
					"bid": 271,
					"bname_pinyin": "bisuqiche",
					"bname": "比速汽车"
				},
				{
					"bid": 301,
					"bname_pinyin": "beiqidaoda",
					"bname": "北汽道达"
				},
				{
					"bid": 333,
					"bname_pinyin": "beijingqingxing",
					"bname": "北京清行"
				}
			]
		},

 */