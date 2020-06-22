package com.ant.recharge.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.pull2fresh.internal.Utils;
import com.ant.recharge.model.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName AreaAdapter
 * @Description 筛选适配器
 * @Author zhouyong
 * @Date 2015-08-13 14:24
 * @Version V 1.0
 */
public class AreaAdapter extends BaseAdapter {
    private List<String> list=null;
    private Context context;
    private int selectPosition;

    public AreaAdapter(List<String> list, Context context){
        this.list=list;
        this.context=context;
    }

    public void setSelectPosition(int position){
        this.selectPosition=position;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.filter_area_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tvPCA= view.findViewById(R.id.tvPCA);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tvPCA.setText(list.get(i));
        viewHolder.tvPCA.setGravity(Gravity.CENTER);
        //为了让点击的item显示不同的颜色，必须在item点击后 重新 notifyDataSetChange
        if (selectPosition==i){
            viewHolder.tvPCA.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.tvPCA.setTextColor(context.getResources().getColor(R.color.color_white));
        }else {
            viewHolder.tvPCA.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            view.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            viewHolder.tvPCA.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        return view;
    }

    class ViewHolder{
        TextView tvPCA;
    }
}
