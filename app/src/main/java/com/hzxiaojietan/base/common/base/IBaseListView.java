package com.hzxiaojietan.base.common.base;

import java.util.List;

/**
 * Created by xiaojie.tan on 2017/10/26
 * IBaseListView
 */
public interface IBaseListView<T, K> extends IBaseView<T> {
    void reloadList(List<K> data);
}
