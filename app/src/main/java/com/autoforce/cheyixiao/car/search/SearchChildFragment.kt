package com.autoforce.cheyixiao.car.search

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseConstant
import com.autoforce.cheyixiao.common.UMengStatistics
import com.autoforce.cheyixiao.common.UserInfoCheckManager
import com.autoforce.cheyixiao.common.data.remote.bean.SearchCarListResult
import com.autoforce.cheyixiao.common.utils.NetUtils
import com.autoforce.cheyixiao.common.utils.StringUtils
import com.autoforce.cheyixiao.common.utils.ToastUtil
import com.autoforce.cheyixiao.common.view.AutoForceRefreshLayout
import com.autoforce.cheyixiao.common.view.CustomerSpinner
import com.autoforce.cheyixiao.common.view.popup.MaskPopupWindowManager
import com.autoforce.cheyixiao.mvp.BaseMvpFragment
import kotlinx.android.synthetic.main.frag_search_child.*

/**
 *  Created by xialihao on 2018/11/16.
 */
class SearchChildFragment : BaseMvpFragment<SearchChildContract.Presenter>(), SearchChildContract.View {

    private var mAdapter: SearchListAdapter? = null
    private var mBrandPos: Int = 0
    private var mSeriesPos: Int = 0
    private var mCheckManager: UserInfoCheckManager? = null

    companion object {
        fun newInstance(): SearchChildFragment {
            val frag = SearchChildFragment()
            return frag
        }
    }

    override fun provideContentViewId(): Int {
        return R.layout.frag_search_child
    }

