package com.ant.recharge.common;

import java.io.Serializable;

/**
 * Created by kwc on 2016/8/11.
 */
public class NetModel<T> implements Serializable {

    private String state;//1 成功  0 失败
    private String msg; //提示信息
    private T data;

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
