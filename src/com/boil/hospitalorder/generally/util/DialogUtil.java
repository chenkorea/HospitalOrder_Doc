package com.boil.hospitalorder.generally.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;

public class DialogUtil {
	public static void DialogOk(Context context, String sTitle, String sMessage) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(sMessage);
		builder.setTitle(sTitle);
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 加载等待信息
	 * @param context
	 * @param msg
	 * @param cancelable
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Dialog createLoadingViewDialog(Context context, String msg, boolean

	cancelable) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog_view, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		ImageView wtImage = (ImageView) v.findViewById(R.id.wt);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
		Animation hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(context, R.anim.scale_anim_new);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息
		wtImage.startAnimation(hyperspaceJumpAnimation2);

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;
	}
	
	/**
	 * 加载动态等待信息
	 * @param context
	 * @param msg
	 * @param cancelable
	 * @return
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public static Dialog createLoadingDialog(Context context, String msg, boolean

	cancelable) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog_new_view, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		
//		ShapeLoadingDialog shapeLoadingDialog = new ShapeLoadingDialog(context);
//		shapeLoadingDialog.setLoadingText("loading...");
//		shapeLoadingDialog.show();
		
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(cancelable);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
		return loadingDialog;
	}
}
