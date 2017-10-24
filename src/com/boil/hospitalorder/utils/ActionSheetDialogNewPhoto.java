package com.boil.hospitalorder.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;

public class ActionSheetDialogNewPhoto {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;
	private TextView txt_cancel;
	private LinearLayout lLayout_content;
	private ScrollView sLayout_content;
	private boolean showTitle = false;
	private List<SheetItem> sheetItemList;
	private Display display;
	private List<ImageView> imageList=new ArrayList();

	public ActionSheetDialogNewPhoto(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public ActionSheetDialogNewPhoto builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_actionsheet, null);

		// 设置Dialog最小宽度为屏幕宽度
		view.setMinimumWidth(display.getWidth());

		// 获取自定义Dialog布局中的控件
		sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
		lLayout_content = (LinearLayout) view
				.findViewById(R.id.lLayout_content);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
  		lp.y = 0;
		dialogWindow.setAttributes(lp);

		return this;
	}

	public ActionSheetDialogNewPhoto setTitle(String title) {
		showTitle = true;
		txt_title.setVisibility(View.VISIBLE);
		txt_title.setText(title);
		return this;
	}

	public ActionSheetDialogNewPhoto setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public ActionSheetDialogNewPhoto setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	/**
	 * 
	 * @param strItem
	 *            条目名称
	 * @param color
	 *            条目字体颜色，设置null则默认蓝色
	 * @param listener
	 * @return
	 */
	public ActionSheetDialogNewPhoto addSheetItem(String strItem, SheetItemColor color,Boolean flag,
			OnSheetItemClickListener listener) {
		if (sheetItemList == null) {
			sheetItemList = new ArrayList<SheetItem>();
		}
		sheetItemList.add(new SheetItem(strItem, color,flag, listener));
		return this;
	}

	/** 设置条目布局 */
	public void setSheetItems() {
		if (sheetItemList == null || sheetItemList.size() <= 0) {
			return;
		}

		int size = sheetItemList.size();

		// TODO 高度控制，非最佳解决办法
		// 添加条目过多的时候控制高度
		if (size >= 7) {
			LinearLayout.LayoutParams params = (LayoutParams) sLayout_content
					.getLayoutParams();
			params.height = display.getHeight() / 2;
			sLayout_content.setLayoutParams(params);
		}

		// 循环添加条目
		for (int i = 1; i <= size; i++) {
			final int index = i;
			SheetItem sheetItem = sheetItemList.get(i - 1);
			String strItem = sheetItem.name;
			SheetItemColor color = sheetItem.color;
			Boolean isChecked = sheetItem.flag;
			final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

			View view = LayoutInflater.from(context).inflate(R.layout.copy_ofview_dialog_item, null);
//			ImageView checkImage = (ImageView) view.findViewById(R.id.check_image);
//			imageList.add(checkImage);
			TextView tvCarType = (TextView) view.findViewById(R.id.tv_cartype);
			tvCarType.setText(strItem);
//			TextView textView = new TextView(context);
//			textView.setText(strItem);
//			textView.setTextSize(18);
//			textView.setGravity(Gravity.CENTER);

//			if(isChecked) {
//				checkImage.setVisibility(View.VISIBLE);
//			} else {
//				checkImage.setVisibility(View.GONE);
//			}
			// 背景图片
			if (size == 1) {
				if (showTitle) {
					view.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
				} else {
					view.setBackgroundResource(R.drawable.actionsheet_single_selector);
				}
			} else {
				if (showTitle) {
					if (i >= 1 && i < size) {
						view.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						view.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				} else {
					if (i == 1) {
						view.setBackgroundResource(R.drawable.actionsheet_top_selector);
					} else if (i < size) {
						view.setBackgroundResource(R.drawable.actionsheet_middle_selector);
					} else {
						view.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
					}
				}
			}

			// 字体颜色
			if (color == null) {
				tvCarType.setTextColor(Color.parseColor(SheetItemColor.Black
						.getName()));
			} else {
				tvCarType.setTextColor(Color.parseColor(color.getName()));
			}

			// 高度
			float scale = context.getResources().getDisplayMetrics().density;
			int height = (int) (45 * scale + 0.5f);
			view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, height));

			// 点击事件
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onClick(index);
					dialog.dismiss();
				}
			});

			lLayout_content.addView(view); //textView
		}
	}

	public void show() {
		//setSheetItems();
		dialog.show();
	}

	public interface OnSheetItemClickListener {
		void onClick(int which);
	}

	public class SheetItem {
		String name;
		OnSheetItemClickListener itemClickListener;
		SheetItemColor color;
		Boolean flag;

		public SheetItem(String name, SheetItemColor color,Boolean flag,
				OnSheetItemClickListener itemClickListener) {
			this.name = name;
			this.color = color;
			this.flag = flag;
			this.itemClickListener = itemClickListener;
		}
	}

	public enum SheetItemColor {
		Blue("#037BFF"), Red("#FD4A2E"),Black("#ff333333") ; 

		private String name;

		private SheetItemColor(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public void setCurrentSelect(String name) {
		int size=sheetItemList.size();
		for (int i = 0; i < size; i++) { 
			SheetItem sheetItem = sheetItemList.get(i);
			if(sheetItem.name.equals(name)){
				imageList.get(i).setVisibility(View.VISIBLE);
			} else {
				imageList.get(i).setVisibility(View.GONE);
			}
		}
	}
	
	
	
}
