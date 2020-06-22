package com.ant.recharge.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.net.HttpUtils;
import com.ant.recharge.common.phone.PhoneUtils;
import com.ant.recharge.common.ui.FindPassDialog;
import com.ant.recharge.common.ui.ValidateCodeView;
import com.ant.recharge.entity.User;

import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;

/**
 * 找回密码
 * Created by kwc on 2016/8/12.
 */
public class ForgotPasswordActivity extends BaseActivity {

    Logger logger = Logger.getLogger(ForgotPasswordActivity.class.getSimpleName());

    private EditText mobileET;//手机号
    //图片验证码
    private ImageView imageCodeIV;
    private EditText imageCodeET;

    private EditText msgCodeET;//短信验证码

    private String userId = VerifyCode.userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_findpass);
        initView(this, R.layout.activity_findpass);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.findpass_title);
        mobileET = (EditText) findViewById(R.id.email);
        imageCodeIV = (ImageView) findViewById(R.id.imagecode_right);
        imageCodeET = (EditText)findViewById(R.id.get_imagecode);
        msgCodeET = (EditText)findViewById(R.id.code);

        //带上手机号
        String mobile = getIntent().getStringExtra("mobilePhone");
        if(StringUtils.isBlank(mobile)){
            String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
            try {
                if(!StringUtils.isBlank(userStr)){
                    User user = JsonUtil.decode(userStr, User.class);
                    mobile = user.getTelephone();
                }
            } catch (Exception e){
            }
        }
        mobileET.setText(mobile);
        //createCode();
        ValidateCodeView validateCodeView = (ValidateCodeView)findViewById(R.id.code_right);
        validateCodeView.setTelephoneTV(mobileET);
    }

    private void createCode(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = VerifyCode.getCodeBitmap(userId);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            imageCodeIV.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
//            e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:

                //校验手机号
                if(!PhoneUtils.isMobilePhone(mobileET.getText().toString(),this)){
                    return;
                }

                //找回密码
                if(StringUtils.isBlank(msgCodeET.getText().toString().trim())){
                    Toast.makeText(this,"请输入验证码!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //验证短信验证码
                isSuccessCode(msgCodeET.getText().toString().trim(), mobileET.getText().toString());
                return;
            case R.id.imagecode_right:
                //图片验证码
                createCode();
                return;
            case R.id.code_right:
                //校验手机号
//                if(!PhoneUtils.isMobilePhone(mobileET.getText().toString(),this)){
//                    return;
//                }
//
//                validatePhoneNumber(1,mobileET.getText().toString(), v, null);
                //获取验证码
//                ValidateCodeView validateCodeView = (ValidateCodeView)v;
//                getValidateCode(validateCodeView);
                return;
            default:
                break;
        }
        super.onClick(v);
    }

    /**
     * 获取验证码
     * validateCodeView
     */
    private void getValidateCode(final ValidateCodeView validateCodeView){

        //图片验证码
        if(StringUtils.isBlank(imageCodeET.getText().toString())){
            Toast.makeText(this,"请输入图形验证码!", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
        if(imageCodeET.getText().toString().length() != 4){
            Toast.makeText(this,"图形验证码不正确!", Toast.LENGTH_SHORT).show();
            return;
        }
         */

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Profile.SERVER_ADDRESS + "/sms/sender/getAppMobileVerifyCode?mobileNumber=" + mobileET.getText().toString().trim()
                        + "&yz=" +imageCodeET.getText().toString().trim()
                        + "&memberId=" + userId;
                final String nResponse = HttpUtils.get(url,null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgotPasswordActivity.this, VerifyCode.getMessageByCode(nResponse), Toast.LENGTH_SHORT).show();
                        if("success:120".equals(nResponse)){
                            validateCodeView.clickView();
                        }
                    }
                });
            }
        }).start();


    }

    /**
     * 短信验证是否成功
     * @param pageVerifyCode
     * @param phoneNumber
     * @return
     */
    public void isSuccessCode(final String pageVerifyCode, final String phoneNumber){

        NetCodeInterface netInterface = new NRestAdapter<NetCodeInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetCodeInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.validateUserPhoneVerifyCode(pageVerifyCode,
                phoneNumber,
                new NCallbackMsg() {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String result) {
                        findPassWord("123456"/*pageVerifyCode*/, phoneNumber);
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(ForgotPasswordActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 找回密码
     * @param pageVerifyCode
     * @param phoneNumber
     */
    public void findPassWord(final String pageVerifyCode, final String phoneNumber){

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.resetPassword(phoneNumber,
                pageVerifyCode,
                new NCallback<String>(this, String.class) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String content) {
                        new FindPassDialog(ForgotPasswordActivity.this, "密码找回成功\n新密码:"
                                + "123456"/*content*/
                                + "\r\n请您尽快登录，重新修改密码！")
                                .show();
                    }
                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(ForgotPasswordActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    //flag  1:获取验证码   2：找回密码
    private void validatePhoneNumber(final int flag, final String phone, final View v, final String pageVerifyCode){

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.validatePhoneNumber(phone,
                new NCallbackMsg() {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String oRet) {
                        Toast.makeText(ForgotPasswordActivity.this, "手机号不存在!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        if(1 == flag){
                            //获取验证码
                            ValidateCodeView validateCodeView = (ValidateCodeView)v;
                            getValidateCode(validateCodeView);
                        }

                        if(2 == flag){
                            findPassWord(pageVerifyCode, phone);
                        }
                    }
                });
    }

}