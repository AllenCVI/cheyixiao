package com.autoforce.cheyixiao.mine.web2;

import android.app.Activity;
import android.content.Context;

import com.autoforce.cheyixiao.common.data.remote.bean.ApproveInfo;
import com.autoforce.cheyixiao.common.utils.DefaultDisposableSubscriber;
import com.autoforce.cheyixiao.mine.minenet.MineRemoteRepository;

import okhttp3.RequestBody;

import java.util.Map;

public class ApproveInfoActivityPresenterImpl implements ApproveInfoActivityPresenter {
    private ApproveInfoWebActivityView approveInfoWebActivityView;
    private DefaultDisposableSubscriber<ApproveInfo> defaultDisposableSubscriber;

    public ApproveInfoActivityPresenterImpl(ApproveInfoWebActivityView approveInfoWebActivityView) {
        this.approveInfoWebActivityView = approveInfoWebActivityView;
    }

    @Override
    public void postApprove(Map<String, Object> map, Context context, boolean isShowLoading) {
        defaultDisposableSubscriber = MineRemoteRepository.getInstance().postApproveInfo(map)
                .subscribeWith(new DefaultDisposableSubscriber<ApproveInfo>((Activity) context, isShowLoading) {
                    @Override
                    protected void success(ApproveInfo data) {
                        approveInfoWebActivityView.showNext(data, true);
                    }

                    @Override
                    protected void failure(String errMsg) {
                        approveInfoWebActivityView.showNext(errMsg, false);
                        super.failure(errMsg);
                    }
                });
    }
}
