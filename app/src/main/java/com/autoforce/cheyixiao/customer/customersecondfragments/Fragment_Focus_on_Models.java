package com.autoforce.cheyixiao.customer.customersecondfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.common.view.MyLinearLayout;
import com.autoforce.cheyixiao.customer.mycommon.MyOnclickListener;
import com.autoforce.cheyixiao.mvp.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujialei on 2018/11/30
 */
public class Fragment_Focus_on_Models extends BaseMvpFragment<Fragment_FocusonModels_Contract.Presenter> implements Fragment_FocusonModels_Contract.View,MyOnclickListener{


    @BindView(R.id.srolllinear_car)
    MyLinearLayout srolllinear_car;
    @BindView(R.id.scrolllinear_preference)
    MyLinearLayout scrolllinear_preference;
    @BindView(R.id.scroll_pre_detail)
    MyLinearLayout scroll_pre_detail;
    @BindView(R.id.tv_countcarbody)
    TextView tv_countcarbody;
    @BindView(R.id.tv_counttrim)
    TextView tv_counttrim;
    @BindView(R.id.tv_countmatching)
    TextView tv_countmatching;
    @BindView(R.id.tv_countpart)
    TextView tv_countpart;



    public static Fragment_Focus_on_Models newInstance() {
        return new Fragment_Focus_on_Models();
    }


    @Override
    protected int provideContentViewId() {

        return R.layout.fragment_focus_on_models;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        initCarScrollLinear();

        initPreferenceScrollLinear();

        initTvScrollLinear();

        initPreferenceDetailScrollLinear();


    }

    private void initTvScrollLinear() {






    }


    private int clickIndex=0;

    @OnClick({R.id.tv_countcarbody,R.id.tv_counttrim,R.id.tv_countmatching,R.id.tv_countpart})
    public void onclick(View v){

        switch (v.getId()){

            case R.id.tv_countcarbody:

                if(clickIndex==0){
                    return;
                }
                changeTextView(tv_countcarbody);
                clickIndex = 0;

                break;

            case R.id.tv_counttrim:

                if(clickIndex==1){
                    return;
                }
                changeTextView(tv_counttrim);
                clickIndex = 1;

                break;

            case R.id.tv_countmatching:
                if(clickIndex==2){
                    return;
                }
                changeTextView(tv_countmatching);
                clickIndex = 2;

                break;

            case R.id.tv_countpart:
                if(clickIndex==3){
                    return;
                }
                changeTextView(tv_countpart);
                clickIndex = 3;
                break;
        }


    }



    private void changeTextView(TextView textView){

        textView.setBackground(getResources().getDrawable(R.drawable.bg_preferencetv_red5));
        textView.setTextColor(getResources().getColor(R.color.redf5));

        TextView oldClickTextView = getOldClickTextView();

        oldClickTextView.setBackground(getResources().getDrawable(R.drawable.bg_preferencetv));
        oldClickTextView.setTextColor(getResources().getColor(R.color.black3));


    }

    private TextView getOldClickTextView() {

        TextView clickTextView;

        switch (clickIndex){

            case 0:

                clickTextView = tv_countcarbody;

                break;


            case 1:

                clickTextView = tv_counttrim;

                break;


            case 2:

                clickTextView = tv_countmatching;

                break;


            case 3:

                clickTextView = tv_countpart;

                break;

             default:
                 clickTextView = tv_countcarbody;
                 break;
        }

        return clickTextView;


    }


    private void initPreferenceDetailScrollLinear() {



        List<View> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            View v = getLayoutInflater().inflate(R.layout.buy_colorpreference_detail,scroll_pre_detail,false);
            list.add(v);
        }
        scroll_pre_detail.setListPicList_View(list);

    }

    private void initPreferenceScrollLinear() {

        List<View> list = new ArrayList<>();

        for (int i=0;i<5;i++){
            View v = getLayoutInflater().inflate(R.layout.item_preferencescroll,scrolllinear_preference,false);
            MyLinearLayout myShowLinear = v.findViewById(R.id.myshowlinear);
            List<View> lists = new ArrayList<>();


            if(i%2==0){
                for(int j=0;j<2;j++){
                    View view = getLayoutInflater().inflate(R.layout.showpriference,myShowLinear,false);
                    lists.add(view);
                }
                myShowLinear.setNoScrollListPicList_View(lists);
            }else {
                View view = getLayoutInflater().inflate(R.layout.showpriference,myShowLinear,false);
                myShowLinear.setNoScrollOnePiToCenter(view);
            }

            list.add(v);
        }

        scrolllinear_preference.setListPicList_View(list);
    }


    private void initCarScrollLinear() {

        List<View> list = new ArrayList<>();

        for (int i=0;i<5;i++){
            View v = getLayoutInflater().inflate(R.layout.item_carscroll,srolllinear_car,false);
            toConfigDetaill(i,v);
            list.add(v);
        }

        srolllinear_car.setListPicList_View(list);
        srolllinear_car.setAllOnclickLstener(this);

    }

    private void toConfigDetaill(final int index,View v) {

        TextView tv_configdetaill = v.findViewById(R.id.tv_configdetaill);

        tv_configdetaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.showToast("textview"+index);

            }
        });



    }


    @Override
    public void myOnclick(View v) {
        ToastUtil.showToast((int) v.getTag()+"");
    }






}
