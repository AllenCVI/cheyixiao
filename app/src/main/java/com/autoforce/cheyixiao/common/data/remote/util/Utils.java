package com.autoforce.cheyixiao.common.data.remote.util;

import android.text.TextUtils;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xialihao on 2018/12/3.
 */
public class Utils {
    public static String paramsToJson(String params) {
        if (params.contains(":")) {
            return params;
        }
        try {
            new JSONArray(params);
            return params;
        } catch (JSONException e) {
            Map<String, String> map = new LinkedHashMap<>();
            if (!TextUtils.isEmpty(params)) {
                String[] array = params.split("&");
                for (String pair : array) {
                    if ("=".equals(pair.trim())) {
                        continue;
                    }
                    String[] entity = pair.split("=");
                    if (entity.length == 1) {
                        map.put(decode(entity[0]), null);
                    } else {
                        map.put(decode(entity[0]), decode(entity[1]));
                    }
                }
            }
            return GsonProvider.gson().toJson(map);
        }


    }

    private static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
