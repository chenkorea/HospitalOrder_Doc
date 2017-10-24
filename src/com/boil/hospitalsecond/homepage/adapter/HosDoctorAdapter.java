package com.boil.hospitalsecond.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalsecond.homepage.model.HosDoctorVo;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class HosDoctorAdapter extends BaseAdapter {

	private ViewHolder holder;
	private List<HosDoctorVo> list;
	private Context context;

	public HosDoctorAdapter(Context context, List<HosDoctorVo> list) {
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.doctor_detail_listview_item, null);
			holder.img = (CLRoundImageView) convertView.findViewById(R.id.img_pic);
			holder.doctor_name = (TextView) convertView.findViewById(R.id.doctor_name);
			holder.text_department = (TextView) convertView.findViewById(R.id.text_department);
			holder.doctor_profile = (TextView) convertView.findViewById(R.id.doctor_profile);
			holder.doctor_expert = (TextView) convertView.findViewById(R.id.doctor_expert);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.doctor_name.setText(list.get(position).getName());
		ImageLoader.getInstance().displayImage(Constants.IMAGE_STR + list.get(position).getPicture(), holder.img);
		holder.text_department.setText(list.get(position).getDep_name());
		holder.doctor_profile.setText(list.get(position).getHos_name());
		holder.doctor_expert.setText(list.get(position).getRemarks());
		
		return convertView;
	}

	
	private static class ViewHolder {
		CLRoundImageView img;
		TextView doctor_name;
		TextView text_department;
		TextView doctor_profile;
		TextView doctor_expert;
	}

}
