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
import com.boil.hospitalsecond.tool.ctedittext.IconCenterEditText;
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

/**
 * 
 * 医生端查看留言。
 *
 * @author ChenYong(修改)
 * @date 2016-12-19
 *
 */
public class MessagePlatformActivity extends BaseBackActivity implements CTPullUpListViewCallBack {
	/** 标记 */
	private static final String TAG = "MessagePlatformActivity";
	private Context context = MessagePlatformActivity.this;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.listview2)
	private CTListView listview2;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.btn_add)
	private ImageButton btn_add;
	@ViewInject(R.id.rel_choose)
	private RelativeLayout rel_choose;
	@ViewInject(R.id.tv_topic)
	private TextView tv_topic;
	@ViewInject(R.id.tv_topic_num)
	private TextView tv_topic_num;
	@ViewInject(R.id.add_small_btn)
	private TextView add_small_btn;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.search_edittext)
	private IconCenterEditText search_edittext;

	private MessagePlatformAdapter adapter;
	private int pagecount = 10;
	private int currentpage = 1;
	private List<Tag> listTgs;
	private List<TopicTypeVo> vos = new ArrayList<TopicTypeVo>();
	private boolean queryTopicTypeFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.message_platform_main);
		
		ViewUtils.inject(this);
		
		// volleyClient.setActivity(this);
		
		initView();
		initData();
		initEvent();
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
				queryTopicType();
			} else if (flag == 2) {
				queryTopicTypeList(0);
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
		
		intentFilter.addAction(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
		intentFilter.setPriority(Integer.MAX_VALUE);
		
		registerReceiver(broadcastReceiver, intentFilter);
	}

	public void initView() {
		Utils.backClick(this, backBtn);

		addreTitle.setText("留言平台");
		addreTitle.setVisibility(View.GONE);

		bt_save.setText("我的留言");
		bt_save.setVisibility(View.GONE);

		add_small_btn.setTypeface(iconFont2);
		add_small_btn.setVisibility(View.VISIBLE);

		search_edittext.setVisibility(View.VISIBLE);
		Utils.changeViewFocus(search_edittext, false);
		
		search_edittext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TopicSearchActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				Utils.changeViewFocus(search_edittext, false);
			}
		});

		initPtr(this, mPtrFrameLayout);
		
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		
		listview2.setPageSize(pagecount);
		listview2.setMyPullUpListViewCallBack(this);

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
		
		adapter = new MessagePlatformAdapter(context, iconFont);
		
		listview2.setDivider(null);
		listview2.setAdapter(adapter);
	}

	private void initData() {
		listTgs = new ArrayList<Tag>();
		
		queryTopicType();
	}

	/**
	 * 
	 * 查询所有留言。
	 * 
	 * @param loadType 加载类型
	 * 
	 */
	private void queryTopicTypeList(final int loadType) {
		if (!queryTopicTypeFlag) {
			return;
		}
		
		StringBuilder lmtids = new StringBuilder();
		
		for (int i = 0; i < listTgs.size(); i++) {
			if (i < (listTgs.size() - 1)) {
				lmtids.append(listTgs.get(i).getId());
				lmtids.append("^");
			} else {
				lmtids.append(listTgs.get(i).getId());
			}
		}
		
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/leavemsg/lsalllmc/503.html";
		
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		
		if (listTgs.size() > 0) {
			params.put("lmtids", lmtids.toString());
		}
		
		if (loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
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

	public void initEvent() {
		add_small_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MessageChannelActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		rel_choose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utils.isValidate(listTgs)) {
					Intent intent = new Intent(context, TopicLabelChooseActivity.class);
					intent.putExtra("tags", (Serializable) listTgs);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				} else {
					Utils.showToastBGNew(context, "没有查到分类，暂不能选择");
				}
			}
		});
	}

	/**
	 * 查询全部分类
	 */
	private void queryTopicType() {
		queryTopicTypeFlag = false;
		
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/leavemsg/lslmtype/502.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("pagecount", "-1");
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								queryTopicTypeFlag = true;
								
								String result = response.getString("t");
								JSONArray array = new JSONArray(result);
								
								listTgs.clear();
								
								for (int i = 0; i < array.length(); i++) {
									JSONObject jsonObj = array.getJSONObject(i);
									String id = jsonObj.getString("id");
									String name = jsonObj.getString("name");
									String isSub = jsonObj.getString("issub");

									// 只显示已订阅的话题
									if ("1".equals(isSub)) {
										Tag tg = new Tag(Integer.parseInt(id), name);
										tg.setChecked(true);
										listTgs.add(tg);
									}
								}

								tv_topic_num.setText(String.format("%s个话题", listTgs.size() + ""));
								
								queryTopicTypeList(0);
							}
						} catch (JSONException e) {
							Log.e(TAG, "queryTopicType", e);
							
							Utils.showToastBGNew(context, "查询我的话题失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询我的话题失败");
					}
				});
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
	public void scrollBottomLoadState() {
		queryTopicTypeList(1);
	}
}