package com.autoforce.cheyixiao.mine.minemvp;

import com.autoforce.cheyixiao.common.utils.StringUtils;

public class ChangePwdActivityPresenterImpl implements ChangePwdActivityPresenter {

    private ChangePwdActivityView changePwdActivityView;

    public ChangePwdActivityPresenterImpl(ChangePwdActivityView changePwdActivityView) {
        this.changePwdActivityView = changePwdActivityView;
    }

    @Override
    public void checkPwd(String currentPwd, String newPwd, String sureNewPwd) {
        if(StringUtils.isEmpty(currentPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(sureNewPwd)){
//            ToastUtil.showToast("所有输入均不能为空！");
            changePwdActivityView.isPass(false,"所有输入均不能为空！");
        }else {
            if(!newPwd.equals(sureNewPwd)){
//                ToastUtil.showToast("新密码与确认密码不一致！请重新输入！");
                changePwdActivityView.isPass(false , "新密码与确认密码不一致！请重新输入！");
            }else {

                changePwdActivityView.isPass(true , "");
            }
        }
    }
}
