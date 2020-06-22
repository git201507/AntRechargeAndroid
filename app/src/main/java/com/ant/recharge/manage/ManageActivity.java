package com.ant.recharge.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.ManageAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.FinanceHistoryHeader;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.FinancialDetailActivity;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 理财记录
 * Created by kwc on 2016/9/6.
 */
public class ManageActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView raiseTV;
    private TextView manageTV;
    private TextView investmentTV;

    private ManageAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    //1, 4, 2
    private String currentType = "2";
    public static final String type_1 = "1";
    public static final String type_2 = "2";
    public static final String type_3 = "3";


    private int pageNo = 1;
    private int pageSize = 20;

    //tab
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage);
        initView(this, R.layout.activity_manage);

        raiseTV = (TextView) findViewById(R.id.record_1);
        manageTV = (TextView) findViewById(R.id.record_2);
        investmentTV = (TextView) findViewById(R.id.record_3);

        refreshListView = (PullToRefreshListView) findViewById(R.id.financial_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new ManageAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        tab1 = (RadioButton) findViewById(R.id.financial_radio_1);
        tab2 = (RadioButton) findViewById(R.id.financial_radio_2);
        tab3 = (RadioButton) findViewById(R.id.financial_radio_3);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.my_manage_records);

        ((RadioGroup)findViewById(R.id.manage_radio_group)).setOnCheckedChangeListener(this);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                queryList(currentType);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageActivity.this, FinancialDetailActivity.class);
                intent.putExtra("financeProductEntity", (Serializable) adapter.getItem(i));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        queryCount();
    }

    /**
     * 查询理财记录金额
     *
     */
    public void queryCount(){

        NetManageInterface netInterface = new NRestAdapter<NetManageInterface>(this,
                Profile.SERVER_ADDRESS, NetManageInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.statPersonalFianceMoney(user.getToken(), new NCallback<FinanceHistoryHeader>(this, new TypeReference<FinanceHistoryHeader>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, FinanceHistoryHeader moneyRecordHeader) {
                    try {
                        if(moneyRecordHeader != null){
                            raiseTV.setText("" + moneyRecordHeader.getInvestment());
                            manageTV.setText("" + moneyRecordHeader.getFinancing());
                            investmentTV.setText("" + moneyRecordHeader.getFinsh());
                        }

                        //初始化
                        setMarginBottom(tab2, 1);
                        queryList(currentType);

                    } catch (Exception e) {
                        Toast.makeText(ManageActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(ManageActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }


    }

    public void queryList(final String currentType){

        NetManageInterface netInterface = new NRestAdapter<NetManageInterface>(this,
                Profile.SERVER_ADDRESS, NetManageInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.fianceList(currentType, pageNo, pageSize, user.getToken(), new NCallback<List<FinanceProductEntity>>(this, new TypeReference<List<FinanceProductEntity>>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, List<FinanceProductEntity> list) {
                    try {
                        if(!currentType.equals(ManageActivity.this.currentType)){
                            refreshListView.onRefreshComplete();
                            return;
                        }
                        adapter.appendList(list);

                    } catch (Exception e) {
                        Toast.makeText(ManageActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    }
                    refreshListView.onRefreshComplete();
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(ManageActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                    refreshListView.onRefreshComplete();
                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        //清空
        adapter.replaceList(new ArrayList());
        pageNo = 1;
        switch (i){
            case R.id.financial_radio_1:
                currentType = type_1;
                setMarginBottom(tab1, 1);
                setMarginBottom(tab2, 0);
                setMarginBottom(tab3, 0);
                break;
            case R.id.financial_radio_2:
                currentType = type_2;
                setMarginBottom(tab1, 0);
                setMarginBottom(tab2, 1);
                setMarginBottom(tab3, 0);
                break;
            case R.id.financial_radio_3:
                currentType = type_3;
                setMarginBottom(tab1, 0);
                setMarginBottom(tab2, 0);
                setMarginBottom(tab3, 1);
                break;
            default:
                return;
        }
        queryList(currentType);
    }

    public void setMarginBottom(RadioButton view, int bottom){
        RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }
}
