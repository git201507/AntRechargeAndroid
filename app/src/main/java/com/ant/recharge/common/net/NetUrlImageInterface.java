package com.ant.recharge.common.net;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.MyAccountDetail;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by kwc on 2016/9/9.
 */
public interface NetUrlImageInterface {

    //获取普通标列表
    @GET("/common/img/vipCode.jpeg")
    public void fianceProductList(@Path("d") String d, @Path("memberId") String memberId, NCallback<MyAccountDetail> callback);

}
