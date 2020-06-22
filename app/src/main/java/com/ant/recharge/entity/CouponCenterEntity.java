package com.ant.recharge.entity;

/**
 * Created by kwc on 2016/9/20.
 */
public class CouponCenterEntity {

   private int amount;// = 5;
    private String couponName;// = "\U6ee125\U5143\U53ef\U7528";
    private int couponType;// = 1;
    private CouponDateEntity createDate;// =                 {
    private String id;// = 8ab3ce1e52a76d400152f2fed1fc18c2;
    private String image;// = "";
    private Boolean isDelete;
    private CouponDateEntity modifyDate;// = "<null>";
    private String remark;// = "";
    private int status;// = 2;
    private int totalAmount;// = 0;
    private int totalCount;// = 0;
    //@property (nonatomic, strong) NSArray *useInfoList;
    private int usingLimitAmount;// = 25;
    private int validityDays;// = 30;

    private Boolean isSelected = false;//是否选中

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public CouponDateEntity getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CouponDateEntity createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public CouponDateEntity getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(CouponDateEntity modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUsingLimitAmount() {
        return usingLimitAmount;
    }

    public void setUsingLimitAmount(int usingLimitAmount) {
        this.usingLimitAmount = usingLimitAmount;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }
}
