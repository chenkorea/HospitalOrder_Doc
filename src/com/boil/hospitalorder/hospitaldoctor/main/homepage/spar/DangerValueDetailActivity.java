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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragmentActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.DangerValueVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.FilterMenuChargeFragment.SureonClick2;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.hospitalmanager.main.homepage.model.CategoryItemVo;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.tool.ptrtool.CTListView;
import com.boil.hospitalsecond.tool.ptrtool.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrDefaultHandler;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DangerValueDetailActivity extends BaseFragmentActivity implements SureonClick2, CTPullUpListViewCallBack{
	/** 标记 */
	private static final String TAG = "DangerValueDetailActivity";
	private DangerValueDetailActivity context = DangerValueDetailActivity.this;
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
	
	@ViewInject(R.id.lv_income2)
	private CTListView lv_income2;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private LoginInfoVo infoVo;
	private MenuDrawer mDrawer;
	private int menuSize;
	private FilterMenuChargeFragment filterMenuFragment;
	private String startDate;
	private String endDate;
	private String depTypeId = "";
	private String admTypeId = "0";
	
	private ListAdapter adapter;
	private int pagecount = 10;
	private int currentpage = 1;
	private List<DangerValueVo> vos = new ArrayList<DangerValueVo>();
	
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.hos_dep_report_main_stuation);
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
		lv_income2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, PatientInfoDetailActivity.class);
				intent.putExtra("admno", vos.get(arg2).getAdmno());
				intent.putExtra("startDate", startDate);
				intent.putExtra("endDate", endDate);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}
	
	private void initView() {
		
		Utils.backClick(context, backBtn);
		addreTitle.setText("危急值");
		lay_right.setVisibility(View.VISIBLE);
		bt_add_usual_line.setVisibility(View.GONE);
		tv_office.setVisibility(View.VISIBLE);
		tv_office.setText("筛选");
		initPtr(this, mPtrFrameLayout);
		
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		
		lv_income2.setPageSize(pagecount);
		lv_income2.setMyPullUpListViewCallBack(this);

		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if(infoVo == null) {
					queryDept();
				} else {
					queryDangerValueList(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId), 0, false);
				}
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		adapter = new ListAdapter();
		lv_income2.setAdapter(adapter);
	}
	
	private void initMenu() {
		startDate = Utils.getFormerDay("yyyy-MM-dd HH:mm");//Utils.getFirstDayMonth();
		endDate = Utils.getCurrentTime("yyyy-MM-dd HH:mm");
		depTypeId = MainActivity.deptVo.getHisId();
		mDrawer = MenuDrawer
				.attach(this, MenuDrawer.Type.OVERLAY, Position.END);
		mDrawer.setMenuView(R.layout.menudrawer);
		mDrawer.setContentView(R.layout.hos_danger_main_stuation);
		// 菜单无阴影
		mDrawer.setDropShadowEnabled(false);
		filterMenuFragment = new FilterMenuChargeFragment();
		
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

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, false, null,
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
									queryDangerValueList(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId), 0, false);
								}else {
									Utils.showToastBGNew(context, "查不到科室数据");
									processException(0);
								}
								
							}else {
								Utils.showToastBGNew(context, "查不到科室数据");
								processException(0);
							}
						} catch (JSONException e) {
							processException(0);
							e.printStackTrace();
							Utils.showToastBGNew(context, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						processException(0);
						Utils.showToastBGNew(context, "查询科室数据失败");
					}
				});
	
	}

	private void queryDangerValueList(final CategoryItemVo vo, final int loadType, boolean isShowOn){
//		http://localhost:8080/hsptapp/doctor/creticallis/querycreticallis/701.html?dhid=79&stime=2017-02-01 00:00&etime=2017-03-01 00:00&pagecount=-1
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/doctor/creticallis/querycreticallis/701.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("dhid", vo.getDepTypeId());//"95"
		params.put("stime", vo.getStartDate());
		params.put("etime", vo.getEndDate());
		if("0".equals(vo.getAdmTypeId())) {
//			params.put("mid", "");
		} else if("1".equals(vo.getAdmTypeId())) {
			params.put("mid", configSP.getString(Constants.USER_HISID, ""));
		}
		if (loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		}
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, isShowOn, null, new VolleyListener<JSONObject>() {
			
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
						List<DangerValueVo> vosTemp = JSONArray.parseArray(result, DangerValueVo.class);
						updateAdapter(vosTemp, loadType);
					} else if("2".equals(resultCode)){
						if (loadType == 0) {
							mPtrFrameLayout.refreshComplete();
							if (vos.size() != 0) {
								vos.clear();
								adapter.notifyDataSetChanged();
								currentpage = 1;
							}
							LoadingUtils.showLoadMsg(loadView, "暂无数据");
						} else {
							lv_income2.onLoadComplete();
							lv_income2.setResultSize(0);
						}
					} else {
						processException(loadType);
					}
				} catch (JSONException e) {
					Log.e(TAG, "queryDangerValueList", e);
					processException(loadType);
				}
			}
			
			@Override
			public void error(VolleyError error) {
				Log.e(TAG, "queryDangerValueList", error.getCause());
				processException(loadType);
			}
		});
	}
	private void processException(int loadType) {
		if (loadType == 0) {
			mPtrFrameLayout.refreshComplete();
			if (vos.size() == 0) {
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			} else {
				Utils.showToastBGNew(context, "刷新数据失败");
			}
		} else {
			lv_income2.onLoadComplete();
			lv_income2.setResultSize(-1);
		}
	}
	public void updateAdapter(List<DangerValueVo> listVo, int type) {
		// 刷新操作
		if (type == 0) {
			currentpage = 1;
			vos = listVo;
			mPtrFrameLayout.refreshComplete();
		} else if (type == 1) {// 上拉加载
			vos.addAll(listVo);
			lv_income2.onLoadComplete();
		}
		
		lv_income2.setResultSize(listVo.size());
		currentpage++;
		adapter.setqVos(vos);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void scrollBottomLoadState() {
		queryDangerValueList(new CategoryItemVo(startDate, endDate, depTypeId, admTypeId), 1, false);
	}
	
	class ListAdapter extends BaseAdapter{

		
		public ListAdapter() {
			super();
		}

		private List<DangerValueVo> qVos = new ArrayList<DangerValueVo>();
		
		public List<DangerValueVo> getqVos() {
			return qVos;
		}

		public void setqVos(List<DangerValueVo> qVos) {
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
	        	convertView = layoutInflater.inflate(R.layout.danger_value_main_listview_item, null);
	        	viewHolder.tv_child_doc = (TextView) convertView.findViewById(R.id.tv_child_doc);
	        	viewHolder.tv_doc_name = (TextView) convertView.findViewById(R.id.tv_doc_name);
	        	viewHolder.tv_child_content = (TextView) convertView.findViewById(R.id.tv_child_content);
	        	viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
	        	viewHolder.doc_head = (CLRoundImageView) convertView.findViewById(R.id.doc_head);
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        DangerValueVo vo = qVos.get(position);
	        ImageLoader.getInstance().displayImage(Constants.IMAGE_STR + vo.getDocId() + ".jpg", viewHolder.doc_head);
	        viewHolder.tv_child_doc.setText(String.format("病人%s的危急值提醒", vo.getPatName()));
	        viewHolder.tv_child_content.setText(String.format("%s(就诊号:%s)%s", vo.getPatName(), vo.getAdmno(), vo.getResnewstr()));
	        viewHolder.tv_doc_name.setText(String.format("主治医生：%s", vo.getDocName()));
	        viewHolder.tv_time.setText(vo.getDatetime());
	        return convertView;
	    }
	}
	class ViewHolder{
		   TextView tv_child_doc;
		   TextView tv_child_content;
		   TextView tv_doc_name;
		   TextView tv_time;
		   CLRoundImageView doc_head;
	    }
	
	@Override
	public void onClick(CategoryItemVo vo) {
		queryDangerValueList(vo, 0, true);
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
