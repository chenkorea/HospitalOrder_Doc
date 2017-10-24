package com.boil.hospitalorder.volley.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.cache.VolleyImageCache;
import com.boil.hospitalorder.volley.util.MyDiskLruCache;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * Http 请求 Volley 客户端。
 * 
 * @author ChenYong
 * @date 2016-09-02
 *
 */
public class VolleyClient {
	/** 标记 */
	private static final String TAG = "VolleyClient";
	/** POST 请求头类型 */
	private static final String POST_CONTENT_TYPE = "application/x-www-form-urlencoded";
	/** 初始超时毫秒数：15秒 */
	private static final int INITIAL_TIMEOUT_MS = 15 * 1000;
	/** 最大重试次数 */
	private static final int MAX_NUM_RETRIES = 0;
	/** 补偿因子 */
	private static final float BACKOFF_MULTIPLIER = 0.0F;
	/** 上下文 */
	private Context context;
	/** 等待视图 */
	private View loadingView;
	/** 当前 Activity */
	private Activity activity;
	/** 请求队列的计数，即每发送 1 次请求加 1 */
	private int iRequestQueue;
	/** 等待信息视图 */
	private TextView tvLoading;
	/** 等待图片视图 */
	private ImageView ivLoading;
	/** 图片加载器 */
	private ImageLoader imageLoader;
	/** 重试策略 */
	private RetryPolicy retryPolicy;
	/** 请求队列 */
	private RequestQueue requestQueue;
	/** 等待动画 */
	private AnimationDrawable loadingAnimDraw;
	/** 图片磁盘缓冲区 */
	private MyDiskLruCache bitmapDiskLruCache;
	/** 是否已提示“网络连接不可用”的标记：true-是；false-否 */
	private boolean isConnectFlag;
	/** 网络连接不可用的提示消息 */
	private String connectedErrorMsg;

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * 
	 */
	public VolleyClient(Context context) {
		this.context = context;
		this.requestQueue = Volley.newRequestQueue(context);
		this.iRequestQueue = 0;
		this.bitmapDiskLruCache = new MyDiskLruCache(context, Constants.IMAGE_CACHE_DIR);
		this.imageLoader = new ImageLoader(requestQueue, new VolleyImageCache(this.bitmapDiskLruCache));
		this.retryPolicy = new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, MAX_NUM_RETRIES, BACKOFF_MULTIPLIER);
		this.isConnectFlag = false;
		this.connectedErrorMsg = "抱歉，网络连接不可用";
		
		if (context instanceof Activity) {
			this.activity = (Activity) context;
		}
		
