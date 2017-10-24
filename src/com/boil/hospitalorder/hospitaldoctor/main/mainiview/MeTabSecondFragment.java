package com.boil.hospitalorder.hospitaldoctor.main.mainiview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.BulletinSecondActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.MyCheckActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.MySalaryActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.SetActivity;
import com.boil.hospitalorder.hospitaldoctor.my.spar.ColligateServiceActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.PerfectUserInfoActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.PerfectUserInfoCommitActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.UpdPasswdActivity;
import com.boil.hospitalorder.utils.ActionSheetDialogNewPhoto;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;

/**
 * 
 * 个人中心页面。
 * 
 * @author ct
 * 
 */
public class MeTabSecondFragment extends BaseFragment {
	/** 我的工资*/
	private LinearLayout lay2;
	/** 设置 */
	private LinearLayout lay3;
	/** 我的服务*/
	private LinearLayout lay4;
	/** 我的体检*/
	private LinearLayout lay5;
	/** 公告信息 */
	private LinearLayout lay_notice;
	
	/** 上传头像的对话框 */
	private ActionSheetDialogNewPhoto dialog;
	private ImageView profile_img;
	/** 个人信息 */
	private RelativeLayout my_1_lay;
	/** 修改密码 RelativeLayout */
	private RelativeLayout my_2_lay;
	/** 登录按钮 */
	private TextView loginBtn;
	/** 姓名/昵称 */
	private TextView nameTv;
	/** 登陆标志 */
	private boolean loginFlag;
	/** 登录手机号 */
	private String loginPhone;
	/** 登录姓名 */
	private String loginName;
	private RelativeLayout web_container;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_metab_second, container, false);

		initView(view);
		initEvent();

		return view;
	}

	@Override
	public void onResume() {
		showLoginName();
		super.onResume();
	}

	private void initView(View view) {
		lay_notice = (LinearLayout) view.findViewById(R.id.lay_notice);
		lay2 = (LinearLayout) view.findViewById(R.id.set_2);
		lay3 = (LinearLayout) view.findViewById(R.id.set_3);
		lay4 = (LinearLayout) view.findViewById(R.id.set_4);
		lay5 = (LinearLayout) view.findViewById(R.id.set_5);
		my_1_lay = (RelativeLayout) view.findViewById(R.id.my_1_lay);
		my_2_lay = (RelativeLayout) view.findViewById(R.id.my_2_lay);
		profile_img = (CLRoundImageView) view.findViewById(R.id.header);
		loginBtn = (TextView) view.findViewById(R.id.personal_login_button);
		nameTv = (TextView) view.findViewById(R.id.name);
		web_container = (RelativeLayout) view.findViewById(R.id.web_container);
		FontManager.markAsIconContainer(web_container, iconFont2);
		showLoginName();
	}

	private void initEvent() {
		lay5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Utils.showToastBGNew(getActivity(), "我的体检");
				Intent in = new Intent(getActivity(), MyCheckActivity.class);
				startActivity(in);
				
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
		lay4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isOpenInter();
			}
		});

		lay3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent in = new Intent(getActivity(), SetActivity.class);
				startActivity(in);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		lay2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), MySalaryActivity.class);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		lay_notice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Utils.showToastBGNew(getActivity(), "公告信息");
				
				Intent intent = new Intent(getActivity(), BulletinSecondActivity.class);
				intent.putExtra("flag", "3");
				getActivity().startActivity(intent);

				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				
			}
		});


		profile_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				getSelectTonnageDialog().show();
				if(isLogin()) {
					Intent intent = new Intent(getActivity(), PerfectUserInfoActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				} else {
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
				
			}
		});

		my_1_lay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Utils.showToastBGNew(getActivity(), "个人信息");
				// 已登录
				if (isLogin()) {
					Intent intent = new Intent(getActivity(), PerfectUserInfoActivity.class);
					getActivity().startActivity(intent);

					// 未登录
				} else {
					toLogin();

				}

				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		my_2_lay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), UpdPasswdActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});

		nameTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PerfectUserInfoCommitActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
		
	}
	
	private void isOpenInter() {
		//http://localhost:8080/hsptapp/appconfig/serviceisusable.html
		String url = "http://58.42.232.110:8086/hsptapp/appconfig/serviceisusable.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
	
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {
	
					@Override
					public void success(JSONObject response) {
	
						try {
							String resultCode = response.getString("resultCode");
							String resultContent = response.getString("resultContent");
							if ("1".equals(resultCode)) {
								Intent in = new Intent(getActivity(), ColligateServiceActivity.class);
								startActivity(in);
								getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
							} else {
								Toast.makeText(getActivity(), resultContent, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							Utils.showToastBGNew(getActivity(), "暂不能查看");
							e.printStackTrace();
						}
					}
	
					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(getActivity(), "暂不能查看");
					}
				});
	}

	/**
	 * 
	 * 显示图片选取。
	 * 
	 * @return
	 * 
	 */
	@SuppressLint("ShowToast")
	private ActionSheetDialogNewPhoto getSelectTonnageDialog() {
		if (dialog == null) {
			dialog = new ActionSheetDialogNewPhoto(getActivity()).builder();
			dialog.setCancelable(false).setCanceledOnTouchOutside(false);
			List<String> list = new ArrayList<String>();
			list.add("图片");
			list.add("照相");
			for (String carType : list) {
				dialog.addSheetItem(
						carType, //
						ActionSheetDialogNewPhoto.SheetItemColor.Blue, //
						false, //
						new ActionSheetDialogNewPhoto.OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								if (which == 2) {
									// 照相
									getActivity().overridePendingTransition(R.anim.actionsheet_dialog_in, R.anim.actionsheet_dialog_out);
								} else if (which == 1) {
									// 图片
									Toast.makeText(getActivity(), "1", 0).show();
								}
							}
						});
			}

			dialog.setSheetItems();
		}

		return dialog;
	}

	/**
	 * 
	 * 显示登录用户的姓名。
	 * 
	 */
	private void showLoginName() {
		loginFlag = configSP.getBoolean(Constants.LOGIN_FLAG, false);
		
		loginPhone = configSP.getString(Constants.LOGIN_INFO_ID, "");
		
		loginName = configSP.getString(Constants.USER_NAME, "");

		// 如果用户已登录
		if (loginFlag) {
			nameTv.setVisibility(View.VISIBLE);
			loginBtn.setVisibility(View.GONE);

			if (StringUtils.isNotEmpty(loginName)) {
				nameTv.setText(loginName);
			} else {
				nameTv.setText(loginPhone);
			}

			// 如果用户未登录
		} else {
			loginBtn.setVisibility(View.VISIBLE);
			nameTv.setVisibility(View.INVISIBLE);

			nameTv.setText("");
		}
	}
}