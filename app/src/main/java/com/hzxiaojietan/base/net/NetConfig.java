package com.hzxiaojietan.base.net;


import com.hzxiaojietan.base.common.utils.Util;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xiaojie.tan on 2017/10/26
 * 网络配置类
 */
public class NetConfig implements INetConfig {

    /**
     * 构造固定头部信息
     * @return
     */
    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        //这里必须加上Accept-Language,否则出错的时候，后端会出错，不能把正确的错误信息返回回来
        headers.put("Accept-Language", "zh-cn,zh;q=0.5");
        String token = Util.getToken();
        if (token != null) {
            headers.put("XBToken", token);
        }
        return headers;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public String getBaseUrl() {
        return UrlPath.SERVER_BASE_URL;
    }

    public static String headerFormat(String value) {
        if (value == null) {
            return "null";
        }
        String newValue = value.replace("\n", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
