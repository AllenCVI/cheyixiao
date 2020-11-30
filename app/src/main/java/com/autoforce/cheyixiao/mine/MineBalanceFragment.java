package com.autoforce.cheyixiao.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.autoforce.cheyixiao.MainActivity;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseFragment;
import com.autoforce.cheyixiao.common.data.remote.bean.BlankCardInfo;
import com.autoforce.cheyixiao.common.data.remote.bean.CanMoneyBean;
import com.autoforce.cheyixiao.common.utils.DialogUtil;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;
import com.autoforce.cheyixiao.mine.adapter.BuyCarOrderMoneyAdapter;
import com.autoforce.cheyixiao.mine.minemvp.MineBalanceFragmentPresenterImpl;
import com.autoforce.cheyixiao.mine.minemvp.MineBalanceFragmentView;

import java.util.List;
import java.util.logging.Logger;

public class MineBalanceFragment extends BaseFragment implements MineBalanceFragmentView {

    @BindView(R.id.can_money)
    TextView canMoney;
    @BindView(R.id.caning_money)
    TextView caningMoney;
    @BindView(R.id.caned_money)
    TextView canedMoney;
    @BindView(R.id.can_money_default)
    TextView canMoneyDefault;

    Unbinder unbinder;

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.all_can_but)
    Button allCanBut;

    private int currentIndex;
    private int currentChildIndex;
    private MineBalanceFragmentPresenterImpl mineBalanceFragmentPresenter;
    private BuyCarOrderMoneyAdapter buyCarOrderMoneyAdapter;
    private String billIds;//提现参数 //全部提现为0；
    private MineBalanceActivity activity;



    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mine_balance;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        currentIndex = getArguments().getInt("current");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current", currentIndex);
        outState.putInt("currentChild", currentChildIndex);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MineBalanceActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        mineBalanceFragmentPresenter = new MineBalanceFragmentPresenterImpl(this);
        boolean b = activity.isFinishing();
        initFragment();
        return rootView;
    }

    private void initFragment() {
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
        }

        buyCarOrderMoneyAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

            }
        });

        buyCarOrderMoneyAdapter.setOnCashMoneyTextClickLinstener(new BuyCarOrderMoneyAdapter.OnCashMoneyTextClickLinstener() {//item 提现
            @Override
            public void click(String billId) {
//                mineBalanceFragmentPresenter.CanMoney(billId);
                billIds = billId;
                mineBalanceFragmentPresenter.getUserBlankCardInfo();
            }
        });

    }

    public void reset(){
        canMoney.setSelected(false);
        caningMoney.setSelected(false);
        canedMoney.setSelected(false);
        canMoneyDefault.setSelected(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.can_money, R.id.caning_money, R.id.caned_money, R.id.can_money_default ,R.id.all_can_but})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.can_money://可提现
                currentChildIndex = 0;
                mineBalanceFragmentPresenter.getCanMoneyData();
                break;
            case R.id.caning_money://提现中
                currentChildIndex = 1;
                mineBalanceFragmentPresenter.getCaningMoneyData();
                break;
            case R.id.caned_money://已提现
                currentChildIndex = 2;
                mineBalanceFragmentPresenter.getCanedMoneyData();
                break;
            case R.id.can_money_default://提现失败
                currentChildIndex = 3;
                mineBalanceFragmentPresenter.getCanMoneyDefaultData();
                break;
            case R.id.all_can_but://全部提现
                mineBalanceFragmentPresenter.getUserBlankCardInfo();
                billIds = "0";
//                mineBalanceFragmentPresenter.CanMoney(billId);
                break;

        }
    }


    @Override
    public void showData(CanMoneyBean data) {
        reset();
        if(currentChildIndex == 0){
            canMoney.setSelected(true);
        }else if(currentChildIndex == 1){
            caningMoney.setSelected(true);
        }else if(currentChildIndex == 2){
            canedMoney.setSelected(true);
        }else if(currentChildIndex == 3){
            canMoneyDefault.setSelected(true);
        }

        if(currentChildIndex == 0){
            allCanBut.setVisibility(View.VISIBLE);
        }else {
            allCanBut.setVisibility(View.GONE);
        }
        List<CanMoneyBean.ResultsBean> results = data.getResults();
        if(results!=null && results.size()>0){
            for (CanMoneyBean.ResultsBean res : results){
                res.setClick_enable(true);
            }
        }

        buyCarOrderMoneyAdapter.setInfos(data.getResults());

        buyCarOrderMoneyAdapter.setCurrentChild(currentChildIndex);

    }

    @Override
    public void shoDialogData(BlankCardInfo data) {

        if(data!=null){
            View view = getLayoutInflater().inflate(R.layout.dialog_blank_view, null);
            TextView msgText = view.findViewById(R.id.msg);
            TextView blankUserName = view.findViewById(R.id.blank_user_name);
            TextView blankNumber = view.findViewById(R.id.blank_number);
            TextView blanCity = view.findViewById(R.id.blank_city);
            TextView blankStyle = view.findViewById(R.id.blank_style);
            TextView blankName = view.findViewById(R.id.blank_name);

            msgText.setText(data.getMsg());
            if(data.getResult()!=null) {
                blankUserName.setText("持卡人姓名： "+data.getResult().getCard_owner());
                blankNumber.setText("银行卡号： "+data.getResult().getCard_num());
                blanCity.setText("开户城市： "+data.getResult().getCity());
                blankStyle.setText("银行卡类型： "+data.getResult().getType());
                blankName.setText("支行名称： "+data.getResult().getBank_name());
            }

            int status = data.getStatus();
            DialogUtil dialogUtil; dialogUtil = new DialogUtil(getContext());
            switch (status){
                case 0:
                    dialogUtil.hintDialog(data.getMsg());
                    break;
                case 1:
                    dialogUtil.sureOrcacleDialog( view, "确认提现银行卡", true, false, null);
                    break;
                case 2:
                    dialogUtil.sureOrcacleDialog( view, "确认提现银行卡", true, true, new DialogUtil.OnSureClickListener() {
                        @Override
                        public void sureClick() {
                            mineBalanceFragmentPresenter.CanMoney(billIds);
                        }
                    });
                    break;
            }

        }

    }
}
