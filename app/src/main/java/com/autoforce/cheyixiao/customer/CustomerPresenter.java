package com.autoforce.cheyixiao.customer;

import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerCarSystemBean;
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.customer.network.CustomerRemoteRepository;
import com.autoforce.cheyixiao.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

/**
 * Created by liujialei on 2018/11/20
 */
public class CustomerPresenter extends BasePresenter<CustomerContract.View> implements CustomerContract.Presenter{


    public CustomerPresenter(CustomerContract.View view) {
        super(view);
    }


    @Override
    public void getData(int brand,int series,int state,int page) {

        if(!mRootView.get().isloading()&&!mRootView.get().isrefreshing()){
            mRootView.get().showLoadingView();
        }


        addDispose(CustomerRemoteRepository.getInstance().getCustomerList(brand,series,state,page).subscribeWith(new DefaultDisposableSubscriber<CustomerBean>() {
            @Override
            protected void success(CustomerBean data) {




                mRootView.get().finishLoadMore();
                mRootView.get().finishRefresh();

                if(data==null){
                    mRootView.get().showNoDataView();
                    return;
                }else if(data.getResults().size()<=0){
                    mRootView.get().showNoContentView();
                }else {
                    mRootView.get().setListDataView(data);
                    mRootView.get().showNomalDataView();
                }
            }

            @Override
            protected void failure(String errMsg) {
                super.failure(errMsg);
                mRootView.get().finishLoadMore();
                mRootView.get().finishRefresh();
                mRootView.get().showNoDataView();
                Logger.i(errMsg);
            }
        }));
    }

    @Override
    public void getBrandData() {

         addDispose(CustomerRemoteRepository.getInstance().getBrandList().subscribeWith(new DefaultDisposableSubscriber<CustomerBrandBean>(){


             @Override
             protected void success(CustomerBrandBean data) {

                  if (data==null){return;}
                  mRootView.get().setListBrandView(data);

             }

             @Override
             protected void failure(String errMsg) {
                 super.failure(errMsg);
                 Logger.i(errMsg);

             }
         }));



    }

    @Override
    public void getCarSystem(int bid) {

        addDispose(CustomerRemoteRepository.getInstance().getCarSystemList(bid).subscribeWith(new DefaultDisposableSubscriber<CustomerCarSystemBean>(){


            @Override
            protected void success(CustomerCarSystemBean data) {

                if (data==null){return;}
                mRootView.get().setListCaySystemView(data);
            }

            @Override
            protected void failure(String errMsg) {
                super.failure(errMsg);
                Logger.i(errMsg);

            }
        }));




    }


}
