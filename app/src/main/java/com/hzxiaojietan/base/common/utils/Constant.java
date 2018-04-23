package com.hzxiaojietan.base.common.utils;

import android.os.Environment;

import com.hzxiaojietan.base.BuildConfig;


/**
 * Created by xiaojie.tan on 2017/10/26
 */
public class Constant {
    public static final String APPLICATION_NAME = "wztc";
    public static final String APP_CACHE_BASE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + APPLICATION_NAME;
    public static final String APP_VIDEO_CACHE_DIR = APP_CACHE_BASE_DIR
            + "/video/";

    public static final String SERVER_BASE_URL = BuildConfig.HTTP_SERVER_URL;
    public static final String socketServer= BuildConfig.SOCKET_SERVER_URL;

    public static final String FILE_BASE_URL = SERVER_BASE_URL + "p/download/";

    //性别
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";

    public static final int TIME_MINUTE = 60000;
    public static final int ONE_MINUTE = 1000;

    //语音设置常量
    public static final String AUDIO_SETTING_PREFERENCE_KEY = "voice_item";
    public static final String AUDIO_SETTING_ALLOW_ALL = "2";
    public static final String AUDIO_SETTING_ONLY_SYSTEM_MESSAGE = "1";
    public static final String AUDIO_SETTING_CLOSE_ALL = "0";

    // 标识服务Fragment是否需要重新刷新获取数据
    public static boolean SERVICE_FRAGMENT_IS_REFRESH = false;
}
