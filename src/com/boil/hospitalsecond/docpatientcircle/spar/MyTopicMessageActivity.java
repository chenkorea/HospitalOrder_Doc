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
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.Utils.OnConfirmClickListener;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.adapter.MessagePlatformDeleteAdapter;
import com.boil.hospitalsecond.docpatientcircle.adapter.MessagePlatformDeleteAdapter.InnerItemOnclickListener;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
import com.boil.hospitalsecond.tool.ptrtool.CTListView;
import com.boil.hospitalsecond.tool.ptrtool.CTListView.CTPullUpListViewCallBack;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrDefaultHandler;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyTopicMessageActivity extends BaseBackActivity implements CTPullUpListViewCallBack,InnerItemOnclickListener{

	private Context context = MyTopicMessageActivity.this;
	@ViewInject(R.id.listview2)
	private CTListView listView;
	
	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private MessagePlatformDeleteAdapter adapter;
	private int pagecount = 10;
	private int currentpage = 1;
	
	@ViewInject(R.id.rel_choose)
	private RelativeLayout rel_choose;
	
	private List<TopicTypeVo> vos = new ArrayList<TopicTypeVo>();
	
	@ViewInject(R.id.btn_add)
	private ImageButton btn_add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_platform_main);
//		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		initView();
	}
	
	public void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("我的留言");
		bt_save.setVisibility(View.INVISIBLE);
		rel_choose.setVisibility(View.GONE);
		initPtr(this,mPtrFrameLayout);
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);
		listView.setPageSize(pagecount);
		listView.setMyPullUpListViewCallBack(this);
		
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				queryMyTopicList(0);
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		
		btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(context, AddMessageActivity.class);
				in.putExtra("intentFlag", "myTopicTo");
				startActivity(in);
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, MessagePlatformDetailActivity.class);
				context.startActivity(intent);
				((BaseBackActivity) context).overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
		
		listView.setDivider(null);
		adapter = new MessagePlatformDeleteAdapter(context,iconFont);
		adapter.setOnInnerItemOnClickListener(this);  
		listView.setAdapter(adapter);
	}
	
		
	/**
	 * query all of topics's list
	 */
	private void queryMyTopicList(final int loadType){
		//http://localhost:8080/hsptapp/leavemsg/lsselflmc/B003.html?uid=5
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/leavemsg/lsselflmc/B003.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", configSP.getString(Constants.USER_ID, ""));
		if(loadType == 1) {
			params.put("currentpage", currentpage + "");
			params.put("pagecount", pagecount + "");
		} 
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<TopicTypeVo> vosTemp = com.alibaba.fastjson.JSONArray.parseArray(result, TopicTypeVo.class);
						updateAdapter(vosTemp, loadType);
					} else if("2".equals(resultCode)){
						if(loadType == 0) {
							mPtrFrameLayout.refreshComplete();
							if(vos.size() != 0) {
								vos.clear();
								adapter.notifyDataSetChanged();
								currentpage = 1;
							}
							LoadingUtils.showLoadMsg(loadView, "暂无数据");
						} else {
							listView.onLoadComplete();
							listView.setResultSize(0);
						}
					} else {
						processException(loadType);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					processException(loadType);
				}
			}
			
			@Override
			public void error(VolleyError error) {
				processException(loadType);
			}
		});
		
	}
	
	private void processException(int loadType) {
		if(loadType == 0) {
			mPtrFrameLayout.refreshComplete();
			if(vos.size() == 0) {
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			} else {
				Utils.showToastBGNew(context, "刷新数据失败");
			}
		} else {
			listView.onLoadComplete();
			listView.setResultSize(-1);
		}
	}
	
	public void updateAdapter(List<TopicTypeVo> listVo, int type) {
		
		//刷新操作
		if(type == 0) {
			currentpage = 1;
			vos = listVo;
			mPtrFrameLayout.refreshComplete();
		} else if(type == 1) {//上拉加载
			vos.addAll(listVo);
			listView.onLoadComplete();
		}
		listView.setResultSize(listVo.size());
		currentpage++;
		adapter.setList(vos);
		adapter.notifyDataSetChanged();
		
	}
	
	private void deleteMyTopic(final int position){
		//http://localhost:8080/hsptapp/leavemsg/rmvselflmc/B004.html?uid=5&lmcId=
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/leavemsg/rmvselflmc/B004.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmcId", vos.get(position).getId());
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						Utils.showToastBGNew(context, "删除留言成功");
						vos.remove(position);
						adapter.notifyDataSetChanged();
						// MessagePlatformActivity.isChange = true;
					} else{
						Utils.showToastBGNew(context, "删除留言失败");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Utils.showToastBGNew(context, "删除留言失败");
				}
			}
			
			@Override
			public void error(VolleyError error) {
				Utils.showToastBGNew(context, "删除留言失败");
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/*if(MessagePlatformActivity.isChange) {
			queryMyTopicList(0);
		}*/
	}
		
	@Override
	public void scrollBottomLoadState() {
		queryMyTopicList(1);
	}

	@Override
	public void itemClick(View v) {
		int position;  
        position = (Integer) v.getTag();  
        switch (v.getId()) {  
        case R.id.lay_delete:  
        	initDialog(position);
            break;  
        default:  
            break;  
        }  
	}
	public void initDialog(final int position) {
	
		Utils.showConfirmDialog(context, "确定要删除吗？", new OnConfirmClickListener() {
			
			@Override
			public void onConfirmClickListener() {
				deleteMyTopic(position);
			}
		});
	}
}
