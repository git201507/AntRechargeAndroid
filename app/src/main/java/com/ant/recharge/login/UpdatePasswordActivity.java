package com.ant.recharge.login;

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
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.net.HttpUtils;
import com.ant.recharge.common.ui.FindPassDialog;
import com.ant.recharge.common.ui.ValidateCodeView;
import com.ant.recharge.entity.User;
import com.ant.recharge.common.NCallbackMsg;

import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/8/12.
 */
public class UpdatePasswordActivity extends BaseActivity {

    Logger logger = Logger.getLogger(UpdatePasswordActivity.class.getSimpleName());
    //图片验证码
    private ImageView imageCodeIV;
    private EditText imageCodeET;
    private String userId = VerifyCode.userid;
    private EditText msgCodeET;//短信验证码

    private EditText passwordET;
    private EditText passwordRepeatET;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_changepass);
        initView(this, R.layout.activity_changepass);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.changepass_title);

        imageCodeIV = (ImageView) findViewById(R.id.imagecode_right);
        imageCodeET = (EditText)findViewById(R.id.get_imagecode);
        msgCodeET = (EditText)findViewById(R.id.code);

        passwordET = (EditText)findViewById(R.id.password);
        passwordRepeatET = (EditText)findViewById(R.id.password_repeat);

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            if(!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
                userId = user.getLoginName();
            }
        } catch (Exception e){
        }

        imageCodeET.setText(userId);
        ValidateCodeView validateCodeView = (ValidateCodeView)findViewById(R.id.code_right);
        validateCodeView.setTelephoneTV(imageCodeET);

        findViewById(R.id.comfirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vilidateCode = msgCodeET.getText().toString();
                if(StringUtils.isBlank(vilidateCode)){
                    Toast.makeText(UpdatePasswordActivity.this, R.string.hint_code, Toast.LENGTH_SHORT).show();
                    return;
                }

                //校验密码
                String password = passwordET.getText().toString();
                if(password == null || "".equals(password.trim())){
                    Toast.makeText(UpdatePasswordActivity.this,R.string.hint_password, Toast.LENGTH_SHORT).show();
                    return;
                }
                String regist_repeat_pass = passwordRepeatET.getText().toString();
                if(regist_repeat_pass == null || "".equals(regist_repeat_pass.trim())){
                    Toast.makeText(UpdatePasswordActivity.this,R.string.regist_repeat_pass, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(regist_repeat_pass.trim())){
                    Toast.makeText(UpdatePasswordActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                }

                //updatePassword();
                isSuccessCode(vilidateCode, userId);
            }
        });

        //createCode();
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
            case R.id.code_right:

                //获取验证码
                ValidateCodeView validateCodeView = (ValidateCodeView)v;
                getValidateCode(validateCodeView);
                return;
            case R.id.imagecode_right:
                //图片验证码
                createCode();
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
        } */

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
                String url = Profile.SERVER_ADDRESS + "/sms/sender/getAppMobileVerifyCode?mobileNumber=" + user.getLoginName()
                        + "&yz=" +imageCodeET.getText().toString().trim()
                        + "&memberId=" + userId;
                final String nResponse = HttpUtils.get(url,null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UpdatePasswordActivity.this, VerifyCode.getMessageByCode(nResponse), Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(ForgotPasswordActivity.this, result, Toast.LENGTH_SHORT).show();
                        updatePassword();
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(UpdatePasswordActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
//                        updatePassword();
                    }
                });

    }

    /**
     * 短信验证是否成功
     * @return
     */
    public void updatePassword(){
        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.resetPassword(userId,
                passwordET.getText().toString(),
                new NCallback<String>(this, String.class) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String content) {
                        finish();
                    }
                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(UpdatePasswordActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private interface NetInterface {

        @POST("/api/wechat/useropr/modifyPassword")
        public void modifyPassword(@Query("token") String token,
                                   @Query("verifyCode") String verifyCode,
                                   @Query("newpassword") String newpassword,
                                   @Query("anewpassword") String anewpassword,
                                   NCallbackMsg callback);
    }

}
