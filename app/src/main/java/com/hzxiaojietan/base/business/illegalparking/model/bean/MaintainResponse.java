package com.hzxiaojietan.base.business.illegalparking.model.bean;


import com.hzxiaojietan.base.common.base.BaseBean;

/**
 * Created by xiaojie.tan on 2017/10/26
 * MaintainResponse
 */
public class MaintainResponse<T> implements BaseBean {
    //返回码 1：成功 其他：失败
    public int Code;

    //业务数据
    public T Msg;
}
