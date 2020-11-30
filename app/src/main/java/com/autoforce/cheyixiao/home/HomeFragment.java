package com.autoforce.cheyixiao.home;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import butterknife.BindView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseActivity;
import com.autoforce.cheyixiao.base.BaseFragment;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.update.UpdateUtil;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.common.view.PageStateView;
import com.autoforce.cheyixiao.home.act.HomeBrandActivity;
import com.autoforce.cheyixiao.home.act.HomeHotCarWebActivity;
import com.autoforce.cheyixiao.home.act.HomeWebActivity;
import com.autoforce.cheyixiao.home.adapter.BrandAdapter;
import com.autoforce.cheyixiao.home.adapter.HeaderAdapter;
import com.autoforce.cheyixiao.home.adapter.ItemClickListener;
import com.autoforce.cheyixiao.home.bean.*;
import com.autoforce.cheyixiao.home.mvp.HomePresenter;
import com.autoforce.cheyixiao.home.mvp.HomePresenterImpl;
import com.autoforce.cheyixiao.home.mvp.HomeView;

import me.yokeyword.indexablerv.IndexableLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Created by liusilong on 2018/11/15.
 */
public class HomeFragment extends BaseFragment implements HomeView, ItemClickListener {

    private static final String TAG = "HomeFragment";


    @BindView(R.id.index_able_layout)
    IndexableLayout indexableLayout;

    @BindView(R.id.page_state_view)
    PageStateView pageStateView;

    private HeaderAdapter headerAdapter;
    private BrandAdapter brandAdapter;

    private HomePresenter homePresenter;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();
        UpdateUtil updateUtil = new UpdateUtil(getContext(), HomeConstant.UPDATE_TEST_URL);
        homePresenter = new HomePresenterImpl(this);
        brandAdapter = new BrandAdapter();
        indexableLayout.setAdapter(brandAdapter);
        homePresenter.getData();
        updateUtil.checkForUpdate();

        ((BaseActivity) Objects.requireNonNull(getContext())).getLocationInfo(this, aMapLocation -> {
            Log.e(TAG, "initData: " + aMapLocation.getLatitude() + "\t" + aMapLocation.getLongitude());
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(Bundle savedInstanceState) {

        indexableLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        indexableLayout.setOverlayStyle_Center();

        pageStateView.bindView(indexableLayout);
        pageStateView.setOnClickRefreshListener(new PageStateView.OnclickRefreshListener() {
            @Override
            public void onClickRefresh() {
                if (homePresenter != null) {
                    homePresenter.getData();
                }
            }
        });
    }


    /**
     * 更新 Adapter 数据
     * <p>
     * 右侧index列表
     *
     * @param headerBean    头部数据
     * @param brandInfoList 列表数据
     */
    @Override
    public void setAdapter(HomeHeaderBean headerBean, List<HomeBrandInfo> brandInfoList) {
        // 避免多次添加 Header
        if (headerAdapter != null) {
            indexableLayout.removeHeaderAdapter(headerAdapter);
            headerAdapter = null;
        }
        headerAdapter = new HeaderAdapter("★", "", Arrays.asList(headerBean));
//        headerAdapter.setHeaderItemClickListener(this);
        headerAdapter.setItemClickListener(this);
        indexableLayout.addHeaderAdapter(headerAdapter);
        brandAdapter.setDatas(brandInfoList);
        brandAdapter.setItemClickListener(this);
//        bannerStart();
    }

    @Override
    public void showNoDataState() {
        pageStateView.showNoData();
    }

    @Override
    public void showErrorState() {
        pageStateView.showNoNetWork();
    }

    @Override
    public void showNormalState() {
        pageStateView.showNomalData();
    }

    @Override
    public void showLoadingView() {
        pageStateView.showLoadingView();
    }


    @Override
    public void onResume() {
        super.onResume();
        bannerStart();
    }


    private void bannerStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (headerAdapter != null) {
                    headerAdapter.bannerStart();
                }
            }
        }, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (headerAdapter != null) {
            headerAdapter.bannerPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null) {
            homePresenter.clear();
        }
    }

    // TODO: 2018/12/21  首页顶部 banner 需要和后台确定类型，有的 banner 是活动（跳转活动页面），有的是车型（跳转到全景页）
    @Override
    public void bannerOnclick(HomeBanner homeBanner) {
//        ToastUtil.showToast(url);
        //Log.e(TAG, "bannerOnclick: " + url);
        HomeBannerCar carInfo = homeBanner.getCarInfo();
        String imageUrl = homeBanner.getImageUrl();
        String brandUrl = homeBanner.getBrandUrl();
        HomeHotCar car = new HomeHotCar(carInfo, imageUrl, brandUrl);

        HomeHotCarWebActivity.start(getContext(), car);
    }

    @Override
    public void serviceOnClick(HomeService homeService) {
        String url = null;
        switch (homeService.getName()) {
            case HomeConstant.INSURANCE:
                url = HomeConstant.INSURANCE_URL;
                break;
            case HomeConstant.GIVE_ORDER:
                url = HomeConstant.GIVE_ORDER_URL;
                break;
            case HomeConstant.FINANCIAL:
                url = HomeConstant.FINANCIAL_URL;
                break;
            case HomeConstant.LOGISTICS:
                url = HomeConstant.LOGISTICS_RUL;
                break;
        }
        HomeWebActivity.start(getContext(), url);
    }

    @Override
    public void hotCarOnClick(HomeHotCar car) {
        HomeHotCarWebActivity.start(getContext(), car);
    }

    @Override
    public void brandonClick(HomeBrandInfo homeBrandInfo) {
        HomeBrandActivity.start(getContext(), homeBrandInfo);
    }

}
