package com.ant.recharge.financial;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.FinancialAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.MoneyRecordEntity;
import com.ant.recharge.entity.MoneyRecordHeader;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 资金记录
 * Created by kwc on 2016/9/6.
 */
public class FinancialActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView financingTV;
    private TextView finshTV;
    private TextView repaymentTV;

    private FinancialAdapter adapter;

    private PullToRefreshListView refreshListView;
    private ListView listView;

    //1, 4, 2
    public static final String financial_type_cz = "1";//充值
    public static final String financial_type_tx = "4";//提现
    public static final String financial_type_tz = "2";//投资
    private String currentType = financial_type_cz;


    private int pageNo = 1;
    private int pageSize = 20;

    //tab
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_financial);
        initView(this, R.layout.activity_financial);

        financingTV = (TextView) findViewById(R.id.record_1);
        finshTV = (TextView) findViewById(R.id.record_2);
        repaymentTV = (TextView) findViewById(R.id.record_3);

        refreshListView = (PullToRefreshListView) findViewById(R.id.financial_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();
        adapter = new FinancialAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);

        tab1 = (RadioButton) findViewById(R.id.financial_radio_1);
        tab2 = (RadioButton) findViewById(R.id.financial_radio_2);
        tab3 = (RadioButton) findViewById(R.id.financial_radio_3);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.my_financial_records);


        ((RadioGroup)findViewById(R.id.radioTab)).setOnCheckedChangeListener(this);
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

        //初始化
        setMarginBottom(tab1, 1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        queryCount();
    }

    /**
     * 查询资金记录金额
     *
     */
    public void queryCount(){

        NetFinancialInterface netInterface = new NRestAdapter<NetFinancialInterface>(this,
                Profile.SERVER_ADDRESS, NetFinancialInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.statPersonalMoney(user.getToken(), new NCallback<MoneyRecordHeader>(this, new TypeReference<MoneyRecordHeader>() {}) {
//            netInterface.statPersonalFianceMoney(user.getToken(), new NCallback<MoneyRecordHeader>(this, new TypeReference<MoneyRecordHeader>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, MoneyRecordHeader moneyRecordHeader) {
                    try {
                        if(moneyRecordHeader != null){
                            //financingTV.setText("" + moneyRecordHeader.getTotalTradeAmount());
                            //update by xss
                            financingTV.setText("" + moneyRecordHeader.getTotalRecharge());
                            finshTV.setText("" + moneyRecordHeader.getTotalDraw());
                            repaymentTV.setText("" + moneyRecordHeader.getTotalIncomeAmount());
                        }
                        queryList(currentType);
                    } catch (Exception e) {
                        Toast.makeText(FinancialActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(FinancialActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }

                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }


    }


    public void queryList(final String currentType){

        NetFinancialInterface netInterface = new NRestAdapter<NetFinancialInterface>(this,
                Profile.SERVER_ADDRESS, NetFinancialInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            User user = JsonUtil.decode(userStr, User.class);
            netInterface.findPersonalMoneyTradeList(currentType, pageNo, pageSize, user.getToken(), new NCallback<List<MoneyRecordEntity>>(this, new TypeReference<List<MoneyRecordEntity>>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, List<MoneyRecordEntity> moneyRecordEntityList) {

                    if(!currentType.equals(FinancialActivity.this.currentType)){
                        refreshListView.onRefreshComplete();
                        return;
                    }

                    for (MoneyRecordEntity entity:moneyRecordEntityList){
                        entity.setCurrentType(currentType);
                    }
                    adapter.appendList(moneyRecordEntityList);
                    refreshListView.onRefreshComplete();
//                    refreshListView
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(FinancialActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
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
                currentType = financial_type_cz;
                setMarginBottom(tab1, 1);
                setMarginBottom(tab2, 0);
                setMarginBottom(tab3, 0);
                break;
            case R.id.financial_radio_2:
                currentType = financial_type_tx;
                setMarginBottom(tab1, 0);
                setMarginBottom(tab2, 1);
                setMarginBottom(tab3, 0);
                break;
            case R.id.financial_radio_3:
                currentType = financial_type_tz;
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
