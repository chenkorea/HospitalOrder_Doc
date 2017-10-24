package com.boil.hospitalorder.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * Android 仿 IOS PickerView。<br>
 * 来自网络：http://blog.csdn.net/zhongkejingwang/article/details/38513301<br>
 * 感谢：陈靖_(http://my.csdn.net/zhongkejingwang)<br>
 * 
 * @author ChenYong--->来自网络
 * @date 2016-04-28
 * 
 */
public class PickerView extends View {
	/** text 之间间距和 minTextSize 之比 */
	public static final float MARGIN_ALPHA = 2.8f;
	/** 自动回滚到中间的速度 */
	public static final float SPEED = 2;
	/** 滑动的距离 */
	private float moveLen = 0;
	/** 数据 */
	private List<String> data;
	/** 高度 */
	private int viewHeight;
	/** 宽度 */
	private int viewWidth;
	/** 画笔 */
	private Paint mPaint;
	/** 被选中的位置，这个位置是 data 的中心位置，保持不变 */
	private int currSelected;
	/** 最小字体大小 */
	private float minTextSize = 40;
	/** 最小字体大小的比例 */
	private float minTextSizeScale = 2.0F;
	/** 最小字体的透明度 */
	private float minTextAlpha = 120;
	/** 最大字体大小 */
	private float maxTextSize = 80;
	/** 最大字体大小的比例 */
	private float maxTextSizeScale = 10.0F;
	/** 最大字体的透明度 */
	private float maxTextAlpha = 255;
	/** 字体颜色 */
	private int textColor = 0x333333;
	/** 最后的 Y 坐标 */
	private float lastDownY;
	/** 是否已初始化：true-是；false-否 */
	private boolean isInit = false;
	/** 选中监听器 */
	private onSelectListener selectListener;
	/** 定时器 */
	private Timer timer;
	/** 我的定时任务 */
	private MyTimerTask myTask;
	
	/** 处理机制 */
	@SuppressLint("HandlerLeak")
	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (Math.abs(moveLen) < SPEED) {
				moveLen = 0;
				
				if (myTask != null) {
					myTask.cancel();
					myTask = null;
					
					performSelect();
				}
			} else {
				// 这里 moveLen / Math.abs(moveLen) 是为了保有 moveLen 的正负号，以实现上滚或下滚
				moveLen = (moveLen - ((moveLen / Math.abs(moveLen)) * SPEED));
			}
			
			invalidate();
		}
	};

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * 
	 */
	public PickerView(Context context) {
		super(context);
		
		init();
	}

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * @param attrs 属性集
	 * 
	 */
	public PickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init();
	}

	/**
	 * 
	 * 设置最小/最大字体大小的比例。
	 * 
	 * @param minTextSizeScale 最小字体大小的比例
	 * @param maxTextSizeScale 最大字体大小的比例
	 * 
	 */
	public void setTextSizeScale(float minTextSizeScale, float maxTextSizeScale) {
		this.minTextSizeScale = minTextSizeScale;
		this.maxTextSizeScale = maxTextSizeScale;
	}

	/**
	 * 
	 * 设置选中监听器。
	 * 
	 * @param selectListener 选中监听器
	 * 
	 */
	public void setOnSelectListener(onSelectListener selectListener) {
		this.selectListener = selectListener;
	}

	/**
	 * 
	 * 调用选择监听器。
	 * 
	 */
	private void performSelect() {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		if (selectListener != null) {
			selectListener.onSelect(data.get(currSelected));
		}
	}

	/**
	 * 
	 * 设置数据。
	 * 
	 * @param data 数据
	 * 
	 */
	public void setData(List<String> data) {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		this.data = data;
		currSelected = data.size() / 2;
		
		invalidate();
	}

	/**
	 * 
	 * 将头部变为尾部。
	 * 
	 */
	private void moveHeadToTail() {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		String head = data.get(0);
		
		data.remove(0);
		data.add(head);
	}

	/**
	 * 
	 * 将尾部变为头部。
	 * 
	 */
	private void moveTailToHead() {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		String tail = data.get(data.size() - 1);
		
		data.remove(data.size() - 1);
		data.add(0, tail);
	}

	/**
	 * 
	 * 
	 * 重写测量。
	 * 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		viewHeight = getMeasuredHeight();
		viewWidth = getMeasuredWidth();
		// 按照 View 的高度计算字体大小
		maxTextSize = viewHeight / maxTextSizeScale;
		minTextSize = maxTextSize / minTextSizeScale;
		isInit = true;
		
		invalidate();
	}

	/**
	 * 
	 * 初始化。
	 * 
	 */
	private void init() {
		timer = new Timer();
		data = new ArrayList<String>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(textColor);
	}

	/**
	 * 
	 * 重写作画。
	 * 
	 * @param canvas 画布
	 * 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// 根据 index 绘制view
		if (isInit) {
			drawData(canvas);
		}
	}

	/**
	 * 
	 * 在画布上画出数据。
	 * 
	 * @param canvas 画布
	 * 
	 */
	private void drawData(Canvas canvas) {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		// 先绘制选中的 text 再往上往下绘制其余的 text
		float scale = parabola(viewHeight / 4.0f, moveLen);
		float size = (maxTextSize - minTextSize) * scale + minTextSize;
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((maxTextAlpha - minTextAlpha) * scale + minTextAlpha));
		// text 居中绘制，注意 baseline 的计算才能达到居中，y 值是 text 中心坐标
		float x = (float) (viewWidth / 2.0);
		float y = (float) (viewHeight / 2.0 + moveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

		canvas.drawText(data.get(currSelected), x, baseline, mPaint);
		
		// 绘制上方的 data
		for (int i = 1; (currSelected - i) >= 0; i++) {
			drawOtherText(canvas, i, -1);
		}
		
		// 绘制下方的 data
		for (int i = 1; (currSelected + i) < data.size(); i++) {
			drawOtherText(canvas, i, 1);
		}
		
		performSelect();
	}

	/**
	 * 
	 * 画。
	 * 
	 * @param canvas 画布
	 * @param position 距离 currSelected 的差值
	 * @param type 1：向下绘制；-1：向上绘制
	 * 
	 */
	private void drawOtherText(Canvas canvas, int position, int type) {
		if ((data == null) || data.isEmpty())	 {
			return;
		}
		
		float d = (float) ((MARGIN_ALPHA * minTextSize * position) + (type * moveLen));
		float scale = parabola(viewHeight / 4.0F, d);
		float size = (maxTextSize - minTextSize) * scale + minTextSize;
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((maxTextAlpha - minTextAlpha) * scale + minTextAlpha));
		float y = (float) (viewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - ((fmi.bottom / 2.0F) + (fmi.top / 2.0F)));
		
		canvas.drawText(data.get(currSelected + type * position), (float) (viewWidth / 2.0F), baseline, mPaint);
	}

	/**
	 * 
	 * 抛物线。
	 * 
	 * @param zero 零点坐标
	 * @param x 偏移量
	 * @return scale 比例
	 * 
	 */
	private float parabola(float zero, float x) {
		float f = (float) (1 - Math.pow(x / zero, 2));
		
		return (f < 0) ? 0 : f;
	}

	/**
	 * 
	 * 触摸事件。
	 * 
	 * @param event 手势事件
	 * 
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			
			break;
		}
		
		return true;
	}

	/**
	 * 
	 * 手指往下按。
	 * 
	 * @param event 手势事件
	 * 
	 */
	private void doDown(MotionEvent event) {
		if (myTask != null) {
			myTask.cancel();
			myTask = null;
		}
		
		lastDownY = event.getY();
	}

	/**
	 * 
	 * 下滑/上滑。
	 * 
	 * @param event 手势事件
	 * 
	 */
	private void doMove(MotionEvent event) {
		moveLen += (event.getY() - lastDownY);

		if (moveLen > ((MARGIN_ALPHA * minTextSize) / 2)) {
			// 往下滑超过离开距离
			moveTailToHead();
			
			moveLen = (moveLen - (MARGIN_ALPHA * minTextSize));
		} else if (moveLen < ((-MARGIN_ALPHA * minTextSize) / 2)) {
			// 往上滑超过离开距离
			moveHeadToTail();
			
			moveLen = (moveLen + (MARGIN_ALPHA * minTextSize));
		}

		lastDownY = event.getY();
		
		invalidate();
	}

	/**
	 * 
	 * 松开手指。
	 * 
	 * @param event 手势事件
	 * 
	 */
	private void doUp(MotionEvent event) {
		// 松开手指后 currSelected 的位置由当前位置 move 到中间选中位置
		if (Math.abs(moveLen) < 0.0001) {
			moveLen = 0;
			
			return;
		}
		
		if (myTask != null) {
			myTask.cancel();
			myTask = null;
		}
		
		myTask = new MyTimerTask(updateHandler);
		timer.schedule(myTask, 0, 10);
	}

	/**
	 * 
	 * 我的定时器。
	 * 
	 */
	class MyTimerTask extends TimerTask {
		/** 处理机制 */
		private Handler handler;

		/**
		 * 
		 * 有参构造器。
		 * 
		 * @param handler 处理机制‘’
		 * 
		 */
		public MyTimerTask(Handler handler) {
			this.handler = handler;
		}

		/**
		 * 
		 * 执行。
		 * 
		 */
		@Override
		public void run() {
			handler.sendMessage(handler.obtainMessage());
		}
	}

	/**
	 * 
	 * 选中接口，提供给外部使用，被选中时执行。
	 *
	 */
	public interface onSelectListener {
		/**
		 * 
		 * 被选中时执行。
		 * 
		 * @param text 文本
		 * 
		 */
		void onSelect(String text);
	}
}