package com.ant.recharge.financial;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.MoneyRecordEntity;
import com.ant.recharge.entity.MoneyRecordHeader;
import com.ant.recharge.entity.User;

import java.util.List;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/6.
 */
public interface NetFinancialInterface {


    @POST("/api/wechat/finance/statPersonalMoney")
    public void statPersonalMoney(@Query("token") String token, NCallback<MoneyRecordHeader> callback);

    @POST("/api/wechat/finance/findPersonalMoneyTradeList")
    public void findPersonalMoneyTradeList(@Query("type") String type,
                                           @Query("start") Integer _pageNo,
                                           @Query("count") Integer count,
                                           @Query("token") String token, NCallback<List<MoneyRecordEntity>> callback);

//    @POST("/api/wechat/finance/statPersonalFianceMoney")
//    public void statPersonalFianceMoney(@Query("token") String token, NCallback<MoneyRecordHeader> callback);
//
//    @POST("/api/wechat/finance/fianceList")
//    public void fianceList(@Query("token") String token, NCallback<MoneyRecordHeader> callback);

}
