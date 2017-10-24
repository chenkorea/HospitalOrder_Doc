package com.boil.hospitalorder.hospitaldoctor.my.modal;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderHosAdapter extends BaseAdapter{

	private Context context;
	private List<CooperationHosVo> list;
	
	
	public OrderHosAdapter(Context context,List<CooperationHosVo> list) {
		super();
		this.context = context;
		this.list=list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if(convertView==null){
			
			convertView=LayoutInflater.from(arg2.getContext()).inflate(R.layout.layout_order_hos_item,null);
		}
		
		TextView name=ViewHolder.get(convertView, R.id.tv_order_hos);
		name.setText(list.get(arg0).getName());
		
		return convertView;
	}

}
