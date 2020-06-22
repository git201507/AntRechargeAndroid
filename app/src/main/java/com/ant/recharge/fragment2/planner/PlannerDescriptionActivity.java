package com.ant.recharge.fragment2.planner;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

/**
 * 理财师说明
 * Created by kwc on 2016/11/28.
 */
public class PlannerDescriptionActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(this, R.layout.base_webview);


        webView = (WebView)findViewById(R.id.md_webview);
        configWebView(webView);

        ((TextView)findViewById(R.id.text_title)).setText("理财师说明");

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
//            webView.loadUrl(Profile.SERVER_ADDRESS + "wx/front/wdzh/financial.jhtml?memberId=" + user.getLoginName());
            webView.loadUrl(Profile.SERVER_ADDRESS + "wx/front/wdzh/appFinancial.jhtml?memberId=" + user.getLoginName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void configWebView(WebView web){
        WebSettings settings = web.getSettings();

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

        settings.setSupportMultipleWindows(true);

        //缓存
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);


        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());
    }

}
