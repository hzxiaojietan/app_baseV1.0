package com.hzxiaojietan.base.business.common;


import com.hzxiaojietan.base.common.base.BaseBean;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 全局配置信息
 */
public class GlobalSettingInfo implements BaseBean {
    //资讯系统跳转地址
    public String cmsUrl;

    //套餐详情页面地址 http://3154.xinpeiauto.com/XB_Promotions/PackageDetail?TID=7&UID={openUid}
    public String tcDetailUrl;

    //信配汽车保养套餐url地址前缀 http://211.155.229.230:8888/API/Package/Select_Products?CityCode=330100&CurrentPage=1&PageSize=10
    public String tcListUrl;
}
