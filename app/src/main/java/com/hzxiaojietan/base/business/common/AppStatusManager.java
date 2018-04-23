package com.hzxiaojietan.base.business.common;

/**
 * Created by xiaojie.tan on 2017/10/26
 * App状态管理类
 */
public class AppStatusManager {
    //APP状态 初始值为没启动 不在前台状态
    public int appStatus = AppStatusConstant.STATUS_FORCE_KILLED;
    public static AppStatusManager appStatusManager;

    private AppStatusManager() {

    }

    public static AppStatusManager getInstance() {
        if(appStatusManager==null) {
            synchronized (AppStatusManager.class) {
                if(appStatusManager==null)
                    appStatusManager = new AppStatusManager();
            }
        }
        return appStatusManager;
    }

    public int getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }
}
