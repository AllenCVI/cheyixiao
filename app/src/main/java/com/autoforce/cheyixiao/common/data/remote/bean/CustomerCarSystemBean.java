package com.autoforce.cheyixiao.common.data.remote.bean;

import java.util.List;

/**
 * Created by liujialei on 2018/11/23
 */
public class CustomerCarSystemBean {


    int code;
    List<Result> result;

    public class Result{

        String name;
        int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    "result": [
        {
            "name": "奥迪A6L",
            "id": 18
        },
        {
            "name": "奥迪A8",
            "id": 146
        },
        {
            "name": "奥迪TT",
            "id": 148
        },
        {
            "name": "奥迪A3(进口)",
            "id": 370
        },
        {
            "name": "奥迪Q7",
            "id": 412
        },
        {
            "name": "奥迪A4(进口)",
            "id": 471
        },
        {
            "name": "奥迪A6(进口)",
            "id": 472
        },
        {
            "name": "奥迪R8",
            "id": 511
        },
        {
            "name": "奥迪A5",
            "id": 538
        },
        {
            "name": "奥迪Q5(进口)",
            "id": 593
        },
        {
            "name": "奥迪A1",
            "id": 650
        },
        {
            "name": "奥迪A4L",
            "id": 692
        },
        {
            "name": "奥迪A7",
            "id": 740
        },
        {
            "name": "奥迪Q5",
            "id": 812
        },
        {
            "name": "奥迪Q3(进口)",
            "id": 2264
        },
        {
            "name": "奥迪S3",
            "id": 2730
        },
        {
            "name": "奥迪RS 3",
            "id": 2731
        },
        {
            "name": "奥迪S4",
            "id": 2732
        },
        {
            "name": "奥迪RS 4",
            "id": 2733
        },
        {
            "name": "奥迪S5",
            "id": 2734
        },
        {
            "name": "奥迪RS 5",
            "id": 2735
        },
        {
            "name": "奥迪S6",
            "id": 2736
        },
        {
            "name": "奥迪RS 6",
            "id": 2737
        },
        {
            "name": "奥迪S7",
            "id": 2738
        },
        {
            "name": "奥迪S8",
            "id": 2739
        },
        {
            "name": "奥迪TTS",
            "id": 2740
        },
        {
            "name": "奥迪TT RS",
            "id": 2741
        },
        {
            "name": "奥迪SQ5",
            "id": 2841
        },
        {
            "name": "奥迪Q3",
            "id": 2951
        },
        {
            "name": "奥迪RS 7",
            "id": 2994
        },
        {
            "name": "奥迪A3",
            "id": 3170
        },
        {
            "name": "奥迪A3新能源(进口)",
            "id": 4325
        },
        {
            "name": "奥迪Q7新能源",
            "id": 4460
        },
        {
            "name": "奥迪A6L新能源",
            "id": 4526
        },
        {
            "name": "奥迪Q5L",
            "id": 4851
        },
        {
            "name": "奥迪Q2L",
            "id": 4871
        }
    ]
}
 */