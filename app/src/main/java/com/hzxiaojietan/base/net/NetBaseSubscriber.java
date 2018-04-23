package com.hzxiaojietan.base.net;

import android.util.Log;

import com.hzxiaojietan.base.application.ParkApplication;
import com.hzxiaojietan.base.common.utils.AppLog;
import com.hzxiaojietan.base.common.utils.ToastUtils;
import com.hzxiaojietan.base.common.utils.Util;
import com.hzxiaojietan.base.event.LoginTokenInvalidEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 订阅者基类
 */
public class NetBaseSubscriber<T> extends rx.Subscriber<T> {

    public static String ERROR_MSG = "网络异常,请重新尝试";
    public NetBaseSubscriber() {

    }

    @Override
    /**
     * 事件开始前的处理
     */
    public void onStart() {
        super.onStart();
//        int tokenStatus = FZLoginUserManager.getInstance().tokenCheck();
//        if(tokenStatus == FZLoginUserManager.TOKEN_EXPIRED){
//            //过期直接终止请求
//        }else if(tokenStatus == FZLoginUserManager.TOKEN_FRESH){
//            //异步刷新 token
//        }
    }

    @Override
    /**
     * 事件完成 子类不要继承
     */
    public void onCompleted() {
    }

    @Override
    /**
     * 事件出错 子类不要继承
     */
    public void onError(final Throwable e) {
        Util.dismissHUD();
        if (e != null) {
            AppLog.e(e.getMessage());
        }
        String errorMsg = ERROR_MSG;
        if (e != null && e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int httpResponseCode = httpException.code();
            if (httpResponseCode == 403) {
                EventBus.getDefault().post(new LoginTokenInvalidEvent());
                errorMsg = "登录已过期，请重新登录";
            } else {
                Response response = httpException.response();
                if (response != null) {
                    Headers responseHeaders = response.headers();
                    String result  = responseHeaders.get("lujing_error");
                    try{
                        if (result != null) {
                            errorMsg = URLDecoder.decode(result, "UTF-8");
                        } else {
                            errorMsg = "服务器出错了，请稍后重试";
                        }
                    }catch (UnsupportedEncodingException e2){
                        errorMsg = "服务器出错了，请稍后重试";
                    }
                } else {
                    errorMsg =  "请求出错，请稍后再试";
                }
            }
        }
        onFail(errorMsg);
    }

    @Override
    /**
     * 事件响应 子类不要继承
     */
    public void onNext(T response) {
        try {
            Util.dismissHUD();
            onSuccess(response);
        }catch (Exception e) {
            onFail(ERROR_MSG);
            //这里添加这个是因为如果在成功回调方法里发生异常，log里不会打印出来，导致调试的时候看不到问题
            Log.e("error in success", "message:", e);
            throw e;
        }
    }

    /**
     * 请求成功 子类继承
     *
     * @param response
     */
    public void onSuccess(T response) {

    }

    /**
     * 请求失败,子类继承
     *
     * @param message
     */
    public void onFail(String message) {
        if (message != null) {
            ToastUtils.show(ParkApplication.getContext(), message);
        } else {
            ToastUtils.show(ParkApplication.getContext(), ERROR_MSG);
        }
    }
}
