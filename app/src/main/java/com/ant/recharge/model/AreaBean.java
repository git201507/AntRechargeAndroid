package com.ant.recharge.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @FileName AreaBean
 * @Description 筛选实体类
 * @Author zhouyong
 * @Date 2015-08-13 14:08
 * @Version V 1.0
 */
public class AreaBean implements Parcelable {
    private String areaName;//省
    private String areaValue;//市

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaName);
        dest.writeString(this.areaValue);
    }

    public AreaBean() {
    }

    private AreaBean(Parcel in) {
        this.areaName = in.readString();
        this.areaValue = in.readString();
    }

    public static final Parcelable.Creator<AreaBean> CREATOR = new Parcelable.Creator<AreaBean>() {
        public AreaBean createFromParcel(Parcel source) {
            return new AreaBean(source);
        }

        public AreaBean[] newArray(int size) {
            return new AreaBean[size];
        }
    };

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String fiterName) {
        this.areaName = fiterName;
    }

    public String getAreaValue() {
        return this.areaValue;
    }

    public void setAreaValue(String fiterValue) {
        this.areaValue = fiterValue;
    }

}
