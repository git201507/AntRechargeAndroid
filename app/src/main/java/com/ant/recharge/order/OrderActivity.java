package com.ant.recharge.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.OrderAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.OrderList;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.NetFianceFragmentInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 订单列表
 * Created by kwc on 2016/9/7.
 */
public class OrderActivity extends BaseActivity {

    private User user;

    private OrderAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_orders);

        refreshListView = (PullToRefreshListView) findViewById(R.id.order_list);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
//        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new OrderAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.orders_title);

        TextView screenTV = (TextView)findViewById(R.id.text_title_right);
        screenTV.setText("筛选");
        screenTV.setOnClickListener(this);

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            user = JsonUtil.decode(userStr, User.class);
        } catch (Exception e){

        }

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                queryList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                queryList();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化
        pageNo = 1;
        queryList();
    }

    public void queryList(){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.orderList("" + pageNo, user.getToken(),
                    new NCallback<OrderList>(this, new TypeReference<OrderList>() {}) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, OrderList orderList) {

                            if(pageNo == 1 && adapter.getCount() > 0)
                            {
                                adapter.removeList();
                            }
                            adapter.appendList(orderList.getResult());

                            pageNo++;
                            refreshListView.onRefreshComplete();
                        }

                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                            //403定义为过期
                            if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
//                                exitLogin();
                            } else {
                                Toast.makeText(OrderActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                            }
                            refreshListView.onRefreshComplete();
                        }

                    });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                return;
            case R.id.text_title_right:
                Intent intent = new Intent(this, ScreenOrderActivity.class);
                startActivityForResult(intent, 1);
                return;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册
        if(1 == requestCode){
//            Log.d("-----", "-----------resultCode=" + resultCode);
//            Log.d("-----", "-----------data=" + data.toString());
            if(200 == resultCode){
                //pageNo=1;
                //queryList();
            }
        }

    }
}
