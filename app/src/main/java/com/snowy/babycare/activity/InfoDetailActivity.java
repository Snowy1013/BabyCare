package com.snowy.babycare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.snowy.babycare.R;
import com.snowy.babycare.view.HorizontalProgressBar;

/**
 * Created by snowy on 16/2/29.
 */
public class InfoDetailActivity extends Activity {
    private ImageView back_info_detail;
    private HorizontalProgressBar progressBar_info_detail;
    private WebView webview_info_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        initView();
    }

    private void initView(){
        back_info_detail = (ImageView) findViewById(R.id.back_info_detail);
        progressBar_info_detail = (HorizontalProgressBar) findViewById(R.id.progressBar_info_detail);
        webview_info_detail = (WebView) findViewById(R.id.webview_info_detail);

        //初始化WebView
        initWebView();

        back_info_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview_info_detail.canGoBack()){
                    webview_info_detail.goBack();//返回上一个界面
                }else {
                    finish();
                }
            }
        });

        progressBar_info_detail.setMax(100);
    }

    public void initWebView(){
        //webview加载web资源
        webview_info_detail.loadUrl("https://www.baidu.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview_info_detail.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }

        });

        //判断页面加载过程
        webview_info_detail.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar_info_detail.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar_info_detail.setVisibility(View.GONE);
                }else {
                    progressBar_info_detail.setVisibility(View.VISIBLE);
                }
            }
        });

        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        WebSettings settings = webview_info_detail.getSettings();
        settings.setJavaScriptEnabled(true);

        //优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    /**
     * 如果希望浏览的网页后退而不是退出浏览器，需要WebView覆盖URL加载，
     * 让它自动生成历史访问记录，那样就可以通过前进或后退访问已访问过的站点。
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webview_info_detail.canGoBack()){
                webview_info_detail.goBack();//返回上一个界面
                return true;
            }else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
