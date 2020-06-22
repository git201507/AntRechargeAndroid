package com.ant.recharge.entity;

import java.math.BigDecimal;

/**
 * 账户信息
 * Created by kwc on 2016/9/1.
 */
public class MyAccountDetail {

    public static final String financial_2 = "2";//理财师

    private BigDecimal availableAmount;//可用余额  3584626.08
    private BigDecimal couponAmount;//5
    private BigDecimal couponNumber;//优惠券个数 1
    private String financial; // "2"：理财师
    private BigDecimal financingAmount;//理财中 1008100
    private BigDecimal freezeAmount;//募集中 209719.65
    private String invitorLevel;//邀请人等级 level_1
    private String ipsAccount;//中金， 是不是已经认证过的用户  长度〉0
    private String isRealNameVerify;//是否已签约 yq
    private String memberLevel;// level0 putong  1  bai  2 hong  3  黑
    private String name;//姓名
    private Integer news;//0
    private BigDecimal salaryAmount;//蚂蚁工资个数1301.73
    private BigDecimal totalAmount;//总资产 4802445.73
    private BigDecimal totalIncomeAmount;//总收益 938.41
    private Integer vip;//1

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(BigDecimal financingAmount) {
        this.financingAmount = financingAmount;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getIpsAccount() {
        return ipsAccount;
    }

    public void setIpsAccount(String ipsAccount) {
        this.ipsAccount = ipsAccount;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(BigDecimal totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public String getIsRealNameVerify() {
        return isRealNameVerify;
    }

    public void setIsRealNameVerify(String isRealNameVerify) {
        this.isRealNameVerify = isRealNameVerify;
    }

    public Integer getNews() {
        return news;
    }

    public void setNews(Integer news) {
        this.news = news;
    }

    public BigDecimal getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(BigDecimal couponNumber) {
        this.couponNumber = couponNumber;
    }

    public String getInvitorLevel() {
        return invitorLevel;
    }

    public void setInvitorLevel(String invitorLevel) {
        this.invitorLevel = invitorLevel;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }
}
