package com.boil.hospitalsecond.docpatientcircle.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.adapter.MessagePlatformDetailAdapter;
import com.boil.hospitalsecond.docpatientcircle.model.AreaVo;
import com.boil.hospitalsecond.docpatientcircle.model.DeptVo;
import com.boil.hospitalsecond.docpatientcircle.model.HospitalsVo;
import com.boil.hospitalsecond.docpatientcircle.model.Mediciners;
import com.boil.hospitalsecond.docpatientcircle.model.MedicinersReplyMessage;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 回复详细 Activity。
 * 
 * @author ChenYong(修改)
 * @date 2016-12-20
 *
 */
public class MessagePlatformDetailActivity extends BaseBackActivity implements CTPullUpListViewCallBack {
	/** 标记 */
	private static final String TAG = "MessagePlatformDetailActivity";
	/** 上下文 */
	private MessagePlatformDetailActivity context = MessagePlatformDetailActivity.this;
	@ViewInject(R.id.listview)
	private CTListView listview;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.iv_chat)
	private TextView iv_chat;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.tv_message_title)
	private TextView tv_message_title;
	@ViewInject(R.id.tv_content)
	private TextView tv_content;
	@ViewInject(R.id.tv_last_name)
	private TextView tv_last_name;
	@ViewInject(R.id.tv_time)
	private TextView tv_time;
	@ViewInject(R.id.tv_count)
	private TextView tv_count;
	@ViewInject(R.id.tv_back_count)
	private TextView tv_back_count;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.et_content)
	private EditText et_content;
	@ViewInject(R.id.btn_commit)
	private BootstrapButton btn_commit;

	private String content;

	private MessagePlatformDetailAdapter adapter;
	private int pagecount = 10;
	private int currentpage = 1;
	private List<MedicinersReplyMessage> vos = new ArrayList<MedicinersReplyMessage>();
	private TopicTypeVo topicVo;
	/** 是否可以删除评论的标记：true-可以删除；false-不可以删除 */
	private boolean removelmrFlag;
	/** 是否可以回复评论的标记：true-可以回复；false-不可以回复 */
	private boolean replyMessageFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.message_platform_detail);
		
		ViewUtils.inject(this);
		
		initView();
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
				int replyNum = Integer.valueOf(topicVo.getReplyNum());
				
				replyNum = (replyNum + 1);
				
				topicVo.setReplyNum(String.valueOf(replyNum));
				
				tv_count.setText(topicVo.getReplyNum());
				tv_back_count.setText(String.format("回复	%s", topicVo.getReplyNum()));

				queryMessageBackList(0);
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
		intentFilter.addAction(Constants.MESSAGE_PLATFORM_DETAIL_BROADCAST_ACTION);
		intentFilter.setPriority(Integer.MAX_VALUE);
		
		registerReceiver(broadcastReceiver, intentFilter);
	}

	public void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("留言");
		iv_chat.setTypeface(iconFont);
		bt_save.setVisibility(View.INVISIBLE);
		topicVo = (TopicTypeVo) getIntent().getSerializableExtra("TopicVo");
		tv_message_title.setText(topicVo.getTitle());
		tv_content.setText(topicVo.getContent());
		tv_last_name.setText(Utils.isValidateStr(topicVo.getUserName()) ? topicVo.getUserName() : "");
		tv_time.setText(topicVo.getCreatetime());
		tv_count.setText(topicVo.getReplyNum());
		tv_back_count.setText(String.format("回复	%s", topicVo.getReplyNum()));
		
		removelmrFlag = true;
		replyMessageFlag = true;

		initPtr(this, mPtrFrameLayout);
	}
	
	public void initEvent() {
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		
		listview.setPageSize(pagecount);
		listview.setMyPullUpListViewCallBack(this);

		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				queryMessageBackList(0);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		
		adapter = new MessagePlatformDetailAdapter(context, iconFont, configSP.getString(Constants.USER_ID, ""));
		listview.setAdapter(adapter);
		
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				content = et_content.getText().toString();

				if (StringUtils.isBlank(content)) {
					Utils.showToastBGNew(context, "请输入回复的内容");

					return;
				}

				replyMessage();
			}
		});
	}

	private void processException(int loadType) {
		if (loadType == 0) {
			mPtrFrameLayout.refreshComplete();
			
			if (vos.size() == 0) {
				mPtrFrameLayout.setVisibility(View.GONE);
				
				LoadingUtils.showLoadMsg(loadView, "加载回复失败");
			} else {
				Utils.showToastBGNew(context, "刷新回复失败");
			}
		} else {
			listview.onLoadComplete();
			listview.setResultSize(-1);
		}
	}

	public void updateAdapter(List<MedicinersReplyMessage> listVo, int type) {
		// 刷新操作
		if (type == 0) {
			currentpage = 1;
			vos = listVo;
			mPtrFrameLayout.refreshComplete();
		} else if (type == 1) {// 上拉加载
			vos.addAll(listVo);
			listview.onLoadComplete();
		}
		
		listview.setResultSize(listVo.size());
		currentpage++;
		adapter.setList(vos);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void scrollBottomLoadState() {
		queryMessageBackList(1);
	}
	
	/**
	 * 
	 * 根据留言 ID 查询留言。
	 * 
	 * @param loadType 加载类型
	 * 
	 */
	private void queryMessageBackList(final int loadType) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/lslmr/506.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("lmcId", topicVo.getId());
		params.put("mid", configSP.getString(Constants.USER_ID, ""));

		if (loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		}

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
								JSONArray jsonArray = new JSONArray(response.getString("t"));
								List<MedicinersReplyMessage> tmpMedicinersReplyMessages = new ArrayList<MedicinersReplyMessage>();

								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObject1 = jsonArray.getJSONObject(i);

									// 医生回复留言实例
									MedicinersReplyMessage medicinersReplyMessage = new MedicinersReplyMessage();
									// 医生实例
									Mediciners mediciners = new Mediciners();
									// 医院映射实例
									HospitalsVo hospitalsVo = new HospitalsVo();
									// 院区映射实例
									AreaVo areaVo = new AreaVo();
									// 部门映射实例
									DeptVo deptVo = new DeptVo();

									medicinersReplyMessage.setId(jsonObject1.getLong("id"));
									medicinersReplyMessage.setContent(jsonObject1.getString("content"));
									medicinersReplyMessage.setCreatetime(jsonObject1.getString("createtime"));
									medicinersReplyMessage.setPostive(jsonObject1.getInt("postive"));

									if (jsonObject1.has("isPostive")) {
										medicinersReplyMessage.setIsPostive(jsonObject1.getInt("isPostive"));
									}

									JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("mediciners"));
									
									if (jsonObject2.has("id")) {
										mediciners.setId(jsonObject2.getLong("id"));
									}
									
									if (jsonObject2.has("hisId")) {
										mediciners.setHisId(jsonObject2.getLong("hisId"));
									}
									
									if (jsonObject2.has("name")) {
										mediciners.setName(jsonObject2.getString("name"));
									}
									
									if (jsonObject2.has("doctortype")) {
										mediciners.setDoctortype(jsonObject2.getString("doctortype"));
									}
									
									if (jsonObject2.has("payway")) {
										mediciners.setPayway(jsonObject2.getInt("payway"));
									}
									
									if (jsonObject2.has("picture")) {
										mediciners.setPicture(jsonObject2.getString("picture"));
									}

									if (jsonObject2.has("hospitals")) {
										JSONObject jsonObject31 = new JSONObject(jsonObject2.getString("hospitals"));
										
										if (jsonObject31.has("id")) {
											hospitalsVo.setId(jsonObject31.getLong("id"));
										}
										
										if (jsonObject31.has("name")) {
											hospitalsVo.setName(jsonObject31.getString("name"));
										}
									}

									if (jsonObject2.has("area")) {
										JSONObject jsonObject32 = new JSONObject(jsonObject2.getString("area"));
										
										if (jsonObject32.has("id")) {
											areaVo.setId(jsonObject32.getLong("id"));
										}
										
										if (jsonObject32.has("areaname")) {
											areaVo.setAreaname(jsonObject32.getString("areaname"));
										}
									}

									if (jsonObject2.has("dept")) {
										JSONObject jsonObject33 = new JSONObject(jsonObject2.getString("dept"));
										
										if (jsonObject33.has("id")) {
											deptVo.setId(jsonObject33.getLong("id"));
										}
										
										if (jsonObject33.has("hisId")) {
											deptVo.setHisId(jsonObject33.getLong("hisId"));
										}
										
										if (jsonObject33.has("name")) {
											deptVo.setName(jsonObject33.getString("name"));
										}
									}

									medicinersReplyMessage.setMediciners(mediciners);
									mediciners.setHospitalsVo(hospitalsVo);
									mediciners.setAreaVo(areaVo);
									mediciners.setDeptVo(deptVo);

									tmpMedicinersReplyMessages.add(medicinersReplyMessage);
								}
								
								mPtrFrameLayout.setVisibility(View.VISIBLE);

								updateAdapter(tmpMedicinersReplyMessages, loadType);
							} else if ("2".equals(resultCode)) {
								if (loadType == 0) {
									mPtrFrameLayout.refreshComplete();
									if (vos.size() != 0) {
										vos.clear();
										adapter.notifyDataSetChanged();
										currentpage = 1;
									}
									
									mPtrFrameLayout.setVisibility(View.GONE);
									LoadingUtils.showLoadMsg(loadView, "暂无回复……");
								} else {
									listview.onLoadComplete();
									listview.setResultSize(0);
								}
							} else {
								processException(loadType);
							}

						} catch (JSONException e) {
							Log.e(TAG, "queryMessageBackList", e);

							processException(loadType);
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "queryMessageBackList", error.getCause());

						processException(loadType);
					}
				});
	}

	/**
	 * 
	 * 点赞接口。
	 * 
	 * @param backId 回复 ID
	 * @param position 位置
	 * 
	 */
	public void addAgree(String backId, final int position) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/pushpraiselmr/507.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("lmrId", backId);
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		
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
								Utils.showToastBGNew(context, "点赞成功");
								
								vos.get(position).setIsPostive(1);
								vos.get(position).setPostive(vos.get(position).getPostive() + 1);

								adapter.notifyDataSetChanged();
								
								// 发送广播
								Intent intent = new Intent(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
								intent.putExtra("flag", 2);
								sendBroadcast(intent);
							} else {
								Utils.showToastBGNew(context, "点赞失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "addAgree", e);
							
							Utils.showToastBGNew(context, "点赞失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "addAgree", error.getCause());
						
						Utils.showToastBGNew(context, "点赞失败");
					}
				});
	}
	
	/**
	 * 
	 * 删除留言接口。
	 * 
	 * @param backId 回复 ID
	 * @param position 位置
	 * 
	 */
	public void removelmr(String backId, final int position) {
		if (!removelmrFlag) {
			Utils.showToastBGNew(context, "正常处理上一个删除请求");
		}
		
		removelmrFlag = false;
		
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/removelmr/509.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("lmrId", backId);
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, //
				url, //
				null, //
				params, //
				true, //
				null, //
				new VolleyListener<JSONObject>() {
			@Override
			public void success(JSONObject response) {
				removelmrFlag = true;
				
				try {
					String resultCode = response.getString("resultCode");
					
					if ("1".equals(resultCode)) {
						Utils.showToastBGNew(context, "删除成功");
						
						vos.remove(position);
						
						int replyNum = Integer.valueOf(topicVo.getReplyNum());
						
						if (replyNum > 0) {
							replyNum = (replyNum - 1);
						}
						
						topicVo.setReplyNum(String.valueOf(replyNum));
						
						tv_count.setText(topicVo.getReplyNum());
						tv_back_count.setText(String.format("回复	%s", topicVo.getReplyNum()));
						
						adapter.notifyDataSetChanged();
						
						// 发送广播
						Intent intent1 = new Intent(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
						intent1.putExtra("flag", 2);
						sendBroadcast(intent1);
						
						// 发送广播
						Intent intent2 = new Intent(Constants.TOPIC_SEARCH_BROADCAST_ACTION);
						intent2.putExtra("flag", 1);
						sendBroadcast(intent2);
						
						// 发送广播
						Intent intent3 = new Intent(Constants.REFRESH_ONE_TOPIC_BROADCAST_ACTION);
						intent3.putExtra("flag", 1);
						sendBroadcast(intent3);
					} else {
						Utils.showToastBGNew(context, "删除失败");
					}
				} catch (JSONException e) {
					Log.e(TAG, "removelmr", e);
					
					Utils.showToastBGNew(context, "删除失败");
				}
			}
			
			@Override
			public void error(VolleyError error) {
				removelmrFlag = true;
				
				Log.e(TAG, "removelmr", error.getCause());
				
				Utils.showToastBGNew(context, "删除失败");
			}
		});
	}
	
	/**
	 * 
	 * 回复评论。
	 * 
	 */
	private void replyMessage() {
		if (!replyMessageFlag) {
			Utils.showToastBGNew(context, "正常处理上一个回复请求");
		}
		
		replyMessageFlag = false;
		
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/addlmr/505.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmcId", topicVo.getId());
		params.put("content", content);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, //
				url, //
				null, //
				params, //
				false, //
				null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						replyMessageFlag = true;
						
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								Utils.showToastBGNew(context, "回复成功");
								
								// 清空回复的内容
								et_content.setText("");
								
								// 发送广播
								Intent intent1 = new Intent(Constants.MESSAGE_PLATFORM_DETAIL_BROADCAST_ACTION);
								intent1.putExtra("flag", 1);
								sendBroadcast(intent1);
								
								// 发送广播
								Intent intent2 = new Intent(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
								intent2.putExtra("flag", 2);
								sendBroadcast(intent2);
								
								// 发送广播
								Intent intent3 = new Intent(Constants.REFRESH_ONE_TOPIC_BROADCAST_ACTION);
								intent3.putExtra("flag", 1);
								sendBroadcast(intent3);
								
								// 发送广播
								Intent intent4 = new Intent(Constants.TOPIC_SEARCH_BROADCAST_ACTION);
								intent4.putExtra("flag", 1);
								sendBroadcast(intent4);
							} else {
								Utils.showToastBGNew(context, "回复失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "replyMessage", e);
							
							Utils.showToastBGNew(context, "回复失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						replyMessageFlag = true;
						
						Log.e(TAG, "replyMessage", error.getCause());
						
						Utils.showToastBGNew(context, "回复失败");
					}
				});
	}
}