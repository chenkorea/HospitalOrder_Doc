package com.boil.hospitalorder.generally.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * Abstract Async Response Listener implementation
 * 
 * Subclass should implement at lease two methods.
 * 1. onSuccess() to handle the corresponding successful response object
 * 2. onFailure() to handle the exception if any
 * 
 * @author bright_zheng
 *
 */
public abstract class AbstractAsyncResponseListener implements AsyncResponseListener{
	public static final int RESPONSE_TYPE_STRING = 1;
	public static final int RESPONSE_TYPE_JSON_ARRAY = 2;
	public static final int RESPONSE_TYPE_JSON_OBJECT = 3;
	public static final int RESPONSE_TYPE_STREAM = 4;
	private int responseType;
	private Activity activity;
	private Context context;
	
	private static String TAG="AbstractAsyncResponseListener";
	
	public AbstractAsyncResponseListener(){
		this.responseType = RESPONSE_TYPE_STRING; // default type
	}
	
	public AbstractAsyncResponseListener(int responseType, Activity activity){
		this.responseType = responseType;
		this.activity = activity;
	}
	
	/**
	 * 传递context 用于重新登录使用
	 * @param responseType
	 * @param activity
	 * @param context
	 */
	public AbstractAsyncResponseListener(int responseType, Activity activity, Context context){
		this.responseType = responseType;
		this.activity = activity;
		this.context = context;
	}
	
	public void onResponseReceived(HttpEntity response){
		try {
			switch(this.responseType){
		        case RESPONSE_TYPE_JSON_ARRAY:{
		        	String responseBody = EntityUtils.toString(response);	
		        	Log.i(TAG, "RESPONSE_TYPE_JSON_ARRAY Return JSON String: " + responseBody);
		        	JSONArray json = null;
		        	if(responseBody!=null && responseBody.trim().length()>0){
		        		try {
							json = (JSONArray) new JSONTokener(responseBody).nextValue();
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
		    		onSuccess(json);
		        	break;
		        }
		        case RESPONSE_TYPE_JSON_OBJECT:{
		        	String responseBody = EntityUtils.toString(response);	
		        	
		        	//responseBody = responseBody.substring(1, responseBody.length() - 1);
		        	
		        	Log.i(TAG, "RESPONSE_TYPE_JSON_OBJECT Return JSON String: " + responseBody);
		        	JSONObject json = null;
		        	if(responseBody!=null && responseBody.trim().length()>0){
		        		json = JSON.parseObject(responseBody);
		        	}
		        	Log.i(TAG, "onSuccess JSON.parseObject(responseBody): " + JSON.parseObject(responseBody));
		    		onSuccess(json);	
		        	break;
		        }
		        case RESPONSE_TYPE_STREAM:{
		        	onSuccess(response.getContent());
		        	break;
		        }
		        default:{
		        	String responseBody = EntityUtils.toString(response);
		        	
		        	// 登录失效
		        	if (StringUtils.isNotEmpty(responseBody) && responseBody.contains("youMustLoginError0000")) {
//		        		Intent intent = new Intent();
//		        		intent.putExtra("loginout", "loginout");
//			        	intent.setClass(activity, ReloginActivity.class);
//			        	activity.startActivity(intent);
			        	
		        	} else {
		        		onSuccess(responseBody);
		        	}
		        }         
			}
	    } catch(IOException e) {
	    	onFailure(e);
	    }	
	}
	
	public void onResponseReceived(Throwable response){
		onFailure(response);
	}
	
	protected void onSuccess(JSONArray response){}
	
	protected void onSuccess(JSONObject response){}
	
	protected void onSuccess(InputStream response){}
	
	protected void onSuccess(String response) {}

	protected void onFailure(Throwable e) {}
}