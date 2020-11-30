package com.autoforce.cheyixiao.mine.adapter;

import android.view.View;
import android.widget.TextView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;
import com.autoforce.cheyixiao.common.data.remote.bean.CanMoneyBean;

public class BuyCarOrderMoneyAdapter extends DefaultAdapter<CanMoneyBean.ResultsBean> {


    private int currentChild;
    private ViewHolder viewHolder;


    public void setCurrentChild(int currentChildIndex) {
        this.currentChild =  currentChildIndex;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<CanMoneyBean.ResultsBean> getHolder(View v, int viewType) {
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.buy_car_order_money_item;
    }

     class ViewHolder extends BaseHolder<CanMoneyBean.ResultsBean>{

        private final TextView payer;
        private final TextView payMoney;
        private final TextView payMoneyCan;
         private TextView canMoneyText;
         private TextView carName;
         private TextView buyCarOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            canMoneyText = itemView.findViewById(R.id.can_money_text);
            buyCarOrder = itemView.findViewById(R.id.buy_car_order);
            carName = itemView.findViewById(R.id.car_name);
            payer = itemView.findViewById(R.id.payer);
            payMoney = itemView.findViewById(R.id.pay_money);
            payMoneyCan = itemView.findViewById(R.id.pay_money_can);

        }

        @Override
        public void bindData(CanMoneyBean.ResultsBean data, int position) {

            buyCarOrder.setText("购车订单：" + data.getId());
            if(currentChild == 0 || currentChild == 3){
                canMoneyText.setVisibility(View.VISIBLE);
                if(currentChild == 0){
                    canMoneyText.setText("提现");
                }else if(currentChild == 3){
                    canMoneyText.setText("重新提现");
                }
            }else {
                canMoneyText.setVisibility(View.GONE);
            }

            carName.setText(data.getCar_name());

            payer.setText("付款人： " + data.getBuyer() + " " + data.getPhone());
            payMoney.setText("付款金额： ￥" + data.getPay_money());
            payMoneyCan.setText("可提现金额： ￥" + data.getCash_money());

            if(data.isClick_enable()){
                canMoneyText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onCashMoneyTextClickLinstener!=null){
                            data.setClick_enable(false);
                            onCashMoneyTextClickLinstener.click(data.getId());

                        }
                    }
                });
            }

        }
    }

    private OnCashMoneyTextClickLinstener onCashMoneyTextClickLinstener;
    public interface OnCashMoneyTextClickLinstener{
        void click(String billId);
    }

    public void setOnCashMoneyTextClickLinstener(OnCashMoneyTextClickLinstener linstener){
        this.onCashMoneyTextClickLinstener = linstener;
    }

}
