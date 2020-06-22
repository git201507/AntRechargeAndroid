package com.ant.recharge.entity;

import java.util.List;

/**
 * Created by kwc on 2016/9/18.
 */
public class SalaryWithdrawList {

    private List<SalaryWithdraw> result;
    private Boolean autoCount;
    private int first;
    private int pageNo;
    private int pageSize;
    private int totalCount;
    private int totalPages;

    public List<SalaryWithdraw> getResult() {
        return result;
    }

    public void setResult(List<SalaryWithdraw> result) {
        this.result = result;
    }

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
