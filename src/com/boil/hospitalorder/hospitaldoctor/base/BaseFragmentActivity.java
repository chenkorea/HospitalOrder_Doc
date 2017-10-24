package com.boil.hospitalorder.hospitaldoctor.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.FontManager;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient;
import com.boil.hospitalsecond.tool.ptrtool.MaterialHeader;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;

/**
 * 
 * 基础 FragmentActivity。
 * 
 * @author ChenYong
 * @date 2016-02-29
 *
 */
public class BaseFragmentActivity extends FragmentActivity {
	/** Config 偏好 */
	protected SharedPreferences configSP;
	/** Http 请求 Volley 客户端 */
	protected VolleyClient volleyClient;
	
	public Typeface iconFont;
	public Typeface iconFont2;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		this.configSP = Utils.getSharedPreferences(this);
		this.volleyClient = new VolleyClient(this);
		this.iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
		this.iconFont2 = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME2);
	}
	
	@Override
	public void finish() {
		// 取消所有请求
		volleyClient.cancelAll();
		
		super.finish();
	}
	
	public void initPtr(Context context,final PtrClassicFrameLayout mPtrFrameLayout) {
		// the following are default settings
		mPtrFrameLayout.setLastUpdateTimeRelateObject(context);
		mPtrFrameLayout.setResistance(1.7f);
		mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
		mPtrFrameLayout.setDurationToClose(200);
		mPtrFrameLayout.setDurationToCloseHeader(1000);
		MaterialHeader header = new MaterialHeader(context);
		int[] colors = getResources().getIntArray(R.array.google_colors);
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, Utils.dip2px(context, 15), 0,
				Utils.dip2px(context, 10));
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
	}
}