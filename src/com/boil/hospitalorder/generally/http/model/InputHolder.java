package com.boil.hospitalorder.generally.http.model;

import org.apache.http.HttpRequest;

import android.content.Context;

import com.boil.hospitalorder.generally.http.AsyncResponseListener;

/**
 * Input holder
 * 
 * @author bright_zheng
 *
 */
public class InputHolder{
	private HttpRequest request;
	private AsyncResponseListener responseListener;
	private Context  pContext;
	private CharSequence pTitle;
	private CharSequence pMessage;
	private  boolean pCancelable;
	
	/**
	 * 包含点击HUD
	 * @param request
	 * @param responseListener
	 * @param pContext
	 * @param pTitle
	 * @param pMessage
	 * @param pCancelable
	 */
	public InputHolder(HttpRequest request,
			AsyncResponseListener responseListener, Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable) {
		super();
		this.request = request;
		this.responseListener = responseListener;
		this.pContext = pContext;
		this.pTitle = pTitle;
		this.pMessage = pMessage;
		this.pCancelable=pCancelable;
	}
	
	/**
	 * 包含显示HUD， 点击HUD
	 * @param request
	 * @param responseListener
	 * @param pContext
	 * @param pTitle
	 * @param pMessage
	 * @param pCancelable
	 * @param pShowHUD
	 */
	public InputHolder(HttpRequest request,
			AsyncResponseListener responseListener, Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable, boolean pShowHUD) {
		super();
		this.request = request;
		this.responseListener = responseListener;
		
		if (pShowHUD) {
			this.pContext = pContext;
			this.pTitle = pTitle;
			this.pMessage = pMessage;
			this.pCancelable=pCancelable;
		}
	}


	public InputHolder(HttpRequest request, AsyncResponseListener responseListener){
		this.request = request;
		this.responseListener = responseListener;
	}
	
	
	public HttpRequest getRequest() {
		return request;
	}

	public AsyncResponseListener getResponseListener() {
		return responseListener;
	}


	public Context getpContext() {
		return pContext;
	}


	public void setpContext(Context pContext) {
		this.pContext = pContext;
	}


	public CharSequence getpTitle() {
		return pTitle;
	}


	public void setpTitle(CharSequence pTitle) {
		this.pTitle = pTitle;
	}


	public CharSequence getpMessage() {
		return pMessage;
	}


	public void setpMessage(CharSequence pMessage) {
		this.pMessage = pMessage;
	}


	public boolean ispCancelable() {
		return pCancelable;
	}


	public void setpCancelable(boolean pCancelable) {
		this.pCancelable = pCancelable;
	}
	
	
}
