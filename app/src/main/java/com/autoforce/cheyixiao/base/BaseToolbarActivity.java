package com.autoforce.cheyixiao.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ActivityUtils;
import com.autoforce.cheyixiao.common.utils.KeyboardUtil;


/**
 * @author xlh
 * @date 2018/9/12.
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    @BindView(R.id.app_bar_layout)
    protected AppBarLayout mAppBar;
    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    protected TextView toolbarTitle;
    @BindView(R.id.bt_toolbar_right)
    protected TextView btToolbarRight;
    @BindView(R.id.ib_back)
    protected ImageButton ibBack;
    protected ActionBar actionBar;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_content;
    }


    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("The subclass of ToolbarActivity must contain a toolbar.");
        }
//        setSupportActionBar(mToolbar);
//        actionBar = getSupportActionBar();
//        if (actionBar == null) {
//            throw new IllegalStateException(" toolbar can't find");
//        }
//        actionBar.setTitle("");
        if (getToolbarTitle() != -1) {
            toolbarTitle.setText(getToolbarTitle());
        } else {
            toolbarTitle.setText("");
        }

        addFragment();

        toolbarTitle.getPaint().setFakeBoldText(true);
    }

    public void setRightIcon(int drawableId, float wRate, float hRate, View.OnClickListener listener) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable != null) {
            drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * wRate), (int) (drawable.getIntrinsicHeight() * hRate));
            btToolbarRight.setCompoundDrawables(null, null, drawable, null);
            btToolbarRight.setOnClickListener(listener);
        }
    }

    @OnClick(R.id.ib_back)
    void click() {
        try {
            KeyboardUtil.hideSoftInput(this);
        } finally {
            onBackPressed();
        }
    }

    protected void addFragment() {
        Fragment fragment = userFragment();
        if (fragment != null) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_);
        }
    }

    public Fragment userFragment() {
        return null;
    }

    protected int getToolbarTitle() {
        return -1;
    }

    protected void showRightButton(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            btToolbarRight.setVisibility(View.VISIBLE);
            btToolbarRight.setText(charSequence);
        } else {
            btToolbarRight.setVisibility(View.GONE);
        }
    }
}
