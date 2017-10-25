package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragmentActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.DangerValueVo;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DangerPatientValueDetailActivity extends BaseFragmentActivity{
	/** 标记 */
	private static final String TAG = "DangerValueDetailActivity";
	private DangerPatientValueDetailActivity context = DangerPatientValueDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	
	@ViewInject(R.id.lay_right)
	private LinearLayout lay_right;
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	
	private String startDate, endDate, admno;
	
	private ListAdapter adapter;
	
	@ViewInject(R.id.rotate_header_list_view_frame)
	private PtrClassicFrameLayout mPtrFrameLayout;	
	@ViewInject(R.id.lv_income3)
	private ListView lv_income3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_danger_main_stuation);
		
		initMenu();
		initView();
		
	}
	
	private void initView() {
		
		Utils.backClick(context, backBtn);
		addreTitle.setText("危急值");
		lay_right.setVisibility(View.GONE);
		lv_income3.setVisibility(View.VISIBLE);
		mPtrFrameLayout.setVisibility(View.GONE);
		
		queryDangerValueList(true);
		
	}
	
	private void initMenu() {
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		startDate = getIntent().getStringExtra("startDate");
		if(StringUtils.isBlank(startDate)) {
			startDate = Utils.getCurrentTime("yyyy-MM-dd");
		}
		endDate = getIntent().getStringExtra("endDate");
		if(StringUtils.isBlank(endDate)) {
			endDate = Utils.getCurrentTime("yyyy-MM-dd");
		}
		admno = getIntent().getStringExtra("admno");
	}

	private void queryDangerValueList(boolean isShowOn){
//		http://localhost:8080/hsptapp/doctor/creticallis/lscreticallisbyadmno/702.html?admno=379703&stime=2017-01-01&etime=2017-03-09
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/creticallis/lscreticallisbyadmno/702.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("admno", admno);//"95"
		params.put("stime", startDate);
		params.put("etime", endDate);
		params.put("pagecount", "-1");
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, isShowOn, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<DangerValueVo> vosTemp = JSONArray.parseArray(result, DangerValueVo.class);
						adapter = new ListAdapter(vosTemp);
						lv_income3.setAdapter(adapter);
					} else if("2".equals(resultCode)){
						if(adapter != null) {
							adapter.notifyDataSetChanged();
						}
						LoadingUtils.showLoadMsg(loadView, "暂无数据");
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				} catch (JSONException e) {
					Log.e(TAG, "queryDangerValueList", e);
					LoadingUtils.showLoadMsg(loadView, "查询数据失败");
				}
			}
			
			@Override
			public void error(VolleyError error) {
				Log.e(TAG, "queryDangerValueList", error.getCause());
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			}
		});
	}

	class ListAdapter extends BaseAdapter{

		
		public ListAdapter() {
			super();
		}
		

		public ListAdapter(List<DangerValueVo> qVos) {
			super();
			this.qVos = qVos;
		}



		private List<DangerValueVo> qVos = new ArrayList<DangerValueVo>();
		
		public List<DangerValueVo> getqVos() {
			return qVos;
		}

		public void setqVos(List<DangerValueVo> qVos) {
			this.qVos = qVos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return qVos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return qVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        LayoutInflater layoutInflater = LayoutInflater.from(context);
	        ViewHolder viewHolder;
	        if(convertView == null) {
	        	viewHolder = new ViewHolder();
	        	convertView = layoutInflater.inflate(R.layout.danger_value_main_listview_item, null);
	        	viewHolder.tv_child_doc = (TextView) convertView.findViewById(R.id.tv_child_doc);
	        	viewHolder.tv_doc_name = (TextView) convertView.findViewById(R.id.tv_doc_name);
	        	viewHolder.tv_child_content = (TextView) convertView.findViewById(R.id.tv_child_content);
	        	viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
	        	viewHolder.doc_head = (CLRoundImageView) convertView.findViewById(R.id.doc_head);
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        DangerValueVo vo = qVos.get(position);
	        ImageLoader.getInstance().displayImage(Constants.IMAGE_STR + vo.getDocId() + ".jpg", viewHolder.doc_head);
	        viewHolder.tv_child_doc.setText(String.format("病人%s的危急值提醒", vo.getPatName()));
	        viewHolder.tv_child_content.setText(String.format("%s(就诊号:%s)%s", vo.getPatName(), vo.getAdmno(), vo.getResnewstr()));
	        viewHolder.tv_doc_name.setText(String.format("主治医生：%s", vo.getDocName()));
	        viewHolder.tv_time.setText(vo.getDatetime());
	        return convertView;
	    }
	}
	class ViewHolder{
		   TextView tv_child_doc;
		   TextView tv_child_content;
		   TextView tv_doc_name;
		   TextView tv_time;
		   CLRoundImageView doc_head;
	    }
	
	/**
	 * 设置菜单宽度
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}
}
