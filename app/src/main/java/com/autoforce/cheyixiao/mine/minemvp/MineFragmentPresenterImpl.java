package com.autoforce.cheyixiao.mine.minemvp;

import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.common.data.remote.bean.UserInfoBean;
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository;

public class MineFragmentPresenterImpl implements MineFragmentPresenter {
    private MineFragmentView mineFragmentView;
    private DefaultDisposableSubscriber<UserInfoBean> defaultDisposableSubscriber;

    public MineFragmentPresenterImpl(MineFragmentView mineFragmentView) {
        this.mineFragmentView = mineFragmentView;
    }


    @Override
    public void getInfo() {
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getUserInfo()
                .subscribeWith(new DefaultDisposableSubscriber<UserInfoBean>() {
                    @Override
                    protected void success(UserInfoBean data) {
                        mineFragmentView.showInfo(data);
                    }
                });
    }
}
