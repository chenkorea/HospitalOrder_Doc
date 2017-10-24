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
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.CheckResultAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.CheckResultVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
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

/**
 * 检查结果
 * 
 * @author mengjiyong
 *
 */
public class PatientCheckActivity extends BaseBackActivity {
	/** 当前 Activity 的上下文 */
	private PatientCheckActivity context = PatientCheckActivity.this;
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
	private ListView listCheck;

	private PatientInfoVo infoVo;
	private List<CheckResultVo> checkVos=new ArrayList<CheckResultVo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_patient_check_result);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
		
		queryCheck();
	}

	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("检查结果");
		saveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
	}

	private void initData() {
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
	}

	private void initEvent() {

		listCheck.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(context, CheckResultDetailActivity.class);
				intent.putExtra("tid", checkVos.get(arg2).getReportId());
				intent.putExtra("item", checkVos.get(arg2).getReportType());
				intent.putExtra("rtype", checkVos.get(arg2).getResType());
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}

	/**
	 * 查询检查结果
	 */
	private void queryCheck() {

		// http://localhost:8080/hsptapp/doctor/lisres/querypacslist/106.html
		String url = "http://58.42.232.110:8086/hsptapp/doctor/lisres/querypacslist/106.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("papid", infoVo.getHisId());
		params.put("patno", infoVo.getRegister());

		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								checkVos=JSONArray.parseArray(result, CheckResultVo.class);
								if(checkVos.size()>0){
									
									listCheck.setVisibility(View.VISIBLE);
									
									CheckResultAdapter adapter=new CheckResultAdapter(checkVos);
									listCheck.setAdapter(adapter);
									
								}else {
									listCheck.setVisibility(View.GONE);
									LoadingUtils.showLoadMsg(loadView, "暂无检查数据");
								}

							} else {
								listCheck.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无检查数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							listCheck.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						listCheck.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				});

	}
}
