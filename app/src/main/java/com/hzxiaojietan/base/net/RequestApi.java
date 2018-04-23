package com.hzxiaojietan.base.net;

import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.business.illegalparking.model.bean.User;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 服务器接口
 */

public interface RequestApi {

    @GET("v2/getGlobalSetting")
    Observable<MyMaintainListInfo> getMyMaintainList(@Query("userId") String userId);

    /**
     * 绑定第三方账号
     * @param params
     * @return
     */
    @POST("user/bind")
    Observable<User> bindThird(@Body Map<String, String> params);

    /**
     * 解除第三方账号绑定
     * @param params
     * @return
     */
    @POST("user/bind/remove")
    Observable<User> unBindThird(@Body Map<String, String> params);
}
