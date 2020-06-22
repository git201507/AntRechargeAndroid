package com.ant.recharge.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class  OrderList {
	private List<OrderEntity> result;

	public List<OrderEntity> getResult() {
		return result;
	}

	public void setResult(List<OrderEntity> result) {
		this.result = result;
	}
}
