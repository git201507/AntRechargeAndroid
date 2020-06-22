package com.ant.recharge.fragment1.planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.ui.BaseDialog;
import com.ant.recharge.fragment1.FinancialVIPDetailActivity;
import com.ant.recharge.fragment2.planner.entity.FinancePlannerEntity;

/**
 * 理财师选择
 * Created by kwc on 2016/11/29.
 */
public class PlannerSelectDialog extends BaseDialog {

    private View cancelTV;
    private View okTV;
    private TextView contentTV;
    private FinancialVIPDetailActivity activity;


    private TextView planner_describe;//activity中的简介组件
    private String userId;
    private FinancePlannerEntity financePlannerEntity;


    public PlannerSelectDialog(FinancialVIPDetailActivity context) {
        super(context);
        this.activity = context;
    }

    public PlannerSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PlannerSelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 初始化视图
     * @return
     */
    @Override
    protected View initView() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(getmContext());
        View view = mLayoutInflater.inflate(R.layout.planner_select_dialog, null);
        //微软雅黑
        FontHelper.applyFont(getmContext(), view);

        okTV = view.findViewById(R.id.dialog_ok);
        cancelTV = view.findViewById(R.id.dialog_cancel);
        contentTV = (TextView) view.findViewById(R.id.dialog_content);

        contentTV.setText("您还没有指定的理财师，选择专属理财师，享受0.2年化额外收益，并有专人为您提供一对一的理财服务。");
        initListener();


        setCancelable(false);
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
                if(activity != null){
                    //立即申请
                    activity.appVerifyVipCode();
                }
                dismiss();
            }
        });

        //选择理财师
        okTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmContext().startActivity(new Intent(getmContext(), PlannerSelectActivity.class));
                dismiss();
            }
        });
    }


}
