package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.MyApplication;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CyUtils;
import com.boil.hospitalorder.utils.IdcardUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 完善个人信息 Activity。 
 * 
 * @author ChenYong
 * @date 2016-01-26
 *
 */
public class PerfectUserInfoCommitActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private PerfectUserInfoCommitActivity context = PerfectUserInfoCommitActivity.this;
	/** 返回上一个 Activity 的按钮 */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView titleTv;
	/** 标题上的保存按钮 */
	@ViewInject(R.id.bt_save)
	private TextView titleSaveBtn;
	/** 用户信息 ScrollView */
	@ViewInject(R.id.sv_user_info_view)
	private ScrollView scUserInfoView;
	/** 姓名 EditText */
	@ViewInject(R.id.et_name)
	private EditText etName;
	/** 身份证 EditText */
	@ViewInject(R.id.et_id_card)
	private EditText etIdCard;
	@ViewInject(R.id.et_phone)
	private EditText etPhone;

	/** 保存按钮 BootstrapButton */
	@ViewInject(R.id.bb_save_btn)
	private BootstrapButton bbSaveBtn;

	/** 登录id */
	private String loginId;
	/**登录密码*/
	private String loginPwd;
	
	/** 姓名 */
	private String name;
	/** 身份证号 */
	private String idCard;
	/**手机号*/
	private String phone;
	
	private String pwd;
	
//	@ViewInject(R.id.bb_send_captcha)
//	private BootstrapButton bb_send_captcha;
	
	@ViewInject(R.id.et_pwd_pre)
	private EditText et_pwd_pre;
	
	@ViewInject(R.id.et_pwd_sure)
	private EditText et_pwd_sure;
	
	/** 获取验证码的定时器 */
	private Timer captchaTimer;
	/** 获取验证码的 Handler */
//	private CaptchaHandler captchaHandler;
	/** 获取验证码的周期 */
	private long captchaInterval;
	
	/**支付的定时器 */
	private Timer payTimer;
	
	@ViewInject(R.id.et_vcode)
	private EditText et_vcode;
	
	@ViewInject(R.id.tv_prototype)
	private TextView tv_prototyp;
	
	@ViewInject(R.id.img_check)
	private CheckBox img_check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.perfect_user_info_commit_view);
		
		// 开启注解
		ViewUtils.inject(context);
		
		// 初始化视图组件
		initView();
		// 初始化数据
		initData();
		// 初始化各种事件
		initEvent();
	}
	
	/**
	 * 
	 * 初始化数据。
	 * 
	 */
	private void initData() {
		loginId = configSP.getString(Constants.LOGIN_INFO_ID, "");
		loginPwd=configSP.getString(Constants.LOGIN_INFO_PWD, "");
		name = configSP.getString(Constants.USER_NAME, "");
		phone = configSP.getString(Constants.USER_PHONE, "");
		idCard = configSP.getString(Constants.USER_ID_NUMBER, "");
		
		setUserData();
	}
	
	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		// 返回上一个 Activity
		backBtn.setVisibility(View.INVISIBLE);
//		Utils.backClick(context, backBtn);
		// 设置标题
		titleTv.setText("完善个人信息");
		// 隐藏标题上的保存按钮
		titleSaveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
