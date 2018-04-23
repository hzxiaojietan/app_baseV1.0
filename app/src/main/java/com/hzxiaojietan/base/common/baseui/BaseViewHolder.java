package com.hzxiaojietan.base.common.baseui;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by xiaojie.tan on 2017/10/26
 */

public abstract class BaseViewHolder<T> {
    protected Context mContext;
    protected View mItemView;

    public View getItemView() {
        return mItemView;
    }

    public void bindView(View view) {
        mContext = view.getContext();
        mItemView = view;
        ButterKnife.bind(this, view);
    }

    public abstract void updateView(T data, int position);

    public abstract int getLayoutResId();
}
