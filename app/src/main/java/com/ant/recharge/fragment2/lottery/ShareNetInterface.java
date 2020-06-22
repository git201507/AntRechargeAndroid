package com.ant.recharge.fragment2.lottery;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.fragment2.lottery.entity.ShareEntity;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 分享接口
 * Created by kwc on 2016/11/29.
 */
public interface ShareNetInterface {

    //分享后，后台接口调用，记录是否分享成功！
    //msg: ok分享成功  分享失败   服务器响应失败
    @POST("api/wechat/coupon/shareCoupon")
    public void shareCoupon(@Query("prizeId") String prizeId,
                            NCallback<ShareEntity> callback);


    @POST("api/wechat/coupon/getSharedCoupon")
    //msg: no红包数不足！ 分享失败！ 服务器响应失败
    public void getSharedCoupon(@Query("prizeId") String prizeId,
                            NCallback<ShareEntity> callback);

}
