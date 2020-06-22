package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.CouponCenterEntity;
import com.ant.recharge.entity.SalaryTotalDetail;

import java.util.List;

/**
 * 兑换优惠券
 * Created by kwc on 2016/9/18.
 */
public class SalaryCouponAdapter extends BaseListAdapter<CouponCenterEntity> {

    public SalaryCouponAdapter(Context context, List<CouponCenterEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
        view = getmInflater().inflate(R.layout.activity_salary_coupon_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
        holder.iconTV = (TextView) view.findViewById(R.id.financial_icon);
        holder.titleTV = (TextView) view.findViewById(R.id.financial_title);
        holder.dateTV = (TextView) view.findViewById(R.id.financial_date);
        holder.moneyTV = (TextView) view.findViewById(R.id.financial_money);
            holder.backgroundLayout = view.findViewById(R.id.salary_coupon_layout);

        view.setTag(holder);
        } else {
        holder = (Holder) view.getTag();
        }

        CouponCenterEntity entity = (CouponCenterEntity) getItem(i);
        holder.iconTV.setText("" + (i + 1));
        holder.titleTV.setText(entity.getCouponName());
        holder.dateTV.setText("有效期" + entity.getValidityDays() + "天");
        holder.moneyTV.setText("" + entity.getAmount() + "元");
        holder.backgroundLayout.setBackgroundResource(entity.getSelected()?R.color.line:android.R.color.white);
        return view;
    }

    private class Holder{
        private View backgroundLayout;
        private TextView iconTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView moneyTV;
    }
}