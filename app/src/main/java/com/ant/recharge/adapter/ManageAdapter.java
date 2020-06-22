package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.FinanceProductEntity;

import java.util.List;

/**
 * Created by kwc on 2016/9/8.
 */
public class ManageAdapter extends BaseListAdapter {


    public ManageAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_financial_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
            holder.titleTV = (TextView) view.findViewById(R.id.financial_title);
            holder.dateTV = (TextView) view.findViewById(R.id.financial_date);
            holder.moneyTV = (TextView) view.findViewById(R.id.financial_money);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        FinanceProductEntity entity = (FinanceProductEntity) getItem(i);
        holder.titleTV.setText(entity.getName());
        holder.dateTV.setText("还款日期：" + entity.getLatestRepaymentDate() + "  理财期" + entity.getFinanceDays()  + "天");
        holder.moneyTV.setText(entity.getAmount().toPlainString() + "(" + entity.getZsy()  + ")元");

        return view;
    }

    private class Holder{

        private TextView titleTV;
        private TextView dateTV;
        private TextView moneyTV;
    }
}
