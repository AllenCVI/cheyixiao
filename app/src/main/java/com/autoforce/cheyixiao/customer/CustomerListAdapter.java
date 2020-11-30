package com.autoforce.cheyixiao.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.data.remote.bean.CustomerBean;
import com.autoforce.cheyixiao.common.utils.ImageLoaderUtils;
import com.autoforce.cheyixiao.common.view.MyLinearLayout;
import com.autoforce.cheyixiao.common.view.rv.BaseHolder;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujialei on 2018/11/20
 */
public class CustomerListAdapter extends DefaultAdapter<CustomerBean.Result> {


    Context mContext;

    public CustomerListAdapter(Context context) {
        mContext = context;
    }

    public CustomerListAdapter() {

    }


    ViewHolder viewHolder;

    @Override
    public BaseHolder<CustomerBean.Result> getHolder(View v, int viewType) {

        viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_custom_recyclerview;
    }


    class ViewHolder extends BaseHolder<CustomerBean.Result> {


        private MyLinearLayout mLinearLayout;
        private TextView tv_carsysytem_num;
        private TextView tv_name;
        private TextView tv_logintime;
        private TextView tv_salesman;
        private ImageView iv_car;
        private TextView tv_predetermine;
        private TextView tv_car;
        private TextView tv_Reservecarmodel;
        private TextView tv_state;
        private TextView tv_statement;

        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.Mylinearlayout);
            tv_carsysytem_num = itemView.findViewById(R.id.tv_carsysytem_num);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_logintime = itemView.findViewById(R.id.tv_logintime);
            tv_salesman = itemView.findViewById(R.id.tv_salesman);
            iv_car = itemView.findViewById(R.id.iv_car);
            tv_predetermine = itemView.findViewById(R.id.tv_predetermine);
            tv_car = itemView.findViewById(R.id.tv_car);
            tv_Reservecarmodel = itemView.findViewById(R.id.tv_Reservecarmodel);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_statement = itemView.findViewById(R.id.tv_statement);
        }


        @Override
        public void bindData(CustomerBean.Result data, int position) {

            if (data == null) {
                return;
            }
            clearView();

            String carimg = data.getImg();
            String car = data.getCar();

            //预定车型
            if (carimg != null && !carimg.equals("") && car != null && !car.equals("")) {

                if (tv_predetermine.getVisibility() == View.VISIBLE) {
                    tv_predetermine.setVisibility(View.GONE);
                }
                if (iv_car.getVisibility() == View.INVISIBLE || tv_car.getVisibility() == View.INVISIBLE) {
                    iv_car.setVisibility(View.VISIBLE);
                    tv_car.setVisibility(View.VISIBLE);
                }
                ImageLoaderUtils.loadImage(carimg, iv_car, -1, -1, null);
                tv_car.setText(car);
                tv_Reservecarmodel.setText(mContext.getResources().getText(R.string.prearRanged_model));


            }


            if (data.getUname() != null && !data.getUname().isEmpty()) {
                tv_name.setText(data.getUname());
            } else {
                tv_name.setText(mContext.getResources().getString(R.string.user) + data.getUser_id());
            }

            long phone = data.getPhone();
            if (phone == 0) {
//                tv_logintime.setText("");
//                  if(tv_logintime.getVisibility()==View.VISIBLE)
                tv_logintime.setVisibility(View.GONE);
            } else {
//                if(tv_logintime.getVisibility()==View.GONE)
                tv_logintime.setVisibility(View.VISIBLE);
                tv_logintime.setText(phone + "");
            }
            tv_salesman.setText(mContext.getResources().getString(R.string.saleman) + data.getSaler());

            String describe = data.getRemark();

            if (describe == null || describe.equals("")) {

//                if(tv_statement.getVisibility()==View.VISIBLE)
                tv_statement.setVisibility(View.GONE);

            } else {

//                if(tv_statement.getVisibility()==View.GONE)
                tv_statement.setVisibility(View.VISIBLE);
                tv_statement.setText(describe);

            }

            mLinearLayout.setOnClickListener(this);


            List<CustomerBean.Result.Attention> attentions = data.getAttentions();
            if (attentions == null) {
                return;
            }

            tv_carsysytem_num.setText(mContext.getResources().getText(R.string.car_systemNum) + "(" + attentions.size() + ")");
            setMylinearlayoutView(attentions, data);

            int state = data.getState();

            String[] stateArr = mContext.getResources().getStringArray(R.array.states);

            switch (state) {

                case 1:
                    tv_state.setText(stateArr[1]);
                    break;
                case 2:
                    tv_state.setText(stateArr[2]);
                    break;
                case 3:
                    tv_state.setText(stateArr[3]);
                    break;
                case 4:
                    tv_state.setText(stateArr[4]);
                    break;
                case 5:
                    tv_state.setText(stateArr[5]);
                    break;
                case 6:
                    tv_state.setText(stateArr[6]);
                    break;

            }


        }


        public void clearView() {
            mLinearLayout.clear();
            iv_car.setVisibility(View.INVISIBLE);
            tv_car.setVisibility(View.INVISIBLE);
            tv_predetermine.setVisibility(View.VISIBLE);
            tv_Reservecarmodel.setText(mContext.getResources().getText(R.string.prearranged_model));

        }


        public void setMylinearlayoutView(List<CustomerBean.Result.Attention> attentions, CustomerBean.Result data) {

            List<View> list = new ArrayList<>();

            int size = attentions.size();
            for (int i = 0; i < size; i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_myview, mLinearLayout, false);
                ImageView iv_car = view.findViewById(R.id.iv_car);
                if (attentions.get(i).getImg() == null || attentions.get(i).getImg().equals("")) {
                    Logger.i(data.toString());
                } else {
                    ImageLoaderUtils.loadImage(attentions.get(i).getImg(), iv_car, -1, -1, null);
                }

                if (attentions.get(i).getName() == null || attentions.get(i).getName().equals("")) {
                    Logger.i(data.toString());
                } else {
                    TextView textView = view.findViewById(R.id.tv_car);
                    textView.setText(attentions.get(i).getName());
                }

                list.add(view);
            }

            if (size == 1) {
                mLinearLayout.setOnePiToCenter(list.get(0));
            } else {
                mLinearLayout.setListPicList_View(list);
            }


        }


    }

}
