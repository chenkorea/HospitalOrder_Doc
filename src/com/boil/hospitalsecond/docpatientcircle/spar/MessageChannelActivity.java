package com.boil.hospitalsecond.docpatientcircle.spar;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.boil.hospitalsecond.docpatientcircle.model.ChannelItem;
import com.boil.hospitalsecond.subscribe.adapter.DragAdapter;
import com.boil.hospitalsecond.subscribe.adapter.OtherAdapter;
import com.boil.hospitalsecond.subscribe.adapter.OtherGridView;
import com.boil.hospitalsecond.tool.DragGrid;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 医生端留言话题管理。
 *
 * @author ChenYong(修改)
 * @date 2016-12-19
 *
 */
public class MessageChannelActivity extends BaseBackActivity implements OnItemClickListener {
	/** 当前上下文 */
	private Context context = MessageChannelActivity.this;
	/** 标记 */
	public static final String TAG = "MessageChannelActivity";
	/** 用户栏目的GRIDVIEW */
	@ViewInject(R.id.userGridView)
	private DragGrid userGridView;
	/** 其它栏目的GRIDVIEW */
	@ViewInject(R.id.otherGridView)
	private OtherGridView otherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	DragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	OtherAdapter otherAdapter;
	/** 其它栏目列表 */
	ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
	/** 用户栏目列表 */
	ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
	boolean isMove = false;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.message_channel);
		
		volleyClient.setActivity(this);
		
		ViewUtils.inject(this);
		
		initView();
		initData();
	}

	/** 初始化数据 */
	private void initData() {
		queryAllSubList(configSP.getString(Constants.USER_ID, ""));
		
		// 设置GRIDVIEW的ITEM的点击监听
		otherGridView.setOnItemClickListener(this);
		userGridView.setOnItemClickListener(this);
	}

	/**
	 * 
	 * 查询订阅和未订阅的分类信息。
	 * 
	 * @param uid 医生 ID
	 * 
	 */
	private void queryAllSubList(final String uid) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/lslmtype/502.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", uid);
		params.put("pagecount", "-1");

		userChannelList.clear();
		otherChannelList.clear();
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {
								String result = response.getString("t");
								JSONArray admArray = new JSONArray(result);

								for (int j = 0; j < admArray.length(); j++) {
									JSONObject subObj = admArray.getJSONObject(j);
									String subID = subObj.getString("id");
									String isSub = subObj.getString("issub");
									String name = subObj.getString("name");
									ChannelItem itemVo = new ChannelItem(subID, name, 1, isSub);

									// 已订阅
									if ("1".equals(isSub)) {
										userChannelList.add(itemVo);

										// 未订阅
									} else {
										otherChannelList.add(itemVo);
									}
								}

								userAdapter = new DragAdapter(context, userChannelList);
								userGridView.setAdapter(userAdapter);
								otherAdapter = new OtherAdapter(context, otherChannelList);
								otherGridView.setAdapter(otherAdapter);
							} else if ("2".equals(resultCode)) {
								LoadingUtils.showLoadMsg(loadView, "暂无话题");
							} else {
								LoadingUtils.showLoadMsg(loadView, "查询话题失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "queryAllSubList", e);
							
							LoadingUtils.showLoadMsg(loadView, "查询话题失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "queryAllSubList", error.getCause());
						
						LoadingUtils.showLoadMsg(loadView, "查询话题失败");
					}
				});
	}

	/** 初始化布局 */
	private void initView() {
		Utils.backClick(this, backBtn);
		
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		addreTitle.setText("话题管理");
		bt_save.setVisibility(View.INVISIBLE);
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	/** GRIDVIEW对应的ITEM点击监听接口 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
		// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if (isMove) {
			return;
		}
		
		switch (parent.getId()) {
		case R.id.userGridView:
			// position 为 0，1 的不可以进行任何操作
			/*
			 * if (position != 0 && position != 1 && position != 2) {}
			 */
			final ChannelItem channelDrag = ((DragAdapter) parent.getAdapter()).getItem(position);

			removeSubItem(channelDrag, view, position);

			break;
		case R.id.otherGridView:
			final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
			
			addSubItem(channel, view, position);

			break;
		default:
			break;
		}
	}

	/**
	 * 添加订阅接口
	 */
	private void addSubItem(final ChannelItem channel, final View view, final int position) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/addsubmsgtype/501.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmtid", channel.getId());

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
								final ImageView moveImageView = getView(view);
								
								if (moveImageView != null) {
									TextView newTextView = (TextView) view.findViewById(R.id.text_item);
									final int[] startLocation = new int[2];
									
									newTextView.getLocationInWindow(startLocation);
									userAdapter.setVisible(false);
									// 添加到最后一个
									userAdapter.addItem(channel);
									
									new Handler().postDelayed(new Runnable() {
										public void run() {
											try {
												int[] endLocation = new int[3];
												// 获取终点的坐标
												userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
												
												MoveAnim(moveImageView, startLocation, endLocation, channel, otherGridView);
												
												otherAdapter.setRemove(position);
											} catch (Exception localException) {
											}
										}
									}, 50L);
								}
								
								// 发送广播
								Intent intent = new Intent(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
								intent.putExtra("flag", 1);
								sendBroadcast(intent);
							} else {
								Utils.showToastBGNew(context, "添加话题失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "addSubItem", e);
							
							Utils.showToastBGNew(context, "添加话题失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "添加话题失败");
					}
				});
	}

	/**
	 * 删除订阅接口
	 */
	private void removeSubItem(final ChannelItem channel, final View view, final int position) {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/cancelsublmt/508.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmtId", channel.getId());

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
								final ImageView moveImageView = getView(view);

								if (moveImageView != null) {
									TextView newTextView = (TextView) view.findViewById(R.id.text_item);
									final int[] startLocation = new int[3];

									newTextView.getLocationInWindow(startLocation);
									otherAdapter.setVisible(false);
									// 添加到最后一个
									otherAdapter.addItem(channel);

									new Handler().postDelayed(new Runnable() {
										public void run() {
											try {
												int[] endLocation = new int[3];
												// 获取终点的坐标
												otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);

												MoveAnim(moveImageView, startLocation, endLocation, channel, userGridView);

												userAdapter.setRemove(position);
											} catch (Exception localException) {
											}
										}
									}, 50L);
								}
								
								// 发送广播
								Intent intent = new Intent(Constants.MESSAGE_PLATFORM_BROADCAST_ACTION);
								intent.putExtra("flag", 1);
								sendBroadcast(intent);
							} else {
								Utils.showToastBGNew(context, "移除话题失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "removeSubItem", e);
							
							Utils.showToastBGNew(context, "移除话题失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "removeSubItem", error.getCause());
						
						Utils.showToastBGNew(context, "移除话题失败");
					}
				});
	}

	/**
	 * 点击ITEM移动动画
	 * 
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, //
			int[] startLocation, //
			int[] endLocation, //
			final ChannelItem moveChannel, //
			final GridView clickGridView) {
		int[] initLocation = new int[3];
		// 获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		// 得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		// 创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(startLocation[0], //
				endLocation[0], //
				startLocation[1], //
				endLocation[1]);
		moveAnimation.setDuration(300L);// 动画时间
		// 动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		
		moveAnimationSet.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				} else {
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				
				isMove = false;
			}
		});
	}

	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 * 
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		
		return view;
	}

	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		
		return moveLinearLayout;
	}

	/**
	 * 获取点击的Item的对应View，
	 * 
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		
		return iv;
	}
}