package com.autoforce.cheyixiao.car.base.refresh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.common.view.AutoForceRefreshLayout;
import com.autoforce.cheyixiao.common.view.rv.DefaultAdapter;
import com.autoforce.cheyixiao.mvp.BaseMvpFragment;
import com.autoforce.cheyixiao.mvp.IPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xialihao on 2018/11/29.
 */
public abstract class ListFragment<T, P extends IPresenter> extends BaseMvpFragment<P> implements IRecyclerView<T> {


    @BindView(R.id.refresh_layout)
    AutoForceRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.vs_empty)
    ViewStub vsEmpty;
    @BindView(R.id.vs_error)
    ViewStub vsError;
    protected DefaultAdapter<T> mAdapter;
    private int pageNo;
    private boolean isEmptyInflate = false;
    private boolean isErrorInflate = false;
    private boolean isFirstShow = true;
    private ViewGroup errGroup;
    private ViewGroup emptyGroup;
    private AutoForceRefreshLayout refreshLayoutInEmpty;

    @Override
    protected int provideContentViewId() {
        return R.layout.layout_recycle;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        refreshLayout.setAutoForceLoadMoreEnabled(isEnableLoadMore());
        refreshLayout.setEnableRefresh(isEnableRefresh());
        recyclerView.setLayoutManager(getLayoutManager());
        mAdapter = getAdapter();
        recyclerView.setAdapter(mAdapter);
        pageNo = getPageStart();

//        refreshLayout.setOnRefreshListener(this::refreshInternal);
//        refreshLayout.setOnLoadMoreListener(refreshLayout -> onRefresh(refreshLayout, ++pageNo, true));

        refreshLayout.setAutoForceRefreshListener(new AutoForceRefreshLayout.OnAutoForceRefreshListener() {
            @Override
            public void onRefresh(AutoForceRefreshLayout autoForceRefreshLayout) {
                refreshInternal(autoForceRefreshLayout);
            }

            @Override
            public void onLoadMore(AutoForceRefreshLayout autoForceRefreshLayout) {
                super.onLoadMore(autoForceRefreshLayout);
                ListFragment.this.onRefresh(refreshLayout, ++pageNo, true);
            }
        });

        vsEmpty.setOnInflateListener((stub, inflated) -> {
            isEmptyInflate = true;
        });

        vsError.setOnInflateListener((stub, inflated) -> {
            isErrorInflate = true;
        });

        resetMarkup();
    }

    private void refreshInternal(RefreshLayout refreshLayout) {
        pageNo = getPageStart();
        refreshLayout.setEnableLoadMore(true);
        onRefresh(refreshLayout, pageNo, false);
    }

    private void resetMarkup() {
        isEmptyInflate = false;
        isErrorInflate = false;
        isFirstShow = true;
    }

    protected int getPageStart() {
        return 1;
    }

    protected void resetPageNo() {
        this.pageNo = getPageStart();
        if (refreshLayout != null) {
            refreshLayout.setEnableLoadMore(true);
        }
    }


    @Override
    public void onDataGot(List<T> data, boolean isLoadMore) {

        isFirstShow = false;
        if (errGroup != null) {
            errGroup.setVisibility(View.GONE);
        }
        if (isLoadMore) {
            if (refreshLayout != null) {
                recyclerView.stopScroll();
                refreshLayout.finishLoadMore();
                if (data == null || data.size() == 0) {
                    refreshLayout.setEnableLoadMore(false);
                    refreshLayout.postDelayed(() ->
                                    ToastUtil.showToast(R.string.no_more_data),
//                                    Toast.makeText(getActivity(), R.string.nomore_data, Toast.LENGTH_SHORT).show(),
                            1000);
                } else {
                    mAdapter.appendInfos(data);
                }
                return;
            }

        }

        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }

        if (refreshLayoutInEmpty != null) {
            refreshLayoutInEmpty.finishRefresh();
        }

        if (data == null || data.size() == 0) {
            showEmpty();
        } else {
            if (emptyGroup != null) {
                emptyGroup.setVisibility(View.GONE);
            }

            if (refreshLayout != null) {
                refreshLayout.setVisibility(View.VISIBLE);
            }
            mAdapter.setInfos(data);
        }


    }

    @Override
    public void onError(String errMsg) {

        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(false);
            } else {
                refreshLayout.finishLoadMore(false);
            }

            refreshLayout.postDelayed(() ->
                            ToastUtil.showToast(errMsg),
//                    Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show(),
                    1000);
        }

        if (isFirstShow) {
            showErrorPage();
        }

    }

    private void showErrorPage() {

        if (refreshLayout != null) {
            refreshLayout.setVisibility(View.GONE);
        }

        if (!isErrorInflate && vsError != null) {
            errGroup = (ViewGroup) vsError.inflate();

            TextView textView = errGroup.findViewById(R.id.text);
            ImageView imageView = errGroup.findViewById(R.id.image);
//            textView.setText(getErrorText());
//            textView.setMovementMethod(LinkMovementMethod.getInstance());
//            imageView.setImageResource(getErrorDrawableId());
        }
    }

    private int getErrorDrawableId() {
        return R.drawable.ic_page_state_no_network;
    }

