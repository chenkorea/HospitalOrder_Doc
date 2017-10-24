package com.boil.hospitalorder.generally.http2.model;

import org.apache.http.HttpEntity;

import com.boil.hospitalorder.generally.http2.AsyncResponseListener;

/**
 * 
 * Http Output Holder。
 * 
 * @author ChenYong
 * @date 2016-08-31
 * 
 */
public class OutputHolder {
	/** 响应 */
	private HttpEntity response;
	/** 异常 */
	private Exception exception;
	/** 异步响应监听器 */
	private AsyncResponseListener listener;

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param response 响应
	 * @param listener 异步响应监听器
	 * 
	 */
	public OutputHolder(HttpEntity response, AsyncResponseListener listener) {
		this.response = response;
		this.listener = listener;
	}

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param exception 异常
	 * @param listener 异步响应监听器
	 * 
	 */
	public OutputHolder(Exception e, AsyncResponseListener listener) {
		this.exception = e;
		this.listener = listener;
	}

	/**
	 * 
	 * 获取响应。
	 * 
	 * @return 响应
	 * 
	 */
	public HttpEntity getResponse() {
		return response;
	}

	/**
	 * 
	 * 获取异常。
	 * 
	 * @return 异常
	 * 
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * 
	 * 获取异步响应监听器。
	 * 
	 * @return 异步响应监听器
	 * 
	 */
	public AsyncResponseListener getListener() {
		return listener;
	}
}