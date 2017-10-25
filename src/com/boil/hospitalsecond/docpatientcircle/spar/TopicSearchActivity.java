package com.boil.hospitalsecond.docpatientcircle.spar;

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
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.adapter.MessagePlatformAdapter;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
import com.boil.hospitalsecond.tool.ctedittext.IconCenterEditText;
import com.boil.hospitalsecond.tool.ctedittext.IconCenterEditText.OnDeleteClickListener;
import com.boil.hospitalsecond.tool.ctedittext.IconCenterEditText.OnSearchClickListener;
import com.boil.hospitalsecond.tool.ctgridview.Tag;
import com.boil.hospitalsecond.tool.ptrtool.CTListView;
import com.boil.hospitalsecond.tool.ptrtool.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrDefaultHandler;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicSearchActivity extends BaseBackActivity implements CTPullUpListViewCallBack, OnDeleteClickListener {
	/** 标签 */
	private static final String TAG = "TopicSearchActivity";
	/** 上下文 */
	private TopicSearchActivity context = TopicSearchActivity.this;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.listview2)
	private CTListView listview2;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.search_edittext)
	private IconCenterEditText search_edittext;
	@ViewInject(R.id.btn_cancel)
	private TextView btn_cancel;
	
	private int pagecount = 10;
	private int currentpage = 1;
	private String keyWords = "";
	private Tag tag = null;
	private List<TopicTypeVo> vos = new ArrayList<TopicTypeVo>();
	private MessagePlatformAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.topic_search_list_main);
		
		ViewUtils.inject(this);
		
		initView();
		initRegisterReceiver();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		
		super.onDestroy();
	}
	
	/**
	 * 
	 * 广播接收器。
	 * 
	 */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int flag = intent.getIntExtra("flag", 0);
			
			if (flag == 1) {
				queryTopicTypeList(0, search_edittext.getText().toString().trim());
			}
		}
	};
	
	/**
	 * 
	 * 注册广播。
	 * 
	 */
	private void initRegisterReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		
		intentFilter.addAction(Constants.TOPIC_SEARCH_BROADCAST_ACTION);
		intentFilter.setPriority(Integer.MAX_VALUE);
		
		registerReceiver(broadcastReceiver, intentFilter);
	}

	@SuppressLint("NewApi")
	private void initView() {
		Utils.backClick(this, backBtn);
		
		search_edittext.setVisibility(View.VISIBLE);
		search_edittext.setListener2(this);

		if (getIntent().getExtras() != null) {
			tag = (Tag) getIntent().getExtras().getSerializable("tg");
			search_edittext.setHint("搜索" + "#" + tag.getTitle() + "#分类");
		} else {
			search_edittext.setHint("搜索全部分类");
		}

		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (search_edittext.getText().toString().trim().length() <= 0) {
					return;
				}
				
				queryTopicTypeList(0, search_edittext.getText().toString().trim());
			}
		});

		search_edittext.setOnSearchClickListener(new OnSearchClickListener() {
			@Override
			public void onSearchClick(View view) {
				if (search_edittext.getText().toString().trim().length() <= 0) {
					return;
				}
				
				queryTopicTypeList(0, search_edittext.getText().toString().trim());
			}
		});

		initPtr(this, mPtrFrameLayout);
		listview2.setPageSize(pagecount);
		listview2.setMyPullUpListViewCallBack(this);

		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (search_edittext.getText().toString().trim().length() <= 0) {
					mPtrFrameLayout.refreshComplete();
					return;
				}
				
				queryTopicTypeList(0, search_edittext.getText().toString().trim());
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		
		adapter = new MessagePlatformAdapter(context, iconFont);

		listview2.setDivider(null);
		listview2.setAdapter(adapter);
	}

	/**
	 * query all of topics's list
	 */
	private void queryTopicTypeList(final int loadType, final String keyWord) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/leavemsg/findlmc/B006.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("keyword", keyWord);
		
		if (tag != null) {
			params.put("lmtid", tag.getId() + "");
		}
		
		if (loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		}
		
		if (!keyWords.equals(keyWord)) {
			vos.clear();
			adapter.notifyDataSetChanged();
		}
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, //
				url, //
				null, //
				params, //
				true, //
				null, //
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						keyWords = keyWord;
						
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								String result = response.getString("t");
								List<TopicTypeVo> vosTemp = com.alibaba.fastjson.JSONArray.parseArray(result, TopicTypeVo.class);
								JSONArray jsonArray = new JSONArray(result);

								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObj = jsonArray.getJSONObject(i);
									jsonObj = jsonObj.getJSONObject("users");
									vosTemp.get(i).setUserId(jsonObj.getString("id"));

									if (jsonObj.has("username")) {
										vosTemp.get(i).setUserName(jsonObj.getString("username"));
									} else {
										vosTemp.get(i).setUserName("");
									}
								}
								
								updateAdapter(vosTemp, loadType);
							} else if ("2".equals(resultCode)) {
								if (loadType == 0) {
									Utils.showToastBGNew(context, "没有搜索到相关数据");
									
									mPtrFrameLayout.refreshComplete();
									
									if (vos.size() != 0) {
										vos.clear();
										adapter.notifyDataSetChanged();
										currentpage = 1;
									}
								} else {
									listview2.onLoadComplete();
									listview2.setResultSize(0);
								}
							} else {
								processException(loadType);
							}
						} catch (JSONException e) {
							Log.e(TAG, "queryTopicTypeList", e);
							
							processException(loadType);
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "queryTopicTypeList", error.getCause());
						
						processException(loadType);
					}
				});
	}

	private void processException(int loadType) {
		if (loadType == 0) {
			mPtrFrameLayout.refreshComplete();
			Utils.showToastBGNew(context, "搜索数据失败");
		} else {
			listview2.onLoadComplete();
			listview2.setResultSize(-1);
		}
	}

	@Override
	public void scrollBottomLoadState() {
		queryTopicTypeList(1, search_edittext.getText().toString().trim());
	}

	public void updateAdapter(List<TopicTypeVo> listVo, int type) {
		// 刷新操作
		if (type == 0) {
			currentpage = 1;
			vos = listVo;
			mPtrFrameLayout.refreshComplete();
			
			// 上拉加载
		} else if (type == 1) {
			vos.addAll(listVo);
			listview2.onLoadComplete();
		}
		
		listview2.setResultSize(listVo.size());
		currentpage++;
		adapter.setList(vos);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDeleteClick(View view) {
		vos.clear();
		
		adapter.notifyDataSetChanged();
		currentpage = 1;
	}
}