package com.ant.recharge.salary;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.CouponRecordList;
import com.ant.recharge.entity.SalaryHeader;
import com.ant.recharge.entity.SalaryTotalDetailList;
import com.ant.recharge.entity.SalaryWithdrawList;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/12.
 */
public interface NetSalaryInterface {

    //头部信息
    @POST("/api/wechat/salary/mysalaryHeaderInfo")
    public void mysalaryHeaderInfo(@Query("token") String token, NCallback<SalaryHeader> callback);

    //我的工资列表
    @POST("/api/wechat/salary/mysalaryList")
    public void mysalaryList(@Query("pageNo") String _pageNo,
                                           @Query("pageSize") String count,
                                           @Query("token") String token, NCallback<SalaryTotalDetailList> callback);

    //已体现列表
    @POST("/api/wechat/salary/withdrawList")
    public void withdrawList(
                             @Query("pageNo") String _pageNo,
                             @Query("pageSize") String count,
                             @Query("token") String token, NCallback<SalaryWithdrawList> callback);

    //兑换优惠券列表
    @POST("/api/wechat/coupon/couponUseRecordList")
    public void couponRecordList(@Query("userid") String userid,
                                 @Query("status") String status,//“1”
                             @Query("pageNo") String _pageNo,
                             @Query("pageSize") String count,
                             @Query("token") String token, NCallback<CouponRecordList> callback);
}
