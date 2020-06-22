package com.ant.recharge.verified;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.main.NetAccountInterface;

/**
 * Created by User on 2016/8/30.
 */
public class VerifiedUtil {


    /**
     * 启动认证的activity
     * @param context
     */
    public static void startVerifiedActivity(Context context, String userId){

        if(isVerified(context,userId)){
//            context.startActivity();
            context.startActivity(new Intent(context, VerifiedSuccessActivity.class));
        } else {
            context.startActivity(new Intent(context, VerifiedActivity.class));
        }
    }


    /**
     * 是否已认证
     * @param context
     * @return
     */
    public static Boolean isVerified(Context context, String userId){
        SharedPreferences preferences = context.getSharedPreferences(NetAccountInterface.ANT_ACCOUNT, Context.MODE_PRIVATE);
        String accountInfo = preferences.getString(userId, null);
        if(accountInfo != null){
            try {
                MyAccountDetail account = JsonUtil.decode(accountInfo, MyAccountDetail.class);
                Log.d("----------accountInfo", "" + accountInfo);
                if(!StringUtils.isBlank(account.getIpsAccount())){
                    //测试，暂时为未认证
                    return true;
                }
            } catch (Exception e) {
                Log.d("----------ipsAcount", "" + e.getMessage());
//                e.printStackTrace();
            }
        }
        return false;
    }

}
