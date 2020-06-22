package com.ant.recharge.entity;

import java.util.List;

/**
 * Created by kwc on 2016/9/11.
 */
public class CouponRecordList {

    private Boolean autoCount;
    private int first;
    private int pageNo;
    private int pageSize;
    private List<CouponEntity> result;
    private int totalCount;
    private int totalPages;

    public Boolean getAutoCount() {
        return autoCount;
    }

    public void setAutoCount(Boolean autoCount) {
        this.autoCount = autoCount;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<CouponEntity> getResult() {
        return result;
    }

    public void setResult(List<CouponEntity> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
