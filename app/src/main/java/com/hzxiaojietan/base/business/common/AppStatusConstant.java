package com.hzxiaojietan.base.business.common;

/**
 * Created by xiaojie.tan on 2017/10/26
 * APP状态跟踪器常量码
 */
public class AppStatusConstant {
    //应用放在后台被强杀了
    public static final int STATUS_FORCE_KILLED = -1;
    //APP正常态
    public static final int STATUS_NORMAL= 1;
    //返回到主页面
    public static final String KEY_HOME_ACTION="key_home_action";
    //默认值
    public static final int ACTION_BACK_TO_HOME= 0;
    //被强杀
    public static final int ACTION_RESTART_APP= 1;
}
