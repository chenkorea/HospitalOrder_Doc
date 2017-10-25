package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.R.id;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.AdviceVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.CheckMessageVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.InfoItemVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientBaseInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PatientBaseInfoActivity extends BaseBackActivity{


	/** 当前 Activity 的上下文 */
	private PatientBaseInfoActivity context = PatientBaseInfoActivity.this;
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
	@ViewInject(R.id.tv_tips)
	private TextView tv_tips;
	@ViewInject(R.id.listview1)
	private ListView listView1;
	@ViewInject(R.id.listview2)
	private ListView listView2;
	@ViewInject(R.id.lay_base_info)
	private ScrollView scrollView;
	
	private PatientInfoVo infoVo;
	private PatientBaseInfoVo baseInfoVo;
	private List<InfoItemVo> itemVos=new ArrayList<InfoItemVo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_patient_base_info);

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
		
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		queryDetail();
		
	}
	
	
	private void setData() {
		
		if(baseInfoVo!=null){
			
			itemVos.add(new InfoItemVo("姓名", baseInfoVo.getName()));
			itemVos.add(new InfoItemVo("身份证", baseInfoVo.getIdnumber()));
			itemVos.add(new InfoItemVo("联系人姓名", baseInfoVo.getContact()));
			itemVos.add(new InfoItemVo("联系人电话", baseInfoVo.getCellphone()));
			itemVos.add(new InfoItemVo("收费类型", baseInfoVo.getInsurancetype()));
			itemVos.add(new InfoItemVo("就诊号", baseInfoVo.getAdmno()));
			itemVos.add(new InfoItemVo("住院号", baseInfoVo.getMedicare()));
			itemVos.add(new InfoItemVo("登记号", baseInfoVo.getRegister()));
			itemVos.add(new InfoItemVo("床位号", baseInfoVo.getBedno()));
			if("A".equals(baseInfoVo.getState())){
				
				itemVos.add(new InfoItemVo("在院状态", "在院"));
			}else {
				itemVos.add(new InfoItemVo("在院状态", "出院"));
				
			}
			
			scrollView.setVisibility(View.VISIBLE);
			
			NationAdapter adapter = new NationAdapter();
			listView1.setAdapter(adapter);
			
			
			if(baseInfoVo.getDiagnos().size()>0){
				
				
				CheckInfoAdapter adapter2=new CheckInfoAdapter(context, baseInfoVo.getDiagnos());
				listView2.setAdapter(adapter2);
				
			}else {
				
				tv_tips.setVisibility(View.VISIBLE);
			}
			
			
			
		}else {
			
			scrollView.setVisibility(View.GONE);
			LoadingUtils.showLoadMsg(loadView, "暂无详细数据");
		}
		
	}
	
	private void queryDetail(){
		
		//http://localhost:8080/hsptapp/doctor/lisres/lkpatinfodetail/108.html?hid=2&admId=359201
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/hsptapp/doctor/lisres/lkpatinfodetail/108.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("admId",infoVo.getAdmId());

		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								
								baseInfoVo=com.alibaba.fastjson.JSONObject.parseObject(result, PatientBaseInfoVo.class);
								setData();

							} else {
								scrollView.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无详细数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							scrollView.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询详细数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						
						scrollView.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询详细数据失败");
					}
				});
		
		
	
	
	}
	
	private class CheckInfoAdapter extends BaseAdapter{

		private Context context;
		private List<CheckMessageVo> list;
		
		public CheckInfoAdapter(Context context, List<CheckMessageVo> list) {
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
				convertView=LayoutInflater.from(context).inflate(R.layout.layout_patient_base_info_item, null);
			}
			
			TextView title=ViewHolder.get(convertView, R.id.tv_title);
			TextView date=ViewHolder.get(convertView, R.id.tv_date);
			TextView statue=ViewHolder.get(convertView, R.id.tv_statue);
			TextView icd=ViewHolder.get(convertView, R.id.tv_icd_desc);
			
			title.setText(list.get(arg0).getDesc());
			icd.setText(list.get(arg0).getIcddxDesc());
			date.setText(list.get(arg0).getCreateTime());
			
			statue.setVisibility(View.VISIBLE);
			if("1".equals(list.get(arg0).getStatus())){
				
				statue.setText("待诊");
			}else if ("2".equals(list.get(arg0).getStatus())) {
				statue.setText("疑诊");
				
			}else if ("3".equals(list.get(arg0).getStatus())) {
				statue.setText("确诊");
				
			}else {
				statue.setVisibility(View.GONE);
			}
			
			return convertView;
		}

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
