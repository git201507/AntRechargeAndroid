package com.ant.recharge.common.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by kwc on 2016/10/18.
 */
public class MainPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        if(position < -1 || position > 1){
            page.setAlpha(0);
            return;
        }

        if(position <= 0){
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        }

        if(position > 0){
            page.setAlpha(1 - position);
            page.setTranslationX(pageWidth * - position);

            float scaleFator = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));

            page.setScaleX(scaleFator);
            page.setScaleY(scaleFator);
        }

    }
}
