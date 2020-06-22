package com.ant.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kwc on 2016/9/8.
 */
public class FinanceProductEntity implements Serializable{

    private String id;//4028810e5499abac015499d89cd3002e
    private BigDecimal amount;
    private String appVersion;
    private String ceaseDate;
    private String draftId;
    private String draftState;//"release"，"finsh", "financing"
    private String draftStateStr;
    private BigDecimal expectIncome;//263.01
    private BigDecimal financeDays;//6  期限
    private BigDecimal financing;//100000   总额
    private String flag;//"1"
    private String imgPath;
    private String ip;
    private String ipsAccount;//"800000014882"
    private String latestRepaymentDate;//"2016-05-18"
    private String limitTime;//"6"
    private String mark;
    private String maxAmount; //1000;
    private String minAmount;//1 起投金额
    private String name;//"新手标"
    private String openId;
    private String phoneID;
    private String phoneType;
    private BigDecimal profit;//15   预期年化收益率
    private String progress;//10
    private String releaseDate;
    private String riskLevel;
    private String systemType;
    private String systemVersion;
    private BigDecimal tempDays;//0
    private String token;
    private String valueDate;//"2016-05-11"
    private String versionCode;
    private BigDecimal zsy;//0

    private Boolean isVip = false;//bip标
    private Boolean isNews = false;//新手标

    private String rzCoupon;//"1"有奖

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCeaseDate() {
        return ceaseDate;
    }

    public void setCeaseDate(String ceaseDate) {
        this.ceaseDate = ceaseDate;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftState() {
        return draftState;
    }

    public void setDraftState(String draftState) {
        this.draftState = draftState;
    }

    public String getDraftStateStr() {
        return draftStateStr;
    }

    public void setDraftStateStr(String draftStateStr) {
        this.draftStateStr = draftStateStr;
    }

    public BigDecimal getExpectIncome() {
        return expectIncome;
    }

    public void setExpectIncome(BigDecimal expectIncome) {
        this.expectIncome = expectIncome;
    }

    public BigDecimal getFinanceDays() {
        return financeDays;
    }

    public void setFinanceDays(BigDecimal financeDays) {
        this.financeDays = financeDays;
    }

    public BigDecimal getFinancing() {
        return financing;
    }

    public void setFinancing(BigDecimal financing) {
        this.financing = financing;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpsAccount() {
        return ipsAccount;
    }

    public void setIpsAccount(String ipsAccount) {
        this.ipsAccount = ipsAccount;
    }

    public String getLatestRepaymentDate() {
        return latestRepaymentDate;
    }

    public void setLatestRepaymentDate(String latestRepaymentDate) {
        this.latestRepaymentDate = latestRepaymentDate;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public BigDecimal getTempDays() {
        return tempDays;
    }

    public void setTempDays(BigDecimal tempDays) {
        this.tempDays = tempDays;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public BigDecimal getZsy() {
        return zsy;
    }

    public void setZsy(BigDecimal zsy) {
        this.zsy = zsy;
    }


    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public Boolean getNews() {
        return isNews;
    }

    public void setNews(Boolean news) {
        isNews = news;
    }

    public String getRzCoupon() {
        return rzCoupon;
    }

    public void setRzCoupon(String rzCoupon) {
        this.rzCoupon = rzCoupon;
    }
}
