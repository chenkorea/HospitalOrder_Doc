package com.boil.hospitalorder.hospitaldoctor.my.adapter;

import java.io.Serializable;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodOrderVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.StaffDiningVo;
import com.boil.hospitalorder.hospitaldoctor.my.spar.MyFoodOrderDetailActivity;
import com.boil.hospitalorder.hospitaldoctor.my.spar.StaffDiningOrderActivity;
import com.boil.hospitalorder.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodOrderAdapter extends BaseAdapter{

	private Activity context;
	private List<FoodOrderVo> list;
	
	
	
	public FoodOrderAdapter(Activity context, List<FoodOrderVo> list) {
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
			
			convertView=LayoutInflater.from(context).inflate(R.layout.layout_food_order_item, null);
		}
		
		TextView canteen=ViewHolder.get(convertView, R.id.tv_f_order_canteen);
		TextView date=ViewHolder.get(convertView, R.id.tv_f_order_date);
		ImageView image=ViewHolder.get(convertView, R.id.iv_f_order_image);
		TextView dish=ViewHolder.get(convertView, R.id.tv_f_order_dish);
		TextView status=ViewHolder.get(convertView, R.id.tv_f_order_status);
		TextView price=ViewHolder.get(convertView, R.id.tv_f_order_price);
		TextView time=ViewHolder.get(convertView, R.id.tv_f_order_time);
		
		FoodOrderVo foodOrderVo=list.get(position);
		List<StaffDiningVo> vos=foodOrderVo.getCookbookCountVos();
		canteen.setText(foodOrderVo.getHospitalName()+foodOrderVo.getCanteenName());
		date.setText(foodOrderVo.getCreateTime());
		ImageLoader.getInstance().displayImage(StaffDiningOrderActivity.IMAGE_URL+vos.get(0).getDishLogoPath(), image);
		
		StringBuffer sb=new StringBuffer();
		int num=0;
		for(StaffDiningVo vo:vos){
			
			sb.append(vo.getDishName());
			if(vos.indexOf(vo)<vos.size()-1){
				
				sb.append("-");
			}
			
			num=num+Integer.valueOf(vo.getDishCount());
			
		}
		dish.setText(sb.toString());
		
		
		String orderStatusId=foodOrderVo.getOrderStatusId();
		String orderStatus=foodOrderVo.getOrderStatus();
		GradientDrawable gd = (GradientDrawable) status.getBackground();
		if("103".equals(orderStatusId)){
			gd.setColor(context.getResources().getColor(R.color.order_status_103));
			status.setText(orderStatus);
			
			time.setText("预计"+foodOrderVo.getExpectSendTime()+"送达");
			time.setTextColor(context.getResources().getColor(R.color.order_status_103));
		}else if ("104".equals(orderStatusId)) {
			status.setText(orderStatus);
			gd.setColor(context.getResources().getColor(R.color.order_status_104));
			
			time.setText("预计"+foodOrderVo.getExpectSendTime()+"送达");
			time.setTextColor(context.getResources().getColor(R.color.order_status_104));
		}else if ("105".equals(orderStatusId)) {
			status.setText(orderStatus);
			gd.setColor(context.getResources().getColor(R.color.order_status_105));
			
			time.setText("预计"+foodOrderVo.getExpectSendTime()+"送达");
			time.setTextColor(context.getResources().getColor(R.color.order_status_105));
		}else if ("106".equals(orderStatusId)) {
			status.setText(orderStatus);
			gd.setColor(context.getResources().getColor(R.color.order_status_106));
			
			time.setText("已送达");
			time.setTextColor(context.getResources().getColor(R.color.order_status_106));
		}else if ("107".equals(orderStatusId)) {
			status.setText(orderStatus);
			gd.setColor(context.getResources().getColor(R.color.order_status_107));
			
			time.setText("");
		}else if ("108".equals(orderStatusId)) {
			status.setText(orderStatus);
			gd.setColor(context.getResources().getColor(R.color.order_status_108));
			
			time.setText("您未接餐");
			time.setTextColor(context.getResources().getColor(R.color.order_status_108));
		}
		
		
		String str=String.format("共%s份外卖　实付款：￥%s", num,foodOrderVo.getOrderPrice());
		price.setText(str);
		
		convertView.setOnClickListener(new MyListener(position));
		
		return convertView;
	}
	
	class MyListener implements View.OnClickListener{
		private int position;
		
		public MyListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if(position <= list.size() -1) {
				Intent intent=new Intent(context,MyFoodOrderDetailActivity.class);
				intent.putExtra("FoodOrderVo", (Serializable)list.get(position));
				context.startActivity(intent);
				context.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);			
			}
		}
	}

}
