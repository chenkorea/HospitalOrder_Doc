package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineVo;
import com.boil.hospitalorder.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PersonCheckAdapter extends BaseAdapter{

	private List<ExamineVo> list;
	
	public PersonCheckAdapter(Context context, List<ExamineVo> list) {
		this.list=list;
	}

	public void upadteAdapter(List<ExamineVo> data) {
		this.list = data;
		this.notifyDataSetChanged();
	}
	public PersonCheckAdapter(List<ExamineVo> list) {
		this.list=list;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.person_health_list_item, null);
		}
		TextView title=ViewHolder.get(convertView, R.id.tv_test_title);
		TextView time=ViewHolder.get(convertView, R.id.tv_test_time);
		TextView IsTest=ViewHolder.get(convertView, R.id.tv_is_test);
		
		ExamineVo ph=list.get(position);
		title.setText(ph.getAddress());
		time.setText(ph.getTjDate());
		IsTest.setText("已体检");
		return convertView;
	}

}
