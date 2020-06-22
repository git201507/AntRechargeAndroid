package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.FinancePersonEntity;

import java.util.List;

/**
 * 投资adapter
 * 显示：投标记录-申购情况
 * Created by kwc on 2016/9/17.
 */
public class InvestmentAdapter extends BaseListAdapter {


    private Boolean isVip = false;

    public InvestmentAdapter(Context context, List list) {
        super(context, list);
    }

    public InvestmentAdapter(Context context, List list, Boolean isVip) {
        super(context, list);
        this.isVip = isVip;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_investment_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
            holder.nameTV = (TextView) view.findViewById(R.id.investment_name);
            holder.dateTV = (TextView) view.findViewById(R.id.investment_date);
            holder.moneyTV = (TextView) view.findViewById(R.id.investment_money);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        FinancePersonEntity entity = (FinancePersonEntity) getItem(i);
        holder.nameTV.setText(entity.getName());
        holder.dateTV.setText(entity.getTimeStr());

        if(isVip){
            //申购记录
//            holder.moneyTV.setText(getStatusStr(entity.getAmount()));
            holder.moneyTV.setText(entity.getAppVersion());
        } else {
            //投标记录
            holder.moneyTV.setText("" + entity.getAmount());
        }


        return view;
    }

    private class Holder{

        private TextView nameTV;
        private TextView moneyTV;
        private TextView dateTV;
    }


    private String getStatusStr(int status){

        if(0 == status){
            return "待审核";
        }

        return "";
    }
}
