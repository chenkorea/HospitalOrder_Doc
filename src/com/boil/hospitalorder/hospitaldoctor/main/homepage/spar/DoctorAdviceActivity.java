package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.DoctorAdviceAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.AdviceVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientFilterVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.MySalaryActivity.MonPickerDialog;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.lbssearch.a.b.n;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 医嘱信息
 * @author mengjiyong
 *
 */
public class DoctorAdviceActivity extends BaseBackActivity implements OnDateSetListener{
	
	/** 当前 Activity 的上下文 */
	private DoctorAdviceActivity context = DoctorAdviceActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	
	
	/** 类别布局 */
	@ViewInject(R.id.ll_sub_filter)
	private LinearLayout ll_filter;
	/** 过滤菜单 */
	@ViewInject(R.id.list_filter)
	private ListView listFilter;
	/** 透明背景层 */
	@ViewInject(R.id.v_order_trans)
	private View transView;
	
	@ViewInject(R.id.tv_1)
	private TextView tv_1;
	@ViewInject(R.id.tv_2)
	private TextView tv_2;
	@ViewInject(R.id.tv_3)
	private TextView tv_3;
	@ViewInject(R.id.list_advice)
	private ListView listAdvice;
	
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
	
	private PatientInfoVo infoVo;
	
	private List<AdviceVo> vos=new ArrayList<AdviceVo>();
	
	private AlertDialog dateDialog;
	private String startDate;
	private String endDate;
	private DatePickerDialog datePickerDialog;
	/**用户点击开始日期还是结束日期  用来做标记*/
	private int whatDate=0;
	private TextView tvStartDate;
	private TextView tvEndDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_advice);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("医嘱信息");
		saveBtn.setText("日期");
		volleyClient.setActivity(context);
		ll_filter.setVisibility(View.GONE);
	}

	private void initData() {
		infoVo=(PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		
		
		startDate=infoVo.getAdmdate();
		endDate=String.format("%s-%s-%s", year,(month+1),day);
		
		f1.add(new PatientFilterVo("临时医嘱", true));
		f1.add(new PatientFilterVo("长期医嘱", false));
		f2.add(new PatientFilterVo("默认显示", true));
		f2.add(new PatientFilterVo("全部显示", false));
		f3.add(new PatientFilterVo("本科室", true));
		f3.add(new PatientFilterVo("所有科室", false));
		
		filterAdapter = new FilterAdapter(f1);
		listFilter.setAdapter(filterAdapter);
		
		queryAdvice();
	}

	private void initEvent() {
		tv_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setFilterData(f1, 1);

			}
		});
		tv_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFilterData(f2, 2);
			}
		}); 
		tv_3.setOnClickListener(new OnClickListener() {

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
				queryAdvice();
			}
		});
		
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDateDialog();
			}
		});
		
		listAdvice.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Intent intent=new Intent(context,AdviceDetailActivity.class);
				intent.putExtra("AdviceVo", (Serializable)vos.get(arg2));
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	

	protected void showDateDialog() {


		if (dateDialog == null) {
			dateDialog = new AlertDialog.Builder(context).create();
			dateDialog.show();
			
			Window window = dateDialog.getWindow();
			window.setContentView(R.layout.layout_advice_date);
			
			LinearLayout lay1=(LinearLayout) window.findViewById(R.id.lay_start_date);
			LinearLayout lay2=(LinearLayout) window.findViewById(R.id.lay_end_date);
			tvStartDate = (TextView) window.findViewById(R.id.tv_start_date);
			tvEndDate=(TextView) window.findViewById(R.id.tv_end_date);
			
			
			tvStartDate.setText(startDate);
			tvEndDate.setText(endDate);
			
			BootstrapButton cancel=(BootstrapButton) window.findViewById(R.id.btn_cancel);
			BootstrapButton sure=(BootstrapButton) window.findViewById(R.id.btn_sure);
			
			
			lay1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					whatDate=0;
					int year=Integer.valueOf(startDate.split("-")[0]);
					int month=Integer.valueOf(startDate.split("-")[1])-1;
					int day=Integer.valueOf(startDate.split("-")[2]);
					
					datePickerDialog=new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,DoctorAdviceActivity.this, year, month,day);
					datePickerDialog.show();
				}
			});
			
			lay2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					whatDate=1;
					int year=Integer.valueOf(endDate.split("-")[0]);
					int month=Integer.valueOf(endDate.split("-")[1])-1;
					int day=Integer.valueOf(endDate.split("-")[2]);
					
					datePickerDialog=new DatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,DoctorAdviceActivity.this, year, month,day);
					datePickerDialog.show();
				}
			});
			
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dateDialog.dismiss();
				}
			});
			
			
			sure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					queryAdvice();
					dateDialog.dismiss();
				}
			});
			
			
		} else {
			
			tvStartDate.setText(startDate);
			tvEndDate.setText(endDate);
			dateDialog.show();
		}
	
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

			tv_1.setText(filterVo.getName());
			
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
			tv_2.setText(filterVo.getName());
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
			tv_3.setText(filterVo.getName());
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
	
	private void queryAdvice(){
		
		//http://localhost:8080/hsptapp/doctor/lisres/lspatorderitem/105.html?hid=2&admno=345544&sdate=2016-11-20&edate=2016-11-21&odept=79&otype=1&ostate=1&stype=1
		String url = "http://58.42.232.110:8086/hsptapp/doctor/lisres/lspatorderitem/105.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("admno",infoVo.getAdmId());
//		params.put("admno","345544");
		params.put("sdate", startDate);
		params.put("edate", endDate);
		params.put("odept", MainActivity.deptVo.getHisId());
//		params.put("odept", "79");
		if(currentPosition1==0){
			params.put("otype", "1");
		}else {
			params.put("otype", "2");
		}
		if(currentPosition2==0){
			
			params.put("ostate", "1");
		}else {
			params.put("ostate", "2");
			
		}
		if(currentPosition3==0){
			
			params.put("stype", "1");
		}else {
			params.put("stype", "2");
			
		}

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								vos=JSONArray.parseArray(result, AdviceVo.class);
								if(vos.size()>0){
									
									DoctorAdviceAdapter adapter=new DoctorAdviceAdapter(context, vos);
									listAdvice.setAdapter(adapter);
								}else {
									Utils.showToastBGNew(context, "暂无医嘱数据");
								}
								

							} else {
								
								Utils.showToastBGNew(context, "暂无医嘱数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							
							Utils.showToastBGNew(context, "查询医嘱数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						
						Utils.showToastBGNew(context, "查询医嘱数据失败");
					}
				});
		
		
	
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		
		
		if(whatDate==0){
			
			startDate=String.format("%s-%s-%s", year,(monthOfYear+1),dayOfMonth);
			tvStartDate.setText(startDate);
			
		}else {
			
			endDate=String.format("%s-%s-%s", year,(monthOfYear+1),dayOfMonth);
			tvEndDate.setText(endDate);
		}
		
	}
}
