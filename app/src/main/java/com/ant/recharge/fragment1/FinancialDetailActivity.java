package com.ant.recharge.fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.InvestmentAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.FinancePersonEntity;
import com.ant.recharge.entity.FinanceProductDetail;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.NetFianceFragmentInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 *
 * 标的详情
 * Created by kwc on 2016/9/9.
 */
public class FinancialDetailActivity extends BaseActivity {

    private FinanceProductEntity moneyRecordEntity;
    private User user;
    private FinanceProductDetail financeProductEntity;


    private TextView nameTV;
    private TextView expectIncomeTV;//预期年化收益率
    private TextView tempDaysTV;//期限
    //起投金额fd_min
    private TextView minAmountTV;
    private TextView surplusTV;//剩余金额
    private TextView financingTV;//计划金额
    private TextView progressTV;//  进度
    private View lineV;//进度条

    private TextView messageTV;

    //tab
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    //tab content layout
    private View m1;
    private View m2;
    private View m3;

    //tab 1 content
    private TextView date1;
    private TextView date2;
    private TextView date3;

    private PullToRefreshListView refreshListView;
    private InvestmentAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_financial_detail);
        initView(this, R.layout.activity_financial_detail);

        moneyRecordEntity = (FinanceProductEntity) getIntent().getSerializableExtra("financeProductEntity");

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            if (!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }


        nameTV = (TextView) findViewById(R.id.fd_name);
        expectIncomeTV = (TextView) findViewById(R.id.b_1_value);
        tempDaysTV = (TextView) findViewById(R.id.b_2_value);
        minAmountTV = (TextView) findViewById(R.id.fd_min);
        surplusTV = (TextView) findViewById(R.id.df_money_last);
        financingTV = (TextView) findViewById(R.id.df_money_plan);
        progressTV = (TextView) findViewById(R.id.fd_percent);
        lineV = findViewById(R.id.fd_percent_line);

        messageTV = (TextView) findViewById(R.id.fd_message);

        tab1 = (RadioButton) findViewById(R.id.a_1);
        tab2 = (RadioButton) findViewById(R.id.a_2);
        tab3 = (RadioButton) findViewById(R.id.a_3);

        m1 = findViewById(R.id.a_1_layout);
        m1.setVisibility(View.VISIBLE);
        m2 = findViewById(R.id.a_2_layout);
        m3 = findViewById(R.id.a_3_layout);

        date1 = (TextView) findViewById(R.id.fd_date_1);
        date2 = (TextView) findViewById(R.id.fd_date_2);
        date3 = (TextView) findViewById(R.id.fd_date_3);


        refreshListView = (PullToRefreshListView) findViewById(R.id.biao_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        adapter = new InvestmentAdapter(this,new ArrayList());
        refreshListView.setAdapter(adapter);



        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.b_title);



        ((RadioGroup)findViewById(R.id.radioTab)).setOnCheckedChangeListener(new SubjectOnCheckedChangeListener());
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                queryList();
            }
        });

        queryEntity();

    }

    public void queryEntity(){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.appFinanceDetail(moneyRecordEntity.getId(), user.getLoginName(), new NCallback<FinanceProductDetail>(this, FinanceProductDetail.class) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, FinanceProductDetail oRet) {
                Log.d("------oRet" , oRet + "");
                financeProductEntity = oRet;

                if(financeProductEntity != null){
                    loadData();
                }

                queryList();
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Log.d("------infoMessage" , infoMessage + "");
            }
        });

    }

    private void loadData() {
//        financeProductEntity
        nameTV.setText(financeProductEntity.getName());
        expectIncomeTV.setText(financeProductEntity.getProfit() + "%");
        tempDaysTV.setText(financeProductEntity.getFinanceDays() + "天");
        minAmountTV.setText("起投金额" + financeProductEntity.getMinAmount() + "元");
        surplusTV.setText("剩余金额：" + financeProductEntity.getSurplus() + "元");
        financingTV.setText("计划金额：" + financeProductEntity.getFinancing() + "元");

        progressTV.setText(financeProductEntity.getProgress() + "%");
        int w = findViewById(R.id.fd_percent_line_base).getWidth();
        ViewGroup.LayoutParams lineParams = lineV.getLayoutParams();
        lineParams.width = (int) (w * financeProductEntity.getProgress().doubleValue() / 100);
        lineV.setLayoutParams(lineParams);




        //投资信息
        date1.setText(getString(R.string.finance_detail_date1) + "  " + financeProductEntity.getValueDate());
        date2.setText(getString(R.string.finance_detail_date2) + "  " + financeProductEntity.getCeaseDate());
        date3.setText(getString(R.string.finance_detail_date3) + "  " + financeProductEntity.getLatestRepaymentDate());

        //汇票信息
        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isBlank(financeProductEntity.getCouponOne())){
            sb.append("    " + financeProductEntity.getCouponOne() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductEntity.getCouponTwo())){
            sb.append("    " + financeProductEntity.getCouponTwo() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductEntity.getCouponThree())){
            sb.append("    " + financeProductEntity.getCouponThree() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductEntity.getCouponFour())){
            sb.append("    " + financeProductEntity.getCouponFour() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductEntity.getCouponFive())){
            sb.append("    " + financeProductEntity.getCouponFive() + "\r\n");
        }
        messageTV.setText(sb.toString());



    }


    @Override
    public void onClick(View v) {

        if(R.id.fd_buttom == v.getId()){
            //立即购买
            Intent intent = new Intent(this, FinancialBuyActivity.class);
            intent.putExtra("financeProductEntity", financeProductEntity);
            startActivity(intent);
            return;
        }

        super.onClick(v);
    }

    private class SubjectOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int page = 1;
            switch (i){
                case R.id.a_1:
                    m2.setVisibility(View.GONE);
                    m3.setVisibility(View.GONE);
                    m1.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 1);
                    setMarginBottom(tab2, 0);
                    setMarginBottom(tab3, 0);
                    break;
                case R.id.a_2:
                    m1.setVisibility(View.GONE);
                    m3.setVisibility(View.GONE);
                    m2.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 0);
                    setMarginBottom(tab2, 1);
                    setMarginBottom(tab3, 0);
                    break;
                case R.id.a_3:
                    m1.setVisibility(View.GONE);
                    m2.setVisibility(View.GONE);
                    m3.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 0);
                    setMarginBottom(tab2, 0);
                    setMarginBottom(tab3, 1);
                    break;
                default:
                    return;
            }
        }
    }

    public void setMarginBottom(RadioButton view, int bottom){
        RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }



    public void queryList(){

        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.findBidderList(pageNo, pageSize, moneyRecordEntity.getId(), new NCallback<List<FinancePersonEntity>>(this, new TypeReference<List<FinancePersonEntity>>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, List<FinancePersonEntity> list) {

                    adapter.appendList(list);
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
                        Toast.makeText(FinancialDetailActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }

                }

            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }
}
