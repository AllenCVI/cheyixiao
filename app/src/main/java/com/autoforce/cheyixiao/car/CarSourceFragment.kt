package com.autoforce.cheyixiao.car

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.autoforce.cheyixiao.R
import com.autoforce.cheyixiao.base.BaseConstant
import com.autoforce.cheyixiao.base.BaseFragment
import com.autoforce.cheyixiao.car.search.PublishSearchAct
import com.autoforce.cheyixiao.car.search.SearchChildFragment
import com.autoforce.cheyixiao.car.source.PublishSourceAct
import com.autoforce.cheyixiao.car.source.SourceChildFragment
import com.autoforce.cheyixiao.car.source.me.MyCarSourceAct
import com.autoforce.cheyixiao.common.StatusBarUtil
import com.autoforce.cheyixiao.common.UMengStatistics
import com.autoforce.cheyixiao.common.UserInfoCheckManager
import com.autoforce.cheyixiao.common.utils.DrawableUtils
import kotlinx.android.synthetic.main.frag_car_source.*

/**
 * Created by xialihao on 2018/11/15.
 */
class CarSourceFragment : BaseFragment() {

    private var currentTabIndex: Int = -1
    private val rbIds = intArrayOf(R.id.rb_source, R.id.rb_search)
    private var mCheckManager: UserInfoCheckManager? = null

    companion object {

        private const val STATE_CURRENT_TAB_INDEX = "StateCurrentTabIndex"
        private const val FRAGMENT_TAG_PREFIX = "CarSourceFragment_"

        fun newInstance(): CarSourceFragment {
            return CarSourceFragment()
        }
    }

    override fun provideContentViewId(): Int {
        return R.layout.frag_car_source
    }

    private var goFragmentFlag = false
    override fun initView(savedInstanceState: Bundle?) {

        val layoutParams =
            RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = context!!.resources.getDimension(R.dimen.dp_30).toInt()
        layoutParams.setMargins(0, StatusBarUtil.getStatuHight(context), 0, 0)
        rl_top.layoutParams = layoutParams

        DrawableUtils.resizeDrawable(tv_publish, DrawableUtils.DRAWABLE_LEFT, 3 / 4f)

        rg_tab.setOnCheckedChangeListener { _, checkedId ->
            val index = rbIds.indexOf(checkedId)
            showTab(index)
        }

        tv_publish.setOnClickListener {

            if (mCheckManager == null) {
                mCheckManager = UserInfoCheckManager(activity!!)
            }

            mCheckManager?.checkUserInfo(object : UserInfoCheckManager.OnCheckResultListener() {
                override fun onCertPass() {
                    jumpSecondPage()
                }
            })
        }

        btn_bottom.setOnClickListener {
            UMengStatistics.statisticEventNumber("carsource_mycar")
            MyCarSourceAct.start(context!!)
        }

        val index = arguments?.getInt(BaseConstant.GOFRAGMENT)

        if(index!=null&&index==1){
            goFragmentFlag = true
            rg_tab.check(rbIds[1])
        }else{
            rg_tab.check(rbIds[savedInstanceState?.getInt(CarSourceFragment.STATE_CURRENT_TAB_INDEX) ?: 0])
            goFragmentFlag = false
        }

    }


    override fun onResume() {
        super.onResume()

        val index = arguments?.getInt(BaseConstant.GOFRAGMENT)
        if(index!=null&&index==1){
            goFragmentFlag = true
            rg_tab.check(rbIds[1])
            val fragment = childFragmentManager.findFragmentByTag(genFragmentTag(1))
            if (fragment != null && fragment is SearchChildFragment) {
                goFragment(fragment!!, 2)
            }
        }else{
            goFragmentFlag = false
        }

    }


    override fun onHiddenChanged(hidden: Boolean) {

        if(!hidden){
            val index = arguments?.getInt(BaseConstant.GOFRAGMENT)
            if(index!=null&&index==1){
                goFragmentFlag = true
                rg_tab.check(rbIds[1])
                val fragment = childFragmentManager.findFragmentByTag(genFragmentTag(1))
                if (fragment != null && fragment is SearchChildFragment) {
                    goFragment(fragment!!, 2)
                }

            }else{
                goFragmentFlag = false
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        mCheckManager?.dispose()
    }


    fun check(index: Int){
        rg_tab.check(rbIds[index])
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CarSourceFragment.STATE_CURRENT_TAB_INDEX, currentTabIndex)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            currentTabIndex = savedInstanceState.getInt(CarSourceFragment.STATE_CURRENT_TAB_INDEX)
        }
    }

    /*-----------------------------private-------------------------*/

    private fun showTab(index: Int) {

        if (index != currentTabIndex) {
            changeBottomButtonText(index)
            changeFragment(index, currentTabIndex)
            currentTabIndex = index
        }
    }

    private fun changeBottomButtonText(index: Int) {

        btn_bottom.visibility = if (index == 1) View.GONE else View.VISIBLE
    }

    private fun changeFragment(newTabIndex: Int, oldTabIndex: Int) {

        val ft = childFragmentManager.beginTransaction()

        val currentFragment = if (oldTabIndex >= 0) childFragmentManager.findFragmentByTag(
            genFragmentTag(oldTabIndex)
        ) else null
        if (currentFragment != null) {
            ft.hide(currentFragment)
        }

        var targetFragment = childFragmentManager.findFragmentByTag(genFragmentTag(newTabIndex))

        if (targetFragment == null) {
            targetFragment = createFragment(newTabIndex)
            ft.add(R.id.fl_content, targetFragment, genFragmentTag(newTabIndex))
        } else {
            ft.show(targetFragment)
        }
        ft.commit()

        if(goFragmentFlag&&targetFragment is SearchChildFragment){
            goFragment(targetFragment,2)
        }else{
           goFragment(targetFragment,-1)
        }

    }


    private fun goFragment(targetFragment: Fragment,index: Int){

        val bundle = Bundle()
        bundle.putInt(BaseConstant.GOFRAGMENT, index)
        targetFragment.setArguments(bundle)
        goFragmentFlag = false

    }




    private fun genFragmentTag(index: Int): String {
        return CarSourceFragment.FRAGMENT_TAG_PREFIX + index
    }

    private fun createFragment(index: Int): Fragment {

        return when (index) {
            0 -> SourceChildFragment.newInstance()
            else -> SearchChildFragment.newInstance()
        }

    }

    fun handleSourceChildFragment(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = childFragmentManager.findFragmentByTag(genFragmentTag(0))

        if (fragment != null && fragment is SourceChildFragment) {
            fragment.handlePriceResult(requestCode, resultCode, data)
        }

    }

    private fun jumpSecondPage() {
        if (currentTabIndex == 0) {
            UMengStatistics.statisticEventNumber("carsource_upload")
            if (activity != null) {
                PublishSourceAct.start(activity!!)
            }
        } else if (currentTabIndex == 1) {
            UMengStatistics.statisticEventNumber("seekcar_search")
            if (activity != null) {
                PublishSearchAct.start(activity!!)
            }
        }
    }

}
