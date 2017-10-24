package com.boil.hospitalorder.hospitaldoctor.my.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.modal.CanTeenVo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CTColligateServiceAdapter extends BaseAdapter {
	private List<CanTeenVo> vos;

	private LayoutInflater mLayoutInflater;
	private Typeface iconFont;
	private Context context;

	public CTColligateServiceAdapter(List<CanTeenVo> vos, Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	    this.vos = vos;
	    this.context = context;
	}

	@Override
	public int getCount() {
		return vos == null ? 0 : vos.size();
	}

	@Override
	public Object getItem(int position) {
		return vos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public List<CanTeenVo> getVos() {
		return vos;
	}

	public void setVos(List<CanTeenVo> vos) {
		this.vos = vos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGridViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.colligate_service_listview_item,
					parent, false);
			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyGridViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(vos.get(position).getCanteenName());
//		ImageLoader.getInstance().displayImage(url, viewHolder.imageView);
		return convertView;
	}

	private static class MyGridViewHolder {
		TextView tv_title;
	}
}
