package com.ant.recharge.fragment1.planner.entity;

import java.io.Serializable;

/**
 * Created by kwc on 2016/11/30.
 */
public class PlannerEntity implements Serializable {

    private String id;
    private String adviserIntroduction;
    private String adviserImg;// = "/att/head/iO5vDh5bfIPOEESLdc2af8tE8FePEREiwq-699mf1143ILAuIIon0b40OOswwwc0.jpg";
    private String loginName;// = "151****111";
    private String realName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdviserIntroduction() {
        return adviserIntroduction;
    }

    public void setAdviserIntroduction(String adviserIntroduction) {
        this.adviserIntroduction = adviserIntroduction;
    }

    public String getAdviserImg() {
        return adviserImg;
    }

    public void setAdviserImg(String adviserImg) {
        this.adviserImg = adviserImg;
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
