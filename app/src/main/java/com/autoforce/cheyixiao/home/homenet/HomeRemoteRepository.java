package com.autoforce.cheyixiao.home.homenet;

import com.autoforce.cheyixiao.common.data.remote.NetFactory;
import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.home.bean.HomeRoot;

import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by liusilong on 2018/11/29.
 * version:1.0
 * Describe:
 */
public class HomeRemoteRepository {

    private static HomeRemoteRepository INSTANCE = new HomeRemoteRepository();
    private HomeServerApi homeServerApi;

    private HomeRemoteRepository() {
        homeServerApi = NetFactory.getRetrofit().create(HomeServerApi.class);
    }

    public static HomeRemoteRepository getInstance() {
        return INSTANCE;
    }

    /**
     * 获取首页数据
     *
     * @return
     */
    public Flowable<HomeRoot> getHomeData() {
        return homeServerApi.getHomeData();
    }

    //认证信息提交
    public Flowable<ApproveInfo> postApproveInfo(Map<String, Object> map) {
        return homeServerApi.postApproveInfo(map);
    }


}
