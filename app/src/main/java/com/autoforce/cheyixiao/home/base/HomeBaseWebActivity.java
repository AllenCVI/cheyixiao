package com.autoforce.cheyixiao.home.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseX5WebViewActivity;
import com.autoforce.cheyixiao.common.FilesDownload;
import com.autoforce.cheyixiao.common.FilesDownloadPool;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.home.HomeConstant;
import com.autoforce.cheyixiao.home.act.HomeBrandPanoramaActivity;
import com.autoforce.cheyixiao.home.act.HomeDownloadListActivity;
import com.autoforce.cheyixiao.home.act.HomeWebActivity;
import com.autoforce.cheyixiao.home.bean.HomeCarFileData;
import com.autoforce.cheyixiao.home.dao.HomeCarFilePathDao;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.http.HEAD;

import java.io.File;
import java.util.List;

/**
 * Created by liusilong on 2018/12/10.
 * version:1.0
 * Describe:
 */
public abstract class HomeBaseWebActivity extends BaseX5WebViewActivity {
    private static final String TAG = "HomeBaseWebActivity";

    protected FilesDownload filesDownload;
    DownloadListener downloadListener;

    public interface DownloadListener {
        /**
         * 下载进度回调方法
         *
         * @param currCount 当前下载到第几个文件
         * @param total     下载文件总个数
         */
        void onProgress(int currCount, int total);
    }


    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    protected String getCarId() {
        return null;
    }

