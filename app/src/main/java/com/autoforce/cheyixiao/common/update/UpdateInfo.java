package com.autoforce.cheyixiao.common.update;

import java.util.List;

/**
 * Created by liusilong on 2018/11/27.
 * version:1.0
 * Describe:
 */
public class UpdateInfo {
//    {"code": 200, "version": "2.0.1", "url": "https://cdn.autoforce.net/ixiao/app/cheyixiao-app/cheyixiao-2.0.1.apk", "update_content":
//        ["1\u3001\u4f18\u5316\u56fe\u7247\u52a0\u8f7d\u901f\u5ea6", "2\u3001\u9996\u9875\u8f6e\u64ad\u8fdb\u884c\u6539\u7248\uff0c\u66f4\u52a0\u7f8e\u89c2", "3\u3001\u4fee\u6539\u4f18\u5316bug\uff0c\u4f53\u9a8c\u5347\u7ea7", "4\u3001\u9996\u9875\u8f6e\u64ad\u8fdb\u884c\u6539\u7248\uff0c\u66f4\u52a0\u7f8e\u89c2"]}
    private int code;
    private String version;
    private String url;
    private List<String> update_content;

    public UpdateInfo(String version, String url, List<String> update_content) {
        this.version = version;
        this.url = url;
        this.update_content = update_content;
    }


    public UpdateInfo(){


    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(List<String> update_content) {
        this.update_content = update_content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(version);
        sb.append("\n").append(url).append("\n");

        for (String s : update_content) {
            sb.append(s).append("\n");
        }

        return sb.toString();
    }
}
