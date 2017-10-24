package com.boil.hospitalorder.utils;

import com.boil.hospitalorder.hospitaldoctor.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CTListView extends ListView implements OnScrollListener {

	/** 底部显示正在加载的页面 */
	private View footerView = null;
	/** 存储上下文 */
	private Context context;
	/** 上拉刷新的ListView的回调监听 */
	private CTPullUpListViewCallBack cTPullUplistViewCallBack;
	/** 记录第一行Item的数值 */
	private int firstVisibleItem;
	
	private int pageSize=10;
	
	private boolean isLoading;// 判断是否正在加载
	
	private boolean loadEnable = true;// 开启或者关闭加载更多功能
	
	private boolean isLoadFull;

	private TextView noData;
	private TextView loadFull;
	private TextView more;
	private ProgressBar loading;
	private RelativeLayout rel_more;
	private int scrollState;
	
	public CTListView(Context context) {
		super(context);
		this.context = context;
		initBottomView();
	}

	public CTListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initBottomView();
	}
	
	public CTListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initBottomView();
	}

	
	
	public boolean isLoadEnable() {
		return loadEnable;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// 这里的开启或者关闭加载更多，并不支持动态调整
	public void setLoadEnable(boolean loadEnable) {
		this.loadEnable = loadEnable;
		this.removeFooterView(footerView);
	}
	/**
	 * 初始化话底部页面
	 */
	public void initBottomView() {
		
		if (footerView == null) {
			footerView = LayoutInflater.from(this.context).inflate(
					R.layout.listview_footer, null);
			
			//已加载全部
			loadFull = (TextView) footerView.findViewById(R.id.loadFull);
			noData = (TextView) footerView.findViewById(R.id.noData);
			more = (TextView) footerView.findViewById(R.id.more);
			loading = (ProgressBar) footerView.findViewById(R.id.loading);
			rel_more = (RelativeLayout) footerView.findViewById(R.id.rel_more);
		}
		addFooterView(footerView);
		
		// 为ListView设置滑动监听
		this.setOnScrollListener(this);
		this.setFooterDividersEnabled(true);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		//当滑动到底部时
		ifNeedLoad(view, scrollState);
	}
	
	// 根据listview滑动的状态判断是否需要加载更多
	private void ifNeedLoad(AbsListView view, int scrollState) {
		if (!loadEnable) {
			return;
		}
		try {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& !isLoading
					&& view.getLastVisiblePosition() == view
							.getPositionForView(footerView) && !isLoadFull) {
				onLoad();
				isLoading = true;
			}
		} catch (Exception e) {
		}
	}
		public void onLoad() {
			if (cTPullUplistViewCallBack != null) {
				cTPullUplistViewCallBack.scrollBottomLoadState();
			}
		}
	

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		
		if (footerView != null) {
			//判断可视Item是否能在当前页面完全显示
			if (visibleItemCount >= totalItemCount) {
				// removeFooterView(footerView);
				if(this.getFooterViewsCount() > 0) {
//					footerView.setVisibility(View.GONE);//隐藏底部布局
					this.removeFooterView(footerView);
				}
			} else {
//				 addFooterView(footerView);
				if(this.getFooterViewsCount() > 0) {
					footerView.setVisibility(View.VISIBLE);//显示底部布局
				} else {
					addFooterView(footerView);
				}
			}
		}
	}

	
	public void setMyPullUpListViewCallBack(
			CTPullUpListViewCallBack cTPullUpListViewCallBack) {
		this.loadEnable = true;
		this.cTPullUplistViewCallBack = cTPullUpListViewCallBack;
	}

	// 用于加载更多结束后的回调
	public void onLoadComplete() {
		isLoading = false;
	}
		
	/**
	 * 上拉刷新的ListView的回调监听
	 * 
	 * @author xiejinxiong
	 * 
	 */
	public interface CTPullUpListViewCallBack {
		void scrollBottomLoadState();
	}
	/**
	 * 这个方法是根据结果的大小来决定footer显示的。
	 * <p>
	 * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
	 * </p>
	 * @param resultSize
	 */
	
	public void setResultSize(int resultSize) {
		if (resultSize == 0) {
			isLoadFull = true;
			loadFull.setVisibility(View.GONE);
			rel_more.setVisibility(View.GONE);
//			loading.setVisibility(View.GONE);
//			more.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		} else if (resultSize > 0 && resultSize < pageSize) {
			isLoadFull = true;
			loadFull.setVisibility(View.VISIBLE);
//			loading.setVisibility(View.GONE);
//			more.setVisibility(View.GONE);
			rel_more.setVisibility(View.GONE);
			noData.setVisibility(View.GONE);
		} else if (resultSize == pageSize) {
			isLoadFull = false;
			loadFull.setVisibility(View.GONE);
//			loading.setVisibility(View.VISIBLE);
//			more.setVisibility(View.VISIBLE);
			rel_more.setVisibility(View.VISIBLE);
			noData.setVisibility(View.GONE);
		} else if(resultSize == -1) {
			isLoadFull = false;
			loadFull.setVisibility(View.GONE);
//			loading.setVisibility(View.GONE);
//			more.setVisibility(View.GONE);
			rel_more.setVisibility(View.GONE);
			noData.setText("加载失败，稍后再试");
			noData.setVisibility(View.VISIBLE);
		}
	}
	
}