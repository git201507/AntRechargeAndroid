package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.MoneyRecordEntity;
import com.ant.recharge.financial.FinancialActivity;

import java.util.List;

/**
 * Created by kwc on 2016/9/6.
 */
public class FinancialAdapter extends BaseListAdapter{


    public FinancialAdapter(Context context) {
        super(context);
    }

    public FinancialAdapter(Context context, List list) {
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

        MoneyRecordEntity entity = (MoneyRecordEntity) getItem(i);

        String title = "";
        if(FinancialActivity.financial_type_cz.equals(entity.getCurrentType())){
            title = "平台充值";
        } else if(FinancialActivity.financial_type_tx.equals(entity.getCurrentType())){
            title = "平台提现";
        } else if(FinancialActivity.financial_type_tz.equals(entity.getCurrentType())){
            title = "平台投资";
        }

        holder.titleTV.setText(title);
        holder.dateTV.setText("投资日期" + entity.getCreateDateStr());
        holder.moneyTV.setText(entity.getTradeAmount().toPlainString() + "元");
        return view;
    }

    private class Holder{

        private TextView titleTV;
        private TextView dateTV;
        private TextView moneyTV;
    }
}
