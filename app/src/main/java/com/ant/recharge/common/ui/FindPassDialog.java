package com.ant.recharge.common.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.login.ForgotPasswordActivity;

/**
 * Created by kwc on 2016/9/5.
 */
public class FindPassDialog extends Dialog {


    private ForgotPasswordActivity mContext;
    private LayoutInflater mLayoutInflater;
    private LinearLayout mMainView;
    private LinearLayout.LayoutParams mMainViewlp;

    private String content;

    public FindPassDialog(ForgotPasswordActivity context, String content) {
        super(context);
        mContext = context;
        this.content = content;
        initView();
    }

    public FindPassDialog(ForgotPasswordActivity context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    protected FindPassDialog(ForgotPasswordActivity context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    private void initView() {


        mLayoutInflater = LayoutInflater.from(mContext);
        mMainViewlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mMainView = (LinearLayout) mLayoutInflater.inflate(
                R.layout.view_password_dialog, null);

        TextView cotentTV = (TextView) mMainView.findViewById(R.id.dialog_content);
        cotentTV.setText(content);

        setCancelable(false);
        //确定
        mMainView.findViewById(R.id.action_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPassDialog.this.dismiss();
                mContext.finish();
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
        lp.width = (int) (dm.widthPixels * 0.9);
        getWindow().setAttributes(lp);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.transparent);
//        setCanceledOnTouchOutside(touchable);
    }
}
