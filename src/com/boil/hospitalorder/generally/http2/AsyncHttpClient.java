package com.boil.hospitalorder.generally.http2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import android.os.AsyncTask;
import android.util.Log;

import com.boil.hospitalorder.generally.http2.model.InputHolder;

/**
 * 
 * 异步请求客户端。
 * 
 * @author ChenYong
 * @date 2016-08-31
 * 
 */
public class AsyncHttpClient {
	/** 标记 */
	private static String TAG = "AsyncHttpClient";
	/** 默认请求客户端 */
	private static DefaultHttpClient httpClient;
	/** 连接超时 */
	public static int CONNECTION_TIMEOUT = 30 * 1000;
	/** 通信超时 */
	public static int SOCKET_TIMEOUT = 2 * 60 * 1000;
	/** 线程安全的异步任务队列 */
	private static Map<Activity, List<AsyncHttpSender>> tasksMap = new ConcurrentHashMap<Activity, List<AsyncHttpSender>>();

	/**
	 * 
	 * 发送请求。
	 * 
	 * @param activity 当前 Activity
	 * @param request 请求实例
	 * @param loadMsg 加载消息
	 * @param showLoad 是否显示加载消息：
	 *            <ol>
	 *            <li>true-显示；</li>
	 *            <li>false-不显示。</li>
	 *            </ol>
	 * @param listener 异步响应监听器
	 * 
	 */
	public static void sendRequest(final Activity activity, //
			final HttpRequest request, //
			String loadMsg, //
			boolean showLoad, //
			AsyncResponseListener listener) {
		InputHolder input = new InputHolder(activity, request, loadMsg, showLoad, listener);
		AsyncHttpSender sender = new AsyncHttpSender(input);
		
		input.setSender(sender);

		if (tasksMap.get(activity) == null) {
			tasksMap.put(activity, new ArrayList<AsyncHttpSender>());
		}

		tasksMap.get(activity).add(sender);

		sender.execute(input);
	}

	/**
	 * 
	 * 获取请求客户端。
	 * 
	 * @return 请求客户端
	 * 
	 */
	public static synchronized HttpClient getClient() {
		if (httpClient == null) {
			BasicHttpParams params = new BasicHttpParams();
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);

			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			httpClient = new DefaultHttpClient(cm, params);

			HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		}
		
		return httpClient;
	}
	
	/**
	 * 
	 * 取消 activity 的所有请求。
	 * 
	 * @param activity 当前 Activity
	 * 
	 */
	public static void cancelRequest(final Activity activity) {
		if ((activity == null) || (tasksMap == null) || tasksMap.isEmpty()) {
			return;
		}

		for (Activity key : tasksMap.keySet()) {
			if (activity == key) {
				List<AsyncHttpSender> tasks = tasksMap.get(key);

				for (AsyncHttpSender sender : tasks) {
					if ((sender.getStatus() != null) && (sender.getStatus() != AsyncTask.Status.FINISHED)) {
						sender.cancel(true);
					}
				}

				tasksMap.remove(key);
				
				Log.i(TAG, String.format("%s--->取消了所有请求", key.getClass().getSimpleName()));
			}
		}
	}

	/**
	 * 
	 * 获取当前 Activity 的异步任务队列 。
	 * 
	 * @return 当前 Activity 的异步任务队列
	 * 
	 */
	public static List<AsyncHttpSender> getTasks(final Activity activity) {
		return tasksMap.get(activity);
	}
}