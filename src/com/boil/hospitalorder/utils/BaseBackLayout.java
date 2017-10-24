package com.boil.hospitalorder.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BaseBackLayout extends FrameLayout {
	private Activity activity;
	private AutoScrollThread autoScrollThread;
	private int lastX;// 移动时记录的上一次位置
	private boolean swipeEnable = true;

	private LinearLayout bgLayout;

	public BaseBackLayout(Context context) {
		super(context);
	}

	public BaseBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseBackLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 触碰事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!swipeEnable)
			return false;
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				closeThread();
				lastX = (int) event.getRawX();
				break;
			case MotionEvent.ACTION_MOVE:
				int x = (int) event.getRawX();
				int dx = x - lastX;
				dragTo(this.getLeft() + dx);
				lastX = x;
				break;
			case MotionEvent.ACTION_UP:
				int orientation = LEFT;
				if (this.getLeft() > this.getWidth() / 3)
					orientation = RIGHT;
				autoScrollThread = new AutoScrollThread(orientation);
				autoScrollThread.start();
				break;
			case MotionEvent.ACTION_CANCEL:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 拖到指定位置
	 * 
	 * @param x
	 */
	private void dragTo(int left) {
		if (left <= 0)// 防止超出左端
			left = 0;
		if (left >= this.getWidth())// 防止超出右端
			left = this.getWidth();
		int oldLeft = this.getLeft();
		int dx = left - oldLeft;
		offsetLeftAndRight(dx);

		// 设置背景透明度的变化
		double rate = 80.0 / this.getWidth();
		int value = (int) (80 - (left * rate));
		bgLayout.getBackground().setAlpha(value + 5);
	}

	/**
	 * 把BaseBackLayout附加到activity
	 */
	public void attachToActivity(Activity activity) {
		this.activity = activity;
		activity.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		activity.getWindow().getDecorView().setBackgroundDrawable(null);
		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decor.removeView(decorChild);

		addView(decorChild);

		FrameLayout frameLayout = new FrameLayout(this.getContext());
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		frameLayout.setLayoutParams(lp);

		LinearLayout linearLayout = new LinearLayout(this.getContext());
		lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		linearLayout.setLayoutParams(lp);
		linearLayout.setBackgroundColor(0xFF000000);
		linearLayout.getBackground().setAlpha(20);
		bgLayout = linearLayout;

		frameLayout.addView(linearLayout);
		frameLayout.addView(this);

		decor.addView(frameLayout);

	}

	/**
	 * 结束activity
	 */
	private void finishActivity() {
		try {
			bgLayout.getBackground().setAlpha(0);
			this.activity.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 自动滚动动画 **/
	/**
	 * 关闭线程(动画)
	 */
	private void closeThread() {
		try {
			if (autoScrollThread != null)
				autoScrollThread.flag = false;
			autoScrollThread = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自动滚动
	 */
	final Handler upHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int left = (Integer) msg.obj;
			dragTo(left);
			if (left >= BaseBackLayout.this.getWidth())
				finishActivity();
		}
	};
	/**
	 * 自动滚动线程
	 */
	public static int LEFT = 0;
	public static int RIGHT = 1;

	private class AutoScrollThread extends Thread {
		private boolean flag = true;
		private int orientation;// 方向，

		AutoScrollThread(int orientation) {
			this.orientation = orientation;
		}

		public void run() {
			while (flag) { 
				// System.out.println("BaseBackLayout 滚动线程正在执行");
				try {
					int left = BaseBackLayout.this.getLeft();
					int width = BaseBackLayout.this.getWidth();
					if ((orientation == LEFT && left <= 0)
							|| (orientation == RIGHT && left >= width) || !flag)// 判断是否超出边界
						break;
					Thread.sleep(5);
					if ((orientation == LEFT && left <= 0)
							|| (orientation == RIGHT && left >= width) || !flag)// 判断是否超出边界
						break;

					if (this.orientation == LEFT) {
						left += -25;
					} else {
						left += 25;
					}
					if (left < 0)
						left = 0;
					if (left > width)
						left = width;
					Message msg = new Message();
					msg.obj = left;
					upHandler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setSwipeEnable(boolean swipeEnable) {
		this.swipeEnable = swipeEnable;
	}
}
