package com.hzxiaojietan.base.business.illegalparking.model.bean;

import com.hzxiaojietan.base.common.base.BaseBean;

/**
 * 获取我已购买的汽车服务套餐列表数据
 * Created by xiaojie.tan on 2017/10/26
 */

public class MyMaintainListInfo implements BaseBean {
    public String imgUrl;   // 套餐图片url
    public String money;    // 金额
    public String orderNo;  // 订单比那好
    public String time; // 创建时间
}
