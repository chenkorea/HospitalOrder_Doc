package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CyUtils;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * 忘记密码 Activity。
 * 
 * @author ChenYong
 * @date 2016-01-28
 * 
 */
public class ForgetPasswdActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private ForgetPasswdActivity context = ForgetPasswdActivity.this;
	/** 返回上一个 Activity 的按钮 */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView titleTv;
	/** 提交按钮 */
	@ViewInject(R.id.bt_save)
	private TextView submitBtn;
	/** 手机号 EditText */
	@ViewInject(R.id.phone_et)
	private EditText phoneEt;
	/** 新密码 EditText */
	@ViewInject(R.id.new_passwd_et)
	private EditText newPasswdEt;
	/** 确认新密码 EditText */
	@ViewInject(R.id.confirm_new_passwd_et)
	private EditText confirmNewPasswdEt;
	/** 验证码 EditText */
	@ViewInject(R.id.captcha_et)
	private EditText captchaEt;
	/** 获取验证码的按钮 */
	@ViewInject(R.id.get_captcha_btn)
	private BootstrapButton captchaBtn;
	/** 手机号 */
	private String phone;
	/** 新密码 */
	private String newPasswd;
	/** 确认新密码 */
	private String confirmNewPasswd;
	/** 验证码 */
	private String captcha;
	/** 是否已获取验证码的标志：true-是；false-否 */
	private boolean isCaptcha;
	/** 获取验证码的定时器 */
	private Timer captchaTimer;
	/** 获取验证码的 Handler */
	private CaptchaHandler captchaHandler;
	/** 获取验证码的周期 */
	private long captchaInterval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.forget_passwd_view);

		// 开启注解
		ViewUtils.inject(context);

		// 初始化视图组件
		initView();
		// 初始化各种事件
		initEvent();
	}

	@Override
	public void finish() {
		super.finish();

		// 取消获取验证码的定时器
		if (captchaTimer != null) {
			captchaTimer.cancel();
		}
	}

	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		// 返回上一个 Activity
		Utils.backClick(context, backBtn);
		// 设置标题
		titleTv.setText("忘记密码");
		// 设置提交按钮的标题
		submitBtn.setText("提交");
		// 初始化时显示提交按钮
		submitBtn.setVisibility(View.VISIBLE);

		// 初始化属性
		isCaptcha = false;
		captchaHandler = new CaptchaHandler();

	}

	/**
	 * 
	 * 初始化各种事件。
	 * 
	 */
	private void initEvent() {
		// 点击提交按钮
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (dataValid()) {

					// 提交后台，更新密码
//					getData();

					/*
					 * 当提交成功后，跳转至登录界面。
					 */
					// Utils.showToastBGNew(context, "修改密码");

				}
			}
		});

		// 点击获取验证码按钮
		captchaBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				//先判断手机号输入情况
				phone = phoneEt.getText().toString().trim();
				if (StringUtils.isEmpty(phone)) {
					Utils.showToastBGNew(context, "请输入手机号");
					CyUtils.getFocus(phoneEt);
					return;
				} else {
					if (!phone.matches(Constants.PHONE_RE)) {
						Utils.showToastBGNew(context, Constants.PHONE_RE_MSG);
						CyUtils.getFocus(phoneEt);
						return;
					}
				}
				
				
				// 禁用按钮
				captchaBtn.setEnabled(false);
				// 120 秒后才能获取下一次验证码
				captchaInterval = 2L * 60L * 1000L;
				// 创建获取验证码的定时器
				captchaTimer = new Timer();
				// 为获取验证码的 Handler 设置获取验证码的按钮
				captchaHandler.setCaptchaBtn(captchaBtn);
				// 为获取验证码的 Handler 设置获取验证码的定时器
				captchaHandler.setCaptchaTimer(captchaTimer);

				captchaTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						Message captchaMsg = new Message();
						captchaMsg.what = 0x123;
						captchaMsg.getData().putLong("captchaInterval",
								(captchaInterval / 1000L));

						captchaHandler.sendMessage(captchaMsg);

						captchaInterval = captchaInterval - 1000L;
					}
				}, 0, 1000);

				/*
				 * 实际上，这里需要调用发送短信的接口去获取验证码，而且只有等接口返回验证码后， 这个“是否已获取验证码的标志”才能改为
				 * true。
				 */
//				isCaptcha = true;
				/**获取验证码*/
