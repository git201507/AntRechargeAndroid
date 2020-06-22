package com.ant.recharge.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;

	private List<T> list;

	public BaseListAdapter(Context context) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public BaseListAdapter(Context context, List<T> list) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
	}

	protected Context getContext() {
		return context;
	}

	protected void setContext(Context context) {
		this.context = context;
	}

	protected LayoutInflater getmInflater() {
		return mInflater;
	}

	protected void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}

	public List<T> getList() {
		return list;
	}

	protected void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (null != list) {
			count = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		Object obj = null;
		if (null != list) {
			obj = list.get(position);
		}
		return obj;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeList(){
		this.list.clear();
		notifyDataSetChanged();
	}

	public void replaceList(List<T> list){
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}


	public void appendList(List<T> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void refreshList(){
		notifyDataSetChanged();
	}
}
