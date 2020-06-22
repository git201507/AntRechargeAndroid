package com.ant.recharge.entity;

/**
 * Created by kwc on 2016/9/18.
 */
public class SalaryWithdraw {
    private float amount;
    private String applicantID;// = 402881e551cda3fc0151cdae1cf40002;
    private String bankCode;// = 1234567;
    private String bankName;// = "\U00e4\U00b8\U00ad\U00e5\U009b\U00bd\U00e5\U0086\U009c\U00e4\U00b8\U009a\U00e9\U0093\U00b6\U00e8\U00a1\U008c";
    private CouponDateEntity createDate;
    private String id;
    private String mobile;
    private CouponDateEntity modifyDate;
    private String realName;
    private int status;// = 0;
    private String withDrawDate;// = "";

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getTelephone() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public CouponDateEntity getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(CouponDateEntity modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWithDrawDate() {
        return withDrawDate;
    }

    public void setWithDrawDate(String withDrawDate) {
        this.withDrawDate = withDrawDate;
    }
}
