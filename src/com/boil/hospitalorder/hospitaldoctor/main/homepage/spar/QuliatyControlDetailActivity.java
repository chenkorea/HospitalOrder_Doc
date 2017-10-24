package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.BasePopAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.QulityCrtContVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.QulityCtrVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class QuliatyControlDetailActivity extends BaseBackActivity{

	private QuliatyControlDetailActivity context = QuliatyControlDetailActivity.this;
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
	
	@ViewInject(R.id.bt_add_usual_line)
	private ImageView bt_add_usual_line;
	
	@ViewInject(R.id.lv_income)
	private ListView lv_income;
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	@ViewInject(R.id.v_order_trans)
	private View v_order_trans;
	
	@ViewInject(R.id.title)
	private RelativeLayout title;
	private PopupWindow popWin;
	
	private List<QulityCtrVo> vos = new ArrayList<QulityCtrVo>();
	private ListAdapter adapter;
	private LoginInfoVo infoVo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_dep_report_main_stuation);
		ViewUtils.inject(this);
		volleyClient.setActivity(this);
		initView();
		initEvent();
		queryQulityCtrList(MainActivity.deptVo.getHisId());
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
		
		lv_income.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, PatientInfoDetailActivity.class);
				intent.putExtra("admno", vos.get(arg2).getAdmno());
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);				
			}
		});
		
	}
	private void queryQulityCtrList(String deptId){
//		http://58.42.232.110:8086/hsptapp/doctor/emr/lsdeptquality/606.html?deptId=79
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/emr/lsdeptquality/606.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("deptId", deptId);
		loadView.setVisibility(View.GONE);
		lv_income.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				vos.clear();
				lv_income.setVisibility(View.VISIBLE);
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						vos = JSONArray.parseArray(result, QulityCtrVo.class);
						org.json.JSONArray jsonArray = new org.json.JSONArray(result);
						String arrayStr = "";
						List<QulityCrtContVo> listChild = new ArrayList<QulityCrtContVo>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObj = jsonArray.getJSONObject(i);
							if(jsonObj.has("qlts")) {
								arrayStr = jsonObj.getString("qlts");
								listChild = JSONArray.parseArray(arrayStr, QulityCrtContVo.class);
								vos.get(i).setChildVo(listChild);
							}
						}
						adapter = new ListAdapter(vos);
						lv_income.setAdapter(adapter);
						
					} else if("2".equals(resultCode)){
						if(adapter != null) {
							adapter.notifyDataSetChanged();
						}
						LoadingUtils.showLoadMsg(loadView, "暂无数据");
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				} catch (JSONException e) {
					LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			}
		});
	}
	
	private void queryDept(){
		
		//http://localhost:8080/hsptapp/doctor/medlogin/initlogin/201.html?uname=20001&pwd=123456
		String url = "http://58.42.232.110:8086/hsptapp/doctor/medlogin/initlogin/201.html";
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
	
	private void initView() {
		Utils.backClick(context, backBtn);
		addreTitle.setText("病历质控信息");
		lay_right.setVisibility(View.VISIBLE);
		bt_add_usual_line.setVisibility(View.VISIBLE);
		tv_office.setVisibility(View.GONE);
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
					if(adapter != null) {
						adapter.notifyDataSetChanged();
					}
				}
			});
			lv_surgery.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					queryQulityCtrList(infoVo.getDepts().get(arg2).getHisId());
					
					popWin.dismiss();
				}
			});
		}
		popAdapter.setList(infoVo.getDepts());
		popWin.showAtLocation(title, Gravity.TOP|Gravity.RIGHT, 0, barHeight);
		popWin.update();
	}

	
	class ListAdapter extends BaseAdapter{

		private List<QulityCtrVo> qVos = new ArrayList<QulityCtrVo>();
		
		public List<QulityCtrVo> getqVos() {
			return qVos;
		}

		public void setqVos(List<QulityCtrVo> qVos) {
			this.qVos = qVos;
		}

		private ListAdapter(List<QulityCtrVo> qVos){
			this.qVos = qVos;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return qVos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return qVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        LayoutInflater layoutInflater = LayoutInflater.from(context);
	        ViewHolder viewHolder;
	        if(convertView == null) {
	        	viewHolder = new ViewHolder();
	        	convertView = layoutInflater.inflate(R.layout.hos_dep_report_charge_listview_item, null);
	        	viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
	        	viewHolder.tv_dignose_num = (TextView) convertView.findViewById(R.id.tv_dignose_num);
	        	viewHolder.lay_content = (LinearLayout) convertView.findViewById(R.id.lay_content);
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        QulityCtrVo vo = vos.get(position);
	        viewHolder.tv_title.setText(vo.getName());
	        viewHolder.tv_dignose_num.setText(String.format("就诊号：%s", vo.getAdmno()));
	        initUIView(viewHolder.lay_content, vo.getChildVo());
	        return convertView;
	    }
	}
   class ViewHolder{
	   LinearLayout lay_content;
	   TextView tv_title;
	   TextView tv_dignose_num;
    }
   

	private void initUIView(LinearLayout lay_content,
			List<QulityCrtContVo> childVo) {
		lay_content.removeAllViews();
		int padLen = Utils.dip2px(context, 8);
		for (int i = 0; i < childVo.size(); i++) {
			View viewSe = LayoutInflater.from(context).inflate(R.layout.separator_view, null);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(context, 0.5f));
			lay_content.addView(viewSe, params);
			
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params2.setMargins(0, padLen, 0, 0);
			TextView tv_child_doc = new TextView(context);
//			tv_child_doc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			tv_child_doc.setTextColor(getResources().getColor(R.color.black));
			tv_child_doc.setAlpha(0.8f);
			tv_child_doc.setGravity(Gravity.LEFT);
			tv_child_doc.setText(String.format("主治医生：%s", childVo.get(i).getDocName()));
			lay_content.addView(tv_child_doc, params2);
			
			TextView tv_child_content = new TextView(context);
			tv_child_content.setTextColor(getResources().getColor(R.color.black));
//			tv_child_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			tv_child_content.setAlpha(0.8f);
			tv_child_content.setGravity(Gravity.LEFT);
			tv_child_content.setText(String.format("质控信息：%s", childVo.get(i).getContent()));
			lay_content.addView(tv_child_content, params2);
		}
	}
	
}
