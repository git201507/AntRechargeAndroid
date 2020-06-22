package com.ant.recharge.application;

import android.app.Application;

import com.ant.recharge.main.MainTabActivity;

public class MyApplication extends Application {

    private MainTabActivity mainTabActivity = null;


    // 总Tab
    public void setMainTabActivity(MainTabActivity activity) {
        this.mainTabActivity = activity;
    }

    public MainTabActivity getMainTabActivity() {
        return this.mainTabActivity;
    }

}