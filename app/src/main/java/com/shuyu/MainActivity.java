package com.shuyu;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.shuyu.action.web.SelectListener;
import com.shuyu.action.web.SelectWebView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    SelectWebView mSelectWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectWebView = (SelectWebView)findViewById(R.id.customActionWebView);
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        mSelectWebView.setWebViewClient(new CustomWebViewClient());

        //设置item
        mSelectWebView.setActionList(list);

        //链接js注入接口，使能选中返回数据
        mSelectWebView.linkJSInterface();

        mSelectWebView.getSettings().setBuiltInZoomControls(true);
        mSelectWebView.getSettings().setDisplayZoomControls(false);
        //使用javascript
        mSelectWebView.getSettings().setJavaScriptEnabled(true);
        mSelectWebView.getSettings().setDomStorageEnabled(true);


        //增加点击回调
        mSelectWebView.setActionSelectListener(new SelectListener() {
            @Override
            public void onClick(String title, String selectText) {

                Toast.makeText(MainActivity.this, "Click Item: " + title + "。\n\nValue: " + selectText, Toast.LENGTH_LONG).show();
            }
        });

        //加载url
        mSelectWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSelectWebView.loadUrl("http://www.importnew.com/23191.html");
            }
        }, 500);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mSelectWebView != null) {
            mSelectWebView.dismissAction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private class CustomWebViewClient extends WebViewClient {

        private boolean mLastLoadFailed = false;

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!mLastLoadFailed) {
                SelectWebView selectWebView = (SelectWebView) webView;
                selectWebView.linkJSInterface();
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mLastLoadFailed = true;
        }
    }
}
