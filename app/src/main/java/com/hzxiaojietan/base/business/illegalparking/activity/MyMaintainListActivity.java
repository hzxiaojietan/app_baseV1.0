package com.hzxiaojietan.base.business.illegalparking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hzxiaojietan.base.business.illegalparking.presenter.MyMaintainListPresenter;
import com.hzxiaojietan.base.business.illegalparking.view.MyMaintainListFragment;
import com.hzxiaojietan.base.common.base.BaseFragmentActivity;

/**
 * 我的已购买汽车保养套餐列表页面
 * Created by xiaojie.tan on 2017/10/26
 * MyMaintainListActivity
 */
public class MyMaintainListActivity extends BaseFragmentActivity<MyMaintainListFragment> {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MyMaintainListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("我的套餐");
        //Presenter和View互相绑定
        new MyMaintainListPresenter(mFragment);
    }

    @Override
    protected MyMaintainListFragment createFragment() {
        return new MyMaintainListFragment();
    }
}
