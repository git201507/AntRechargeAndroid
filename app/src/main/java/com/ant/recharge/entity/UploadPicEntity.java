package com.ant.recharge.entity;



/**
 * Created by wangxiayang on 2017/10/23.
 */

public class UploadPicEntity {
    private String state;

    private String msg;

    private PicEntity data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PicEntity getData() {
        return data;
    }

    public void setData(PicEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
