package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.R.id;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientFilterVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.utils.CHScrollView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 病人信息查看
 * 
 * @author mengjiyong
 *
 */
public class PatientInfoActivity extends BaseBackActivity {

	/** 当前 Activity 的上下文 */
	private PatientInfoActivity context = PatientInfoActivity.this;

	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;

	/** 类别布局 */
	@ViewInject(R.id.ll_sub_filter)
	private LinearLayout ll_filter;
	/** 病人所属 */
	@ViewInject(R.id.tv_belong)
	private TextView tv_belong;
	/** 在院 */
	@ViewInject(R.id.tv_hos)
	private TextView tv_hos;
	/** 搜索 */
	@ViewInject(R.id.tv_search)
	private TextView tv_search;
	
	@ViewInject(R.id.et_search)
	private EditText et_search;
	
	@ViewInject(R.id.btn_search)
	private ImageButton btn_search;
	
	/** 过滤菜单 */
	@ViewInject(R.id.list_filter)
	private ListView listFilter;
	/** 透明背景层 */
	@ViewInject(R.id.v_order_trans)
	private View transView;
	/** 表格头 */
	@ViewInject(R.id.ch_table_header)
	private CHScrollView tableHeader;
	/** 表格体 */
	@ViewInject(R.id.lv_table_body)
	private ListView tableBody;
	
	
	@ViewInject(R.id.ll_table)
	private View ll_patient;
	@ViewInject(R.id.grid_patient)
	private GridView grid_patient;
	@ViewInject(R.id.iv_switch)
	private ImageView iv_switch;
	
	private int showFlag=0;

	private List<PatientFilterVo> f1 = new ArrayList<PatientFilterVo>();
	private List<PatientFilterVo> f2 = new ArrayList<PatientFilterVo>();
	private List<PatientFilterVo> f3 = new ArrayList<PatientFilterVo>();

	private int countClickFilter1 = 0;
	private int countClickFilter2 = 0;
	private int countClickFilter3 = 0;
	private int currentPosition1=0;
	private int currentPosition2=0;
	private int currentPosition3=0;

	private int currentFilter = 0;

	private FilterAdapter filterAdapter;
	
	private List<PatientInfoVo> infoVos=new ArrayList<PatientInfoVo>();
	private List<PatientInfoVo> temp=new ArrayList<PatientInfoVo>();

	private String searchValue="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		volleyClient.setActivity(context);
		ll_filter.setVisibility(View.GONE);
		
		// 添加头滑动事件
		mHScrollViews.add(tableHeader);
		
