package com.hzxiaojietan.base.common.baseui.refreshview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hzxiaojietan.base.common.baseui.CommonRecyclerAdapter;


/**
 * Created by xiaojie.tan on 2017/10/26
 * 刷新列表对外提供的接口
 */
public interface IRefreshRecyclerView extends IListDataView {

    void setRefreshListener(IRefreshListener refreshListener);

    void setLoadMoreEnable(boolean loadMoreEnable);

    void setRefreshEnable(boolean refreshEnable);

    void setRefreshing(boolean isRefreshing);

    IEmptyView getEmptyView();

    boolean isRefresh();

    void setAdapter(@NonNull CommonRecyclerAdapter adapter);

    void setLayoutManager(@NonNull RecyclerView.LayoutManager layoutManager);

    void setEmptyView(@NonNull IEmptyView emptyView);

    SwipeToLoadLayout getSwipeRefreshLayout();

    RecyclerView getRecyclerView();
}
