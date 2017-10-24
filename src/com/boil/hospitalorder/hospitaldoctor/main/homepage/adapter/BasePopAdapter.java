package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.BasePopVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginDeptVo;
import com.boil.hospitalorder.utils.ViewHolder;

public class BasePopAdapter extends BaseAdapter{

	private Context context;
	private List<LoginDeptVo> list = new ArrayList<LoginDeptVo>();
	
	
	public BasePopAdapter(Context context,List<LoginDeptVo> list) {
		super();
		this.context = context;
		this.list=list;
	}

	
	public BasePopAdapter(Context context) {
		super();
		this.context = context;
	}


	public List<LoginDeptVo> getList() {
		return list;
	}

	public void setList(List<LoginDeptVo> list) {
		this.list = list;
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
			
			convertView=LayoutInflater.from(arg2.getContext()).inflate(R.layout.base_popwindow_item,null);
		}
		
		TextView name=ViewHolder.get(convertView, R.id.tv_order_hos);
		name.setText(list.get(arg0).getName());
		
		return convertView;
	}

}
