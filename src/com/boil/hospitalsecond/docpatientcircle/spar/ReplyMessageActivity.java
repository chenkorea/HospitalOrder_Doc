package com.boil.hospitalsecond.docpatientcircle.spar;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 回复评论 Activity。
 * 
 * @author ChenYong(站在巨人的基础上修改)
 * @date 2016-12-20
 *
 */
public class ReplyMessageActivity extends BaseBackActivity {
	/** 标记 */
	private static final String TAG = "ReplyMessageActivity";
	private ReplyMessageActivity context = ReplyMessageActivity.this;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.et_content)
	private EditText et_content;
	@ViewInject(R.id.btn_commit)
	private BootstrapButton btn_commit;

	private String replyId;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.reply_message_platform_content);
		
		ViewUtils.inject(this);
		
		initView();
		initEvent();
		initData();
	}

	private void initData() {
		if (getIntent().getExtras() != null) {
			replyId = getIntent().getExtras().getString("replyId");
		}
	}

	public void initView() {
		Utils.backClick(this, backBtn);
	
		addreTitle.setText("回复");
		volleyClient.setActivity(context);
	}

	public void initEvent() {
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				content = et_content.getText().toString();

				if (StringUtils.isBlank(content)) {
					Utils.showToastBGNew(context, "请输入回复的内容");

					return;
				}

				addMessage();
			}
		});
	}

	private void addMessage() {
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp + "/hsptapp/doctor/leavemsg/addlmr/505.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", configSP.getString(Constants.USER_ID, ""));
		params.put("lmcId", replyId);
		params.put("content", content);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							
							if ("1".equals(resultCode)) {
								Utils.showToastBGNew(context, "回复成功");
								
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
								
								finish();
							} else {
								Utils.showToastBGNew(context, "回复失败");
							}
						} catch (JSONException e) {
							Log.e(TAG, "addMessage", e);
							
							Utils.showToastBGNew(context, "回复失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Log.e(TAG, "addMessage", error.getCause());
						
						Utils.showToastBGNew(context, "回复失败");
					}
				});
	}
}