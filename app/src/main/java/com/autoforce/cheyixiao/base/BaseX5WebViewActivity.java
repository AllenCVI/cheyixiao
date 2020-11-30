package com.autoforce.cheyixiao.base;

import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;

import com.autoforce.cheyixiao.BuildConfig;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.Bean;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.common.view.PageStateView;
import com.autoforce.cheyixiao.home.dao.HomeCarFilePathDao;
import com.autoforce.cheyixiao.login.InfoCertificateAct;
import com.autoforce.cheyixiao.login.LoginActivity;
import com.google.gson.Gson;
import com.jakewharton.disklrucache.DiskLruCache;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.*;
import com.tencent.smtt.sdk.*;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseX5WebViewActivity extends BaseActivity {


    //    @BindView(R.id.ib_back)
//    ImageButton ibBack;
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
    @BindView(R.id.fl_content)
    protected FrameLayout flContent;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.web_view)
    protected WebView webView;
    @BindView(R.id.ll_webView)
    protected LinearLayout llWebView;
    @BindView(R.id.psv_tip)
    protected PageStateView psvTip;
    @BindView(R.id.iv_back)
    protected ImageView ivBack;


    protected String cookie;
    protected DiskLruCache diskLruCache;
    private File file;
    private Uri imageUri;
    private String loadUrl = null;

    private static final int TAKE_PHOTO = 100;
    private static final int CHOSE_PHOTO = 101;
    protected String key;

    // javaInterface Method name
    public static final String PHOTO_TAKE = "openCamera";
    public static final String ALBUM_OPEN = "openPhotoLibrary";

    protected boolean finishflag = false;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_base_webview;
    }

    /*@Override
    protected int getToolbarTitle() {
        return getWebTitle();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ibBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        getWindow().setFormat(PixelFormat.TRANSLUCENT);//防止网页中的视频，上屏幕的时候，可能出现闪烁的情况
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//避免输入法界面弹出后遮挡输入光标的问题
        diskLruCache = DiskLruCacheUtils.getDiskLruCache(this);
        webSet();
    }

    ///

    private void webSet() {


        psvTip.bindView(webView);
        psvTip.setTitleVisibity();

        WebSettings webSetting = webView.getSettings();

        // chrome调试打开，debug包打开，release包关闭
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webSetting.setJavaScriptEnabled(true);//js交互允许

        //自适应屏幕
        webSetting.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSetting.setLoadWithOverviewMode(true);//缩放至屏幕的大小

        //缩放
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);

        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        webView.requestFocusFromTouch();

        //访问文件
        webSetting.setAllowFileAccess(true);

        //多窗口
        webSetting.setSupportMultipleWindows(true);
        //支持js打开新窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);

        webSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
        //支持内容重新布局
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        //h5缓存
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        //Log.e("CachePath", getExternalFilesDir("myCache").getPath());
        //webSetting.setAppCachePath(getExternalFilesDir("myCache").getPath());
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSetting.setDomStorageEnabled(true);//当网页需要保存数时据
        webSetting.setGeolocationEnabled(true);//启用还H5的地理定位服务
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setLoadWithOverviewMode(true);

        webView.addJavascriptInterface(new BaseJs2JavaObject(), "CYX_JSBridge");

        String realUrl = concatUrl();
        //Log.e("realUrl->", realUrl);
        loadUrl = realUrl;
        Logger.i("realUrl =-> " + realUrl);
        webView.loadUrl(realUrl);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {//本应用打开URL  子类不用重写此方法
                doUrlOverride(webView, s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {//该方法运行在主线程
                if (isInterceptRequest()) { //拦截下载需要请求页面的Html
                    //Log.e("onPageStarted->", s);
                    final String urlMd5 = MD5Util.md5(s);
                    if (diskLruCache != null)
                        if (!DiskLruCacheUtils.isFileSaved(diskLruCache, urlMd5, s))
                            DiskLruCacheUtils.downLoadFile(diskLruCache, s, urlMd5);
                }
                onPageStart();
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                if (isInterceptRequest()) {
                    Log.e("onPageFinished->", s);
                }
                cookie = CookieManager.getInstance().getCookie(s);
                onPageFinish();
                super.onPageFinished(webView, s);
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {//该方法运行在子线程
                Logger.w("shouldInterceptRequest -> " + request.getUrl().toString());
                if (isInterceptRequest()) {
                    //通过url检索本地数据，并用本地数据创建生成WebResourceResponse
                    WebResourceResponse response = getLocalWebResourceResponse(request);
                    if (response != null) {
                        return response;
                    }
                }
                return super.shouldInterceptRequest(webView, request);
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                if (llWebView != null)
                    llWebView.setVisibility(View.GONE);
                if (psvTip != null) {
                    psvTip.showNoNetWork();
                    psvTip.setVisibility(View.VISIBLE);
                    /*ivBack.setVisibility(View.VISIBLE);
                    ivBack.setOnClickListener(v -> finish());*/
                }
                finishflag = true;
                super.onReceivedError(webView, errorCode, description, failingUrl);
                Logger.e("onReceivedError -> " + errorCode + ", " + description + ", " + failingUrl);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                finishflag = true;
                Logger.e("onReceivedError -> " + webResourceError.getDescription());
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {//HTTPS接受所有证书
                finishflag = true;
                sslErrorHandler.proceed();
            }


        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {//网页打开进度条  子类不用实现此方法
                super.onProgressChanged(view, newProgress);
                if (!isFinishing() && progressBar != null) {
                    if (newProgress != 100) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }


            @Override
            public void onReceivedTitle(WebView webView, String s) {

                if (!isFinishing()) {
                    if (s.contains("0") || s.contains("4") || s.contains("5")) {
                        finishflag = true;
                        psvTip.showNoData();

                    }
                }
            }
        });

        psvTip.setOnClickRefreshListener(new PageStateView.OnclickRefreshListener() {
            @Override
            public void onClickRefresh() {
                if (NetUtils.isConnected(getApplicationContext())) {
                    webView.reload();
//                    ivBack.setVisibility(View.GONE);
                    psvTip.setVisibility(View.GONE);
                    llWebView.setVisibility(View.VISIBLE);
                } else {
                    ToastUtil.showToast("当前网络不可用，请打开网络连接");
                }
            }
        });
        psvTip.setOnBackClickListener(new PageStateView.OnBackClickListener() {
            @Override
            public void onBackClick() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

        setOther();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMengStatistics.onPageEnd(getClass().getSimpleName());
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMengStatistics.onPageStart(getClass().getSimpleName());
    }

    class BaseJs2JavaObject {

        @JavascriptInterface
        public void goBack(Object object) {//统一处理h5的返回
//            onBackPressed();
//            finish();
            doBack();
        }

        @JavascriptInterface
        public void goLogIn() {//h5需要调用的登陆页
            LoginActivity.start(BaseX5WebViewActivity.this);
        }

        @JavascriptInterface
        public void postMessage(String json) {

            Logger.i("postMessage: " + json);
            Gson gson = new Gson();
            Bean bean = gson.fromJson(json, Bean.class);
            runOnUiThread(() -> {
                if (bean != null) {
                    dealWithUpload(bean);
                    if ("goBack".equals(bean.getMethod())) {
                        goBack(bean);
                    } else if ("goLogIn".equals(bean.getMethod())) {
                        goLogIn();
                    } else {//子类实现的js交互
                        childUse(bean);
                    }

                    key = bean.getType();

                }
            });

        }

        @JavascriptInterface
        public void umengEvent(String eventString ) {
            Log.e("maidian==" , "点击事件2222"+eventString);
            String eventId = "";
            String eventKey="";
            String  eventValue="";

            String[] split = eventString.split(",");
            Logger.e("maidian==%s", "点击事件"+split.length);
            if(split.length == 1){
                eventId = split[0];
            }else {
                 eventKey = StringUtils.isEmpty(split[1]) ? "" : split[1];
                 eventValue = StringUtils.isEmpty(split[2]) ? "" : split[2];
            }

            if (StringUtils.isEmpty(eventKey) && StringUtils.isEmpty(eventValue)) {
                UMengStatistics.statisticEventNumber(eventId);
                Logger.e("maidian==%s" , "点击事件"+eventId+"---"+eventKey+"==="+eventValue);
            } else {
                Logger.e("maidian==%s", "点击事件========="+eventId+"---"+eventKey+"==="+eventValue);
                HashMap<String, String> map = new HashMap<>();
                map.put(eventKey, eventValue);
                UMengStatistics.statisticEventNumber(eventId, map);
            }

        }

        @JavascriptInterface
        public void jumpInfoCertificate(){
            InfoCertificateAct.Companion.start(BaseX5WebViewActivity.this , true);
        }
    }

    protected void dealWithUpload(Bean bean) {
        if ("openCamera".equals(bean.getMethod())) {
            takePhoto();
        } else if ("openPhotoLibrary".equals(bean.getMethod())) {
            chosePhoto();
        }
    }

    public void doBack() {
        finish();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    protected int getTakePhotoRequestCode() {
        return 100;
    }

    @Override
    protected int getChosePhotoRequestCode() {
        return 101;
    }

    @NonNull
    protected String concatUrl() {
        String url = getUrl();

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        String token = LocalRepository.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            map.put(BaseConstant.TOKEN, token);
        }

        String salerId = LocalRepository.getInstance().getSalerId();
        if (!TextUtils.isEmpty(salerId)) {
            map.put(BaseConstant.SALER, salerId);
        }

        String role = LocalRepository.getInstance().getRole();

        if (!TextUtils.isEmpty(role)) {
            map.put(BaseConstant.ROLE, role);
        }

        String certCode = LocalRepository.getInstance().getCertCode();

        if (!TextUtils.isEmpty(certCode)) {
            map.put(BaseConstant.CERT_CODE, certCode);
        }

        map.put(BaseConstant.ISLINK, "1");

        addParams(map);

        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }

        if (sb.lastIndexOf("&") == sb.length() - 1) {
            sb.delete(sb.length() - 1, sb.length());
        }


        Log.i("allence", url + "?" + sb.toString());


        return url + "?" + sb.toString();
    }


    @Override
    public void onBackPressed() {
        if (finishflag) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            finishflag = false;
            return;
        }
        webView.loadUrl("javascript:physicsBack()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(false);
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.destroy();
            webView = null;
        }

    }

    /**
     * 根据request获取本地文件生成WebResourceResponse
     *
     * @param request 拦截的请求
     * @return 生成的WebResourceResponse
     */
    public WebResourceResponse getLocalWebResourceResponse(WebResourceRequest request) {
        WebResourceResponse response = null;
        try {
            //请求的url
            final String fileUrl = request.getUrl().toString();
            //Log.e("interceptUrl -> " + fileUrl);
            //Log.e("interceptUrl->", fileUrl);
            //请求url的md5值，用作该文件在DiskLruCache操作的Key值

            final String fileKey = MD5Util.md5(fileUrl);// + fileUrl.substring(fileUrl.lastIndexOf(".") + 1);

            Log.e("interceptUrl -> ", fileKey);

            HomeCarFilePathDao dao = new HomeCarFilePathDao(this);
            String path = null;
            //判断本地离线缓存包数据是否存在该文件，是优先调用离线缓存包数据
            if ((path = dao.queryHomeCarFilePathData(fileKey)) != null) {
                //Log.e("ccc", "加载本地数据filePath:" + path + "   url:" + fileUrl);
                InputStream inputStream = FileUtils.getFileInputStream(path);
                if (inputStream != null)
                    response = createWebResourceResponse(fileUrl, inputStream);
            }

            //如果DiskLruCache初始化成功，并且本地离线缓存包数据中不存在该文件则进行本地在线数据缓存处理
            if (response == null && diskLruCache != null) {
                if ((!DiskLruCacheUtils.isFileSaved(diskLruCache, fileKey, fileUrl)) && (!fileUrl.equals(loadUrl))) {
                    Log.e("aaa", "下载在线拦截数据:" + fileUrl + "   md5:" + fileKey);
                    DiskLruCacheUtils.downLoadFile(diskLruCache, fileUrl, fileKey);
                } else {
                    Log.e("bbb", "加载本地myCache数据:" + fileUrl + "   md5:" + fileKey);
                    InputStream inputStream = DiskLruCacheUtils.getSavedFileInputStream(diskLruCache, fileKey, fileUrl);
                    response = createWebResourceResponse(fileUrl, inputStream);
                }
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }

    private WebResourceResponse createWebResourceResponse(String fileUrl, InputStream inputStream) {
        WebResourceResponse response = null;
        Map<String, String> map = new HashMap<>();
        map.put("Access-Control-Allow-Origin", "*");
        map.put("Access-Control-Allow-Headers", "Content-Type");
        if (inputStream != null) {
            response = new WebResourceResponse(MimeTypeUtils.getMimeTypeFromUrl(fileUrl)
                    , "utf-8"
                    , 200
                    , "localFile"
                    , map
                    , inputStream);
        }
        return response;
    }

    /**
     * webView 开始加载的时候调用
     */
    protected void onPageStart() {

    }

    /**
     * webView 结束加载的时候调用
     */
    protected void onPageFinish() {
    }

    /*返回标题*/
    /*protected abstract int getWebTitle();*/
    /*子类根据需求实现自己的内容的方法  比如js交互映射类 或者WebChromeCline等*/
    protected abstract void setOther();

    /*h5网页链接*/
    protected abstract String getUrl();

    /*子类在URL后缀参数需要实现的方法*/
    protected abstract void addParams(@NonNull HashMap<String, String> map);

    /*是否拦截   默认不拦截*/
    protected boolean isInterceptRequest() {
        return false;
    }

    protected void doUrlOverride(WebView webView, String s) {
        webView.loadUrl(s);
    }


    /*子类根据需求实现自己的方法   可以根据bean中的method字段   仿postmessage（）方式*/
    protected void childUse(Bean bean) {

    }
}