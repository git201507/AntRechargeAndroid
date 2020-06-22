package com.ant.recharge.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by kwc on 2016/8/16.
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //状态栏颜色与app应用保持一致
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0033FF"));
        }

    }

    protected void initView(Context context, int layout){
        View root = getLayoutInflater().inflate(layout, null);
        FontHelper.applyFont(context, root);
        setContentView(root);
    }

    /**
     * read the preferences and get the value
     * @return String
     */
    protected String readPreferences(String perferencesName, String key) {
        SharedPreferences preferences = getSharedPreferences(perferencesName, MODE_PRIVATE);
        String result = preferences.getString(key, null);
        return result;
    }

    /**
     * write the preferences
     */
    protected void writePreferences(String perferencesName, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(perferencesName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
