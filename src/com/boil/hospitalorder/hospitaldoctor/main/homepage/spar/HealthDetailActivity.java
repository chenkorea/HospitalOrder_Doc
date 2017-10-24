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
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.HealthSuggestAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineChildVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineLisResVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineListVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineVo;
import com.boil.hospitalorder.utils.Constants;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * 个人体检详细。
 * 
 * @author mengjiyong
 * 
 */
public class HealthDetailActivity extends BaseBackActivity {
	/** 此 Activity 上下文 */
	private HealthDetailActivity context = HealthDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 性别 */
	@ViewInject(R.id.tv_sex)
	private TextView cardTv;
	@ViewInject(R.id.tv_ages)
	private TextView tv_age;
	/** 部门 */
	@ViewInject(R.id.tv_class)
	private TextView tv_class;
	
	@ViewInject(R.id.tv_phone)
	private TextView tv_phone;
	@ViewInject(R.id.tv_test_name)
	private TextView tv_test_name;
	
//	/** 体检结论 */
//	@ViewInject(R.id.list_result)
//	private ListView listView1;
//	/** 健康建议 */
//	@ViewInject(R.id.list_suggestion)
//	private ListView listView2;
	/**体检项目*/
	@ViewInject(R.id.list_check)
	private ListView listView3;
	
	//检查结论
	@ViewInject(R.id.tv_result)
	private TextView resultTv;
	//建议
	@ViewInject(R.id.tv_suggestion)
	private TextView sugTv;
//	//检查项目
//	@ViewInject(R.id.tv_check)
//	private TextView checkTv;
	
	List<String> checks = new ArrayList<String>();
	
	private ExamineVo examineVo;
	//检查结论
	private List<ExamineChildVo> examineChildVos=new ArrayList<ExamineChildVo>();
	//检查项目
	private List<ExamineListVo> examineListVos=new ArrayList<ExamineListVo>();
	//检查项目详细
	private List<ExamineLisResVo> vrVos=new ArrayList<ExamineLisResVo>();
	
	private List<ExamineLisResVo> tempVrVos=new ArrayList<ExamineLisResVo>();
	
	@ViewInject(R.id.tv_tips)
	private TextView tv_tips;
	@ViewInject(R.id.tv_tips1)
	private TextView tv_tips1;
	@ViewInject(R.id.tv_tips2)
	private TextView tv_tips2;
	@ViewInject(R.id.main_view)
	private ScrollView mainView;
	
	public static HealthDetailActivity healthDetailActivity;
	
