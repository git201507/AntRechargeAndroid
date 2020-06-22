package com.ant.recharge.fragment2.lottery.entity;

/**
 * 分享
 * 分享后，后台接口调用返回的实体
 * Created by kwc on 2016/11/29.
 */
public class ShareEntity {

    private String title;
    private String link;
    private String desc;
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
