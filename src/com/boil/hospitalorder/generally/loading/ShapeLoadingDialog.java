package com.boil.hospitalorder.generally.loading;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.boil.hospitalorder.hospitaldoctor.R;

/**
 * 
 * 会改变形状的  HUB 等待框。
 * 
 * @author zzz40500
 * @date 2015-06-15
 * 
 */
public class ShapeLoadingDialog {
	private Context mContext;
	private Dialog mDialog;
	private LoadingView mLoadingView;
	private View mDialogContentView;

	public ShapeLoadingDialog(Context context) {
		this.mContext = context;
		init();
	}

	@SuppressLint("InflateParams")
	private void init() {
		mDialog = new Dialog(mContext, R.style.custom_dialog);
		mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog, null);

		mDialog.setCancelable(false);
		mLoadingView = (LoadingView) mDialogContentView.findViewById(R.id.loadView);
		mDialog.setContentView(mDialogContentView);
	}

	public void setBackground(int color) {
		GradientDrawable gradientDrawable = (GradientDrawable) mDialogContentView.getBackground();
		gradientDrawable.setColor(color);
	}

	public void setLoadingText(CharSequence charSequence) {
		mLoadingView.setLoadingText(charSequence);
	}

	public void show() {
		if (mDialog != null) {
			mDialog.show();
		}
	}

	public void dismiss() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	public Dialog getDialog() {
		return mDialog;
	}

	public void setCanceledOnTouchOutside(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
	}
	
	/**
	 * 
	 * 点击返回键时，取消 HUB 等待框。
	 * 
	 */
	public void clickBackKeyCancel() {
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(false);
		
		mDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
	}
}