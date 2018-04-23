package com.hzxiaojietan.base.common.baseui.refreshview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 正在加载，空白，出错视图
 */
public interface IEmptyView {

    void showLoading();

    void showLoading(String loadingText);

    void showError();

    void showError(String errorText);

    void showEmpty();

    void showEmpty(String emptyText);

    void showNothing();

    void setLoadingView(View view);

    void setEmptyView(View view);

    void setEmptyText(String text);

    void setEmptyIcon(int iconRes);

    void setErrorView(View view);

    void setErrorText(String text);

    void setErrorIcon(int iconRes);

    void setRetryListener(View.OnClickListener onClickListener);

    void attach(ViewGroup root);

    View getView();
}
