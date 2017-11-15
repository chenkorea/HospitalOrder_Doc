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
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.VerifyResultListAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.VerifyResultVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VerifyResultActivity extends BaseBackActivity{

	
	/** 当前 Activity 的上下文 */
	private VerifyResultActivity context = VerifyResultActivity.this;
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
	@ViewInject(R.id.lv_verify_result)
	private ListView listVerify;
	
	protected List<VerifyResultVo> vrVos=new ArrayList<VerifyResultVo>();
	
	private PatientInfoVo infoVo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.verify_result_list_view);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
		
		queryLisList();
	}


	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("检验结果");
		saveBtn.setVisibility(View.INVISIBLE);
		volleyClient.setActivity(context);
	}


	private void initData() {
		
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		
		
	}


	private void initEvent() {
		
		
		listVerify.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				VerifyResultVo vrVo = vrVos.get(arg2);
				
				Intent intent = new Intent(context, VerifyResultDetailActivity.class);
				intent.putExtra("lisId", vrVo.getLisId() + "");
				intent.putExtra("orderId", vrVo.getOrderId() + "");
				intent.putExtra("orderName", vrVo.getOrderName() + "");
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	
	/**
	 * 
	 * 检验结果
	 * 
	 */
	private void queryLisList() {
		
		//http://localhost:8080/hsptapp/doctor/lisres/lslis/102.html
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/lisres/lslis/102.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("patno", infoVo.getRegister());
		params.put("date", "^");
		

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								
								vrVos=JSONArray.parseArray(result, VerifyResultVo.class);
								
								if(vrVos.size()>0){
									
									VerifyResultListAdapter adapter=new VerifyResultListAdapter(vrVos);
									listVerify.setAdapter(adapter);
									
								}else {
									listVerify.setVisibility(View.GONE);
									LoadingUtils.showLoadMsg(loadView, "暂无检验结果数据");
								}
								
							}else {
								
								listVerify.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无检验结果数据");
								
							} 
							
							
						} catch (JSONException e) {

							e.printStackTrace();
							
							listVerify.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询检验结果数据失败");
						}
						

					}

					@Override
					public void error(VolleyError error) {

						listVerify.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询检验结果数据失败");
					}
				});
		
	}
	
}
