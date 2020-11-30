package com.autoforce.cheyixiao.mine.minemvp;

import com.autoforce.cheyixiao.common.data.remote.bean.BlankCardInfo;
import com.autoforce.cheyixiao.common.data.remote.bean.CanMoneyBean;

public interface MineBalanceFragmentView {

    void showData(CanMoneyBean data);

    void shoDialogData(BlankCardInfo data);
}
