package com.hzxiaojietan.base.common.baseui.refreshview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.common.baseui.CommonRecyclerAdapter;

/**
 * Created by xiaojie.tan on 2017/10/26
 * SwipeRefreshRecyclerView
 */
public class SwipeRefreshRecyclerView extends RelativeLayout implements IRefreshRecyclerView, OnRefreshListener, OnLoadMoreListener {

    private SwipeToLoadLayout mSwipeRefreshLayout;
    private IRefreshListener mRefreshListener;
    private RecyclerView mRecyclerView;
    private IEmptyView mEmptyView;
    private CommonRecyclerAdapter mAdapter;

    private boolean mIsLoading;
    private boolean mIsHasMore;
    private boolean mIsRefresh;
    private boolean mIsLoadMoreEnable = true;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_swipe_refresh_recycler, this);
        mSwipeRefreshLayout = (SwipeToLoadLayout) view.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setOnLoadMoreListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        mEmptyView = new EmptyView(getContext());
        mEmptyView.attach(this);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    public void setRefreshListener(IRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    @Override
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mIsLoadMoreEnable = loadMoreEnable;
    }

    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        mIsLoading = true;
        mIsRefresh = true;
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public IEmptyView getEmptyView() {
        return mEmptyView;
    }

    @Override
    public boolean isRefresh() {
        return mIsRefresh;
    }

    @Override
    public void setAdapter(@NonNull CommonRecyclerAdapter adapter) {
        mAdapter = adapter;
        mSwipeRefreshLayout.setLoadMoreEnabled(mIsLoadMoreEnable);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setLayoutManager(@NonNull final RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mAdapter != null && mAdapter.getFooterSize() != 0) {
                        if (position == mAdapter.getItemCount() - 1) {
                            return gridLayoutManager.getSpanCount();
                        } else {
                            return 1;
                        }
                    }
                    return 1;
                }
            });
        }
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setEmptyView(@NonNull IEmptyView emptyView) {
        this.removeView(mEmptyView.getView());
        mEmptyView = emptyView;
        mEmptyView.attach(this);
    }

    @Override
    public SwipeToLoadLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void showList(boolean isHasMore) {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setLoadingMore(false);
        mIsLoading = false;
        mIsHasMore = isHasMore;
        mEmptyView.showNothing();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmpty() {
        mIsLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setLoadingMore(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mEmptyView.showEmpty();
    }

    @Override
    public void showError() {
        mIsLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setLoadingMore(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mEmptyView.showError();
    }

    @Override
    public void showLoading() {
        mIsLoading = true;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setLoadingMore(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mEmptyView.showLoading();
    }

    @Override
    public void onRefresh() {
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }


    @Override
    public void onLoadMore() {
        if (mRefreshListener != null) {
            mRefreshListener.onLoadMore();
        }
    }
}
