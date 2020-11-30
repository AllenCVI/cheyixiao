package com.autoforce.cheyixiao.customer.customersecondfragments;

import android.os.Bundle;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.mvp.BaseMvpFragment;

/**
 * Created by liujialei on 2018/11/30
 */
public class Fragment_Order_details extends BaseMvpFragment<Fragment_Order_details_Contract.Presenter> implements Fragment_Order_details_Contract.View {



    public static Fragment_Order_details newInstance() {
        return new Fragment_Order_details();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {









    }




}
