package com.hzxiaojietan.base.net;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by xiaojie.tan on 2017/10/26
 * 订阅请求数据执行者
 */
public class NetBaseSubscription {

    @SuppressWarnings("unchecked")
    public static Subscription subscription(@NonNull Observable observable, @NonNull Subscriber subscriber){

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
