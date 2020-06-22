package com.ant.recharge.fragment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.FinanceProductDetail;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.NetAccountInterface;
import com.ant.recharge.main.NetFianceFragmentInterface;
import com.ant.recharge.verified.VerifiedActivity;
import com.ant.recharge.verified.VerifiedUtil;
import com.ant.recharge.webview.WebviewZjActivity;

import java.math.BigDecimal;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;

/**
 * 标的详情 step 2
 * Created by kwc on 2016/9/9.
 */
public class FinancialBuyActivity extends BaseActivity {

    private FinanceProductEntity financeProductEntity;
    private FinanceProductDetail financeProductDetail;
    private User user;
    private MyAccountDetail myAccountDetail;

    private TextView nameTV;
    private TextView expectIncomeTV;//预期年化收益率
    private TextView tempDaysTV;//期限
    //起投金额fd_min
    private TextView minAmountTV;
    private TextView surplusTV;//剩余金额
    private TextView financingTV;//计划金额
    private TextView progressTV;//  进度
    private View lineV;//进度条

    private EditText moneyET;//购买金额
    private TextView date1;//预期收益
    private TextView date2;//会员增益金额
    private TextView date3;

    private BigDecimal surplus;//余额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_financial_detail);
        initView(this, R.layout.activity_financial_detail);

        financeProductEntity = (FinanceProductEntity) getIntent().getSerializableExtra("financeProductEntity");
        nameTV = (TextView) findViewById(R.id.fd_name);
        expectIncomeTV = (TextView) findViewById(R.id.b_1_value);
        tempDaysTV = (TextView) findViewById(R.id.b_2_value);
        minAmountTV = (TextView) findViewById(R.id.fd_min);
        surplusTV = (TextView) findViewById(R.id.df_money_last);
        financingTV = (TextView) findViewById(R.id.df_money_plan);
        progressTV = (TextView) findViewById(R.id.fd_percent);
        lineV = findViewById(R.id.fd_percent_line);

        moneyET = (EditText) findViewById(R.id.m_money);

