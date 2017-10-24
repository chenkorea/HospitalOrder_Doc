package com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.WelcomActivity;
import com.boil.hospitalorder.utils.Utils;

public class Fragment3 extends Fragment {

	private boolean isSet;
	private Handler handler = new Handler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.view_welcome3, container, false);
		isSet = ((WelcomActivity)getActivity()).isSet;
		TextView mGoButton  = (TextView)view.findViewById(R.id.btn_go_app);
		mGoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//如果是设置界面点击欢迎界面的“立即体验”按钮 将不做任何操作
				if(!isSet){
					gotoMainPage();
				}
			}
		});
		
		return view;
	}

	
	private void gotoMainPage() {
		// 偏好设置
		// 设置 Web Service 的命名空间
		Utils.savePref("WS_NAMESPACE", "http://webser.data.zlyy", getActivity());
		// 设置 Web Service 的请求地址
		Utils.savePref("WS_URL", "http://yyghw.gzszlyy.cn:8086/ZlyyData/services/ZlyyData", getActivity());
		
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
		((WelcomActivity)getActivity()).sp.edit().putBoolean("isFirst", false).commit();
		getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		getActivity().finish();
	}
}
