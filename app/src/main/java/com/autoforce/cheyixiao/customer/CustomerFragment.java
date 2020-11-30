package com.autoforce.cheyixiao.customer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerCarSystemBean;
import com.autoforce.cheyixiao.common.utils.DeviceUtil;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import com.autoforce.cheyixiao.common.utils.NetUtils;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.common.view.AutoForceRefreshLayout;
import com.autoforce.cheyixiao.common.view.MyPopupWindow;
import com.autoforce.cheyixiao.common.view.PageStateView;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;
import com.autoforce.cheyixiao.customer.customersecondh5.CustomerSecondH5Activity;
import com.autoforce.cheyixiao.customer.mycommon.CustomerConstant;
import com.autoforce.cheyixiao.mvp.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;


public class CustomerFragment extends BaseMvpFragment<CustomerContract.Presenter> implements CustomerContract.View {

    @BindView(R.id.custom_RecyclerView)
    RecyclerView custom_RecyclerView;
    private CustomerListAdapter customerListAdapter;
    @BindView(R.id.iv_brandlistmark)
    ImageView iv_brandlistmark;
    @BindView(R.id.tv_Bradn)
    TextView tv_Bradn;
    @BindView(R.id.lin_popdown)
    LinearLayout lin_popdown;
    @BindView(R.id.tv_titlebar)
    TextView tv_titlebar;
    @BindView(R.id.refresh_layout)
    AutoForceRefreshLayout refresh_layout;
    @BindView(R.id.tv_carSystem)
    TextView tv_carSystem;
    @BindView(R.id.iv_vehicellistmark)
    ImageView iv_vehicellistmark;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.iv_statellistmark)
    ImageView iv_statellistmark;
    @BindView(R.id.emptyView)
    PageStateView emptyView;
    private long clickTime=0;

    private int bid = 0;//0为全部
    private int cid = 0;//0为全部
    private int page = 1;//第一页
    private int state = 0;//全部

    private PopupWindow popupWindow;
    private BrandRecyclerViewAdapter brandBrandRecyclerViewAdapter;
    private View RecyclerContentView;
    private RecyclerView popwindowRecyclerView;
    private CaySystemRecyclerViewAdapter caySystemRecyclerViewAdapter;
    private int total;
    private List<CustomerStateBean> stateList;
    private CustomerStateRecyclerViewAdapter stateAdapter;


    public static CustomerFragment newInstance() {
        return new CustomerFragment();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.frag_customer;
    }

    Context context;

    @Override
    protected void initView(Bundle savedInstanceState) {

        context = getContext();
        initEmptyView();
        judgeNetWork();
        initReycyclerView();
        initPopupWindowRecyclerView();
        initBrandAdapter();
        initStateAdapter();
        initPopupWindow();
        initCarSystemAdapter();
        initRrefresh_layout();

    }

    /**
     * 初始化空布局
     */
    private void initEmptyView() {

        emptyView.bindView(custom_RecyclerView);
        emptyView.setOnClickRefreshListener(new PageStateView.OnclickRefreshListener() {
            @Override
            public void onClickRefresh() {

                if(!judgeNetWork()){
                    showNoNetWorkView();
                    return;
                }

                mPresenter.getData(bid,cid,state,page);

            }
        });

    }

    /**
     * 判断网络
     * @return
     */
    private boolean judgeNetWork() {

        boolean connected = NetUtils.isNetworkConnected(context);
        if(!connected){
            netWarn();
        }
        return connected;
    }

    private void netWarn(){
        ToastUtil.showToast(R.string.warnning);
    }

    /**
     * 初始化刷新布局
     */
    private void initRrefresh_layout() {
        int itemNum = 10;

        refresh_layout.setAutoForceRefreshListener(new AutoForceRefreshLayout.OnAutoForceRefreshListener() {
            @Override
            public void onRefresh(AutoForceRefreshLayout autoForceRefreshLayout) {

                if(!judgeNetWork()){
                    refresh_layout.finishAutoForceRefresh();
                    return;
                }

                //刷新时就回到当前选择的第一页
                page = 1;
                mPresenter.getData(bid, cid, state, page);
//              refresh_layout.setNoMoreData(false);
                refresh_layout.setAutoForceNoMoreData(false);
            }

            @Override
            public void onLoadMore(AutoForceRefreshLayout autoForceRefreshLayout) {

                if(!judgeNetWork()){
                    refresh_layout.finishAutoForceLoadMore();
                    return;
                }

                //通过返回的总数去判断有几页，在加载更多的时候进行判断
                int Remainder = total%itemNum;
                int totalPage;
                if(Remainder!=0){
                    totalPage  = total/itemNum+1;
                }else {
                    totalPage = total/itemNum;
                }

                if(page < totalPage ){
                    page++;
                }else {
                    refresh_layout.finishAutoForceLoadMoreWithNoMoreData();
                    return;
                }

                mPresenter.getData(bid,cid,state,page);

            }
        });

    }

    /**
     * 初始化popupwindow的recycleview
     */
    private void initPopupWindowRecyclerView() {
        RecyclerContentView = getLayoutInflater().inflate(R.layout.recyclerview_popwindow, null, false);
        popwindowRecyclerView = RecyclerContentView.findViewById(R.id.recyclerview);
        popwindowRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        popupWindow = MyPopupWindow.getInstance(context, RecyclerContentView);
        popupWindow.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        //        popupWindow.setHeight(DeviceUtil.getScreenHeight(context) -tv_titlebar.getMeasuredHeight()-lin_popdown.getMeasuredHeight()-DeviceUtil.getStatusBarHeight(context)-10);
        //        Logger.i("tv_titlebar:"+tv_titlebar.getMeasuredHeight()+"/n"+"lin_popdown:"+lin_popdown.getMeasuredHeight());

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                int color = getResources().getColor(R.color.d0021b);

                //取消的时候应该动那个红色的，所以通过红色去判断
                 if(color == tv_Bradn.getCurrentTextColor()){
                     ObjectAnimator animator = ObjectAnimator.ofFloat(iv_brandlistmark, "rotation", -180f, 0f);
                     animator.setDuration(500);
                     animator.start();
                     tv_Bradn.setTextColor(getResources().getColor(R.color.black3));
                 }


                 if(color == tv_carSystem.getCurrentTextColor()){
                     ObjectAnimator animator = ObjectAnimator.ofFloat(iv_vehicellistmark, "rotation", -180f, 0f);
                     animator.setDuration(500);
                     animator.start();
                     tv_carSystem.setTextColor(getResources().getColor(R.color.black3));
                 }


                 if(color == tv_state.getCurrentTextColor()){

                     ObjectAnimator animator = ObjectAnimator.ofFloat(iv_statellistmark, "rotation", -180f, 0f);
                     animator.setDuration(500);
                     animator.start();
                     tv_state.setTextColor(getResources().getColor(R.color.black3));

                 }

            }
        });

    }

    /**
     * 初始化装状态的recyclerview的adapter
     */
    private void initStateAdapter() {

        stateList = new ArrayList<>();
        String[] stateTextArr = getResources().getStringArray(R.array.states);
        String[] stateNumArr = getResources().getStringArray(R.array.stateNum);

        for (int i=0;i<stateNumArr.length;i++){

            stateList.add(new CustomerStateBean(stateTextArr[i],Integer.parseInt(stateNumArr[i])));

        }

        stateAdapter = new CustomerStateRecyclerViewAdapter();
        stateAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

                if(!judgeNetWork()){
                    return;
                }

                //每次切换状态都会重新请求对应状态的第一页
                page = 1;
                refresh_layout.setAutoForceNoMoreData(false);
                CustomerStateBean bean = (CustomerStateBean) data;
                state = bean.getStateNum();
                mPresenter.getData(bid,cid,state,page);
                stateAdapter.setState(state);

                popupWindow.dismiss();

                if(state==0){
                    tv_state.setText(getResources().getString(R.string.state));
                }else {
                    tv_state.setText(bean.getStateText());
                }
            }
        });

    }

    /**
     * 初始化车系的recyclerview的adapter
     */
    private void initCarSystemAdapter() {

        caySystemRecyclerViewAdapter = new CaySystemRecyclerViewAdapter();
//        popwindowRecyclerView.setAdapter(caySystemRecyclerViewAdapter);
        caySystemRecyclerViewAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

                if(!judgeNetWork()){
                    return;
                }
                //每次切换车系都会重新请求对应车系的第一页数据
                page = 1;
                refresh_layout.setAutoForceNoMoreData(false);
                String name = ((CustomerCarSystemBean.Result)data).getName();
                cid = ((CustomerCarSystemBean.Result)data).getId();
                mPresenter.getData(bid,cid,state,page);
                caySystemRecyclerViewAdapter.setIndex(cid);

                popupWindow.dismiss();

                if(cid==0){

                    tv_carSystem.setText(getResources().getString(R.string.car_type));

                }else {

                    tv_carSystem.setText(name);
                }

            }
        });

    }


    /**
     * 初始化品牌的recyclerview的adapter
     */
    private void initBrandAdapter() {

        brandBrandRecyclerViewAdapter = new BrandRecyclerViewAdapter();
        brandBrandRecyclerViewAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if(!judgeNetWork()){
                    return;
                }
                //每次切换品牌都会把车系状态变成0（全部），然后请求对应品牌的第一页
                page = 1;
                refresh_layout.setAutoForceNoMoreData(false);
                tv_carSystem.setText(getResources().getString(R.string.car_type));
                cid = 0;
                String bname = ((CustomerBrandBean.Result) data).getBname();
                bid = ((CustomerBrandBean.Result) data).getBid();
                brandBrandRecyclerViewAdapter.setIndex(bid);
                popupWindow.dismiss();
                mPresenter.getData(bid,cid,state,page);
                if(bid==0){
                    tv_Bradn.setText(getResources().getString(R.string.brand));
                }else {
                    tv_Bradn.setText(bname);
                }

            }
        });

    }


    /**
     * 分别点击品牌、车系、状态的事件分发
     * @param v
     */
    @OnClick({R.id.lin_brand, R.id.lin_vehicelistmark, R.id.lin_state})
    public void clickView(View v) {

        switch (v.getId()) {

            case R.id.lin_brand:

                getBrandData();

                break;


            case R.id.lin_vehicelistmark:

                getVihicelSystem();

                break;

            case R.id.lin_state:
                getState();

                break;
        }


    }


    /**
     * 点击车系请求车系接口内容
     */
    private void getVihicelSystem() {
        if(!judgeNetWork()){
            return;
        }

        if (bid == 0) {
            ToastUtil.showToast(R.string.warn);
            return;
        }

        mPresenter.getCarSystem(bid);
        tv_carSystem.setTextColor(getResources().getColor(R.color.d0021b));
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_vehicellistmark, "rotation", 0f, -180f);
        animator.setDuration(500);
        animator.start();


    }

    /**
     * 点击品牌请求品牌接口内容
     */
    private void getBrandData() {
        if(!judgeNetWork()){
            return;
        }

        mPresenter.getBrandData();
        tv_Bradn.setTextColor(getResources().getColor(R.color.d0021b));
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_brandlistmark, "rotation", 0f, -180f);
        animator.setDuration(500);
        animator.start();

    }

    /**
     * 点击状态获取内容
     */
    private void getState() {

        if(!judgeNetWork()){
            return;
        }
        setStateView();
        tv_state.setTextColor(getResources().getColor(R.color.d0021b));
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_statellistmark, "rotation", 0f, -180f);
        animator.setDuration(500);
        animator.start();

    }


    @Override
    protected void initData() {
        new CustomerPresenter(this);
        CustomerBean data;
        if(!judgeNetWork()){
            String dataJson = SpUtils.getInstance().getString(CustomerConstant.CUSTOMER_CASH);
            if(dataJson==null||dataJson.equals("")){
                showNoNetWorkView();
                return;
            }else {
                data = GsonProvider.gson().fromJson(dataJson,CustomerBean.class);
                setListDataView(data);
            }

        }else {
            mPresenter.getData(bid,cid,state,page);
        }


    }


    /**
     * 初始化大列表
     */
    private void initReycyclerView() {

        customerListAdapter = new CustomerListAdapter(context);
        custom_RecyclerView.setLayoutManager(new LinearLayoutManager(context));
        custom_RecyclerView.setAdapter(customerListAdapter);


        customerListAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

                if(!judgeNetWork()){
                    return;
                }

                long currenttime = System.currentTimeMillis();

                if(currenttime - clickTime < 1000){
                    return;
                }

                clickTime = currenttime;
                CustomerBean.Result result = (CustomerBean.Result)data;
                Intent intent = new Intent(context, CustomerSecondH5Activity.class);
                intent.putExtra(CustomerConstant.CUSTOMER_USER_ID, result.getUser_id()+"");
                getActivity().startActivityForResult(intent,CustomerConstant.TOCOUSTOMERDETAILLREQUESTCODI);
                UMengStatistics.statisticEventNumber(CustomerConstant.EVENT_CUSTOMER_MANAGEMENT);

//                CustomerSecondActivity.start(context);



            }
        });
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==CustomerConstant.TOCOUSTOMERDETAILLREQUESTCODI){

            if(!judgeNetWork()){
                showNoNetWorkView();
                return;
            }

            mPresenter.getData(bid,cid,state,page);

        }
    }

    //大列表的数据
    @Override
    public void setListDataView(CustomerBean data) {

        if (data != null) {
            setData(data);
        }
    }


    /**
     * 展示点击状态弹出的popupwindow中的recycleview
     *
     */
    private void setStateView() {

        popwindowRecyclerView.setAdapter(stateAdapter);
        popupWindow.setHeight(DeviceUtil.getScreenHeight(context) - tv_titlebar.getHeight() - lin_popdown.getHeight() - DeviceUtil.getStatusBarHeight(context) - 10);
        popupWindow.showAsDropDown(lin_popdown, 0, DeviceUtil.dip2px(context, 10));
        stateAdapter.setInfos(stateList);


    }



    /**
     * 展示点击品牌弹出的popupwindow中的recycleview
     * @param data
     */
    @Override
    public void setListBrandView(CustomerBrandBean data) {

        if (data != null) {
            popwindowRecyclerView.setAdapter(brandBrandRecyclerViewAdapter);
            popupWindow.setHeight(DeviceUtil.getScreenHeight(context) - tv_titlebar.getHeight() - lin_popdown.getHeight() - DeviceUtil.getStatusBarHeight(context) - 10);
            popupWindow.showAsDropDown(lin_popdown, 0, DeviceUtil.dip2px(context, 10));
            brandBrandRecyclerViewAdapter.setInfos(data.getResult());

        }
    }


    /**
     * 展示点击车系弹出的popupwindow中的recycleview
     * @param data
     */
    @Override
    public void setListCaySystemView(CustomerCarSystemBean data) {

        if (data != null) {

            popwindowRecyclerView.setAdapter(caySystemRecyclerViewAdapter);
            popupWindow.setHeight(DeviceUtil.getScreenHeight(context) - tv_titlebar.getHeight() - lin_popdown.getHeight() - DeviceUtil.getStatusBarHeight(context) - 10);
            popupWindow.showAsDropDown(lin_popdown, 0, DeviceUtil.dip2px(context, 10));
            caySystemRecyclerViewAdapter.setInfos(data.getResult());

        }

    }

    @Override
    public void showLoadingView() {

        emptyView.showLoadingView();

    }

    @Override
    public void showNoDataView() {

        emptyView.showNoData();

    }

    @Override
    public void showNoContentView() {

        emptyView.showNoContent();

    }

    @Override
    public void showNomalDataView() {

        emptyView.showNomalData();

    }

    @Override
    public void showNoNetWorkView() {

        emptyView.showNoNetWork();

    }

    @Override
    public void finishRefresh() {
        if(refresh_layout.isAutoForceRefreshing()) {
            refresh_layout.finishAutoForceRefreshNodelayTime();
        }
    }

    @Override
    public void finishLoadMore() {

        if(refresh_layout.isAutoForceLoading()) {
            refresh_layout.finishAutoForceLoadMoreNodelayTime();
        }

    }

    @Override
    public boolean isrefreshing() {
        return refresh_layout.isAutoForceRefreshing();
    }

    @Override
    public boolean isloading() {
        return refresh_layout.isAutoForceLoading();
    }


    private void setData(CustomerBean data) {

        if (data != null) {

            //每次请求所有品牌、车系、状态时缓存最新信息
            if(judgeNetWork()&&bid==0&&cid==0&&state==0&&page==1){
                String json = GsonProvider.gson().toJson(data);
                SpUtils.getInstance().put(CustomerConstant.CUSTOMER_CASH,json);
            }

            total = data.getTotal();

            if(page==1){
                customerListAdapter.setInfos(data.getResults());
            }else {
                customerListAdapter.appendInfos(data.getResults());
            }


        }


    }


}
