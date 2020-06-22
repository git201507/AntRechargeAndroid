package com.ant.recharge.fragment2.memberlevel;

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
public class MemberLevelHongDialog extends BaseDialog {

    private View cancelTV;
    private TextView understandTV;
    private TextView contentTV;

    public MemberLevelHongDialog(Context context) {
        super(context);
    }


    public MemberLevelHongDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MemberLevelHongDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        //FontHelper.applyFont(getContext(), view);

        cancelTV = view.findViewById(R.id.dialog_cancel);
        contentTV = (TextView) view.findViewById(R.id.dialog_content);
        understandTV = (TextView) view.findViewById(R.id.dialog_btn);
        //String  currmemberLevel = getMemberLevel();
        String alert = "您的会员等级是红钻，享受投资增益1%，推荐人投资1%返利待遇。";
        /**
        switch (currmemberLevel){
            case "level_4"://普通
                alert = "您的会员等级是普通会员。";
            case "level_3"://铂金
                alert = "您的会员等级是铂金，享受投资增益0.5%，推荐人投资0.5%返利待遇。";
            case "level_2"://红钻
                alert = "您的会员等级是红钻，享受投资增益1%，推荐人投资1%返利待遇。";
            case "level_1"://黑钻
                alert = "您的会员等级是黑钻，享受投资增益1%，推荐人投资2%返利待遇。";
            case "level_0":
                alert = "您的会员等级是普通会员。";
        } */
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
                getmContext().startActivity(new Intent(getContext(), MemberLevelDescriptionActivity.class));
                dismiss();
            }
        });
    }


}
