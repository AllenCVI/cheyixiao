package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseFragment;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.mine.adapter.BuyOrderAdapter;
import com.autoforce.cheyixiao.mine.minemvp.BuyOrderFragmentPresenterImpl;
import com.autoforce.cheyixiao.mine.minemvp.BuyOrderFragmentView;

import java.util.List;

public class BuyOrderFragment extends BaseFragment implements BuyOrderFragmentView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    private BuyOrderFragmentPresenterImpl buyOrderFragmentPresenter;
    private String salerId;
    private int page = 1;
    private BuyOrderAdapter buyOrderAdapter;


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_buy_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        salerId = LocalRepository.getInstance().getSalerId();
        buyOrderFragmentPresenter = new BuyOrderFragmentPresenterImpl(this);
        buyOrderFragmentPresenter.getData(salerId, page);


    }

    @Override
    public void showData(List<String> list, int page) {
        if (list != null && list.size() > 0) {
            if(buyOrderAdapter==null) {
                buyOrderAdapter = new BuyOrderAdapter();
                recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                recycleView.setAdapter(buyOrderAdapter);
            }else {
                buyOrderAdapter.notifyDataSetChanged();
            }
            buyOrderAdapter.setInfos(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
