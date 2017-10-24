package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.HosIncomeClassifyAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientMedicalRecordVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PatientMedicalRecordActivity extends BaseBackActivity{

	private PatientMedicalRecordActivity context = PatientMedicalRecordActivity.this;
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
	
	
	private HosIncomeClassifyAdapter incomeAdapter;
	
	private PatientInfoVo patientInfoVo;
	
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
		Utils.backClick(context, backBtn);
		
		addreTitle.setText("病历信息");
		patientInfoVo = (PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		lay_right.setVisibility(View.INVISIBLE);
		queryReportClassify();
	}
	
	private void initEvent() {
		lv_income.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent in = new Intent(context, patientMedicalRecordDetailActivity.class);
				in.putExtra("ListId", incomeAdapter.getList().get(arg2).getId());
				startActivity(in);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				
			}
		});
	}
	private void queryReportClassify(){
		//http://localhost:8080/hsptapp/doctor/emr/lsemrsuptype/601.html?hid=2
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/emr/lsemrsuptype/601.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<PatientMedicalRecordVo> vosTemp = JSONArray.parseArray(result, PatientMedicalRecordVo.class);
						incomeAdapter = new HosIncomeClassifyAdapter(context, vosTemp);
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
	
}
