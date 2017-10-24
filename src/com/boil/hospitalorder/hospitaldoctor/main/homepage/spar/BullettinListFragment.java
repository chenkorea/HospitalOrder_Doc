package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.CoopHosNoticeAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ChannelItem;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.DocCircleInformation;
import com.boil.hospitalorder.utils.CTListView;
import com.boil.hospitalorder.utils.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.PtrClassicFrameLayout;
import com.boil.hospitalorder.utils.PtrDefaultHandler;
import com.boil.hospitalorder.utils.PtrFrameLayout;
import com.boil.hospitalorder.utils.PtrHandler;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class BullettinListFragment extends BaseFragment implements
		CTPullUpListViewCallBack {

	private CTListView listview;

	private CoopHosNoticeAdapter adapter;

	private PtrClassicFrameLayout mPtrFrameLayout;
	
	private LinearLayout loadView;
	
	private ChannelItem chanelItemVo = null;
	
	private int currentpage = 1;
	private int pagecount = 10; 

	private List<DocCircleInformation> list = new ArrayList<DocCircleInformation>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.subscribe_fragment_diet_item,
				null);

		initView(view);
		initData();
		initEvent();
		querySubListDetail(0);
		return view;
	}

	public void initView(View view) {
		listview = (CTListView) view.findViewById(R.id.listview);
		loadView = (LinearLayout) view.findViewById(R.id.loading_view_952658);
		mPtrFrameLayout = (PtrClassicFrameLayout) view
				.findViewById(R.id.rotate_header_list_view_frame);
		initPtr(getActivity(), mPtrFrameLayout);
		listview.setPageSize(pagecount);
		listview.setMyPullUpListViewCallBack(this);
		adapter = new CoopHosNoticeAdapter(getActivity());
		listview.setAdapter(adapter);
	}
	
	public void initEvent() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(), SubscribeArticleDetailActivity.class);
				intent.putExtra("flag", "3");
				intent.putExtra("id", list.get(arg2).getId());
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
			
		});
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			//fragment可见时加载数据
			if(!Utils.isValidate(list)){
				if(chanelItemVo != null) {
					querySubListDetail(0);
				}
			} else {
				adapter.setList(list);
				adapter.notifyDataSetChanged();
			}
		}else{
			//fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	private void querySubListDetail(final int loadType) {
//		http://localhost:8080/hsptapp/doctor/notice/lsnotice/402.html?hid=2&tid=16
		String url = "http://58.42.232.110:8086/hsptapp/doctor/notice/lsnotice/402.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", "-1");
		params.put("tid", chanelItemVo.getId());//2代表订阅，3代表公告，4代表通知
		if(loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		} else {
			list.clear();
		}

		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						
						try {
							String resultCode = response.getString("resultCode");
							if ("1".equals(resultCode)) {
								
								String result = response.getString("t");
								List<DocCircleInformation> vosTemp = JSON.parseArray(result, DocCircleInformation.class);
								updateAdapter(vosTemp, loadType);

							} else if("2".equals(resultCode)){
								LoadingUtils.showLoadMsg(loadView, "暂无数据");
							} else {
								LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						mPtrFrameLayout.refreshComplete();
						LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
					}
				});
	}
	
	private void initData() {
		chanelItemVo = (ChannelItem) getArguments().getSerializable("chnnelItem");
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				querySubListDetail(0);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		
	}
	
	public void updateAdapter(List<DocCircleInformation> listVo, int type) {
		
		//刷新操作
		if(type == 0) {
			currentpage = 1;
			list = listVo;
			mPtrFrameLayout.refreshComplete();
		} else if(type == 1) {//上拉加载
			System.out.println("==上拉加载==");
			listview.onLoadComplete();
			list.addAll(listVo);
			listview.setResultSize(listVo.size());
			currentpage++;
		}
		adapter.setList(list);
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void scrollBottomLoadState() {
		querySubListDetail(1);
	}
}