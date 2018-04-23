package com.hzxiaojietan.base.business.illegalparking.contract;


import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.common.base.IBaseListView;
import com.hzxiaojietan.base.common.base.IBasePresenter;
import com.hzxiaojietan.base.common.baseui.refreshview.IListDataView;

/**
 * 我已购买的套餐列表
 * Created by xiaojie.tan on 2017/10/26
 * MyMaintainListContract
 */
public interface MyMaintainListContract {

    interface View extends IBaseListView<Presenter, MyMaintainListInfo>, IListDataView {

    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取我已购买的汽车保养套餐列表
         * @param loadMore 是否加载更多
         * @param fristLoad 是否第一次加载
         */
        void  getMyMaintainList(boolean loadMore, boolean fristLoad);
    }
}
