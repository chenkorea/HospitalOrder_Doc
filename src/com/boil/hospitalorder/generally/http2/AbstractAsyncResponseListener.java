package com.boil.hospitalorder.generally.http2;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONTokener;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 抽象的异步响应监听器。
 * 
 * @author ChenYong
 * @date 2016-08-31
 *
 */
public abstract class AbstractAsyncResponseListener implements AsyncResponseListener {
	/** 标志 */
	private static String TAG = "AbstractAsyncResponseListener";
	/** 响应类型 */
	private int responseType;
	/** 响应：字符串 */
	public static final int RESPONSE_TYPE_STRING = 1;
	/** 响应：JSON 对象 */
	public static final int RESPONSE_TYPE_JSON_OBJECT = 2;
	/** 响应：JSON 数组 */
	public static final int RESPONSE_TYPE_JSON_ARRAY = 3;
	/** 响应：流 */
	public static final int RESPONSE_TYPE_STREAM = 4;

	/**
	 * 
	 * 默认构造器。<br>
	 * 响应：json 对象。
	 * 
	 */
	public AbstractAsyncResponseListener() {
		this(RESPONSE_TYPE_JSON_OBJECT);
	}

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param responseType
	 *            响应类型：
	 *            <ul>
	 *            <li>1-响应字符串</li>
	 *            <li>2-响应 JSON 对象</li>
	 *            <li>3-响应 JSON 数组</li>
	 *            <li>4-响应流</li>
	 *            </ul>
	 * 
	 */
	public AbstractAsyncResponseListener(int responseType) {
		super();

		if ((responseType < 1) || (responseType > 4)) {
			responseType = RESPONSE_TYPE_JSON_OBJECT;
		}

		this.responseType = responseType;
	}

	@Override
	public void onBeforeSend() {
		onBefore();
	}

	@Override
	public void onCompleteReceived() {
		onComplete();
	}

	@Override
	public void onSuccessReceived(HttpEntity response) {
		try {
			switch (this.responseType) {
			case RESPONSE_TYPE_STRING: {
				String responseStr = EntityUtils.toString(response);

				Log.i(TAG,  String.format("请求响应字符串：%s。", responseStr));

				onSuccess(responseStr);
			}
			case RESPONSE_TYPE_JSON_OBJECT: {
				String responseStr = EntityUtils.toString(response);

				Log.i(TAG, String.format("请求响应JSON对象：%s。", responseStr));

				// 结果
				JSONObject result = null;
				if (StringUtils.isNotEmpty(responseStr)) {
					result = JSON.parseObject(responseStr);
				}

				onSuccess(result);

				break;
			}
			case RESPONSE_TYPE_JSON_ARRAY: {
				String responseStr = EntityUtils.toString(response);

				Log.i(TAG, String.format("请求响应JSON数组：%s。", responseStr));

				// 结果
				JSONArray result = null;
				if (StringUtils.isNotEmpty(responseStr)) {
					result = (JSONArray) new JSONTokener(responseStr).nextValue();
				}

				onSuccess(result);

				break;
			}
			case RESPONSE_TYPE_STREAM: {
				Log.i(TAG, "请求响应流。");
				
				onSuccess(response.getContent());

				break;
			}
			default: {
				onFailure(new Exception("请求找到不到对应响应类型（Response Type）。"));

				break;
			}
			}
		} catch (Exception e) {
			onFailure(e);
		}
	}

	@Override
	public void onFailureReceived(Exception e) {
		// 查询数据出错
		onFailure(e);
	}

	@Override
	public void onCompleteReload() {
		// 重新加载
		onReload();
	}

	/**
	 * 
	 * 请求发送前执行。
	 * 
	 */
	protected void onBefore() {
	}

	/**
	 * 
	 * 请求完成时执行。
	 * 
	 */
	protected void onComplete() {

	}

	/**
	 * 
	 * 请求成功时执行。
	 * 
	 * @param response
	 *            响应字符串
	 * 
	 */
	protected void onSuccess(String response) {
	}

	/**
	 * 
	 * 请求成功时执行。
	 * 
	 * @param response
	 *            响应 JSON 对象
	 * 
	 */
	protected void onSuccess(JSONObject response) {
	}

	/**
	 * 
	 * 请求成功时执行。
	 * 
	 * @param response
	 *            响应 JSON 数组
	 * 
	 */
	protected void onSuccess(JSONArray response) {
	}

	/**
	 * 
	 * 请求成功时执行。
	 * 
	 * @param response
	 *            响应流
	 * 
	 */
	protected void onSuccess(InputStream response) {
	}

	/**
	 * 
	 * 请求失败时执行。
	 * 
	 * @param e
	 *            异常
	 * 
	 */
	protected void onFailure(Exception e) {
	}

	/**
	 * 
	 * 请求完成时重新加载。
	 * 
	 */
	protected void onReload() {
	}
}