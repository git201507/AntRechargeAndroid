package com.ant.recharge.fragment2.planner;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListRecyclerAdapter;
import com.ant.recharge.fragment2.planner.entity.Customer;

import java.util.List;

/**
 * 理财师：我的客户
 * Created by kwc on 2016/11/30.
 */
public class PlannerCustomerAdapter extends BaseListRecyclerAdapter<PlannerCustomerAdapter.MyViewHolder> {

    public PlannerCustomerAdapter(Activity context, List list) {
        super(context, list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.planner_tab1_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder2(PlannerCustomerAdapter.MyViewHolder holder, int position) {

        final Customer entity = (Customer) list.get(position);
        holder.realNameTV.setText(entity.getRealName());
        holder.totalInvestTV.setText(entity.getTotalInvest());
        holder.totalSalaryTV.setText(entity.getTotalSalary());
        holder.totalIncomeTV.setText(entity.getTotalIncome());

        holder.realNameTV.setTypeface(Typeface.DEFAULT);
        if("合计".equals(entity.getRealName())){
            holder.realNameTV.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView realNameTV;//姓名
        TextView totalInvestTV;//累计投资
        TextView totalSalaryTV;//创造工资
        TextView totalIncomeTV;//创造收益

        public MyViewHolder(View view){
            super(view);
            realNameTV = (TextView) view.findViewById(R.id.realName);
            totalInvestTV = (TextView) view.findViewById(R.id.totalInvest);
            totalSalaryTV = (TextView) view.findViewById(R.id.totalSalary);
            totalIncomeTV = (TextView) view.findViewById(R.id.totalIncome);
        }
    }

}
