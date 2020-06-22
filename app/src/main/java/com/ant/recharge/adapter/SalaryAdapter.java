package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.CouponEntity;
import com.ant.recharge.entity.SalaryTotalDetail;
import com.ant.recharge.entity.SalaryWithdraw;

import java.util.List;

/**
 * Created by kwc on 2016/9/11.
 */
public class SalaryAdapter extends BaseListAdapter {
    public SalaryAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //判断是谁的实例
        Object entity = getItem(i);
        if(SalaryTotalDetail.class.isInstance(entity)){

            view = getmInflater().inflate(R.layout.activity_salary_withdraw_item, null);
            TextView iconTV = (TextView) view.findViewById(R.id.financial_icon);
            TextView titleTV = (TextView) view.findViewById(R.id.financial_title);
            TextView amountTV = (TextView) view.findViewById(R.id.sw_amount);
            TextView bankCodeTV = (TextView) view.findViewById(R.id.sw_bankCode);
            TextView dateTV = (TextView) view.findViewById(R.id.sw_date);
            SalaryTotalDetail salaryWithdraw = (SalaryTotalDetail) entity;
            iconTV.setText("" + (i+1));
            titleTV.setText(salaryWithdraw.getName());
            dateTV.setText(salaryWithdraw.getCreateDateStr());
            bankCodeTV.setText("       工资来源：" + salaryWithdraw.getRealName());
            amountTV.setText(salaryWithdraw.getProfit() + "元");

        } else if(SalaryWithdraw.class.isInstance(entity)){
            view = getmInflater().inflate(R.layout.activity_salary_withdraw_item, null);
            TextView iconTV = (TextView) view.findViewById(R.id.financial_icon);
            TextView titleTV = (TextView) view.findViewById(R.id.financial_title);
            TextView amountTV = (TextView) view.findViewById(R.id.sw_amount);
            TextView bankCodeTV = (TextView) view.findViewById(R.id.sw_bankCode);
            TextView dateTV = (TextView) view.findViewById(R.id.sw_date);
            SalaryWithdraw salaryWithdraw = (SalaryWithdraw) entity;
            iconTV.setText("" + (i+1));
            titleTV.setText(salaryWithdraw.getRealName());
            dateTV.setText(salaryWithdraw.getWithDrawDate());
            bankCodeTV.setText("卡号：" + salaryWithdraw.getBankCode());
            amountTV.setText(salaryWithdraw.getAmount() + "元  退回");
        } else {
            view = getmInflater().inflate(R.layout.activity_coupon_item, null);

            TextView iconTV = (TextView) view.findViewById(R.id.financial_icon);
            TextView titleTV = (TextView) view.findViewById(R.id.financial_title);
            TextView dateTV = (TextView) view.findViewById(R.id.financial_date);
            TextView moneyTV = (TextView) view.findViewById(R.id.financial_money);
            CouponEntity couponEntity = (CouponEntity) entity;
            iconTV.setText("" + (i+1));
            titleTV.setText(couponEntity.getCouponName());
            dateTV.setText("有效期至：" + couponEntity.getShowCreateDate());
            moneyTV.setText("" + couponEntity.getAmount() + "元");
        }

        FontHelper.applyFont(getContext(), view);

        return view;
    }

}
