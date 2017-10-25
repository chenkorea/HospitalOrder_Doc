package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.mainiview.HomePageTabFragment;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PatientInfoDetailActivity extends BaseBackActivity{

	/** 当前 Activity 的上下文 */
	private PatientInfoDetailActivity context = PatientInfoDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/**姓名*/
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	/**性别*/
	@ViewInject(R.id.tv_sex)
	private TextView tv_sex;
	/**年龄*/
	@ViewInject(R.id.tv_age)
	private TextView tv_age;
	/**住院号*/
	@ViewInject(R.id.tv_admno)
	private TextView tv_admno;
	/**登记号*/
	@ViewInject(R.id.tv_register)
	private TextView tv_register;
	/**床号*/
	@ViewInject(R.id.tv_bedno)
	private TextView tv_bedno;
	/**入院时间*/
	@ViewInject(R.id.tv_adm_date)
	private TextView tv_adm_date;
	/**在院状态*/
	@ViewInject(R.id.tv_state)
	private TextView tv_state;
	/**病人基本信息*/
	@ViewInject(R.id.ll_base_info)
	private LinearLayout ll_base_info;
	/**历史就诊记录*/
	@ViewInject(R.id.ll_recorder)
	private LinearLayout ll_recorder;
	/**医嘱信息*/
	@ViewInject(R.id.ll_yz)
	private LinearLayout ll_yz;
	/**病例信息*/
	@ViewInject(R.id.ll_bl)
	private LinearLayout ll_bl;
	/**检查结果*/
	@ViewInject(R.id.ll_check)
	private LinearLayout ll_check;
	/**检验结果*/
	@ViewInject(R.id.ll_result)
	private LinearLayout ll_result;
	/**病例质控*/
	@ViewInject(R.id.ll_control)
	private LinearLayout ll_control;
	/**危急值*/
	@ViewInject(R.id.ll_safe_value)
	private LinearLayout ll;
	/**随访病人*/
	@ViewInject(R.id.ll_sui_patient)
	private LinearLayout ll_sui_patient;
	/**随访记录*/
	@ViewInject(R.id.ll_sui_recoder)
	private LinearLayout tv_sui_recoder;
	@ViewInject(R.id.lay_all)
	private LinearLayout lay_all;
	
	public static PatientInfoVo infoVo;
	public boolean isFirst = true;
	
	private String admno = "";
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private String startDate = "";	
	private String endDate = "";	
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_patient_info_detail);

		// 开启注解
		ViewUtils.inject(context);
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		admno = getIntent().getStringExtra("admno");
		startDate = getIntent().getStringExtra("startDate");
		endDate = getIntent().getStringExtra("endDate");
		initView();
		if(infoVo != null) {
			initData();
			initEvent();
		} else {
			if(StringUtils.isNotBlank(admno)) {
				queryPatientInfo(admno);
			} else {
				LoadingUtils.showLoadMsg(loadView, "暂不能查询病人信息");
			}
		}
	}
	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("病人信息");
		saveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
		FontManager.markAsIconContainer(lay_all, iconFont2);
	}
	private void initData() {
		
		//添加最近浏览的病人 最多八个
		if(!isExist()){
			
			if(HomePageTabFragment.visits.size()<8){
				
				HomePageTabFragment.visits.add(0, infoVo);
				
			}else {
				
				HomePageTabFragment.visits.remove((HomePageTabFragment.visits.size()-1));
				HomePageTabFragment.visits.add(0, infoVo);
				
			}
			
			Editor editor=configSP.edit();
			editor.putString(Constants.LATEST_VISIT_PATIENT_JSON, com.alibaba.fastjson.JSONObject.toJSONString(HomePageTabFragment.visits));
			editor.commit();
			HomePageTabFragment.visitUpdate=1;
				
		}
		
		tv_name.setText("姓名："+infoVo.getName());
		tv_age.setText("年龄："+infoVo.getAge());
		tv_sex.setText("性别："+infoVo.getSex());
		tv_admno.setText("住院号:"+infoVo.getMedicare());
		tv_register.setText("登记号:"+infoVo.getRegister());
		tv_bedno.setText("床号:"+infoVo.getBedno());
		tv_adm_date.setText("入院时间："+infoVo.getAdmdate());
		
		if("A".equals(infoVo.getState())){
			
			tv_state.setText("在院状态：在院");
		}else {
			tv_state.setText("在院状态：出院");
		}
	}
	
	private void queryPatientInfo(String admId){
		//http://localhost:8080/hsptapp/doctor/lisres/lkpatadminfo/110.html?hid=2&admid=361801
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/lisres/lkpatadminfo/110.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("admid", admId);
		loadView.setVisibility(View.GONE);
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
								initData();
								initEvent();
							} 
							
						} catch (JSONException e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询病人信息失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						LoadingUtils.showLoadMsg(loadView, "查询病人信息失败");
					}
				});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!isFirst) {
			if(infoVo != null) {
				initData();
			}
		} else { 
			isFirst = false;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		infoVo = null;
	}
	
	private boolean isExist(){
		
		for(PatientInfoVo vo:HomePageTabFragment.visits){
			
			if(vo.getRegister().equals(infoVo.getRegister())){
				
				return true;
			}
			
		}
		
		return false;
		
	}
	
	private void initEvent() {
		
		ll_recorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(context,PatientRecorderActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//检查结果
		ll_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(context,PatientCheckActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//检验结果
		ll_result.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,VerifyResultActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//医嘱
		ll_yz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,DoctorAdviceActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		
		//病人基本信息
		ll_base_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,PatientBaseInfoActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		ll_bl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,CopyOfPatientMedicalRecordActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//病历质控
		ll_control.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, QuliatyControlPatientDetailActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)infoVo);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//危急值
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DangerPatientValueDetailActivity.class);
				if(StringUtils.isBlank(admno)) {
					admno = infoVo.getAdmId();
				}
				intent.putExtra("admno", admno);
				intent.putExtra("startDate", startDate);
				intent.putExtra("endDate", endDate);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
}
