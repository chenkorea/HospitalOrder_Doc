package com.boil.hospitalorder.hospitaldoctor.my.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.modal.StaffDiningVo;
import com.boil.hospitalorder.hospitaldoctor.my.spar.StaffDiningOrderActivity;
import com.boil.hospitalorder.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodOrderDetailAdapter extends BaseAdapter{

	private Context context;
	private List<StaffDiningVo> list;
	
	
	public FoodOrderDetailAdapter(Context context, List<StaffDiningVo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			
			convertView=LayoutInflater.from(context).inflate(R.layout.layout_food_order_detail_item, null);
		}
		
		ImageView image=ViewHolder.get(convertView, R.id.iv_order_detail_img);
		TextView desc=ViewHolder.get(convertView, R.id.tv_order_detail_desc);
		TextView price=ViewHolder.get(convertView, R.id.tv_order_detail_price);
		TextView num=ViewHolder.get(convertView, R.id.tv_order_detail_num);
		TextView remark=ViewHolder.get(convertView, R.id.tv_order_detail_remark);
		
		StaffDiningVo vo=list.get(position);
		ImageLoader.getInstance().displayImage(StaffDiningOrderActivity.IMAGE_URL+vo.getDishLogoPath(), image);
		desc.setText(vo.getDishName()+" "+vo.getDishDesc());
		price.setText("￥"+vo.getDishPrice());
		num.setText(String.format("共%s份", vo.getDishCount()));
		if(!"\\@".equals(vo.getDishRemark())){
			remark.setVisibility(View.VISIBLE);
			remark.setText(vo.getDishRemark());
		}else {
			remark.setVisibility(View.GONE);
		}
		return convertView;
	}

	
	
}
