package com.autoforce.cheyixiao.car.source.me;

import android.view.View;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;

/**
 * Created by xialihao on 2018/11/29.
 */
public class MyCarSourceAdapter extends DefaultAdapter<String> {

    @Override
    public ViewHolder getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_source;
    }

    class ViewHolder extends BaseHolder<String> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(String data, int position) {

        }
    }
}