	private String hid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_detail);
		// 开启注解
		ViewUtils.inject(HealthDetailActivity.this);
		healthDetailActivity=this;
		initView();
		initEvent();
	}

	private void initEvent() {
		
		

		listView3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				ExamineListVo itemVo = examineListVos.get(position);
				Intent intent = null;
				
				if (position < 3) {
					intent = new Intent(HealthDetailActivity.this, ItemCheckDetailActivity.class);
				} else {
					intent = new Intent(HealthDetailActivity.this, ItemListDetailActivity.class);
					
					
					//根据项目id筛选 
					tempVrVos.clear();
					for(ExamineLisResVo vo:vrVos){
						
						if(itemVo.getProjectId().equals(vo.getProjectId())){
							tempVrVos.add(vo);
						}
						
					}
					
				}
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("CheckItem", itemVo);
				bundle.putString("tj_id", examineVo.getTjId());
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	
	

	public List<ExamineLisResVo> getTempVrVos() {
		return tempVrVos;
	}
	

	private void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("详细体检报告");
		saveBtn.setVisibility(View.INVISIBLE);
		volleyClient.setActivity(context);
		initData();
	}

	private void initData() {

		hid=configSP.getString(Constants.LOGIN_INFO_HID, "");
		examineVo = (ExamineVo) getIntent().getSerializableExtra("examineVo");
		
		queryExamList(examineVo.getTjId());

		cardTv.setText("性别：" + examineVo.getGender());
		tv_age.setText("年龄：" + examineVo.getAges());
		tv_phone.setText("电话：" + examineVo.getCellphone());
		tv_test_name.setText("姓名：" + examineVo.getUserName());
		tv_class.setText("部门："+examineVo.getTjClass());
		
		
	}

	private void showTips(){
		
		resultTv.setVisibility(View.GONE);
		sugTv.setVisibility(View.GONE);
		
		tv_tips.setVisibility(View.VISIBLE);
		tv_tips1.setVisibility(View.VISIBLE);
		tv_tips2.setVisibility(View.VISIBLE);
	}
			
	
	private void setTJRes() {
		
		
		
		String result=examineChildVos.get(0).getTjRes();
		String sug=examineChildVos.get(0).getTjSuggest();
//		String check=examineChildVos.get(0).getProjectName();
		
		
		if(!result.isEmpty()){
			result=result.replaceAll("<br />", "");
			resultTv.setText(result);
		}else {
			tv_tips.setVisibility(View.VISIBLE);
		}
		
		if (!sug.isEmpty()) {
			sug=sug.replaceAll("<br />", "");
			sugTv.setText(sug);
		} else {
			tv_tips1.setVisibility(View.VISIBLE);
		}

//		if (!check.isEmpty()) {
//			check=check.replaceAll("<br />", "");
//			checkTv.setText(check);
//		} else {
//			tv_tips2.setVisibility(View.VISIBLE);
//		}
		
		
		ExamineListVo listVo1=new ExamineListVo("","-1","心电图","");
		ExamineListVo listVo2=new ExamineListVo("","-2","超声波","");
		ExamineListVo listVo3=new ExamineListVo("","-3","影像科","");
		examineListVos.add(0,listVo1);
		examineListVos.add(1,listVo2);
		examineListVos.add(2,listVo3);
		
		checks.clear();
		for(ExamineListVo listVo:examineListVos){
			checks.add(listVo.getProjectName());
		}
		listView3.setVisibility(View.VISIBLE);
		listView3.setAdapter(new HealthSuggestAdapter(checks));	
		
		
	}
	/**
	 * 查询体检结果
	 * @param tj_id
	 */

	private void queryExamList(final String tj_id) {
		
		String url = "http://58.42.232.110:8086/hsptapp/ptin/lktjjl/2008.html";
		

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", hid);
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								
								String result=response.getString("t");
								
								examineChildVos=JSONArray.parseArray(result, ExamineChildVo.class);
								if (examineChildVos.size() == 0) {
									
									showTips();
								} else {
									
									queryCheckItemList(tj_id);
								}
								
							} else{
								showTips();
							}

						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询体检数据失败");
							
						}
						

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询体检数据失败");
					}
				});
	
	}
	
	/**
	 * 查询检查项目
	 * @param tj_id
	 */
	private void queryCheckItemList(final String tj_id){
		
		
		String url = "http://58.42.232.110:8086/hsptapp/ptin/lktjlsres/200C.html";
		

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", hid);
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								examineListVos=JSONArray.parseArray(result, ExamineListVo.class);
								
								if(examineListVos.size()>0){
									queryCheckItemDetailList(tj_id);
								}
								
							} 
							

						} catch (JSONException e) {

							e.printStackTrace();
						}
						
						setTJRes();

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询检查项目数据失败");
					}
				});
	
		
	}
	/**
	 * 一次性查询 检查项目详细
	 * @param tj_id
	 */
	
	private void queryCheckItemDetailList(String tj_id){
		
		String url = "http://58.42.232.110:8086/hsptapp/ptin/lktjlsrsd/200D.html";
		
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", hid);
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								
								String result=response.getString("t");
								
								vrVos=JSONArray.parseArray(result, ExamineLisResVo.class);
								
							}
							

						} catch (JSONException e) {

							e.printStackTrace();
						}
						

					}

					@Override
					public void error(VolleyError error) {

						
					}
				});
		
		
	}

}