    override fun initView(savedInstanceState: Bundle?) {

//        page_state_view.bindView(recycler_view)

        // 看后期改动，如果改动多则重构成多个子Fragment，这个地方实际上违背了单一职责原则，还好仅仅只是展示
        rg_tab.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_all -> {
                    if (rb_all.tag == null) {
                        mPresenter?.loadCacheData(0)
                        rb_all.tag = true
                    } else {
                        mPresenter?.loadAllData()
                    }
                }
                R.id.rb_mine -> {
                    if (rb_mine.tag == null) {
                        mPresenter?.loadCacheData(1)
                        rb_mine.tag = true
                    } else {
                        mPresenter?.loadMyData()
                    }
                }
                R.id.rb_receive -> {
                    if (rb_receive == null) {
                        mPresenter?.loadCacheData(2)
                        rb_receive.tag = true
                    } else {
                        mPresenter?.loadReceiveData()
                    }
                }
                R.id.rb_publish -> {
                    if (rb_publish == null) {
                        mPresenter?.loadCacheData(3)
                        rb_publish.tag = true
                    } else {
                        mPresenter?.loadPublishData()
                    }
                }
            }
        }

        addFilterListener(tv_brand)
        addFilterListener(tv_car_type)

        mAdapter = SearchListAdapter()
        recycler_view.adapter = mAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        mAdapter?.setOnItemClickListener { _, _, data, _ ->

            if (mCheckManager == null) {
                mCheckManager = UserInfoCheckManager(activity!!)
            }

            mCheckManager?.checkUserInfo(object : UserInfoCheckManager.OnCheckResultListener() {
                override fun onCertPass() {
                    if (activity != null) {
                        val resultsBean = data as SearchCarListResult.ResultsBean
                        UMengStatistics.statisticEventNumber("searchlist_click", resultsBean.spec)
                        SearchDetailAct.start(activity!!, resultsBean.findId.toString())
                    }
                }
            })

        }

        refresh_layout.setAutoForceRefreshListener(object : AutoForceRefreshLayout.OnAutoForceRefreshListener() {
            override fun onRefresh(autoForceRefreshLayout: AutoForceRefreshLayout?) {
                autoForceRefreshLayout?.setAutoForceLoadMoreEnabled(true)
                loadData(false)
            }

            override fun onLoadMore(autoForceRefreshLayout: AutoForceRefreshLayout?) {
                loadData(true)
            }
        })

    }

    override fun initData() {
        super.initData()
        SearchChildPresenter(this)
        rg_tab.check(R.id.rb_all)
        val index = arguments?.getInt(BaseConstant.GOFRAGMENT)
        if(index!=null&&index==2){
            rg_tab.check(R.id.rb_receive)
        }
    }


    override fun onResume() {
        super.onResume()
        val index = arguments?.getInt(BaseConstant.GOFRAGMENT)
        if (index != null && index == 2) {
            rg_tab.check(R.id.rb_receive)
        }

    }



    override fun onHiddenChanged(hidden: Boolean) {

        if(!hidden) {
            val index = arguments?.getInt(BaseConstant.GOFRAGMENT)
            if (index != null && index == 2) {
                rg_tab.check(R.id.rb_receive)
            }

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        mCheckManager?.dispose()
    }

    override fun onDataGot(
        results: List<SearchCarListResult.ResultsBean>?,
        isLoadMore: Boolean
    ) {
        if (isLoadMore) {
            recycler_view.stopScroll()
            refresh_layout.finishAutoForceLoadMore()
            if (results != null && results.isNotEmpty()) {
                mAdapter?.appendInfos(results)
            } else {
                ToastUtil.showToast(R.string.no_more_data)
                refresh_layout.setAutoForceLoadMoreEnabled(false)
            }

        } else {
            if (refresh_layout.isAutoForceRefreshing) {
                refresh_layout.finishAutoForceRefresh()
            }

            if (results == null || results.isEmpty()) {
                // 没有数据
                // 需要展示空数据页面
//                page_state_view.showNoData()
                mAdapter?.showEmpty(150)
            } else {
//                page_state_view.showNomalData()
                mAdapter?.infos = results
            }

//            mAdapter?.infos = results
        }
    }

    override fun onDataError(isLoadMore: Boolean) {
        if (isLoadMore) {
            refresh_layout.finishAutoForceLoadMore()
            ToastUtil.showToast(R.string.load_more_error)
        } else {
            refresh_layout.finishAutoForceRefresh(false)

            val infos = mAdapter?.infos

            if (infos != null && infos.isEmpty()) {
//                page_state_view.showNoNetWork()
                mAdapter?.showNoNetWork(150)
            }
        }
    }

    override fun onCacheDataLoad(results: List<SearchCarListResult.ResultsBean>?) {

        // 本地有缓存
        if (results != null) {
            mAdapter?.infos = results
        }

        if (NetUtils.isConnected(context)) {
            refresh_layout.autoForceRefreshingAutomatic(300)
        } else {
            ToastUtil.showToast(R.string.warnning)

            if (results == null || results.isEmpty()) {
//                page_state_view.showNoNetWork()
                mAdapter?.showNoNetWork(150)
            }
        }
    }

    override fun onBrandsGot(brandArray: Array<String?>) {

        MaskPopupWindowManager.Builder()
            .setAnchorView(tv_brand)
            .setStringArray(brandArray)
            .setShowItemCount(7)
            .setSelectedPos(mBrandPos)
            .setOnDismissListener(
                object : MaskPopupWindowManager.OnDismissListener {
                    override fun onDismiss() {
                        tv_brand.closeList()
                    }
                }
            )
            .setOnItemClickListener(object : MaskPopupWindowManager.OnItemClickListener {
                override fun onItemClick(position: Int, string: String?) {

                    // 全部的话还是显示车源类型
                    tv_brand.setText(if (position == 0) StringUtils.getString(R.string.brand) else string)
                    mBrandPos = position
                    loadData(false)
                }
            })
            .show(activity!!)
    }

    override fun onSeriesGot(t: Array<String?>) {

        MaskPopupWindowManager.Builder()
            .setAnchorView(tv_car_type)
            .setStringArray(t)
            .setShowItemCount(7)
            .setSelectedPos(mSeriesPos)
            .setOnDismissListener(
                object : MaskPopupWindowManager.OnDismissListener {
                    override fun onDismiss() {
                        tv_car_type.closeList()
                    }
                }
            )
            .setOnItemClickListener(object : MaskPopupWindowManager.OnItemClickListener {
                override fun onItemClick(position: Int, string: String?) {

                    // 全部的话还是显示车源类型
                    tv_car_type.setText(if (position == 0) StringUtils.getString(R.string.car_type) else string)
                    mSeriesPos = position
                    loadData(false)
                }
            })
            .show(activity!!)
    }

    override fun onBrandNotSelected(msg: String) {
        ToastUtil.showToast(R.string.please_select_brand_first)
        tv_car_type.closeList()
    }

    override fun getBrandPos() = mBrandPos

    override fun getSeriesPos() = mSeriesPos

    /*-----------------------------private------------------------------*/
    private fun loadData(isLoadMore: Boolean) {
        when {
            rb_all.isChecked -> mPresenter?.loadAllData(isLoadMore = isLoadMore)
            rb_mine.isChecked -> mPresenter?.loadMyData(isLoadMore)
            rb_receive.isChecked -> mPresenter?.loadReceiveData(isLoadMore)
            rb_publish.isChecked -> mPresenter?.loadPublishData(isLoadMore)
        }
    }

    private fun addFilterListener(spinner: CustomerSpinner) {

        spinner.setOnClickChangeListener(object : CustomerSpinner.OnClickChangeListener {
            override fun onClose() {
                spinner.setTextColor(ContextCompat.getColor(activity!!, R.color.black26))
            }

            override fun onShow() {

                spinner.setTextColor(ContextCompat.getColor(activity!!, R.color.redD5))
                when (spinner) {
                    tv_brand -> mPresenter?.loadCarBrands()
                    tv_car_type -> mPresenter?.loadSeriesByBrand()
                }

            }
        })
    }
}