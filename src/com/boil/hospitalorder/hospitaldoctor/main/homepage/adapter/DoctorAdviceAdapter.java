package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.AdviceVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.RecorderVo;
import com.boil.hospitalorder.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DoctorAdviceAdapter extends BaseAdapter{

	private Context context;
	private List<AdviceVo> list;
	
	public DoctorAdviceAdapter(Context context, List<AdviceVo> list) {
		super();
		this.context = context;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.layout_advice_item, null);
		}
		
		TextView time=ViewHolder.get(convertView, R.id.tv_time);
		TextView name=ViewHolder.get(convertView, R.id.tv_name);
		TextView freg=ViewHolder.get(convertView, R.id.tv_freg);
		TextView sta=ViewHolder.get(convertView, R.id.tv_sta);
		TextView doc=ViewHolder.get(convertView, R.id.tv_doc);
		TextView type=ViewHolder.get(convertView, R.id.tv_type);
		
		time.setText(list.get(arg0).getOrderDate()+"　"+list.get(arg0).getOrderTime());
		name.setText("医嘱名称："+list.get(arg0).getOrderName());
		
		String fregStr=list.get(arg0).getOrderFreg();
		if(StringUtils.isNotBlank(fregStr)){
			
			freg.setText("频次："+fregStr);
		}
		
		String staStr=list.get(arg0).getOrderSta();
		if(StringUtils.isNotBlank(staStr)){
			
			sta.setVisibility(View.VISIBLE);
			sta.setText(staStr);
			
		}else {
			sta.setVisibility(View.GONE);
		}
		
		doc.setText("开嘱人："+list.get(arg0).getOrderDoc());
		
		String typeStr=list.get(arg0).getOrderType();
		if(StringUtils.isNotBlank(typeStr)){
			
			type.setVisibility(View.VISIBLE);
			type.setText(typeStr);
			
		}else {
			type.setVisibility(View.GONE);
		}
		
		
		return convertView;
	}

}
