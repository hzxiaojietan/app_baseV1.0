package com.hzxiaojietan.base.net;

import java.util.Map;

/**
 * Created by xiaojie.tan on 2017/10/26
 * INetConfig
 */
public interface INetConfig {

    Map<String, String> getHeaders();

    Map<String, String> getParams();

    String getBaseUrl();
}
