package com.autoforce.cheyixiao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.KeyboardUtil;

public abstract class BaseDoubleTitleFragmentActivity extends FragmentActivity {

    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.rb_source)
    RadioButton rbSource;
    @BindView(R.id.rb_search)
    RadioButton rbSearch;
    @BindView(R.id.content_view)
    FrameLayout contentView;

    int currentIndex = -1;
    Fragment leftFragment;
    Fragment rightFragment;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_mine_balance);
        ButterKnife.bind(this);

        initView();

        barBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    KeyboardUtil.hideSoftInput(BaseDoubleTitleFragmentActivity.this);
                }finally {
                    onBackPressed();
                }
            }
        });

    }

    protected void initView() {
        currentIndex = getCurrentIndex();
        rbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradioClickChange(1);
            }
        });
        rbSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gradioClickChange(0);
            }
        });

        rbSearch.setText(getResources().getString(getLeftTitle() == -1? 0:getRightTitle()));
        rbSource.setText(getResources().getString(getRightTitle() == -1?0:getLeftTitle()));
        gradioClickChange(currentIndex);
    }

    /*动态获取标签*/
    protected abstract int getCurrentIndex() ;/*{
        return getIntent().getIntExtra("current" , 0);
    }*/

    /*设置左右标题*/
    public abstract int getRightTitle();

    public abstract int getLeftTitle() ;

    /*获取内容leftFragment*/
    protected abstract Fragment getLeftFragment();
    /*右边fragment*/
    protected abstract Fragment getRightFragment();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIndex", currentIndex);
    }

    public void hint() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (leftFragment != null) {
            fragmentTransaction.hide(getSupportFragmentManager().findFragmentByTag("leftFragment"));
        }
        if(rightFragment != null){
            fragmentTransaction.hide(getSupportFragmentManager().findFragmentByTag("rightFragment"));
        }
        fragmentTransaction.commit();

    }

    public void show(int currentIndex) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(currentIndex == 0) {
            if (leftFragment == null) {
                leftFragment = getLeftFragment();
                fragmentTransaction.add(R.id.content_view, leftFragment, "leftFragment");
            } else {
                fragmentTransaction.show(getSupportFragmentManager().findFragmentByTag("leftFragment"));
            }
        }else if(currentIndex ==1){

            if (rightFragment == null) {
                rightFragment = getRightFragment();
                fragmentTransaction.add(R.id.content_view, rightFragment, "rightFragment");
            } else {
                fragmentTransaction.show(getSupportFragmentManager().findFragmentByTag("rightFragment"));
            }
        }
        fragmentTransaction.commit();

    }



    public void gradioClickChange(int currentIndex) {
        if(currentIndex == 0) {
            rbSearch.setSelected(false);
            rbSource.setSelected(true);
        }else if(currentIndex == 1){
            rbSource.setSelected(false);
            rbSearch.setSelected(true);
        }
        hint();
        show(currentIndex);
    }
}
