package com.hzxiaojietan.base.common.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.common.utils.ActivityUtils;
import com.hzxiaojietan.base.common.utils.AppLog;
import com.hzxiaojietan.base.common.utils.CheckUtils;


/**
 * Created by xiaojie.tan on 2017/10/26
 * 只是添加一个fragment的Activity
 */
public abstract class BaseFragmentActivity<T extends Fragment> extends BaseActivity {

    protected T mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        initData();
        addFragment();
    }

    protected void initData() {

    }

    protected void addFragment() {
        mFragment = (T) getSupportFragmentManager().findFragmentById(R.id.layout_content);

        if (mFragment != null) {
            AppLog.e(getClass().getSimpleName(), "mFragment is: " + mFragment.getClass().getSimpleName());
        }
        if (mFragment == null) {
            mFragment = createFragment();
            CheckUtils.checkNotNull(mFragment, "fragment is NULL");
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.layout_content);
        }
    }

    protected abstract T createFragment();
}
