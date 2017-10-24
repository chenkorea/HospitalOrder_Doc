package com.boil.hospitalorder.generally.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommonDialog {
	
	public static Dialog getDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
//		dialog.setTitle("");
		dialog.setMessage("数据读取中,请稍候...");
		//View view = dialog.findViewById(android.R.id.);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		return dialog;
	}
	
	public static Dialog getDialog2(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage("数据处理中,请稍候...");
		//View view = dialog.findViewById(android.R.id.);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		return dialog;
	}
	
	public static Toast getToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
		LinearLayout layout = (LinearLayout)toast.getView();
        TextView view = (TextView)layout.getChildAt(0);
        view.setText(text);
        view.setTextSize(14);
        //view.setBackgroundColor(context.getResources().getColor(R.color.white));
       // view.setTextColor(context.getResources().getColor(R.color.holo_blue_dark));
        toast.setView(layout);
		return toast;
	}
	
	public static Toast getBigToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
		LinearLayout layout = (LinearLayout)toast.getView();
        TextView view = (TextView)layout.getChildAt(0);
        view.setText(text);
        view.setTextSize(18);
        //view.setTextColor(R.color.white);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM,0,0);
		return toast;
	}
	
	public static ProgressDialog getProgress(Context context, String text, int maxValue) {
		ProgressDialog dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("文件下载进度");
		//dialog.setMessage("数据读取中,请稍候...");
		dialog.setMax(maxValue);

		return dialog;
	}
	
}
