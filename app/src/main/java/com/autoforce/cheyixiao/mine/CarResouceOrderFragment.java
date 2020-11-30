package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseFragment;
import com.autoforce.cheyixiao.common.data.remote.bean.BlankCardInfo;
import com.autoforce.cheyixiao.mine.adapter.BuyCarOrderMoneyAdapter;
import com.autoforce.cheyixiao.common.data.remote.bean.CanMoneyBean;
import com.autoforce.cheyixiao.mine.minemvp.MineBalanceFragmentPresenterImpl;
import com.autoforce.cheyixiao.mine.minemvp.MineBalanceFragmentView;

public class CarResouceOrderFragment extends BaseFragment implements MineBalanceFragmentView {
    @BindView(R.id.can_money)
    TextView canMoney;
    @BindView(R.id.caning_money)
    TextView caningMoney;
    @BindView(R.id.caned_money)
    TextView canedMoney;
    @BindView(R.id.can_money_default)
    TextView canMoneyDefault;
    @BindView(R.id.frost_can_money)
    TextView frostCanMoney;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.all_can_but)
    Button allCanBut;
    @BindView(R.id.child_content_view)
    RelativeLayout childContentView;
    Unbinder unbinder;

    private int currentChildIndex;
    private MineBalanceFragmentPresenterImpl mineBalanceFragmentPresenter;
    private int currentIndex;
    private BuyCarOrderMoneyAdapter buyCarOrderMoneyAdapter;
    private String billIds;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_car_resouce_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        mineBalanceFragmentPresenter = new MineBalanceFragmentPresenterImpl(this);
        initFragment();
        return rootView;
    }

    private void initFragment() {
        currentIndex = getArguments().getInt("current");
        currentChildIndex = getArguments().getInt("currentChild", 0);

        buyCarOrderMoneyAdapter = new BuyCarOrderMoneyAdapter();
        buyCarOrderMoneyAdapter.setInfos(null);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(buyCarOrderMoneyAdapter);

        switch (currentChildIndex){
            case 0:
                mineBalanceFragmentPresenter.getCanMoneyData();
                break;
            case 1:
                mineBalanceFragmentPresenter.getCaningMoneyData();
                break;
            case 2:
                mineBalanceFragmentPresenter.getCanedMoneyData();
                break;
            case 3:
                mineBalanceFragmentPresenter.getCanMoneyDefaultData();
                break;
            case 4:
                mineBalanceFragmentPresenter.getFrostCanMoneyData();
                break;
        }
    }

    public void reset() {
        canMoney.setSelected(false);
        caningMoney.setSelected(false);
        canedMoney.setSelected(false);
        canMoneyDefault.setSelected(false);
        frostCanMoney.setSelected(false);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current", currentIndex);
        outState.putInt("currentChild", currentChildIndex);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.can_money, R.id.caning_money, R.id.caned_money, R.id.can_money_default, R.id.frost_can_money, R.id.all_can_but})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.can_money:
                currentChildIndex = 0;
                mineBalanceFragmentPresenter.getCanMoneyData();
                break;
            case R.id.caning_money:
                currentChildIndex = 1;
                mineBalanceFragmentPresenter.getCaningMoneyData();
                break;
            case R.id.caned_money:
                currentChildIndex = 2;
                mineBalanceFragmentPresenter.getCanedMoneyData();
                break;
            case R.id.can_money_default:
                currentChildIndex = 3;
                mineBalanceFragmentPresenter.getCanMoneyDefaultData();
                break;
            case R.id.frost_can_money:
                currentChildIndex = 4;
                mineBalanceFragmentPresenter.getFrostCanMoneyData();
                break;
            case R.id.all_can_but:
                billIds = "0";
                //mineBalanceFragmentPresenter.CanMoney(billId);
                break;
        }
    }

    @Override
    public void showData(CanMoneyBean data) {
        reset();
        if (currentChildIndex == 0) {
            canMoney.setSelected(true);
        } else if (currentChildIndex == 1) {
            caningMoney.setSelected(true);
        } else if (currentChildIndex == 2) {
            canedMoney.setSelected(true);
        } else if (currentChildIndex == 3) {
            canMoneyDefault.setSelected(true);
        } else if (currentChildIndex == 4) {
            frostCanMoney.setSelected(true);
        }

        if(currentChildIndex == 0){
            allCanBut.setVisibility(View.VISIBLE);
        }else {
            allCanBut.setVisibility(View.GONE);
        }

        buyCarOrderMoneyAdapter.setInfos(data.getResults());

        buyCarOrderMoneyAdapter.setCurrentChild(currentChildIndex);
    }

    @Override
    public void shoDialogData(BlankCardInfo data) {

    }
}
