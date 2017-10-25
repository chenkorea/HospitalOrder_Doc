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
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.PersonCheckAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineVo;
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

public class MyCheckActivity extends BaseBackActivity{

	
	/** 当前 Activity 的上下文 */
	private MyCheckActivity context = MyCheckActivity.this;
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
	@ViewInject(R.id.list_check)
	private ListView listView;
	private List<ExamineVo> examineVos = new ArrayList<ExamineVo>();
	
	private PersonCheckAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_check);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
		
		queryExamList();
	}


	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("我的体检");
		saveBtn.setVisibility(View.INVISIBLE);
		volleyClient.setActivity(context);
	}


	private void initData() {
		
		
		
	}


	private void initEvent() {
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Intent intent = new Intent(context, HealthDetailActivity.class);
				intent.putExtra("examineVo", examineVos.get(arg2));
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	
	/**
	 * 个人体检
	 */
	
	private void queryExamList() {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/hsptapp/ptin/lstjres/2006.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("cid", configSP.getString(Constants.USER_ID_NUMBER, ""));
		params.put("name", configSP.getString(Constants.USER_NAME, ""));
		params.put("phone", configSP.getString(Constants.USER_PHONE, ""));

		
//		params.put("hid", currHosId);
//		params.put("cid", "522224199004024015");
//		params.put("name", "汪海洋");
//		params.put("phone","");

		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						
						examineVos.clear();
						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								String result=response.getString("t");
								examineVos=JSONArray.parseArray(result, ExamineVo.class);
								
								if(examineVos.size()>0){
									
									adapter=new PersonCheckAdapter(examineVos);
									listView.setAdapter(adapter);
									
								}else {
									
									listView.setVisibility(View.GONE);
									LoadingUtils.showLoadMsg(loadView, "暂无体检数据");
								}
								
								
							}else {
								
								listView.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无体检数据");
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
							listView.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询数据失败");
						}
						
					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询数据失败");
					}
				});
	}
	
}
