package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CyUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 修改密码 Activity。
 * 
 * @author ChenYong
 * @date 2016-01-27
 * 
 */
public class UpdPasswdActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private UpdPasswdActivity context = UpdPasswdActivity.this;
	/** 返回上一个 Activity 的按钮 */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView titleTv;
	/** 标题上的保存按钮 */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 注册用户的手机号 */
	@ViewInject(R.id.phone_tv)
	private TextView tvPhone;
	/** 旧密码 EditText */
	@ViewInject(R.id.old_passwd_et)
	private EditText oldPasswdEt;
	/** 新密码 EditText */
	@ViewInject(R.id.new_passwd_et)
	private EditText newPasswdEt;
	/** 确认新密码 EditText */
	@ViewInject(R.id.confirm_new_passwd_et)
	private EditText confirmNewPasswdEt;
	/** 修改按钮 BootstrapButton */
	@ViewInject(R.id.bb_update_btn)
	private BootstrapButton bbUpdateBtn;
	/** 旧密码 */
	private String oldPasswd;
	/** 新密码 */
	private String newPasswd;
	/** 确认新密码 */
	private String confirmNewPasswd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upd_passwd_view);

		// 开启注解
		ViewUtils.inject(context);

		// 初始化视图组件
		initView();
		// 初始化各种事件
		initEvent();
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
		titleTv.setText("修改密码");
		// 初始化时隐藏标题上的保存按钮
		saveBtn.setVisibility(View.INVISIBLE);
		tvPhone.setText(configSP.getString(Constants.LOGIN_INFO_ID, ""));
	}
	
	/**
	 * 
	 * 初始化各种事件。
	 * 
	 */
	private void initEvent() {
		// 点击修改按钮
		bbUpdateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dataValid()) {
					changePwd();
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
		oldPasswd = oldPasswdEt.getText().toString().trim();
		newPasswd = newPasswdEt.getText().toString().trim();
		confirmNewPasswd = confirmNewPasswdEt.getText().toString().trim();
		
		// 校验旧密码
		if (StringUtils.isEmpty(oldPasswd)) {
			Utils.showToastBGNew(context, "请输入旧密码");
			CyUtils.getFocus(oldPasswdEt);
			
			return false;
		} 
		
		// 校验新密码
		if (StringUtils.isEmpty(newPasswd)) {
			Utils.showToastBGNew(context, "请输入新密码");
			CyUtils.getFocus(newPasswdEt);
			return false;
		} else {
			if (!newPasswd.matches(Constants.PASSWD_RE3)) {
				Utils.showToastBGNew(context, "新" + Constants.PASSWD_RE_MSG3);
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
		
		return true;
	}
	
	/**
	 * 
	 * 修改密码。
	 * 
	 */
	private void changePwd() {
		//localhost:8080/hsptapp/doctor/medlogin/updmedadmpwd/204.html?uname=20001&oldpwd=123456&newpwd=000000
		String url = "http://58.42.232.110:8086/hsptapp/doctor/medlogin/updmedadmpwd/204.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("newpwd", newPasswd);
		params.put("oldpwd", oldPasswd);
		params.put("uname", configSP.getString(Constants.LOGIN_INFO_ID, ""));

		bbUpdateBtn.setText("正在修改……");
		bbUpdateBtn.setEnabled(false);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");

							if ("1".equals(resultCode)) {

								Utils.showToastBGNew(context, "修改密码成功");
								Utils.clearLoginInfo(context);
								Utils.clearUserInfo(context);

								toLogin();

								overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

								finish();

							} else {
								Utils.showToastBGNew(context, "修改密码失败");

								bbUpdateBtn.setText("修改");
								bbUpdateBtn.setEnabled(true);
							}
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "修改密码失败");

							bbUpdateBtn.setText("修改");
							bbUpdateBtn.setEnabled(true);
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "修改密码失败");
						bbUpdateBtn.setText("修改");
						bbUpdateBtn.setEnabled(true);
					}
				});
		
	}
}