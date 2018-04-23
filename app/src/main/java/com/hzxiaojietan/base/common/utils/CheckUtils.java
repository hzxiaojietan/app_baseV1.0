package com.hzxiaojietan.base.common.utils;

import android.support.annotation.Nullable;

/**
 * Created by xiaojie.tan on 2017/10/26
 * CheckUtils
 */
public class CheckUtils {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
