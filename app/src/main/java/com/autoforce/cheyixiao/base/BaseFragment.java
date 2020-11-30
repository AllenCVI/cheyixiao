package com.autoforce.cheyixiao.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.autoforce.cheyixiao.common.UMengStatistics;

/**
 * Created by xialihao on 2018/11/16.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    /**
     * 提供布局id
     *
     * @return 布局id
     */
    abstract protected int provideContentViewId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        initData();
    }


    protected abstract void initView(Bundle savedInstanceState);

    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UMengStatistics.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        UMengStatistics.onPageEnd(getClass().getSimpleName());
    }
}
