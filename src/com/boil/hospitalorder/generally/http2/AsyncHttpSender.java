package com.boil.hospitalorder.generally.http2;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.boil.hospitalorder.generally.http2.model.InputHolder;
import com.boil.hospitalorder.generally.http2.model.OutputHolder;
import com.boil.hospitalorder.utils.LoadingUtils;

/**
 * 
 * 异步请求发送器。
 * 
 * @author ChenYong
 * @date 2016-08-31
 *
 */
public class AsyncHttpSender extends AsyncTask<InputHolder, Void, OutputHolder> {
	/** 标记 */
	private static String TAG = "AsyncHttpSender";
	/** Input Holder */
	private InputHolder input;
	
	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param input Input Holder
	 * 
	 */
	public AsyncHttpSender(InputHolder input) {
		super();
		
		this.input = input;
	}

	@Override
	protected OutputHolder doInBackground(InputHolder... params) {
		try {
			HttpEntity entity = null;
			InputHolder input = params[0];
			HttpResponse response = AsyncHttpClient.getClient().execute((HttpUriRequest) input.getRequest());
			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() >= 300) {
				return new OutputHolder(new HttpResponseException(status.getStatusCode(), //
						status.getReasonPhrase()), //
						input.getListener());
			}

			entity = response.getEntity();
			
			if (entity != null) {
				try {
					entity = new BufferedHttpEntity(entity);
				} catch (Exception e) {
					throw e;
				}
			}
			
			return new OutputHolder(entity, input.getListener());
		} catch(Exception e){
			return new OutputHolder(e, input.getListener());
		}
	}

	@Override
	protected void onPreExecute() {
		input.getListener().onBeforeSend();
		
		if (input.isShowLoad()) {
			View loadView = LoadingUtils.showLoadView(input.getActivity(), input.getLoadMsg());
			
			if (loadView != null) {
				loadView.setClickable(false);
			}
		}

		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(OutputHolder output) {
		super.onPostExecute(output);
		
		output.getListener().onCompleteReceived();

		// 获取当前 Activity 的异步任务队列
		List<AsyncHttpSender> tasks =  AsyncHttpClient.getTasks(input.getActivity());
		
		if ((tasks != null ) && !tasks.isEmpty()) {
			tasks.remove(input.getSender());
			
			if (input.isShowLoad() && tasks.isEmpty()) {
				View loadView = LoadingUtils.hideLoadView(input.getActivity());
				
				if (loadView != null) {
					loadView.setClickable(true);
					
					loadView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							input.getListener().onCompleteReload();
						};
					});
				}
			}
		}
		
		// 异步任务已经取消
		if (isCancelled()) {
			Log.i(TAG, String.format("%s--->异步任务已经取消，不需要再继续执行了。", input.getActivity().getClass().getSimpleName()));
			
			return;
		}
		
		AsyncResponseListener listener = output.getListener();
		HttpEntity response = output.getResponse();
		Exception exception = output.getException();
		
		// 请求成功
		if (response != null) {
			Log.i(TAG, String.format("%s--->请求成功。", input.getActivity().getClass().getSimpleName()));
			
			listener.onSuccessReceived(response);
			
			// 请求失败
		} else {
			Log.i(TAG, String.format("%s--->请求失败。", input.getActivity().getClass().getSimpleName()));
			
			listener.onFailureReceived(exception);
		}

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
}