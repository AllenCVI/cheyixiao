package com.autoforce.cheyixiao.mine.minemvp;


import java.util.ArrayList;
import java.util.List;

public class BuyOrderFragmentPresenterImpl implements BuyOrderFragmentPresenter {

    private BuyOrderFragmentView buyOrderFragmentView;
    private List<String> list = new ArrayList<>();

    public BuyOrderFragmentPresenterImpl(BuyOrderFragmentView buyOrderFragmentView) {
        this.buyOrderFragmentView = buyOrderFragmentView;
    }

    @Override
    public void getData(String salerId, int page) {
        for(int i = 0 ; i < 10 ; i++){
            list.add("购车订单" + i);
        }
        buyOrderFragmentView.showData(list ,page);
    }
}
