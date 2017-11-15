package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.BasePopAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.HosIncomeClassifyAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.MyExpandableListAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientCaseRecordChildVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientCaseRecordVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginDeptVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CopyOfPatientMedicalRecordActivity extends BaseBackActivity implements
OnScrollListener{

	private CopyOfPatientMedicalRecordActivity context = CopyOfPatientMedicalRecordActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	
	@ViewInject(R.id.lay_right)
	private LinearLayout lay_right;
	
	@ViewInject(R.id.tv_office)
	private TextView tv_office;
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private PatientInfoVo patientInfoVo;
	@ViewInject(R.id.expandableListView)
	private ExpandableListView listView;
	private FrameLayout indicatorGroup;
	private MyExpandableListAdapter mAdapter;
	
	private int indicatorGroupId = -1;
	private int indicatorGroupHeight;
	private LoginInfoVo infoVo;
	@ViewInject(R.id.v_order_trans)
	private View v_order_trans;
	@ViewInject(R.id.title)
	private RelativeLayout title;
	
	private List<PatientCaseRecordVo> vosTemp = new ArrayList<PatientCaseRecordVo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_case_expand_listview_data);
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		indicatorGroup = (FrameLayout) findViewById(R.id.topGroup);
		listView.setOnScrollListener(this);
		listView.setGroupIndicator(null);
		initView();
		initEvent();
	}
	
	private void initView() {
		Utils.backClick(context, backBtn);
		addreTitle.setText(String.format("病历信息(%s)", MainActivity.deptVo.getName()));
		patientInfoVo = (PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		lay_right.setVisibility(View.VISIBLE);
		tv_office.setVisibility(View.GONE);
		queryReportClassify(MainActivity.deptVo);
	}
	
	private void initEvent() {
		lay_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(infoVo != null) {
					getPop();
				} else {
					queryDept();
				}
			}
		});
