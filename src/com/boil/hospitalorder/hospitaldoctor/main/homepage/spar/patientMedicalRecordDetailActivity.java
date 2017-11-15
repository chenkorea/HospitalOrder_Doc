package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.HosIncomeClassifyDetailAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientMedicalRecordDetailVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class patientMedicalRecordDetailActivity extends BaseBackActivity{

	private patientMedicalRecordDetailActivity context = patientMedicalRecordDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	
	@ViewInject(R.id.lay_right)
	private LinearLayout lay_right;
	
	@ViewInject(R.id.lv_income)
	private ListView lv_income;
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private HosIncomeClassifyDetailAdapter incomeAdapter;
	
	//就诊人选择窗
	private AlertDialog contactDialog;
	
	private String stype = "";
	
	private LoginInfoVo infoVo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_income_main_stuation);
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		initView();
		initEvent();
	}
	
	private void initView() {
		stype = getIntent().getStringExtra("ListId");
		Utils.backClick(context, backBtn);
		addreTitle.setText("病历列表");
		lay_right.setVisibility(View.VISIBLE);
		remoteLogin(false);
	}
	
	private void initEvent() {
		lv_income.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				if(in != null) {
//					startActivity(in);
//					overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//				}
				
			}
		});
		
		lay_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(infoVo == null) {
					remoteLogin(true);
				} else {
					showContactDialog(true);
				}
			}
		});
	}
	
	/**
	 * 
	 * 显示选择科室对话框
	 * 
	 */
	private void showContactDialog(boolean isShow) {
		
		
		if (contactDialog == null) {
			// 显示民族列表的对话框
			contactDialog = new AlertDialog.Builder(context).create();
			contactDialog.show();
			
			Window window = contactDialog.getWindow();
			window.setContentView(R.layout.contact_list_view);
			TextView dTitle = (TextView) window.findViewById(R.id.tv_title);
			ListView dListView = (ListView) window.findViewById(R.id.list_deps);
			
			dTitle.setText("请选择科室");
			
			dListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					queryReportClassify(infoVo.getDepts().get(position).getHisId());
					// 关闭对话框
					contactDialog.dismiss();
				}
			});
			
			if (dListView.getAdapter() == null) {
				dListView.setAdapter(new NationAdapter());
			}
			if(!isShow) {
				contactDialog.dismiss();
			}
		} else {
			if(!isShow) {
				contactDialog.dismiss();
			} else {
				contactDialog.show();
			}
		}
	}
	
	/**
	 * 
	 * 就诊人 Adapter。
	 * 
	 * @author ct
	 * @date 2017-01-22
	 *
	 */
	private class NationAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return infoVo.getDepts().size();
		}

		@Override
		public Object getItem(int position) {
			return infoVo.getDepts().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.nation_list_view_item, null);
			}
			
			TextView tvNation = ViewHolder.get(convertView, R.id.tv_nation);
			tvNation.setText(infoVo.getDepts().get(position).getName());
			
			return convertView;
		}
	}
	
	
	private void queryReportClassify(String did){
		//http://localhost:8080/hsptapp/doctor/emr/lsemrtype/602.html?did=370&stype=1
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/doctor/emr/lsemrtype/602.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("did", did);
		params.put("stype", stype);
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<PatientMedicalRecordDetailVo> vosTemp = JSONArray.parseArray(result, PatientMedicalRecordDetailVo.class);
						incomeAdapter = new HosIncomeClassifyDetailAdapter(context, vosTemp);
						lv_income.setAdapter(incomeAdapter);
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
					}
				} catch (JSONException e) {
					LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
			}
		});
	}
	
	/**
	 * 
	 * 远程登录验证。
	 * 
	 */
	private void remoteLogin(final boolean isShowt) {
		
		//http://localhost:8080/hsptapp/doctor/medlogin/initlogin/201.html?uname=20001&pwd=123456
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medlogin/initlogin/201.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", configSP.getString(Constants.LOGIN_INFO_ID, ""));
		params.put("pwd", configSP.getString(Constants.LOGIN_INFO_PWD, ""));
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								
								infoVo=com.alibaba.fastjson.JSONObject.parseObject(result, LoginInfoVo.class);
								
								if(infoVo.getDepts().size()>0){
									showContactDialog(isShowt);
									queryReportClassify(infoVo.getDepts().get(0).getHisId());
								}else {
									LoadingUtils.showLoadMsg(loadView, "查不到科室数据");
								}
							}else {
								LoadingUtils.showLoadMsg(loadView, "用户名或密码错误");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						LoadingUtils.showLoadMsg(loadView, "查询科室数据失败");
					}
				});
		
		
	}
}
