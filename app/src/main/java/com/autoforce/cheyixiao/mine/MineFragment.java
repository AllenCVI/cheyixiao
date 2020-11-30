package com.autoforce.cheyixiao.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autoforce.cheyixiao.MainActivity;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseConstant;
import com.autoforce.cheyixiao.base.CommonX5WebViewInterceptActivity;
import com.autoforce.cheyixiao.common.StatusBarUtil;
import com.autoforce.cheyixiao.common.UMcommonParam;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.remote.bean.UserInfoBean;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.common.view.OverScrollView;
import com.autoforce.cheyixiao.common.view.roundedimageview.RoundedImageView;
import com.autoforce.cheyixiao.login.LoginActivity;
import com.autoforce.cheyixiao.mine.minegloab.MineGloab;
import com.autoforce.cheyixiao.mine.minemvp.MineFragmentPresenterImpl;
import com.autoforce.cheyixiao.mine.minemvp.MineFragmentView;
import com.autoforce.cheyixiao.mine.web2.ApproveInfoWebActivity;

/**
 * Created by xialihao on 2018/11/15.
 */
public class MineFragment extends Fragment implements MineFragmentView, View.OnClickListener {


    private RoundedImageView userHeadIma;
    private TextView userName;
    private TextView userNumber;
    private TextView auditState;
    private RelativeLayout relaBuycarOrder;
    private RelativeLayout relaBuycarOrderCan;
    private RelativeLayout relaBuycarOrderCaning;
    private TextView buyCayorderCanMoney;
    private TextView buyCarOrderCaningMoney;
    private RelativeLayout relaCarResouceOrder;
    private RelativeLayout relaCarResouceOrderCan;
    private RelativeLayout relaCarResouceOrderCaning;
    private TextView carResouceOrderCanMoney;
    private TextView carResouceOrderCaningMoney;
    private TextView approveInfo;
    private TextView myIntegral;
    private TextView orderCarSource;
    private TextView blankCard;
    private TextView insurceMange;
    private TextView hotCar;
    private TextView changePhoneNumber;
    private TextView changePwd;
    private MainActivity activity;
    private TextView textLogout;
    private MineFragmentPresenterImpl mineFragmentPresenter;
    private String bindPhone;
    private TextView versionText;

    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mineView = inflater.inflate(R.layout.fragment_mine_nolog_view, null);
        initView(mineView);

