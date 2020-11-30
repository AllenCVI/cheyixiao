package com.autoforce.cheyixiao.car.source

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.car.FilterPriceCustomAct
import com.autoforce.cheyixiao.common.UMengStatistics
import com.autoforce.cheyixiao.common.UserInfoCheckManager
import com.autoforce.cheyixiao.common.data.remote.bean.CarSourceListResult
import com.autoforce.cheyixiao.common.utils.*
import com.autoforce.cheyixiao.common.view.AutoForceRefreshLayout
import com.autoforce.cheyixiao.common.view.CustomerSpinner
import com.autoforce.cheyixiao.common.view.popup.MaskPopupWindowManager
import com.autoforce.cheyixiao.mvp.BaseMvpFragment
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.frag_source_child.*

/**
 *  Created by xialihao on 2018/11/16.
 *
 *
 */
class SourceChildFragment : BaseMvpFragment<SourceChildContract.Presenter>(),
    SourceChildContract.View {


    private var mAdapter: SourceListAdapter? = null
    private var mCheckManager: UserInfoCheckManager? = null

    // 车源类型选中下标
    private var mCarTypePos: Int = 0

    // 搜索内容
    private var words: String? = null

    // 车源所在地下标
    private var mAreaPos: Int = 0

    // 价格区间选中下标
    private var mPricePos: Int = -1


    companion object {

        const val CODE_PRICE = 200
        const val SOURCE_DETAIL = 201

        fun newInstance(): SourceChildFragment {
            return SourceChildFragment()
        }
    }

    /*-----------------------------system override------------------------------*/
    override fun provideContentViewId(): Int {
        return R.layout.frag_source_child
    }

    override fun initView(savedInstanceState: Bundle?) {

//        page_state_view.bindView(recycler_view)


        DrawableUtils.resizeDrawable(et_input, DrawableUtils.DRAWABLE_LEFT, 3 / 4f)
        mAdapter = SourceListAdapter()
        recycler_view.adapter = mAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        mAdapter?.setOnItemClickListener { _, _, data, _ ->

            if (mCheckManager == null) {
                mCheckManager = UserInfoCheckManager(activity!!)
            }

            mCheckManager?.checkUserInfo(object : UserInfoCheckManager.OnCheckResultListener() {
                override fun onCertPass() {
                    if (activity != null) {
                        SourceDetailAct.start(activity!!, (data as CarSourceListResult.ResultsBean).id.toString(), SOURCE_DETAIL)
                    }
                }
            })
        }

        refresh_layout.setAutoForceRefreshListener(object : AutoForceRefreshLayout.OnAutoForceRefreshListener() {
            override fun onRefresh(autoForceRefreshLayout: AutoForceRefreshLayout?) {
                autoForceRefreshLayout?.setAutoForceLoadMoreEnabled(true)
                mPresenter?.loadData()
            }

            override fun onLoadMore(autoForceRefreshLayout: AutoForceRefreshLayout?) {
                mPresenter?.loadData(true)
            }
        })

        // 搜索按钮响应
        btn_search.setOnClickListener {
            doSearch()
        }

        // 重置
        btn_reset.setOnClickListener {
            doReset()
        }

        // 车源类型筛选
        addFilterListener(tv_type)
        addFilterListener(tv_area)
        addFilterListener(tv_price)

        SourceChildPresenter(this)
    }


    override fun initData() {
        super.initData()
        mPresenter?.loadCacheData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCheckManager?.dispose()
    }


    /*---------------------------mvp override----------------------------------*/

    override fun onCacheDataLoad(results: List<CarSourceListResult.ResultsBean>?) {
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
                mAdapter?.showNoNetWork()
            }
        }
    }

    override fun onDataGot(results: List<CarSourceListResult.ResultsBean>?, isLoadMore: Boolean) {

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
                mAdapter?.showEmpty()
            } else {
//                page_state_view.showNomalData()
                mAdapter?.infos = results
            }

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
                mAdapter?.showNoNetWork()
            }
        }
    }

    override fun onCarTypesGot(showList: Array<String?>) {

        MaskPopupWindowManager.Builder()
            .setAnchorView(tv_type)
            .setStringArray(showList)
            .setSelectedPos(mCarTypePos)
            .setOnDismissListener(object : MaskPopupWindowManager.OnDismissListener {
                override fun onDismiss() {
                    tv_type.closeList()
                }
            })
            .setOnItemClickListener(
                object : MaskPopupWindowManager.OnItemClickListener {
                    override fun onItemClick(position: Int, string: String?) {

                        // 全部的话还是显示车源类型
                        tv_type.setText(if (position == 0) StringUtils.getString(R.string.car_source_type) else string)
                        mCarTypePos = position
                        mPresenter?.loadData()
                    }
                }
            )
            .show(activity!!)
    }

    override fun onAreaInfoGot(t: Array<String?>) {

        MaskPopupWindowManager.Builder()
            .setAnchorView(tv_area)
            .setStringArray(t)
            .setSelectedPos(mAreaPos)
            .setOnDismissListener(
                object : MaskPopupWindowManager.OnDismissListener {
                    override fun onDismiss() {
                        tv_area.closeList()
                    }
                }
            )
            .setOnItemClickListener(object : MaskPopupWindowManager.OnItemClickListener {
                override fun onItemClick(position: Int, string: String?) {

                    // 全部的话还是显示车源类型
                    tv_area.setText(if (position == 0) StringUtils.getString(R.string.car_source_address) else string)
                    mAreaPos = position
                    mPresenter?.loadData()
                }
            })
            .show(activity!!)

    }

    override fun getCarTypePos() = mCarTypePos

    override fun getSearchWords() = words

    override fun getAreaPos() = mAreaPos


    /*-----------------------------private------------------------------*/
    private fun doSearch() {
        KeyboardUtil.hideSoftInput(activity)
        val words = et_input.text.toString().trim()
        if (TextUtils.isEmpty(words)) {
            ToastUtil.showToast(R.string.please_input_search_content)
            return
        }

        this.words = words
        UMengStatistics.statisticEventNumber("carsource_search")
        mPresenter?.loadData()
    }

    private fun doReset() {
        if (TextUtils.isEmpty(et_input.text.toString().trim())) {
            return
        }
        et_input.setText("")
        this.words = null
        mPresenter?.loadData()
    }

    private fun addFilterListener(spinner: CustomerSpinner) {

        spinner.setOnClickChangeListener(object : CustomerSpinner.OnClickChangeListener {
            override fun onClose() {
                spinner.setTextColor(ContextCompat.getColor(activity!!, R.color.black26))
            }

            override fun onShow() {

                spinner.setTextColor(ContextCompat.getColor(activity!!, R.color.redD5))
                when (spinner) {
                    tv_type -> mPresenter?.loadCarTypeData()
                    tv_area -> mPresenter?.loadCarArea()
                    tv_price -> showPricePopup()
                }

            }
        })
    }

    private fun showPricePopup() {

        val footerView = LayoutInflater.from(activity).inflate(R.layout.footer_price_filter, null)
        val etLower: EditText = footerView.findViewById(R.id.et_lower)
        val etUpper: EditText = footerView.findViewById(R.id.et_upper)

        val manager = MaskPopupWindowManager.Builder()
            .setAnchorView(tv_price)
            .setSelectedPos(mPricePos)
            .setStringArray(resources.getStringArray(R.array.price_filter_gaps))
            .addFooterView(footerView)
            .setOnItemClickListener(object : MaskPopupWindowManager.OnItemClickListener {
                override fun onItemClick(position: Int, string: String?) {
                    tv_price.setText(string)
                    mPresenter?.cachePriceByPos(position)
                    mPricePos = position
                }
            })
            .setOnDismissListener(object : MaskPopupWindowManager.OnDismissListener {
                override fun onDismiss() {
                    tv_price.closeList()
                }
            })
            .show(activity!!)

        addPriceClickListener(etLower, manager)
        addPriceClickListener(etUpper, manager)
    }

    private fun addPriceClickListener(
        editText: EditText,
        manager: MaskPopupWindowManager
    ) {

        editText.isFocusableInTouchMode = false;
        editText.isFocusable = false

        editText.setOnClickListener {
            manager.dismiss()
            if (activity != null && parentFragment != null) {
                FilterPriceCustomAct.start(activity!!, CODE_PRICE)
            }
        }
    }

    fun handlePriceResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.e("SourceChildFragment handlePriceResult")
        if (requestCode == CODE_PRICE && resultCode == Activity.RESULT_OK) {
            val minPrice = data?.getIntExtra(FilterPriceCustomAct.INTENT_PRICE_MIN, -1)
            val maxPrice = data?.getIntExtra(FilterPriceCustomAct.INTENT_PRICE_MAX, -1)

            Logger.e("minPrice = $minPrice, maxPrice = $maxPrice")

            val unit = StringUtils.getString(R.string.ten_thousands)
            tv_price.setText("$minPrice$unit-$maxPrice$unit")
            mPresenter?.cachePrice(minPrice, maxPrice)
        }
    }

}