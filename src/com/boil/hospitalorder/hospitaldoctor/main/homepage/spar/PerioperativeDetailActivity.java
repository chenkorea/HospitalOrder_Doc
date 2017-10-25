package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
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
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PerioperativeDetailVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PerioperativeDetailActivity extends BaseBackActivity{

	private PerioperativeDetailActivity context = PerioperativeDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	
	@ViewInject(R.id.lay_right)
	private LinearLayout lay_right;
	
	@ViewInject(R.id.lv_income)
	private ListView lv_income;
	
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_income_main_stuation);
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		initView();
	}
	
	private void initView() {
		Utils.backClick(context, backBtn);
		addreTitle.setText("围术期医嘱状态");
		lay_right.setVisibility(View.INVISIBLE);
		String admno = getIntent().getStringExtra("admno");
		String ocode = getIntent().getStringExtra("ocode");
		queryDetail(admno, ocode);
	}
	
	private void queryDetail(final String admno, String ocode){
//		http://58.42.232.110:8086/hsptapp/doctor/operation/lsoeorditem/802.html?admno=397239&ocode=56.0%20003
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/operation/lsoeorditem/802.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("admno", admno);
		params.put("ocode", ocode);
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<PerioperativeDetailVo> vosTemp = JSONArray.parseArray(result, PerioperativeDetailVo.class);
						if(!Utils.isValidate(vosTemp)) {
							Intent intent = new Intent(context, PatientInfoDetailActivity.class);
							intent.putExtra("admno", admno);
							startActivity(intent);
							overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
							finish();
						} 
						ListAdapter incomeAdapter = new ListAdapter(vosTemp);
						lv_income.setAdapter(incomeAdapter);
					} else if("2".equals(resultCode)){
						Intent intent = new Intent(context, PatientInfoDetailActivity.class);
						intent.putExtra("admno", admno);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
						finish();
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询详细失败");
					}
				} catch (JSONException e) {
					LoadingUtils.showLoadMsg(loadView, "查询详细失败");
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询详细失败");
			}
		});
	}
	
	class ListAdapter extends BaseAdapter{

		private List<PerioperativeDetailVo> qVos = new ArrayList<PerioperativeDetailVo>();
		
		public ListAdapter() {
			super();
		}
		
		public ListAdapter(List<PerioperativeDetailVo> qVos) {
			super();
			this.qVos = qVos;
		}

		public List<PerioperativeDetailVo> getqVos() {
			return qVos;
		}

		public void setqVos(List<PerioperativeDetailVo> qVos) {
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
	        	convertView = layoutInflater.inflate(R.layout.patient_perioperative_detail_listview_item, null);
	        	viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
	        	viewHolder.tv_state_type = (TextView) convertView.findViewById(R.id.tv_state_type);
	        	viewHolder.tv_perioperative_type = (TextView) convertView.findViewById(R.id.tv_perioperative_type);
	        	
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        PerioperativeDetailVo vo = qVos.get(position);
	        viewHolder.tv_name.setText(vo.getOdesc());
        	viewHolder.tv_state_type.setText(vo.getStateDesc());
        	String type = "";
        	if("1".equals(vo.getType())) {
        		type = "术前";
        	} else if("2".equals(vo.getType())) {
        		type = "术中";
        	} else {
        		type = "术后";
        	}
        	viewHolder.tv_perioperative_type.setText(type);
	        return convertView;
	    }
	}
	class ViewHolder{
		TextView tv_name;
		TextView tv_state_type;
		TextView tv_perioperative_type;
	    }
}
