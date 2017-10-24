package com.boil.hospitalsecond.docpatientcircle.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FastJsonUtils;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.adapter.SubscribeDoctorUserListAdapter;
import com.boil.hospitalsecond.docpatientcircle.model.SubscribeDoctorUserVo;
import com.boil.hospitalsecond.tool.ptrtool.CTListView;
import com.boil.hospitalsecond.tool.ptrtool.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrDefaultHandler;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 关注医生的用户列表 Activity。
 * 
 * @author ChenYong
 * @date 2016-12-20
 *
 */
public class SubscribeDoctorUserListActivity extends BaseBackActivity implements CTPullUpListViewCallBack {
	/** 标记 */
	private static final String TAG = "SubscribeDoctorUserListActivity";
	/** 上下文 */
	private SubscribeDoctorUserListActivity context = SubscribeDoctorUserListActivity.this;
	@ViewInject(R.id.listview)
	private CTListView listview;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;

	private SubscribeDoctorUserListAdapter adapter;
	private int pagecount = 10;
	private int currentpage = 1;
	private List<SubscribeDoctorUserVo> vos = new ArrayList<SubscribeDoctorUserVo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_subscribe_doctor_user_list);
		
		ViewUtils.inject(this);
		
		initView();
		initEvent();
	}

	public void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("关注");
		bt_save.setVisibility(View.INVISIBLE);

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
				querySubscribeList(0);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		
		adapter = new SubscribeDoctorUserListAdapter(context);
		listview.setAdapter(adapter);
	}

	private void processException(int loadType) {
		if (loadType == 0) {
			mPtrFrameLayout.refreshComplete();
			
			if (vos.size() == 0) {
				LoadingUtils.showLoadMsg(loadView, "加载关注失败");
			} else {
				Utils.showToastBGNew(context, "刷新关注失败");
			}
		} else {
			listview.onLoadComplete();
			listview.setResultSize(-1);
		}
	}

	public void updateAdapter(List<SubscribeDoctorUserVo> listVo, int type) {
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
		querySubscribeList(1);
	}
	
	/**
	 * 
	 * 查询关注列表。
	 * 
	 * @param loadType 加载类型
	 * 
	 */
	private void querySubscribeList(final int loadType) {
		String url = Constants.WEB_URL_4 + "/hsptapp/doctor/leavemsg/lssubtousers/510.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
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
								List<SubscribeDoctorUserVo> subscribeDoctorUserVos = FastJsonUtils.json2List(response.getString("t"), SubscribeDoctorUserVo.class);

								updateAdapter(subscribeDoctorUserVos, loadType);
							} else if ("2".equals(resultCode)) {
								if (loadType == 0) {
									mPtrFrameLayout.refreshComplete();
									
									if (vos.size() != 0) {
										vos.clear();
										adapter.notifyDataSetChanged();
										currentpage = 1;
									}
									
									LoadingUtils.showLoadMsg(loadView, "暂无关注……");
								} else {
									listview.onLoadComplete();
									listview.setResultSize(0);
								}
							} else {
								processException(loadType);
							}

						} catch (JSONException e) {
							Log.e(TAG, "querySubscribeList", e);

							processException(loadType);
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "querySubscribeList", error.getCause());

						processException(loadType);
					}
				});
	}
}