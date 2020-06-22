package com.ant.recharge.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.application.MyApplication;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.Md5Util;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.phone.PhoneUtils;
import com.ant.recharge.entity.User;
import com.ant.recharge.main.MainTabActivity;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;

/**
 * 登录
 * input:  mobile phone, password
 *
 */
public class LoginActivity extends BaseActivity {

    Logger logger = Logger.getLogger(LoginActivity.class.getSimpleName());


    private AutoCompleteTextView userNameTV;//用户名
    private EditText passwordTV;//密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(this, R.layout.activity_login);

        userNameTV = (AutoCompleteTextView)findViewById(R.id.phone_number_tv);
        passwordTV = (EditText)findViewById(R.id.password);

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");

        User user = null;
        try {
            if(!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
                userNameTV.setText(user.getLoginName());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                 login(userNameTV.getText().toString(), passwordTV.getText().toString());
                return;
            case R.id.login_regist:
                startActivityForResult(new Intent(this, RegistActivity.class), 1);
                return;
            case R.id.login_forgot:
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                intent.putExtra("mobilePhone", userNameTV.getText().toString());
                startActivity(intent);
                return;
            default:
                break;
        }
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册
        if(1 == requestCode){
//            Log.d("-----", "-----------resultCode=" + resultCode);
//            Log.d("-----", "-----------data=" + data.toString());
            if(200 == resultCode){
                finish();
            }
        }

    }

    /**
     * login step 1
     *
     */
    public void login(String userName, String password){

        //校验手机号
        if(!PhoneUtils.isMobilePhone(userName,this)){
            return;
        }

        //校验密码
        if(password == null || "".equals(password.trim())){
            Toast.makeText(this,R.string.hint_password, Toast.LENGTH_SHORT).show();
            return;
        }

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }


//        netInterface.login(userName, Md5Util.md5(password), new NCallback<User>(this, new TypeReference<User>() {}) {
        netInterface.login(userName, password, new NCallback<User>(this, new TypeReference<User>() {}) {

            @Override
            public void onSuccess(int statusCode, List<Header> headers, User oRet) {
                try {
                    String userStr = JsonUtil.encode(oRet);

                    Log.d(this.getClass().getSimpleName(), "-----" + userStr);

                    //保存user附加信息， 使用json
                    writePreferences(NetLoginInterface.ANT_LOGIN_USER, "additional", userStr);

                    //进入主页
                    Intent mainIntent = new Intent(LoginActivity.this, MainTabActivity.class);
                    startActivity(mainIntent);
                    MyApplication myApplication = (MyApplication) getApplicationContext();
                    MainTabActivity mainTabActivity = myApplication.getMainTabActivity();
                    mainTabActivity.updateAllFragment();
                    LoginActivity.this.finish();

                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    logger.warning("list_header error" + e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Toast.makeText(LoginActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

}