        date1 = (TextView) findViewById(R.id.m_pre);
        date2 = (TextView) findViewById(R.id.m_pre2);
        date3 = (TextView) findViewById(R.id.m_sur);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.c_title);
        findViewById(R.id.radioTab).setVisibility(View.GONE);
        findViewById(R.id.m_money_layout).setVisibility(View.VISIBLE);

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            user = JsonUtil.decode(userStr, User.class);
            String accountInfoStr = readPreferences(NetAccountInterface.ANT_ACCOUNT, user.getLoginName());

            myAccountDetail = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);
            surplus = myAccountDetail.getAvailableAmount();
        } catch (Exception e){
        }


        financeProductDetail = (FinanceProductDetail) getIntent().getSerializableExtra("financeProductDetail");
        if(financeProductDetail != null){
            loadData();
        } else {
            queryEntity();
        }


        moneyET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                date1.setText(expectedReturn() + "元");
                date2.setText(memberReturn() + "元");
            }
        });
    }
    /**
     * 预计收益
     * @return
     */
    private BigDecimal expectedReturn(){

        String moneyStr = moneyET.getText().toString();
        if(StringUtils.isBlank(moneyStr)){
            return new BigDecimal(0);
        }

        if(financeProductDetail.getProfit() == null
                || financeProductDetail.getTempDays() <= 0){
            return new BigDecimal(0);
        }

        double money = Double.parseDouble(moneyStr);


        double result = money * financeProductDetail.getTempDays()  * financeProductDetail.getProfit().doubleValue() / 36500;
        return  new BigDecimal(result).setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 会员增益
     * @return
     */
    private BigDecimal memberReturn(){
        String moneyStr = moneyET.getText().toString();
        if(StringUtils.isBlank(moneyStr)){
            return new BigDecimal(0);
        }

        if(financeProductDetail.getTempDays() <= 0){
            return new BigDecimal(0);
        }

        double fPercent = getFpercentByLevel(myAccountDetail.getMemberLevel(), myAccountDetail.getInvitorLevel());
        if(fPercent <= 0){
            return new BigDecimal(0);
        }

        double money = Double.parseDouble(moneyStr);
//        double result = money * (financeProductEntity.getTempDays().intValue() - 1) / 365 * fPercent;
        double result = money * financeProductDetail.getTempDays() / 365 * fPercent;

        return  new BigDecimal(result).setScale(2, BigDecimal.ROUND_DOWN);
    }

    private double getFpercentByLevel(String level, String invitorLevel){

        if("level_1".equals(level) || "level_1".equals(invitorLevel)){
            return 0.01;
        }

        if("level_2".equals(level) || "level_2".equals(invitorLevel)){
            return 0.01;
        }

        if("level_3".equals(level) || "level_3".equals(invitorLevel)){
            return 0.005;
        }

        return 0;
    }

    public void queryEntity(){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.appFinanceDetail(financeProductEntity.getId(), user.getLoginName(), new NCallback<FinanceProductDetail>(this, FinanceProductDetail.class) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, FinanceProductDetail oRet) {
                financeProductDetail = oRet;
                if(financeProductDetail != null){
                    loadData();
                }
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Log.d("------infoMessage" , infoMessage + "");
            }
        });

    }



    private void loadData() {
        nameTV.setText(financeProductDetail.getName());
        expectIncomeTV.setText(financeProductDetail.getProfit() + "%");
        tempDaysTV.setText(financeProductDetail.getFinanceDays() + "天");
        minAmountTV.setText("起投金额" + financeProductDetail.getMinAmount() + "元");
        surplusTV.setText("剩余金额：" + financeProductDetail.getSurplus() + "元");
        financingTV.setText("计划金额：" + financeProductDetail.getFinancing() + "元");

        progressTV.setText(financeProductDetail.getProgress() + "%");
        int w = findViewById(R.id.fd_percent_line_base).getWidth();
        ViewGroup.LayoutParams lineParams = lineV.getLayoutParams();
        lineParams.width = (int) (w * financeProductDetail.getProgress().doubleValue() / 100);
        lineV.setLayoutParams(lineParams);



        date3.setText("*您的账余额还剩" + surplus + "元");



    }


    @Override
    public void onClick(View v) {

        if(R.id.fd_buttom == v.getId()){

            if(StringUtils.isBlank(moneyET.getText().toString().trim())){
                Toast.makeText(this,"购买金额不能为空!", Toast.LENGTH_SHORT).show();
                return;
            }

            long surplus = financeProductDetail.getSurplus() * 100;

            //购买金额
            double money = Double.parseDouble(moneyET.getText().toString().trim());
            long amount = StringUtils.formatDouble(money*100,2);

            //新手标
            if(financeProductEntity.getNews()){

                //不是新手
                SharedPreferences preferences = getSharedPreferences(NetAccountInterface.ANT_ACCOUNT, Context.MODE_PRIVATE);
                String accountInfoStr = preferences.getString(user.getLoginName(), null);
                try {
                    MyAccountDetail myAccountDetail  = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);
                    //不是新手
                    if(myAccountDetail.getNews() != 1){
                        Toast.makeText(this,"只有第一次购买的用户才能购买且最大购买金额为2000!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
//                            e.printStackTrace();
                }

                long max = financeProductDetail.getMaxAmount();

                if(max < 2000){
                    max = 2000;
                }
                //新手标，不能买超过2000
                if(money > max){
                    Toast.makeText(this,"本新手标最大购买金额为" + max + "元!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this,"只有第一次购买的用户才能购买!", Toast.LENGTH_SHORT).show();
                    return;

                }
            }

            if(amount > surplus){
                Toast.makeText(this,"投资金额不能大于可融资金额!", Toast.LENGTH_SHORT).show();
                return;
            }

            //小于起投
            if(money < financeProductDetail.getMinAmount()){
                Toast.makeText(this,financeProductDetail.getMinAmount() + "元起投!", Toast.LENGTH_SHORT).show();
                return;
            }

            //剩余金额，小于起投金额
            if((financeProductDetail.getSurplus() > money) && ((financeProductDetail.getSurplus() - money) < financeProductDetail.getMinAmount())){
                Toast.makeText(this,"标的即将满标，您需要一次性投资" + financeProductDetail.getSurplus() + "元!", Toast.LENGTH_SHORT).show();
                return;
            }

            //是否认证
            if(!VerifiedUtil.isVerified(this, user.getLoginName())){
                startActivity(new Intent(this, VerifiedActivity.class));
                finish();
                return;
            }

            //立即购买
            Intent intent = new Intent(this, WebviewZjActivity.class);
            StringBuilder sb = new StringBuilder();
            sb.append("amount=");
            sb.append(amount);
            sb.append("&gmUserID=");
            sb.append(user.getLoginName());
            sb.append("&draftID=");
            sb.append(financeProductEntity.getId());

            intent.putExtra(WebviewZjActivity.ZJ_FROM, "/cp/member/appZhiFu");
            intent.putExtra(WebviewZjActivity.ZJ_TITLE, "立即购买");
            intent.putExtra(WebviewZjActivity.ZJ_CONTENT, sb.toString().getBytes());
            startActivity(intent);
            finish();
            return;
        }

        super.onClick(v);
    }

    public interface NetInterface {

        //立即购买 //amount=%@&gmUserID=%@&draftID=%@
        @POST("/cp/member/appZhiFu")
        public void appZhiFu(NCallback<MyAccountDetail> callback);
    }

}