//		captchaHandler = new CaptchaHandler();
	}
	
	/**
	 * 
	 * 初始化各种事件。
	 * 
	 */
	private void initEvent() {
		
//		//点击发送验证码
//		bb_send_captcha.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//
//				if(dataValid()) {
//					// 禁用按钮
//					bb_send_captcha.setEnabled(false);
//					// 120 秒后才能获取下一次验证码
//					captchaInterval = 2L * 60L * 1000L;
//					// 创建获取验证码的定时器
//					captchaTimer = new Timer();
//					// 为获取验证码的 Handler 设置获取验证码的按钮
//					captchaHandler.setBbSendCaptcha(bb_send_captcha);
//					
//					captchaTimer.schedule(new TimerTask() {
//						@Override
//						public void run() {
//							Message captchaMsg = new Message();
//							captchaMsg.what = 0x123;
//							captchaMsg.getData().putLong("captchaInterval", (captchaInterval / 1000L));
//							
//							captchaHandler.sendMessage(captchaMsg);
//							
//							captchaInterval = captchaInterval - 1000L;
//						}
//					}, 0, 1000);
//				}
//			}
//		});
		
		
		tv_prototyp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(context,UserProtocalItemListActivity.class);
				in.putExtra("flag", "4");
				startActivity(in);
			}
		});
		
		
		// 点击保存按钮，提交数据
		bbSaveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 提交数据
				if (dataValid()) {
					String vCode = et_vcode.getText().toString().trim();
					if(StringUtils.isEmpty(vCode)) {//验证码输入
						Utils.showToastBGNew(context, "请输入验证码");
						return;
					} else {
						saveUserInfo(vCode);
					}
				}
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
		// 获取姓名
		name = etName.getText().toString().trim();
		// 获取身份证
		idCard = etIdCard.getText().toString().trim().toUpperCase(Locale.getDefault());
		
		phone=etPhone.getText().toString().trim();
		
		pwd = et_pwd_pre.getText().toString().trim();
		String pwdSure = et_pwd_sure.getText().toString().trim();
		
		// 校验姓名
		if (StringUtils.isEmpty(name)) {
			Utils.showToastBGNew(context, "请输入姓名");
			
			CyUtils.getFocus(etName);
			
			return false;
			
		} else if (!name.matches(Constants.NAME_RE)) {
			Utils.showToastBGNew(context, Constants.NAME_RE_MSG);
			
			CyUtils.getFocus(etName);
			
			return false;
		}
		
		// 校验身份证
		if (StringUtils.isEmpty(idCard)) {
			Utils.showToastBGNew(context, "请输入身份证号");
			
			CyUtils.getFocus(etIdCard);
			
			return false;
			
		} else if(!IdcardUtils.validateCard(idCard)){
			
			Utils.showToastBGNew(context, "身份证号格式错误");
			CyUtils.getFocus(etIdCard);
			return false;
		}
		
		//校验密码
		if(StringUtils.isEmpty(pwd)) {
			Utils.showToastBGNew(context, "请输入新密码");
			CyUtils.getFocus(et_pwd_pre);
			return false;
		} else if(!Utils.isPasswordDigitStrAndOk(pwd)) {
			Utils.showToastBGNew(context, Constants.PASSWD_RE_MSG3);
			CyUtils.getFocus(et_pwd_pre);
			return false;
		}
		if(StringUtils.isEmpty(pwdSure)) {
			Utils.showToastBGNew(context, "请输入确认新密码");
			CyUtils.getFocus(et_pwd_sure);
			return false;
		}
		if(!pwd.equals(pwdSure)) {
			Utils.showToastBGNew(context, "两次输入的密码不一样");
			CyUtils.getFocus(et_pwd_sure);
			return false;
		}
		
		//校验手机号
		
		if(StringUtils.isEmpty(phone)){
			Utils.showToastBGNew(context, "请输入手机号");
			
			CyUtils.getFocus(etPhone);
			
			return false;
			
		}else if (!Utils.isMobileNO(phone)) {
			Utils.showToastBGNew(context, "手机号格式错误");
			
			CyUtils.getFocus(etPhone);
			
			return false;
		}
		
		
		if (!img_check.isChecked()) {
			Utils.showToastBGNew(context, "请先同意协议才能继续");
			return false;
		}
		
		return true;
	}
	
	private void setUserData(){
		
		etName.setText(name);
		etIdCard.setText(idCard);
		etPhone.setText(phone);
		
		etIdCard.setEnabled(false);
		etName.setEnabled(false);
		etPhone.setEnabled(false);
		
	}
	
	
	/**
	 * 
	 * 设置保存按钮是否可用：
	 * 
	 * @param enabled 是否可用：<br>
	 * <ul>
	 * 	<li>true-可用；</li>
	 * 	<li>true-不可用。</li>
	 * </ul>
	 * 
	 */
	private void setSaveBtnEnabled(boolean enabled) {
		if (enabled) {
			bbSaveBtn.setText("保存");
			bbSaveBtn.setEnabled(true);
		} else {
			bbSaveBtn.setText("正在保存……");
			bbSaveBtn.setEnabled(false);
		}
	}
	
	/**
	 * 
	 * 保存用户信息。
	 * 
	 * 
	 */
	private void saveUserInfo(String vCode) {

		// localhost:8080/hsptapp/doctor/medlogin/verifymedadm/205.html
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medlogin/verifymedadm/205.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", loginId);
		params.put("oldpwd", loginPwd);
		params.put("newpwd", pwd);
		params.put("idcard", idCard);
		params.put("vcode", vCode);
		params.put("macno", ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
		setSaveBtnEnabled(false);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								Utils.showToastBGNew(context, "保存成功");
								//保存用户信息
								Editor editor=configSP.edit();
								editor.putString(Constants.USER_ID_NUMBER, idCard);
								editor.putString(Constants.USER_NAME, name);
								editor.putString(Constants.USER_PHONE, phone);
								editor.putString(Constants.LOGIN_INFO_PWD, pwd);
								editor.commit();
								finish();
								
							} else if("-100".equals(resultCode)){
								Utils.showToastBGNew(context, "短信验证码错误或过期");
							} else if("-101".equals(resultCode)) {
								Utils.showToastBGNew(context, "医生信息不匹配");
							} else if("-102".equals(resultCode)) {
								Utils.showToastBGNew(context, "身份证号或密码不符合规则");
							} else {
								Utils.showToastBGNew(context, "保存失败");
							}
							
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "保存失败");
						}
						setSaveBtnEnabled(true);
					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "保存失败");
						setSaveBtnEnabled(true);
					}
				});
	}
		
	 /**
   	 * 
   	 * 获取验证码的 Handler。
   	 * 
   	 * @author ChenYong
   	 * @date 2016-01-28
   	 *
   	 */
