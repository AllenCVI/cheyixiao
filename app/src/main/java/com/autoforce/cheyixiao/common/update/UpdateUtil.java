package com.autoforce.cheyixiao.common.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.data.remote.OkHttpClientProvider;
import com.autoforce.cheyixiao.common.utils.JSONUtil;
import com.google.gson.Gson;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by liusilong on 2018/11/27.
 * version:1.0
 * Describe:
 */
public class UpdateUtil {
    private static final String TAG = "UpdateUtil";
    //    private static final String UPDATE_URL = "http://192.168.3.233:8080/server.json";
    private Context context;
    private DownloadTask task;
    private String url;
    private Gson gson;

    private String name = "new.apk";
    private File apkFile;
    private TextView tv_rate;
    private ProgressBar progressBar;
    private UpdateDialog dialog;

    public UpdateUtil(Context context, String url) {
        this.context = context;
        this.url = url;
        gson = new Gson();
    }

    public void checkForUpdate() {
        getServerData();
    }

    private void compareVersion(String serverJson) {
        if (!JSONUtil.isJSONValid(serverJson)) {
            return;
        }
        try {
            // 获取本地版本号
            String localVersion = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            final UpdateInfo updateInfo = gson.fromJson(serverJson, UpdateInfo.class);
            String version = updateInfo.getVersion();
            if (localVersion != null && version != null) {
                int versionCode = VersionUtil.compareVersion(version, localVersion);
                if (versionCode == 1) {
                    // 下载
                    if (updateInfo != null) {
                        ((Activity) context).runOnUiThread(() -> showDialog(updateInfo));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     * TODO 需要添加下载 APK 事件
     *
     * @param url
     */
    private void downloadAPK(String url) {

        Log.e(TAG, "downloadAPK: " + url);

        apkFile = new File(context.getFilesDir().getAbsolutePath() + File.separator + name);

        if (apkFile.exists()) {
            apkFile.delete();
        }
        task = new DownloadTask.Builder(url, apkFile).build();
        task.enqueue(downloadListener);

    }


    long time=0l;
    private void showDialog(final UpdateInfo updateInfo) {
        //        if (updateInfo != null && updateInfo.getTips().size() > 0) {
        if (updateInfo != null) {
            dialog = new UpdateDialog(context);
            dialog.setTips(updateInfo.getUpdate_content());
            dialog.show();
            dialog.setOnClickListener(v -> {
//                  dialog.dismiss();

                long currentTimeMillis = System.currentTimeMillis();
                if(currentTimeMillis - time<1000){
                    return;
                }
                time = currentTimeMillis;

                LinearLayout linearLayout = v.findViewById(R.id.linearlayout);

                LinearLayout lin = v.findViewById(R.id.lin);

                progressBar = v.findViewById(R.id.progressbar);
                tv_rate = v.findViewById(R.id.tv_rate);

                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                    lin.setVisibility(View.VISIBLE);
                }

                downloadAPK(updateInfo.getUrl());
            });

            //            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            //            builder.setTitle("版本更新");
            //            View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
            //            LinearLayout tipContainer = view.findViewById(R.id.linear_tip_container);
            //            tipContainer.removeAllViews();
            //            List<String> tips = updateInfo.getTips();
            //            int size = tips.size();
            //            for (int i = 0; i < size; i++) {
            //                TextView textView = new TextView(context);
            //                textView.setText((i + 1) + "、" + tips.get(i));
            //                tipContainer.addView(textView);
            //            }
            //            builder.setView(view);
            //            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    dialog.dismiss();
            //                }
            //            });
            //            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    dialog.dismiss();
            //                    downloadAPK(updateInfo.getUrl());
            //                }
            //            });
            //            Dialog dialog = builder.create();
            //            dialog.show();
        }
    }






    /**
     * 获取远程地址
     *
     * @return
     */
    private void getServerData() {
        OkHttpClient client = OkHttpClientProvider.myClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: update failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String serverJson = response.body().string();
                Log.e(TAG, "onResponse: " + serverJson);
                compareVersion(serverJson);
            }
        });
    }

    private void installAPK() {


        String[] command = {"chmod", "777", apkFile.getPath() };
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (apkFile.exists() && apkFile.isFile()) {
                FileProvider7.setIntentDataAndType(context,
                        intent, "application/vnd.android.package-archive", apkFile, true);
            }
        }else {
            if (!apkFile.exists()) {
                Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    private DownloadListener downloadListener = new DownloadListener4WithSpeed() {


        private long totalLength;
        private long contentLength=0;

        @Override
        public void taskStart(DownloadTask task) {
            Log.e(TAG, "taskStart: start");
        }

        @Override
        public void connectStart(DownloadTask task, int blockIndex, Map<String, List<String>> requestHeaderFields) {
            Log.e(TAG, "connectStart: ");
        }

        @Override
        public void connectEnd(DownloadTask task, int blockIndex, int responseCode, Map<String, List<String>> responseHeaderFields) {

            try {
                List<String> list = responseHeaderFields.get("Content-Length");
                contentLength = Long.parseLong(list.get(0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "connectEnd: ");


        }

        @Override
        public void infoReady(DownloadTask task, BreakpointInfo info, boolean fromBreakpoint, Listener4SpeedAssistExtend.Listener4SpeedModel model) {
            totalLength = info.getTotalLength();
        }

        @Override
        public void progressBlock(DownloadTask task, int blockIndex, long currentBlockOffset, SpeedCalculator blockSpeed) {

        }

        @Override
        public void progress(DownloadTask task, long currentOffset, SpeedCalculator taskSpeed) {

            if(contentLength==0){
                return;
            }
            int persent = (int) (100*currentOffset/totalLength);
            progressBar.setProgress(persent);
            tv_rate.setText("正在更新应用..."+"("+persent+"%)");
            Log.e(TAG, "progress: " + currentOffset);
        }

        @Override
        public void blockEnd(DownloadTask task, int blockIndex, BlockInfo info, SpeedCalculator blockSpeed) {
            Log.e(TAG, "end");
        }

        @Override
        public void taskEnd(DownloadTask task, EndCause cause, Exception realCause, SpeedCalculator taskSpeed) {
            Log.e(TAG, "taskEnd: " + cause);
            switch (cause) {
                case ERROR:
                case FILE_BUSY:
                case SAME_TASK_BUSY:
                    task.enqueue(this);
                    break;
                case CANCELED:
                    break;
                case COMPLETED:
                    dialog.dismiss();
                    installAPK();
                    break;
            }
        }
    };


}
