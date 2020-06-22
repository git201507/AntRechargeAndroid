package com.ant.recharge.login;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;

import java.util.logging.Logger;

/**
 * Created by kwc on 2016/8/18.
 * 注册协议
 */
public class ProtocolActivity extends BaseActivity {


    Logger logger = Logger.getLogger(ProtocolActivity.class.getSimpleName());
    private WebSettings settings;
    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regist_protocol);
        initView(this, R.layout.activity_regist_protocol);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.regist_title);

        webView = (WebView)findViewById(R.id.p_webview);
        configWebView(webView);

        if(progressDialog == null){
            progressDialog = new ProgressDialog(ProtocolActivity.this);
            progressDialog.setMax(100);
            progressDialog.setMessage("加载中……");
        }
        progressDialog.show();


        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_confirm:
                finish();
                return;
            default:
                break;
        }
        super.onClick(v);
    }

    private void configWebView(WebView web){
        settings = web.getSettings();

        //支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);//不显示默认放大缩小图
//		settings.setMinimumFontSize((int)DisplayUtil.sp2px(this, getResources().getDimension(R.dimen.webview_fontsize_1)));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
//            settings.setTextZoom(70);
//        } else {
//            settings.setTextSize(WebSettings.TextSize.LARGEST);
//        }
        settings.setDefaultFontSize(40);

//		settings.setTextZoom();

        //无限缩放
//		web.setInitialScale(25);
        settings.setUseWideViewPort(true);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        settings.setDefaultTextEncodingName("UTF-8");


        //支持javascript
        settings.setJavaScriptEnabled(true);

        //缓存
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);


        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(progressDialog != null){
                    progressDialog.setProgress(newProgress);
                    if(100 == newProgress && progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }

            }
        });
        web.setWebViewClient(new PaperWebViewClient());
    }

    class PaperWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if(progressDialog != null){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            String data = "数据读取错误!";
            view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            return true;
        }
    }

    public void loadData(){

        new Thread(new Runnable() {
            @Override
            public void run() {


//                final String helpContent = HttpUtils.post(Profile.SERVER_ADDRESS + NetLoginInterface.ANT_REGISTER_PROTOTOL, null);
//                logger.warning("-------------helpContent=" + helpContent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        webView.loadDataWithBaseURL(null, helpContent, "text/html", "UTF-8", null);
                        webView.loadUrl("file:///android_asset/regist_prototol.html");
                    }
                });
            }
        }).start();

    }
}
