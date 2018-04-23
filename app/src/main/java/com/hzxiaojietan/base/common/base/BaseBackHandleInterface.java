package com.hzxiaojietan.base.common.base;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 宿主FragmentActivity需要继承BaseBackHandleInterface，
 * 子Fragment会通过该接口告诉宿主FragmentActivity自己是当前屏幕可见的Fragment。
 */
public interface BaseBackHandleInterface {
       void setSelectedFragment(BaseFragment selectedFragment);
}
