package com.autoforce.cheyixiao;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.autoforce.cheyixiao.base.BaseConstant;
import com.autoforce.cheyixiao.base.CommonX5WebViewInterceptActivity;
import com.autoforce.cheyixiao.car.source.me.MyCarSourceAct;
import com.autoforce.cheyixiao.common.BuglyReport;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.login.LoginActivity;
import com.autoforce.cheyixiao.mine.minegloab.MineGloab;
import com.autoforce.cheyixiao.mine.web2.ApproveInfoWebActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xialihao on 2018/11/15.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        sInstance = this;

        //初始化友盟phsh
        initUmengPush();
        UMengStatistics.init(getApplicationContext() , null);
        //初始化Bugly
        BuglyReport.getInstance(this).init();

        initLogger(BuildConfig.DEBUG);
//        initLogger(false);

        initX5WebView();

        // 品牌多音字处理
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<>();
                        map.put("长安", new String[]{"CHANG", "AN"});
                        map.put("蔚来", new String[]{"WEI", "LAI"});
                        return map;
                    }
                }));
    }

    private void initUmengPush() {

        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,BaseConstant.UMENGPUSHSCRET);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token



            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });


        UmengMessageHandler umengMessageHandler = new UmengMessageHandler(){

            /**
             * 处理以自定义方式发送消息时回调的方法
             * @param context
             * @param uMessage
             */
            @Override
            public void dealWithCustomMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
            }


            /**
             * 处理以通知的方式发送消息时的回调方法（通知送达时会回调）
             * @param context
             * @param uMessage
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
            }

        };

        mPushAgent.setMessageHandler(umengMessageHandler);

        UmengNotificationClickHandler umengNotificationClickHandler = new UmengNotificationClickHandler(){

            /**
             * 自定义点击通知栏的行为
             * @param context
             * @param uMessage
             */
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {


                String token = LocalRepository.getInstance().getToken();

                if(token==null||token.equals("")){
                    LoginActivity.start(context);
                    return;
                }

                String custom = uMessage.custom;

                if(custom==null||custom.equals("")){
                    MainActivity.start(context);
                    return;
                }

                if(custom.equals("驳回")){
                    Intent intent = new Intent(context,ApproveInfoWebActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String url = BaseConstant.BASEURL+MineGloab.IDENFTIFY;
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }else if(custom.equals("购车签约")||custom.equals("定金担保")){
                    String url = BaseConstant.BASEURL + MineGloab.OPTION_ORDERS;
                    Intent intent = new Intent(context,CommonX5WebViewInterceptActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }else if(custom.equals("我发布的车源被预定")){
                    Intent intent = new Intent(context,MyCarSourceAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else if(custom.equals("我发布的寻车已收到报价")){
                    MainActivity.start(context,BaseConstant.CARFRAGMENT);
                }else if(custom.equals("购车订单余额提现")||custom.equals("车源订单余额提现")){
                    String url = BaseConstant.BASEURL + MineGloab.MY_BALANUCE;
                    Intent intent = new Intent(context,CommonX5WebViewInterceptActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                }else {
                    MainActivity.start(context,BaseConstant.HOMEFRAGMENT);
                }
            }
        };

        mPushAgent.setNotificationClickHandler(umengNotificationClickHandler);

    }

    /*初始化腾讯x5内核*/
    private void initX5WebView() {

        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }


    public static App getInstance() {
        return sInstance;
    }

    private void initLogger(boolean loggable) {

        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(0)
                .methodCount(2)
                .tag("cheyixiao")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return loggable;
            }
        });

    }

    // for SmartRefreshLayout
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter footer = new ClassicsFooter(context).setDrawableSize(20);
                footer.setFinishDuration(0);
                return footer;
            }
        });

        // 测试人员提出的需求
        ClassicsFooter.REFRESH_FOOTER_FINISH = "";

    }
}
