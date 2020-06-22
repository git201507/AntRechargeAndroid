package com.ant.recharge.entity;

import java.io.Serializable;

/**
 * Created by wangxiayang on 2017/10/23.
 */

public class PicEntity implements Serializable {

    private String pic1;
    private String pic2;
    private String pic3;

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic1() {
        return pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic3() {
        return pic3;
    }
}