//    private CharSequence getErrorText() {
//
//        String textPre = StringUtils.getString(R.string.error_page_text) + "\n";
//        String textPost = StringUtils.getString(R.string.error_page_refresh);
//
//        SpannableString ss = new SpannableString(textPre + textPost);
//
//        ss.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                onErrorTextClick();
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setColor(getErrorLinkTextColor());
//                ds.setUnderlineText(true);
//            }
//        }, textPre.length(), textPre.length() + textPost.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return ss;
//    }

    private int getErrorLinkTextColor() {
        return getResources().getColor(R.color.redD5);
    }

    protected abstract void onErrorTextClick();

    protected void showEmpty() {
        mAdapter.setInfos(new ArrayList<>());
        if (isShowEmpty()) {

            if (!isEmptyInflate) {
                if (refreshLayout != null) {
                    refreshLayout.setVisibility(View.GONE);
                }

                if (vsEmpty != null) {
                    emptyGroup = (ViewGroup) vsEmpty.inflate();
                    if (emptyGroup != null) {
                        TextView textView = emptyGroup.findViewById(R.id.text);
                        ImageView imageView = emptyGroup.findViewById(R.id.image);
                        refreshLayoutInEmpty = emptyGroup.findViewById(R.id.refresh_layout);
                        refreshLayoutInEmpty.setOnRefreshListener(this::refreshInternal);
                        textView.setText(getText());
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        imageView.setImageResource(getDrawableId());
                    }
                }
            } else {
                if (emptyGroup != null) {
                    emptyGroup.setVisibility(View.VISIBLE);
                }

                if (refreshLayout != null) {
                    refreshLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    @NonNull
    private CharSequence getText() {

        String textPost = getEmptyTextPost();
        String textPre = getEmptyTextPre() + "\n";

        if (!TextUtils.isEmpty(textPost)) {
            SpannableString ss = new SpannableString(textPre + textPost);

            ss.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    onEmptyTextClick();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getLinkTextColor());
                    ds.setUnderlineText(true);
                }
            }, textPre.length(), textPre.length() + textPost.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return ss;
        }

//        ss.setSpan(object : ClickableSpan() {
//
//            override fun updateDrawState(ds: TextPaint?) {
//                super.updateDrawState(ds)
//                ds?.color = resources.getColor(R.color.blue00)
//                ds?.isUnderlineText = true
//            }
//
//            override fun onClick(widget: View?) {
//
//                H5DocAct.start(activity, Utils.getString(R.string.fee_doc), H5DocUrl.FEE_DOC_URL)
////                Toast.makeText(activity, "费率声明被点击了", Toast.LENGTH_SHORT).show()
//            }
//        }, ss.length - 4, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

//        tv_fee?.text = ss
//        tv_fee?.movementMethod = LinkMovementMethod.getInstance()
        return textPre;
    }

    private int getLinkTextColor() {
        return getResources().getColor(R.color.orangef5);
    }

    protected void onEmptyTextClick() {

    }

    protected boolean isShowEmpty() {
        return false;
    }

    protected String getEmptyTextPost() {
        return "";
    }

    protected int getDrawableId() {
        return R.drawable.ic_page_state_no_data;
    }

    protected String getEmptyTextPre() {
        return "";
    }

    protected abstract void onRefresh(RefreshLayout refreshlayout, int pageNo, boolean isLoadMore);

    protected abstract DefaultAdapter<T> getAdapter();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract boolean isEnableRefresh();

    protected boolean isEnableLoadMore() {
        return false;
    }


}
