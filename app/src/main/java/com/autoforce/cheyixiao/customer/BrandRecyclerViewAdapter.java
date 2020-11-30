package com.autoforce.cheyixiao.customer;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBrandBean;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;

/**
 * Created by liujialei on 2018/11/23
 */
public class BrandRecyclerViewAdapter extends DefaultAdapter<CustomerBrandBean.Result> {

    private int mBid=0;

    @Override
    public BaseHolder<CustomerBrandBean.Result> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_popupwindowrecyclerview;
    }


    public void setIndex(int bid){
        mBid = bid;
    }


    class ViewHolder extends BaseHolder<CustomerBrandBean.Result>{

        private TextView tv_cartext;

        private ImageView iv_check;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_cartext = itemView.findViewById(R.id.tv_cartext);
            iv_check = itemView.findViewById(R.id.iv_check);


        }

        @Override
        public void bindData(CustomerBrandBean.Result data, int position) {


            tv_cartext.setTextColor(Color.parseColor("#262626"));
            tv_cartext.setText(data.getBname());

            if(mBid==data.getBid()){
                iv_check.setVisibility(View.VISIBLE);
                tv_cartext.setTextColor(Color.parseColor("#D5001C"));
            }else {
                iv_check.setVisibility(View.GONE);
            }





        }
    }


}
