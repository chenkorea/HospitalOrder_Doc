package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.PatientRecorderAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.RecorderVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 历史就诊记录
 * @author mengjiyong
 *
 */
public class PatientRecorderActivity extends BaseBackActivity{

	/** 当前 Activity 的上下文 */
	private PatientRecorderActivity context = PatientRecorderActivity.this;
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
	@ViewInject(R.id.list_recorder)
	private ListView listRecorder;
	
	private PatientInfoVo infoVo;
	
	private List<RecorderVo> vos=new ArrayList<RecorderVo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_recorder);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
		
		queryRecorder();
	}

	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("就诊记录");
		saveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
	}

	private void initData() {
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
	}

	private void initEvent() {
		listRecorder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				queryPatientInfo(vos.get(arg2).getAdmNo());
			}
		});
	}
	
	private void queryPatientInfo(String admId){
		//http://localhost:8080/hsptapp/doctor/lisres/lkpatadminfo/110.html?hid=2&admid=361801
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/lisres/lkpatadminfo/110.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("admid", admId);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {
								
								String result = response.getString("t");
								infoVo = JSONArray.parseArray("[" + result + "]", PatientInfoVo.class).get(0);
								
								JSONObject obj = new JSONObject(result);
								JSONObject docObject=obj.getJSONObject("doc");
								
								String doc_hisId="";
								if(docObject.has("hisId")){
									
									doc_hisId=docObject.getString("hisId");
								}
								String doc_name="";
								if(docObject.has("name")){
									doc_name=docObject.getString("name");
								}
								String doc_payway=docObject.getString("payway");
								infoVo.setDoc_hisId(doc_hisId);
								infoVo.setDoc_name(doc_name);
								infoVo.setDoc_payway(doc_payway);
								
								PatientInfoDetailActivity.infoVo = infoVo;
								finish();
							} 
							
						} catch (JSONException e) {
							e.printStackTrace();
							Utils.showToastBGNew(context, "查询病人信息失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询病人信息失败");
					}
				});
	}
	
	private void queryRecorder(){

		// http://localhost:8080/hsptapp/doctor/lisres/lsadmbypapmi/104.html
		String url = "http://58.42.232.110:8086/hsptapp/doctor/lisres/lsadmbypapmi/104.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("papid", infoVo.getHisId());
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								vos=JSONArray.parseArray(result, RecorderVo.class);
								if(vos.size()>0){
									
									listRecorder.setVisibility(View.VISIBLE);
									PatientRecorderAdapter adapter=new PatientRecorderAdapter(context, vos, "");
									listRecorder.setAdapter(adapter);
									
								}else {
									listRecorder.setVisibility(View.GONE);
									LoadingUtils.showLoadMsg(loadView, "暂无就诊记录数据");
								}

							} else {
								listRecorder.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无就诊记录数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							listRecorder.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						listRecorder.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				});
	}
}
