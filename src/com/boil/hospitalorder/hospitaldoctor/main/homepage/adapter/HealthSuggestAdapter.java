package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HealthSuggestAdapter extends BaseAdapter{

	private List<String> list;
	public HealthSuggestAdapter(List<String> list) {
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.health_detail_suggest_item, null);
		}
		TextView result=ViewHolder.get(convertView, R.id.tv_suggest_item);
		result.setText(list.get(position));
		
		return convertView;
	}

}
