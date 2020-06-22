package com.ant.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kwc on 2016/9/14.
 */
public class FinanceProductDetail implements Serializable{

    private String acceptance;
    private String appVersion;
    private String ceaseDate;
    private String couponFive;
    private String couponFour;
    private String couponOne;
    private String couponThree;
    private String couponTwo;
    private String draftId;
    private String draftType;
    private String faceValue;
    private long financeDays;
    private long financing;
    private String imgPath;
    private String ip;
    private String isNew;
    private String latestRepaymentDate;
    private long maxAmount;
    private long minAmount;
    private String name;
    private String openId;
    private String phoneID;
    private String phoneType;
    private BigDecimal profit;
    private BigDecimal progress;
    private long surplus;
    private String systemType;
    private String systemVersion;
    private int tempDays;//0
    private String token;
    private String valueDate;
    private String versionCode;

    private String count;//"1"申请过vip标， 0 未申请过vip标

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
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

    public String getCouponFive() {
        return couponFive;
    }

    public void setCouponFive(String couponFive) {
        this.couponFive = couponFive;
    }

    public String getCouponFour() {
        return couponFour;
    }

    public void setCouponFour(String couponFour) {
        this.couponFour = couponFour;
    }

    public String getCouponOne() {
        return couponOne;
    }

    public void setCouponOne(String couponOne) {
        this.couponOne = couponOne;
    }

    public String getCouponThree() {
        return couponThree;
    }

    public void setCouponThree(String couponThree) {
        this.couponThree = couponThree;
    }

    public String getCouponTwo() {
        return couponTwo;
    }

    public void setCouponTwo(String couponTwo) {
        this.couponTwo = couponTwo;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftType() {
        return draftType;
    }

    public void setDraftType(String draftType) {
        this.draftType = draftType;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public long getFinanceDays() {
        return financeDays;
    }

    public void setFinanceDays(long financeDays) {
        this.financeDays = financeDays;
    }

    public long getFinancing() {
        return financing;
    }

    public void setFinancing(long financing) {
        this.financing = financing;
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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getLatestRepaymentDate() {
        return latestRepaymentDate;
    }

    public void setLatestRepaymentDate(String latestRepaymentDate) {
        this.latestRepaymentDate = latestRepaymentDate;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(long minAmount) {
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

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }

    public long getSurplus() {
        return surplus;
    }

    public void setSurplus(long surplus) {
        this.surplus = surplus;
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

    public int getTempDays() {
        return tempDays;
    }

    public void setTempDays(int tempDays) {
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
}
