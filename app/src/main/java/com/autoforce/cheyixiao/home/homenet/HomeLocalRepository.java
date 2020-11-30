package com.autoforce.cheyixiao.home.homenet;

import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import com.autoforce.cheyixiao.common.utils.JSONUtil;
import com.autoforce.cheyixiao.home.bean.HomeRoot;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liusilong on 2018/11/29.
 * version:1.0
 * Describe:
 */
public class HomeLocalRepository {
    public static final String HOME_CACHE = "home_cache";
    private static HomeLocalRepository INSTANCE = new HomeLocalRepository();

    private HomeLocalRepository() {

    }

    public static HomeLocalRepository getInstance() {
        return INSTANCE;
    }

    public Flowable<HomeRoot> getHomeDataFromLocal() {
        String localData = SpUtils.getInstance().getString(HOME_CACHE);
        if (localData != null && JSONUtil.isJSONValid(localData)) {
            HomeRoot homeRoot = GsonProvider.gson().fromJson(localData, HomeRoot.class);
            return Flowable.create(new FlowableOnSubscribe<HomeRoot>() {
                @Override
                public void subscribe(FlowableEmitter<HomeRoot> emitter) throws Exception {
                    emitter.onNext(homeRoot);
                    if (!emitter.isCancelled()) {
                        emitter.onComplete();
                    }
                }
            }, BackpressureStrategy.BUFFER);
        }
        return Flowable.create(new FlowableOnSubscribe<HomeRoot>() {
            @Override
            public void subscribe(FlowableEmitter<HomeRoot> emitter) throws Exception {
                emitter.onNext(new HomeRoot());
                if (!emitter.isCancelled()) {
                    emitter.onComplete();
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
