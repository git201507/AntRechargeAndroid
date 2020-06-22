package com.ant.recharge.salary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.SalaryCouponAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.CouponCenterEntity;
import com.ant.recharge.entity.CouponCenterList;
import com.ant.recharge.entity.SalaryHeader;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 兑换优惠券
 * Created by kwc on 2016/9/18.
 *
 */
public class SalaryCouponActivity extends BaseActivity {

    private TextView financingTV;//工资余额
    private PullToRefreshListView refreshListView;
    private ListView listView;
    private SalaryCouponAdapter adapter;
    private CouponCenterEntity selectCouponCenterEntity = null;

    private String status = "2";
    private int pageNo = 1;
    private int pageSize = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_salary_coupon);
        initView(this, R.layout.activity_salary_coupon);

        financingTV = (TextView) findViewById(R.id.salary_coupon_money);

        refreshListView = (PullToRefreshListView) findViewById(R.id.coupon_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new SalaryCouponAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);


        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.salary_coupon);

        Intent intent = getIntent();
        StringBuilder sb = new StringBuilder();
        sb.append("工资余额：");
        int first_start = sb.length();
        sb.append(intent.getStringExtra("surplus"));
        int first_end = sb.length();
        sb.append("元");
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, first_start, first_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        financingTV.setText(builder);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectCouponCenterEntity != null){
                    selectCouponCenterEntity.setSelected(false);
                }
                selectCouponCenterEntity = (CouponCenterEntity) adapter.getItem(i-1);
                selectCouponCenterEntity.setSelected(true);

                adapter.notifyDataSetChanged();
            }
        });



        queryList();

    }

    public void queryList(){

        NetInterface netInterface = new NRestAdapter<NetInterface>(this,
                Profile.SERVER_ADDRESS, NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.couponRecordList(user.getLoginName(),
                    status, "" + pageNo, "" + pageSize, "1",
                    new NCallback<CouponCenterList>(this, new TypeReference<CouponCenterList>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, CouponCenterList list) {
                    adapter.appendList(list.getResult());
                    refreshListView.onRefreshComplete();
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    Toast.makeText(SalaryCouponActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    refreshListView.onRefreshComplete();
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    protected interface NetInterface{

        @POST("/api/wechat/salary/salaryAmount")
        public void mysalaryHeaderInfo(@Query("token") String token, NCallback<SalaryHeader> callback);

        //兑换优惠券列表
        @POST("/api/wechat/coupon/couponList")
        public void couponRecordList(@Query("userid") String userid,
                                     @Query("status") String status,//2
                                     @Query("pageNo") String pageNo,
                                     @Query("pageSize") String pageSize,
                                     @Query("couponType") String couponType, NCallback<CouponCenterList> callback);

        @POST("/api/wechat/salary/salaryExchangeCoupon")
        public void salaryExchangeCoupon(@Query("token") String token,
                                     @Query("couponID") String couponID, NCallback<String> callback);
    }

    @Override
    public void onClick(View v) {

        if(R.id.sign_in_button == v.getId()){
            //申请兑换
            if(selectCouponCenterEntity == null){
                return;
            }

//            if(selectCouponCenterEntity != null){
//                Log.d("--------coupon",selectCouponCenterEntity.getAmount() + "");
//                return;
//            }

            NetInterface netInterface = new NRestAdapter<NetInterface>(this,
                    Profile.SERVER_ADDRESS, NetInterface.class)
                    .create();
            if(netInterface == null){
                Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
            try {
                User user = JsonUtil.decode(userStr, User.class);
                netInterface.salaryExchangeCoupon(user.getToken(),
                        selectCouponCenterEntity.getId(),
                        new NCallback<String>(this, new TypeReference<String>() {}) {
                            @Override
                            public void onSuccess(int statusCode, List<Header> headers, String infoMessage) {
                                Toast.makeText(SalaryCouponActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                Toast.makeText(SalaryCouponActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            } catch (Exception e) {
//            e.printStackTrace();
            }

            return;
        }

        super.onClick(v);
    }
}
