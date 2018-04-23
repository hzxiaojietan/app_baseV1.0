package com.hzxiaojietan.base.common.baseui.refreshview;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 数据列表视图接口，加载中，加载出错，没有更多，没有数据时的显示
 */
public interface IListDataView {

    void showList(boolean isHasMore);

    void showEmpty();

    void showError();

    void showLoading();

}
