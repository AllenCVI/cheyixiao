package com.autoforce.cheyixiao.mvp;
import android.support.annotation.Nullable;
import com.autoforce.cheyixiao.base.BaseActivity;

/**
 * Created by liujialei on 2018/11/21
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity implements IView<P> {


    @Nullable
    protected P mPresenter;


    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }


    }

}
