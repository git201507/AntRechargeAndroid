package com.ant.recharge.fragment2.planner.entity;

/**
 * Created by kwc on 2016/11/30.
 */
public class SalaryEntity {

//    private String memberId;
//    private String name;//"恭喜您在前三名！"
//    private String rank;//0 1
//    private String totalInvest;//
//    private String totalSalary;//0
//    private String type;//"1" "0"


    private String bidId;// = 8a232d725875d76e0158761c5a7d0018;
    private String createDate;// = "2016-11-18";
    private String draftId;// = 8a232d725875d76e015876108088000a;
    private String draftName;// = "\U6d4b\U8bd5VIP1118";
    private String memberId;// = 8a232d7258163c550158189c88280020;
    private String memberName;// = "\U9b4f\U5353";
    private String salary;// = "493.15";

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftName() {
        return draftName;
    }

    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
