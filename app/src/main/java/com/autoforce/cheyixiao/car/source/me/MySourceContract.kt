package com.autoforce.cheyixiao.car.source.me

import com.autoforce.cheyixiao.car.base.refresh.IRecyclerView
import com.autoforce.cheyixiao.mvp.IPresenter
import com.autoforce.cheyixiao.mvp.IView

/**
 *  Created by xialihao on 2018/11/29.
 */

class MySourceContract{

    interface Presenter : IPresenter

    interface View : IView<Presenter>, IRecyclerView<String>
}