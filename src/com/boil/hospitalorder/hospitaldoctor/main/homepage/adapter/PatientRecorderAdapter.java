package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.RecorderVo;
import com.boil.hospitalorder.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PatientRecorderAdapter extends BaseAdapter{

	private Context context;
	private List<RecorderVo> list;
	private String hosName;
	
	public PatientRecorderAdapter(Context context, List<RecorderVo> list,String hosName) {
		super();
		this.context = context;
		this.list = list;
		this.hosName=hosName;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.layout_adm_history_item, null);
		}
		
		TextView no=ViewHolder.get(convertView, R.id.tv_no);
		TextView date=ViewHolder.get(convertView, R.id.tv_date);
		TextView dept=ViewHolder.get(convertView, R.id.tv_dept);
		TextView state=ViewHolder.get(convertView, R.id.tv_state);
		
		dept.setText("就诊科室："+list.get(arg0).getAdmDep());
		state.setText(list.get(arg0).getAdmType());
		no.setText("就诊号："+list.get(arg0).getAdmNo());
		date.setText("就诊日期："+list.get(arg0).getAdmTime());
		
		return convertView;
	}

}
