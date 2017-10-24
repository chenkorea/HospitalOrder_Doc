package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * 完善个人信息 Activity。 
 * 
 * @author ChenYong
 * @date 2016-01-26
 *
 */
public class PerfectUserInfoActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private PerfectUserInfoActivity context = PerfectUserInfoActivity.this;
	/** 返回上一个 Activity 的按钮 */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView titleTv;
	/** 标题上的保存按钮 */
	@ViewInject(R.id.bt_save)
	private TextView titleSaveBtn;
	/** 姓名 EditText */
	@ViewInject(R.id.et_name)
	private EditText etName;
	/** 身份证 EditText */
	@ViewInject(R.id.et_id_card)
	private EditText etIdCard;
	@ViewInject(R.id.et_phone)
	private EditText etPhone;
	
	@ViewInject(R.id.et_code)
	private EditText et_code;

	/** 登录id */
	private String loginId;
	
	/** 姓名 */
	private String name;
	/** 身份证号 */
	private String idCard;
	/**手机号*/
	private String phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.perfect_user_info_view);
		
		// 开启注解
		ViewUtils.inject(context);
		
		// 初始化视图组件
		initView();
		// 初始化数据
		initData();
		
	}
	
	/**
	 * 
	 * 初始化数据。
	 * 
	 */
	private void initData() {
		loginId = configSP.getString(Constants.LOGIN_INFO_ID, "");
		setUserData();
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
		titleTv.setText("个人信息");
		// 隐藏标题上的保存按钮
		titleSaveBtn.setVisibility(View.INVISIBLE);
		
		volleyClient.setActivity(context);
	}
	
	private void setUserData(){
		
		name = configSP.getString(Constants.USER_NAME, "");
		phone = configSP.getString(Constants.USER_PHONE, "");
		idCard = configSP.getString(Constants.USER_ID_NUMBER, "");
		etName.setText(name);
		etIdCard.setText(idCard);
		etPhone.setText(phone);
		et_code.setText(loginId);
		
		etIdCard.setEnabled(false);
		etName.setEnabled(false);
		etPhone.setEnabled(false);
		et_code.setEnabled(false);
		
	}
}