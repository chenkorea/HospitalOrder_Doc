package com.boil.hospitalorder.generally.http2.model;

import org.apache.http.HttpRequest;

import android.app.Activity;

import com.boil.hospitalorder.generally.http2.AsyncHttpSender;
import com.boil.hospitalorder.generally.http2.AsyncResponseListener;

/**
 * 
 * Http Input Holder。
 * 
 * @author ChenYong
 * @date 2016-08-31
 * 
 */
public class InputHolder {
	/** 当前 Activity */
	private Activity activity;
	/** 请求 */
	private HttpRequest request;
	/** 加载消息 */
	private String loadMsg;
	/** 是否显示等待框：true-显示；false-不显示 */
	private boolean showLoad;
	/** Web Service 异步请求发送器 */
	private AsyncHttpSender sender;
	/** 异步响应监听器 */
	private AsyncResponseListener listener;
	
	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param activity 当前 Activity
	 * @param request 请求
	 * @param loadMsg 加载消息
	 * @param showLoad 是否显示加载提示：
	 *            <ol>
	 *            <li>true-显示；</li>
	 *            <li>false-不显示。</li>
	 *            </ol>
	 * @param listener 异步响应监听器
	 * 
	 */
	public InputHolder(Activity activity, //
			HttpRequest request, //
			String loadMsg, //
			boolean showLoad, //
			AsyncResponseListener listener) {
		super();
		
		this.activity = activity;
		this.request = request;
		this.loadMsg = loadMsg;
		this.showLoad = showLoad;
		this.listener = listener;
	}

	/**
	 * 
	 * 获取当前 Activity。
	 * 
	 * @return 当前 Activity
	 * 
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * 
	 * 获取请求。
	 * 
	 * @return 请求
	 * 
	 */
	public HttpRequest getRequest() {
		return request;
	}

	/**
	 * 
	 * 获取加载消息。
	 * 
	 * @return 加载消息
	 * 
	 */
	public String getLoadMsg() {
		return loadMsg;
	}

	/**
	 * 
	 * 判断是否显示等待框。
	 * 
	 * 
	 * @return <ol>
	 *         <li>true-显示；</li>
	 *         <li>false-不显示。</li>
	 *         </ol>
	 * 
	 */
	public boolean isShowLoad() {
		return showLoad;
	}
	
	/**
	 * 
	 * 获取异步请求发送器。
	 * 
	 * @return 异步请求发送器
	 * 
	 */
	public AsyncHttpSender getSender() {
		return sender;
	}
	
	/**
	 * 
	 * 设置异步请求发送器。
	 * 
	 * @param sender 异步请求发送器
	 * 
	 */
	public void setSender(AsyncHttpSender sender) {
		this.sender = sender;
	}
	
	/**
	 * 
	 * 异步响应监听器。
	 * 
	 * @return 异步响应监听器
	 * 
	 */
	public AsyncResponseListener getListener() {
		return listener;
	}
}