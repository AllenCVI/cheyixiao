package com.autoforce.cheyixiao.car.base.refresh;

import java.util.List;

/**
 * Created by xialihao on 2018/11/29.
 */
public interface IRecyclerView<T> {

    void onDataGot(List<T> data, boolean isLoadMore);

    void onError(String errMsg);
}

