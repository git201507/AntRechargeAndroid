package com.ant.recharge.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ant.recharge.R;

/**
 * 定义超类
 * 定义初始化视图、监听等方法，子类实现
 * Created by kwc on 2016/11/26.
 */
public abstract class BaseDialog extends Dialog {

    private Context mContext;
    private View mMainView;
    private LinearLayout.LayoutParams mMainViewlp;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        init();
    }

    private void init() {
        mMainViewlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mMainView = initView();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(mMainView, mMainViewlp);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.25f;
        lp.alpha = 1.0f;
        lp.width = (int) (dm.widthPixels * 0.85);
        getWindow().setAttributes(lp);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.transparent);
//        setCanceledOnTouchOutside(touchable);
    }

    /**
     * 初始化视图
     */
    protected abstract View initView();
//    protected abstract void initView(View dialogLayout);


    public Context getmContext() {
        return mContext;
    }
}
