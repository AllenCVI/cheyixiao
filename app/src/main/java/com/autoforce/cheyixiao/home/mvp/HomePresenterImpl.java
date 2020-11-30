package com.autoforce.cheyixiao.home.mvp;

import android.util.Log;

import com.autoforce.cheyixiao.App;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import com.autoforce.cheyixiao.common.utils.JSONUtil;
import com.autoforce.cheyixiao.common.utils.NetUtils;
import com.autoforce.cheyixiao.home.HomeFragment;
import com.autoforce.cheyixiao.home.bean.HomeBanner;
import com.autoforce.cheyixiao.home.bean.HomeBrand;
import com.autoforce.cheyixiao.home.bean.HomeBrandInfo;
import com.autoforce.cheyixiao.home.bean.HomeHeaderBean;
import com.autoforce.cheyixiao.home.bean.HomeHotCar;
import com.autoforce.cheyixiao.home.bean.HomeRoot;
import com.autoforce.cheyixiao.home.bean.HomeService;
import com.autoforce.cheyixiao.home.homenet.HomeLocalRepository;
import com.autoforce.cheyixiao.home.homenet.HomeRemoteRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liusilong on 2018/11/21.
 * version:1.0
 * Describe:
 */
public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
    private HomeView view;
    private DefaultDisposableSubscriber<HomeRoot> defaultDisposableSubscriber;

    public HomePresenterImpl(HomeView view) {
        this.view = view;
    }

    /**
     * 从服务器获取数据，刷新 UI
     */
    @Override
    public void getData() {
        if (view != null) {
            view.showLoadingView();
        }
        final Flowable<HomeRoot> localData = HomeLocalRepository.getInstance().getHomeDataFromLocal().subscribeOn(Schedulers.io());
        final Flowable<HomeRoot> remoteData = HomeRemoteRepository.getInstance()
                .getHomeData()
                .doOnNext(new Consumer<HomeRoot>() {
                    @Override
                    public void accept(HomeRoot homeRoot) throws Exception {

                        String toJson = GsonProvider.gson().toJson(homeRoot);
                        Log.e(TAG, "accept: " + toJson);
                        SpUtils.getInstance().put(HomeLocalRepository.HOME_CACHE, toJson);
                    }
                })
                .subscribeOn(Schedulers.io());

        Flowable.concat(localData, remoteData)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<HomeRoot>() {
                    @Override
                    public boolean test(HomeRoot homeRoot) throws Exception {
                        return homeRoot != null && homeRoot.getResults() != null;
                    }
                })
                .subscribe(new DefaultDisposableSubscriber<HomeRoot>() {
                    @Override
                    protected void success(HomeRoot homeRoot) {
                        if (view != null) {
                            List<HomeBanner> banner = homeRoot.getResults().getBanner();
                            List<HomeHotCar> hotCars = homeRoot.getResults().getHotCars();
                            List<HomeService> services = homeRoot.getResults().getServices();
                            HomeHeaderBean homeHeaderBean = new HomeHeaderBean(banner, services, hotCars);

                            List<HomeBrand> brands = homeRoot.getResults().getBrands();
                            List<HomeBrandInfo> homeBrandInfos = new ArrayList<>();
                            int size = brands.size();
                            for (int i = 0; i < size; i++) {
                                homeBrandInfos.addAll(brands.get(i).getBrandInfos());
                            }
                            view.showNormalState();
                            view.setAdapter(homeHeaderBean, homeBrandInfos);
                        } else {
                            view.showNoDataState();
                        }
                    }

                    @Override
                    protected void failure(String errMsg) {
                        super.failure(errMsg);
                    }
                });


    }

    @Override
    public void clear() {
        if (defaultDisposableSubscriber != null) {
            defaultDisposableSubscriber.dispose();
        }
    }

}
