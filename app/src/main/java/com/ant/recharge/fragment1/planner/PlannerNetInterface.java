package com.ant.recharge.fragment1.planner;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.fragment1.planner.entity.PlannerEntity;
import com.ant.recharge.fragment2.lottery.entity.ShareEntity;

import java.util.List;

import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * 买标需要的理财师接口
 * Created by kwc on 2016/11/29.
 */
public interface PlannerNetInterface {

    //获取理财师列表
    @POST("/api/member/pageAdvanserList")
    public void pageAdvanserList(@Query("memberId") String memberId,
                                 @Query("start") int start,
                                 @Query("count") String pageSize,
                                 @Query("name") String name,
                            NCallback<List<PlannerEntity>> callback);

    //获取理财师列表
    @POST("/api/member/pageAdvanserList")
    public void pageAdvanserList2(@Query("memberId") String memberId,
                                 @Query("start") int start,
                                 @Query("count") String pageSize,
                                 NCallback<List<PlannerEntity>> callback);

    //设定某人的理财师
    //state  1成功  0失败
    @POST("/api/wechat/finance/setMyFinancial")
    public void setMyFinancial(@Query("memberId") String memberId,
                               @Query("financialId") String financialId,
                             NCallbackMsg callback);

    //申请购买vip标时判断当前用户是否有理财师
    //msg: 1有理财师  0无理财师
    @POST("/api/wechat/finance/getMyAdviser")
    public void getMyAdviser(@Query("memberId") String memberId,
                                 NCallbackMsg callback);

}
