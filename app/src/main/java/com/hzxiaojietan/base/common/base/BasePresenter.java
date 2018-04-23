package com.hzxiaojietan.base.common.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by xiaojie.tan on 2017/10/26
 * BasePresenter
 */
public class BasePresenter implements IBasePresenter {
    protected String TAG = getClass().getSimpleName();
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.unsubscribe();
        mSubscriptions = new CompositeSubscription();
    }
}