//		lv_income.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				Intent in = new Intent(context, patientMedicalRecordDetailActivity.class);
//				in.putExtra("ListId", incomeAdapter.getList().get(arg2).getId());
//				startActivity(in);
//				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//				
//			}
//		});
	}
	int barHeight = 0;
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus) {
			barHeight = title.getBottom();
			barHeight += Utils.getStatusBarHeight(this);
		}
	}
	View view;
	ListView lv_surgery;
	private PopupWindow popWin;
	BasePopAdapter popAdapter;
	public void getPop() {
		v_order_trans.setVisibility(View.VISIBLE);
		if(popWin == null) {
			
			view = LayoutInflater.from(this).inflate(R.layout.base_popwindow_dialog, null);
			lv_surgery = (ListView) view.findViewById(R.id.list_order_hos);
			popAdapter = new BasePopAdapter(this);
			lv_surgery.setAdapter(popAdapter);
			
			if(infoVo.getDepts().size()>4){
				popWin = new PopupWindow(view,LayoutParams.MATCH_PARENT, Utils.dip2px(context, 210));
			}else {
				popWin = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			}
			popWin.setBackgroundDrawable(new BitmapDrawable());
			popWin.setAnimationStyle(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
			popWin.setOutsideTouchable(true);
			popWin.setFocusable(true);
			popWin.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					v_order_trans.setVisibility(View.GONE);
					mAdapter.notifyDataSetChanged();
				}
			});
			lv_surgery.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					queryReportClassify(infoVo.getDepts().get(arg2));
					popWin.dismiss();
				}
			});
		}
		popAdapter.setList(infoVo.getDepts());
		popWin.showAtLocation(title, Gravity.TOP|Gravity.RIGHT, 0, barHeight);
		popWin.update();
	}
	
	private void queryDept(){
		
		//http://localhost:8080/hsptapp/doctor/medlogin/initlogin/201.html?uname=20001&pwd=123456
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medlogin/initlogin/201.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", configSP.getString(Constants.LOGIN_INFO_ID, ""));
		params.put("pwd", configSP.getString(Constants.LOGIN_INFO_PWD, ""));

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						
						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								infoVo=com.alibaba.fastjson.JSONObject.parseObject(result, LoginInfoVo.class);
								
								if(infoVo.getDepts().size()>0){
									
									getPop();
									
								}else {
									
									Utils.showToastBGNew(context, "查不到科室数据");
								}
								
							}else {
								Utils.showToastBGNew(context, "查不到科室数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询科室数据失败");
					}
				});
	}
	
	
	private void queryReportClassify(final LoginDeptVo depVo){
//		http://58.42.232.110:8086/hsptapp/doctor/emr/lsemrtype/602.html?did=370&stype=1&admno=390699
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/doctor/emr/lsemrtype/602.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("did", depVo.getHisId());
		params.put("stype", "1");
		params.put("admno", patientInfoVo.getAdmId());
		params.put("pagecount", "-1");
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				addreTitle.setText(String.format("病历信息(%s)", depVo.getName()));
				vosTemp.clear();
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						vosTemp = JSONArray.parseArray(result, PatientCaseRecordVo.class);
						org.json.JSONArray jsonArray = response.getJSONArray("t");
						for (int i = 0; i < jsonArray.length(); i++) {
							List<PatientCaseRecordChildVo> vosChildTemp = new ArrayList<PatientCaseRecordChildVo>();
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							if("1".equals(jsonObj.getString("haveChild")) && jsonObj.has("emrRcbcs")) {
								vosChildTemp = JSONArray.parseArray(jsonObj.getString("emrRcbcs"), PatientCaseRecordChildVo.class);
								vosTemp.get(i).setChildVos(vosChildTemp);
							}
						}
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
					}
					mAdapter = new MyExpandableListAdapter(context, patientInfoVo.getAdmId(), vosTemp);
					listView.setAdapter(mAdapter);
					
				} catch (JSONException e) {
					LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询病历列表失败");
			}
		});
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		final ExpandableListView listView = (ExpandableListView) view;
		/**
		 * calculate point (0,0)
		 */
		int npos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
		if (npos == AdapterView.INVALID_POSITION)// 如果第一个位置值无效
			return;

		long pos = listView.getExpandableListPosition(npos);
		int childPos = ExpandableListView.getPackedPositionChild(pos);// 获取第一行child的id
		int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 获取第一行group的id
		if (childPos == AdapterView.INVALID_POSITION) {// 第一行不是显示child,就是group,此时没必要显示指示器
			View groupView = listView.getChildAt(npos
					- listView.getFirstVisiblePosition());// 第一行的view
			indicatorGroupHeight = groupView.getHeight();// 获取group的高度
			indicatorGroup.setVisibility(View.GONE);// 隐藏指示器
		} else{
			indicatorGroup.setVisibility(View.VISIBLE);// 滚动到第一行是child，就显示指示器
		}
		// get an error data, so return now
		if (indicatorGroupHeight == 0) {
			return;
		}
		// update the data of indicator group view
		if (groupPos != indicatorGroupId) {// 如果指示器显示的不是当前group
			mAdapter.getGroupView(groupPos, listView.isGroupExpanded(groupPos),
					indicatorGroup.getChildAt(0), null);// 将指示器更新为当前group
			indicatorGroupId = groupPos;
//			Log.e(TAG, PRE + "bind to new group,group position = " + groupPos);
			// mAdapter.hideGroup(indicatorGroupId); // we set this group view
			// to be hided
			// 为此指示器增加点击事件
			indicatorGroup.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					listView.collapseGroup(indicatorGroupId);
				}
			});
		}
		if (indicatorGroupId == -1) // 如果此时grop的id无效，则返回
			return;

		/**
		 * calculate point (0,indicatorGroupHeight) 下面是形成往上推出的效果
		 */
		int showHeight = indicatorGroupHeight;
		int nEndPos = listView.pointToPosition(0, indicatorGroupHeight);// 第二个item的位置
		if (nEndPos == AdapterView.INVALID_POSITION)//如果无效直接返回
			return;
		long pos2 = listView.getExpandableListPosition(nEndPos);
		int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);//获取第二个group的id
		if (groupPos2 != indicatorGroupId) {//如果不等于指示器当前的group
			View viewNext = listView.getChildAt(nEndPos
					- listView.getFirstVisiblePosition());
			showHeight = viewNext.getTop();
//			Log.e(TAG, PRE + "update the show part height of indicator group:"
//					+ showHeight);
		}

		// update group position
		MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup
				.getLayoutParams();
		layoutParams.topMargin = -(indicatorGroupHeight - showHeight);
		indicatorGroup.setLayoutParams(layoutParams);		
	}
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
}
