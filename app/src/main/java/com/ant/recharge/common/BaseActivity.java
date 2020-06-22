package com.ant.recharge.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ant.recharge.R;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;

/**
 * Created by kwc on 2016/8/10.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {


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
        //FontHelper.applyFont(context, root);
        setContentView(root);
    }

    /**
     * read the preferences and get the value
     * @return String
     */
    public String readPreferences(String perferencesName, String key) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                return;
            default:
                break;
        }
    }

    //退出登录
    protected void exitLogin() {
        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        User user = null;
        try {
            user = JsonUtil.decode(userStr, User.class);
            user.setToken("");
            writePreferences(NetLoginInterface.ANT_LOGIN_USER, "additional", JsonUtil.encode(user));

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
