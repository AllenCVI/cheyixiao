package com.autoforce.cheyixiao.customer;

import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerCarSystemBean;
import com.autoforce.cheyixiao.mvp.IPresenter;
import com.autoforce.cheyixiao.mvp.IView;

/**
 * Created by liujialei on 2018/11/20
 */
public class CustomerContract {


    interface Presenter extends IPresenter{

        void getData(int brand,int series,int state,int page);

        void getBrandData();

        void getCarSystem(int bid);

    }



    interface View extends IView<Presenter>{

        void setListDataView(CustomerBean data);

        void setListBrandView(CustomerBrandBean data);

        void setListCaySystemView(CustomerCarSystemBean data);

        void showLoadingView();

        void showNoDataView();

        void showNoContentView();

        void showNomalDataView();//显示正常数据

        void showNoNetWorkView();


        void finishRefresh();

        void finishLoadMore();

        boolean isrefreshing();

        boolean isloading();


    }




}
