package com.autoforce.cheyixiao.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.home.bean.HomeCarFileData;
import com.autoforce.cheyixiao.home.dao.HomeCarFilePathDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilesDownload {

    private static final String TAG = "FilesDownload";

    Context context;
    boolean isDownloading = false;
    OnProgressListener listener;

    public FilesDownload(Context context) {
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public void dealOfflineExperience(String downloadUrl, String carId) {
        String urlKey = MD5Util.md5(downloadUrl);
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String carFileDataStr = HttpUtils.downloadDataString(downloadUrl);
            if (!StringUtils.isEmpty(carFileDataStr)) {
                Log.e(TAG, "文件下载成功");
                emitter.onNext(carFileDataStr);
            }
        }).map(s -> {
            List<HomeCarFileData> onlineData = resolveHomeCarFileData(s);
            Log.e(TAG, "网络文件资源个数 : " + onlineData.size());
            List<HomeCarFileData> localData = getLocalHomeCarFileData(carId, urlKey);
            Log.e(TAG, "本地资源文件个数 : " + (localData == null ? 0 : localData.size()));
            Map<String, String> onlineHashData = transListToMap(onlineData);
            Map<String, String> localHashData = transListToMap(localData);

            if (isOnlineDataUpdate(onlineData, localData, onlineHashData, localHashData)) {
                Log.e(TAG, "本地文件与网络文件内容有差异");
                saveHomeCarFileData(carId, urlKey, s);
                filterHomeCarFileData(onlineData, onlineHashData, localHashData, carId);
            } else {
                Log.e(TAG, "本地文件与网络文件内容没有差异");
                onlineData.clear();
            }
            return onlineData;
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(carFileData -> {
            dealFileUrl(carFileData, carId);
            deleteLocalFile(carId, carFileData);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(carFileData -> {
            Log.e(TAG, carFileData.size() + "");
            downloadFiles(carFileData, carId, urlKey);
        });
    }

    /**
     * 获取本地存储Json数据
     *
     * @param urlKey 文件名称
     * @return
     */
    private List<HomeCarFileData> getLocalHomeCarFileData(String carId, String urlKey) {
        String fileContentStr = FileUtils.getFileContentStr(context.getExternalFilesDir(carId), urlKey);
        return resolveHomeCarFileData(fileContentStr);
    }

    /**
     * 通过比较网络数据与本地数据差异，判断本地数据是否需要更新
     */
    private boolean isOnlineDataUpdate(List<HomeCarFileData> onlineCarFileData, List<HomeCarFileData> localCarFileData
            , Map<String, String> onlineCarFileHashData, Map<String, String> localCarFileHashData) {
        boolean isUpdate = false;
        if (localCarFileData == null || localCarFileData.size() != onlineCarFileData.size()) {
            Log.e(TAG, "本地文件与网络文件内容有差异001");
            isUpdate = true;
        } else {
            for (HomeCarFileData data : onlineCarFileData) {
                if (!onlineCarFileHashData.get(data.getFile()).equals(localCarFileHashData.get(data.getFile()))) {
                    isUpdate = true;
                    Log.e(TAG, "本地文件与网络文件内容有差异002");
                    break;
                }
            }
        }
        return isUpdate;
    }

    /**
     * 保存网络数据到本地
     */
    private void saveHomeCarFileData(String carId, String urlKey, String carFileDataStr) {
        File parentFile = context.getExternalFilesDir(carId);
        if (FileUtils.isFileExist(parentFile, urlKey)) {
            Log.e(TAG, "删除本地文件carId:" + carId + ", urlKey : " + urlKey);
        }
        FileUtils.deleteFile(parentFile, urlKey);
        Log.e(TAG, "保存本地文件carId:" + carId + ", urlKey : " + urlKey);
        FileUtils.saveFileContentStr(parentFile, urlKey, carFileDataStr);

    }

    /**
     * 把网络数据中没有变化的过滤掉
     */
    private void filterHomeCarFileData(List<HomeCarFileData> onlineCarFileData, Map<String, String> onlineCarFileHashData
            , Map<String, String> localCarFileHashData, String carId) {
        HomeCarFilePathDao dao = new HomeCarFilePathDao(context.getApplicationContext());
        String urlHost = String.format(context.getResources().getString(R.string.offline_download_url_host), carId);
        Log.e(TAG, "开始过滤数据");
        List<HomeCarFileData> equalDataList = new ArrayList<>();
        for (HomeCarFileData data : onlineCarFileData) {
            if (onlineCarFileHashData.get(data.getFile()).equals(localCarFileHashData.get(data.getFile()))
                    && dao.queryHomeCarFilePathData(MD5Util.md5(urlHost + data.getFile())) != null) {
                equalDataList.add(data);
            }
        }
        Log.e(TAG, "无变化文件个数 : " + equalDataList.size());
        onlineCarFileData.removeAll(equalDataList);
        Log.e(TAG, "有变化文件个数 : " + onlineCarFileData.size());
        Log.e(TAG, "过滤数据完毕");
    }

    /**
     * 网络原生字符串数据解析
     */
    public List<HomeCarFileData> resolveHomeCarFileData(String rawDataStr) {
        Type listType = new TypeToken<List<HomeCarFileData>>() {
        }.getType();
        Gson gson = new Gson();
        List<HomeCarFileData> carFileData = gson.fromJson(rawDataStr, listType);
        return carFileData;
    }

    /**
     * 把List保存的数据转变化Map存储，file字段为key，hash为value
     */
    private Map<String, String> transListToMap(List<HomeCarFileData> carFileData) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (carFileData != null) {
            for (HomeCarFileData data : carFileData) {
                map.put(data.getFile(), data.getHash());
            }
        }
        return map;
    }

    /**
     * 把变化的文件url拼接完整
     */
    private void dealFileUrl(List<HomeCarFileData> carFileData, String carId) {
        String urlHost = String.format(context.getResources().getString(R.string.offline_download_url_host), carId);
        for (HomeCarFileData data : carFileData) {
            data.setFile(urlHost + data.getFile());
        }
    }

    /**
     * 删除本地变化的文件
     */
    private void deleteLocalFile(String carId, List<HomeCarFileData> carFileData) {
        File parentFile = context.getExternalFilesDir(carId);
        HomeCarFilePathDao dao = new HomeCarFilePathDao(context.getApplicationContext());
        for (HomeCarFileData data : carFileData) {
            FileUtils.deleteFile(parentFile, MD5Util.md5(data.getFile()));
            dao.deleteHomeCarFilePathData(MD5Util.md5(data.getFile()));
        }
    }

    private void downloadFiles(List<HomeCarFileData> carFileData, String carId, String jsonKey) {
        if (carFileData.size() == 0) {
            //ToastUtil.showToast("本地已存在离线数据");
            if (listener != null)
                listener.onProgress(0, 0);
            return;
        }
        HomeCarFilePathDao dao = new HomeCarFilePathDao(context.getApplicationContext());
        final int[] current = {0};
        int total = carFileData.size();
        File bunchDir = context.getExternalFilesDir(carId);
        DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setParentPathFile(bunchDir)
                .setMinIntervalMillisCallbackProcess(300)
                .commit();
        for (int i = 0; i < carFileData.size(); i++) {
            DownloadTask.Builder taskBuilder = new DownloadTask
                    .Builder(carFileData.get(i).getFile(), bunchDir)
                    .setFilename(MD5Util.md5(carFileData.get(i).getFile()));
            builder.bind(taskBuilder).addTag(1, i);
        }
        DownloadContext downloadContext = builder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {}

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
                isDownloading = false;
            }
        }).build();
        downloadContext.start(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                isDownloading = true;
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
                current[0] += 1;
                Log.e(TAG, "file:" + task.getFile().getAbsolutePath() + "   url:" + task.getUrl());
                if (cause == EndCause.COMPLETED) {
                    if (dao.queryHomeCarFilePathData(MD5Util.md5(task.getUrl())) != null) {
                        dao.deleteHomeCarFilePathData(MD5Util.md5(task.getUrl()));
                    }
                    dao.insertHomeCarFilePathData(MD5Util.md5(task.getUrl()), task.getFile().getAbsolutePath(), carId);
                }
                if (current[0] < total) {
                    if (listener != null) {
                        listener.onProgress(current[0], total);
                    }
                } else {
                    if (listener != null) {
                        listener.onProgress(current[0], total);
                    }
                }
            }
        }, true);
    }

    private boolean isFilesDownloaded(String carId, String jsonKey) {
        boolean isFinish = true;
        HomeCarFilePathDao dao = new HomeCarFilePathDao(context.getApplicationContext());
        List<HomeCarFileData> localData = getLocalHomeCarFileData(carId, jsonKey);
        dealFileUrl(localData, carId);
        for (HomeCarFileData data : localData) {
            if (dao.queryHomeCarFilePathData(MD5Util.md5(data.getFile())) == null) {
                isFinish = false;
                break;
            }
        }
        return isFinish;
    }

    @SuppressLint("CheckResult")
    public void isFilesNeedUpdate(String downloadUrl, String carId, OnUpdateListener listener) {
        String urlKey = MD5Util.md5(downloadUrl);
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String carFileDataStr = HttpUtils.downloadDataString(downloadUrl);
            if (!StringUtils.isEmpty(carFileDataStr)) {
                Log.e(TAG, "文件下载成功");
                emitter.onNext(carFileDataStr);
            }
        }).map(s -> {
            List<HomeCarFileData> onlineData = resolveHomeCarFileData(s);
            Log.e(TAG, "网络文件资源个数 : " + onlineData.size());
            List<HomeCarFileData> localData = getLocalHomeCarFileData(carId, urlKey);
            Log.e(TAG, "本地资源文件个数 : " + (localData == null ? 0 : localData.size()));
            Map<String, String> onlineHashData = transListToMap(onlineData);
            Map<String, String> localHashData = transListToMap(localData);
            if (isOnlineDataUpdate(onlineData, localData, onlineHashData, localHashData)) {
                Log.e(TAG, "本地文件与网络文件内容有差异");
                return true;
            } else {
                Log.e(TAG, "本地文件与网络文件内容没有差异");
                return false;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                listener.onUpdate(aBoolean);
            }
        });
        /*.map(s -> {
            List<HomeCarFileData> onlineData = resolveHomeCarFileData(s);
            Log.e(TAG, "网络文件资源个数 : " + onlineData.size());
            List<HomeCarFileData> localData = getLocalHomeCarFileData(carId, urlKey);
            Log.e(TAG, "本地资源文件个数 : " + (localData == null ? 0 : localData.size()));
            Map<String, String> onlineHashData = transListToMap(onlineData);
            Map<String, String> localHashData = transListToMap(localData);
            if (isOnlineDataUpdate(onlineData, localData, onlineHashData, localHashData)) {
                Log.e(TAG, "本地文件与网络文件内容有差异");
                SpUtils.getInstance(carId).put(HomeConstant.IS_NEED_UPDATE, true);
            } else {
                Log.e(TAG, "本地文件与网络文件内容没有差异");
                SpUtils.getInstance(carId).put(HomeConstant.IS_NEED_UPDATE, false);
            }
            return onlineData;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(carFileData -> {
            Log.e(TAG, carFileData.size() + "");
            downloadFiles(carFileData, carId, urlKey);
        });*/
    }

    public static void deleteFiles(HomeCarFilePathDao dao, File carIdFile) {
        for (File file : carIdFile.listFiles()) {
            if (file.isFile()) {
                if (FileUtils.deleteFile(file)) {
                    dao.deleteHomeCarFilePathData(file.getName());
                }
            } else {
                deleteFiles(dao, file);
            }
        }
    }

    public void setListener(OnProgressListener listener) {
        this.listener = listener;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public interface OnProgressListener {
        public void onProgress(int downloadedNumber, int totalNumber);
    }

    public interface OnUpdateListener {
        public void onUpdate(boolean isNeedUpdate);
    }
}
