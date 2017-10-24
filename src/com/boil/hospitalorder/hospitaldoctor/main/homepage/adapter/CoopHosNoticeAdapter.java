package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.DocCircleInformation;
import com.boil.hospitalorder.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CoopHosNoticeAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<DocCircleInformation> list = new ArrayList<DocCircleInformation>();

	public CoopHosNoticeAdapter(Context context, List<DocCircleInformation> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public List<DocCircleInformation> getList() {
				return list;
			}
		
			public void setList(List<DocCircleInformation> list) {
				this.list = list;
			}
			public CoopHosNoticeAdapter(Context context) {
						super();
						this.context = context;
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
			
			convertView=LayoutInflater.from(context).inflate(R.layout.layout_notice_item, null);
		}
		
		TextView title=ViewHolder.get(convertView, R.id.tv_notice_title);
		TextView time=ViewHolder.get(convertView, R.id.tv_notice_time);
		
		TextView tv_hos=ViewHolder.get(convertView, R.id.tv_notice_hos);
			DocCircleInformation vo=list.get(arg0);
		title.setText(vo.getTitle());
		time.setText(vo.getRecordtime());
		tv_hos.setText(vo.getSource());
		return convertView;
	}

}
