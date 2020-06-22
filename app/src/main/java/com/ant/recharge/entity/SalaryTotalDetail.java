package com.ant.recharge.entity;

/**
 * Created by kwc on 2016/9/18.
 * 工资记录
 */
public class SalaryTotalDetail {

    private CouponDateEntity createDate;
    private String createDateStr; //5
    private String id;
    private CouponDateEntity modifyDate;
    private String name;
    private String operate;
    private String profit;
    private String realName;

    public CouponDateEntity getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CouponDateEntity createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CouponDateEntity getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(CouponDateEntity modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
