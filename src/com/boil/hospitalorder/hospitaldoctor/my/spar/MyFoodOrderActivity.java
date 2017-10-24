package com.boil.hospitalorder.hospitaldoctor.my.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.adapter.FoodOrderAdapter;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodOrderVo;
import com.boil.hospitalorder.utils.BaseBackActivity;
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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFoodOrderActivity extends BaseBackActivity implements CTPullUpListViewCallBack {

	private MyFoodOrderActivity context = MyFoodOrderActivity.this;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.bt_save)
	private TextView bt_save;

	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.list_food_order)
	private CTListView listOrder;
	
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;
	
	private List<FoodOrderVo> vos=new ArrayList<FoodOrderVo>();
	private List<FoodOrderVo> temp=new ArrayList<FoodOrderVo>();
	
	private FoodOrderAdapter adapter;
	public static int update=0;
	/**当前页*/
	private int currentPage=1;
	/**总共有好多页*/
	private int countPage=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_fooder_order);
		ViewUtils.inject(this);
		initView();
		initData();
		initEvent();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(update==1){
			
			queryOrderData(1);
			update=0;
		}
	}
	

	private void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("我的订单");
		bt_save.setVisibility(View.INVISIBLE);
//		volleyClient.setActivity(context);
		
		mPtrFrameLayout.setVisibility(View.VISIBLE);
		initPtr(context,mPtrFrameLayout);
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		listOrder.setPageSize(10);
		listOrder.setMyPullUpListViewCallBack(this);
	}

	private void initData() {
		
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				queryOrderData(1);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		
	}

	private void initEvent() {
		listOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Intent intent=new Intent(context,MyFoodOrderDetailActivity.class);
				intent.putExtra("FoodOrderVo", (Serializable)vos.get(arg2));
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});
	}

	
	private void setData(int pageNum){
		
		
		
		listOrder.setResultSize(temp.size());
		
		//当请求为第一页时  先清空列表
 		if(pageNum==1){
			vos.clear();
		}
		
		vos.addAll(temp);
		
		if(adapter==null){
			
			adapter=new FoodOrderAdapter(context, vos);
			listOrder.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		listOrder.onLoadComplete();
		mPtrFrameLayout.refreshComplete();
	}
	
	private void queryOrderData(final int pageNum) {

		
		
		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/dish/order/list.json?pageNum=1&userId=147&userTypeCode=10

		String url = Constants.WEB_DINNER_URL + "/canteen/dish/order/list.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNum", pageNum+"");
		params.put("userId", configSP.getString(Constants.USER_ID, ""));
		params.put("userTypeCode", "20");

		loadView.setVisibility(View.GONE);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							if ("0".equals(resultCode)) {

								String results=response.getString("results");
								org.json.JSONArray dataArray=new org.json.JSONArray(results);
//								vos=JSONArray.parseArray(dataArray.getString(4), FoodOrderVo.class);
//								
//								if(vos.size()>0){
//									
//									adapter=new FoodOrderAdapter(context, vos);
//									listOrder.setAdapter(adapter);
//									
//								}else {
//									LoadingUtils.showLoadMsg(loadView, "暂无订单数据");
//								}
								countPage=Integer.valueOf(dataArray.getString(0));
								currentPage=Integer.valueOf(dataArray.getString(2));
								
								temp=JSONArray.parseArray(dataArray.getString(4), FoodOrderVo.class);
								
								if(temp.size()>0){
									
									mPtrFrameLayout.setVisibility(View.VISIBLE);
								}else {
									LoadingUtils.showLoadMsg(loadView, "暂无订单数据");
									mPtrFrameLayout.setVisibility(View.GONE);
								}
							} else {
								LoadingUtils.showLoadMsg(loadView, "暂无订单数据");
								mPtrFrameLayout.setVisibility(View.GONE);
								
							}
							
							setData(pageNum);

						} catch (Exception e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询订单数据失败");
							mPtrFrameLayout.setVisibility(View.GONE);
							listOrder.onLoadComplete();
							mPtrFrameLayout.refreshComplete();
							
						}
					}

					@Override
					public void error(VolleyError error) {
						LoadingUtils.showLoadMsg(loadView, "查询订单数据失败");
						mPtrFrameLayout.setVisibility(View.GONE);
						listOrder.onLoadComplete();
						mPtrFrameLayout.refreshComplete();
					}
				});

	}
	
	

	@Override
	public void scrollBottomLoadState() {
		
		//请求页不能超过总页数
		if((currentPage+1)<=countPage){
			
			queryOrderData(currentPage+1);
		}else {
			
			listOrder.setResultSize(1);
		}
	}
}
