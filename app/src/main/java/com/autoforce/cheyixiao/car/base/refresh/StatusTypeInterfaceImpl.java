package com.autoforce.cheyixiao.car.base.refresh;

/**
 * Created by xialihao on 2018/12/8.
 */
public class StatusTypeInterfaceImpl implements StatusTypeInterface {

    private int viewType = 0;

    @Override
    public int getViewType() {
        return viewType;
    }

    @Override
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
