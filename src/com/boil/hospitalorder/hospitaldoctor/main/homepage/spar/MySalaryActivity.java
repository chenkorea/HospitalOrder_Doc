package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.InfoItemVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.SalaryVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MySalaryActivity extends BaseBackActivity implements OnDateSetListener{

	/** 当前 Activity 的上下文 */
	private MySalaryActivity context = MySalaryActivity.this;
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
	@ViewInject(R.id.list_salary)
	private ListView listSalary;
	
	private MonPickerDialog dateDialog;
	private SalaryVo salaryVo;
	private List<InfoItemVo> itemVos=new ArrayList<InfoItemVo>();
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_my_salary);

		// 开启注解
		ViewUtils.inject(context);

		initView();
		initData();
		initEvent();
		
	}

	private void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("我的工资");
		
		 
		volleyClient.setActivity(context);
	}

	private void initData() {
		
		
		Calendar calendar=Calendar.getInstance();
		try {
			calendar.setTime(df.parse(Utils.getCurrentYearMon()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH) + 1;
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		
		
		saveBtn.setText(month + "月份");
		
		dateDialog=new MonPickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,this, year, month,day);
		
//		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
//		String date=format.format(new Date());
		String date=year+"-"+month;
//		try {
//			//最大不能超过当前日期
//			datePicker.setMaxDate(format.parse(new Date().toString()).getTime());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		querySalary(date);
	}
	
	private void setData() {

		itemVos.clear();
		if (salaryVo != null) {

			if(salaryVo.getIdcard()!=null){
				
				itemVos.add(new InfoItemVo("日期", salaryVo.getYear() + "-" + salaryVo.getMonth()));
				itemVos.add(new InfoItemVo("工号", salaryVo.getEmpcardid()));
				itemVos.add(new InfoItemVo("姓名", salaryVo.getPsname()));
				itemVos.add(new InfoItemVo("身份证号", salaryVo.getIdcard()));
				itemVos.add(new InfoItemVo("科室名称", ""));
				itemVos.add(new InfoItemVo("学历工资", salaryVo.getPositionwage()));
				itemVos.add(new InfoItemVo("院龄补贴", salaryVo.getOtherbonusone()));
				itemVos.add(new InfoItemVo("独生子女费", salaryVo.getOnlychildsubsidy()));
				itemVos.add(new InfoItemVo("职称工资", salaryVo.getFeeforprStringing()));
				itemVos.add(new InfoItemVo("薪级工资", salaryVo.getPayscalewage()));
				itemVos.add(new InfoItemVo("基本合计", salaryVo.getBasewage()));
				itemVos.add(new InfoItemVo("绩效工资", salaryVo.getHoldwagesubsidy()));
				itemVos.add(new InfoItemVo("岗位工资", salaryVo.getCommunicationsubsidy()));
				itemVos.add(new InfoItemVo("在岗补贴", salaryVo.getPositionSubsidy()));
				itemVos.add(new InfoItemVo("职务补贴", salaryVo.getDutymoneysubsidy()));
				itemVos.add(new InfoItemVo("附加基础绩效奖", salaryVo.getFeeforhortationsafety()));
				itemVos.add(new InfoItemVo("个人养老金", salaryVo.getPspayforendowmentinsurance()));
				itemVos.add(new InfoItemVo("个人失业金", salaryVo.getPspayforunemployment()));
				itemVos.add(new InfoItemVo("个人公积金", salaryVo.getPspayforaccumulationfund()));
				itemVos.add(new InfoItemVo("个人医疗金", salaryVo.getPspayformedicare()));
				itemVos.add(new InfoItemVo("个人医保合计", ""));
				itemVos.add(new InfoItemVo("养老金单位", ""));
				itemVos.add(new InfoItemVo("失业金单位", salaryVo.getComPayforUnemployment()));
				itemVos.add(new InfoItemVo("公积金单位", salaryVo.getComPayforAccumulationFund()));
				itemVos.add(new InfoItemVo("医疗金单位", salaryVo.getComPayforMedicare()));
				itemVos.add(new InfoItemVo("工伤保险", salaryVo.getComPayforInjury()));
				itemVos.add(new InfoItemVo("生育保险单位", salaryVo.getComPayforProcreateInsurance()));
				itemVos.add(new InfoItemVo("公务员补助", salaryVo.getOtherInsurance()));
				itemVos.add(new InfoItemVo("垫付工资", salaryVo.getPositionSubsidy()));
				itemVos.add(new InfoItemVo("补助费", salaryVo.getFestivalSubsidy()));
				itemVos.add(new InfoItemVo("加班费", salaryVo.getOverTime()));
				itemVos.add(new InfoItemVo("夜班费", salaryVo.getNightMealSubsidy()));
				itemVos.add(new InfoItemVo("午餐补助", salaryVo.getMealSubsidy()));
				itemVos.add(new InfoItemVo("绩效", salaryVo.getAccountancySubsidy()));
				itemVos.add(new InfoItemVo("交税", salaryVo.getDeductTotal()));
				
				listSalary.setVisibility(View.VISIBLE);
				
				NationAdapter adapter = new NationAdapter();
				listSalary.setAdapter(adapter);
			}else {
				
				listSalary.setVisibility(View.GONE);
				LoadingUtils.showLoadMsg(loadView, "暂无该月份的工资");
			}
			
		} else {
			listSalary.setVisibility(View.GONE);
			LoadingUtils.showLoadMsg(loadView, "暂无该月份的工资");
		}

	}

	private void initEvent() {
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dateDialog.show();
			}
		});

	}

	/**
	 * 查询检查结果
	 */
	private void querySalary(String date) {

		// http://58.42.232.110:8086/hsptapp/doctor/medinfo/lkwagesdetail/301.html?&idcard=522224199004024015&date=2016-06&name=%E6%97%BA%E6%B5%B7%E6%B4%8B
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medinfo/lkwagesdetail/301.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("idcard", configSP.getString(Constants.USER_ID_NUMBER, ""));
		params.put("date", date);
		params.put("name", configSP.getString(Constants.USER_NAME, ""));

		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								
								salaryVo=com.alibaba.fastjson.JSONObject.parseObject(result, SalaryVo.class);
								setData();

							} else {
								listSalary.setVisibility(View.GONE);
								LoadingUtils.showLoadMsg(loadView, "暂无该月份的工资");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							listSalary.setVisibility(View.GONE);
							LoadingUtils.showLoadMsg(loadView, "查询数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						listSalary.setVisibility(View.GONE);
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				});

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		querySalary(year+"-"+(monthOfYear+1));
		saveBtn.setText((monthOfYear+1)+"月份");
	}
	
	
	private class NationAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return itemVos.size();
		}

		@Override
		public Object getItem(int position) {
			return itemVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.layout_salary_item, null);
			}
			
			TextView name = ViewHolder.get(convertView, R.id.tv_salary_item);
			TextView value = ViewHolder.get(convertView, R.id.tv_salary_value);
			
			name.setText(itemVos.get(position).getName());
			value.setText(itemVos.get(position).getValue());
			
			return convertView;
		}
	}

	public class MonPickerDialog extends DatePickerDialog {
		 public MonPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		        super(context, callBack, year, monthOfYear, dayOfMonth);

		        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);

		    }

		    public MonPickerDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
		        super(context, theme, listener, year, monthOfYear, dayOfMonth);

		        ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
		    }

		    @Override
		    public void onDateChanged(DatePicker view, int year, int month, int day) {
		        super.onDateChanged(view, year, month, day);
		        
		    }
	}
}
