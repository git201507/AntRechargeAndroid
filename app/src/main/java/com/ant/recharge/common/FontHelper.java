package com.ant.recharge.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kwc on 2016/11/9.
 */
public class FontHelper {

    private static final String TAG = FontHelper.class.getSimpleName();

    public static void applyTextViewFont(final Context context, TextView textView) {
        String fontName = "MicrosoftYaHeiGB.ttf";
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
    }

    public static void applyFont(final Context context, final View root) {
       /**影响性能暂时去掉
        String fontName = "MicrosoftYaHeiGB.ttf";
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView){
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            }
        } catch (Exception e) {
            Log.e(TAG, String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }
        */
    }

    public static void applyFont(final Context context, final View root, final String fontName) {
        /** 影响性能暂时去掉
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
        } catch (Exception e) {
           Log.e(TAG, String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }
         */
    }
}
