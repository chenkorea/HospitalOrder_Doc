package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.VerifyResultVo;
import com.boil.hospitalorder.utils.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VerifyResultListAdapter extends BaseAdapter{


	/** 检验结果列表数据 */
	private List<VerifyResultVo> vrVos;
	
	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param vrVos 个人数据
	 * 
	 */
	public VerifyResultListAdapter(List<VerifyResultVo> vrVos) {
		this.vrVos = vrVos;
	}
	
	public void upadteAdapter(List<VerifyResultVo> data) {
		this.vrVos = data;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return vrVos.size();
	}

	@Override
	public Object getItem(int position) {
		return vrVos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.verify_result_list_view_item, null);
		}
		
		TextView tvOrderName = ViewHolder.get(convertView, R.id.tv_order_name);
		TextView tvIsVerify = ViewHolder.get(convertView, R.id.tv_is_verify);
		TextView tvLisDate = ViewHolder.get(convertView, R.id.tv_lis_date);
		
		if ((vrVos != null) && !vrVos.isEmpty()) {
			VerifyResultVo vrVo = vrVos.get(position);
			
			tvOrderName.setText(vrVo.getOrderName());
			tvLisDate.setText(String.format("于%s检验", vrVo.getLisDate()));
			tvIsVerify.setText("已检验");
		}
		
		return convertView;
	}

}
