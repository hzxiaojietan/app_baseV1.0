package com.hzxiaojietan.base.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.application.ParkApplication;
import com.hzxiaojietan.base.business.common.AppStatusConstant;
import com.hzxiaojietan.base.business.common.AppStatusManager;
import com.hzxiaojietan.base.business.illegalparking.activity.MainActivity;
import com.hzxiaojietan.base.business.illegalparking.model.bean.User;
import com.hzxiaojietan.base.common.utils.AppLog;
import com.hzxiaojietan.base.common.utils.ToastUtils;
import com.hzxiaojietan.base.common.utils.Util;
import com.hzxiaojietan.base.common.utils.ViewUtils;


/**
 * Created by xiaojie.tan on 2017/10/26
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    protected TextView mTvTitle;
    protected TextView mTvTitleLeft;
    protected TextView mTvTitleRight;
    protected ImageView mImgTitleLeft;
    protected ImageView mImgTitleRight;

    protected ViewGroup mToolbar;

    protected RelativeLayout rootView;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //判断是否已被后台回收，如果是则重新走启动流程
        switch (AppStatusManager.getInstance().getAppStatus()) {
            case AppStatusConstant.STATUS_FORCE_KILLED:
                //跳到主页,主页lauchmode SINGLETASK
                restartApp();
                break;
            case AppStatusConstant.STATUS_NORMAL:
                break;
        }
    }

    /**
     * 重启app
     */
    protected  void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_RESTART_APP);
        startActivity(intent);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mToolbar = (ViewGroup) rootView.findViewById(R.id.toolbar);
        mImgTitleLeft = (ImageView) rootView.findViewById(R.id.img_title_left);
        mImgTitleRight = (ImageView) rootView.findViewById(R.id.img_title_right);
        mTvTitle = (TextView) rootView.findViewById(R.id.top_title_tv);
        mTvTitleLeft = (TextView) rootView.findViewById(R.id.tv_title_left);
        mTvTitleRight = (TextView) rootView.findViewById(R.id.top_action_tv);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.toolbar);
        view.setLayoutParams(layoutParams);
        rootView.addView(view);
        super.setContentView(rootView);

        ViewUtils.rippleClickDelay(mImgTitleLeft, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void hideTitleShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
    }

    protected void hideToolbar() {
        mToolbar.setVisibility(View.GONE);
    }

    protected void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    public View showAlertDialog(int layout, boolean cancelOnTouchOutside) {
        AlertDialog.Builder builder;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(cancelOnTouchOutside);
        builder.setView(view);
        mAlertDialog = builder.create();
        mAlertDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        mAlertDialog.show();
        return view;
    }

    public void dismissDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    public void showProgress(String message) {
        Util.showHUD(mActivity, message);
    }

    public void hideProgress() {
        Util.dismissHUD();
    }

    public void showToast(int resId) {
        ToastUtils.show(ParkApplication.getAppContext(), resId);
    }


    public void showToast(String msg) {
        ToastUtils.show(ParkApplication.getAppContext(), msg);
    }

    /**
     * 是否设置状态栏颜色
     */
    protected boolean tintStatusBar() {
        return true;
    }

    public User getUser() {
        return Util.getCurrentUser();
    }

    @Override
    protected void onResume() {
        AppLog.e(getClass().getSimpleName(), "生命周期: onResume");
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        AppLog.e(getClass().getSimpleName(), "生命周期: onDestroy");
        super.onDestroy();
    }
}
