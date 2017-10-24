package com.boil.hospitalsecond.docpatientcircle.adapter;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalsecond.docpatientcircle.model.SubscribeDoctorUserVo;
import com.boil.hospitalsecond.docpatientcircle.spar.SubscribeDoctorUserListActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * 关注医生的用户列表 Adapter。
 * 
 * @author ChenYong
 * @date 2016-12-23
 *
 */
public class SubscribeDoctorUserListAdapter extends BaseAdapter {
	private ViewHolder holder;
	private List<SubscribeDoctorUserVo> list = new ArrayList<SubscribeDoctorUserVo>();
	private SubscribeDoctorUserListActivity context;

	public SubscribeDoctorUserListAdapter(SubscribeDoctorUserListActivity context) {
		this.context = context;
	}

	public List<SubscribeDoctorUserVo> getList() {
		return list;
	}

	public void setList(List<SubscribeDoctorUserVo> list) {
		this.list = list;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.subscribe_doctor_user_list_listview_item, null);
			holder.img = (CLRoundImageView) convertView.findViewById(R.id.head);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.user_name = (TextView) convertView.findViewById(R.id.user_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Date currTime = new Date();
		
		ImageLoader.getInstance().displayImage(list.get(position).getHicon() + "?t=" + currTime.getTime(), holder.img);
		holder.tv_time.setText(list.get(position).getRegistertime());
		holder.user_name.setText(list.get(position).getUsername());

		return convertView;
	}

	private static class ViewHolder {
		TextView user_name, tv_time;
		CLRoundImageView img;
	}
}