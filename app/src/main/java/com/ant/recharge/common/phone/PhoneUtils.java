package com.ant.recharge.common.phone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.ant.recharge.R;

import java.util.regex.Pattern;


/**
 * Created by kwc on 2016/8/12.
 */
public class PhoneUtils {


    /**
     * 检查手机号
     * @param phone
     * @param context
     * @return
     */
    public static Boolean isMobilePhone(String phone, Context context) {

        //手机号空
        if (phone == null || "".equals(phone.trim())) {
            Toast.makeText(context, R.string.hint_mobile, Toast.LENGTH_SHORT).show();
            return false;
        }

        //手机号11位
        if (phone.trim().length() != 11) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        //正则表达式，手机号
        Pattern p = Pattern.compile("^1[3|4|5|8]\\d{9}$");
        if (!p.matcher(phone.trim()).matches()) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    /**
     * 拨打电话
     * @param context
     * @param phoneNo
     */
    public static void call(Context context, String phoneNo) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNo);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "没有设置拨打电话的权限！", Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intent);
    }

}
