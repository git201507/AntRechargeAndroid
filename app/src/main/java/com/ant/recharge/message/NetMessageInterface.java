package com.ant.recharge.message;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.entity.MessageEntity;
import com.ant.recharge.entity.MessageList;
import com.ant.recharge.common.NCallbackMsg;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 消息的网络接口
 * Created by kwc on 2016/9/6.
 */
public interface NetMessageInterface {


    //消息未读数
    @POST("/api/wechat/user/getMessageNumber")
    public void getMessageNumber(@Query("memberId") String memberId,
                                 NCallbackMsg callback);


    @POST("/wx/front/login/commonAppPrize.jhtml")
    public void commonAppPrize(@Query("memberId") String memberId,
                                 NCallbackMsg callback);


    //消息列表
    @POST("/api/wechat/msg/mymsgList")
    public void mymsgList(@Query("readStateType") String type,
                           @Query("pageNo") String pageNo,
                           @Query("pageSize") String pageSize,
                           @Query("ownerId") String ownerId, NCallback<MessageList> callback);

    //消息详情
    @POST("/api/wechat/msg/mymsgDetail")
    public void mymsgDetail(@Query("msgId") String msgId, NCallback<MessageEntity> callback);
}
