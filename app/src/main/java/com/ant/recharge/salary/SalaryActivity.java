package com.ant.recharge.salary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.SalaryAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.CouponRecordList;
import com.ant.recharge.entity.SalaryHeader;
import com.ant.recharge.entity.SalaryTotalDetailList;
import com.ant.recharge.entity.SalaryWithdrawList;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 蚂蚁工资
 * Created by kwc on 2016/9/7.
 */
public class SalaryActivity extends BaseActivity {

    private TextView surplusTV;//余额
    private TextView financingTV;
    private TextView finshTV;
    private TextView repaymentTV;

    private SalaryAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    private SalaryHeader header = null;

    private User user;

    //1, 4, 2
    private String status = "1";
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_salary);
        initView(this, R.layout.activity_salary);

        surplusTV = (TextView) findViewById(R.id.s_shengyu);
        financingTV = (TextView) findViewById(R.id.record_1);
        finshTV = (TextView) findViewById(R.id.record_2);
        repaymentTV = (TextView) findViewById(R.id.record_3);

        refreshListView = (PullToRefreshListView) findViewById(R.id.coupon_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new SalaryAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);


        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            user = JsonUtil.decode(userStr, User.class);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.my_antsalary);
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



    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化
        queryCount();
    }

    /**
     * 查询优惠券数量
     *
     */
    public void queryCount(){

        NetSalaryInterface netInterface = new NRestAdapter<NetSalaryInterface>(this,
                Profile.SERVER_ADDRESS, NetSalaryInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            netInterface.mysalaryHeaderInfo(user.getToken(), new NCallback<SalaryHeader>(this, new TypeReference<SalaryHeader>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, SalaryHeader header) {

                    queryList(status);
                    try {
                        if(header != null){
                            SalaryActivity.this.header = header;
                            surplusTV.setText("" + header.getSurplus());
                            financingTV.setText("" + header.getTotal());
                            finshTV.setText("" + header.getWithdraw());
                            repaymentTV.setText("" + header.getCashed());
                        }

                    } catch (Exception e) {
                        Toast.makeText(SalaryActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(SalaryActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void queryList(final String status){

        NetSalaryInterface netInterface = new NRestAdapter<NetSalaryInterface>(this,
                Profile.SERVER_ADDRESS, NetSalaryInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            if("1".equals(status)){
                netInterface.mysalaryList("" + pageNo, "" + pageSize, user.getToken(), new NCallback<SalaryTotalDetailList>(this, new TypeReference<SalaryTotalDetailList>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, SalaryTotalDetailList list) {

                        if(!status.equals(SalaryActivity.this.status)){
                            isTabRunning = false;
                            refreshListView.onRefreshComplete();
                            return;
                        }
                        adapter.appendList(list.getResult());
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
                            Toast.makeText(SalaryActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                        }
                        refreshListView.onRefreshComplete();
                    }

                });
            } else if("2".equals(status)){
                netInterface.withdrawList("" + pageNo, "" + pageSize, user.getToken(), new NCallback<SalaryWithdrawList>(this, new TypeReference<SalaryWithdrawList>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, SalaryWithdrawList list) {

                        if(!status.equals(SalaryActivity.this.status)){
                            isTabRunning = false;
                            refreshListView.onRefreshComplete();
                            return;
                        }
                        adapter.appendList(list.getResult());
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
                            Toast.makeText(SalaryActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                        }
                        refreshListView.onRefreshComplete();
                    }

                });
            } else if("3".equals(status)){
                netInterface.couponRecordList(user.getLoginName(),"1",
                        "" + pageNo, "" + pageSize, user.getToken(), new NCallback<CouponRecordList>(this, new TypeReference<CouponRecordList>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, CouponRecordList list) {

                        if(!status.equals(SalaryActivity.this.status)){
                            isTabRunning = false;
                            refreshListView.onRefreshComplete();
                            return;
                        }
                        adapter.appendList(list.getResult());
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
                            Toast.makeText(SalaryActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                        }
                        refreshListView.onRefreshComplete();
                    }

                });
            }

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
                status = "2";
                break;
            case R.id.coupon_tab_3:
                status = "3";
                break;
        }
        adapter.getList().clear();
        pageNo = 1;
        queryList(status);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.my_recharge:
                //兑换优惠券
                if(header != null){
                    Intent intent = new Intent(this, SalaryCouponActivity.class);
                    intent.putExtra("surplus", "" + header.getSurplus());
                    startActivity(intent);
                }
                break;
            case R.id.my_withdraw:
                //工资体现
                if(header != null){
                    Intent intent = new Intent(this, SalaryWithDrawActivity.class);
                    intent.putExtra("surplus", "" + header.getSurplus());
                    startActivity(intent);

                }
                break;
        }

        super.onClick(v);
    }
}
