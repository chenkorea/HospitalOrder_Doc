package com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginDeptVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.ForgetPasswdActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CyUtils;
import com.boil.hospitalorder.utils.InstallationIdUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 登录 Activity。
 * 
 * @date 2016-02-25
 * 
 */
public class LoginActivity extends BaseBackActivity {
	/** 当前 Activity 的上下文 */
	private LoginActivity context = LoginActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 登录手机号 EditText */
	@ViewInject(R.id.edit_phone)
	private EditText etLoginPhone;
	/** 登录密码 EditText */
	@ViewInject(R.id.edit_password)
	private EditText etLoginPasswd;
	/** 登录按钮 BootstrapButton */
	@ViewInject(R.id.btn_login)
	private BootstrapButton loginBtn;
	/** 注册 TextView */
//	@ViewInject(R.id.tv_register)
//	private TextView regBtn;
	/** 忘记密码 TextView */
	@ViewInject(R.id.tv_forget_pwd)
	private TextView forgetBtn;
	@ViewInject(R.id.img_check)
	private CheckBox box;
	@ViewInject(R.id.edit_depart)
	private EditText et_dept;
	@ViewInject(R.id.edit_hos)
	private EditText edit_hos;
	
	/** 登录用户名 */
	private String loginUsername;
	/** 登录密码 */
	private String loginPasswd;
	/**登录科室*/
	private String loginDept;
	
	private LoginInfoVo infoVo;
	
	private LoginDeptVo deptVo;
	
	private List<HospitalLoginVo> vos = new ArrayList<HospitalLoginVo>();
	
	/** 选择民族的对话框 */
	private AlertDialog chooseNationDialog;
	
