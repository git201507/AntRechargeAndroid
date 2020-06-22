package com.ant.recharge.fragment2.planner.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的客户
 * Created by kwc on 2016/11/29.
 */
public class PlannerCustomerEntity implements Serializable {

    private String state;//1 成功  0 失败
    private String msg; //提示信息

    private List<Customer> data = new ArrayList<>();

    private String sumIncome;

    private String sumInvest;

    private String sumSalary;


    public List<Customer> getData() {
        return data;
    }

    public void setData(List<Customer> data) {
        this.data = data;
    }

    public String getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(String sumIncome) {
        this.sumIncome = sumIncome;
    }

    public String getSumInvest() {
        return sumInvest;
    }

    public void setSumInvest(String sumInvest) {
        this.sumInvest = sumInvest;
    }

    public String getSumSalary() {
        return sumSalary;
    }

    public void setSumSalary(String sumSalary) {
        this.sumSalary = sumSalary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
