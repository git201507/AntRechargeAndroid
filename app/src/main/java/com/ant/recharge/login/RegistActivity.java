package com.ant.recharge.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.Md5Util;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.net.HttpUtils;
import com.ant.recharge.common.phone.PhoneUtils;
import com.ant.recharge.common.ui.ValidateCodeView;
import com.ant.recharge.entity.User;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.order.DelegateOrderChangeActivity;
import com.ant.recharge.verified.VerifiedActivity;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/8/11.
 * 注册
 */
public class RegistActivity extends BaseActivity{

    Logger logger = Logger.getLogger(RegistActivity.class.getSimpleName());

    private AutoCompleteTextView phoneET;//手机号
    private EditText codeET;//验证码
    private View clickView;
    //loading view
    private TextView loadingView;

    private EditText passwordET;//密码
    private EditText passwordRepeatET;//重复密码
//    private EditText invitationcodeET;//邀请码
    private String userId = VerifyCode.userid;

    //图片验证码
    private ImageView imageCodeIV;
    private EditText imageCodeET;

    public AutoCompleteTextView getPhoneET() {
        return phoneET;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regist);
        initView(this, R.layout.activity_regist);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.regist_title);

        phoneET = (AutoCompleteTextView)findViewById(R.id.email);
        codeET = (EditText)findViewById(R.id.code);
        passwordET = (EditText)findViewById(R.id.password);
        passwordRepeatET = (EditText)findViewById(R.id.password_repeat);
//        invitationcodeET = (EditText)findViewById(R.id.invitationcode);
        imageCodeIV = (ImageView) findViewById(R.id.imagecode_right);
        imageCodeET = (EditText)findViewById(R.id.get_imagecode);
        ValidateCodeView validateCodeView = (ValidateCodeView)findViewById(R.id.code_layout);
        validateCodeView.setTelephoneTV(phoneET);

        createCode();
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
                if(!PhoneUtils.isMobilePhone(phoneET.getText().toString(),this)){
                    return;
                }

                //校验验证码
                String vilidateCode = codeET.getText().toString();
                if(StringUtils.isBlank(vilidateCode)){
                    Toast.makeText(this, R.string.hint_code, Toast.LENGTH_SHORT).show();
                    return;
                }

                //校验密码
                String password = passwordET.getText().toString();
                if(password == null || "".equals(password.trim())){
                    Toast.makeText(this,R.string.hint_password, Toast.LENGTH_SHORT).show();
                    return;
                }
                String regist_repeat_pass = passwordRepeatET.getText().toString();
                if(regist_repeat_pass == null || "".equals(regist_repeat_pass.trim())){
                    Toast.makeText(this,R.string.regist_repeat_pass, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(regist_repeat_pass.trim())){
                    Toast.makeText(this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //邀请码， 不为空需要验证
//                String invitationcode = invitationcodeET.getText().toString().trim();
//                if(!StringUtils.isBlank(invitationcode)){
//                    validateInvisitCode(phoneET.getText().toString().trim(), invitationcode);
//                } else {
                    //验证短信验证码  //注册
                    isSuccessCode(vilidateCode, phoneET.getText().toString());
//                }

                return;
            case R.id.regist_protocal:
                //注册协议
                startActivity(new Intent(this, ProtocolActivity.class));
                return;
            case R.id.code_layout:
                //获取验证码
                ValidateCodeView validateCodeView = (ValidateCodeView)v;
                getValidateCode(validateCodeView);
//                validatePhoneNumber(1,phoneET.getText().toString(), v, null);

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


    //flag  1:获取验证码   2：注册
    private void validatePhoneNumber(final int flag, final String phone, final View v, final String vilidateCode){

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

                        if(1 == flag){
                            //获取验证码
                            ValidateCodeView validateCodeView = (ValidateCodeView)v;
                            getValidateCode(validateCodeView);
                        }

                        if(2 == flag){
//                            String invitationcode = invitationcodeET.getText().toString().trim();
//                            regist(phone, imageCodeET.getText().toString(), passwordET.getText().toString(), invitationcode, "");
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(RegistActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 邀请码判断
     * @param invitationcode
     */
    public void validateInvisitCode(final String phoneNumber,final String invitationcode){

        NeInterface netInterface = new NRestAdapter<NeInterface>(this,
                Profile.SERVER_ADDRESS, NeInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.checkgetInviteCode(invitationcode,
                new NCallbackMsg() {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String result) {

                        validatePhoneNumber(2, phoneNumber, null, invitationcode);

                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(RegistActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });
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
                        Intent intent = new Intent(RegistActivity.this, RegistDelegateActivity.class);
                        intent.putExtra("password", passwordET.getText().toString().trim());
                        intent.putExtra("telephone", phoneNumber);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(RegistActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * login step 1
     *
     */
    public void regist(final String userName, String code,final String password, String inviteCode, String openId){

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
//        netInterface.register(userName, code,password, inviteCode,openId,
//                new NCallbackMsg() {
//                    @Override
//                    public void onSuccess(int statusCode, List<Header> headers, String oRet) {
////                        Toast.makeText(RegistActivity.this, oRet, Toast.LENGTH_SHORT).show();
//
//                        login(userName,password);
//                    }
//                    @Override
//                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
//                        Toast.makeText(RegistActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }


    /**
     * 获取验证码
     * validateCodeView
     */
    private void getValidateCode(final ValidateCodeView validateCodeView){

        //图片验证码
//        if(StringUtils.isBlank(imageCodeET.getText().toString())){
//            Toast.makeText(this,"请输入图形验证码!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        //if(imageCodeET.getText().toString().length() != 4){
        //    Toast.makeText(this,"图形验证码不正确!", Toast.LENGTH_SHORT).show();
        //    return;
       // }

//        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
//                Profile.SERVER_ADDRESS_DEV, NetLoginInterface.class)
//                .create();
//        if(netInterface == null){
//            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Profile.SERVER_ADDRESS_DEV + "/getSms.do" + phoneET.getText().toString().trim();
                final String nResponse = HttpUtils.get(url,null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegistActivity.this, VerifyCode.getMessageByCode(nResponse), Toast.LENGTH_SHORT).show();
                        if("success:120".equals(nResponse)){
                            validateCodeView.clickView();
                        }
                    }
                });
            }
        }).start();


    }

    /**
     * 登录
     * @param userName
     * @param password
     */
    public void login(String userName, String password){

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.login(userName, Md5Util.md5(password), new NCallback<User>(this, new TypeReference<User>() {}) {

            @Override
            public void onSuccess(int statusCode, List<Header> headers, User oRet) {
                try {
                    String userStr = JsonUtil.encode(oRet);
                    //保存user附加信息， 使用json
                    writePreferences(NetLoginInterface.ANT_LOGIN_USER, "additional", userStr);

                    //进入主页
                    Intent mainIntent = new Intent(RegistActivity.this, VerifiedActivity.class);
                    mainIntent.putExtra("fromRegist", true);
                    startActivity(mainIntent);

                    Intent data = new Intent();
//                    data.putExtra("userName", userName);
//                    data.putExtra("password", password);
                    setResult(200, data);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(RegistActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    logger.warning("list_header error" + e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Toast.makeText(RegistActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public interface NeInterface {

        //判断邀请码是否存在
        @POST("/api/wechat/useropr/checkgetInviteCode")
        public void checkgetInviteCode(@Query("inviteCode") String inviteCode,
                                                NCallbackMsg callback);
    }

}
