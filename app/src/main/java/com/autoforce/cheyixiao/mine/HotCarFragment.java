package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseFragment;
import com.autoforce.cheyixiao.mine.adapter.HotCarAdapter;

import java.util.ArrayList;
import java.util.List;

public class HotCarFragment extends Fragment {
    RecyclerView recycleView;
    TextView addOtherHotCar;


    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_hot_car, null);

        initView(inflate);
        return inflate;
    }



    private void initView(View inflate) {
       recycleView = inflate.findViewById(R.id.recycle_view);
       addOtherHotCar = inflate.findViewById(R.id.add_other_hot_car);
    }

    @Override
    public void onStart() {
        super.onStart();

        for (int i = 0; i < 10; i++) {
            list.add(i + "车型");
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        HotCarAdapter hotCarAdapter = new HotCarAdapter(list, getContext());
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(hotCarAdapter );
    }



}
