package com.ant.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kwc on 2016/9/8.
 */
public class MoneyRecordEntity implements Serializable{

    private String billNo;// = 22rCDDYiJeV8zzUVvm9LxD;
    private String createDate;// = "<null>";
    private String createDateStr;// = "2016-03-18 13:58";
    private String uid;// = 402881e953884c5b0153884e06020004;
    private String modifyDate;// = "<null>";
    private String name;// = "";
    private String referID;// = 800000013519;
    private BigDecimal tradeAmount;// = 100;

    private String currentType;//

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferID() {
        return referID;
    }

    public void setReferID(String referID) {
        this.referID = referID;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
}
