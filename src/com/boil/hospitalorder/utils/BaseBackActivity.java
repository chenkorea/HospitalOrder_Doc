package com.boil.hospitalorder.utils;

import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.volley.http.VolleyClient;
import com.boil.hospitalsecond.listener.BackGestureListener;
import com.boil.hospitalsecond.tool.ptrtool.MaterialHeader;
import com.boil.hospitalsecond.tool.ptrtool.PtrClassicFrameLayout;
import com.boil.hospitalsecond.tool.ptrtool.PtrFrameLayout;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

/**
 * 
 * 基础 Activity。
 * 
 * @date 2016-02-26
 * 
 */
public class BaseBackActivity extends Activity {
	/** Config 偏好 */
	protected SharedPreferences configSP;
	/** 布局加载器 */
	protected LayoutInflater inflater;
	private BaseBackLayout baseBackLayout;
	public HorizontalScrollView mTouchView;
	public HorizontalScrollView mWTouchView;
	protected List<CHScrollView> mHScrollViews = new ArrayList<CHScrollView>();
	protected List<CHScrollView> mWHScrollViews = new ArrayList<CHScrollView>();
	protected Typeface iconFont;
	protected Typeface iconFont2;
	/** Http 请求 Volley 客户端 */
	protected VolleyClient volleyClient;
	
	/** 手势监听 */
	GestureDetector mGestureDetector;
	/** 是否需要监听手势关闭功能 */
	private boolean mNeedBackGesture = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 创建BaseBackLayout，并附加到activity上
		this.baseBackLayout = new BaseBackLayout(this);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		this.baseBackLayout.setLayoutParams(lp);
		this.baseBackLayout.attachToActivity(this);

		this.configSP = Utils.getSharedPreferences(this);
		this.inflater = getLayoutInflater();
		this.iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
		this.iconFont2 = FontManager.getTypeface(this, FontManager.FONTAWESOME2);
		this.volleyClient = new VolleyClient(this);

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus(true);
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setStatusBarTintResource(R.color.theme_color_1);//
		// 通知栏所需颜色
		// }R.layout.loading_view_952658
		initGestureDetector();

	}

	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (mHScrollViews != null && mHScrollViews.size() > 0) {
			for (CHScrollView scrollView : mHScrollViews) {
				// 防止重复滑动
				if (mTouchView != scrollView)
					scrollView.smoothScrollTo(l, t);
			}
		}
		EditText editText = new EditText(BaseBackActivity.this);
		editText.clearFocus();
	}

	// 开启或关闭滑动
	public void setSwipeEnable(boolean swipeEnable) {
		baseBackLayout.setSwipeEnable(swipeEnable);
	}

	/**
	 * 不使用方法，用于弹出水波纹效果
	 * 
	 * @param view
	 */
	public void noUse(View view) {
		
	}

	/**
	 * 设置状态栏颜色背景
	 * 
	 * @param on
	 */
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		// final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		// if (on) {
		// winParams.flags |= bits;
		// } else {
		// winParams.flags &= ~bits;
		// }
		win.setAttributes(winParams);
	}

	@Override
	public void finish() {
		// 取消所有请求
		volleyClient.cancelAll();
		
		super.finish();
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
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
//		if (!isLogin()) {
//		}
	}
	
	private void initGestureDetector() {
		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getApplicationContext(),
					new BackGestureListener(this));
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(mNeedBackGesture){
			return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}
	
	/*
	 * 设置是否进行手势监听
	 */
	public void setNeedBackGesture(boolean mNeedBackGesture){
		this.mNeedBackGesture = mNeedBackGesture;
	}
	
	/*
	 * 返回
	 */
	public void doBack(View view) {
		onBackPressed();
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