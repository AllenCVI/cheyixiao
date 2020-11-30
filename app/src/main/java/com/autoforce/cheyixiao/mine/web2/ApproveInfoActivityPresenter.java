package com.autoforce.cheyixiao.mine.web2;

import android.content.Context;
import okhttp3.RequestBody;

import java.util.Map;

public interface ApproveInfoActivityPresenter {
    void postApprove(Map<String , Object> map, Context context , boolean isShowLoading);
}
