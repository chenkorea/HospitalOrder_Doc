package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.VerifyResultVo;
import com.boil.hospitalorder.utils.CHScrollView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 检查结果详细 Activity。
 * 
 * @author ChenYong
 * @date 2016-03-01
 * 
 */
public class VerifyResultDetailActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private VerifyResultDetailActivity context = VerifyResultDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView tvTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 表格 LinearLayout */
	@ViewInject(R.id.ll_table)
	private LinearLayout llTable;
	/** 表格头 */
	@ViewInject(R.id.ch_table_header)
	private CHScrollView tableHeader;
	/** 表格体 */
	@ViewInject(R.id.lv_table_body)
	private ListView tableBody;
	/** 检验项目 */
	private String orderName;
	/** 检验 ID */
	private String lisId;
	/** 医嘱 ID */
	private String orderId;
	/** 检验结果 List */
	private List<VerifyResultVo> vrVos;
	/** 检验结果 Adapter */
	private VerifyResultAdapter vrAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_result_view);

		// 开启注解
		ViewUtils.inject(context);

		initData();
		initView();
		initEvent();
		queryLisResult();
	}

	/**
	 * 
	 * 初始化数据。
	 * 
	 */
	private void initData() {
		Intent intent = getIntent();
		lisId = intent.getStringExtra("lisId");
		orderId = intent.getStringExtra("orderId");
		orderName = intent.getStringExtra("orderName");
		vrVos = new ArrayList<VerifyResultVo>();
	}

	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		// 点击返回上一个 Activity
		Utils.backClick(this, backBtn);
		// 设置标题
		tvTitle.setText(orderName);
		// 隐藏保存按钮
		saveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
		// 添加头滑动事件
		mHScrollViews.add(tableHeader);
	}

	/**
	 * 
	 * 初始化事件。
	 * 
	 */
	private void initEvent() {

	}
	
	/**
	 * 
	 * 添加滑动元素。
	 * 
	 * @param chScrollView
	 * 
	 */
	private void addCHScrollView(final CHScrollView chScrollView) {
		if (!mHScrollViews.isEmpty()) {
			CHScrollView scrollView = mHScrollViews.get(mHScrollViews.size() - 1);
			final int scrollX = scrollView.getScrollX();
			
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				tableBody.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						chScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		
		mHScrollViews.add(chScrollView);
	}
	
	/**
	 * 
	 * 查询检验结果。
	 * 
	 */
	private void queryLisResult() {
		
		//http://localhost:8080/hsptapp/doctor/lisres/lklisres/103.html
		
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/lisres/lklisres/103.html";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("hid", configSP.getString(Constants.LOGIN_INFO_HID, ""));
		params.put("lisid", lisId);
		params.put("oid", orderId);

		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								
								String result=response.getString("t");
								vrVos=com.alibaba.fastjson.JSONArray.parseArray(result, VerifyResultVo.class);
								if(vrVos.size()>0){
									
									vrAdapter = new VerifyResultAdapter(vrVos);
									tableBody.setAdapter(vrAdapter);
									
									llTable.setVisibility(View.VISIBLE);
								}else {
									Utils.showToastBGNew(context, "暂无结果数据");
								}
								
								
							} else if("2".equals(resultCode)){

								Utils.showToastBGNew(context, "暂无结果数据");
							}else if("3".equals(resultCode)){
								Utils.showToastBGNew(context, "用户未登录");
							}else {
								Utils.showToastBGNew(context, "查询结果数据失败");
							}
							
							

						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询结果数据失败");
						}
						
						

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询结果数据失败");
					}
				});
		
	
	}

	/**
	 * 
	 * 检验结果的 Adapter。
	 * 
	 * @author ChenYong
	 * @date 2016-03-02
	 * 
	 */
	private class VerifyResultAdapter extends BaseAdapter {
		/** 检验结果 List */
		private List<VerifyResultVo> vrVos;

		/**
		 * 
		 * 有参构造器。
		 * 
		 * @param vrVos 检验结果 List
		 * 
		 */
		public VerifyResultAdapter(List<VerifyResultVo> vrVos) {
			this.vrVos = vrVos;
		}

		@Override
		public int getCount() {
			return vrVos.size();
		}

		@Override
		public Object getItem(int position) {
			return vrVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.verify_result_item, null);
			}

			CHScrollView chTableBody = ViewHolder.get(convertView, R.id.ch_table_body);
			TextView tvResName = ViewHolder.get(convertView, R.id.tv_res_name);
			TextView tvResResult = ViewHolder.get(convertView, R.id.tv_res_result);
			TextView tvResResultArrows = ViewHolder.get(convertView, R.id.tv_verify_result_arrows);
			TextView tvResRanges = ViewHolder.get(convertView, R.id.tv_res_ranges);
			TextView tvResDhc = ViewHolder.get(convertView, R.id.tv_res_dhc);

			
			if ((vrVos != null) && !vrVos.isEmpty()) {
				VerifyResultVo vrVo = vrVos.get(position);
				tvResName.setText(vrVo.getResName());
				tvResResult.setText(vrVo.getResResult());
				tvResRanges.setText(vrVo.getResRanges());
				tvResDhc.setText(vrVo.getResDhc());
				
				if ("L".equals(vrVo.getResTag())) {
					tvResResultArrows.setText("↓");
				} else if ("H".equals(vrVo.getResTag())) {
					tvResResultArrows.setText("↑");
				} else {
					tvResResultArrows.setText("");
				}
				
				addCHScrollView(chTableBody);
			}

			return convertView;
		}
	}
}