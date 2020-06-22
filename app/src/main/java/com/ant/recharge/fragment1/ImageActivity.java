package com.ant.recharge.fragment1;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;

/**
 * 图片放大
 * Created by kwc on 2016/10/17.
 */
public class ImageActivity extends BaseActivity {

//    private ZoomImageView imageView;

    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_image);
        initView(this, R.layout.activity_image);


        ((TextView)findViewById(R.id.text_title)).setText(" ");


        final String url = getIntent().getStringExtra("imagePath");

        WebView webView = (WebView) findViewById(R.id.md_webview);
//        setContentView(webView);
        configWebView(webView);

        String content = "<div style=\"text-align:center; width:100%\"><img style=\"width:100%;height:auto\" src=" + url + " /></div>";
        webView.loadDataWithBaseURL(null, content,"text/html","utf-8", null);


//        final ImageView imageView = new ImageView(this);
//        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        setContentView(imageView);

//        imageView = (ZoomImageView) findViewById(R.id.md_webview);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
//                    runOnUiThread(new Runnable(){
//                        @Override
//                        public void run() {
////                            ImageView iv = (ImageView) findViewById(R.id.fd_message_image);
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    private void configWebView(WebView web){
        settings = web.getSettings();

        //支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
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
