package com.autoforce.cheyixiao.mine.adapter;

import android.view.View;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;

public class BuyOrderAdapter extends DefaultAdapter<String> {

    private TextView carName;

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.buy_order_item;
    }

    class ViewHolder extends BaseHolder<String>{

        public ViewHolder(View itemView) {
            super(itemView);
            carName = itemView.findViewById(R.id.car_name);
        }

        @Override
        public void bindData(String data, int position) {
            carName.setText(data);
        }
    }
}
