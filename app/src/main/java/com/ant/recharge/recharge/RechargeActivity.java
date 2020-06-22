package com.ant.recharge.recharge;

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

/**
 * 充值
 * Created by kwc on 2016/9/7.
 */
public class RechargeActivity extends BaseActivity {

    private EditText rechargeET;
    //中金帐号
    private TextView zjTV;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recharge);
        initView(this, R.layout.activity_recharge);

        rechargeET = (EditText) findViewById(R.id.recharge_money_value);
        zjTV = (TextView) findViewById(R.id.zj);

        userId = getIntent().getStringExtra("userId");

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.recharge_title);


        try {
            String accountInfoStr = readPreferences(NetAccountInterface.ANT_ACCOUNT, userId);
            MyAccountDetail myAccountDetail = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);

            zjTV.setText(myAccountDetail.getIpsAccount());
        } catch (Exception e) {
//            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.sign_in_button){

            String _amountFillin = rechargeET.getText().toString();
            if(StringUtils.isBlank(_amountFillin)){
                Toast.makeText(RechargeActivity.this, R.string.recharge_hint_money, Toast.LENGTH_SHORT).show();
                return;
            }

            //充值。。。。
            Intent intent = new Intent(this, WebviewZjActivity.class);
            StringBuilder sb = new StringBuilder();
            sb.append("paymentAccountNumber=");
            sb.append(zjTV.getText().toString());
            sb.append("&amount=");
            Double f = Double.parseDouble(_amountFillin) * 100;
            Log.d("---", "f=" + f);
            sb.append(StringUtils.formatDouble(f,2));
            intent.putExtra(WebviewZjActivity.ZJ_FROM, "/cp/member/appChongZhi");
            intent.putExtra(WebviewZjActivity.ZJ_TITLE, "充值");
            intent.putExtra(WebviewZjActivity.ZJ_CONTENT, sb.toString().getBytes());
            startActivity(intent);
            finish();
            return;
        }

        super.onClick(v);
    }
}
