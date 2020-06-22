package com.ant.recharge.entity;

import java.math.BigDecimal;

/**
 * 资金记录
 *
 * Created by kwc on 2016/9/7.
 */
public class MoneyRecordHeader {

    private CouponDateEntity abnormalDate;
    private String accountId;// = 402881e551cda3fc0151cdae1cf40002;
    private float availableAmount;// = "912355080.6900001";
    private float cAvaliable;// = "912355080.6900001";
    private float cFinancing;// = 0;
    private float cFreeze;// = "2298446.88";
    private float cTotal;// = "914653527.5700001";
    private CouponDateEntity createDate;
    private float eAvaliable;// = "912355080.6900001";
    private float eFinancing;// = 0;
    private float eFreeze;// = "2298446.88";
    private float eTotal;// = "914653527.5700001";
    private float financingAmount;// = 0;
    private float freezeAmount;// = "2298546.82";
    private String id;// = 402881e551cda3fc0151cdae1cf40003;
    private String ipsAccount;// = 800000013519;
    private CouponDateEntity modifyDate;
    private String name;// = "";
    private int reason;// = 0;
    private float totalAmount;// = "914653627.51";
    private BigDecimal totalDraw;// 提现金额
    private float totalExpensesAmount;// = "20048.43";
    private BigDecimal totalIncomeAmount;// 理财收入
    private float totalRecharge;// = 300;
    private BigDecimal totalTradeAmount;// 充值

    public CouponDateEntity getAbnormalDate() {
        return abnormalDate;
    }

    public void setAbnormalDate(CouponDateEntity abnormalDate) {
        this.abnormalDate = abnormalDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public float getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(float availableAmount) {
        this.availableAmount = availableAmount;
    }

    public float getcAvaliable() {
        return cAvaliable;
    }

    public void setcAvaliable(float cAvaliable) {
        this.cAvaliable = cAvaliable;
    }

    public float getcFinancing() {
        return cFinancing;
    }

    public void setcFinancing(float cFinancing) {
        this.cFinancing = cFinancing;
    }

    public float getcFreeze() {
        return cFreeze;
    }

    public void setcFreeze(float cFreeze) {
        this.cFreeze = cFreeze;
    }

    public float getcTotal() {
        return cTotal;
    }

    public void setcTotal(float cTotal) {
        this.cTotal = cTotal;
    }

    public CouponDateEntity getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CouponDateEntity createDate) {
        this.createDate = createDate;
    }

    public float geteAvaliable() {
        return eAvaliable;
    }

    public void seteAvaliable(float eAvaliable) {
        this.eAvaliable = eAvaliable;
    }

    public float geteFinancing() {
        return eFinancing;
    }

    public void seteFinancing(float eFinancing) {
        this.eFinancing = eFinancing;
    }

    public float geteFreeze() {
        return eFreeze;
    }

    public void seteFreeze(float eFreeze) {
        this.eFreeze = eFreeze;
    }

    public float geteTotal() {
        return eTotal;
    }

    public void seteTotal(float eTotal) {
        this.eTotal = eTotal;
    }

    public float getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(float financingAmount) {
        this.financingAmount = financingAmount;
    }

    public float getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(float freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpsAccount() {
        return ipsAccount;
    }

    public void setIpsAccount(String ipsAccount) {
        this.ipsAccount = ipsAccount;
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

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalDraw() {
        return totalDraw;
    }

    public void setTotalDraw(BigDecimal totalDraw) {
        this.totalDraw = totalDraw;
    }

    public float getTotalExpensesAmount() {
        return totalExpensesAmount;
    }

    public void setTotalExpensesAmount(float totalExpensesAmount) {
        this.totalExpensesAmount = totalExpensesAmount;
    }

    public BigDecimal getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(BigDecimal totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public float getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(float totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public BigDecimal getTotalTradeAmount() {
        return totalTradeAmount;
    }

    public void setTotalTradeAmount(BigDecimal totalTradeAmount) {
        this.totalTradeAmount = totalTradeAmount;
    }
}
