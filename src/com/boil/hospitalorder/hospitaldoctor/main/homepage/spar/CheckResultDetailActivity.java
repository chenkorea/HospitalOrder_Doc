package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * 检查结果详细 Activity。
 * 
 * @author ChenYong
 * @date 2016-03-01
 * 
 */
public class CheckResultDetailActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private CheckResultDetailActivity context = CheckResultDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView tvTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 主界面 ScrollView */
	@ViewInject(R.id.main_view)
	private ScrollView mainView;
//	/** 医生 TextView */
//	@ViewInject(R.id.tv_check_doctor)
//	private TextView tvCheckDoctor;
	/** 检查描述 TextView */
	@ViewInject(R.id.tv_check_descr)
	private TextView tvCheckDescr;
	/** 检查结论 */
	@ViewInject(R.id.tv_check_concl)
	private TextView tvCheckResult;
	
	
	/**检查项*/
	private String item;
	
	private String tid;
	
	private String rtype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_result_detail_view);

		// 开启注解
		ViewUtils.inject(context);

		initData();
		initView();
	}

	/**
	 * 
	 * 初始化数据。
	 * 
	 */
	private void initData() {
		Intent intent = getIntent();
		item=intent.getStringExtra("item");
		tid=intent.getStringExtra("tid");
		rtype=intent.getStringExtra("rtype");
		getResultDetail();
	}

	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		// 点击返回上一个 Activity
		Utils.backClick(this, backBtn);
		// 设置标题
		tvTitle.setText(item);
		// 隐藏保存按钮
		saveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
		
		
	}

	// 设置检查描述和检查结论
	private void setData(String des, String result) {
		tvCheckDescr.setText(des);
		tvCheckResult.setText(result);
	}


	
	private void getResultDetail() {
		
		//http://localhost:8080/hsptapp/doctor/lisres/querypacsres/107.html
		String url = "http://58.42.232.110:8086/hsptapp/doctor/lisres/querypacsres/107.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("tid", tid);
		params.put("rtype", rtype);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								
								if(!result.isEmpty()){
									
									JSONArray array=new JSONArray(result);
									
									JSONObject object=array.getJSONObject(0);
									String eyesee=object.getString("eyesee");
									String checkResult=object.getString("result");
									
									setData(eyesee, checkResult);
									
									mainView.setVisibility(View.VISIBLE);
								}else {
									setData("暂无描述数据", "暂无结论数据");
									
								}
								
							} else{
								setData("暂无描述数据", "暂无结论数据");
							}
							

						} catch (JSONException e) {

							e.printStackTrace();
							setData("暂无描述数据", "暂无结论数据");
						}
						
						

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询检查结果数据失败");
					}
				});
		
		
	}

	
}