    @Override
    protected void setOther() {
        Log.e(TAG, "setOther carId\t: " + getCarId());
        //filesDownload = new FilesDownload(getApplicationContext());
        filesDownload = FilesDownloadPool.getInstance().getFilesDownload(getApplicationContext(), getCarId());
        webView.addJavascriptInterface(new HomeWebJSBridge(), HomeConstant.JS_BRIDGE_KEY);
        setDownloadListener((currCount, total) -> {
            try {
                if (webView != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("curr", String.valueOf(currCount));
                    jsonObject.put("total", String.valueOf(total));
                    webView.loadUrl("javascript:androidUpdateProgress(" + jsonObject.toString() + ")");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        filesDownload.setListener(new FilesDownload.OnProgressListener() {
            @Override
            public void onProgress(int downloadedNumber, int totalNumber) {
                if (downloadedNumber > 0) {
                    downloadListener.onProgress(downloadedNumber, totalNumber);
                    if (downloadedNumber == totalNumber) {
                        SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_RECORD).put(getCarId(), true);
                        ToastUtil.showToast("离线数据包下载完成");
                    }
                } else {
                    SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_RECORD).put(getCarId(), true);
                    ToastUtil.showToast("本地已存在离线数据");
                }
            }
        });
    }

    @Override
    protected void onPageFinish() {
        super.onPageFinish();
//        if (getCarId() != null) {
//            Log.e(TAG, "setOther: " + getCarId());
//            boolean isDownloaded = SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_RECORD).getBoolean(getCarId());
//            Log.e(TAG, "setOther: " + String.valueOf(isDownloaded));
//            webView.loadUrl("javascript:checkAndroidDownLoaded(" + String.valueOf(isDownloaded) + ")");
//        }
    }

    class HomeWebJSBridge {
        /**
         * 获取车型包的下载资源 url
         * window.home_module.offlineExperience(carId)
         *
         * @param carId
         */
        @JavascriptInterface
        public void offlineExperience(String carId) {
            if (!filesDownload.isDownloading()) {
                String string = getResources().getString(R.string.offline_download_url);
                String downloadUrl = String.format(string, carId);
                Log.e(TAG, "offlineExperience: " + downloadUrl);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    preTestDeal(downloadUrl, carId);
                    SpUtils.getInstance(HomeConstant.CAR_DOWNLOAD_LIST_RECORD).put(carId, carId);
                    filesDownload.dealOfflineExperience(downloadUrl, carId);
                } else {
                    ToastUtil.showToast("无存储操作权限，无法下载离线文件");
                }
            } else {
                ToastUtil.showToast("正在下载。。。");
            }
        }

        /**
         * 跳转到 全景看车页面
         * window.home_module.toPanorama(url, carId)
         *
         * @param url
         */
        @JavascriptInterface
        public void toPanorama(String url, String carId) {
            Log.e(TAG, "toPanorama: carId:\t" + carId);
            webView.post(() -> HomeBrandPanoramaActivity.start(HomeBaseWebActivity.this, url, carId));
        }

        /**
         * 跳转到购车页面
         *
         * @param url
         */
        @JavascriptInterface
        public void toBuyCar(String url) {
            Log.e(TAG, "toBuyCar: " + url);
            HomeWebActivity.start(getApplicationContext(), url);
        }

        @JavascriptInterface
        public void deleteCar(String carId) {
            Log.e(TAG, "deleteCar: " + carId);
            //ToastUtil.showToast("carId:\t" + carId);
            deleteCarFiles(carId);
        }

        @JavascriptInterface
        public void toDownloadList() {
            HomeDownloadListActivity.start(getApplicationContext());
        }

    }

    @SuppressLint("CheckResult")
    private void deleteCarFiles(String carId) {
        final View[] view = {null};
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                File file = getExternalFilesDir(carId);
                HomeCarFilePathDao dao = new HomeCarFilePathDao(getApplicationContext());
                if (file.exists() && file.listFiles().length > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view[0] = showFilesDeleteLoading();
                        }
                    });
                    filesDownload.deleteFiles(dao, file);
                    FileUtils.deleteFilesDirectory(file);
                }
                emitter.onNext("文件已删除");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (view.length > 0 && view[0] != null)
                            hideFileDeleteLoading(view[0]);
                        ToastUtil.showToast(s);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void preTestDeal(String downloadUrl, String carId) {
        String urlKey = MD5Util.md5(downloadUrl);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String carFileDataStr = HttpUtils.downloadDataString(downloadUrl);
                if (!StringUtils.isEmpty(carFileDataStr)) {
                    Log.e(TAG, "文件下载成功");
                    emitter.onNext(carFileDataStr);
                }
            }
        }).map(new Function<String, List<HomeCarFileData>>() {
            @Override
            public List<HomeCarFileData> apply(String s) throws Exception {
                List<HomeCarFileData> onlineData = filesDownload.resolveHomeCarFileData(s);
                preTetSaveHomeCarFileData(carId, downloadUrl, s);
                return onlineData;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HomeCarFileData>>() {
                    @Override
                    public void accept(List<HomeCarFileData> carFileData) throws Exception {
                        Log.e(TAG, carFileData.size() + "");
                        preTestDownloadFiles(carFileData, carId, urlKey);
                    }
                });
    }

    private void preTetSaveHomeCarFileData(String carId, String fileUrl, String carFileDataStr) {
        Log.e(TAG, fileUrl);
        File parentFile = new File(getFilesDir(), "cars/" + carId);
        if (!parentFile.exists())
            parentFile.mkdirs();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        FileUtils.saveFileContentStr(parentFile, fileName, carFileDataStr);
    }

    private void preTestDownloadFiles(List<HomeCarFileData> carFileData, String carId, String jsonKey) {
        final int[] current = {0};
        int total = carFileData.size();
        DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setMinIntervalMillisCallbackProcess(300)
                .commit();
        String urlHost = String.format(getResources().getString(R.string.offline_download_url_host), carId);
        for (int i = 0; i < carFileData.size(); i++) {
            String file = carFileData.get(i).getFile();
            String url = urlHost + file;
            Log.e(TAG, file);
            File bunchDir = null;
            if (!file.contains("/")) {
                bunchDir = new File(getFilesDir(), "cars/" + carId);
            } else {
                bunchDir = new File(new File(getFilesDir(), "cars/" + carId)
                        , file.substring(0, file.lastIndexOf("/")));
            }
            if (!bunchDir.exists())
                bunchDir.mkdirs();
            DownloadTask.Builder taskBuilder = new DownloadTask
                    .Builder(url, bunchDir);
            builder.bind(taskBuilder).addTag(1, i);
        }
        DownloadContext downloadContext = builder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {
            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
            }
        }).build();
        downloadContext.start(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
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
                Log.e(TAG, current[0] + "");
                if (current[0] < total) {

                } else {
                    ToastUtil.showToast("离线数据包下载完成");
                }
            }
        }, true);
    }

    /*public View showPreDealProgress() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_files_download_progress, null);
        flContent.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void hidePreDealProgress(View view) {
        view.setVisibility(View.GONE);
        flContent.removeView(view);
    }*/

    public View showFilesDeleteLoading() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_cut_progress, null);
        FrameLayout flCutProgress = view.findViewById(R.id.fl_cut_progress);
        flCutProgress.setVisibility(View.VISIBLE);
        TextView tvTip = view.findViewById(R.id.tv_tip);
        tvTip.setText("本地数据删除中...");
        ImageView ivChaHao = view.findViewById(R.id.iv_cha_hao);
        ivChaHao.setVisibility(View.GONE);
        flContent.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void hideFileDeleteLoading(View view) {
        view.setVisibility(View.GONE);
        flContent.removeView(view);
    }

}
