package com.ant.recharge.message;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.entity.MessageEntity;

import java.util.List;

import retrofit.client.Header;

/**
 * 消息详情
 * Created by kwc on 2016/9/8.
 */
public class MessageDetailActivity extends BaseActivity {

    private TextView titleTV;
    private WebView webView;
    private MessageEntity messageEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message_detail);
        initView(this, R.layout.activity_message_detail);

        titleTV = (TextView)findViewById(R.id.md_title);
        webView = (WebView)findViewById(R.id.md_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置标题
        ((TextView) findViewById(R.id.text_title)).setText(R.string.message_detail_title);

        messageEntity = (MessageEntity) getIntent().getSerializableExtra("messageEntity");
        titleTV.setText(messageEntity.getTitle());

        StringBuilder sb = new StringBuilder();
        sb.append("<script>function clickfont(str){window.location.href=\"");
        sb.append(Profile.SERVER_ADDRESS);
        sb.append("/api/apply/download/\" + str;}</script>");
        sb.append(messageEntity.getContent());
        webView.loadDataWithBaseURL(null, sb.toString(),"text/html","utf-8", null);
        webView.getSettings().setDefaultFontSize(14);
        webView.setBackgroundColor(66000000);
        loadDetail();
    }

    public void loadDetail(){

        NetMessageInterface netInterface = new NRestAdapter<NetMessageInterface>(this,
                Profile.SERVER_ADDRESS, NetMessageInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {


            netInterface.mymsgDetail(messageEntity.getMsgId(),
                    new NCallback<MessageEntity>(this, MessageEntity.class) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, MessageEntity messageEntity) {
                            MessageDetailActivity.this.messageEntity = messageEntity;

                        }
                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                        }
                    });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

}
