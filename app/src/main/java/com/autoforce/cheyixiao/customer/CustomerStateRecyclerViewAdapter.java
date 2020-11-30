package com.autoforce.cheyixiao.customer;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;

/**
 * Created by liujialei on 2018/11/27
 */
public class CustomerStateRecyclerViewAdapter extends DefaultAdapter<CustomerStateBean> {



    private int state=0;



    @Override
    public BaseHolder<CustomerStateBean> getHolder(View v, int viewType) {
        return new ViewHolder(v);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_popupwindowrecyclerview;
    }


    public void setState(int state) {
        this.state = state;
    }


    class ViewHolder extends BaseHolder<CustomerStateBean>{

        private TextView tv_cartext;

        private ImageView iv_check;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_cartext = itemView.findViewById(R.id.tv_cartext);
            iv_check = itemView.findViewById(R.id.iv_check);
        }

        @Override
        public void bindData(CustomerStateBean data, int position) {

            tv_cartext.setTextColor(Color.parseColor("#262626"));
            tv_cartext.setText(data.getStateText());

            if(data.getStateNum()==state){
                iv_check.setVisibility(View.VISIBLE);
                tv_cartext.setTextColor(Color.parseColor("#D5001C"));
            }else {
                iv_check.setVisibility(View.GONE);
            }



        }


    }


}
