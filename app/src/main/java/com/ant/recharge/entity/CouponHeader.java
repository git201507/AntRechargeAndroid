package com.ant.recharge.entity;

/**
 * Created by kwc on 2016/9/11.
 */
public class CouponHeader {

    private CouponDateEntity createDate;
    private int hasBeenUsed; //5
    private String uid;
    private String memberId; //402881e551cda3fc0151cdae1cf40002;
    private CouponDateEntity modifyDate;
    private int outOfDate;
    private int total;
    private int unused;

    public CouponDateEntity getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CouponDateEntity createDate) {
        this.createDate = createDate;
    }

    public int getHasBeenUsed() {
        return hasBeenUsed;
    }

    public void setHasBeenUsed(int hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public int getOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(int outOfDate) {
        this.outOfDate = outOfDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUnused() {
        return unused;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }
}
