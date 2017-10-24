package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CyUtils;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterGetVertifyActivity extends Activity {

	private RegisterGetVertifyActivity context = RegisterGetVertifyActivity.this;

	@ViewInject(R.id.btn_commit)
	private BootstrapButton btn_commit;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.tv_second)
	private TextView tv_second;

	@ViewInject(R.id.tv_detail)
	private TextView tv_detail;

	@ViewInject(R.id.et_vertify_code)
	private EditText et_vertify_code;
	
	@ViewInject(R.id.tv_decribe)
	private TextView tv_decribe;

	private TimeCount timeCount;
	private String phone;
	
	@ViewInject(R.id.et_password)
	private EditText et_password;

	private PopupWindow mWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_two);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		et_vertify_code.setCursorVisible(false);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intentTo();
			}
		});
		addreTitle.setText("获取验证码");
		
		phone = getIntent().getStringExtra("phone");
		String number = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
		
//		getViertifyCode();
		
		tv_decribe.setText("已经向" + number + "发送了短信验证码");
		
		
		timeCount = new TimeCount(60000 * 3, 1000);//构造CountDownTimer对象
		timeCount.start();	
		et_vertify_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_vertify_code.setCursorVisible(true);
				et_vertify_code.setFocusable(true);
				et_vertify_code.setFocusableInTouchMode(true);
			}
		});

		btn_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String vertify_code = et_vertify_code.getText().toString().trim();
				if (StringUtils.isEmpty(vertify_code)) {
					Utils.showToastBGNew(context, "请输入验证码");
					CyUtils.getFocus(et_vertify_code);
					return;
				} else {
					if (!vertify_code.matches(Constants.CAPTCHA_RE)) {
						Utils.showToastBGNew(context, Constants.CAPTCHA_RE_MSG);
						
						CyUtils.getFocus(et_vertify_code);
						
						return;
					}
				}
				
				String passwordText = et_password.getText().toString().trim();
				
				// 校验新密码
				if (StringUtils.isEmpty(passwordText)) {
					Utils.showToastBGNew(context, "请输入密码");
					CyUtils.getFocus(et_password);
					return;
				} else {
					if (!passwordText.matches(Constants.PASSWD_RE)) {
						Utils.showToastBGNew(context,Constants.PASSWD_RE_MSG);
						CyUtils.getFocus(et_password);
						return;
					}
				}
//				registerComplete(phone, passwordText, vertify_code);
			}
		});
		tv_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				timeCount.start();	
			}
		});
	}

	
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

				Intent intent = new Intent(RegisterGetVertifyActivity.this,PerfectUserInfoCommitActivity.class);
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
		dialog.setCanceledOnTouchOutside(false);
	}
	
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			tv_detail.setText("重新验证");
			tv_detail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
			tv_detail.getPaint().setAntiAlias(true);//抗锯齿
			tv_detail.setClickable(true);
			tv_detail.setTextSize(14);
			
			tv_detail.setTextColor(context.getResources().getColor(R.color.actionsheet_blue));
			tv_second.setVisibility(View.GONE);
			btn_commit.setClickable(false);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			tv_detail.setClickable(false);
			tv_second.setVisibility(View.VISIBLE);
			tv_second.setText(millisUntilFinished / 1000 + "");
			tv_detail.setText("秒后重新发送");
			tv_detail.setTextColor(context.getResources().getColor(R.color.actionsheet_gray));
			tv_detail.setTextSize(12);
			tv_detail.getPaint().setFlags(0);	
			btn_commit.setClickable(true);
		}
	}
	
	/*private void registerComplete(String phone, String password, String code) {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "RegisteredSer";

		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_1, wsMethod);
		// 设置参数
		request.addProperty("TradeID", "60024");
		request.addProperty("Mobile", phone);
		request.addProperty("Passw", password);
		request.addProperty("Vid", code);
		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request, Constants.WS_URL_1);
		httpClient.sendRequest(context, null, null, true, true, new WsAbstractAsyncResponseListener() {
			@Override
			protected void onSuccess(SoapObject response) {
				
				if (response != null) {
					SoapObject result = (SoapObject) response.getProperty(0);
					ResultRes res = Soap2Utils.parseBean(result, ResultRes.class);
					*//**
					 * 1,表示成功完成注册
					 * *//*
					if("1".equals(res.getResultCode())) {
						// 显示提示窗口
						showDialog();
					} else if("-5".equals(res.getResultCode())) {
						Utils.showToastBGNew(context, "验证码不正确");
					} else if("-3".equals(res.getResultCode()) || "-4".equals(res.getResultCode())) {
						Utils.showToastBGNew(context, "验证码失效");
					}
				} else {
					Utils.showToastBGNew(context, "注册失败");
				}
			}

			@Override
			protected void onFailure(Exception e) {
				Utils.showToastBGNew(context, "注册出错1");
			}
		});
	}
	
	private void getViertifyCode() {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "SendVid";

		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_1, wsMethod);
		// 设置参数
		request.addProperty("TradeID", "60023");
		request.addProperty("Type", "1");
		request.addProperty("Mobile", phone);
		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request, Constants.WS_URL_1);
		httpClient.sendRequest(context, null, null, true, true, new WsAbstractAsyncResponseListener() {
			@Override
			protected void onSuccess(SoapObject response) {
				System.out.println("RegistergetVertifyActivity--------->" + response.toString());
				if (response != null) {
					SoapObject result = (SoapObject) response.getProperty(0);
					System.out.println("Register--------->" + result.toString());
					ResultRes res = Soap2Utils.parseBean(result, ResultRes.class);
					*//**
					 * 1,表示注册过，0表示没注册过
					 * *//*
					if("1".equals(res.getResultCode())) {
						//TODO 发送成功
					} else if("-1".equals(res.getResultCode())) {
						Utils.showToastBGNew(context, "手机号已被注册");
					}
				} else {
					Utils.showToastBGNew(context, "没有查询到数据");
				}
			}

			@Override
			protected void onFailure(Exception e) {
				Utils.showToastBGNew(context, "查询数据出错11");
			}
		});
	}*/
	
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
		intent.setClass(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK); // 注意本行的FLAG设置
		startActivity(intent);
		context.finish();
	}
}
