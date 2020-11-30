package com.autoforce.cheyixiao.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseDoubleTitleFragmentActivity;

public class MineBalanceActivity extends BaseDoubleTitleFragmentActivity {


    private int currentIndex;
    private int currentChildIndex;
    private MineBalanceFragment mineBalanceFragment;

    @Override
    protected int getCurrentIndex() {
        currentIndex = getIntent().getIntExtra("current", 0);
        currentChildIndex = getIntent().getIntExtra("currentChild", 0);
        return currentIndex;
    }

    @Override
    public int getRightTitle() {
        return R.string.car_resouce_order_lose;
    }

    @Override
    public int getLeftTitle() {
        return R.string.buy_car_order_lose;
    }

    @Override
    protected Fragment getLeftFragment() {
        mineBalanceFragment = new MineBalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("current" ,currentIndex);
        bundle.putInt("currentChild" ,currentChildIndex);
        mineBalanceFragment.setArguments(bundle);
        currentChildIndex = 0;
        return mineBalanceFragment;
    }

    @Override
    protected Fragment getRightFragment() {
        CarResouceOrderFragment carResouceOrderFragment = new CarResouceOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("current" ,currentIndex);
        bundle.putInt("currentChild" ,currentChildIndex);
        carResouceOrderFragment.setArguments(bundle);
        currentChildIndex = 0;
        return carResouceOrderFragment;
    }


}
