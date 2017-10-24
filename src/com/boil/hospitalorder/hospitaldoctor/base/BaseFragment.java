package com.boil.hospitalorder.hospitaldoctor.base;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.MaterialHeader;
import com.boil.hospitalorder.utils.PtrClassicFrameLayout;
import com.boil.hospitalorder.utils.PtrFrameLayout;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 基础 Fragment。
 * 
 * @author ChenYong
 * @date 2016-02-29
 * 
 */
public class BaseFragment extends Fragment {
	/** Config 偏好 */
	protected SharedPreferences configSP;
	/** 布局加载器 */
	protected LayoutInflater inflater;

	public Typeface iconFont;
	public Typeface iconFont2;
	
	protected VolleyClient volleyClient;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		configSP = Utils.getSharedPreferences(getActivity());
		inflater = getActivity().getLayoutInflater();
		iconFont = FontManager.getTypeface(getActivity(),
				FontManager.FONTAWESOME);
		iconFont2 = FontManager.getTypeface(getActivity(),
				FontManager.FONTAWESOME2);
		
		volleyClient = new VolleyClient(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	
	public void initPtr(Context context, final PtrClassicFrameLayout mPtrFrameLayout) {
		// the following are default settings
		mPtrFrameLayout.setLastUpdateTimeRelateObject(context);
		mPtrFrameLayout.setResistance(1.7f);
		mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrameLayout.setDurationToClose(200);
		mPtrFrameLayout.setDurationToCloseHeader(1000);
		MaterialHeader header = new MaterialHeader(getActivity());
		int[] colors = getResources().getIntArray(R.array.google_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, Utils.dip2px(getActivity(), 15), 0,
				Utils.dip2px(getActivity(), 10));
		header.setPtrFrameLayout(mPtrFrameLayout);
		mPtrFrameLayout.setDurationToClose(100);
		mPtrFrameLayout.setPinContent(true);
		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setDurationToCloseHeader(1500);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.setHeaderView(header);
		// default is false
		mPtrFrameLayout.setPullToRefresh(false);
		// default is true
		mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
	/*	mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 100);*/

	}
	
	/**
	 * 
	 * 判断用户是否已经登录。
	 * 
	 * @return 是否登录：
	 *         <ol>
	 *         <li>true-已登录；</li>
	 *         <li>false-未登录。</li>
	 *         <ol>
	 * 
	 */
	protected boolean isLogin() {
		return configSP.getBoolean(Constants.LOGIN_FLAG, false);
	}

	/**
	 * 
	 * 如果未登录，跳转至登录界面。
	 * 
	 */
	protected void toLogin() {
		// 如果未登录，跳转至登陆界面。
		if (!isLogin()) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
		}
	}
}