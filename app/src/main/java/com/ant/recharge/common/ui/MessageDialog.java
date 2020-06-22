package com.ant.recharge.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ant.recharge.R;
import com.ant.recharge.common.phone.PhoneUtils;

/**
 * Created by kwc on 2016/8/18.
 */
public class MessageDialog extends Dialog {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LinearLayout mMainView;
    private LinearLayout.LayoutParams mMainViewlp;

    public MessageDialog(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MessageDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    protected MessageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    private void initView() {

        mLayoutInflater = LayoutInflater.from(mContext);
        mMainViewlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mMainView = (LinearLayout) mLayoutInflater.inflate(
                R.layout.view_messagedialog, null);

        //取消
        mMainView.findViewById(R.id.action_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.this.dismiss();
            }
        });
        //确定
        mMainView.findViewById(R.id.action_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.this.dismiss();
                PhoneUtils.call(getContext(),"4006551295");
            }
        });
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

}
