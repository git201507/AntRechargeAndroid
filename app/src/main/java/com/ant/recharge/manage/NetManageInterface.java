package com.ant.recharge.manage;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.FinanceHistoryHeader;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.MoneyRecordHeader;

import java.util.List;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 *
 * Created by kwc on 2016/9/6.
 */
public interface NetManageInterface {


    @POST("/api/wechat/finance/statPersonalFianceMoney")
    public void statPersonalFianceMoney(@Query("token") String token, NCallback<FinanceHistoryHeader> callback);

    @POST("/api/wechat/finance/fianceList")
    public void fianceList(@Query("type") String type,
                           @Query("start") Integer _pageNo,
                           @Query("count") Integer count,
                           @Query("token") String token, NCallback<List<FinanceProductEntity>> callback);

}
