package com.autoforce.cheyixiao.customer.customersecond;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.customer.customersecondfragments.Fragment_Focus_on_Models;
import com.autoforce.cheyixiao.customer.customersecondfragments.Fragment_Order_details;
import com.autoforce.cheyixiao.mvp.BaseMvpActivity;

/**
 * Created by liujialei on 2018/11/21
 */
public class CustomerSecondActivity extends BaseMvpActivity<CustomerSecondContract.Presenter> implements CustomerSecondContract.View {


    @BindView(R.id.tv_carmodel)
    TextView tv_carmodel;
    @BindView(R.id.tv_orderdetail)
    TextView tv_orderdetail;
    @BindView(R.id.vline_carmodel)
    View vline_carmodel;
    @BindView(R.id.vline_orderdetail)
    View vline_orderdetail;

    private int currentTabIndex = -1;
    private static final String STATE_CURRENT_TAB_INDEX = "StateCurrentTabIndex";
    private static final String FRAGMENT_TAG_PREFIX = "CustomerSecondActivityFragment_";
    private static final int DEFAULT_INDEX = 0;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_customersecond;
    }


    public static void start(Context context){

        Intent intent = new Intent(context,CustomerSecondActivity.class);
        context.startActivity(intent);

    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        int index;
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(CustomerSecondActivity.STATE_CURRENT_TAB_INDEX);
        } else {
            index = DEFAULT_INDEX;
        }

        showTab(index);

    }



    private void showTab(int index) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (index != currentTabIndex) {
            changeFragment(index, currentTabIndex);
            currentTabIndex = index;
        }

    }


    @OnClick(R.id.ib_back)
    public void clickBack(View view){

        onBackPressed();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void changeFragment(int newTabIndex, int oldTabIndex) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        Fragment currentFragment = null;
        if (oldTabIndex >= 0) {
            currentFragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(oldTabIndex));
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        Fragment targetFragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(newTabIndex));

        if (targetFragment == null) {
            targetFragment = createFragment(newTabIndex);
            transaction.add(R.id.fragment, targetFragment, genFragmentTag(newTabIndex));
        } else {
            transaction.show(targetFragment);
        }

        transaction.commit();
    }

    private Fragment createFragment(int index) {

        Fragment fragment;
        switch (index) {
            case 0:
                fragment = Fragment_Focus_on_Models.newInstance();
                break;
            case 1:
                fragment = Fragment_Order_details.newInstance();
                break;
            default:
                fragment = Fragment_Focus_on_Models.newInstance();
        }

        return fragment;
    }





    private String genFragmentTag(int index) {
        return CustomerSecondActivity.FRAGMENT_TAG_PREFIX + index;
    }



    @OnClick({R.id.tv_carmodel,R.id.tv_orderdetail})
    public void onClickView(View v){

        int pos;

        switch (v.getId()){

            case R.id.tv_carmodel:
                selectTab_carModel();
                pos = 0;
                break;

            case R.id.tv_orderdetail:
                selectTab_orderDetail();
                pos = 1;
                break;
            default:
                pos = 0;
        }
        showTab(pos);
    }





    private void selectTab_carModel() {


        if(vline_carmodel.getVisibility()!=View.VISIBLE){

            if(vline_orderdetail.getVisibility()==View.VISIBLE){
                vline_orderdetail.setVisibility(View.GONE);
            }
            vline_carmodel.setVisibility(View.VISIBLE);

            tv_carmodel.setTextColor(getResources().getColor(R.color.redf5));
            tv_orderdetail.setTextColor(getResources().getColor(R.color.black9));

        }


    }




    private void selectTab_orderDetail() {


        if(vline_orderdetail.getVisibility()!=View.VISIBLE){

            if(vline_carmodel.getVisibility()==View.VISIBLE){
                vline_carmodel.setVisibility(View.GONE);
            }
            vline_orderdetail.setVisibility(View.VISIBLE);
            tv_carmodel.setTextColor(getResources().getColor(R.color.black9));
            tv_orderdetail.setTextColor(getResources().getColor(R.color.redf5));
        }





    }







}