//				getVid();
				
			}
		});
	}

	/**
	 * 
	 * 数据校验。
	 * 
	 * @return 数据是否通过校验：
	 *         <ol>
	 *         <li>true-通过校验</li>
	 *         <li>false-未通过校验</li>
	 *         </ol>
	 * 
	 */
	private boolean dataValid() {
		phone = phoneEt.getText().toString().trim();
		newPasswd = newPasswdEt.getText().toString().trim();
		confirmNewPasswd = confirmNewPasswdEt.getText().toString().trim();
		captcha = captchaEt.getText().toString().trim();

		// 校验手机号
		if (StringUtils.isEmpty(phone)) {
			Utils.showToastBGNew(context, "请输入手机号");

			CyUtils.getFocus(phoneEt);

			return false;
		} else {
			if (!phone.matches(Constants.PHONE_RE)) {
				Utils.showToastBGNew(context, Constants.PHONE_RE_MSG);

				CyUtils.getFocus(phoneEt);

				return false;
			}
		}

		// 校验新密码

		if (StringUtils.isEmpty(newPasswd)) {
			Utils.showToastBGNew(context, "请输入新密码");

			CyUtils.getFocus(newPasswdEt);

			return false;
		} else {
			if (!newPasswd.matches(Constants.PASSWD_RE)) {
				Utils.showToastBGNew(context, "新" + Constants.PASSWD_RE_MSG);

				CyUtils.getFocus(newPasswdEt);

				return false;
			}
		}

		// 校验确认新密码
		if (StringUtils.isEmpty(confirmNewPasswd)) {
			Utils.showToastBGNew(context, "请输入确认新密码");

			CyUtils.getFocus(confirmNewPasswdEt);

			return false;
		} else {
			if (!newPasswd.equals(confirmNewPasswd)) {
				Utils.showToastBGNew(context, "新密码与确认新密码不一致");

				CyUtils.getFocus(confirmNewPasswdEt);

				return false;
			}
		}

		// 校验验证码
		if (!isCaptcha) {
			Utils.showToastBGNew(context, "请获取验证码");
			return false;
		} else {
			if (StringUtils.isEmpty(captcha)) {
				Utils.showToastBGNew(context, "请输入验证码");
				CyUtils.getFocus(captchaEt);

				return false;
			} else {
				if (!captcha.matches(Constants.CAPTCHA_RE)) {
					Utils.showToastBGNew(context, Constants.CAPTCHA_RE_MSG);
					CyUtils.getFocus(captchaEt);
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * 
	 * 获取验证码的 Handler。
	 * 
	 * @author ChenYong
	 * @date 2016-01-28
	 *
	 */
	class CaptchaHandler extends Handler {
		/** 获取验证码的按钮 */
		private BootstrapButton captchaBtn;
		/** 获取验证码的定时器 */
		private Timer captchaTimer;
		
		/**
		 * 
		 * 默认构造器。
		 * 
		 */
		public CaptchaHandler() {
			super();
		}
		
		/**
		 * 
		 * 设置获取验证码的按钮。
		 * 
		 * @param captchaBtn 获取验证码的按钮
		 * 
		 */
		public void setCaptchaBtn(BootstrapButton captchaBtn) {
			this.captchaBtn = captchaBtn;
		}

		/**
		 * 
		 * 设置获取验证码的定时器。
		 * 
		 * @param captchaTimer 获取验证码的定时器
		 * 
		 */
		public void setCaptchaTimer(Timer captchaTimer) {
			this.captchaTimer = captchaTimer;
		}

		/**
		 * 
		 * 消息处理。
		 * 
		 */
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				long captchaInterval1 = msg.getData().getLong("captchaInterval");

				if (captchaInterval1 > 0) {
					captchaBtn.setText(String.format("等待%s秒", captchaInterval1));
				} else {
					// 启用按钮
					captchaBtn.setEnabled(true);
					captchaBtn.setText("获取验证码");
					
					// 取消定时器
					captchaTimer.cancel();
				}
			}
		}
	}

	/*// 获取验证码
	private void getVid() {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "SendVid";

		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_1, wsMethod);

		// 设置参数
		request.addProperty("TradeID", "60023");
		request.addProperty("Type", 2+"");
		request.addProperty("Mobile", phone);

		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request, Constants.WS_URL_1);
		httpClient.sendRequest(ForgetPasswdActivity.this, null, null, true,
				true, new WsAbstractAsyncResponseListener() {
					@Override
					protected void onSuccess(SoapObject response) {
						// 查询到数据
						if (response != null) {
							for (int i = 0; i < response.getPropertyCount(); i++) {
								SoapObject result = (SoapObject) response
										.getProperty(i);
								String resultCode=result.getProperty("resultCode").toString();
								
								if("1".equals(resultCode)){
									
									isCaptcha=true;
									Utils.showToastBGNew(ForgetPasswdActivity.this, "发送成功");
									
								}else {
									isCaptcha=false;
									Utils.showToastBGNew(ForgetPasswdActivity.this, "发送失败");
								}
								
							}

							// 没有查询到数据
						} else {
							Utils.showToastBGNew(context, "没有查询到数据");
						}
					}

					@Override
					protected void onFailure(Exception e) {
						Utils.showToastBGNew(context,
								"查询数据出错" + e.toString());
					}
				});
	}

	
	
	// 更新密码
	private void getData() {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "QueryPassW";

		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_1, wsMethod);

		// 设置参数
		request.addProperty("TradeID", "60006");
		request.addProperty("Mobile", phone);
		request.addProperty("Vid", captcha);
		request.addProperty("Npsw", newPasswd);

		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request, Constants.WS_URL_1);
		httpClient.sendRequest(ForgetPasswdActivity.this, null, null, true,
				true, new WsAbstractAsyncResponseListener() {
					@Override
					protected void onSuccess(SoapObject response) {
						// 查询到数据
						if (response != null) {
							for (int i = 0; i < response.getPropertyCount(); i++) {
								SoapObject result = (SoapObject) response
										.getProperty(i);
								
								String resultCode=result.getProperty("resultCode").toString();
								if("1".equals(resultCode)){
									Utils.showToastBGNew(ForgetPasswdActivity.this, "提交成功");
									ForgetPasswdActivity.this.finish();
								}else {
									Utils.showToastBGNew(ForgetPasswdActivity.this, "提交失败");
									
								}
							}

							// 没有查询到数据
						} else {
							Utils.showToastBGNew(context, "没有查询到数据");
						}
					}

					@Override
					protected void onFailure(Exception e) {
						Utils.showToastBGNew(context,
								"查询数据出错" + e.toString());
					}
				});
	}*/

}