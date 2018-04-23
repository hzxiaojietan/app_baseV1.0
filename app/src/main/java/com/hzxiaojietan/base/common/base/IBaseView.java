package com.hzxiaojietan.base.common.base;

/**
 * Created by xiaojie.tan on 2017/10/26
 * IBaseView
 */
public interface IBaseView<T> {

    void setPresenter(T presenter);

    void showProgress(String message);

    void hideProgress();

    void showToast(int resId);

    void showToast(String msg);

    void finish();
}
