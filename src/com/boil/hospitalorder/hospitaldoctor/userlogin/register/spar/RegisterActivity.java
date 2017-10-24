package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import org.apache.commons.lang.StringUtils;
import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends BaseBackActivity {

	private RegisterActivity context = RegisterActivity.this;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.tv_prototype)
	private TextView tv_prototyp;

	@ViewInject(R.id.et_phone)
	private EditText et_phone;

	@ViewInject(R.id.btn_vertify)
	private BootstrapButton btn_vertify;

	@ViewInject(R.id.img_check)
	private CheckBox img_check;

	public static RegisterActivity instance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		ViewUtils.inject(this);
		initView();
	}

	private void initView() {

		instance = this;
		et_phone.setCursorVisible(false);
		et_phone.setText("");
		
		Utils.backClick(this, backBtn);
		addreTitle.setText("注册");
		tv_prototyp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		tv_prototyp.getPaint().setAntiAlias(true);// 抗锯齿
		tv_prototyp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(context,UserProtocalItemListActivity.class);
				in.putExtra("flag", "1");
				startActivity(in);
			}
		});

		et_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_phone.setCursorVisible(true);
				et_phone.setFocusable(true);
				et_phone.setFocusableInTouchMode(true);
			}
		});

		btn_vertify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String phone = et_phone.getText().toString().trim();

				if (StringUtils.isNotBlank(phone)) {
					if (!Utils.isMobileNO(phone)) {
						Utils.showToastBGNew(context, "手机号格式不正确");
						return;
					}
				} else {
					Utils.showToastBGNew(context, "电话号码不能为空");
					return;
				}
				if (!img_check.isChecked()) {
					Utils.showToastBGNew(context, "请先同意协议才能继续");
					return;
				}

//				vertifyCode(phone);
			}
		});
	}
	
	/*private void vertifyCode(final String phoneAccount) {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "UserInfo";

		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_1, wsMethod);
		// 设置参数
		request.addProperty("TradeID", "60008");
		request.addProperty("Mobile", phoneAccount);
		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request, Constants.WS_URL_1);
		httpClient.sendRequest(context, null, null, true, true, new WsAbstractAsyncResponseListener() {
			@Override
			protected void onSuccess(SoapObject response) {
				
				if (response != null) {
					SoapObject result = (SoapObject) response.getProperty(0);
					ResultRes res = Soap2Utils.parseBean(result, ResultRes.class);
					*//**
					 * 1,表示注册过，0表示没注册过
					 * *//*
					if("1".equals(res.getResultCode())) {
						Utils.showToastBGNew(context, "手机号已被注册");
					} else if("0".equals(res.getResultCode())) {
						Intent intent = new Intent(context, RegisterGetVertifyActivity.class);
						intent.putExtra("phone", phoneAccount);
						startActivity(intent);
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
					}
				} else {
					Utils.showToastBGNew(context, "没有查询到数据");
				}
			}

			@Override
			protected void onFailure(Exception e) {
				Utils.showToastBGNew(context, "查询数据出错");
			}
		});
	}*/
}
