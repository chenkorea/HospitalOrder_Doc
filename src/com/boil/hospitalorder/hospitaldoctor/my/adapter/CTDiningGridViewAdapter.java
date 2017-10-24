package com.boil.hospitalorder.hospitaldoctor.my.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.modal.StaffDiningVo;
import com.boil.hospitalorder.hospitaldoctor.my.spar.StaffDiningOrderActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CTDiningGridViewAdapter extends BaseAdapter {
	private List<StaffDiningVo> vos;

	private LayoutInflater mLayoutInflater;
	private Context context;
	private Typeface iconFont;

	public CTDiningGridViewAdapter(List<StaffDiningVo> vos, Context context, Typeface iconFont) {
		mLayoutInflater = LayoutInflater.from(context);
	    this.vos = vos;
	    this.context = context;
	    this.iconFont = iconFont;
	}
	public CTDiningGridViewAdapter(Context context, Typeface iconFont) {
		super();
		mLayoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.iconFont = iconFont;
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
	
	public List<StaffDiningVo> getVos() {
		return vos;
	}

	public void setVos(List<StaffDiningVo> vos) {
		this.vos = vos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGridViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.griditem_dining_new,
					parent, false);
			viewHolder.iv_dining = (ImageView) convertView.findViewById(R.id.iv_dining);
			viewHolder.tv_dining_name = (TextView) convertView
					.findViewById(R.id.tv_dining_name);
			viewHolder.tv_dining_price = (TextView) convertView
					.findViewById(R.id.tv_dining_price);
			viewHolder.iv_add = (TextView) convertView
					.findViewById(R.id.iv_add);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyGridViewHolder) convertView.getTag();
		}
		viewHolder.tv_dining_name.setText(vos.get(position).getDishName());
		viewHolder.tv_dining_price.setText("¥" + vos.get(position).getDishPrice());
		viewHolder.iv_add.setTypeface(iconFont);
		ImageLoader.getInstance().displayImage(StaffDiningOrderActivity.IMAGE_URL+vos.get(position).getDishLogoPath(), viewHolder.iv_dining);
		return convertView;
	}
	

	private static class MyGridViewHolder {
		TextView tv_dining_price, tv_dining_name, iv_add;
		ImageView iv_dining;
	}
}
