package com.ant.recharge.entity;

import java.io.Serializable;
import java.util.Date;

public class  ResponseEntity implements Serializable {

	private String state;
	
	private String msg;
	
	private String data;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
