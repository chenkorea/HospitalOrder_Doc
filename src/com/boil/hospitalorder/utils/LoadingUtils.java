package com.boil.hospitalorder.utils;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;

/**
 * 
 * 等待加载的工具类。
 * 
 * @author ChenYong
 * @date 2016-03-10
 *
 */
public class LoadingUtils {
	/**
	 * 
	 * 显示等待加载的 View（菊花版）。
	 * 
	 * @param activity 当前 Activity
	 * @param loadMsg 加载消息
	 * @return 等待加载的 View
	 * 
	 */
	public static View showLoadView(Activity activity, String loadMsg) {
		if (activity == null) {
			return null;
		}
		
		View loadView = activity.findViewById(R.id.loading_view_952658);
		
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.VISIBLE) {
					ivLoading.setVisibility(View.VISIBLE);
				}
				
				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// true-只执行一次；false-循环执行
				animDrawable.setOneShot(false);
				// 启动菊花的转动
				animDrawable.start();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
				if (StringUtils.isNotEmpty(loadMsg)) {
					tvLoading.setText(loadMsg);
				}
			}
			
			if (loadView.getVisibility() != View.VISIBLE) {
				loadView.setVisibility(View.VISIBLE);
			}
		}
		
		return loadView;
	}
	
	/**
	 * 
	 * 显示等待加载的 View（菊花版）。
	 * 
	 * @param loadView 等待加载的 View
	 * @param loadMsg 加载消息
	 * @return 等待加载的 View
	 * 
	 */
	public static void showLoadView(View loadView, String loadMsg) {
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.VISIBLE) {
					ivLoading.setVisibility(View.VISIBLE);
				}
				
				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// true-只执行一次；false-循环执行
				animDrawable.setOneShot(false);
				// 启动菊花的转动
				animDrawable.start();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
				if (StringUtils.isNotEmpty(loadMsg)) {
					tvLoading.setText(loadMsg);
				}
			}
			
			if (loadView.getVisibility() != View.VISIBLE) {
				loadView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * 
	 * 显示加载消息。
	 * 
	 * @param activity 当前 Activity
	 * @param loadMsg 加载消息
	 * 
	 */
	public static View showLoadMsg(Activity activity, String loadMsg) {
		if (activity == null) {
			return null;
		}
		
		View loadView = activity.findViewById(R.id.loading_view_952658);
		
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.GONE) {
					ivLoading.setVisibility(View.GONE);
				}

				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// 停止菊花的转动
				animDrawable.stop();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
				if (StringUtils.isNotEmpty(loadMsg)) {
					tvLoading.setText(loadMsg);
				}
			}
			
			if (loadView.getVisibility() != View.VISIBLE) {
				loadView.setVisibility(View.VISIBLE);
			}
		}
		
		return loadView;
	}
	
	/**
	 * 
	 * 显示加载消息。
	 * 
	 * @param loadView 等待加载的 View
	 * @param loadMsg 加载消息
	 * 
	 */
	public static void showLoadMsg(View loadView, String loadMsg) {
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.GONE) {
					ivLoading.setVisibility(View.GONE);
				}
				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// 停止菊花的转动
				animDrawable.stop();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
				if (StringUtils.isNotEmpty(loadMsg)) {
					tvLoading.setText(loadMsg);
				}
			}
			
			if (loadView.getVisibility() != View.VISIBLE) {
				loadView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * 
	 * 隐藏等待加载的 View（菊花版）。
	 * 
	 * @param activity 当前 Activity
	 * 
	 */
	public static View hideLoadView(Activity activity) {
		if (activity == null) {
			return null;
		}
		
		View loadView = activity.findViewById(R.id.loading_view_952658);
		
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.VISIBLE) {
					ivLoading.setVisibility(View.VISIBLE);
				}
				
				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// 停止菊花的转动
				animDrawable.stop();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
			}
			
			loadView.setVisibility(View.GONE);
		}
		
		return loadView;
	}
	
	/**
	 * 
	 * 隐藏等待加载的 View（菊花版）。
	 * 
	 * @param loadView 等待加载的 View
	 * 
	 */
	public static void hideLoadView(View loadView) {
		if (loadView != null) {
			ImageView ivLoading = (ImageView) loadView.findViewById(R.id.iv_loading);
			if (ivLoading != null) {
				if (ivLoading.getVisibility() != View.VISIBLE) {
					ivLoading.setVisibility(View.VISIBLE);
				}
				
				AnimationDrawable animDrawable = (AnimationDrawable) ivLoading.getDrawable();
				// 停止菊花的转动
				animDrawable.stop();
			}
			
			TextView tvLoading = (TextView) loadView.findViewById(R.id.tv_loading);
			if (tvLoading != null) {
				if (tvLoading.getVisibility() != View.VISIBLE) {
					tvLoading.setVisibility(View.VISIBLE);
				}
				
				tvLoading.setText("");
			}
			
			loadView.setVisibility(View.GONE);
		}
	}
}