package com.ant.recharge.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;

import java.net.URL;
import java.util.Date;
import java.util.List;

import retrofit.client.Header;

/**
 * Created by kwc on 2016/9/21.
 */
public class VerifyCode {

    public static final String userid = "402881e551cda3fc0151cdae1cf40002";

    /**
     * 图片验证码
     * @param userId
     * @return
     * @throws Exception
     */
    public static Bitmap getCodeBitmap(String userId) throws Exception{

        URL url = new URL(Profile.SERVER_ADDRESS + "/common/img/appYzCode.jpeg?d=" + new Date().getTime() + "&memberId=" + userId);
//        URL url = new URL("/common/img/vipCode.jpeg?d=%f&memberId=%@");
        return BitmapFactory.decodeStream(url.openStream());
    }

    /**
     * 根据返回的代码，提示信息
     * @return
     */
    public static String getMessageByCode(String nResponse){
        String result = "";
        if("success:120".equals(nResponse)){
            //成功
            result = "您生成验证码并发送到您的手机！";
        } else if("codeerror".equals(nResponse)){
            result = "图形验证码填写错误！";
        } else if("haveMakeOutError".equals(nResponse)){
            result = "在一段时间内不能重复生成验证码！";
        } else {
            result = "手机验证码发送失败，请稍后重试！";
        }
        return result;

    }





}
