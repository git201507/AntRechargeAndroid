package com.ant.recharge.verified;

import android.os.Bundle;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.entity.User;
import com.ant.recharge.main.NetAccountInterface;

/**
 * Created by kwc on 2016/9/3.
 */
public class VerifiedSuccessActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_verified_success);
        initView(this, R.layout.activity_verified_success);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.verified_success_title);

        try {
            String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
            User user = JsonUtil.decode(userStr, User.class);

            String accountInfoStr = readPreferences(NetAccountInterface.ANT_ACCOUNT, user.getLoginName());
            MyAccountDetail myAccountDetail = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);

            ((TextView)findViewById(R.id.vs_name)).setText(user.getName());
            ((TextView)findViewById(R.id.vs_phone)).setText(user.getTelephone());
            ((TextView)findViewById(R.id.vs_pay_account)).setText(myAccountDetail.getIpsAccount());
            String  realNameVerify = "";
            if (myAccountDetail != null && myAccountDetail.getIpsAccount() != null && myAccountDetail.getIpsAccount().length() > 0) {
                realNameVerify = "已认证";
            } else {
                realNameVerify = " 未认证";
            }
            ((TextView)findViewById(R.id.vs_really)).setText(realNameVerify);
        } catch (Exception e) {

        } finally {

        }




    }

}
