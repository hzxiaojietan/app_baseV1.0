package com.hzxiaojietan.base.business.illegalparking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzxiaojietan.base.business.illegalparking.contract.MyMaintainListContract;
import com.hzxiaojietan.base.business.illegalparking.model.bean.MyMaintainListInfo;
import com.hzxiaojietan.base.business.illegalparking.view.viewholder.MyMaintainListVH;
import com.hzxiaojietan.base.common.base.BaseRecyclerFragment;
import com.hzxiaojietan.base.common.baseui.BaseViewHolder;
import com.hzxiaojietan.base.common.baseui.CommonRecyclerAdapter;
import com.hzxiaojietan.base.common.baseui.refreshview.IRefreshListener;

import java.util.List;


/**
 * Created by Jake on 2017/4/21.
 * 信配 我已购买汽车保养套餐列表页面
 */
public class MyMaintainListFragment extends BaseRecyclerFragment<MyMaintainListContract.Presenter>
        implements MyMaintainListContract.View {
    private ViewGroup mRootView;
    private CommonRecyclerAdapter<MyMaintainListInfo> mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        mRefreshRecyclerView.setRefreshEnable(false);
        mRefreshRecyclerView.setRefreshListener(new IRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyMaintainList(false, false);
            }

            @Override
            public void onLoadMore() {
                mPresenter.getMyMaintainList(true, false);
            }
        });

        mAdapter = new CommonRecyclerAdapter<MyMaintainListInfo>() {

            @Override
            public BaseViewHolder<MyMaintainListInfo> createViewHolder(int type) {
                return new MyMaintainListVH();
            }
        };

        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO:跳转到已购买套餐详情页

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRefreshRecyclerView.setLayoutManager(layoutManager);
        mRefreshRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void showList(boolean isHasMore) {
        super.showList(isHasMore);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void reloadList(List<MyMaintainListInfo> data) {
        mAdapter.setDatas(data);
    }
}
