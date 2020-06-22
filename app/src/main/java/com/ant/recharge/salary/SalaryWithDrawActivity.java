package com.ant.recharge.salary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 工资提现
 * Created by kwc on 2016/9/18.
 */
public class SalaryWithDrawActivity extends BaseActivity {


    private EditText amountET;
    private EditText nameET;
    private EditText bankCodeET;
    private EditText bankNameET;

    private TextView tipTV;

    private Double surplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_salary_withdraw);
        initView(this, R.layout.activity_salary_withdraw);


        amountET = (EditText) findViewById(R.id.salary_salary);
        nameET = (EditText) findViewById(R.id.salary_realName);
        bankCodeET = (EditText) findViewById(R.id.salary_bankcode);
        bankNameET = (EditText) findViewById(R.id.salary_bankname);
        tipTV = (TextView) findViewById(R.id.salary_tips);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.salary_get);


        Intent intent = getIntent();
        surplus = Double.parseDouble(intent.getStringExtra("surplus"));


        StringBuilder sb = new StringBuilder();
        int first_start = sb.length();
        sb.append("        温馨提示:");
        int first_end = sb.length();
        sb.append(" 您的账户余额还剩" + surplus);
        sb.append("元");
        sb.append("根据国家相关规定，我们需要在您提现时代扣20%个人所得税。\n");
        sb.append("        如您希望全额获得工资，建议您将工资提现为优惠券");
        sb.append("优惠券理财将全额返还到您的账户中。\n");
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, first_start, first_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tipTV.setText(builder);

    }

    protected interface NetInterface{

        @POST("/api/wechat/salary/salaryWithdraw")
        public void salaryWithdraw(@Query("token") String token,
                                         @Query("amount") String amount,
                                   @Query("realName") String realName,
                                   @Query("bankCode") String bankCode,
                                   @Query("bankName") String bankName,
                                   NCallback<String> callback);
    }

    @Override
    public void onClick(View v) {

        if (R.id.sign_in_button == v.getId()){

            if(surplus < 100){
                Toast.makeText(this, "工资余额不足!", Toast.LENGTH_SHORT).show();
                return;
            }


            //确认提现
            String amount = amountET.getText().toString();
            String realName = nameET.getText().toString();
            String bankCode = bankCodeET.getText().toString();
            String bankName = bankNameET.getText().toString();

            if(StringUtils.isBlank(amount)){
                Toast.makeText(this,R.string.tx_1, Toast.LENGTH_SHORT).show();
                return;
            }

            double amountValue = Double.valueOf(amount);
            if(amountValue < 100){
                Toast.makeText(this, "提现金额不能少于100元!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(surplus < amountValue){
                Toast.makeText(this, "工资余额不足!", Toast.LENGTH_SHORT).show();
                return;
            }


            if(StringUtils.isBlank(realName)){
                Toast.makeText(this,R.string.tx_2, Toast.LENGTH_SHORT).show();
                return;
            }
            if(StringUtils.isBlank(bankCode)){
                Toast.makeText(this,R.string.tx_3, Toast.LENGTH_SHORT).show();
                return;
            }
            if(StringUtils.isBlank(bankName)){
                Toast.makeText(this,R.string.tx_4, Toast.LENGTH_SHORT).show();
                return;
            }

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
                netInterface.salaryWithdraw(user.getToken(),
                        amount,realName,bankCode,bankName,
                        new NCallback<String>(this, new TypeReference<String>() {}) {
                            @Override
                            public void onSuccess(int statusCode, List<Header> headers, String infoMessage) {

//                                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示信息" message:@"工资提现成功" delegate:self cancelButtonTitle:@"继续提现" otherButtonTitles:@"完成", nil];
//                                [alert show];
                                Toast.makeText(SalaryWithDrawActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                Toast.makeText(SalaryWithDrawActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
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
