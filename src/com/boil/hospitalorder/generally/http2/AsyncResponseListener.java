package com.boil.hospitalorder.generally.http2;

import org.apache.http.HttpEntity;

/**
 * 
 * 异步响应监听器接口。
 * 
 * @author ChenYong
 * @date 2016-08-31
 * 
 */
public interface AsyncResponseListener {
	/**
	 * 
	 * 请求发送前执行。
	 * 
	 */
	public void onBeforeSend();
	
	/**
	 * 
	 * 请求完成时执行。
	 * 
	 */
	public void onCompleteReceived();
	
	/**
	 * 
	 * 请求成功时执行。
	 * 
	 * @param envelope Soap 序列化的封装体
	 * 
	 */
	public void onSuccessReceived(HttpEntity response);
	
	/**
	 * 
	 * 请求失败时执行。
	 * 
	 * @param e 异常
	 * 
	 */
	public void onFailureReceived(Exception e);
	
	/**
	 * 
	 * 请求完成时重新加载。
	 * 
	 */
	public void onCompleteReload();
}