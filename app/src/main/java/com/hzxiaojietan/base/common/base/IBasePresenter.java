package com.hzxiaojietan.base.common.base;

import java.io.Serializable;

/**
 * Created by xiaojie.tan on 2017/10/26
 * IBasePresenter
 */
public interface IBasePresenter extends Serializable {

    void subscribe();

    void unsubscribe();
}
