package com.autoforce.cheyixiao.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ImageLoaderUtils;

import net.qiujuer.genius.ui.widget.Loading;

public class PageStateView extends FrameLayout implements View.OnClickListener {

    public static final int STATE_NO_NETWORK = 1;
    public static final int STATE_NO_DATA = 2;
    public static final int STATE_NO_CONTENT = 3;

    private ImageView imgState;
    private Context context;
    private ImageView iv_empty;
    private View[] mBindViews;
    private Loading mLoading;
    private TextView tv_loading;
    private OnclickRefreshListener mOnclickRefreshListener;
    private TextView tv_warnning;
    private RelativeLayout rel;
    private RelativeLayout titleReal;
    private TextView title;
    private ImageView barBack;


    public PageStateView(@NonNull Context context) {
        this(context, null);
    }

    public PageStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //        this.imgState = new ImageView(context);
        //        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //        addView(imgState, layoutParams);

        initView(context);


    }

    private void initView(Context context) {

        View view = View.inflate(context, R.layout.emptyview, this);
        iv_empty = view.findViewById(R.id.iv_empty);
        mLoading = view.findViewById(R.id.loading);
        tv_loading = view.findViewById(R.id.tv_loading);
        rel = view.findViewById(R.id.rel);
        tv_warnning = view.findViewById(R.id.tv_warnning);

        titleReal = view.findViewById(R.id.title_real);
        barBack = view.findViewById(R.id.bar_back);
        title = view.findViewById(R.id.title);
        barBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBackClickListener!=null){
                    onBackClickListener.onBackClick();
                }
            }
        });

    }


    /**
     * 绑定外面要隐藏的控件
     *
     * @param views
     */
    public void bindView(View... views) {
        mBindViews = views;
    }


    /**
     * 隐藏绑定控件显示无网络
     * 可扩展
     */
    public void showNoNetWork() {
        title.setText("无网络");
        mLoading.setVisibility(View.GONE);
        mLoading.stop();
        tv_loading.setVisibility(View.GONE);
        changeBindViewVisibility(View.GONE);
        tv_warnning.setText(getResources().getString(R.string.networkerr));
        rel.setVisibility(VISIBLE);
        ImageLoaderUtils.loadImage(R.drawable.ic_page_state_no_network, iv_empty, -1, -1, null);
        setVisibility(View.VISIBLE);
    }


    /**
     * 隐藏绑定控件显示无数据
     * 可扩展
     */
    public void showNoData() {
        title.setText("无数据");
        mLoading.setVisibility(View.GONE);
        mLoading.stop();
        tv_loading.setVisibility(View.GONE);
        changeBindViewVisibility(View.GONE);
        tv_warnning.setText(getResources().getString(R.string.nodata));
        rel.setVisibility(VISIBLE);
        ImageLoaderUtils.loadImage(R.drawable.ic_page_state_no_data, iv_empty, -1, -1, null);
        setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏绑定控件显示无内容
     * 可扩展
     */
    public void showNoContent() {
        title.setText("无内容");
        mLoading.setVisibility(View.GONE);
        mLoading.stop();
        tv_loading.setVisibility(View.GONE);
        changeBindViewVisibility(View.GONE);
        tv_warnning.setText(getResources().getString(R.string.nodata));
        rel.setVisibility(VISIBLE);
        ImageLoaderUtils.loadImage(R.drawable.ic_page_state_no_content, iv_empty, -1, -1, null);
        setVisibility(View.VISIBLE);
    }



    /**
     * 显示loadingView
     */
    public void showLoadingView() {
        tv_loading.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.start();
        changeBindViewVisibility(View.GONE);
        rel.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
    }


    /**
     * 展示绑定的控件隐藏本控件
     */
    public void showNomalData() {
        titleReal.setVisibility(GONE);
        mLoading.setVisibility(View.GONE);
        mLoading.stop();
        tv_loading.setVisibility(View.GONE);
        rel.setVisibility(GONE);
        setVisibility(View.GONE);
        changeBindViewVisibility(View.VISIBLE);
    }


    /**
     * 更改绑定布局的显示状态
     *
     * @param visible 显示的状态
     */
    private void changeBindViewVisibility(int visible) {
        final View[] views = mBindViews;
        if (views == null || views.length == 0)
            return;
        for (View view : views) {
            view.setVisibility(visible);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel:
                if (mOnclickRefreshListener != null) {
                    mOnclickRefreshListener.onClickRefresh();
                }
                break;
        }
    }


    public interface OnclickRefreshListener {
        void onClickRefresh();
    }


    /**
     * 点击无内容图片时刷新
     */

    public void setOnClickRefreshListener(OnclickRefreshListener onClickRefreshListener) {
        rel.setOnClickListener(this);
        this.mOnclickRefreshListener = onClickRefreshListener;
    }

    private OnBackClickListener onBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener){
        this.onBackClickListener = onBackClickListener;
    }
    public interface OnBackClickListener{
        void onBackClick();
    }

    public void setTitleVisibity(){
        titleReal.setVisibility(VISIBLE);
    }


}
