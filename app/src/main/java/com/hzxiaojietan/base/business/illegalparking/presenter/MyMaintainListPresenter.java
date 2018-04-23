package com.hzxiaojietan.base.business.illegalparking.presenter;

import com.hzxiaojietan.base.business.illegalparking.contract.MyMaintainListContract;
import com.hzxiaojietan.base.business.illegalparking.model.MaintainModel;
import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.common.base.BasePresenter;
import com.hzxiaojietan.base.common.utils.Util;
import com.hzxiaojietan.base.net.NetBaseSubscriber;
import com.hzxiaojietan.base.net.NetBaseSubscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaojie.tan on 2017/10/26
 * MyMaintainListPresenter
 * 我已订购的汽车套餐列表Presenter
 */
public class MyMaintainListPresenter extends BasePresenter implements MyMaintainListContract.Presenter {
    private static final int PAGE_SIZE = 20;
    private MyMaintainListContract.View mView;

    private MaintainModel mMaintainModel;
    private List<MyMaintainListInfo> mMyMaintainList = new ArrayList<>();

    public MyMaintainListPresenter(MyMaintainListContract.View view) {
        mView = view;
        mMaintainModel = new MaintainModel();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        super.subscribe();
        mView.showLoading();
        mMyMaintainList.clear();
        getMyMaintainList(false,true);
    }

    @Override
    public void getMyMaintainList(boolean loadMore, boolean fristLoad) {
        mSubscriptions.add(NetBaseSubscription.subscription(mMaintainModel.getMyMaintainList(Util.getCurrentUser().getUserId()+""),
                new NetBaseSubscriber<List<MyMaintainListInfo>>() {
                    @Override
                    public void onSuccess(List<MyMaintainListInfo> response) {
                        super.onSuccess(response);
                        if (response!= null && !response.isEmpty()) {
                            mMyMaintainList.addAll(response);
                            mView.showList(false);
                        } else {
                            if(mMyMaintainList.isEmpty()){
                                mView.showEmpty();
                            }else{
                                mView.showList(false);
                            }
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        /*super.onFail(message);
                        mView.showError();*/
                        // 测试数据
                        if(mMyMaintainList.size() != 0){
                            return;
                        }
                        List<MyMaintainListInfo> dataList = new ArrayList<MyMaintainListInfo>();
                        for(int i = 0; i < 5 ; i++){
                            MyMaintainListInfo item = new MyMaintainListInfo();
                            item.money = 500+i+"";
                            item.orderNo = "20170427"+i;
                            Date  date = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            item.time = format.format(date);
                            dataList.add(item);
                        }
                        mMyMaintainList.addAll(dataList);
                        mView.reloadList(mMyMaintainList);
                        mView.showList(false);
                    }
                }));
    }
}
