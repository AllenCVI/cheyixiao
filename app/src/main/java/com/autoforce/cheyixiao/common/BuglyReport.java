package com.autoforce.cheyixiao.common;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.autoforce.cheyixiao.common.utils.AppMessageUtils;
import com.autoforce.cheyixiao.common.utils.StringUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.crash.h5.H5JavaScriptInterface;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * create by AiYaChao on 2018/11/29
 * 腾讯Bugly封装类
 */
public class BuglyReport {

    private static final String TAG = "BuglyReport";

    //Bugly 注册App ID
    private static final String APP_ID = "a55e1ae4e2";
    //Bugly 注册App KEY
    private static final String APP_KEY = "2d6286eb-085c-47f8-80d3-74341e58de7e";
    //Manifest meta 数据渠道号标识Key
    private static final String CHANNEL_KEY = "";
    //是否把当前设备设置成"开发设备"
    private static final boolean isDevelopmentDevice = true;
    //是否开启Bugly调试模式
    private static final boolean isOpenBugly = true;
    //Bugly延迟上报时间（单位毫秒）
    private static final int APP_BUGLY_REPORT_DELAY = 10000;

    private static Context context;
    private static volatile BuglyReport instance = null;

    private BuglyReport(){}

    public static BuglyReport getInstance(Context application) {
        context = application;
        if (instance == null) {
            synchronized (BuglyReport.class) {
                if (instance == null) {
                    instance = new BuglyReport();
                }
            }
        }
        return instance;
    }

    /**
     * Bugly初始化
     */
    public void init() {

        //设置当前设备属性（是否为"开发设备"）
        CrashReport.setIsDevelopmentDevice(context, isDevelopmentDevice);

        //获取当前包名
        String packageName = context.getPackageName();

        //获取当前进程名
        String processName = getProcessName(Process.myPid());

        //初始化Bugly上报策略
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        initBuglyStrategy(strategy);

        //设置是否上报进程
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        //初始化Bugly
        CrashReport.initCrashReport(context, APP_ID, isOpenBugly, strategy);
    }

    /**
     * 自定义标签，用于标明App的某个“场景”。
     * 在发生Crash时会显示该Crash所在的“场景”，
     * 以最后设置的标签为准，标签id需大于0。
     * @param scentTagId Crash场景Id
     */
    public void setSceneTag(int scentTagId) {
        if (scentTagId <= 0) {
            Log.e(TAG, "Crash场景Id小于等于0，设置失败");
            return;
        }
        CrashReport.setUserSceneTag(context, scentTagId);
    }

    /**
     * 自定义Map参数可以保存发生Crash时的一些自定义的环境信息。
     * 在发生Crash时会随着异常信息一起上报并在页面展示。
     * 最多可以有9对自定义的key-value（超过则添加失败）；
     * key限长50字节，value限长200字节，过长截断；
     * key必须匹配正则：[a-zA-Z[0-9]]+。
     * @param key   Crash场景数据Key
     * @param value Crash场景数据Value
     */
    public void putSceneData(String key, String value) {
        CrashReport.putUserData(context, key, value);
    }

    /**
     * Javascript的异常捕获和上报能力，
     * 以便开发者可以感知到 WebView中发生的Javascript异常。
     * 建议在WebChromeClient的onProgressChanged函数中调用该方法。
     * @param nativeWebView Android官方的WebView
     */
    public void catchNativeWebViewJsError(android.webkit.WebView nativeWebView) {
        CrashReport.setJavascriptMonitor(nativeWebView, true);
    }

    /**
     * Javascript的异常捕获和上报能力，
     * 以便开发者可以感知到 WebView中发生的Javascript异常。
     * 建议在WebChromeClient的onProgressChanged函数中调用该方法。
     * @param threePartWebView 非Android官方的WebView（例如使用X5内核）
     */
    public void catchThreePartWebviewJsError(WebView threePartWebView) {
        CrashReport.setJavascriptMonitor(new CrashReport.WebViewInterface() {
            @Override
            public String getUrl() {
                return threePartWebView.getUrl();
            }

            @Override
            public void setJavaScriptEnabled(boolean b) {
                WebSettings webSettings = threePartWebView.getSettings();
                webSettings.setJavaScriptEnabled(b);
            }

            @Override
            public void loadUrl(String s) {
                threePartWebView.loadUrl(s);
            }

            @Override
            public void addJavascriptInterface(H5JavaScriptInterface h5JavaScriptInterface, String s) {
                threePartWebView.addJavascriptInterface(h5JavaScriptInterface, s);
            }

            @Override
            public CharSequence getContentDescription() {
                return threePartWebView.getContentDescription();
            }
        }, true);
    }

    /**
     * 设置用户ID
     * 精确定位到某个用户的异常
     * @param userId 用户Id
     */
    public void setUserId(String userId) {
        CrashReport.setUserId(userId);
    }

    /**
     * 主动上报Catch的异常
     * 例如在try catch结构catch方法中调用
     * @param thr 捕获的异常
     */
    public void postCatchException(Throwable thr) {
        CrashReport.postCatchedException(thr);
    }

    /**
     * Bugly上报策略初始化
     */
    private void initBuglyStrategy(CrashReport.UserStrategy strategy) {

        //设置App渠道
        strategy.setAppChannel(AppMessageUtils.getAppMetaData(context, CHANNEL_KEY));

        //设置App版本
        strategy.setAppVersion(AppMessageUtils.getAppVersionName(context));

        //设置App包名
        strategy.setAppPackageName(AppMessageUtils.getAppPackageName(context));

        //设置Bugly初始化延迟，默认为10s
        strategy.setAppReportDelay(APP_BUGLY_REPORT_DELAY);
    }

    /**
     * 获取当前进程名称
     */
    private String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!StringUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
