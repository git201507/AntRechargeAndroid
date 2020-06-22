package com.ant.recharge.member;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.MemberAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.InvitorEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 邀请人员列表
 * Created by kwc on 2016/9/19.
 */
public class MemberActivity extends BaseActivity {

    private TextView titleTV;

    private MemberAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private int pageNo = 1;
    private int pageSize = 100;//一次性加载


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_member);
        initView(this, R.layout.activity_member);



        titleTV = (TextView) findViewById(R.id.member_count_and_salary);
        refreshListView = (PullToRefreshListView) findViewById(R.id.member_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new MemberAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.member_title);

        titleTV.setText("            " + getString(R.string.member_count) + "0    " + getString(R.string.member_salary_tatal) + "0");


        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {}
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                queryList();
            }
        });

        queryList();
    }

    private interface NetInterface{

        //头部信息
        @POST("/api/wechat/salary/findMyInvitorList")
        public void mysalaryHeaderInfo(@Query("start") String _pageNo,
                                       @Query("count") String count,
                                       @Query("token") String token, NCallback<List<InvitorEntity>> callback);
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
            netInterface.mysalaryHeaderInfo("" + pageNo, "" + pageSize, user.getToken(), new NCallback<List<InvitorEntity>>(this, new TypeReference<List<InvitorEntity>>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, List<InvitorEntity> list) {
                    adapter.appendList(list);

                    String[] total = adapter.getTotalStr();
                    titleTV.setText("            " + getString(R.string.member_count) + total[0] + "    " + getString(R.string.member_salary_tatal) + total[1]);
                    refreshListView.onRefreshComplete();
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    //403定义为过期
                        Toast.makeText(MemberActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    refreshListView.onRefreshComplete();
                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }
}
