package com.ant.recharge.login;

import com.ant.recharge.common.NCallbackMsg;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/21.
 */
public interface NetCodeInterface {

    //验证码是否正确
    @POST("/checkSms.do")
    public void validateUserPhoneVerifyCode(@Query("code") String code,
                      @Query("telephone") String telephone,
                                            NCallbackMsg callback);


}
