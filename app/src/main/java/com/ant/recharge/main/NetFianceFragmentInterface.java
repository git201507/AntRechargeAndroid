package com.ant.recharge.main;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.FinancePersonEntity;
import com.ant.recharge.entity.FinanceProductDetail;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.OrderList;
import com.ant.recharge.entity.TelephoneEntity;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by kwc on 2016/9/9.
 */
public interface NetFianceFragmentInterface {
    //电话号码列表
    @POST("/telQuery.do")
    public void telNumberList(
            @Query("current") String _pageNo,//当前页数
            @Query("telephoneNo") String telephoneNo,//电话号码
            @Query("network") String network,//网络代码
            @Query("company") String company,//运营商代码
            @Query("tariff") String tariff,//资费
            @Query("form") String form,//属性代码
            @Query("provinceCode") String provinceCode,//省代码
            @Query("city") String city,//市代码
            @Query("isSale") String isSale,//是否上架
            @Query("isDiscount") String isDiscount,//是否特价
            @Query("isRecommend") String isRecommend,//是否推荐
            @Query("isPretty") String isPretty,//是否靓号
            @Query("isLock") String isLock,//是否锁定
            @Query("isOver") String isOver,//是否结束
            @Query("isWrite") String isWrite,//是否写卡
            NCallback<List<TelephoneEntity>> callback);

    //订单列表
    @POST("/getOrders.do")
    public void orderList(
            @Query("current") String _pageNo,//当前页数
            @Query("token") String token,//token
//            @Query("network") String network,//网络代码
//            @Query("company") String company,//运营商代码
//            @Query("tariff") String tariff,//资费
//            @Query("form") String form,//属性代码
//            @Query("provinceCode") String provinceCode,//省代码
//            @Query("city") String city,//市代码
//            @Query("isSale") String isSale,//是否上架
//            @Query("isDiscount") String isDiscount,//是否特价
//            @Query("isRecommend") String isRecommend,//是否推荐
//            @Query("isPretty") String isPretty,//是否靓号
//            @Query("isLock") String isLock,//是否锁定
//            @Query("isOver") String isOver,//是否结束
//            @Query("isWrite") String isWrite,//是否写卡
            NCallback<OrderList> callback);

    //收藏列表
    @POST("/getCollect.do")
    public void favorList(
            @Query("current") String _pageNo,//当前页数
            @Query("token") String token,//token
            NCallback<List<TelephoneEntity>> callback);

    //新手标
    @POST("/api/wechat/finance/fianceNewProductList")
    public void fianceNewProductList(
                                     @Query("start") Integer _pageNo,
                                     @Query("count") Integer count,
                                     @Query("token") String token,
                                     NCallback<List<FinanceProductEntity>> callback);

    //获取普通标列表
    @POST("/api/wechat/finance/fianceProductList")
    public void fianceProductList(
                                  @Query("start") Integer _pageNo,
                                  @Query("count") Integer count,
                                  @Query("token") String token,
                                  NCallback<List<FinanceProductEntity>> callback);

    //获取VIP标
    @POST("/api/wechat/finance/fianceVipProductList")
    public void fianceVipProductList(
                                     @Query("start") Integer _pageNo,
                                     @Query("count") Integer count,
                                     @Query("token") String token,
                                     NCallback<List<FinanceProductEntity>> callback);

    //标的详情
    @GET("/api/wechat/finance/appFinanceDetail")
    public void appFinanceDetail(@Query("draftId") String draftId,
                                 @Query("memberId") String memberId,
                                 NCallback<FinanceProductDetail> callback);

    //购买记录
    @POST("/api/wechat/finance/findBidderList")
    public void findBidderList(@Query("start") Integer _pageNo,
                               @Query("count") Integer count,
                               @Query("draftId") String draftId,
                               NCallback<List<FinancePersonEntity>> callback);

    //申请记录
    @POST("/api/wechat/draft/findApplyList")
    public void findApplyList(@Query("start") Integer _pageNo,
                               @Query("count") Integer count,
                               @Query("draftId") String draftId,
                               NCallback<List<FinancePersonEntity>> callback);

    //VIP标申请
    @POST("/api/wechat/draft/appVerifyVipCode")
    public void appVerifyVipCode(
            @Query("draftId") String draftId,
            @Query("memberId") String memberId,
            @Query("adviserId") String adviserId,//""
            @Query("adviserName") String adviserName,//""
            @Query("choice") String choice,//2
            @Query("isTicket") String isTicket,
            NCallback<String> callback);


    @POST("/api/wechat/draft/getDraftNumber")
    public void getDraftNumber(NCallbackMsg callback);

}
