package com.hzxiaojietan.base.common.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hzxiaojietan.base.common.base.BaseFragmentActivity;

import java.util.HashMap;


/**
 * Created by xiaojie.tan on 2017/10/26
 * 通用展示浏览器的Activity,有特殊需求的请继承WebviewFragment定制
 */
public class CommonWebViewActivity extends BaseFragmentActivity<WebViewFragment> {

    /**
     *
     * @param context
     * @param title 标题，如果设置为空，则使用webview加载成功后的title;如果不为空，则使用指定的title
     * @param url 网页链接
     * @return
     */
    public static Intent createIntent(Context context, String title, String url, HashMap<String, String> headers) {
        Intent intent = new Intent(context, CommonWebViewActivity.class);
        intent.putExtra(WebViewFragment.KEY_TITLE, title);
        intent.putExtra(WebViewFragment.KEY_URL, url);
        intent.putExtra(WebViewFragment.KEY_HEADERS, headers);
        intent.putExtra(WebViewFragment.KEY_SHOW_LOADING, true);
        intent.putExtra(WebViewFragment.KEY_SHOW_TOOLBAR, true);
        return intent;
    }

    public static Intent createIntent(Context context, String title, String url) {
        return createIntent(context, title, url, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       hideToolbar();
    }

    @Override
    protected WebViewFragment createFragment() {
        Intent intent = getIntent();
        return WebViewFragment.createFragment(
                intent.getStringExtra(WebViewFragment.KEY_TITLE),
                intent.getStringExtra(WebViewFragment.KEY_URL),
                (HashMap<String, String>) intent.getSerializableExtra(WebViewFragment.KEY_HEADERS),
                intent.getBooleanExtra(WebViewFragment.KEY_SHOW_LOADING, true),
                intent.getBooleanExtra(WebViewFragment.KEY_SHOW_TOOLBAR, true));
    }

    @Override
    public void onBackPressed() {
        if(!mFragment.onBackPressed()){
            finish();
        }
    }
}
