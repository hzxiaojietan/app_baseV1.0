package com.hzxiaojietan.base.net;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 这个类主要是因为/getDrvInfo 获取驾驶证信息这个接口，在查不到信息的情况下，返回代码是200，但是http响应体是空，Content-Length: 0
 * 这个会导致retrofit报错。see: https://github.com/square/retrofit/issues/1554#issuecomment-178633697
 */


public class RetrofitNullOnEmptyConvertFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);                }
        };
    }
}