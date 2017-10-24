package com.boil.hospitalorder.hospitaldoctor.my.spar;

import java.io.Serializable;
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
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.my.adapter.CTColligateServiceAdapter;
import com.boil.hospitalorder.hospitaldoctor.my.modal.CanTeenVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.CooperationHosVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.OrderHosAdapter;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ColligateServiceActivity extends BaseBackActivity{
	
	private Context context = ColligateServiceActivity.this;

	@ViewInject(R.id.listview)
	private ListView listview;
	
	@ViewInject(R.id.listview2)
	private ListView listview2;
	
	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	
	@ViewInject(R.id.rel_choose)
	private RelativeLayout rel_choose;
	@ViewInject(R.id.v_order_trans)
	private View transView;
	@ViewInject(R.id.tv_department)
	private TextView tv_department;
	@ViewInject(R.id.ll_canteen)
	private LinearLayout ll_canteen;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private CTColligateServiceAdapter adapter1;
	private CTColligateServiceAdapter adapter2;
	
	/**食堂列表*/
	private List<CanTeenVo> teenVos=new ArrayList<CanTeenVo>();
	/**菜的类别*/
	private List<FoodVo> foodTypeVos=new ArrayList<FoodVo>();
	/**口味*/
	private List<FoodVo> foodTasteVos=new ArrayList<FoodVo>();
	
	private PopupWindow mWindow;
	private List<CooperationHosVo> hosVos = new ArrayList<CooperationHosVo>();
	private String currHosId;
	private String currHosName;
	//已选择的医院id
	private String hos_id;
	private SharedPreferences sp;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_colligate_service_main);
		
		ViewUtils.inject(this);
		initView();
		initData();
		initEvent();
		
		/*
		String json="{\"resultCode\":0,\"resultMsg\":\"成功\",\"result\":null,\"results\":[1,1,1,1,[{\"id\":102,\"canteenName\":\"音乐餐厅\",\"canteenLogoId\":null,\"canteenLogoPath\":null,\"createTime\":\"2016-11-04\",\"updateTime\":\"2016-11-04\"},{\"id\":101,\"canteenName\":\"职工食堂\",\"canteenLogoId\":null,\"canteenLogoPath\":null,\"createTime\":\"2016-11-04\",\"updateTime\":\"2016-11-04\"},{\"id\":100,\"canteenName\":\"放心食堂\",\"canteenLogoId\":null,\"canteenLogoPath\":null,\"createTime\":\"2016-11-04\",\"updateTime\":\"2016-11-04\"}],[{\"id\":7,\"parentId\":1,\"dictName\":\"东北菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":11,\"parentId\":1,\"dictName\":\"清真菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":10,\"parentId\":1,\"dictName\":\"江苏菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":5,\"parentId\":1,\"dictName\":\"湘菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":6,\"parentId\":1,\"dictName\":\"鲁菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":12,\"parentId\":1,\"dictName\":\"其他\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":8,\"parentId\":1,\"dictName\":\"北京菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":9,\"parentId\":1,\"dictName\":\"闽菜\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":4,\"parentId\":1,\"dictName\":\"粤菜\",\"deleteCode\":0,\"createTime\":1477970919000,\"updateTime\":1477970919000,\"parent\":null,\"childs\":[]},{\"id\":3,\"parentId\":1,\"dictName\":\"川菜\",\"deleteCode\":0,\"createTime\":1477970919000,\"updateTime\":1477970919000,\"parent\":null,\"childs\":[]},{\"id\":2,\"parentId\":1,\"dictName\":\"贵州菜\",\"deleteCode\":0,\"createTime\":1477970919000,\"updateTime\":1477970919000,\"parent\":null,\"childs\":[]}],[{\"id\":14,\"parentId\":13,\"dictName\":\"滋补养生\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":15,\"parentId\":13,\"dictName\":\"疾病防治\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":19,\"parentId\":13,\"dictName\":\"抗衰老\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":17,\"parentId\":13,\"dictName\":\"清热解毒\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":18,\"parentId\":13,\"dictName\":\"增强抵抗力\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]},{\"id\":16,\"parentId\":13,\"dictName\":\"促进消化\",\"deleteCode\":0,\"createTime\":1477970920000,\"updateTime\":1477970920000,\"parent\":null,\"childs\":[]}],[{\"id\":21,\"parentId\":20,\"dictName\":\"清淡\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":22,\"parentId\":20,\"dictName\":\"咸鲜\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":23,\"parentId\":20,\"dictName\":\"甜\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":28,\"parentId\":20,\"dictName\":\"水果味\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":25,\"parentId\":20,\"dictName\":\"酸\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":26,\"parentId\":20,\"dictName\":\"苦\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":27,\"parentId\":20,\"dictName\":\"咖喱\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]},{\"id\":24,\"parentId\":20,\"dictName\":\"辣\",\"deleteCode\":0,\"createTime\":1477970921000,\"updateTime\":1477970921000,\"parent\":null,\"childs\":[]}],[{\"id\":207,\"parentId\":29,\"dictName\":\"小火锅\",\"deleteCode\":0,\"createTime\":1478157273000,\"updateTime\":1478157273000,\"parent\":null,\"childs\":[]},{\"id\":201,\"parentId\":29,\"dictName\":\"肉末面\",\"deleteCode\":0,\"createTime\":1477984051000,\"updateTime\":1478143955000,\"parent\":null,\"childs\":[]},{\"id\":205,\"parentId\":29,\"dictName\":\"干锅\",\"deleteCode\":0,\"createTime\":1477984193000,\"updateTime\":1478143120000,\"parent\":null,\"childs\":[]},{\"id\":206,\"parentId\":29,\"dictName\":\"爆炒\",\"deleteCode\":0,\"createTime\":1477984279000,\"updateTime\":1477984279000,\"parent\":null,\"childs\":[]},{\"id\":204,\"parentId\":29,\"dictName\":\"盖饭\",\"deleteCode\":0,\"createTime\":1477984123000,\"updateTime\":1477984123000,\"parent\":null,\"childs\":[]},{\"id\":203,\"parentId\":29,\"dictName\":\"炒菜\",\"deleteCode\":0,\"createTime\":1477984112000,\"updateTime\":1477984112000,\"parent\":null,\"childs\":[]},{\"id\":202,\"parentId\":29,\"dictName\":\"肉末粉\",\"deleteCode\":0,\"createTime\":1477984062000,\"updateTime\":1477984062000,\"parent\":null,\"childs\":[]},{\"id\":200,\"parentId\":29,\"dictName\":\"火锅\",\"deleteCode\":0,\"createTime\":1477983961000,\"updateTime\":1477983973000,\"parent\":null,\"childs\":[]}]]}";
		
		try {
			JSONObject response=new JSONObject(json);
			
			String results=response.getString("results");
			
			JSONArray dataArray=new JSONArray(results);
			
			teenVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4), CanTeenVo.class);
			
			foodTypeVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(8),FoodVo.class);
			
			foodTasteVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(7), FoodVo.class);
			
			
			initData();
		} catch (Exception e) {
		}*/
		
		
		queryHosList();
	}
	
	public void initView(){
		Utils.backClick(this, backBtn);
		bt_save.setText("我的订单");
		addreTitle.setText("院内资源");
		
		volleyClient.setActivity(this);
		
		sp = Utils.getSharedPreferences(this);
		editor = sp.edit();
		//获取保存的已选择的医院
		hos_id = sp.getString("select_hos_id", "");
	}
	
	public void initData() {
		
		/*
		List<ColligateServiceVo> vos1 = new ArrayList<ColligateServiceVo>();
		List<ColligateServiceVo> vos2 = new ArrayList<ColligateServiceVo>();
		
		for (int i = 0; i < 4; i++) {
			ColligateServiceVo vo = null;
			if(i == 3) {
				vo = new ColligateServiceVo(i + "", "肿瘤医院护工服务中心");
				vos2.add(vo);
			} else {
				if(i == 0) {
					vo = new ColligateServiceVo(i + "", "职工食堂");
				} else if(i == 1) {
					vo = new ColligateServiceVo(i + "", "中山园食堂");
				} else if(i == 2) {
					vo = new ColligateServiceVo(i + "", "探花私房菜");
				}
				vos1.add(vo);
			}
			
		} 
		adapter1 = new CTColligateServiceAdapter(vos1, context);
		adapter2 = new CTColligateServiceAdapter(vos2, context);
		listview.setAdapter(adapter1);
		listview2.setAdapter(adapter2);*/
		
		ll_canteen.setVisibility(View.VISIBLE);
		adapter1 = new CTColligateServiceAdapter(teenVos, context);
		listview.setAdapter(adapter1);
		
	}
	
	public void initEvent() {
		
		//我的订单
		bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isLogin()){
					
					Intent intent = new Intent(context, MyFoodOrderActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
				}else {
					toLogin();
				}
				
			}
		});
		
		rel_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(hosVos.size()==0){
					
					queryHosList();
					return;
					
				}
				
				showPhotoDialog();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, StaffDiningOrderActivity.class);
				intent.putExtra("TeenId", teenVos.get(arg2).getId());
				intent.putExtra("FoodType", (Serializable)foodTypeVos);
				intent.putExtra("FoodTaste", (Serializable)foodTasteVos);
				intent.putExtra("hid", currHosId);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});
	}
	
	/**
	 * 查询食堂列表
	 */
	private void queryHallData(){
		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/list.json?pageNum=1

		String url = Constants.WEB_DINNER_URL + "/canteen/list.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hospitalId", currHosId);
		params.put("pageNum", "1");

		
		ll_canteen.setVisibility(View.GONE);
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode=response.getString("resultCode");
							if("0".equals(resultCode)){
								
								String results=response.getString("results");
								
								JSONArray dataArray=new JSONArray(results);
								
								teenVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4), CanTeenVo.class);
								
								foodTypeVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(8),FoodVo.class);
								
								foodTasteVos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(7), FoodVo.class);
								
								
								initData();
								
								/*
								//餐厅数据
								JSONArray canTeenArray=dataArray.getJSONArray(4);
								
								for(int i=0;i<canTeenArray.length();i++){
									
									JSONObject canteenObject=canTeenArray.getJSONObject(i);
									String id=canteenObject.getString("id");
									String canteenName=canteenObject.getString("canteenName");
									String canteenLogoId=canteenObject.getString("canteenLogoId");
									String canteenLogoPath=canteenObject.getString("canteenLogoPath");
									String createTime=canteenObject.getString("createTime");
									String updateTime=canteenObject.getString("updateTime");
									
									CanTeenVo vo=new CanTeenVo(id, canteenName, canteenLogoId, canteenLogoPath, createTime, updateTime);
									teenVos.add(vo);
									
								}
								
								//菜的类别
								JSONArray foodTypeArray=dataArray.getJSONArray(8);
								for(int i=0;i<foodTypeArray.length();i++){
									JSONObject typeObject=foodTypeArray.getJSONObject(i);
									String id=typeObject.getString("id");
									String parentId=typeObject.getString("parentId");
									String dictName=typeObject.getString("dictName");
									String deleteCode=typeObject.getString("deleteCode");
									String createTime=typeObject.getString("createTime");
									String updateTime=typeObject.getString("updateTime");
									String parent=typeObject.getString("parent");
									String childs=typeObject.getString("childs");
									
									
									FoodVo vo=new FoodVo(id, parentId, dictName, deleteCode, createTime, updateTime, parent, childs);
									
									
								}*/
								
								
							}else {
								
								LoadingUtils.showLoadMsg(loadView, "暂无食堂数据");
								
							}
							
							
						} catch (Exception e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询食堂数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						LoadingUtils.showLoadMsg(loadView, "查询食堂数据失败");
					}
				});
		
	}
	
	/**
	 * 选择医院窗口
	 */
	private void showPhotoDialog() {
		if (hosVos.size() == 0) {

			Utils.showToastBGNew(context, "暂无医院数据");
			return;
		}

		transView.setVisibility(View.VISIBLE);
		View view = LayoutInflater.from(this).inflate(R.layout.layout_order_dialog, null);
		ListView listView = (ListView) view.findViewById(R.id.list_order_hos);

		OrderHosAdapter adapter = new OrderHosAdapter(context, hosVos);
		listView.setAdapter(adapter);

		mWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mWindow.setBackgroundDrawable(new BitmapDrawable());
		mWindow.setAnimationStyle(0);
		mWindow.showAtLocation(rel_choose, Gravity.TOP, 0, Utils.dip2px(context, 110));

		mWindow.setOutsideTouchable(true);
		mWindow.setFocusable(true);
		mWindow.update();

		mWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				transView.setVisibility(View.GONE);
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				tv_department.setText(hosVos.get(arg2).getName());

				currHosId = hosVos.get(arg2).getId();
				currHosName = hosVos.get(arg2).getName();

				editor.putString("select_hos_id", currHosId);
				editor.commit();

				queryHallData();

				mWindow.dismiss();
			}

			
		});

	}
	
	/**
	 * 查询医院列表
	 */
	private void queryHosList() {

		String url = "http://58.42.232.110:8086/hsptapp/hpin/listhspt/1001.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("state", "1");

		ll_canteen.setVisibility(View.GONE);
		loadView.setVisibility(View.GONE);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							if ("1".equals(resultCode)) {

								String result = response.getString("t");

								hosVos = com.alibaba.fastjson.JSONArray.parseArray(result, CooperationHosVo.class);

								if (hosVos.size() > 0) {
									
									int index = 0;
									for (CooperationHosVo vo : hosVos) {

										if (vo.getId().equals(hos_id)) {
											index = hosVos.indexOf(vo);
											break;
										}

									}
									
									tv_department.setText(hosVos.get(index).getName());
									currHosId = hosVos.get(index).getId();
									currHosName = hosVos.get(index).getName();
									
									queryHallData();
									
								}

							} else {

								Utils.showToastBGNew(context, "暂无医院数据");
								LoadingUtils.showLoadMsg(loadView, "暂无食堂数据");
							} 
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询医院数据失败");
							LoadingUtils.showLoadMsg(loadView, "暂无食堂数据");
							
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询医院数据失败");
						LoadingUtils.showLoadMsg(loadView, "暂无食堂数据");
					}
				});

	}
	
}
