package com.hzxiaojietan.base.business.illegalparking.model;

import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.common.base.BaseModel;

import rx.Observable;


/**
 * Created by xiaojie.tan on 2017/10/26
 */
public class MaintainModel extends BaseModel {
    public MaintainModel(){

    }

    /**
     * 获取我已购买的汽车保养套餐列表
     * @param userId
     * @return
     */
    public Observable<MyMaintainListInfo> getMyMaintainList(String userId){
        return mApi.getMyMaintainList(userId);
    }

}