//   	@SuppressLint("HandlerLeak")
//   	private class CaptchaHandler extends Handler {
//   		/** 发送验证码的按钮 */
//   		private BootstrapButton bbSendCaptcha;
//   		
//   		/**
//   		 * 
//   		 * 默认构造器。
//   		 * 
//   		 */
//   		public CaptchaHandler() {
//   			super();
//   		}
//   		
//   		/**
//   		 * 
//   		 * 设置发送验证码的按钮。
//   		 * 
//   		 * @param bbSendCaptcha 发送验证码的按钮
//   		 * 
//   		 */
//   		public void setBbSendCaptcha(BootstrapButton bbSendCaptcha) {
//   			this.bbSendCaptcha = bbSendCaptcha;
//   		}
//
//   		/**
//   		 * 
//   		 * 消息处理。
//   		 * 
//   		 */
//   		@Override
//   		public void handleMessage(Message msg) {
//   			if (msg.what == 0x123) {
//				long captchaInterval1 = msg.getData()
//						.getLong("captchaInterval");
//
//				if (captchaInterval1 > 0) {
//					bbSendCaptcha.setText(String.format("等待%s秒",
//							captchaInterval1));
//				} else {
//					cancelCaptchaTimer();
//				}
//			} 
//   		}
//   	}
   	
    /**
     * 
     * 取消发送验证码的定时器。
     * 
     */
//    private void cancelCaptchaTimer() {
//    	if (captchaTimer != null) {
//    		// 启用按钮
//    		bb_send_captcha.setEnabled(true);
//    		bb_send_captcha.setText("获取验证码");
//    		
//    		// 取消定时器
//    		captchaTimer.cancel();
//    	}
//    }

	

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	           MyApplication.getInstance().exit();
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}