	/** 选择医院的对话框 */
	private AlertDialog chooseHosDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// 开启注解
		ViewUtils.inject(context);
		getHospitalLogin();
		initView();
		initEvent();
	}

	/**
	 * 
	 * 初始化组件。
	 * 
	 */
	private void initView() {
		
//		String id = InstallationIdUtils.id(context);
//		final String deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
//		Utils.backClick(this, backBtn);
		backBtn.setVisibility(View.INVISIBLE);

		addreTitle.setText("用户登陆");
		saveBtn.setVisibility(View.INVISIBLE);
		// 填充登录手机号
		etLoginPhone.setText(configSP.getString(Constants.LOGIN_INFO_ID, ""));
		// 填充登录密码
		etLoginPasswd.setText(configSP.getString(Constants.LOGIN_INFO_PWD, ""));
		// 加下划线
//		regBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forgetBtn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		forgetBtn.setVisibility(View.GONE);

		
		if(StringUtils.isNotBlank(configSP.getString(Constants.LOGIN_INFO_HISID, ""))){
			
			deptVo=new LoginDeptVo(configSP.getString(Constants.LOGIN_INFO_HISID, ""), "", configSP.getString(Constants.LOGIN_INFO_DEP_NAME, ""), configSP.getString(Constants.LOGIN_INFO_SUPERVISE, ""));
			
			et_dept.setText(deptVo.getName());
		}
		
		box.setVisibility(View.GONE);
		//设置 记住科室
		if("1".equals(configSP.getString(Constants.LOGIN_INFO_ISDEFAULT, "1"))){
			box.setChecked(true);
		}else {
			box.setChecked(false);
			
		}
		volleyClient.setActivity(context);
	}

	/**
	 * 
	 * 初始化事件。
	 * 
	 */
	private void initEvent() {
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				loginUsername = etLoginPhone.getText().toString().trim();
				loginPasswd = etLoginPasswd.getText().toString().trim();
				loginDept=et_dept.getText().toString();
				
				// 校验手机号
				if (StringUtils.isEmpty(loginUsername)) {
					Utils.showToastBGNew(context, "请输入账号");
					CyUtils.getFocus(etLoginPhone);
					
					// 校验密码
				} else if (!loginPasswd.matches(Constants.PASSWD_RE)) {
					Utils.showToastBGNew(context, Constants.PASSWD_RE_MSG);
					
					CyUtils.getFocus(etLoginPasswd);
					
					// 校验科室
				}else if (StringUtils.isEmpty(loginDept)) {
					Utils.showToastBGNew(context, "请选择科室");
				}else {
					queryUserInfo();
					
				}
			}
		});

		/*
		regBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, RegisterActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});*/
		
		et_dept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginUsername = etLoginPhone.getText().toString().trim();
				loginPasswd = etLoginPasswd.getText().toString().trim();
				
				// 校验手机号
				if (StringUtils.isEmpty(loginUsername)) {
					Utils.showToastBGNew(context, "请输入账号");
					CyUtils.getFocus(etLoginPhone);
					
					// 校验密码
				} else if (!loginPasswd.matches(Constants.PASSWD_RE)) {
					Utils.showToastBGNew(context, Constants.PASSWD_RE_MSG);
					
					CyUtils.getFocus(etLoginPasswd);
					
				}else {
					remoteLogin();
				}
			}
		});
		
		etLoginPhone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				etLoginPasswd.setText("");
				et_dept.setText("");
				deptVo=null;
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor=configSP.edit();
				if(isChecked){
					
					editor.putString(Constants.LOGIN_INFO_ISDEFAULT, "1");
				}else {
					editor.putString(Constants.LOGIN_INFO_ISDEFAULT, "0");
					
				}
				editor.commit();
			}
		});
		
		forgetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ForgetPasswdActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		edit_hos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getHospitalLogin();
			}
		});
	}

	/**
	 * 
	 * 远程登录验证。
	 * 
	 */
	private void remoteLogin() {
		
		//http://localhost:8080/hsptapp/doctor/medlogin/initlogin/201.html?uname=20001&pwd=123456
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medlogin/initlogin/201.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", loginUsername);
		params.put("pwd", loginPasswd);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								
								infoVo=com.alibaba.fastjson.JSONObject.parseObject(result, LoginInfoVo.class);
								
								Editor editor=configSP.edit();
								editor.putString(Constants.LOGIN_INFO_HID, infoVo.getId());
								editor.putString(Constants.LOGIN_INFO_HOS_NAME, infoVo.getName());
								editor.commit();
								
								if(infoVo.getDepts().size()>0){
									showChooseNationDialog();
									
								}else {
									
									Utils.showToastBGNew(context, "查不到科室数据");
								}
								
									
								
							}else {
								Utils.showToastBGNew(context, "用户名或密码错误");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询科室数据失败");
					}
				});
		
		
	}
	
	private void getHospitalLogin() {
		
		//http://58.42.232.110:8086/hsptapp/appconfig/hospitalports/8004.html
		String url = Constants.WEB_URL_4+"/hsptapp/appconfig/hospitalports/8004.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "2");

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								vos = JSON.parseArray(result, HospitalLoginVo.class);
								if (vos != null && vos.size() > 0) {
									
									showChooseHosDialog();
								}
								
							}else {
								Utils.showToastBGNew(context, "用户名或密码错误");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(context, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(context, "查询医院数据失败");
					}
				});
		
		
	}
	
	/**
	 * 
	 * 显示选择名族的对话框。
	 * 
	 */
	private void showChooseNationDialog() {
		if (chooseNationDialog == null) {
			// 显示民族列表的对话框
			chooseNationDialog = new AlertDialog.Builder(context).create();
			chooseNationDialog.show();
			
			Window window = chooseNationDialog.getWindow();
			window.setContentView(R.layout.nation_list_view);
			TextView dTitle = (TextView) window.findViewById(R.id.tv_title);
			ListView dListView = (ListView) window.findViewById(R.id.lv_nation);
			
			dTitle.setText("请选择科室");
			
			dListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					deptVo=infoVo.getDepts().get(position);
					//显示选择的科室
					et_dept.setText(deptVo.getName());
					
					
					// 关闭对话框
					chooseNationDialog.dismiss();
				}
			});
			
			if (dListView.getAdapter() == null) {
				dListView.setAdapter(new NationAdapter());
			}
		} else {
			chooseNationDialog.show();
		}
	}
	
	/**
	 * 显示选择医院
	 */
	private void showChooseHosDialog() {
		if (chooseHosDialog == null) {
			// 显示民族列表的对话框
			chooseHosDialog = new AlertDialog.Builder(context).create();
			chooseHosDialog.show();
			
			Window window = chooseHosDialog.getWindow();
			window.setContentView(R.layout.nation_list_view);
			TextView dTitle = (TextView) window.findViewById(R.id.tv_title);
			ListView dListView = (ListView) window.findViewById(R.id.lv_nation);
			
			dTitle.setText("请选择医院");
			
			dListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
					HospitalLoginVo hospitalLoginVo = vos.get(position);
					//显示选择的科室
					edit_hos.setText(hospitalLoginVo.getName());
					
					Editor editor=configSP.edit();
					editor.putString(Constants.HOSPITAL_LOGIN_ADD, hospitalLoginVo.getPort_url());
					editor.putString(Constants.LOGIN_INFO_HID, hospitalLoginVo.getHos_id());
					editor.putString(Constants.LOGIN_INFO_HOS_NAME, hospitalLoginVo.getName());
					editor.commit();
					// 关闭对话框
					chooseHosDialog.dismiss();
				}
			});
			
			if (dListView.getAdapter() == null) {
				dListView.setAdapter(new HosAdapter());
			}
		} else {
			chooseHosDialog.show();
		}
	}
	
	// 返回登录 ，完善信息选择
	@SuppressWarnings("deprecation")
	private void showDialog() {

		AlertDialog dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		dialog.setTitle(null);
		dialog.setMessage("您的信息尚未完善！");
		dialog.setButton2("去完善", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});
		/*dialog.setButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				intent.putExtra("DeptVo", (Serializable)deptVo);
				intent.putExtra("IsInfo", "0");
				startActivity(intent);
				LoginActivity.this.finish();
				dialog.dismiss();
			}
		});*/
		dialog.show();
		dialog.setCanceledOnTouchOutside(false);
	}
	
	
	/**
	 * 
	 * 获取医生信息
	 * 
	 * 
	 */
	private void queryUserInfo() {
		
		//http://localhost:8080/hsptapp/doctor/medlogin/verifylogin/202.html?uname=20001&pwd=123456&did=79&type=E
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/medlogin/verifylogin/202.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", loginUsername);
		params.put("pwd", loginPasswd);
		params.put("did", deptVo.getHisId());
		params.put("type", deptVo.getSupervise());
		params.put("macno",((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode) || "100".equals(resultCode)) {

								String result = response.getString("t");

								JSONObject object = new JSONObject(result);

								JSONObject deptObject=object.getJSONObject("dept");
								String dep_hisId=deptObject.getString("hisId");
								String dep_id=deptObject.getString("id");
								String dep_name=deptObject.getString("name");
								
								String hisId=object.getString("hisId");
								JSONObject hospitalsObject=object.getJSONObject("hospitals");
								String hospitals_id=hospitalsObject.getString("id");
								String hospitals_name=hospitalsObject.getString("name");
								
								String id=object.getString("id");
								String idcard="";
								if(object.has("idcard")){
									idcard=object.getString("idcard");
								}
								String name=object.getString("name");
								
								String phone="";
								if(object.has("phone")){
									
									phone=object.getString("phone");
								}
								
								Editor editor=configSP.edit();
								//登录不同用户则清除之前用户的最近浏览病人
								if(!loginUsername.equals(configSP.getString(Constants.LOGIN_INFO_ID, ""))){
									editor.remove(Constants.LATEST_VISIT_PATIENT_JSON);
									editor.commit();
									
								}
								
								//保存用户信息
								editor.putBoolean(Constants.LOGIN_FLAG, true);
								editor.putString(Constants.LOGIN_INFO_ID, loginUsername);
								editor.putString(Constants.LOGIN_INFO_PWD, loginPasswd);
								
								editor.putString(Constants.USER_HISID, hisId);
								editor.putString(Constants.USER_ID, id);
								editor.putString(Constants.USER_ID_NUMBER, idcard);
								editor.putString(Constants.USER_NAME, name);
								editor.putString(Constants.USER_PHONE, phone);
								
								//保存科室到偏好
								editor.putString(Constants.LOGIN_INFO_HISID, deptVo.getHisId());
								editor.putString(Constants.LOGIN_INFO_DEP_NAME, deptVo.getName());
								editor.putString(Constants.LOGIN_INFO_SUPERVISE, deptVo.getSupervise());
								
								/*
								//是否记住科室
								if(box.isChecked()){
									
									//保存科室到偏好
									editor.putString(Constants.LOGIN_INFO_HISID, deptVo.getHisId());
									editor.putString(Constants.LOGIN_INFO_DEP_NAME, deptVo.getName());
									editor.putString(Constants.LOGIN_INFO_SUPERVISE, deptVo.getSupervise());
								}else {
									editor.remove(Constants.LOGIN_INFO_HISID);
									editor.remove(Constants.LOGIN_INFO_DEP_NAME);
									editor.remove(Constants.LOGIN_INFO_SUPERVISE);
									
								}*/
								editor.commit();
							
								//跳转到个人信息页面
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								intent.putExtra("DeptVo", (Serializable)deptVo);
								if ("100".equals(resultCode)){
									intent.putExtra("IsInfo", "1");
								} 
								startActivity(intent);
								overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
								LoginActivity.this.finish();

							} else if("2".equals(resultCode)) {
								Utils.showToastBGNew(context, response.getString("resultContent"));
								Utils.clearLoginInfo(context);
								Utils.clearUserInfo(context);
							} else {
								Utils.showToastBGNew(context, "用户名或密码错误");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Utils.showToastBGNew(context, "登录失败");
						}

					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "登录失败");
					}
				});
	}
	
	
	private class NationAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return infoVo.getDepts().size();
		}

		@Override
		public Object getItem(int position) {
			return infoVo.getDepts().get(position);
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
			tvNation.setText(infoVo.getDepts().get(position).getName());
			
			return convertView;
		}
	}
	
	private class HosAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return vos.size();
		}

		@Override
		public Object getItem(int position) {
			return vos.get(position);
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
			tvNation.setText(vos.get(position).getName());
			
			return convertView;
		}
	}
}