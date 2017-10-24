package com.boil.hospitalorder.hospitaldoctor.my.spar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodCartVo;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.NumberUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 订餐车页面
 * @author mengjiyong
 *
 */
public class FoodCartActivity extends BaseBackActivity{

	
	private FoodCartActivity context = FoodCartActivity.this;
	
	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.list_food_cart)
	private ListView listCart;
	@ViewInject(R.id.img_check)
	private CheckBox img_check;
	@ViewInject(R.id.tv_total_price)
	private TextView tv_total_price;
	@ViewInject(R.id.btn_go_pay)
	private Button btn_go_pay;
	
	private List<FoodCartVo> cartVos=new ArrayList<FoodCartVo>();
	private FoodCartAdapter cartAdapter;
	private AlertDialog payDialog;
	
	private String teenId;
	private String hid;

	protected String nameStr;

	protected String phoneStr;

	protected String addressStr;

	protected String remarkStr;
	
	private String foodIds;
	
	private String foodPrice;
	
	private String foodNum;
	
	private String sendTime;
	
	//最低送餐小时
	private int minH;
	//最低送餐分钟
	private int minM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_food_cart);
		ViewUtils.inject(this);
		initView();
		initData();
		initEvent();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("订餐车");
		bt_save.setVisibility(View.INVISIBLE);	
		volleyClient.setActivity(context);
	}

	private void initData() {
		
		teenId=getIntent().getStringExtra("teenId");
		hid=getIntent().getStringExtra("hid");
		cartVos=StaffDiningOrderActivity.cartVos;
		
		nameStr=configSP.getString("food_cart_name", "");
		phoneStr=configSP.getString("food_cart_phone", "");
		addressStr=configSP.getString("food_cart_address", "");
		
		cartAdapter=new FoodCartAdapter();
		listCart.setAdapter(cartAdapter);
		calculatePrice();
	}

	private void initEvent() {
		
		img_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				for (FoodCartVo vo : cartVos) {
					vo.setChecked(img_check.isChecked());
				}
				
				cartAdapter.notifyDataSetChanged();
				
				calculatePrice();
				
			}
		});
		
		btn_go_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isLogin()){
					
					showContactDialog();
				}else {
					toLogin();
				}
			}
		});

	}
	
	
	private int getMaxMakeTime(){
		
		FoodCartVo maxVo=cartVos.get(0);
		for(FoodCartVo vo:cartVos){
			if(Integer.valueOf(vo.getMakeTime())>Integer.valueOf(maxVo.getMakeTime())){
				maxVo=vo;
			}
		}
		
		return Integer.valueOf(maxVo.getMakeTime());
	}
	
	/**
	 * 两时间比较
	 * @param t1
	 * @param t2
	 * @return
	 */
	private int timeCompare(String t1,String t2){
		
		SimpleDateFormat df=new SimpleDateFormat("HH:mm");
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		
		try {
			c1.setTime(df.parse(t1));
			c2.setTime(df.parse(t2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int result=c1.compareTo(c2);
		
		return result;
	}
	
	/**
	 * 获取最低送餐时间
	 * @return
	 */
	private String getMinSendTime(){
		//获取当前时间
		Calendar calendar=Calendar.getInstance();
		int currHour=calendar.get(Calendar.HOUR_OF_DAY);
		int curMi=calendar.get(Calendar.MINUTE);
		//做菜最大时间
		int maxMakeTime=getMaxMakeTime();
		
		//如果大于等于60分钟，需要转换
		if(maxMakeTime>=60){
			
			int h=maxMakeTime/60;
			int m=maxMakeTime%60;
			minH=currHour+h;
			minM=curMi+m;
		}else {
			minH=currHour;
			minM=curMi+maxMakeTime;
			
			if(minM>=60){
				
				minH+=1;
				minM=minM%60;
			}
		}
		
		return minH+":"+minM;
		
	}
	
	/**
	 * 
	 * 缴费dialog
	 * 
	 */
	private void showContactDialog() {
		
		
		if (payDialog == null) {
			payDialog = new AlertDialog.Builder(context).create();
			payDialog.show();
			
		} else {
			payDialog.show();
		}
		final String minTime=getMinSendTime();
		Window window = payDialog.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		LayoutParams params = window.getAttributes();
		params.width = Utils.getScreenWidth(context)-Utils.dip2px(context, 26);
		window.setAttributes(params);
		window.setContentView(R.layout.layout_food_cart_pay);
		
		
		final EditText name=(EditText) window.findViewById(R.id.et_name);
		final EditText phone=(EditText) window.findViewById(R.id.et_phone);
		final EditText address=(EditText) window.findViewById(R.id.et_address);
		final TextView time=(TextView) window.findViewById(R.id.tv_time);
		BootstrapButton cancel=(BootstrapButton) window.findViewById(R.id.btn_cancel);
		BootstrapButton sure=(BootstrapButton) window.findViewById(R.id.btn_sure);
		
		name.setText(nameStr);
		phone.setText(phoneStr);
		address.setText(addressStr);
		time.setText(minH+"时"+minM+"分");
		
		final TimePickerDialog timePickerDialog=new TimePickerDialog(context, TimePickerDialog.THEME_HOLO_LIGHT, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
				String nowTime=hourOfDay+":"+minute;
				
				if(timeCompare(nowTime, minTime)<0){
					
					Utils.showToastBGNew(context, "不能低于最低送餐时间");
					time.setText(minH+"时"+minM+"分");
					
				}else {
					
					time.setText(hourOfDay+"时"+minute+"分");
				}
				
				
			}
			
			
		}, minH, minM, true);
		
		
		
		time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				timePickerDialog.show();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				payDialog.dismiss();
			}
		});
		
		sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nameStr = name.getText().toString();
				phoneStr = phone.getText().toString();
				addressStr = address.getText().toString();
				sendTime = time.getText().toString();

				StringBuffer ids = new StringBuffer();
				StringBuffer nums = new StringBuffer();
				StringBuffer remarks = new StringBuffer();

				for (int i = 0; i < cartVos.size(); i++) {
					FoodCartVo vo = cartVos.get(i);
					String r = vo.getRemark();

					if (StringUtils.isBlank(r)) {
						r = "@";
					}

					r = r.replace("|", "");

					if (i < (cartVos.size() - 1)) {
						ids.append(vo.getId());
						ids.append("|");

						nums.append(vo.getNum());
						nums.append("|");

						remarks.append(r);
						remarks.append("|");
					} else {
						ids.append(vo.getId());
						nums.append(vo.getNum());
						remarks.append(r);
					}
				}

				foodIds = ids.toString();
				foodNum = nums.toString();
				remarkStr = remarks.toString();

				if (TextUtils.isEmpty(nameStr)) {
					Utils.showToastBGNew(context, "请输入姓名");

					return;
				}

				if (TextUtils.isEmpty(phoneStr)) {
					Utils.showToastBGNew(context, "请输入手机号码");

					return;
				}
				
				if (TextUtils.isEmpty(addressStr)) {
					Utils.showToastBGNew(context, "请输入送餐地址");
					
					return;
				}

				Editor editor = configSP.edit();
				editor.putString("food_cart_name", nameStr);
				editor.putString("food_cart_phone", phoneStr);
				editor.putString("food_cart_address", addressStr);
				editor.commit();

				// 提交订单
				submitOrder();

				payDialog.dismiss();
			}
		});
	}
	
	private void submitOrder(){
		
		
		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/dish/order/create.json

		String url =  Constants.WEB_DINNER_URL + "/canteen/dish/order/create.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNum", "1");
		params.put("canteenId", teenId);
		params.put("hospitalId", hid);
		params.put("userId", configSP.getString(Constants.USER_ID, ""));
		params.put("dishIds", foodIds);
		params.put("dishCounts", foodNum);
		params.put("userName", nameStr);
		params.put("userTypeCode", "20");
		params.put("userPhone", phoneStr);
		params.put("orderPrice", foodPrice);
		params.put("orderAddr", addressStr);
		params.put("dishRemarks", remarkStr);
		params.put("expectSendTime", sendTime);
		

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							String resultMsg = ((response.getString("resultMsg") == null) ? "订餐失败" : response.getString("resultMsg"));
							
							if ("0".equals(resultCode)) {
								Utils.showToastBGNew(context, "订餐成功");
								cartVos.clear();
								
								finish();
							} else {
								Utils.showToastBGNew(context, resultMsg);
							}
						} catch (Exception e) {
							e.printStackTrace();
							Utils.showToastBGNew(context, "订餐失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "订餐失败");
					}
				});
	}
	
	private void calculatePrice(){
		
		// 计算总价格和数量并更新到界面

		int num = 0;
		double price = 0;
		for (FoodCartVo cartVo : cartVos) {

			if (cartVo.isChecked()) {

				num = num + Integer.valueOf(cartVo.getNum());
				price = price + Integer.valueOf(cartVo.getNum()) * Double.valueOf(cartVo.getFoodPrice());
			}
		}
		
		foodPrice=NumberUtils.formatPrice(price).replace("￥", "");
		
		String priceStr = String.format("%s", NumberUtils.formatPrice(price));
		String numStr = String.format("提交订单(%s)", num + "");

		tv_total_price.setText(priceStr);
		btn_go_pay.setText(numStr);
		
	}
	
	
	/**
	 * 是否是全选
	 */
	private void checkSelectAll(){
		
		boolean isAll=true;
		for(FoodCartVo vo:cartVos){
			
			if(!vo.isChecked()){
				img_check.setChecked(false);
				isAll=false;
				break;
			}
		}
		
		img_check.setChecked(isAll);
		
	}
	
	
	
	public class FoodCartAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			return cartVos.size();
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
				
				convertView=LayoutInflater.from(context).inflate(R.layout.layout_food_cart_item, null);
				
			}
			final CheckBox box=ViewHolder.get(convertView, R.id.img_check);
			ImageView img=ViewHolder.get(convertView, R.id.iv_cart_img);
			TextView desc=ViewHolder.get(convertView, R.id.tv_cart_desc);
			TextView price=ViewHolder.get(convertView, R.id.tv_cart_price);
			AmountView amountView=ViewHolder.get(convertView, R.id.amountView);
			EditText remark=ViewHolder.get(convertView, R.id.et_remark);
			
			final FoodCartVo vo=cartVos.get(position);
			ImageLoader.getInstance().displayImage(StaffDiningOrderActivity.IMAGE_URL+vo.getFoodImage(), img);
			desc.setText(vo.getFoodName()+"　"+vo.getFoodDesc());
			price.setText("￥"+vo.getFoodPrice());
			remark.setText(vo.getRemark());
			box.setChecked(vo.isChecked());
			box.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					vo.setChecked(box.isChecked());
					calculatePrice();
					checkSelectAll();
				}
			});
			
			
			amountView.setGoods_storage(Integer.MAX_VALUE);
			amountView.setZeroFlag(false);
			amountView.setGoods_value(vo.getNum());
			amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
				@Override
				public void onAmountChange(View view, int amount) {
					
					if(amount>0){
						
						vo.setNum(amount+"");
						calculatePrice();
					}
					
					
				}
			});
			
			remark.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					
					vo.setRemark(s.toString());
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					
				}
			});
			
			return convertView;
		}
	}
}
