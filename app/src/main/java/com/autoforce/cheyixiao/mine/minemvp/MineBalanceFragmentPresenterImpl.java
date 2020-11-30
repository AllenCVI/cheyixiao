package com.autoforce.cheyixiao.mine.minemvp;

import com.autoforce.cheyixiao.common.data.remote.bean.BlankCardInfo;
import com.autoforce.cheyixiao.common.data.remote.bean.CanMoneyBean;
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository;

public class MineBalanceFragmentPresenterImpl implements MineBalanceFragmentPresenter {
    private com.autoforce.cheyixiao.mine.minemvp.MineBalanceFragmentView mineBalanceFragmentView;
    private DefaultDisposableSubscriber<CanMoneyBean> defaultDisposableSubscriber;
    private DefaultDisposableSubscriber<BlankCardInfo> defaultDisposableSubscriber1;

    public MineBalanceFragmentPresenterImpl(MineBalanceFragmentView mineBalanceFragmentView) {
        this.mineBalanceFragmentView = mineBalanceFragmentView;
    }


    @Override
    public void getCanMoneyData() {//可提现
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getCanMoney()
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        mineBalanceFragmentView.showData(data);
                    }
                });
    }

    @Override
    public void getCaningMoneyData() {//提现中
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getCaningMoney()
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        mineBalanceFragmentView.showData(data);
                    }
                });
    }

    @Override
    public void getCanedMoneyData() {//已提现
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getCanedMoney()
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        mineBalanceFragmentView.showData(data);
                    }
                });
    }

    @Override
    public void getFrostCanMoneyData() {//冻结资金
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getFrostCanMoney()
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        mineBalanceFragmentView.showData(data);
                    }
                });
    }

    @Override
    public void getCanMoneyDefaultData() {//提现失败
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().getFrostCanMoney()
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        mineBalanceFragmentView.showData(data);
                    }
                });
    }

    /*提现  全部提现*/
    @Override
    public void CanMoney(String billId) {
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().postCanMoney(billId)
                .subscribeWith(new DefaultDisposableSubscriber<CanMoneyBean>() {

                    @Override
                    protected void success(CanMoneyBean data) {
                        getCanMoneyData();
                    }
                });
    }

    /*获取用户银行卡信息*/
    @Override
    public void getUserBlankCardInfo() {
        defaultDisposableSubscriber1 = MineRemoteRepository.getInstance().getUserBlankCardInfo()
                .subscribeWith(new DefaultDisposableSubscriber<BlankCardInfo>() {
                    @Override
                    protected void success(BlankCardInfo data) {
                        mineBalanceFragmentView.shoDialogData(data);
                    }
                });
    }


}
