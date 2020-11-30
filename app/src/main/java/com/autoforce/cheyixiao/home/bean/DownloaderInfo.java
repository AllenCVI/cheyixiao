package com.autoforce.cheyixiao.home.bean;

/**
 * Created by liusilong on 2018/12/25.
 * version:1.0
 * Describe: 下载列表车型包数据库实体
 */
public class DownloaderInfo {
    // 车型 id
    private String carId;
    // 车型 名称
    private String carName;
    // 车型 表示 （4K, 3D）
    private String carTag;
    // 该车型包总量
    private int totalFileCount;
    // 该车型包已下载数量
    private int alreadyDownloadCount;
}
