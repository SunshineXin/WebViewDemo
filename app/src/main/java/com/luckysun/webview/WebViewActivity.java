package com.luckysun.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends Activity implements View.OnClickListener {

    private String mUrl;
    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        mUrl = "http://www.baidu.com/";
        initViews();

        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键时的操作
                        System.out.println("mWebView.canGoBack() ");
                        mWebView.goBack();
                        return true;    //已处理
                    } else {
                        System.out.println("mWebView.canGoBack() is false");
                    }
                }
                return false;
            }
        });
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress) {
            System.out.println("------------>" + progress + "<-----------");
        }

    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mUrl = url;
            System.out.println("------------>" + url + "<-----------");
//            mLoadingDialog = new LoadingProgressDialog(WebViewActivity.this);
//            mLoadingDialog.show();
//            mLoadingDialog.setCanceledOnTouchOutside(false);
//            mLoadingDialog.setMessageContent("加载中，请稍后...");
        }

        public void onPageFinished(WebView view, String url) {
//            mLoadingDialog.dismiss();
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            mLoadingDialog.dismiss();
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                this.finish();
                break;
            case R.id.btn_home:
                WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
                if(mWebBackForwardList != null && mWebBackForwardList.getSize() > 1){
                int steps = mWebBackForwardList.getCurrentIndex();
                if(mWebView.canGoBackOrForward(- steps)){
                    mWebView.goBackOrForward( - steps);
                    System.out.println(" canGoBackOrForward : " + ( - steps));
                }else{
                    System.out.println("do not can goBackOrForward : " + ( - steps));
                }
            }
                break;

            case R.id.btn_goback:
                if (mWebView.canGoBack()) {
                    System.out.println("mWebView.canGoBack() ");
                    mWebView.goBack();
                } else {
                    System.out.println("do not can goBack() ");
                }
                break;

            case R.id.btn_goforward:
                if (mWebView.canGoForward()) {
                    System.out.println("mWebView.goForward() ");
                    mWebView.goForward();
                } else {
                    System.out.println("do not can goForward() ");
                }
                break;
            case R.id.btn_refresh:
                mWebView.reload();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
