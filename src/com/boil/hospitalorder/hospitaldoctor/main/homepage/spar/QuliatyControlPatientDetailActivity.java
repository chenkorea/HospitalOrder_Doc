package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.QulityCrtContVo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class QuliatyControlPatientDetailActivity extends BaseBackActivity{

	private QuliatyControlPatientDetailActivity context = QuliatyControlPatientDetailActivity.this;
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
	
	private List<QulityCrtContVo> vos = new ArrayList<QulityCrtContVo>();
	private ListAdapter adapter;
	
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	
	@ViewInject(R.id.tv_dignose_num)
	private TextView tv_dignose_num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hos_quliaty_control_patient_main);
		ViewUtils.inject(this);
		volleyClient.setActivity(this);
		initView();
	}
	
	private void queryQulityCtrList(String admno){
//		http://58.42.232.110:8086/hsptapp/doctor/emr/lspatquality/605.html?admno=392998
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/emr/lspatquality/605.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("admno", admno);
		loadView.setVisibility(View.GONE);
		lv_income.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			@Override
			public void success(JSONObject response) {
				vos.clear();
				lv_income.setVisibility(View.VISIBLE);
				try {
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						vos = JSONArray.parseArray(result, QulityCrtContVo.class);
						adapter = new ListAdapter(vos);
						lv_income.setAdapter(adapter);
					} else if("2".equals(resultCode)){
						if(adapter != null) {
							adapter.notifyDataSetChanged();
						}
						LoadingUtils.showLoadMsg(loadView, "暂无质控信息数据...");
					} else {
						LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					}
				} catch (JSONException e) {
					LoadingUtils.showLoadMsg(loadView, "查询数据失败");
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(VolleyError error) {
				LoadingUtils.showLoadMsg(loadView, "查询数据失败");
			}
		});
	}
	
	private void initView() {
		Utils.backClick(context, backBtn);
		addreTitle.setText("病历质控信息");
		lay_right.setVisibility(View.GONE);
		PatientInfoVo inVo = (PatientInfoVo) getIntent().getSerializableExtra("PatientInfoVo");
		tv_title.setText(inVo.getName());
		tv_dignose_num.setText(String.format("就诊号：%s", inVo.getAdmId()));
		queryQulityCtrList(inVo.getAdmId());
	}
	
	class ListAdapter extends BaseAdapter{

		private List<QulityCrtContVo> qVos = new ArrayList<QulityCrtContVo>();
		
		public List<QulityCrtContVo> getqVos() {
			return qVos;
		}

		public void setqVos(List<QulityCrtContVo> qVos) {
			this.qVos = qVos;
		}

		private ListAdapter(List<QulityCrtContVo> qVos){
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
	        	convertView = layoutInflater.inflate(R.layout.quliaty_control_patient_listview_item, null);
	        	viewHolder.tv_child_doc = (TextView) convertView.findViewById(R.id.tv_child_doc);
	        	viewHolder.tv_child_content = (TextView) convertView.findViewById(R.id.tv_child_content);
	        	convertView.setTag(viewHolder);
	        } else {
	        	viewHolder = (ViewHolder) convertView.getTag();
	        }
	        QulityCrtContVo vo = vos.get(position);
	        viewHolder.tv_child_doc.setText(String.format("主治医生：%s", vo.getDocName()));
	        viewHolder.tv_child_content.setText(String.format("质控信息：%s", vo.getContent()));
	        return convertView;
	    }
	}
   class ViewHolder{
	   TextView tv_child_doc;
	   TextView tv_child_content;
    }
}
