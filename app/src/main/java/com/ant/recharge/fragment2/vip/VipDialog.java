package com.ant.recharge.fragment2.vip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.ui.BaseDialog;

/**
 * 会员等级对话框
 *
 * Created by kwc on 2016/11/26.
 */
public class VipDialog extends BaseDialog {

    private View cancelTV;
    private TextView understandTV;
    private TextView contentTV;


    public VipDialog(Context context) {
        super(context);
    }

    public VipDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected VipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 初始化视图
     * @return
     */
    @Override
    protected View initView() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        View view = mLayoutInflater.inflate(R.layout.base_dialog, null);
        //微软雅黑
        FontHelper.applyFont(getContext(), view);

        cancelTV = view.findViewById(R.id.dialog_cancel);
        contentTV = (TextView) view.findViewById(R.id.dialog_content);
        understandTV = (TextView) view.findViewById(R.id.dialog_btn);
        final String alert = "VIP用户独享VIP标的独享买断，持票代收，并有用专享客服，随时为您服务。";
        contentTV.setText(alert);
        understandTV.setText(R.string.btn_understand);

        initListener();
        return view;
    }

    /**
     * 设置监听
     */
    protected void initListener() {

        //关闭
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //了解更多
        understandTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmContext().startActivity(new Intent(getContext(), VipDescriptionActivity.class));
                dismiss();
            }
        });
    }


}
