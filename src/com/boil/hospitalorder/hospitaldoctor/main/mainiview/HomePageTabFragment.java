package com.boil.hospitalorder.hospitaldoctor.main.mainiview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.MomentScanPatientAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.NoticeAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.PatientTipsAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.HosNoticeVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.DangerValueDetailActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.PatientInfoActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.PatientInfoDetailActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.PatientPerioperativeManagerActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.QuliatyControlDetailActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.SubscribeArticleDetailActivity;
import com.boil.hospitalorder.hospitaldoctor.my.spar.ColligateServiceActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.MyListView;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;

/**
 * 查询页面
 * 
 * @author ct
 * 
 */
@SuppressLint("NewApi")
public class HomePageTabFragment extends BaseFragment {

	FindTabFragmentToActivity findTabFragmentToActivity;
	private MyListView listview;
//	List<PatientObj> listPatients = new ArrayList<PatientObj>();
	List<HosNoticeVo> noticeVos = new ArrayList<HosNoticeVo>();
	RelativeLayout rlayout;

	private PatientTipsAdapter adapter;

	private NoticeAdapter adapter2;

	private GridView gridView;

	private MyListView listview2;

	private MomentScanPatientAdapter scanAdapter;
	VolleyClient volleyClient;

	private LinearLayout lay_all;
	/** 病历质控信息*/
	private RelativeLayout rel_quli_contr;
	private RelativeLayout rel_perioperative;
	private RelativeLayout rel_danger_value;
	private RelativeLayout rel_berth;
	private RelativeLayout rel_pati_mes;
	
	/**最近浏览的病人*/
	public static List<PatientInfoVo> visits=new ArrayList<PatientInfoVo>();
	
	public static int visitUpdate=0;
	
	public interface FindTabFragmentToActivity {
		public void toActivity();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home_page3, container,
				false);
		volleyClient = new VolleyClient(getActivity());
		volleyClient.setActivity(getActivity());
		initView(view);
		initData();
		initEvent();
		return view;
	}

	private void initData() {
		/**gridview就诊号init*/
		String json=configSP.getString(Constants.LATEST_VISIT_PATIENT_JSON, "");
		if(StringUtils.isNotBlank(json)){
			visits=JSONArray.parseArray(json, PatientInfoVo.class);
			setGridView();
		}
		
		queryHomeNotice();
		
	}

	private void initView(View view) {
		listview = (MyListView) view.findViewById(R.id.listview);
		listview2 = (MyListView) view.findViewById(R.id.listview2);
		gridView = (GridView) view.findViewById(R.id.grid);
		lay_all = (LinearLayout) view.findViewById(R.id.lay_all);
		rel_quli_contr = (RelativeLayout) view.findViewById(R.id.rel_quli_contr);
		rel_perioperative = (RelativeLayout) view.findViewById(R.id.rel_perioperative);
		rel_danger_value = (RelativeLayout) view.findViewById(R.id.rel_danger_value);
		rel_berth = (RelativeLayout) view.findViewById(R.id.rel_berth);
		rel_pati_mes = (RelativeLayout) view.findViewById(R.id.rel_pati_mes);
		FontManager.markAsIconContainer(lay_all, iconFont2);
	}


   private void setGridView() {
       int size = visits.size();
       int length = 70;
       DisplayMetrics dm = new DisplayMetrics();
       getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
       float density = dm.density;
       int gridviewWidth = (int) ((size * (length + 15)) * density);
       int itemWidth = (int) (length * density);

       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
       gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
       gridView.setColumnWidth(itemWidth); // 设置列表项宽
       gridView.setHorizontalSpacing(Utils.dip2px(getActivity(), 15)); // 设置列表项水平间距
       gridView.setStretchMode(GridView.NO_STRETCH);
       gridView.setNumColumns(size); // 设置列数量=列表集合数

       scanAdapter = new MomentScanPatientAdapter(getActivity(),visits);
       gridView.setAdapter(scanAdapter);
   }
	
	private void initEvent() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent=new Intent(getActivity(),PatientInfoDetailActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)visits.get(arg2));
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		rel_quli_contr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), QuliatyControlDetailActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		rel_perioperative.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), PatientPerioperativeManagerActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
					
				}
			});
		rel_danger_value.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DangerValueDetailActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		rel_berth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isOpenInter();
			}
		});
		rel_pati_mes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Utils.showToastBGNew(getActivity(), "病人信息查看");
				
				Intent intent = new Intent(getActivity(), PatientInfoActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		//医院公告栏
		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), SubscribeArticleDetailActivity.class);
				intent.putExtra("id", noticeVos.get(arg2).getId());
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	private void isOpenInter() {
		//http://localhost:8080/hsptapp/appconfig/serviceisusable.html
		String url = "http://58.42.232.110:8086/hsptapp/appconfig/serviceisusable.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
	
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
	
					@Override
					public void success(JSONObject response) {
	
						try {
							String resultCode = response.getString("resultCode");
							String resultContent = response.getString("resultContent");
							if ("1".equals(resultCode)) {
								Intent in = new Intent(getActivity(), ColligateServiceActivity.class);
								startActivity(in);
								getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
							} else {
								Toast.makeText(getActivity(), resultContent, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							Utils.showToastBGNew(getActivity(), "暂不能查看");
							e.printStackTrace();
						}
	
					}
	
					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(getActivity(), "暂不能查看");
					}
				});
	}
	/**
	 * 查询医院公告信息
	 */
	private void queryHomeNotice() {

		// http://localhost:8080/hsptapp/doctor/notice/hsptnotice/403.html?hid=2
		String url = "http://58.42.232.110:8086/hsptapp/doctor/notice/hsptnotice/403.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								noticeVos=JSONArray.parseArray(result, HosNoticeVo.class);
								adapter2 = new NoticeAdapter(getActivity(), noticeVos);
								listview2.setAdapter(adapter2);
								
							} else {

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
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(visitUpdate==1){
			
			setGridView();
			visitUpdate=0;
		}
		
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		findTabFragmentToActivity = (FindTabFragmentToActivity) activity;
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
