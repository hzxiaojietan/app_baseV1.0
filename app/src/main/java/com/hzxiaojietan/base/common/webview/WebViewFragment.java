package com.hzxiaojietan.base.common.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.hzxiaojietan.base.R;
import com.hzxiaojietan.base.common.base.BaseBean;
import com.hzxiaojietan.base.common.base.BaseFragment;
import com.hzxiaojietan.base.common.utils.AppLog;
import com.hzxiaojietan.base.common.utils.Util;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 通用Webview界面
 */
public class WebViewFragment extends BaseFragment {
    protected static final String JS_OBJ = "clientInterface";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";
    public static final String KEY_HEADERS = "headers";
    public static final String KEY_SHOW_TOOLBAR = "key_show_toolbar";
    public static final String KEY_SHOW_LOADING = "key_show_loading";

    private String   mTitle;
    private String   mUrl;
    private HashMap<String, String> mHeaders;
    private boolean  mShowLoading;
    private boolean  mShowToolBar;


    @BindView(R.id.toolbar)
    View mToolbar;
    @BindView(R.id.img_title_left)
    ImageView mImgTitleLeft;

    @BindView(R.id.top_title_tv)
    TextView mTvTitle;

    @BindView(R.id.webview)
    WebView mWebView;

    //底部动态布局
    @BindView(R.id.bottom_layout)
    FrameLayout mBottomLay;

    public static WebViewFragment createFragment(String url) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    /**
     * 创建webveiw fragment
     * @param title  标题，如果不设置，则加载完成后使用html的
     * @param url  网页链接
     * @return
     */
    public static WebViewFragment createFragment(String title, String url) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, url);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    /**
     * 创建webveiw fragment
     * @param title  标题，如果不设置，则加载完成后使用html的
     * @param url  网页链接
     * @param showLoadingIcon 是否显示加载中对话框
     * @param showToolBar  是否显示toolbar
     * @return
     */
    public static WebViewFragment createFragment(String title, String url, boolean showLoadingIcon,
                                                    boolean showToolBar) {
        return createFragment(title, url, null, showLoadingIcon, showToolBar);
    }

    /**
     * 创建webveiw fragment
     * @param title  标题，如果不设置，则加载完成后使用html的
     * @param url  网页链接
     * @param headers 头部参数
     * @param showLoadingIcon 是否显示加载中对话框
     * @param showToolBar  是否显示toolbar
     * @return
     */
    public static WebViewFragment createFragment(String title, String url, HashMap<String, String> headers,
                                                 boolean showLoadingIcon, boolean showToolBar) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_URL, url);
        bundle.putSerializable(KEY_HEADERS, headers);
        bundle.putBoolean(KEY_SHOW_LOADING, showLoadingIcon);
        bundle.putBoolean(KEY_SHOW_TOOLBAR, showToolBar);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        initWebView();
        loadData();
        return rootView;
    }

    private void init(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mTitle = bundle.getString(KEY_TITLE);
            mUrl = bundle.getString(KEY_URL);
            mHeaders = (HashMap<String, String>) bundle.getSerializable(KEY_HEADERS);
            mShowLoading = bundle.getBoolean(KEY_SHOW_LOADING, true);
            mShowToolBar = bundle.getBoolean(KEY_SHOW_TOOLBAR, true);
        }
    }

    private void initView() {
        mImgTitleLeft.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(mTitle)){
            mTvTitle.setText(mTitle);
        }
        if(!mShowToolBar){
            mToolbar.setVisibility(View.GONE);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        // 配置浏览器，使其可支持 JavaScript
        if (null == mWebView) {
            AppLog.e(TAG, "webview is null!");
            return;
        }

        WebSettings webSettings = mWebView.getSettings();
        if (null == webSettings) {
            Log.e(TAG, "webSettings is null!");
            return;
        }

        webSettings.setJavaScriptEnabled(true);

        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = mActivity.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);

        if(Build.VERSION.SDK_INT >= 21){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebChromeClient wcc = getWebChromeClient();
        mWebView.setWebChromeClient(wcc);

        WebViewClient wvc = getWebViewClient();
        mWebView.setWebViewClient(wvc);
        mWebView.addJavascriptInterface(new WebViewFragment.JavaScriptInterface(), JS_OBJ);
    }

    private void loadData() {
        if (mShowLoading) {
            Util.showHUD(mActivity);
        }
        if(mHeaders != null && !mHeaders.isEmpty()){
            mWebView.loadUrl(mUrl, mHeaders);
        }else{
            mWebView.loadUrl(mUrl);
        }
    }

    /**
     * @return WebChromeClient的对象
     * @fn getWebChromeClient
     * @brief 获取WebChromeClient对象
     * @author mlianghua
     */
    private WebChromeClient getWebChromeClient() {
        return new WebChromeClientDemo();
    }

    /**
     * @return WebViewClient的对象
     * @fn getWebViewClient
     * @brief 获取WebViewClient对象
     * @author mlianghua
     */
    private WebViewClient getWebViewClient() {
        return new WebViewClientDemo();
    }

    /**
     * WebChromeClientDemo
     *
     * @author weilinfeng
     * @Data 2013-10-28
     */
    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
            //TODO:进度控制
        }

        //配置权限（同样在WebChromeClient中实现）
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    /**
     * WebViewClientDemo
     *
     * @author weilinfeng
     * @Data 2013-10-28
     */
    private class WebViewClientDemo extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void errorWebSet(WebView wv) {
            if (null == wv) {
                AppLog.e(TAG, "wv is null!");
                return;
            }
            wv.getSettings().setDefaultFontSize(10);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            AppLog.d(TAG, "start " + url);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Util.dismissHUD();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // Handle the error
            AppLog.d(TAG, "xxxx error code:" + errorCode + "," + description);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Util.dismissHUD();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (TextUtils.isEmpty(mTitle)) {
                mTvTitle.setText(view.getTitle());
            }
            Util.dismissHUD();
        }
    }

    @OnClick({R.id.img_title_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_title_left:
                //返回
                onBackPressed();
                break;
        }
    }

    protected void setBottomLay(View view)
    {
        if(view != null)
        {
            mBottomLay.setVisibility(View.VISIBLE);
            mBottomLay.addView(view);
        }
    }

    protected  void hideBottomLay()
    {
        mBottomLay.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        }else{
            finish();
        }
        return true;
    }

    public class JavaScriptInterface {

        public JavaScriptInterface() {

        }

        /**
         * 获取用户信息
         * @return
         */
        @JavascriptInterface
        public String getUserData() {
            String data = "";
            WebViewFragment.UserData userData = new WebViewFragment.UserData();
            userData.userId = Util.getCurrentUser().getUserId();
            userData.token = Util.getToken();
            data = new GsonBuilder().disableHtmlEscaping().create().toJson(userData);
            AppLog.e(TAG, "getUserData:" + data);

            return data;
        }

        /**
         * JS回调给客户端的数据，json格式
         * @param text
         */
        @JavascriptInterface
        public void jsCallback(final String text) {
            AppLog.e(TAG, "jsCallback:" + text);
        }
    }

    public class UserData implements BaseBean {
        //用户id
        public long userId;

        //Token
        public String token;
    }
}
