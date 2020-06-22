package com.ant.recharge.fragment2.planner.entity;

/**
 * 业绩排名
 * Created by kwc on 2016/11/29.
 */
public class RankEntity {

    private String memberId;

    private String name;

    private int rank;

    private String totalInvest;

    private String totalSalary;

    private int type;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
