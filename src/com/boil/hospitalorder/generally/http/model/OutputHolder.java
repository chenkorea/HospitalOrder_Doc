package com.boil.hospitalorder.generally.http.model;

import org.apache.http.HttpEntity;

import com.boil.hospitalorder.generally.http.AsyncResponseListener;

public class OutputHolder{
	private HttpEntity response;
	private Throwable exception;
	private AsyncResponseListener responseListener;
	
	public OutputHolder(HttpEntity response, AsyncResponseListener responseListener){
		this.response = response;
		this.responseListener = responseListener;
	}
	
	public OutputHolder(Throwable exception, AsyncResponseListener responseListener){
		this.exception = exception;
		this.responseListener = responseListener;
	}

	public HttpEntity getResponse() {
		return response;
	}

	public Throwable getException() {
		return exception;
	}
	
	public AsyncResponseListener getResponseListener() {
		return responseListener;
	}
	
}