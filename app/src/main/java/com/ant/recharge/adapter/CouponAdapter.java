package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.CouponEntity;
import com.ant.recharge.entity.MoneyRecordEntity;

import java.util.List;

/**
 * Created by kwc on 2016/9/11.
 */
public class CouponAdapter extends BaseListAdapter<CouponEntity> {


    public CouponAdapter(Context context, List<CouponEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_coupon_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
            holder.iconTV = (TextView) view.findViewById(R.id.financial_icon);
            holder.titleTV = (TextView) view.findViewById(R.id.financial_title);
            holder.dateTV = (TextView) view.findViewById(R.id.financial_date);
            holder.moneyTV = (TextView) view.findViewById(R.id.financial_money);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        CouponEntity entity = (CouponEntity) getItem(i);
        holder.iconTV.setText("" + (i+1));
        holder.titleTV.setText(entity.getCouponName());
//        holder.dateTV.setText("有效期至：" + entity.getShowCreateDate());
        holder.dateTV.setText("有效期至：" + entity.getDeadline());
        holder.moneyTV.setText("" + entity.getAmount() + "元");

        return view;
    }

    private class Holder{

        private TextView iconTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView moneyTV;
    }
}
