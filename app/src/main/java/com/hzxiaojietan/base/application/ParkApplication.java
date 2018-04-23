package com.hzxiaojietan.base.application;

import android.content.Context;
import android.os.Build;

import com.hzxiaojietan.base.BuildConfig;
import com.hzxiaojietan.base.common.utils.AppLog;

import org.litepal.LitePalApplication;

/**
 * Created by xiaojie.tan on 2017/10/27
 */
public class ParkApplication extends LitePalApplication {

    private static final String TAG = "ParkApplication";
    public static final String SAVED_CITY = "_saved_city";
    private static Context context;
    public static String MOBILE_TYPE;
    public static String OS_VERSION;

    public void onCreate() {
        super.onCreate();
        ParkApplication.context = getApplicationContext();
        MOBILE_TYPE = Build.MODEL;
        OS_VERSION = Build.VERSION.RELEASE;

        AppLog.init(BuildConfig.DEBUG);
    }

    public static Context getAppContext() {
        return ParkApplication.context;
    }

    public static String channelIdString;

    public static String getChannelIdString() {
        return channelIdString;
    }

    public static void setChannelIdString(String channelIdString) {
        ParkApplication.channelIdString = channelIdString;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //加这段是因为在4.x版本的手机上，photoview会出现: java.lang.NoClassDefFoundError: uk.co.senab.photoview.PhotoViewAttacher 这个bug，导致查看图片失败.
        //refer to : https://github.com/chrisbanes/PhotoView/issues/336
//        MultiDex.install(this);
    }
}
