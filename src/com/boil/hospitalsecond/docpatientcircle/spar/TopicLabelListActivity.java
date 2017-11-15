package com.boil.hospitalsecond.docpatientcircle.spar;

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
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.adapter.MessagePlatformAdapter;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
import com.boil.hospitalsecond.tool.ctgridview.Tag;
import com.boil.hospitalsecond.tool.ptrtool.CTListView;
import com.boil.hospitalsecond.tool.ptrtool.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrDefaultHandler;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopicLabelListActivity extends BaseBackActivity implements CTPullUpListViewCallBack {
	/** 标记 */
	private static String TAG = "TopicLabelListActivity";
	/** 上下文 */
	private TopicLabelListActivity context = TopicLabelListActivity.this;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.bt_add_usual_line)
	private ImageButton bt_search;
	@ViewInject(R.id.rel_choose)
	private RelativeLayout rel_choose;
	@ViewInject(R.id.listview2)
	private CTListView listview2;
	@ViewInject(R.id.btn_add)
	private ImageButton btn_add;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;

	private Tag tg;
	private List<TopicTypeVo> vos = new ArrayList<TopicTypeVo>();
	private MessagePlatformAdapter adapter;

	private int pagecount = 10;
	private int currentpage = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.message_platform_main);
		
		ViewUtils.inject(this);
		
		initView();
		initEvent();
		initRegisterReceiver();
	}

	/**
	 * 
	 * 注册一个广播接收器。
	 * 
	 */
	private void initRegisterReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.REFRESH_ONE_TOPIC_BROADCAST_ACTION);
		intentFilter.setPriority(Integer.MAX_VALUE);
		
		// 注册广播接收器。
		registerReceiver(broadcastReceiver, intentFilter);
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
			
			if (flag == 0) {
				tg = (Tag) intent.getSerializableExtra("tagNew");
			}
			
			queryTopicTypeList(0);
		}
	};

	private void initView() {
		Utils.backClick(this, backBtn);
		tg = (Tag) getIntent().getExtras().getSerializable("tag");
		bt_save.setVisibility(View.GONE);
		bt_search.setVisibility(View.VISIBLE);
		rel_choose.setVisibility(View.GONE);
		addreTitle.setText(tg.getTitle());

		initPtr(this, mPtrFrameLayout);
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		
		listview2.setPageSize(pagecount);
		listview2.setMyPullUpListViewCallBack(this);
	}

	public void initEvent() {
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				queryTopicTypeList(0);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin()) {
					toLogin();
					return;
				}
				
				Intent intent = new Intent(TopicLabelListActivity.this, AddMessageActivity.class);
				intent.putExtra("tag", (Serializable) tg);
				intent.putExtra("intentFlag", "labelTo");
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		bt_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TopicLabelListActivity.this, TopicSearchActivity.class);
				intent.putExtra("tg", (Serializable) tg);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		adapter = new MessagePlatformAdapter(context, iconFont);
		
		listview2.setDivider(null);
		listview2.setAdapter(adapter);
	}

	/**
	 * query one of topics's list
	 */
	private void queryTopicTypeList(final int loadType) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/leavemsg/lsotherlmc/B005.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
	
		if (loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		}
		
		params.put("lmtid", tg.getId() + "");
		
		loadView.setVisibility(View.GONE);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, //
				url, //
				null, //
				params, //
				true, //
				null, //
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
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
									mPtrFrameLayout.refreshComplete();
									
									if (vos.size() != 0) {
										vos.clear();
										adapter.notifyDataSetChanged();
										currentpage = 1;
									}
									
									LoadingUtils.showLoadMsg(loadView, "暂无数据");
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
			
			if (vos.size() == 0) {
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			} else {
				Utils.showToastBGNew(context, "刷新数据失败");
			}
		} else {
			listview2.onLoadComplete();
			listview2.setResultSize(-1);
		}
	}

	public void updateAdapter(List<TopicTypeVo> listVo, int type) {
		// 刷新操作
		if (type == 0) {
			currentpage = 1;
			vos = listVo;
			mPtrFrameLayout.refreshComplete();
		} else if (type == 1) {// 上拉加载
			vos.addAll(listVo);
			listview2.onLoadComplete();
		}
		
		listview2.setResultSize(listVo.size());
		currentpage++;
		adapter.setList(vos);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		
		super.onDestroy();
	}

	@Override
	public void scrollBottomLoadState() {
		queryTopicTypeList(1);
	}
}