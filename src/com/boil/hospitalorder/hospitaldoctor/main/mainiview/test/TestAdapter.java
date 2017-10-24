package com.boil.hospitalorder.hospitaldoctor.main.mainiview.test;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;

public class TestAdapter extends BaseAdapter{

	private List<String> list = new ArrayList<String>();
	private Context context;
	
	public TestAdapter(List<String> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.from(context).inflate(R.layout.gridview_home_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(getCount() > 0) {
			viewHolder.tv_content.setText(list.get(position));
		}
		
		return convertView;
	}

	class ViewHolder{
		
		TextView tv_content;
		
	}
	
}
