package com.autoforce.cheyixiao.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.autoforce.cheyixiao.R;
import com.autoforce.cheyixiao.base.BaseToolbarActivity;
import com.autoforce.cheyixiao.common.utils.ToastUtil;
import com.autoforce.cheyixiao.mine.minemvp.ChangePwdActivityPresenterImpl;
import com.autoforce.cheyixiao.mine.minemvp.ChangePwdActivityView;

public class ChangePwdActivity extends BaseToolbarActivity implements View.OnClickListener ,ChangePwdActivityView {


    @BindView(R.id.current_pwd)
    EditText currentPwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.sure_new_pwd)
    EditText sureNewPwd;
    @BindView(R.id.sure)
    TextView sure;
    private ChangePwdActivityPresenterImpl changePwdActivityPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbarTitle.setText(getResources().getString(R.string.change_pwd_title));
        sure.setOnClickListener(this);

        changePwdActivityPresenter = new ChangePwdActivityPresenterImpl(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sure:
                String currentPwd = this.currentPwd.getText().toString().trim();
                String newPwd = this.newPwd.getText().toString().trim();
                String sureNewPwd = this.sureNewPwd.getText().toString().trim();
                changePwdActivityPresenter.checkPwd(currentPwd , newPwd , sureNewPwd);
                sure.setClickable(false);
                break;
        }
    }

    @Override
    public void isPass(boolean isPass, String msg) {
        sure.setClickable(true);
        if(isPass){
            ToastUtil.showToast(getResources().getString(R.string.chang_pwd_success));
            finish();
        }else {
            ToastUtil.showToast(msg);
        }
    }
}
