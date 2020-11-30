package com.autoforce.cheyixiao.mine.minenet;

import com.autoforce.cheyixiao.common.data.remote.NetFactory;
import com.autoforce.cheyixiao.common.data.remote.bean.*;
import io.reactivex.Flowable;

import java.util.Map;

/**
 * @author xlh
 * @date 2018/9/25.
 * 网络数据仓库，对外统一代理类
 */
public class MineRemoteRepository {

    private static MineRemoteRepository INSTANCE = new MineRemoteRepository();
    private final MineServerApi serverApi;

    public static MineRemoteRepository getInstance() {
        return INSTANCE;
    }

    private MineRemoteRepository() {
        serverApi = NetFactory.getRetrofit().create(MineServerApi.class);
    }


    //可提现列表
    public Flowable<CanMoneyBean> getCanMoney() {
        return serverApi.getCanMoney();
    }

    //已提现列表
    public Flowable<CanMoneyBean> getCanedMoney() {

        return serverApi.getCaedMoney();
    }

    //提现中列表
    public Flowable<CanMoneyBean> getCaningMoney() {
        return serverApi.getCaingMoney();
    }

    //提现失败列表
    public Flowable<CanMoneyBean> getFrostCanMoney() {
        return serverApi.getFrostCanMoney();
    }

    //资金冻结列表
    public Flowable<CanMoneyBean> postCanMoney(String sillId) {
        return serverApi.postCanMoney(sillId);
    }

    //获取用户信息
    public Flowable<UserInfoBean> getUserInfo() {
        return serverApi.getUserInfo();
    }

    //获取用户银行卡信息
    public Flowable<BlankCardInfo> getUserBlankCardInfo(){
        return serverApi.getUserBlankCardInfo();
    }

    //认证信息提交
    public Flowable<ApproveInfo> postApproveInfo(Map<String , Object> map){
        return serverApi.postApproveInfo(map);
    }
}
