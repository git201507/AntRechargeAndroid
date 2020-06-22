package com.ant.recharge.common;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by kwc on 2016/6/30.
 */
public class DisplayUtil {

    public static float dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float sp2px(Context context, float spValue){
        final float fontscale = context.getResources().getDisplayMetrics().scaledDensity;
        return fontscale * spValue + 0.5f;
    }

    public static int getWindowWidth(Activity context){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

}