		grid_patient.setVisibility(View.GONE);
		
	}

	private void initData() {
		
		f1.add(new PatientFilterVo("本人病人", true));
		f1.add(new PatientFilterVo("本科病人", false));
		f2.add(new PatientFilterVo("在院", true));
		f2.add(new PatientFilterVo("已出院", false));
		f3.add(new PatientFilterVo("登记号搜索", true));
		f3.add(new PatientFilterVo("住院号搜索", false));
		f3.add(new PatientFilterVo("姓名搜索", false));
		
		filterAdapter = new FilterAdapter(f1);
		listFilter.setAdapter(filterAdapter);
		
		queryPatientInfo();
		
	}
	
	/**
	 * 
	 * 添加滑动元素。
	 * 
	 * @param chScrollView
	 * 
	 */
	private void addCHScrollView(final CHScrollView chScrollView) {
		if (!mHScrollViews.isEmpty()) {
			CHScrollView scrollView = mHScrollViews.get(mHScrollViews.size() - 1);
			final int scrollX = scrollView.getScrollX();
			
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				tableBody.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						chScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		
		mHScrollViews.add(chScrollView);
	}

	private void initEvent() {

		tv_belong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setFilterData(f1, 1);

			}
		});
		tv_hos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFilterData(f2, 2);
			}
		});
		tv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setFilterData(f3, 3);
			}
		});

		transView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearData();
			}
		});

		listFilter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				
				initFilterData(arg2);
				clearData();
				
				//本地过滤
				if(currentFilter!=3){
					
					
					if (infoVos.size() > 0) {
						
						switchFilterData();
						
					} else {
						
						Utils.showToastBGNew(context, "暂无病人信息");
					}
				}

			}
		});
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				searchValue=et_search.getText().toString();
				if(StringUtils.isEmpty(searchValue)){
					
					Utils.showToastBGNew(context, "请输入搜索内容");
					return;
				}
				
				queryPatientInfo();
				
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				
				if(s.length()==0){
					
					searchValue="";
					queryPatientInfo();
					
				}
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		iv_switch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(showFlag==0){
					
					ll_patient.setVisibility(View.GONE);
					grid_patient.setVisibility(View.VISIBLE);
					iv_switch.setImageResource(R.drawable.menu_grid);
					showFlag=1;
					
				}else {
					
					ll_patient.setVisibility(View.VISIBLE);
					grid_patient.setVisibility(View.GONE);
					iv_switch.setImageResource(R.drawable.menu_list);
					showFlag=0;
				}
				
			}
		});
		
		grid_patient.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent=new Intent(context,PatientInfoDetailActivity.class);
				intent.putExtra("PatientInfoVo", (Serializable)temp.get(arg2));
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		
	}

	/**
	 * 过滤菜单
	 */
	private void switchFilterData() {
		
		temp.clear();
		
		if(currentPosition1==0){
			
			//过滤本人病人
			for(PatientInfoVo vo:infoVos){
				
				if(vo.getDoc_hisId().equals(configSP.getString(Constants.USER_HISID, ""))){
					
					if(currentPosition2==0){
						
						if("A".equals(vo.getState())){
							
							temp.add(vo);
						}
					}else {
						
						if("D".equals(vo.getState())){
							
							temp.add(vo);
						}
						
					}
				}
				
			}
			
		}else {
			
			for (PatientInfoVo vo : infoVos) {

				if (currentPosition2 == 0) {

					if ("A".equals(vo.getState())) {

						temp.add(vo);
					}
				} else {

					if ("D".equals(vo.getState())) {

						temp.add(vo);
					}

				}
			}
				
			
		}
		
		
		if(temp.size()==0){
			
			Utils.showToastBGNew(context, "暂无病人信息");
			
		}
		
		PatientInfoAdapter adapter=new PatientInfoAdapter(temp);
		tableBody.setAdapter(adapter);
		
		PatientInfoGridAdapter adapter2=new PatientInfoGridAdapter(temp);
		grid_patient.setAdapter(adapter2);
		

	}

	private void initFilterData(int position) {

		if (currentFilter == 1) {

			PatientFilterVo filterVo = f1.get(position);

			// 设置为已选择项 以便改变背景颜色
			for (PatientFilterVo vo : f1) {

				if (vo == filterVo) {

					vo.setSelect(true);
				} else {
					vo.setSelect(false);
				}

			}

			tv_belong.setText(filterVo.getName());
			
			currentPosition1=position;

		}
		if (currentFilter == 2) {

			PatientFilterVo filterVo = f2.get(position);
			for (PatientFilterVo vo : f2) {

				if (vo == filterVo) {

					vo.setSelect(true);
				} else {
					vo.setSelect(false);
				}

			}
			tv_hos.setText(filterVo.getName());
			currentPosition2=position;

		}
		if (currentFilter == 3) {

			PatientFilterVo filterVo = f3.get(position);
			for (PatientFilterVo vo : f3) {

				if (vo == filterVo) {

					vo.setSelect(true);
				} else {
					vo.setSelect(false);
				}

			}
			tv_search.setText(filterVo.getName());
			currentPosition3=position;

		}

		if (filterAdapter != null) {

			filterAdapter.notifyDataSetChanged();

		}
	}

	/**
	 * 初始化操作
	 */
	private void clearData() {
		ll_filter.setVisibility(View.GONE);
		transView.setVisibility(View.GONE);
		countClickFilter1 = 0;
		countClickFilter2 = 0;
		countClickFilter3 = 0;
	}

	/**
	 * 控制过滤列表的隐藏和显示
	 * 
	 * @param list
	 * @param filter
	 */
	private void setFilterData(List<PatientFilterVo> list, int filter) {

		// 重复点击 隐藏选择菜单
		if (filter == 1) {
			countClickFilter1++;
			countClickFilter2 = 0;
			countClickFilter3 = 0;
			if (countClickFilter1 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter1 = 0;
				return;
			}
		}
		if (filter == 2) {
			countClickFilter2++;
			countClickFilter1 = 0;
			countClickFilter3 = 0;
			if (countClickFilter2 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter2 = 0;
				return;
			}
		}
		if (filter == 3) {
			countClickFilter3++;
			countClickFilter1 = 0;
			countClickFilter2 = 0;
			if (countClickFilter3 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter3 = 0;
				return;
			}
		}

		currentFilter = filter;

		ll_filter.setVisibility(View.VISIBLE);
		transView.setVisibility(View.VISIBLE);

		filterAdapter.setList(list);
		listFilter.setAdapter(filterAdapter);
		filterAdapter.notifyDataSetChanged();

	}

	private void queryPatientInfo(){
		//0106
		//http://localhost:8080/hsptapp/doctor/lisres/lspatadminfo/101.html?hid=2&did=79
		String url = "http://58.42.232.110:8086/hsptapp/doctor/lisres/lspatadminfo/101.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("did", MainActivity.deptVo.getHisId());
//		params.put("mid", mid);
		params.put("name", searchValue);
		if(currentPosition3==0){
			
			params.put("state", "1");
		}else if(currentPosition3==1){
			params.put("state", "2");
			
		}else {
			params.put("state", "3");
			
		}
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						infoVos.clear();
						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {
								
								JSONArray array=response.getJSONArray("t");
								for(int i=0;i<array.length();i++){
									
									JSONObject object=array.getJSONObject(i);
									
									String admId=object.getString("admId");
									String admdate=object.getString("admdate");
									String admno=object.getString("admno");
									String bedno="";
									if(object.has("bedno")){
										bedno=object.getString("bedno");
									}
									
									JSONObject docObject=object.getJSONObject("doc");
									String doc_hisId="";
									if(docObject.has("hisId")){
										
										doc_hisId=docObject.getString("hisId");
									}
									String doc_name="";
									if(docObject.has("name")){
										doc_name=docObject.getString("name");
									}
									String doc_payway=docObject.getString("payway");
									
									
									String hisId=object.getString("hisId");
									String medicare="";
									if(object.has("medicare")){
										medicare=object.getString("medicare");
									}
									
									String name=object.getString("name");
									String register=object.getString("register");
									String state=object.getString("state");
									
									
									String age=object.getString("age");
									String sex=object.getString("sex");
									String isCPW="";
									if(object.has("isCPW")){
										isCPW=object.getString("isCPW");
									}
									
									PatientInfoVo vo=new PatientInfoVo(admId, admdate, admno, bedno, doc_hisId, doc_name, doc_payway, hisId, medicare, name, register, state,age,sex,isCPW);
									infoVos.add(vo);
								}
								
							} 
							
							switchFilterData();
							
						} catch (JSONException e) {

							e.printStackTrace();
							
							Utils.showToastBGNew(context, "查询病人信息失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询病人信息失败");
					}
				});
		
		
	
		
	}
	
	/**
	 * 过滤适配器
	 * @author mengjiyong
	 *
	 */
	private class FilterAdapter extends BaseAdapter {

		private List<PatientFilterVo> list;

		public FilterAdapter(List<PatientFilterVo> list) {
			super();
			this.list = list;
		}

		public void setList(List<PatientFilterVo> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.nation_list_view_item, null);
			}

			TextView tvNation = ViewHolder.get(convertView, R.id.tv_nation);
			tvNation.setTextColor(Color.BLACK);
			tvNation.setText(list.get(position).getName());

			if (list.get(position).isSelect()) {

				convertView.setBackgroundColor(getResources().getColor(R.color.listview_divider));

			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}

			return convertView;

		}

	}
	
	/**
	 * 病人信息列表适配器
	 * @author mengjiyong
	 *
	 */
	private class PatientInfoAdapter extends BaseAdapter {

		private List<PatientInfoVo> list;

		public PatientInfoAdapter(List<PatientInfoVo> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int arg0, View convertView, ViewGroup arg2) {

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.patient_info_table_item, null);

			}

			CHScrollView chTableBody = ViewHolder.get(convertView, R.id.ch_table_body);
			LinearLayout llBody=ViewHolder.get(convertView, R.id.ll_body);
			TextView tv1 = ViewHolder.get(convertView, R.id.tv_1);
			TextView tv2 = ViewHolder.get(convertView, R.id.tv_2);
			TextView tv3 = ViewHolder.get(convertView, R.id.tv_3);
			TextView tv4 = ViewHolder.get(convertView, R.id.tv_4);
			TextView tv5 = ViewHolder.get(convertView, R.id.tv_5);
			TextView tv6 = ViewHolder.get(convertView, R.id.tv_6);
			TextView tv7 = ViewHolder.get(convertView, R.id.tv_7);
			TextView tv8 = ViewHolder.get(convertView, R.id.tv_8);
			TextView tv9 = ViewHolder.get(convertView, R.id.tv_9);

			tv1.setText(list.get(arg0).getBedno());
			tv2.setText(list.get(arg0).getName());
			tv3.setText(list.get(arg0).getMedicare());
			tv4.setText(list.get(arg0).getAdmdate());
			tv5.setText(list.get(arg0).getRegister());
			tv6.setText(list.get(arg0).getAge());
			tv7.setText(list.get(arg0).getSex());
			if("A".equals(list.get(arg0).getState())){
				tv8.setText("在院");
			}else {
				tv8.setText("出院");
			}
			tv9.setText(list.get(arg0).getDoc_name());
			
			if("I".equals(list.get(arg0).getIsCPW())){
				
				tv1.setBackgroundResource(R.drawable.bg_table_green);
				tv2.setBackgroundResource(R.drawable.bg_table_green);
				tv3.setBackgroundResource(R.drawable.bg_table_green);
				tv4.setBackgroundResource(R.drawable.bg_table_green);
				tv5.setBackgroundResource(R.drawable.bg_table_green);
				tv6.setBackgroundResource(R.drawable.bg_table_green);
				tv7.setBackgroundResource(R.drawable.bg_table_green);
				tv8.setBackgroundResource(R.drawable.bg_table_green);
				tv9.setBackgroundResource(R.drawable.bg_table_green);
			}else {
				tv1.setBackgroundResource(R.drawable.bg_table);
				tv2.setBackgroundResource(R.drawable.bg_table);
				tv3.setBackgroundResource(R.drawable.bg_table);
				tv4.setBackgroundResource(R.drawable.bg_table);
				tv5.setBackgroundResource(R.drawable.bg_table);
				tv6.setBackgroundResource(R.drawable.bg_table);
				tv7.setBackgroundResource(R.drawable.bg_table);
				tv8.setBackgroundResource(R.drawable.bg_table);
				tv9.setBackgroundResource(R.drawable.bg_table);
				
			}
			
			addCHScrollView(chTableBody);
		
			llBody.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,PatientInfoDetailActivity.class);
					intent.putExtra("PatientInfoVo", (Serializable)list.get(arg0));
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
			});
			
			
			
			return convertView;
		}

	}
	
	/**
	 * 病人信息列表适配器
	 * @author mengjiyong
	 *
	 */
	private class PatientInfoGridAdapter extends BaseAdapter {

		private List<PatientInfoVo> list;

		public PatientInfoGridAdapter(List<PatientInfoVo> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int arg0, View convertView, ViewGroup arg2) {

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.layout_patient_grid_item, null);

			}

			TextView bedno=ViewHolder.get(convertView, R.id.tv_bedno);
			TextView tv1=ViewHolder.get(convertView, R.id.tv_1);
			TextView tv2=ViewHolder.get(convertView, R.id.tv_2);
			TextView tv3=ViewHolder.get(convertView, R.id.tv_3);
			
			LinearLayout ll_info=ViewHolder.get(convertView, R.id.lay_info);
			
			bedno.setText(list.get(arg0).getBedno());
			tv1.setText(list.get(arg0).getMedicare()+" "+list.get(arg0).getName());
			tv2.setText(list.get(arg0).getSex()+","+list.get(arg0).getAge());
			tv3.setText(list.get(arg0).getDoc_name()+" "+MainActivity.deptVo.getName());
			
			
			if("I".equals(list.get(arg0).getIsCPW())){
				bedno.setBackgroundColor(getResources().getColor(R.color.theme_color_1));
				ll_info.setBackgroundColor(getResources().getColor(R.color.theme_color_1));
				bedno.setTextColor(getResources().getColor(R.color.white));
				tv1.setTextColor(getResources().getColor(R.color.white));
				tv2.setTextColor(getResources().getColor(R.color.white));
				tv3.setTextColor(getResources().getColor(R.color.white));
		
			}else {
				bedno.setBackgroundColor(getResources().getColor(R.color.listview_divider));
				ll_info.setBackgroundColor(getResources().getColor(R.color.listview_divider));
				bedno.setTextColor(Color.DKGRAY);
				tv1.setTextColor(Color.DKGRAY);
				tv2.setTextColor(Color.DKGRAY);
				tv3.setTextColor(Color.DKGRAY);
			}
			
			return convertView;
		}

	}
}
