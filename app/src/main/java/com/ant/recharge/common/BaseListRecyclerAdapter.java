package com.ant.recharge.common;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by kwc on 2016/11/30.
 */
public abstract class BaseListRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected Activity context;
    protected LayoutInflater mInflater;
    protected List list;

    public BaseListRecyclerAdapter(Activity context, List list){
        this.context = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder2((T)holder, position);
    }

    public abstract void onBindViewHolder2(T holder, int position);

    @Override
    public int getItemCount() {
        int count = 0;
        if (null != list) {
            count = list.size();
        }
        return count;
    }

    public void replaceList(List list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void appendList(List list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
