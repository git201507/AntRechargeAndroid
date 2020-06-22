package com.ant.recharge.common.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by kwc on 2016/9/19.
 */
public class ScrollControlerViewPager extends ViewPager {

    private boolean isCanScroll = false;


    public ScrollControlerViewPager(Context context) {
        super(context);
    }

    public ScrollControlerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean canScroll) {
        isCanScroll = canScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll){
            super.scrollTo(x, y);
        }
    }
}
