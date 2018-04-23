package com.hzxiaojietan.base.common.base;

import com.hzxiaojietan.base.net.NetManager;
import com.hzxiaojietan.base.net.RequestApi;

/**
 * Created by xiaojie.tan on 2017/10/26
 * BaseModel
 */
public class BaseModel {

    protected RequestApi mApi;

    public BaseModel() {
        mApi =  NetManager.getInstance().getApi();
    }
}
