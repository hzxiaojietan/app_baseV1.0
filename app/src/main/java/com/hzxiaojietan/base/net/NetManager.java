package com.hzxiaojietan.base.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hzxiaojietan.base.common.utils.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 网络管理类
 */

public class NetManager {

    private static NetManager instance;

    //自己服务器api
    private RequestApi sRxjavaApi;

    private INetConfig mNetConfig;

    private NetManager() {
        mNetConfig = new NetConfig();
    }

    public static NetManager getInstance() {
        if (instance == null) {
            instance = new NetManager();
        }
        return instance;
    }

    public void setNetConfig(INetConfig netConfig) {
        mNetConfig = netConfig;
    }

    public OkHttpClient.Builder  getOkHttpBuilder() {
        return new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new FZHeaderInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor(new AppLog())
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
    }

    /**
     * 获取请求api
     *
     * @return
     */
    public RequestApi getApi() {
        if (sRxjavaApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpBuilder().build())
                    .baseUrl(UrlPath.SERVER_BASE_URL)
                    .addConverterFactory(new RetrofitNullOnEmptyConvertFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            sRxjavaApi = retrofit.create(RequestApi.class);
        }
        return sRxjavaApi;
    }

    /**
     * 简单文件下载
     * @param url       下载资源路径
     * @param filePath  下载后保存路径
     * @return          Observable
     */
    public Observable<Float> simpleDownload(final String url, final String filePath){
        return Observable.create(new Observable.OnSubscribe<Float>() {
            @Override
            public void call(Subscriber<? super Float> subscriber) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try{
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        inputStream = response.body().byteStream();
                        long length = response.body().contentLength();
                        //将文件下载到file路径下
                        outputStream = new FileOutputStream(new File(filePath));
                        byte data[] = new byte[1024];
                        subscriber.onNext(0f);
                        long total = 0;
                        int count;
                        float percent = 0;
                        while ((count = inputStream.read(data)) != -1){
                            total += count;
                            // 返回当前实时进度
                            percent = total * 100f / length;
                            subscriber.onNext(percent);
                            outputStream.write(data, 0, count);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                    }
                }catch (IOException e){
                    //告诉订阅者错误信息
                    subscriber.onError(e);
                }finally {
                    if (inputStream != null){
                        try{
                            inputStream.close();
                        }catch (IOException e){
                            subscriber.onError(e);
                        }
                    }
                    if (outputStream != null){
                        try {
                            outputStream.close();
                        }catch (IOException e){
                            subscriber.onError(e);
                        }
                    }
                }
                //告诉订阅者请求数据结束
                subscriber.onCompleted();
            }
        }).buffer(1000, TimeUnit.MILLISECONDS).map(new Func1<List<Float>, Float>() {
            @Override
            public Float call(List<Float> floats) {
                if (floats.isEmpty()) {
                    return 0f;
                }
                return floats.get(floats.size() - 1);
            }
        });
    }

    /**
     * 请求头拦截器 用来封装公共的头信息
     */
    public class FZHeaderInterceptor implements Interceptor {

        public FZHeaderInterceptor() {

        }

        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            Map<String, String> headers = mNetConfig.getHeaders();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
