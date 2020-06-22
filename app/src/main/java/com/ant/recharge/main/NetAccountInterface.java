package com.ant.recharge.main;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.entity.MyAccountDetail;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/1.
 */
public interface NetAccountInterface {

    public static final String ANT_ACCOUNT = "ant_account";

    //获取账户信息
    @POST("/api/wechat/account/findAccountInfo")
    public void findAccountInfo(@Query("token") String token, NCallback<MyAccountDetail> callback);

    //如果有优选标抽奖次数，调用接口
    @POST("/api/member/appGetPrizeId")
    public void appGetPrizeId(@Query("memberId") String memberId, NCallbackMsg callback);


}
