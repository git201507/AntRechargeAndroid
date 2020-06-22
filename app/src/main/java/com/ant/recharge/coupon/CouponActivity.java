package com.ant.recharge.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.CouponAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.CouponHeader;
import com.ant.recharge.entity.CouponRecordList;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 优惠券
 * Created by kwc on 2016/9/7.
 */
public class CouponActivity extends BaseActivity {

    private User user;

    private TextView financingTV;
    private TextView finshTV;
    private TextView repaymentTV;

    private CouponAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    //优惠券状态 status 1:未使用 8:已使用 9:已过期
    private String status = "1";
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_coupon);
//        setContentView(R.layout.activity_coupon);

        financingTV = (TextView) findViewById(R.id.record_1);
        finshTV = (TextView) findViewById(R.id.record_2);
        repaymentTV = (TextView) findViewById(R.id.record_3);

        refreshListView = (PullToRefreshListView) findViewById(R.id.coupon_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new CouponAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.coupon_title);

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
        queryCount();

    }


    /**
     * 查询优惠券数量
     *
     */
    public void queryCount(){

        NetCouponInterface netInterface = new NRestAdapter<NetCouponInterface>(this,
                Profile.SERVER_ADDRESS, NetCouponInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.mycouponHeaderInfo(user.getToken(), new NCallback<CouponHeader>(this, new TypeReference<CouponHeader>() {}) {
                @Override
                public void onSuccess(int statusCode, List<Header> headers, CouponHeader header) {
                    try {
                        if(header != null){
                            financingTV.setText("" + header.getUnused());
                            finshTV.setText("" + header.getHasBeenUsed());
                            repaymentTV.setText("" + header.getOutOfDate());
                        }

                        queryList(status);

                    } catch (Exception e) {
                        Toast.makeText(CouponActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(CouponActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void queryList(final String status){

        Log.d("---queryList", "status=" + status);
        NetCouponInterface netInterface = new NRestAdapter<NetCouponInterface>(this,
                Profile.SERVER_ADDRESS, NetCouponInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.couponUseRecordList(status, "" + pageNo, "" + pageSize, user.getLoginName(), user.getToken(),
                    new NCallback<CouponRecordList>(this, new TypeReference<CouponRecordList>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, CouponRecordList couponRecordList) {

                    if(!status.equals(CouponActivity.this.status)){
                        isTabRunning = false;
                        refreshListView.onRefreshComplete();
                        return;
                    }
                    adapter.appendList(couponRecordList.getResult());
                    isTabRunning = false;
                    refreshListView.onRefreshComplete();
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    isTabRunning = false;
                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(CouponActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                    refreshListView.onRefreshComplete();
                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    private Boolean isTabRunning = false;
    public void tabOnClick(View view){
        if(isTabRunning){
            return;
        }

        isTabRunning = true;
        switch (view.getId()){
            case R.id.coupon_tab_1:
                status = "1";
                break;
            case R.id.coupon_tab_2:
                status = "8";
                break;
            case R.id.coupon_tab_3:
                status = "9";
                break;
        }
        pageNo = 1;
        adapter.getList().clear();
        queryList(status);


    }
}
