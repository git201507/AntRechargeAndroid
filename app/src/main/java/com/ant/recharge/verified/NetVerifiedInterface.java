package com.ant.recharge.verified;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.User;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by User on 2016/8/30.
 */
public interface NetVerifiedInterface {

    @POST("/cp/member/appRenZheng")
    public void verified(@Query("") String params, NCallback<String> callback);
}
