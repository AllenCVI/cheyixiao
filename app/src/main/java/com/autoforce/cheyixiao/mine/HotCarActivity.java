package com.autoforce.cheyixiao.mine;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseToolbarActivity;
import org.jetbrains.annotations.Nullable;
import com.autoforce.cheyixiao.mine.HotCarFragment;

public class HotCarActivity extends BaseToolbarActivity {



    @Override
    public Fragment userFragment() {
        HotCarFragment hotCarFragment = new HotCarFragment();
        return hotCarFragment;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.hot_car_suited;
    }
}
