package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.DoctorAdviceAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.AdviceDetailVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.AdviceVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.InfoItemVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AdviceDetailActivity extends BaseBackActivity{


	/** 当前 Activity 的上下文 */
	private AdviceDetailActivity context = AdviceDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.list_info)
	private ListView listInfo;
	
	private AdviceVo adviceVo;
	private AdviceDetailVo detailVo;
	private List<InfoItemVo> itemVos=new ArrayList<InfoItemVo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_idcard_detail);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		
	}

	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("详细信息");
		saveBtn.setVisibility(View.INVISIBLE);
		 
		volleyClient.setActivity(context);
	}

	private void initData() {
		
		adviceVo= (AdviceVo) getIntent().getSerializableExtra("AdviceVo");
		queryDetail();
		
	}
	
	
	private void setData() {
		
		if(detailVo!=null){
			
			itemVos.add(new InfoItemVo("名称", detailVo.getOrderName()));
			itemVos.add(new InfoItemVo("备注", detailVo.getOrderNotes()));
			itemVos.add(new InfoItemVo("类型", detailVo.getOrderType()));
			itemVos.add(new InfoItemVo("频次", detailVo.getOrderFreg()));
			itemVos.add(new InfoItemVo("状态", detailVo.getOrderSta()));
			itemVos.add(new InfoItemVo("用法", detailVo.getOrderInstruc()));
			itemVos.add(new InfoItemVo("开单科室", detailVo.getResDept()));
			itemVos.add(new InfoItemVo("开单医生", detailVo.getOrderDoc()));
			itemVos.add(new InfoItemVo("接收科室", detailVo.getRecDept()));
			itemVos.add(new InfoItemVo("开嘱日期", detailVo.getOrderDate()));
			itemVos.add(new InfoItemVo("开嘱时间", detailVo.getOrderTime()));
			itemVos.add(new InfoItemVo("停止日期", detailVo.getOrderXDate()));
			itemVos.add(new InfoItemVo("停止时间", detailVo.getOrderXTime()));
			itemVos.add(new InfoItemVo("记账状态", detailVo.getOrderBill()));
			listInfo.setVisibility(View.VISIBLE);
			
			NationAdapter adapter = new NationAdapter();
			listInfo.setAdapter(adapter);
		}else {
			
			listInfo.setVisibility(View.GONE);
			LoadingUtils.showLoadMsg(loadView, "暂无详细数据");
		}
		
	}
	
	private void queryDetail(){
		

		
		//http://58.42.232.110:8086/hsptapp/doctor/lisres/lkpatorderdetail/109.html?hid=2&oid=354987||27
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/lisres/lkpatorderdetail/109.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("oid",adviceVo.getOrderId());

		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								detailVo=com.alibaba.fastjson.JSONObject.parseObject(result, AdviceDetailVo.class);
								
									
								setData();
								

							} else {
								listInfo.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无详细数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							listInfo.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询详细数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						
						listInfo.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询详细数据失败");
					}
				});
		
		
	
	
	}
	
	

	private class NationAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return itemVos.size();
		}

		@Override
		public Object getItem(int position) {
			return itemVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.layout_salary_item, null);
			}
			
			TextView name = ViewHolder.get(convertView, R.id.tv_salary_item);
			TextView value = ViewHolder.get(convertView, R.id.tv_salary_value);
			
			name.setText(itemVos.get(position).getName());
			value.setText(itemVos.get(position).getValue());
			
			return convertView;
		}
	}
	

}
