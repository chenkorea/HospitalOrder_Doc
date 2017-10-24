package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.generally.http.AbstractAsyncResponseListener;
import com.boil.hospitalorder.generally.http.AsyncHttpClient;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterThreeActivity extends Activity {

	private Context context = RegisterThreeActivity.this;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.btn_complete)
	private BootstrapButton btn_complete;

	@ViewInject(R.id.et_password)
	private EditText et_password;

	private String phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_three);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {


		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intentTo();
			}
		});

		phone = getIntent().getStringExtra("phone");
		
		addreTitle.setText("设置密码");
		btn_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String passwordText = et_password.getText().toString().trim();
				if (StringUtils.isBlank(passwordText)
						|| passwordText.length() < 6
						|| passwordText.length() > 12) {
					Utils.showToastBGNew(context, "密码长度必须为6-12位数字、字母");
					return;
				}
				if (Utils.isPasswordDigitOk(passwordText)
						|| Utils.isPasswordCharOk(passwordText)
						|| Utils.isPasswordStrOk(passwordText)) {
					/** 字符匹配成功，只有数字和字母或者字母和数字混搭 */
					// TODO跳转到完善界面

					// 显示提示窗口
					showDialog();
//					xmlRegister(phone, passwordText);
					
				} else {
					Utils.showToastBGNew(context, "密码长度必须为6-12位数字、字母");
					return;
				}

			}
		});
	}
	
	/*protected void xmlRegister(String phoneAcount, String password) {

		String processURL = "http://yyghw.gzszlyy.cn:8086/ZlyyData/services/ZlyyData/UserInfo";

		HttpPost httpRequest = new HttpPost(processURL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("TradeID", "60001"));
		params.add(new BasicNameValuePair("Mobile", phoneAcount));
		params.add(new BasicNameValuePair("Info", password));

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequest.addHeader("Accept", "text/json");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		AsyncHttpClient.sendRequest(RegisterThreeActivity.this, httpRequest, context,
				"请等待", "正在查询…", false, new AbstractAsyncResponseListener(
						AbstractAsyncResponseListener.RESPONSE_TYPE_STRING,
						RegisterThreeActivity.this) {

					@Override
					protected void onSuccess(String response) {
						// response为需要解析的参数返回结果
						System.out.println("jhhhhhhhh—— " + response);
					}

					@Override
					protected void onFailure(Throwable e) {
						Log.e("test",
								"UploadAndMatch.onFailure(), error: "
										+ e.getMessage(), e);
					}

				});
	}*/
	

	// 返回登录 ，完善信息选择
	@SuppressWarnings("deprecation")
	private void showDialog() {
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_password.getWindowToken(), 0);

		AlertDialog dialog = new AlertDialog.Builder(context,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		dialog.setTitle(null);
		dialog.setMessage("您的信息尚未完善！");
		dialog.setButton2("去完善", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(RegisterThreeActivity.this,PerfectUserInfoCommitActivity.class);
				startActivity(intent);
				RegisterActivity.instance.finish();
				finish();
				dialog.dismiss();
			}
		});
		dialog.setButton("返回登录", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				RegisterActivity.instance.finish();
				finish();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	public boolean onKeyDown(int arg0, KeyEvent arg1) {
		if (arg0 == KeyEvent.KEYCODE_BACK) {
			intentTo();
		}
		return super.onKeyDown(arg0, arg1);
	}

	private void intentTo() {
		Intent intent = new Intent();
		// 这里应该直接跳到登录页面
		intent.setClass(context, RegisterActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK); // 注意本行的FLAG设置
		startActivity(intent);
		RegisterThreeActivity.this.finish();
	}

}
