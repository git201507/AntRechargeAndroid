package com.ant.recharge.recharge;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.MyAccountDetail;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/10.
 */
public interface NetRechargeInterface {

    //充值
    @POST("/cp/member/appChongZhi")
    public void appChongZhi(@Query("paymentAccountNumber") String _ipsAccount,
                            @Query("amount") String _amountFillin,
                            NCallback<MyAccountDetail> callback);

}
