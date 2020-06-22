package com.ant.recharge.fragment2.planner.entity;

import java.io.Serializable;

/**
 * 客户
 * Created by kwc on 2016/11/29.
 */
public class Customer implements Serializable{

    private String id;
    //姓名
    private String realName;

    //创造收益
    private String totalIncome;

    //累计投资
    private String totalInvest;

    //创造工资
    private String totalSalary;

    public Customer(){}
    public Customer(String realName, String totalIncome, String totalInvest, String totalSalary) {
        this.realName = realName;
        this.totalIncome = totalIncome;
        this.totalInvest = totalInvest;
        this.totalSalary = totalSalary;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalInvest() {
        return totalInvest;
    }

    public void setTotalInvest(String totalInvest) {
        this.totalInvest = totalInvest;
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