        RelativeLayout titleRela = mineView.findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, StatusBarUtil.getStatuHight(getContext()), 0, 0);
        titleRela.setLayoutParams(layoutParams);
        int[] te = {R.id.approve_info, R.id.my_integral, R.id.order_car_source, R.id.blank_card,
                R.id.insurce_manage, R.id.hot_car, R.id.change_phone_number, R.id.change_pwd};
        for (int t : te) {
            TextView texIma = mineView.findViewById(t);
            DrawableUtils.resizeDrawable(texIma, DrawableUtils.DRAWABLE_TOP, 2.5f / 3f);
        }
        int[] ic = {R.id.mine_sprlus, R.id.mine_account, R.id.mine_manager};
        for (int z : ic) {
            TextView texIma = mineView.findViewById(z);
            DrawableUtils.resizeDrawable(texIma, DrawableUtils.DRAWABLE_LEFT, 3 / 4f);
        }

        mineFragmentPresenter = new MineFragmentPresenterImpl(this);


        return mineView;
    }


    private void initView(View mineView) {
        userHeadIma = mineView.findViewById(R.id.user_head_photo);
        userName = mineView.findViewById(R.id.user_name);
        userNumber = mineView.findViewById(R.id.user_number);
        auditState = mineView.findViewById(R.id.audit_state);

        relaBuycarOrder = mineView.findViewById(R.id.rela_buy_car_order);
        relaBuycarOrderCan = mineView.findViewById(R.id.rela_buy_car_order_can);
        relaBuycarOrderCaning = mineView.findViewById(R.id.rela_buy_car_order_caning);
        buyCayorderCanMoney = mineView.findViewById(R.id.buy_cay_order_can_money);
        buyCarOrderCaningMoney = mineView.findViewById(R.id.buy_cay_order_caning_money);

        relaCarResouceOrder = mineView.findViewById(R.id.rela_car_resouce_order);
        relaCarResouceOrderCan = mineView.findViewById(R.id.rela_car_resouce_order_can);
        relaCarResouceOrderCaning = mineView.findViewById(R.id.rela_car_resouce_order_caning);
        carResouceOrderCanMoney = mineView.findViewById(R.id.car_resouce_order_can_money);
        carResouceOrderCaningMoney = mineView.findViewById(R.id.car_resouce_order_caning_money);

        approveInfo = mineView.findViewById(R.id.approve_info);
        myIntegral = mineView.findViewById(R.id.my_integral);
        orderCarSource = mineView.findViewById(R.id.order_car_source);
        blankCard = mineView.findViewById(R.id.blank_card);


        insurceMange = mineView.findViewById(R.id.insurce_manage);
        hotCar = mineView.findViewById(R.id.hot_car);
        changePhoneNumber = mineView.findViewById(R.id.change_phone_number);
        changePwd = mineView.findViewById(R.id.change_pwd);
        textLogout = mineView.findViewById(R.id.log_out_text);

        versionText = mineView.findViewById(R.id.version_text);

        ImageView ivHeadbj = (ImageView) mineView.findViewById(R.id.ima);
        OverScrollView mHeadbigview = (OverScrollView) mineView.findViewById(R.id.headbigview);
        mHeadbigview.setImageView(ivHeadbj);//设置缩放的对象

        userHeadIma.setOnClickListener(this);

        relaBuycarOrder.setOnClickListener(this);
        relaBuycarOrderCan.setOnClickListener(this);
        relaBuycarOrderCaning.setOnClickListener(this);

        relaCarResouceOrder.setOnClickListener(this);
        relaCarResouceOrderCan.setOnClickListener(this);
        relaCarResouceOrderCaning.setOnClickListener(this);

        approveInfo.setOnClickListener(this);
        myIntegral.setOnClickListener(this);
        orderCarSource.setOnClickListener(this);
        blankCard.setOnClickListener(this);

        insurceMange.setOnClickListener(this);
        hotCar.setOnClickListener(this);
        changePhoneNumber.setOnClickListener(this);
        changePwd.setOnClickListener(this);

        textLogout.setOnClickListener(this);

        String appVersionName = AppMessageUtils.getAppVersionName(getContext());
        versionText.setText(getResources().getString(R.string.version_text) + appVersionName);
    }

    @Override
    public void onResume() {
        super.onResume();
        mineFragmentPresenter.getInfo();
        UMengStatistics.onPageStart("mineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        UMengStatistics.onPageEnd("mineFragment");
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        String url;
        switch (view.getId()) {
           /* case R.id.user_head_photo://头像

                break;
            case R.id.rela_buy_car_order_can://购车余额可提现

                intent = new Intent(activity , MineBalanceActivity.class);
                intent.putExtra("current" , 0);
                intent.putExtra("currentChild" ,0);
                startActivity(intent);

                break;
            case R.id.rela_buy_car_order_caning://购车余额提现中

                intent = new Intent(activity , MineBalanceActivity.class);
                intent.putExtra("current" , 0);
                intent.putExtra("currentChild" ,1);
                startActivity(intent);

                break;
            case R.id.rela_car_resouce_order_can://车源订单余额可提现

                intent = new Intent(activity , MineBalanceActivity.class);
                intent.putExtra("current" , 1);
                intent.putExtra("currentChild" ,0);

                startActivity(intent);

                break;
            case R.id.rela_car_resouce_order_caning://车源订单余额提现中

                intent = new Intent(activity , MineBalanceActivity.class);
                intent.putExtra("current" , 1);
                intent.putExtra("currentChild" ,1);
                startActivity(intent);

                break;*/
            case R.id.rela_buy_car_order_can:
            case R.id.rela_buy_car_order_caning:
            case R.id.rela_car_resouce_order_can:
            case R.id.rela_car_resouce_order_caning:
                UMengStatistics.statisticEventNumber(UMcommonParam.MY_BALANCE_EVENID);
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/myBalance";
                url = BaseConstant.BASEURL + MineGloab.MY_BALANUCE;
                CommonX5WebViewInterceptActivity.start(activity, url, null);
                break;

            case R.id.approve_info://认证信息
            case R.id.user_head_photo://头像
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/identify";
                //url="https://cheyixiao.autoforce.net/static/app/mobileIndex.html#/userMobile/identify";
                url = BaseConstant.BASEURL + MineGloab.IDENFTIFY;
                ApproveInfoWebActivity.start(getActivity(), url);
                break;
            case R.id.my_integral://我的积分
                UMengStatistics.statisticEventNumber(UMcommonParam.MY_INTEGRAL_EVENID);
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/myCredit";
                //url = "https://blog.csdn.net/lin_dianwei/article/details/79025324";
                url = BaseConstant.BASEURL + MineGloab.MY_CRETDIT;
                CommonX5WebViewInterceptActivity.start(getActivity(), url, null);

                break;
            case R.id.order_car_source://车源订单
                /*intent = new Intent(activity , CarResouceOrderActivity.class);
                startActivity(intent);*/
                //url ="http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/optionsOrders";
                url = BaseConstant.BASEURL + MineGloab.OPTION_ORDERS;
                CommonX5WebViewInterceptActivity.start(activity, url, null);
                break;
            case R.id.blank_card://银行卡

                break;
            case R.id.insurce_manage://保险管理
                UMengStatistics.statisticEventNumber(UMcommonParam.INSURANCE_MANAGER_EVENID);
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/insuranceMobile";
                url = BaseConstant.BASEURL + MineGloab.INSURACNCE_MOBILE;
                CommonX5WebViewInterceptActivity.start(getActivity(), url, null);
                break;
            case R.id.hot_car://热销车
                intent = new Intent(activity, HotCarActivity.class);
                startActivity(intent);
                break;
            case R.id.change_phone_number://更改手机号
                UMengStatistics.statisticEventNumber("change_number");
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/changePhone";
                url = BaseConstant.BASEURL + MineGloab.CHANGE_PHONE;
                CommonX5WebViewInterceptActivity.start(getActivity(), url, bindPhone);
                break;
            case R.id.change_pwd://更改密码
                /*intent = new Intent(activity, ChangePwdActivity.class);
                startActivity(intent);*/
                //url = "http://192.168.10.25:8080/saler/mobileIndex.html#/userMobile/changePwd";
                url = BaseConstant.BASEURL + MineGloab.CHANGE_PWD;
                CommonX5WebViewInterceptActivity.start(getActivity(), url, null);
                break;
            case R.id.log_out_text: // 退出登录
                UMengStatistics.statisticEventNumber(UMcommonParam.LOG_OUT_EVENID);
                logout();
                break;

        }
    }

    private void logout() {
        if (getActivity() != null) {
            LoginActivity.start(getActivity());
            getActivity().finish();
        }
    }


    @Override
    public void showInfo(UserInfoBean data) {
        if (data != null && data.getResults() != null) {
            if (data.getResults().getIsPass()) {
                auditState.setVisibility(View.GONE);
            } else {
                auditState.setVisibility(View.VISIBLE);
            }
            bindPhone = data.getResults().getPhone() + "";
            userName.setText(data.getResults().getName());
            userNumber.setText(data.getResults().getPhone() + "");
            if (!StringUtils.isEmpty(data.getResults().getAvatar())) {
                ImageLoaderUtils.loadImage(data.getResults().getAvatar(), userHeadIma, R.drawable.default_headimg, R.drawable.default_headimg, null);
            }
            buyCayorderCanMoney.setText(data.getResults().getCan_cash());
            buyCarOrderCaningMoney.setText(data.getResults().getProcess_cash());
            carResouceOrderCanMoney.setText(data.getResults().getSource_can_cash());
            carResouceOrderCaningMoney.setText(data.getResults().getSource_process_cash());
        }
    }
}
