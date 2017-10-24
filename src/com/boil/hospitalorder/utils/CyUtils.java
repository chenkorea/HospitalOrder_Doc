package com.boil.hospitalorder.utils;

import android.content.Context;
import android.view.View;

/**
 * 
 * 我的工具类。
 * 
 * @author ChenYong
 * @date 2016-01-27
 * 
 */
public class CyUtils {
	/**
	 * 
	 * 让视图 v 获得焦点。
	 * 
	 * @param v 视图
	 * 
	 */
	public static void getFocus(View v) {
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
	}

	/**
	 * 
	 * 将 dp 转换为 px（像素）。
	 * 
	 * @param context 上下文
	 * @param dp
	 * @return px 像素
	 * 
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		
		return (int) (dp * scale + 0.5F);
	}
	
	/**
	 * 
	 * 将 sp 转换为 px（像素） 。
	 * 
	 * @param context 上下文
	 * @param sp
	 * @return px 像素
	 * 
	 */
	public static int sp2px(Context context, float sp) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		
		return (int) (sp * scale + 0.5f);
	}

	/**
	 * 
	 * 将 px（像素）转换为 dp。
	 * 
	 * @param context 上下文
	 * @param px 像素
	 * @return dp
	 * 
	 */
	public static int px2dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		
		return (int) (px / scale + 0.5F);
	}
	
	/**
	 * 
	 * 将 px（像素） 转换为 sp。
	 * 
	 * @param context 上下文
	 * @param px 像素
	 * @return sp
	 * 
	 */
	public static int px2sp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		
		return (int) (px / scale + 0.5f);
	}
}