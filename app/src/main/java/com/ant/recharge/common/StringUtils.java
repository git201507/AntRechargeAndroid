package com.ant.recharge.common;

import java.math.BigDecimal;

/**
 * Created by kwc on 2016/9/1.
 */
public class StringUtils {

    /**
     * 字符串为空
     * @param string
     * @return
     */
    public static Boolean isBlank(String string){
        if (string == null) return true;
        if ("".equals(string.trim())) return true;
        return false;
    }


    /**
     *
     * @param a
     * @param length 保留小数点位数
     * @return
     */
    public static Long formatDouble(Double a, int length){
        BigDecimal b = new BigDecimal(a);
        return b.setScale(length, BigDecimal.ROUND_HALF_UP).longValue();
    }

}
