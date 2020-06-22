package com.ant.recharge.fragment2.planner.entity;

import java.io.Serializable;

/**
 * 理财师简介
 * Created by kwc on 2016/11/29.
 */
public class FinancePlannerEntity implements Serializable{

    private String adviserImg;

    private String adviserIntroduction;

    private String id;

    private String loginName;

    private String realName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdviserImg() {
        return adviserImg;
    }

    public void setAdviserImg(String adviserImg) {
        this.adviserImg = adviserImg;
    }

    public String getAdviserIntroduction() {
        return adviserIntroduction;
    }

    public void setAdviserIntroduction(String adviserIntroduction) {
        this.adviserIntroduction = adviserIntroduction;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
