package com.boil.hospitalorder.generally.http;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.boil.hospitalorder.generally.http.model.InputHolder;

public class AsyncHttpClient {
	private static DefaultHttpClient httpClient;
	
	public static int CONNECTION_TIMEOUT = 30*1000;
	public static int SOCKET_TIMEOUT  = 2*60*1000;
	
	private static String TAG="AbstractAsyncResponseListener";
	
	private static ConcurrentHashMap<Activity,AsyncHttpSender> tasks = new ConcurrentHashMap<Activity,AsyncHttpSender>();
		
	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			AsyncResponseListener callback) {
		
		sendRequest(currentActitity, request, callback, CONNECTION_TIMEOUT, SOCKET_TIMEOUT);
	}

	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable,AsyncResponseListener callback) {
		
		sendRequest(currentActitity, request, callback, CONNECTION_TIMEOUT, SOCKET_TIMEOUT,pContext,pTitle,pMessage,pCancelable);
	}
	
	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable,AsyncResponseListener callback , boolean pShowHUD) {
		
		sendRequest(currentActitity, request, callback, CONNECTION_TIMEOUT, SOCKET_TIMEOUT,pContext,pTitle,pMessage,pCancelable, pShowHUD);
	}
	
	
	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			AsyncResponseListener callback,
			int timeoutConnection,
			int timeoutSocket) {
		
		InputHolder input = new InputHolder(request, callback);
		AsyncHttpSender sender = new AsyncHttpSender(input);
		sender.execute(input);
		tasks.put(currentActitity, sender);
	}
	
	/**
	 * 包含点击HUD失效
	 * @param currentActitity
	 * @param request
	 * @param callback
	 * @param timeoutConnection
	 * @param timeoutSocket
	 * @param pContext
	 * @param pTitle
	 * @param pMessage
	 * @param pCancelable
	 */
	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			AsyncResponseListener callback,
			int timeoutConnection,
			int timeoutSocket, 
			Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable) {
		
		InputHolder input = new InputHolder(request, callback,pContext,pTitle,pMessage,pCancelable);
		AsyncHttpSender sender = new AsyncHttpSender(input);
		sender.execute(input);
		tasks.put(currentActitity, sender);
	}
	
	/**
	 * 包含是否显示HUD， 点击HUD失效
	 * @param currentActitity
	 * @param request
	 * @param callback
	 * @param timeoutConnection
	 * @param timeoutSocket
	 * @param pContext
	 * @param pTitle
	 * @param pMessage
	 * @param pCancelable
	 * @param pShowHUD
	 */
	public static void sendRequest(
			final Activity currentActitity,
			final HttpRequest request,
			AsyncResponseListener callback,
			int timeoutConnection,
			int timeoutSocket, 
			Context pContext,
			CharSequence pTitle, CharSequence pMessage,boolean pCancelable, boolean pShowHUD) {
		
		InputHolder input = new InputHolder(request, callback,pContext,pTitle,pMessage,pCancelable, pShowHUD);
		AsyncHttpSender sender = new AsyncHttpSender(input);
		sender.execute(input);
		tasks.put(currentActitity, sender);
	}
	
	public static void cancelRequest(final Activity currentActitity){
		if(tasks==null || tasks.size()==0) return;
		for (Activity key : tasks.keySet()) {
		    if(currentActitity == key){
		    	AsyncTask<?,?,?> task = tasks.get(key);
		    	if(task.getStatus()!=null && task.getStatus()!=AsyncTask.Status.FINISHED){
			    	Log.i(TAG, "AsyncTask of " + task + " cancelled.");
		    		task.cancel(true);
		    	}
		    	tasks.remove(key);
		    }
		}
	}
 
	public static synchronized HttpClient getClient() {
		if (httpClient == null){			
			//use following code to solve Adapter is detached error
			//refer: http://stackoverflow.com/questions/5317882/android-handling-back-button-during-asynctask
			BasicHttpParams params = new BasicHttpParams();
			
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
			schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
			
			// Set the timeout in milliseconds until a connection is established.
			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
			
			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			httpClient = new DefaultHttpClient(cm, params);	
			
			HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		}
		return httpClient;
	}
 
}