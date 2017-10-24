package com.boil.hospitalorder.hospitaldoctor.main.mainiview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.NoticeAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.PatientTipsAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.HosNoticeVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientMessageTipVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.MyListView;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
import com.boil.hospitalsecond.docpatientcircle.spar.MessagePlatformActivity;
import com.boil.hospitalsecond.docpatientcircle.spar.SubscribeDoctorUserListActivity;
import com.boil.hospitalsecond.tool.ctgridview.Tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

/**
 * 查询页面
 * 
 * @author ct
 * 
 */
public class DocAndPatientCircleTabFragment extends BaseFragment {

	private RelativeLayout web_container;

	private MyListView lv_message;
	private MyListView lv_consult;

	private PatientTipsAdapter adapter;
	private NoticeAdapter adapter2;

	private RelativeLayout rel_consult;
	private RelativeLayout rel_message;
	private RelativeLayout rel_focus;

	private List<PatientMessageTipVo> messVos = new ArrayList<PatientMessageTipVo>();
	List<HosNoticeVo> noticeVos = new ArrayList<HosNoticeVo>();
	
	private List<Tag> listTgs=new ArrayList<Tag>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_doc_patient_circle,
				container, false);

		initView(view);
		initData();
		initEvent();
		
		queryTopicType();
		return view;
	}

	private void initData() {
		
		/*
		PatientMessageTipVo messVo = null;
		HosNoticeVo noVo = null;
		for (int i = 0; i < 5; i++) {
			messVo = new PatientMessageTipVo(i + "", "你好，我有个问题想要问一下。");
			messVos.add(messVo);
			noVo = new HosNoticeVo("",i + "", "","", "在吗？");
			noticeVos.add(noVo);
		}
		adapter = new PatientTipsAdapter(getActivity(), messVos);
		lv_message.setAdapter(adapter);
		adapter2 = new NoticeAdapter(getActivity(), noticeVos);
		lv_consult.setAdapter(adapter2);
		*/
	}

	private void initView(View view) {
		rel_consult = (RelativeLayout) view.findViewById(R.id.rel_consult);
		rel_message = (RelativeLayout) view.findViewById(R.id.rel_message);
		rel_focus = (RelativeLayout) view.findViewById(R.id.rel_focus);
		lv_message = (MyListView) view.findViewById(R.id.lv_message);
		lv_consult = (MyListView) view.findViewById(R.id.lv_consult);
		web_container = (RelativeLayout) view.findViewById(R.id.web_container);
		FontManager.markAsIconContainer(web_container, iconFont2);
	}

	private void initEvent() {
		rel_consult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.showToastBGNew(getActivity(), "正在开发中");
			}
		});
		rel_message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MessagePlatformActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});
		rel_focus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SubscribeDoctorUserListActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});
		
		
	}
	
	/**
	 * 
	 * 查询所有留言。
	 * 
	 * @param loadType 加载类型
	 * 
	 */
	private void queryTopicTypeList() {
		
		StringBuilder lmtids = new StringBuilder();
		
		for (int i = 0; i < listTgs.size(); i++) {
			if (i < (listTgs.size() - 1)) {
				lmtids.append(listTgs.get(i).getId());
				lmtids.append("^");
			} else {
				lmtids.append(listTgs.get(i).getId());
			}
		}
		
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/leavemsg/lsalllmc/503.html";
		
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		if (listTgs.size() > 0) {
			params.put("lmtids", lmtids.toString());
		}
		
		params.put("currentpage", "1");
		params.put("pagecount", "5");
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								String result = response.getString("t");
								List<TopicTypeVo> vosTemp = com.alibaba.fastjson.JSONArray.parseArray(result, TopicTypeVo.class);
								JSONArray jsonArray = new JSONArray(result);

								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObj = jsonArray.getJSONObject(i);
									jsonObj = jsonObj.getJSONObject("users");
									vosTemp.get(i).setUserId(jsonObj.getString("id"));

									if (jsonObj.has("username")) {
										vosTemp.get(i).setUserName(jsonObj.getString("username"));
									} else {
										vosTemp.get(i).setUserName("");
									}
								}
								
								adapter = new PatientTipsAdapter(getActivity(), vosTemp);
								lv_message.setAdapter(adapter);
								
							} 

						} catch (JSONException e) {
							
						}
					}

					@Override
					public void error(VolleyError error) {
						
					}
				});
	}
	
	/**
	 * 查询全部分类
	 */
	private void queryTopicType() {
		
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/leavemsg/lslmtype/502.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("pagecount", "-1");
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								
								String result = response.getString("t");
								JSONArray array = new JSONArray(result);
								
								listTgs.clear();
								
								for (int i = 0; i < array.length(); i++) {
									JSONObject jsonObj = array.getJSONObject(i);
									String id = jsonObj.getString("id");
									String name = jsonObj.getString("name");
									String isSub = jsonObj.getString("issub");

									// 只显示已订阅的话题
									if ("1".equals(isSub)) {
										Tag tg = new Tag(Integer.parseInt(id), name);
										tg.setChecked(true);
										listTgs.add(tg);
									}
								}
								
								queryTopicTypeList();
							}
						} catch (JSONException e) {
						}
					}

					@Override
					public void error(VolleyError error) {
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
