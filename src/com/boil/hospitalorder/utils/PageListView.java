package com.boil.hospitalorder.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.boil.hospitalorder.hospitaldoctor.R;

/**
 * 
 * 向上拉/向下拉可以进行分页的 ListView。<br>
 * <b style="color: red;">
 * 注：下拉分页还未实现。
 * </b>
 * 
 * @author ChenYong
 * @date 2016-03-21
 *
 */
public class PageListView extends ListView implements OnScrollListener {
	/** 布局加载器 */
	private LayoutInflater inflater;
	/** 底部加载等待框 */
	private LinearLayout footerLoadingView;
	/** 加载监听器 */
	private OnLoadListener onLoadListener;
	/** 是否开启加载：true-是；false-否 */
	private boolean loadEnable;
	/** 加载状态：true-正在加载；false-加载完毕 */
	private boolean loadStatus;
	/** 是否加载完毕：true-是；false-否 */
	private boolean loadFull;
	/** 底部加载时的总页数 */
	private int footerPages;
	
	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * 
	 */
	public PageListView(Context context) {
		super(context);
		
		this.inflater = LayoutInflater.from(context);
		initView();
	}

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * @param attrs 属性集合
	 * 
	 */
	public PageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.inflater = LayoutInflater.from(context);
		initView();
	}

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * @param attrs 属性集合
	 * @param defStyle 默认样式
	 * 
	 */
	public PageListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		this.inflater = LayoutInflater.from(context);
		initView();
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	}
	
	/**
	 * 
	 * 监听 ListView 滑动。
	 * 
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		try {
			if (loadEnable //
					&& !loadStatus //
					&& !loadFull //
					&& (footerPages > 1) //
					&& (OnScrollListener.SCROLL_STATE_IDLE == scrollState) //
					&& (view.getLastVisiblePosition() == view.getPositionForView(footerLoadingView))) {
				loadStatus = true;

				onLoad();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 获取底部加载时的总页数。
	 * 
	 * @return 底部加载时的总页数
	 * 
	 */
	public int getFooterPages() {
		return footerPages;
	}

	/**
	 * 
	 * 设置底部加载时的总页数。
	 * 
	 * @param footerPages 底部加载时的总页数
	 * 
	 */
	public void setFooterPages(int footerPages) {
		this.footerPages = footerPages;
		
		if (this.footerPages <= 1) {
			this.loadStatus = false;
			this.loadFull = true;
			
			LoadingUtils.showLoadMsg(footerLoadingView, "已加载完毕");
		}
	}

	/**
	 * 
	 * 初始化组件。
	 * 
	 */
	@SuppressLint("InflateParams")
	private void initView() {
		footerLoadingView = (LinearLayout) inflater.inflate(R.layout.loading_view_wrap_952658, null);
		
		loadEnable = false;
		loadStatus = false;
		loadFull = false;
		
		setOnScrollListener(this);
		addFooterView(footerLoadingView);
		LoadingUtils.showLoadView(footerLoadingView, "正在加载……");
	}
	
	/**
	 * 
	 * 加载监听器接口。
	 * 
	 */
	public interface OnLoadListener {
		public void onLoad();
	}
	
	/**
	 * 
	 * 设置加载监听器。
	 * 
	 * @param onLoadListener 加载监听器
	 * 
	 */
	public void setOnLoadListener(OnLoadListener onLoadListener) {
		this.loadEnable = true;
		this.onLoadListener = onLoadListener;
	}
	
	/**
	 * 
	 * 内部调用加载。
	 * 
	 */
	private void onLoad() {
		if (onLoadListener != null) {
			onLoadListener.onLoad();
		}
	}
	
	/**
	 * 
	 * 加载完毕调用。
	 * 
	 * @param pageNum 页数
	 * 
	 */
	public void loadComplete(int pageNum) {
		loadStatus = false;
		
		if ((pageNum <= 0) || (pageNum >= footerPages)) {
			loadFull = true;
			
			LoadingUtils.showLoadMsg(footerLoadingView, "已加载完毕");
		} else {
			loadFull = false;
		}
	}
}