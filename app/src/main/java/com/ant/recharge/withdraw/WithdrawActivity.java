package com.ant.recharge.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.main.NetAccountInterface;
import com.ant.recharge.webview.WebviewZjActivity;

import java.math.BigDecimal;

/**
 * 提现
 * Created by kwc on 2016/9/7.
 */
public class WithdrawActivity extends BaseActivity {

    private EditText withDrawET;

    //中金帐号
    private TextView zjTV;
    private BigDecimal surplus;//余额
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_withdraw);
        initView(this, R.layout.activity_withdraw);

        zjTV = (TextView) findViewById(R.id.zj);
        withDrawET = (EditText) findViewById(R.id.withdraw_money_value);

        userId = getIntent().getStringExtra("userId");

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.withdraw_title);

        try {
            String accountInfoStr = readPreferences(NetAccountInterface.ANT_ACCOUNT, userId);
            MyAccountDetail myAccountDetail = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);
            surplus = myAccountDetail.getAvailableAmount();

            zjTV.setText(myAccountDetail.getIpsAccount());
            withDrawET.setHint("可用余额" + surplus + "元");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.sign_in_button){

            String _amountFillin = withDrawET.getText().toString();
            if(StringUtils.isBlank(_amountFillin)){
                Toast.makeText(WithdrawActivity.this, R.string.withdraw_money_money, Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("---", "surplus=" + surplus);
            if(surplus.compareTo(new BigDecimal(_amountFillin)) < 0){
                Toast.makeText(WithdrawActivity.this, "余额不足！", Toast.LENGTH_SHORT).show();
                return;
            }

            //提现。。。。
            Intent intent = new Intent(this, WebviewZjActivity.class);
            StringBuilder sb = new StringBuilder();
            sb.append("paymentAccountNumber=");
            sb.append(zjTV.getText().toString());
            sb.append("&amount=");
            Double f = Double.parseDouble(_amountFillin) * 100;
            sb.append(StringUtils.formatDouble(f,2));
            intent.putExtra(WebviewZjActivity.ZJ_TITLE, "提现");
            intent.putExtra(WebviewZjActivity.ZJ_FROM, "cp/member/appTiXian");
            intent.putExtra(WebviewZjActivity.ZJ_CONTENT, sb.toString().getBytes());
            startActivity(intent);
            finish();
            return;
        }

        super.onClick(v);
    }
}
