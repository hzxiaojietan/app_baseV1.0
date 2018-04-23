package com.hzxiaojietan.base.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.common.baseui.refreshview.IListDataView;
import com.hzxiaojietan.base.common.baseui.refreshview.IRefreshRecyclerView;
import com.hzxiaojietan.base.common.baseui.refreshview.SwipeRefreshRecyclerView;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 带列表的Fragment基类
 */
public class BaseRecyclerFragment<T extends IBasePresenter> extends BaseFragment<T> implements IListDataView {

    protected IRefreshRecyclerView mRefreshRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = createRootView(inflater, container);

        initView((ViewGroup) rootView);

        return rootView;
    }

    protected View createRootView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    protected void initView(ViewGroup rootView){
        mRefreshRecyclerView = new SwipeRefreshRecyclerView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rootView.addView((View) mRefreshRecyclerView, params);
        mRefreshRecyclerView.getEmptyView().setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });
    }

    protected void onRetry() {
        showLoading();
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void showList(boolean isHasMore) {
        mRefreshRecyclerView.showList(isHasMore);
    }

    @Override
    public void showEmpty() {
        mRefreshRecyclerView.showEmpty();
    }

    @Override
    public void showError() {
        mRefreshRecyclerView.showError();
    }

    @Override
    public void showLoading() {
        mRefreshRecyclerView.showLoading();
    }
}
