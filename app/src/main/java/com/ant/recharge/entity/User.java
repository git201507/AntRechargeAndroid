package com.ant.recharge.entity;

import java.io.Serializable;

/**
 * 登录用户
 * Created by kwc on 2016/8/11.
 */
public class User implements Serializable {

    private String loginName;
    private String name;
    private String userType;
    private String orgId;
    private String loginFlag;
    private String isResetPwd;
    private String gender;
    private String telephone;

    private String email;
    private String wechat;
    private String qq;
    private String address;
    private String roleId;
    private String userInfoProvinceCode;
    private String userInfoCityCode;
    private String userInfoProvinceName;
    private String userInfoCityName;

    private String invite;
    private String token;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getIsResetPwd() {
        return isResetPwd;
    }

    public void setIsResetPwd(String isResetPwd) {
        this.isResetPwd = isResetPwd;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getUserInfoProvinceCode() {
        return userInfoProvinceCode;
    }

    public void setUserInfoProvinceCode(String userInfoProvinceCode) {
        this.userInfoProvinceCode = userInfoProvinceCode;
    }
    public String getUserInfoCityCode() {
        return userInfoCityCode;
    }

    public void setUserInfoCityCode(String userInfoCityCode) {
        this.userInfoCityCode = userInfoCityCode;
    }

    public String getUserInfoCityName() {
        return userInfoCityName;
    }

    public void setUserInfoCityName(String userInfoCityName) {
        this.userInfoCityName = userInfoCityName;
    }

    public String getUserInfoProvinceName() {
        return userInfoProvinceName;
    }

    public void setUserInfoProvinceName(String userInfoProvinceName) {
        this.userInfoProvinceName = userInfoProvinceName;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
