package com.ant.recharge.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.MessageAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.MessageEntity;
import com.ant.recharge.entity.MessageList;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;

/**
 * Created by kwc on 2016/9/2.
 */
public class MessageActivity extends BaseActivity {
    Logger logger = Logger.getLogger(MessageActivity.class.getSimpleName());

//    private TextView mycodeTV;
    private MessageAdapter adapter;
    private PullToRefreshListView refreshListView;
    private ListView listView;

    private String currentType = "all";
    private int pageNo = 1;
    private int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message);
        initView(this, R.layout.activity_message);


        refreshListView = (PullToRefreshListView) findViewById(R.id.financial_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new MessageAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.message_title);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {}
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                queryList(currentType);
            }
        });

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(MessageActivity.this, MessageDetailActivity.class);
                MessageEntity messageEntity = (MessageEntity)(adapter.getItem(i-1));
                messageEntity.setReadState("1");//已读
                intent.putExtra("messageEntity", messageEntity);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        queryList(currentType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
//                login(userNameTV.getText().toString(), passwordTV.getText().toString());
                return;
            case R.id.login_regist:
//                startActivity(new Intent(this, RegistActivity.class));
                return;
            case R.id.login_forgot:
//                startActivity(new Intent(this, ForgotPasswordActivity.class));
                return;
            default:
                break;
        }
        super.onClick(v);
    }

    public void queryList(final String currentType){

        NetMessageInterface netInterface = new NRestAdapter<NetMessageInterface>(this,
                Profile.SERVER_ADDRESS, NetMessageInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.mymsgList(currentType,"" + pageNo,"" + pageSize, user.getLoginName(),
                    new NCallback<MessageList>(this, new TypeReference<MessageList>() { }) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, MessageList oRet) {

                            adapter.appendList(oRet.getResult());
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                            //403定义为过期
                            if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                                exitLogin();
                            } else {
                                Toast.makeText(MessageActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                            }
                            refreshListView.onRefreshComplete();
                        }
                    });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }
}
