package com.ant.recharge.entity;

/**
 * Created by kwc on 2016/9/18.
 */
public class SalaryHeader {

    private float cashFrozen;//冻结资金
    private float cashed;//提现
    private CouponDateEntity createDate;
    private String id;// = "9e377362-f4cd-11e5-99eb-00163e00";
    private String memberId;// = 402881e551cda3fc0151cdae1cf40002;
    private CouponDateEntity modifyDate;
    private float surplus;// = "444.45";//余额
    private float total;// = "1444.45";//总
    private float withdraw;// = 0;//兑换优惠

    public float getCashFrozen() {
        return cashFrozen;
    }

    public void setCashFrozen(float cashFrozen) {
        this.cashFrozen = cashFrozen;
    }

    public float getCashed() {
        return cashed;
    }

    public void setCashed(float cashed) {
        this.cashed = cashed;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public CouponDateEntity getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(CouponDateEntity modifyDate) {
        this.modifyDate = modifyDate;
    }

    public float getSurplus() {
        return surplus;
    }

    public void setSurplus(float surplus) {
        this.surplus = surplus;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(float withdraw) {
        this.withdraw = withdraw;
    }
}