		createLoadingView();
	}

	/**
	 * 
	 * 设置当前 Activity，目的是为了将等待视图添加到当前 activity 上。
	 * 
	 * @param currActivity 当前 Activity
	 * 
	 */
	public void setActivity(Activity currActivity) {
		if (currActivity == null) {
			return;
		}
		
		this.activity = currActivity;
		
		// 将等待视图添加到 activity 上
		this.activity.addContentView(loadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
	}

	/**
	 * 
	 * 发送字符串请求。
	 * 
	 * @param method 请求方式，“post”或“get”
	 * @param url 请求路径
	 * @param headers 请求头
	 * @param params 请求参数
	 * @param isLoading 是否显示等待框：
	 * <ul>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ul>
	 * @param loadingMsg 等待消息
	 * @param listener Volley 监听器
	 * 
	 */
	public void sendStringRequest(final int method, //
			final String url, //
			final Map<String, String> headers, //
			final Map<String, String> params, //
			final boolean isLoading, //
			final String loadingMsg, //
			final VolleyListener<String> listener) {
		if ((method != Request.Method.GET) && (method != Request.Method.POST)) {
			Toast.makeText(context, String.format("%s--->只支持GET和POST请求", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if ((url == null) || ("".equals(url))) {
			Toast.makeText(context, String.format("%s--->请求路径不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if (listener == null) {
			Toast.makeText(context, String.format("%s--->Volley监听器不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}

		// 字符串请求
		StringRequest sRequest = null;
		// 请求体
		String requestBody = getRequestBody(params, "UTF-8");
		// 临时请求路径
		String tmpUrl = url;

		if (requestBody != null) {
			tmpUrl = String.format("%s?%s", url, requestBody);
		}

		sRequest = new StringRequest(method, //
				tmpUrl, //
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (isLoading) {
							hide();
						}

						Log.d(TAG, response);

						listener.success(response);
					}
				}, //
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (isLoading) {
							hide();
						}

						Log.e(TAG, error.getMessage(), error.getCause());

						listener.error(error);
					}
				}) {
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						Map<String, String> tmpHeaders = headers;
						
						if (tmpHeaders == null) {
							tmpHeaders = new HashMap<String, String>();
						}
						
						tmpHeaders.putAll(super.getHeaders());
						
						return tmpHeaders;
					}
		};
		
		if (!isConnect()) {
			sRequest = null;
			
			listener.error(new VolleyError(new RuntimeException(connectedErrorMsg)));
			
			return;
		}

		if (isLoading) {
			show(loadingMsg);
		}

		sRequest.setTag(TAG);
		sRequest.setRetryPolicy(retryPolicy);
		
		requestQueue.add(sRequest);
	}
	
	/**
	 * 
	 * 发送 JSON 对象请求。
	 * 
	 * @param method 请求方式，“post”或“get”
	 * @param url 请求路径
	 * @param headers 请求头
	 * @param params 请求参数
	 * @param isLoading 是否显示等待框：
	 * <ul>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ul>
	 * @param loadingMsg 等待消息
	 * @param listener Volley 监听器
	 * 
	 */
	public void sendJsonObjectRequest(final int method, //
			final String url, //
			final Map<String, String> headers, //
			final Map<String, String> params, //
			final boolean isLoading, //
			final String loadingMsg, //
			final VolleyListener<JSONObject> listener) {
		if ((method != Request.Method.GET) && (method != Request.Method.POST)) {
			Toast.makeText(context, String.format("%s--->只支持GET和POST请求", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if ((url == null) || ("".equals(url))) {
			Toast.makeText(context, String.format("%s--->请求路径不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if (listener == null) {
			Toast.makeText(context, String.format("%s--->Volley监听器不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}

		// Json 对象请求
		JsonObjectRequest joRequest = null;
		// 请求体
		String requestBody = getRequestBody(params, "UTF-8");
		// 临时请求路径
		String tmpUrl = url;

		// GET 请求
		if (method == Request.Method.GET) {
			if (requestBody != null) {
				tmpUrl = String.format("%s?%s", url, requestBody);
			}

			requestBody = null;
		}

		joRequest = new JsonObjectRequest(method, //
				tmpUrl, //
				requestBody, //
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (isLoading) {
							hide();
						}

						Log.d(TAG, response.toString());

						listener.success(response);
					}
				}, //
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (isLoading) {
							hide();
						}

						Log.e(TAG, error.getMessage(), error.getCause());

						listener.error(error);
					}
				}) {
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						Map<String, String> tmpHeaders = headers;
						
						if (tmpHeaders == null) {
							tmpHeaders = new HashMap<String, String>();
						}
						
						tmpHeaders.putAll(super.getHeaders());
						
						// POST 请求
						if (Request.Method.POST == method) {
							tmpHeaders.put("Content-type", POST_CONTENT_TYPE);
						}
						
						return tmpHeaders;
					}
		};
		
		if (!isConnect()) {
			joRequest = null;
			
			listener.error(new VolleyError(new RuntimeException(connectedErrorMsg)));
			
			return;
		}

		if (isLoading) {
			show(loadingMsg);
		}

		joRequest.setTag(TAG);
		joRequest.setRetryPolicy(retryPolicy);
		
		requestQueue.add(joRequest);
	}
	
	/**
	 * 
	 * 发送 JSON 数组请求。
	 * 
	 * @param method 请求方式，“post”或“get”
	 * @param url 请求路径
	 * @param headers 请求头
	 * @param params 请求参数
	 * @param isLoading 是否显示等待框：
	 * <ul>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ul>
	 * @param loadingMsg 等待消息
	 * @param listener Volley 监听器
	 * 
	 */
	public void sendJsonArrayRequest(final int method, //
			final String url, //
			final Map<String, String> headers, //
			final Map<String, String> params, //
			final boolean isLoading, //
			final String loadingMsg, //
			final VolleyListener<JSONArray> listener) {
		if ((method != Request.Method.GET) && (method != Request.Method.POST)) {
			Toast.makeText(context, String.format("%s--->只支持GET和POST请求", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if ((url == null) || ("".equals(url))) {
			Toast.makeText(context, String.format("%s--->请求路径不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if (listener == null) {
			Toast.makeText(context, String.format("%s--->Volley监听器不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}

		// Json 数组请求
		JsonArrayRequest jaRequest = null;
		// 请求体
		String requestBody = getRequestBody(params, "UTF-8");
		// 临时请求路径
		String tmpUrl = url;

		// GET 请求
		if (method == Request.Method.GET) {
			if (requestBody != null) {
				tmpUrl = String.format("%s?%s", url, requestBody);
			}

			requestBody = null;
		}

		jaRequest = new JsonArrayRequest(method, //
				tmpUrl, //
				requestBody, //
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						if (isLoading) {
							hide();
						}

						Log.d(TAG, response.toString());

						listener.success(response);
					}
				}, //
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (isLoading) {
							hide();
						}

						Log.e(TAG, error.getMessage(), error.getCause());

						listener.error(error);
					}
				}) {
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						Map<String, String> tmpHeaders = headers;
						
						if (tmpHeaders == null) {
							tmpHeaders = new HashMap<String, String>();
						}
						
						tmpHeaders.putAll(super.getHeaders());
						
						// POST 请求
						if (Request.Method.POST == method) {
							tmpHeaders.put("Content-type", POST_CONTENT_TYPE);
						}
						
						return tmpHeaders;
					}
		};
		
		if (!isConnect()) {
			jaRequest = null;
			
			listener.error(new VolleyError(new RuntimeException(connectedErrorMsg)));
			
			return;
		}

		if (isLoading) {
			show(loadingMsg);
		}

		jaRequest.setTag(TAG);
		jaRequest.setRetryPolicy(retryPolicy);
		
		requestQueue.add(jaRequest);
	}
	
	/**
	 * 
	 * 发送加载图片请求（无缓存）。
	 * 
	 * @param url 请求路径
	 * @param isLoading 是否显示等待框：
	 * <ul>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ul>
	 * @param loadingMsg 等待消息
	 * @param maxWidth 图片的最大宽度
	 * @param maxHeight 图片的最大高度
	 * @param listener Volley 监听器
	 * 
	 */
	public void sendImageRequest(final String url, //
			final boolean isLoading, //
			final String loadingMsg, //
			final int maxWidth, //
			final int maxHeight, //
			final VolleyListener<Bitmap> listener) {
		if ((url == null) || "".equals(url)) {
			Toast.makeText(context, String.format("%s--->图片路径不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if (listener == null) {
			Toast.makeText(context, String.format("%s--->Volley监听器不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}

		// 图片请求
		ImageRequest iRequest = new ImageRequest(url, //
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						if (isLoading) {
							hide();
						}

						Log.d(TAG, response.toString());

						listener.success(response);
					}
				}, //
				maxWidth, //
				maxHeight, //
				ImageView.ScaleType.CENTER_INSIDE, //
				Config.RGB_565, //
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (isLoading) {
							hide();
						}

						Log.e(TAG, error.getMessage(), error.getCause());

						listener.error(error);
					}
				});

		if (!isConnect()) {
			iRequest = null;
			
			listener.error(new VolleyError(new RuntimeException(connectedErrorMsg)));
			
			return;
		}
		
		if (isLoading) {
			show(loadingMsg);
		}

		iRequest.setTag(TAG);
		iRequest.setRetryPolicy(retryPolicy);
		
		requestQueue.add(iRequest);
	}
	
	/**
	 * 
	 * 发送加载图片请求（有缓存）。
	 * 
	 * @param view 显示图片的 ImageView
	 * @param url 图片路径
	 * @param maxWidth 图片的最大宽度
	 * @param maxHeight 图片的最大高度
	 * @param defaultImageResId 默认图片的资源 ID
	 * @param errorImageResId 错误图片的资源 ID
	 * 
	 */
	public void sendImageLoader(final ImageView view, //
			final String url, //
			final int maxWidth, //
			final int maxHeight, //
			final int defaultImageResId, //
			final int errorImageResId) {
		if ((url == null) || "".equals(url)) {
			Toast.makeText(context, String.format("%s--->图片路径不能为空", TAG), Toast.LENGTH_SHORT).show();

			return;
		}
		
		if (!isConnect()) {
			return;
		}
		
		imageLoader.get(url, //
				ImageLoader.getImageListener(view, defaultImageResId, errorImageResId), //
				maxWidth, //
				maxHeight,//
				ImageView.ScaleType.CENTER_INSIDE);
	}

	/**
	 * 
	 * 获取图片磁盘缓冲区。
	 * 
	 * @return 图片磁盘缓冲区
	 * 
	 */
	public MyDiskLruCache getBitmapDiskLruCache() {
		return bitmapDiskLruCache;
	}

	/**
	 * 
	 * 创建等待视图。
	 * 
	 */
	@SuppressLint("InflateParams")
	private void createLoadingView() {
		if (loadingView == null) {
			// 获取布局加载器
			LayoutInflater inflater = LayoutInflater.from(context);
			
			// 加载布局
			loadingView = inflater.inflate(R.layout.loading_view, null);
			ivLoading = (ImageView) loadingView.findViewById(R.id.iv_loading);
			tvLoading = (TextView) loadingView.findViewById(R.id.tv_loading);
			
			// Gif 动画
			loadingAnimDraw = (AnimationDrawable) ivLoading.getDrawable();
			// true-只执行一次；false-循环执行
			loadingAnimDraw.setOneShot(false);
			
			loadingView.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 
	 * 判断网络连接是否可用。
	 * 
	 * @return
	 * <ul>
	 * 	<li>true：可用；</li>
	 * 	<li>false：不可用。</li>
	 * </ul>
	 * 
	 */
	private boolean isConnect() {
		try {
			// 获取手机所有连接管理对象（包括对 wifi，移动数据等连接的管理）
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			if (connectivityManager != null) {
				// 获取网络连接管理的对象
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
				
				if ((networkInfo != null) && networkInfo.isConnected()) {
					// 判断当前网络是否已经连接
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
						isConnectFlag = false;
						
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		
		if (!isConnectFlag) {
			Utils.showToastBGNew(context, connectedErrorMsg);

			isConnectFlag = true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * 显示等待对话框。
	 * 
	 * @param loadingMsg
	 * 
	 */
	private void show(String loadingMsg) {
		iRequestQueue++;
		
		if ((loadingMsg != null) && (!"".equals(loadingMsg))) {
			tvLoading.setText(loadingMsg);
			tvLoading.setVisibility(View.VISIBLE);
		}
		
		if ((iRequestQueue == 1) && (loadingView.getVisibility() == View.GONE)) {
			// 启动菊花的转动
			loadingAnimDraw.start();
			// 让等待视图浮动在最上层
			loadingView.bringToFront();
			// 显示等待视图
			loadingView.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 
	 * 隐藏等待对话框。
	 * 
	 */
	private void hide() {
		iRequestQueue--;
		
		if (iRequestQueue <= 0) {
			iRequestQueue = 0;
		}
		
		if ((iRequestQueue == 0) && (loadingView.getVisibility() != View.GONE)) {
			// 停止菊花的转动
			loadingAnimDraw.stop();
			
			tvLoading.setText("");
			tvLoading.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 
	 * 获取请求体（标准的请求参数）。
	 * 
	 * @param params 参数 Map
	 * @param encode 编码类型，如：GBK，UTF-8，ISO-8859-1
	 * @return 请求体
	 * 
	 */
	private String getRequestBody(Map<String, String> params, String encode) {
		if ((params == null) || params.isEmpty() || (encode == null) || "".equals(encode)) {
			return null;
		}
		
		try {
			// 编码后的请求参数
			StringBuilder encodeParams = new StringBuilder();
			// 步进变量
			int i = 0;
			
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (params.size() == 1) {
					encodeParams.append(URLEncoder.encode(entry.getKey(), encode));
					encodeParams.append("=");
					encodeParams.append(URLEncoder.encode(entry.getValue(), encode));
				} else {
					if (i < (params.size() - 1)) {
						encodeParams.append(URLEncoder.encode(entry.getKey(), encode));
						encodeParams.append("=");
						encodeParams.append(URLEncoder.encode(entry.getValue(), encode));
						encodeParams.append("&");
					} else {
						encodeParams.append(URLEncoder.encode(entry.getKey(), encode));
						encodeParams.append("=");
						encodeParams.append(URLEncoder.encode(entry.getValue(), encode));
					}	
				}
				
				i++;
			}
			
			return encodeParams.toString();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, String.format("不支持的编码格式：%s", encode), e);
			
			return null;
		}
	}
	
	/**
	 * 
	 * 取消所有请求。
	 * 
	 */
	public void cancelAll() {
		requestQueue.cancelAll(TAG);
		bitmapDiskLruCache.close();
		
		iRequestQueue = 0;
		
		hide();
	}
	
	/**
	 * 
	 * Volley 监听器。
	 * 
	 * @author ChenYong
	 *
	 * @param <T> 泛型
	 * 
	 */
	public interface VolleyListener<T> {
		/**
		 * 
		 * 请求执行之前调用。
		 * 
		 */
		// public abstract void before();
		
		/**
		 * 
		 * 请求执行完成后调用。
		 * 
		 */
		// public abstract void complete();
		
		/**
		 * 
		 * 请求成功时执行。
		 * 
		 * @param response 响应实例
		 * 
		 */
		public abstract void success(T response);
		
		/**
		 * 
		 * 请求失败时执行。
		 * 
		 * @param error 失败实例
		 * 
		 */
		public abstract void error(VolleyError error);
	}
}