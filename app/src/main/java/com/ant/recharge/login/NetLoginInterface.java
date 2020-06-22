package com.ant.recharge.login;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.User;
import com.ant.recharge.common.NCallbackMsg;

import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/8/11.
 */
public interface NetLoginInterface {

    public static final String ANT_LOGIN_USER = "ant_login_user";

    @POST("/login.do")
    public void login(@Query("username") String userName, @Query("password") String password, NCallback<User> callback);


    @POST("/wx/front/login/userLogin.jhtml")
    public void login(@Query("userName") String userName, NCallback<User> callback);


    //忘记密码
    @POST("/api/wechat/useropr/forgetPassword")
    public void forgetPassword(@Query("mobile") String mobile,
                               @Query("code") String code,
                               NCallback<String> callback);

    //重置密码
    @POST("/resetPassword.do")
    public void resetPassword(@Query("loginName") String telephone,
                               @Query("password") String password,
                               NCallback<String> callback);


    //是否注册过
    @POST("/api/wechat/user/validatePhoneNumber")
    public void validatePhoneNumber(@Query("phone") String phone,
                         NCallbackMsg callback);

    //注册
    @POST("/register.do")
    public void register(@Query("account") String account,//账号
                         @Query("password") String password,//密码
                         @Query("username") String username,//用户名
                         @Query("provinceCode") String provinceCode,//省代码
                         @Query("cityCode") String cityCode,//市代码
                         @Query("inviteById") String inviteById,//销售id
                         @Query("addressDetail") String addressDetail,//详细地址
                         @Query("sex") String sex,//性别
                         @Query("tel") String tel,//联系电话
                         @Query("fixTel") String fixTel,//固定电话
                         @Query("weixin") String weixin,
                         @Query("qq") String qq,
                         @Query("email") String email,
                         @Query("registerType") String registerType,
                         NCallback<User> callback);

    @POST("/api/wechat/user/register")
    public void register2(@Path("phone") String userName,
                         @Path("openId") String openId,
                         NCallback<User> callback);

    //注册协议
    public static final String ANT_REGISTER_PROTOTOL = "ant_login_user";


    //获取验证码
    @POST("/sms/sender/getTelephoneVerifyCode")
    public void getTelephoneVerifyCode(@Path("mobileNumber") String mobileNumber, NCallback<String> callback);

    @POST("/sms/sender/getAppMobileVerifyCode")
    public void getAppMobileVerifyCode(@Path("mobileNumber") String mobileNumber,
                                       @Path("yz") String yz,
                                       @Path("memberId") String userId,
                                       VerifyCodeNCallback callback);

}
