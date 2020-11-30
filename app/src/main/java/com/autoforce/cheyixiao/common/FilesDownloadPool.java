package com.autoforce.cheyixiao.common;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class FilesDownloadPool {

    private static volatile FilesDownloadPool instance = null;
    private Map<String, FilesDownload> filesDownloadContainer = new HashMap<>();

    private FilesDownloadPool() {
    }

    public static FilesDownloadPool getInstance() {
        if (instance == null) {
            synchronized (FilesDownloadPool.class) {
                if (instance == null) {
                    instance = new FilesDownloadPool();
                }
            }
        }
        return instance;
    }

    public FilesDownload getFilesDownload(Context context, String carId) {
        FilesDownload filesDownload = null;
        if (filesDownloadContainer.containsKey(carId)) {
            filesDownload = filesDownloadContainer.get(carId);
        } else {
            filesDownload = new FilesDownload(context);
            filesDownloadContainer.put(carId, filesDownload);
        }
        return filesDownload;
    }
}
