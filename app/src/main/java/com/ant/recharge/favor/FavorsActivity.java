package com.ant.recharge.favor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.FavorAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.coupon.NetCouponInterface;
import com.ant.recharge.entity.CouponEntity;
import com.ant.recharge.entity.CouponRecordList;
import com.ant.recharge.entity.OrderList;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.NetFianceFragmentInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 收藏列表
 * Created by kwc on 2016/9/7.
 */
public class FavorsActivity extends BaseActivity {

    private User user;

    private FavorAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    //优惠券状态 status 1:未使用 8:已使用 9:已过期
    private String status = "1";
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_favor);

        refreshListView = (PullToRefreshListView) findViewById(R.id.activity_favor_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new FavorAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.favors_title);

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            user = JsonUtil.decode(userStr, User.class);
        } catch (Exception e){

        }


        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                queryList(status);
            }
        });


        //初始化
        queryList(status);

    }


    public void queryList(final String status){

        Log.d("---queryList", "status=" + status);
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.favorList("" + pageNo, user.getToken(),
                    new NCallback<List<TelephoneEntity>>(this, new TypeReference<List<TelephoneEntity>>() {}) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, List<TelephoneEntity> entityList) {
                            refreshListView.onRefreshComplete();
                            adapter.appendList(entityList);
                        }

                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                            refreshListView.onRefreshComplete();
                            Toast.makeText(FavorsActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
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
                //finish();
                //refreshTable();
            }
        }

    }
}
