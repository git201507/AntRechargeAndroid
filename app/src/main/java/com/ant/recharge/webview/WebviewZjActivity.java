package com.ant.recharge.webview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;

/**
 * 调用到中金接口
 * Created by kwc on 2016/9/12.
 */
public class WebviewZjActivity extends BaseActivity {


    private WebView webView;
    private WebSettings settings;

    private String from;// activity name
    private byte[] content;
    private String title;

    public static final String ZJ_TITLE = "title";
    public static final String ZJ_FROM = "activity";
    public static final String ZJ_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recharge_webview);
        initView(this, R.layout.activity_recharge_webview);

        webView = (WebView)findViewById(R.id.md_webview);
        configWebView(webView);



        Intent intent = getIntent();
        from = intent.getStringExtra(ZJ_FROM);
        content = intent.getByteArrayExtra(ZJ_CONTENT);
        title = intent.getStringExtra(ZJ_TITLE);

        //设置标题
        if(!StringUtils.isBlank(title)){
            ((TextView)findViewById(R.id.text_title)).setText(title);
        } else {
            ((TextView)findViewById(R.id.text_title)).setText(R.string.recharge_title);
        }



        loadData();
    }

    public void loadData(){
        webView.postUrl(Profile.SERVER_ADDRESS + from, content);
    }

    private void configWebView(WebView web){
        settings = web.getSettings();

        //支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);//不显示默认放大缩小图
//		settings.setMinimumFontSize((int)DisplayUtil.sp2px(this, getResources().getDimension(R.dimen.webview_fontsize_1)));

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


//        web.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                if(progressDialog != null){
//                    progressDialog.setProgress(newProgress);
//                    if(100 == newProgress && progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
//                }
//
//            }
//        });
//        web.setWebViewClient(new PaperWebViewClient());
    }

}
