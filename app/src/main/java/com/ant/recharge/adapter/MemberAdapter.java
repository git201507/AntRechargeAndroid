package com.ant.recharge.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.InvitorEntity;

import java.util.List;

/**
 * adapter
 * 邀请人员
 * Created by kwc on 2016/9/19.
 */
public class MemberAdapter extends BaseListAdapter {


    public MemberAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_member_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();

            holder.titleTV = (TextView) view.findViewById(R.id.member_name);
            holder.dateTV = (TextView) view.findViewById(R.id.member_date);
            holder.detailTV = (TextView) view.findViewById(R.id.member_salary_item);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        InvitorEntity entity = (InvitorEntity) getItem(i);
        holder.titleTV.setText(entity.getName());
        holder.dateTV.setText(entity.getDate());
        holder.detailTV.setText("" + entity.getSalary());

        return view;
    }

    private class Holder{
        private TextView titleTV;
        private TextView dateTV;
        private TextView detailTV;
    }

    public String[] getTotalStr(){
        String[] total = {"" + getCount(),"0.00"};
        float totalSalary = 0.00f;
        if(getCount() > 0){
            for (int i = 0; i < getCount(); i++){
                InvitorEntity entity = (InvitorEntity) getItem(i);
                totalSalary+=entity.getSalary();
            }
            total[1] = "" + totalSalary;
        }
        return total;
    }
}