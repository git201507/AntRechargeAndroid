package com.ant.recharge.fragment2.planner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.fragment2.planner.entity.SalaryEntity;

import java.util.List;

/**
 * 理财师，我的收益
 * Created by kwc on 2016/11/30.
 */
public class PlannerSalaryAdapter extends BaseListAdapter<SalaryEntity> {

    public PlannerSalaryAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.planner_tab2_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new MyViewHolder();
            holder.numberTV = (TextView) view.findViewById(R.id.planner_salary_icon);
            holder.planner_salary_1 = (TextView) view.findViewById(R.id.planner_salary_1);
            holder.planner_salary_2 = (TextView) view.findViewById(R.id.planner_salary_2);
            holder.planner_salary_3 = (TextView) view.findViewById(R.id.planner_salary_3);
            holder.planner_salary_4 = (TextView) view.findViewById(R.id.planner_salary_4);
            holder.planner_salary_5 = (TextView) view.findViewById(R.id.planner_salary_5);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }

        SalaryEntity entity = (SalaryEntity) getItem(i);
        holder.numberTV.setText((i + 1) + "");
        holder.planner_salary_1.setText(entity.getDraftName());
        holder.planner_salary_2.setText("");
        holder.planner_salary_3.setText("工资来源：" + entity.getMemberName());
        holder.planner_salary_4.setText(entity.getSalary());
        holder.planner_salary_5.setText(entity.getCreateDate());

        return view;
    }

    class MyViewHolder {
        TextView numberTV;//脚标
        TextView planner_salary_1;
        TextView planner_salary_2;
        TextView planner_salary_3;
        TextView planner_salary_4;
        TextView planner_salary_5;

    }

}
