package com.autoforce.cheyixiao.car.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseActivity;
import com.autoforce.cheyixiao.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xialihao on 2018/11/28.
 */
public abstract class BaseTabActivity<T extends BaseFragment> extends BaseActivity {

    protected TabAdapter mTabAdapter;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.rb_sell)
    RadioButton rbSell;
    @BindView(R.id.rb_off)
    RadioButton rbOff;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_tab_content;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        CharSequence[] titles = getPageTitles();
        List<T> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(getChildFragment(i));
        }

        mTabAdapter = new TabAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(mTabAdapter);

        // 动态添加RadioButton
        rbSell.setOnClickListener(v -> {
            rbSell.setChecked(true);
            viewPager.setCurrentItem(0);
            rbSell.setText(mTabAdapter.getPageTitle(0));
        });

        rbOff.setOnClickListener(v -> {
            rbOff.setChecked(true);
            viewPager.setCurrentItem(1);
            rbOff.setText(mTabAdapter.getPageTitle(1));
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rbSell.setChecked(true);
                    rbOff.setChecked(false);
                } else {
                    rbOff.setChecked(true);
                    rbSell.setChecked(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected abstract CharSequence[] getPageTitles();

    protected abstract T getChildFragment(int index);

    public class TabAdapter extends FragmentPagerAdapter {

        private final CharSequence[] pageTitles;
        private List<T> mFragments;

        public TabAdapter(FragmentManager fm, CharSequence[] pageTitles, List<T> fragments) {
            super(fm);

            this.mFragments = fragments;
            this.pageTitles = pageTitles;
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return pageTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles[position];
        }
    }

}
