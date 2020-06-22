package com.ant.recharge.entity;

import java.io.Serializable;

/**
 * 消息
 * Created by kwc on 2016/9/8.
 */
public class MessageEntity implements Serializable{

    private String title;
    private String sendTime;
    private String content;
    private String msgId;
    private String readState;//读的状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }
}
