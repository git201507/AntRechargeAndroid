package com.ant.recharge.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @FileName FiterBean
 * @Description 筛选实体类
 * @Author zhouyong
 * @Date 2015-08-13 14:08
 * @Version V 1.0
 */
public class FiterBean implements Parcelable {
    private String fiterName;//筛选名字
    private String fiterValue;//删选值

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fiterName);
        dest.writeString(this.fiterValue);
    }

    public FiterBean() {
    }

    private FiterBean(Parcel in) {
        this.fiterName = in.readString();
        this.fiterValue = in.readString();
    }

    public static final Parcelable.Creator<FiterBean> CREATOR = new Parcelable.Creator<FiterBean>() {
        public FiterBean createFromParcel(Parcel source) {
            return new FiterBean(source);
        }

        public FiterBean[] newArray(int size) {
            return new FiterBean[size];
        }
    };

    public String getFiterName() {
        return this.fiterName;
    }

    public void setFiterName(String fiterName) {
        this.fiterName = fiterName;
    }

    public String getFiterValue() {
        return this.fiterValue;
    }

    public void setFiterValue(String fiterValue) {
        this.fiterValue = fiterValue;
    }

}
