package com.ant.recharge.coupon;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.CouponHeader;
import com.ant.recharge.entity.CouponRecordList;
import com.ant.recharge.entity.MoneyRecordEntity;

import java.util.List;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/11.
 */
public interface NetCouponInterface {

    @POST("/api/wechat/coupon/mycouponHeaderInfo")
    public void mycouponHeaderInfo(@Query("token") String token, NCallback<CouponHeader> callback);

    @POST("/api/wechat/coupon/couponUseRecordList")
    public void couponUseRecordList(@Query("status") String status,
                                           @Query("pageNo") String pageNo,
                                           @Query("pageSize") String pageSize,
                                           @Query("userId") String userId,
                                        @Query("token") String token,
                                        NCallback<CouponRecordList> callback);
}

