package com.boil.hospitalsecond.docpatientcircle.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.boil.hospitalsecond.docpatientcircle.model.MessageTypeVo;
import com.boil.hospitalsecond.tool.ctgridview.Tag;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class AddMessageActivity extends BaseBackActivity{

	
	private AddMessageActivity context=AddMessageActivity.this;
	
	@ViewInject(R.id.back)
	private ImageView backBtn;
	
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	
	@ViewInject(R.id.et_title)
	private EditText et_title;
	
	@ViewInject(R.id.et_content)
	private EditText et_content;
	
	@ViewInject(R.id.btn_commit)
	private BootstrapButton btn_commit;
	@ViewInject(R.id.et_type)
	private EditText et_type;
	@ViewInject(R.id.img_check)
	private CheckBox checkBox;
	@ViewInject(R.id.v_order_trans)
	private View transView;
	
	private PopupWindow mWindow; 
	private List<MessageTypeVo> types=new ArrayList<MessageTypeVo>();
	private String typeId;
	private String title;
	private String content;
	private Tag tag = null;
	private String intentFlag = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_message_platform_content);
		ViewUtils.inject(this);
		initView();
		initEvent();
		initData();
		
	}
	
	private void initData() {
		if(getIntent().getExtras() != null) {
			tag = (Tag) getIntent().getExtras().getSerializable("tag");
			intentFlag = getIntent().getExtras().getString("intentFlag");
		}
		if(tag != null) {
			typeId = tag.getId() + "";
			et_type.setText(tag.getTitle());
		}
		queryMessageType();
	}

	public void initView(){
		Utils.backClick(this, backBtn);
		addreTitle.setText("添加留言");
		volleyClient.setActivity(context);
	}
	
	public void initEvent() {
		btn_commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

//				Utils.showToastBGNew(AddMessageActivity.this, "发布");
				
				title=et_title.getText().toString();
				content=et_content.getText().toString();
				
				if(TextUtils.isEmpty(title)){
					
					Utils.showToastBGNew(context, "请输入标题");
					return;
				}
				
				/*if(TextUtils.isEmpty(content)){
					
					Utils.showToastBGNew(context, "请输入内容");
					return;
				}*/
				if(content.length()<10){
					
					Utils.showToastBGNew(context, "内容不能小于10个字");
					return;
				}
				
				addMessage();
				
				
			}
		});
		
		et_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(types.size()>0){
					
					showWindow();
				}else {
					
					queryMessageType();
				}
				
			}
		});
	}
	
	private void showWindow() {
		
		transView.setVisibility(View.VISIBLE);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_add_message_type, null);
		ListView listView=(ListView) view.findViewById(R.id.list_type);
	
		NationAdapter adapter=new NationAdapter();
		listView.setAdapter(adapter);
		
		if(types.size()<5){
			
			mWindow = new PopupWindow(view, Utils.getScreenWidth(context) - Utils.dip2px(context, 13) * 2,LayoutParams.WRAP_CONTENT);
		}else {
			mWindow = new PopupWindow(view, Utils.getScreenWidth(context) - Utils.dip2px(context, 13) * 2,Utils.dip2px(context, 200));
			
		}
		
		mWindow.setBackgroundDrawable(new BitmapDrawable());
		mWindow.setAnimationStyle(0);
		mWindow.showAsDropDown(et_type);
		mWindow.setOutsideTouchable(true);
		mWindow.setFocusable(true);
		mWindow.update();
		
		mWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				transView.setVisibility(View.GONE);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				et_type.setText(types.get(arg2).getName());
				typeId=types.get(arg2).getId();
				
				transView.setVisibility(View.GONE);
				mWindow.dismiss();
			}
		});
		
	}
	
	
	private void addMessage(){


		//http://localhost:8080/hsptapp/leavemsg/leavemsg/B002.html?uid=5&lmtid=7&title=神经痛&content=&ispublic=1
		String url = "http://58.42.232.110:8086/hsptapp/leavemsg/leavemsg/B002.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmtid",typeId);
		params.put("title", title);
		params.put("content", content);
		
		if(checkBox.isChecked()){
			
			params.put("ispublic", "1");
		}else {
			params.put("ispublic", "0");
			
		}

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							if ("1".equals(resultCode)) {

								if("labelTo".equals(intentFlag)) {
									Intent intent = new Intent();
									intent.setAction(Constants.REFRESH_ONE_TOPIC_BROADCAST_ACTION);
									intent.putExtra("tagNew", (Serializable)tag);
									sendBroadcast(intent);
								}
								// MessagePlatformActivity.isChange = true;
								finish();
								

							} else {

								Utils.showToastBGNew(context, "添加失败");
							} 
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "添加失败");
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "添加失败");
					}
				});
	}
	
	private void queryMessageType(){

		//http://localhost:8080/hsptapp/leavemsg/lslmtype/B001.html
		String url = "http://58.42.232.110:8086/hsptapp/leavemsg/lslmtype/B001.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pagecount", "-1");

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							if ("1".equals(resultCode)) {

								String result = response.getString("t");
								
								types=JSONArray.parseArray(result, MessageTypeVo.class);
								
								if(types.size() > 0){
									if(tag == null) {
										tag = new Tag(Integer.parseInt(types.get(0).getId()), types.get(0).getName());
										et_type.setText(types.get(0).getName());
										typeId=types.get(0).getId();
									}
								}else {
									Utils.showToastBGNew(context, "暂无留言分类数据");
								}
							} else {

								Utils.showToastBGNew(context, "暂无留言分类数据");
							} 
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询留言分类数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询留言分类数据失败");
					}
				});
	}
	
	
	
	private class NationAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return types.size();
		}

		@Override
		public Object getItem(int position) {
			return types.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.nation_list_view_item, null);
			}
			
			TextView tvNation = ViewHolder.get(convertView, R.id.tv_nation);
			tvNation.setText(types.get(position).getName());
			
			return convertView;
		}
	}
}
