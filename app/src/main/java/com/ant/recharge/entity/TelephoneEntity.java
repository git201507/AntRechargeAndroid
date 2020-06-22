package com.ant.recharge.entity;

import java.math.BigDecimal;
import java.io.Serializable;


public class  TelephoneEntity implements Serializable{

	private static final long serialVersionUID = 2087438178966405250L;

//			"createBy":{
//		"id":"1",
//				"isNewRecord":false,
//				"loginFlag":"1",
//				"admin":true,
//				"roleNames":""},

	private String id;
	private String isNewRecord;
	private String createDate;
	private String updateDate;
	private String telephoneNo;
	private String network;
	private String networkName;
	private BigDecimal priceOriginal;
	private BigDecimal priceAgent;
	private String company;
	private String companyName;
	private String tariff;
	private String form;
	private String province;
	private String provinceCode;
	private String city;
	private String cityCode;
	private String isSale;
	private String isDiscount;
	private String isRecommend;
	private String isPretty;
	private String isLock;
	private String isOver;
	private String isWrite;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsNewRecord() {
		return isNewRecord;
	}
	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public BigDecimal getPriceOriginal() {
		return priceOriginal;
	}
	public void setPriceOriginal(BigDecimal priceOriginal) {
		this.priceOriginal = priceOriginal;
	}
	public BigDecimal getPriceAgent() {
		return priceAgent;
	}
	public void setPriceAgent(BigDecimal priceAgent) {
		this.priceAgent = priceAgent;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTariff() {
		return tariff;
	}
	public void setTariff(String tariff) {
		this.tariff = tariff;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getIsSale() {
		return isSale;
	}
	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getIsPretty() {
		return isPretty;
	}
	public void setIsPretty(String isPretty) {
		this.isPretty = isPretty;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getIsOver() {
		return isOver;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	public String getIsWrite() {
		return isWrite;
	}
	public void setIsWrite(String isWrite) {
		this.isWrite = isWrite;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
}
