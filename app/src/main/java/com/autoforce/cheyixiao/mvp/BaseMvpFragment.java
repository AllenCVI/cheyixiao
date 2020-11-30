package com.autoforce.cheyixiao.mvp;

import android.support.annotation.Nullable;
import com.autoforce.cheyixiao.base.BaseFragment;

/**
 * Created by xialihao on 2018/11/16.
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment implements IView<P> {

    @Nullable
    protected P mPresenter;


    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }




}
