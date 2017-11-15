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
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineCsVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineListVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineXdVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineYxVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 检查项目详细
 * 
 * @author mengjiyong
 * 
 */
public class ItemCheckDetailActivity extends BaseBackActivity {

	private ItemCheckDetailActivity context = ItemCheckDetailActivity.this;

	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;

	private ExamineListVo examineListVo;

	private String tj_id;

	/** 检查描述 */
	@ViewInject(R.id.tv_check_des)
	private TextView tv_check_des;

	@ViewInject(R.id.tv_check_result)
	private TextView tv_check_result;

	@ViewInject(R.id.tv_des)
	private TextView tv_des;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_normal_detail);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		saveBtn.setVisibility(View.INVISIBLE);
		examineListVo = (ExamineListVo) getIntent().getSerializableExtra("CheckItem");
		tj_id = getIntent().getStringExtra("tj_id");

		addreTitle.setText(examineListVo.getProjectName() + "详细");
		String projectId = examineListVo.getProjectId();

		if ("-1".equals(projectId)) { // 心电
			tv_des.setVisibility(View.GONE);
			tv_check_des.setVisibility(View.GONE);
			queryXdtRes(tj_id);
		} else if ("-2".equals(projectId)) {// 超声
			queryCsRes(tj_id);
		} else if ("-3".equals(projectId)) {// 影象
			queryYxRes(tj_id);
		}
	}

	/**
	 * 心电查询
	 */
	private void queryXdtRes(String tj_id) {

		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/hsptapp/ptin/lktjxdt/2009.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", "2");
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						List<ExamineXdVo> examineXdVos = new ArrayList<ExamineXdVo>();
						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								examineXdVos = JSONArray.parseArray(result, ExamineXdVo.class);
								if (examineXdVos.size() > 0) {
									String temp = examineXdVos.get(0).getXdtDoc() == null ? "暂无"
											: examineXdVos.get(0).getXdtDoc();
									tv_check_result.setText(examineXdVos.get(0).getXdtRes() + "\n\n检查医生：" + temp);

								} else {

									tv_check_result.setText("暂无心电图数据");
								}

							} else {
								tv_check_result.setText("暂无心电图数据");
							}

						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询心电图数据失败");

						}

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询心电图数据失败");
					}
				});

	}

	/**
	 * 超声
	 */
	private void queryCsRes(String tj_id) {

		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/hsptapp/ptin/lktjcs/200A.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", "2");
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						List<Object> examineCsVos = new ArrayList<Object>();
						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								org.json.JSONArray array = new org.json.JSONArray(result);
								for (int i = 0; i < array.length(); i++) {

									ExamineCsVo csVo = com.alibaba.fastjson.JSONObject.parseObject(array.getString(i),
											ExamineCsVo.class);
									examineCsVos.add(csVo);
								}
								initYxView(examineCsVos);

							}

						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询超声数据失败");

						}

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询超声数据失败");
					}
				});

	}

	/**
	 * 影像
	 * 
	 * @param tj_id
	 */

	private void queryYxRes(String tj_id) {

		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/hsptapp/ptin/lktjyx/200B.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", "2");
		params.put("uid", tj_id);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						List<Object> examineYxVos = new ArrayList<Object>();
						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								org.json.JSONArray array = new org.json.JSONArray(result);
								for (int i = 0; i < array.length(); i++) {

									ExamineYxVo yxVo = com.alibaba.fastjson.JSONObject.parseObject(array.getString(i),
											ExamineYxVo.class);
									examineYxVos.add(yxVo);
								}
								initYxView(examineYxVos);

							}

						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询影像数据失败");

						}

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询影像数据失败");
					}
				});
	}

	private void initYxView(List<Object> list) {

		String code = "";
		String temp = "";
		String res = "";
		for (Object obj : list) {
			if (list.get(0) instanceof ExamineYxVo) {
				ExamineYxVo vo = (ExamineYxVo) obj;
				temp = vo.getYxDoc() == null ? "暂无" : vo.getYxDoc();
				res = vo.getYxRes();
				code = vo.getYxCode();
			} else if (list.get(0) instanceof ExamineCsVo) {
				ExamineCsVo vo = (ExamineCsVo) obj;
				temp = vo.getYxDoc() == null ? "暂无" : vo.getYxDoc();
				res = vo.getYxRes();
				code = vo.getCsCode();
			}
			res = res.replaceAll("<br />", "");
			if ("000003".equals(code)) {// 体检结论
				tv_check_des.setText(res + "\n\n检查医生：" + temp);
			} else if ("000004".equals(code)) {// 检查描述
				tv_check_result.setText(res + "\n\n检查医生：" + temp);
			}
		}
	}

}
