package com.ant.recharge.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class  OrderEntity implements Serializable{

	private static final long serialVersionUID = 2087438178966405250L;

	private String id;

	private String orderTitle;
	private String orderDescription;

	private String telephoneId;

	private String orderNo;

	private BigDecimal orderPrice;

	private String isDeliver;

	private String attachment_id;

	private String attachment_orderId;


	private String attachment_pic1;

	private String attachment_pic2;

	private String attachment_pic3;

	private String createDate;

	private String createBy;

	private Date updateDate;

	private String updateBy;

	private String orderStatus;
	private String orderStatusDetails;

	private String deliver_id;
	private String deliver_orderId;
	private String deliver_receiverName;
	private String deliver_receiverTel;
	private String deliver_receiverAddress;

	private String area;
	private String company;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public String getTelephoneId() {
		return telephoneId;
	}

	public void setTelephoneId(String telephoneId) {
		this.telephoneId = telephoneId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getIsDeliver() {
		return isDeliver;
	}

	public void setIsDeliver(String isDeliver) {
		this.isDeliver = isDeliver;
	}

	public String getAttachment_id() {
		return attachment_id;
	}

	public void setAttachment_id(String attachment_id) {
		this.attachment_id = attachment_id;
	}

	public String getAttachment_orderId() {
		return attachment_orderId;
	}

	public void setAttachment_orderId(String attachment_orderId) {
		this.attachment_orderId = attachment_orderId;
	}

	public String getAttachment_pic1() {
		return attachment_pic1;
	}

	public void setAttachment_pic1(String attachment_pic1) {
		this.attachment_pic1 = attachment_pic1;
	}

	public String getAttachment_pic2() {
		return attachment_pic2;
	}

	public void setAttachment_pic2(String attachment_pic2) {
		this.attachment_pic2 = attachment_pic2;
	}

	public String getAttachment_pic3() {
		return attachment_pic3;
	}

	public void setAttachment_pic3(String attachment_pic3) {
		this.attachment_pic3 = attachment_pic3;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusDetails() {
		return orderStatusDetails;
	}

	public void setOrderStatusDetails(String orderStatusDetails) {
		this.orderStatusDetails = orderStatusDetails;
	}

	public String getDeliver_id() {
		return deliver_id;
	}

	public void setDeliver_id(String deliver_id) {
		this.deliver_id = deliver_id;
	}

	public String getDeliver_orderId() {
		return deliver_orderId;
	}

	public void setDeliver_orderId(String deliver_orderId) {
		this.deliver_orderId = deliver_orderId;
	}

	public String getDeliver_receiverName() {
		return deliver_receiverName;
	}

	public void setDeliver_receiverName(String deliver_receiverName) {
		this.deliver_receiverName = deliver_receiverName;
	}

	public String getDeliver_receiverTel() {
		return deliver_receiverTel;
	}

	public void setDeliver_receiverTel(String deliver_receiverTel) {
		this.deliver_receiverTel = deliver_receiverTel;
	}

	public String getDeliver_receiverAddress() {
		return deliver_receiverAddress;
	}

	public void setDeliver_receiverAddress(String deliver_receiverAddress) {
		this.deliver_receiverAddress = deliver_receiverAddress;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
