package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragmentActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PerioperativeVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.FilterMenuperioperativeFragment.SureonClick5;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.hospitalmanager.main.homepage.model.CategoryItemVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PatientPerioperativeManagerActivity extends BaseFragmentActivity implements SureonClick5{
	/** 标记 */
	private static final String TAG = "PatientPerioperativeManagerActivity";
	private PatientPerioperativeManagerActivity context = PatientPerioperativeManagerActivity.this;
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
	
	private LoginInfoVo infoVo;
	private MenuDrawer mDrawer;
	private int menuSize;
	private FilterMenuperioperativeFragment filterMenuFragment;
	private String startDate;
	private String endDate;
	private String depTypeId = "";
	private String admTypeId = "0";
	
	private ListAdapter adapter;
	private List<PerioperativeVo> vos = new ArrayList<PerioperativeVo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initMenu();
		initView();
		initEvent();
	}
	
	private void initEvent() {
		lay_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(infoVo != null) {
					filterMenuFragment.setCategoryItemVo(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId));
					filterMenuFragment.initDataView();
					toggleFilterMenu();
				} else {
					queryDept();
				}
			}
		});
		
		lv_income.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, PerioperativeDetailActivity.class);
				intent.putExtra("admno", vos.get(arg2).getAdmNo());
				intent.putExtra("ocode", vos.get(arg2).getOperCode());
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	
	private void initView() {
		
		Utils.backClick(context, backBtn);
		addreTitle.setText("围术期列表");
		lay_right.setVisibility(View.VISIBLE);
		bt_add_usual_line.setVisibility(View.GONE);
		tv_office.setVisibility(View.VISIBLE);
		tv_office.setText("筛选");
		queryDept();
//		queryPerioperativeList(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId));
	}
	
	private void initMenu() {
		startDate = Utils.getFormerDay("yyyy-MM-dd");//Utils.getFirstDayMonth();
		endDate = Utils.getCurrentTime("yyyy-MM-dd");
		depTypeId = MainActivity.deptVo.getHisId();
		mDrawer = MenuDrawer
				.attach(this, MenuDrawer.Type.OVERLAY, Position.END);
		mDrawer.setMenuView(R.layout.menudrawer);
		mDrawer.setContentView(R.layout.hos_dep_report_main_stuation);
		// 菜单无阴影
		mDrawer.setDropShadowEnabled(false);
		filterMenuFragment = new FilterMenuperioperativeFragment();
		
		CategoryItemVo cateVo2= new CategoryItemVo(startDate, endDate, depTypeId, admTypeId);
		filterMenuFragment.setCategoryItemVo(cateVo2);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.menu_container, filterMenuFragment).commit();
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
	}
	
	public void toggleFilterMenu() {
		mDrawer.toggleMenu();
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
									filterMenuFragment.setVosOffices(infoVo.getDepts());
									filterMenuFragment.initFlow2();
									queryPerioperativeList(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId));
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

	private void queryPerioperativeList(final CategoryItemVo vo){
//		http://localhost:8080/hsptapp/doctor/operation/lspatoperlist/801.html?dhid=95&ocode=0
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/doctor/operation/lspatoperlist/801.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("dhid", vo.getDepTypeId());//"95"
		params.put("startDate", vo.getStartDate());
		params.put("endDate", vo.getEndDate());
		params.put("ocode", "0");
		if("1".equals(vo.getAdmTypeId())) {
			params.put("mhid", configSP.getString(Constants.USER_HISID, ""));
		}
		loadView.setVisibility(View.GONE);
		lv_income.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				
				try {
					
//					vos.clear();
					depTypeId = vo.getDepTypeId();
					startDate = vo.getStartDate();
					endDate = vo.getEndDate();
					admTypeId = vo.getAdmTypeId();
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						vos = JSONArray.parseArray(result, PerioperativeVo.class);
						ListAdapter adapter = new ListAdapter();
						lv_income.setAdapter(adapter);
					} else if("2".equals(resultCode)){
						LoadingUtils.showLoadMsg(loadView, "暂无数据");
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询失败");
					}
					lv_income.setVisibility(View.VISIBLE);
				} catch (JSONException e) {
					e.printStackTrace();
					LoadingUtils.showLoadMsg(loadView, "查询失败");
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询失败");
			}
		});
	}

	class ListAdapter extends BaseAdapter{

		private List<PerioperativeVo> qVos = new ArrayList<PerioperativeVo>();
		
		public ListAdapter() {
			super();
		}

		public ListAdapter(List<PerioperativeVo> liss) {
			this.qVos = liss;
		}
		
		
		public List<PerioperativeVo> getvos() {
			return vos;
		}

		public void setvos(List<PerioperativeVo> vos) {
			this.qVos = vos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return vos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return vos.get(position);
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
	        	convertView = layoutInflater.inflate(R.layout.patient_perioperative_main_listview_item, null);
	        	viewHolder.im_icon = (TextView) convertView.findViewById(R.id.im_icon);
	        	viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
	        	viewHolder.tv_dignose_num = (TextView) convertView.findViewById(R.id.tv_dignose_num);
	        	viewHolder.tv_bed_no = (TextView) convertView.findViewById(R.id.tv_bed_no);
	        	viewHolder.tv_prtdiagnosis_str = (TextView) convertView.findViewById(R.id.tv_prtdiagnosis_str);
	        	viewHolder.tv_booldtype_str = (TextView) convertView.findViewById(R.id.tv_booldtype_str);
	        	viewHolder.tv_opername_str = (TextView) convertView.findViewById(R.id.tv_opername_str);
	        	viewHolder.tv_operdoc_str = (TextView) convertView.findViewById(R.id.tv_operdoc_str);
	        	viewHolder.tv_operroom_str = (TextView) convertView.findViewById(R.id.tv_operroom_str);
	        	viewHolder.tv_operpositon_str = (TextView) convertView.findViewById(R.id.tv_operpositon_str);
	        	viewHolder.tv_opertime_str = (TextView) convertView.findViewById(R.id.tv_opertime_str);
	        	viewHolder.tv_operappTime_str = (TextView) convertView.findViewById(R.id.tv_operappTime_str);
	        	viewHolder.tv_operstaTime_str = (TextView) convertView.findViewById(R.id.tv_operstaTime_str);
	        	viewHolder.tv_opertype_str = (TextView) convertView.findViewById(R.id.tv_opertype_str);
	        	viewHolder.tv_opersta_str = (TextView) convertView.findViewById(R.id.tv_opersta_str);
	        	viewHolder.tv_medi_type = (TextView) convertView.findViewById(R.id.tv_medi_type);
	        	
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        PerioperativeVo vo = vos.get(position);
	        
	        viewHolder.im_icon.setTypeface(iconFont2);
        	viewHolder.tv_title.setText(vo.getPrtName());
        	viewHolder.tv_dignose_num.setText(String.format("就诊号：%s", vo.getAdmNo()));
        	viewHolder.tv_bed_no.setText(String.format("%s %s %s", vo.getPrtAge(), vo.getPrtSex(), vo.getPrtDept()));
        	viewHolder.tv_prtdiagnosis_str.setText(vo.getPrtDiagnosis());
        	viewHolder.tv_booldtype_str.setText(vo.getBooldType());
        	viewHolder.tv_opername_str.setText(vo.getOperName());
        	viewHolder.tv_operdoc_str.setText(vo.getOperDoc());
        	viewHolder.tv_operroom_str.setText(vo.getOperRoom());
        	viewHolder.tv_operpositon_str.setText(vo.getOperPositon());
        	viewHolder.tv_opertime_str.setText(vo.getOperTime());
        	viewHolder.tv_operappTime_str.setText(vo.getOperAppTime());
        	viewHolder.tv_operstaTime_str.setText(vo.getOperStaTime());
        	viewHolder.tv_opertype_str.setText(vo.getOperType());
        	viewHolder.tv_opersta_str.setText(vo.getOperSta());
        	viewHolder.tv_medi_type.setText(vo.getPrtType());
	        return convertView;
	    }
	}
	class ViewHolder{
		   TextView im_icon;
		   TextView tv_title;
		   TextView tv_dignose_num;
		   TextView tv_bed_no;
		   TextView tv_prtdiagnosis_str;
		   TextView tv_booldtype_str;
		   TextView tv_opername_str;
		   TextView tv_operdoc_str;
		   TextView tv_operroom_str;
		   TextView tv_operpositon_str;
		   TextView tv_opertime_str;
		   TextView tv_operappTime_str;
		   TextView tv_operstaTime_str;
		   TextView tv_opertype_str;
		   TextView tv_opersta_str;
		   TextView tv_medi_type;
	    }
	
	@Override
	public void onClick(CategoryItemVo vo) {
		queryPerioperativeList(vo);
		toggleFilterMenu();
	}
	/**
	 * 设置菜单宽度
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// 若不将menuSize设为成员变量，在从第二层菜单返回时，会造成菜单消失
		if (menuSize == 0) {
			int screenWidth = Utils.getScreenWidth(this);
			menuSize = screenWidth / 7 * 6;
			mDrawer.setMenuSize(menuSize);
		}
	}
	
	// 《当用户按下了"手机上的返回功能按键"的时候会回调这个方法》
	@Override
	public void onBackPressed() {
		final int drawerState = mDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN
				|| drawerState == MenuDrawer.STATE_OPENING) {
			mDrawer.closeMenu();
			return;
		}
		// 也就是说，当按下返回功能键的时候，不是直接对Activity进行弹栈，而是先将菜单视图关闭
		super.onBackPressed();
	}
	
}
