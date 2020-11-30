package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseDoubleTitleFragmentActivity;

public class CarResouceOrderActivity extends BaseDoubleTitleFragmentActivity {


    @Override
    protected int getCurrentIndex() {
        return 0;
    }

    @Override
    public int getRightTitle() {
        return R.string.stork_remove_order;
    }

    @Override
    public int getLeftTitle() {
        return R.string.buy_order;
    }

    @Override
    protected Fragment getLeftFragment() {
        BuyOrderFragment buyOrderFragment = new BuyOrderFragment();
        Bundle bundle = new Bundle();
        buyOrderFragment.setArguments(bundle);
        return buyOrderFragment;
    }

    @Override
    protected Fragment getRightFragment() {
        StorkRemoveOrderFragment storkRemoveOrderFragment = new StorkRemoveOrderFragment();
        Bundle bundle = new Bundle();
        storkRemoveOrderFragment.setArguments(bundle);
        return storkRemoveOrderFragment;
    }
}
