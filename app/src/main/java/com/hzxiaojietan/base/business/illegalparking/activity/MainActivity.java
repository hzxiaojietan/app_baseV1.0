package com.hzxiaojietan.base.business.illegalparking.activity;

import android.os.Bundle;

import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.business.common.AppStatusConstant;
import com.hzxiaojietan.base.business.common.AppStatusManager;
import com.hzxiaojietan.base.common.base.BaseActivity;
import com.hzxiaojietan.base.common.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进入应用初始化设置成正常状态
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void myClick() {
        ToastUtils.show(this,"基础项目移植成功！");
    }
}
