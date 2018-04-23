package com.hzxiaojietan.base.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hzxiaojietan.base.application.ParkApplication;
import com.hzxiaojietan.base.business.illegalparking.model.bean.User;
import com.hzxiaojietan.base.common.utils.CheckUtils;
import com.hzxiaojietan.base.common.utils.ToastUtils;
import com.hzxiaojietan.base.common.utils.Util;


/**
 * Created by xiaojie.tan on 2017/10/26
 * fragment基类
 */
public class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView<T> {
    protected String TAG = this.getClass().getSimpleName();

    protected Activity mActivity;
    protected T mPresenter;
    protected BaseBackHandleInterface mBackHandledInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseBackHandleInterface){
            this.mBackHandledInterface = (BaseBackHandleInterface)context;
         }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //告诉FragmentActivity，当前Fragment在栈顶
            if(mBackHandledInterface != null){
                mBackHandledInterface.setSelectedFragment(this);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    /**
     * Activity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消费时FragmentActivity自己才会消费该事件，默认不消费，需要时子类重写该方法
     * @return
     */
    public boolean onBackPressed(){
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public User getUser() {
        return Util.getCurrentUser();
    }

    @Override
    public void setPresenter(T presenter) {
        mPresenter = CheckUtils.checkNotNull(presenter);
    }

    @Override
    public void showProgress(String message) {
        Util.showHUD(mActivity, message);
    }

    @Override
    public void hideProgress() {
        Util.dismissHUD();
    }

    @Override
    public void showToast(int resId) {
        ToastUtils.show(ParkApplication.getAppContext(), resId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(ParkApplication.getAppContext(), msg);
    }

    @Override
    public void finish() {
        mActivity.finish();
    }
}
