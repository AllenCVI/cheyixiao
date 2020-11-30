package com.autoforce.cheyixiao.customer.network;

import com.autoforce.cheyixiao.common.data.remote.NetFactory;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerCarSystemBean;
import io.reactivex.Flowable;

/**
 * @author xlh
 * @date 2018/9/25.
 * 网络数据仓库，对外统一代理类
 */
public class CustomerRemoteRepository {

    private static CustomerRemoteRepository INSTANCE = new CustomerRemoteRepository();
    private final CustomerServerApi serverApi;

    public static CustomerRemoteRepository getInstance() {
        return INSTANCE;
    }

    private CustomerRemoteRepository() {
        serverApi = NetFactory.getRetrofit().create(CustomerServerApi.class);
    }

    /**
     * 客户列表
     * @return
     */
    public Flowable<CustomerBean> getCustomerList(int brand,int series,int state,int page) {
        return serverApi.getCustomerList(brand,series,state,page);
    }


    /**
     * 客户车型
     * @return
     */
    public Flowable<CustomerBrandBean> getBrandList(){
        return serverApi.getBrandList();
    }


    /**
     * 客户车系
     *
     */

    public Flowable<CustomerCarSystemBean> getCarSystemList(int bid){
        return serverApi.getCarSysytemList(bid);
    }



}
