package com.boil.hospitalorder.generally.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

import com.boil.hospitalorder.generally.http.model.InputHolder;
import com.boil.hospitalorder.generally.http.model.OutputHolder;
import com.boil.hospitalorder.generally.http.util.DialogUtil;

/**
 * AsyncHttpSender is the AsyncTask implementation
 * 
 * @author bright_zheng
 * 
 */
public class AsyncHttpBackupSender extends AsyncTask<InputHolder, Void, OutputHolder> {
	private static String TAG = "AsyncHttpSender";
	private Dialog mPD;
	private InputHolder input;

	public AsyncHttpBackupSender(InputHolder input) {
		super();
		this.input = input;
	}

	public AsyncHttpBackupSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OutputHolder doInBackground(InputHolder... params) {
		HttpEntity entity = null;
		InputHolder input = params[0];
		try {
			HttpResponse response = AsyncHttpClient.getClient().execute(
					(HttpUriRequest) input.getRequest());
			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() >= 300) {
				return new OutputHolder(new HttpResponseException(
						status.getStatusCode(), status.getReasonPhrase()),
						input.getResponseListener());
			}

			entity = response.getEntity();
			Log.i(TAG, "isChunked:" + entity.isChunked());
			if (entity != null) {
				try {
					entity = new BufferedHttpEntity(entity);
				} catch (Exception e) {
					// Log.e(<span
					// style="background-color: #ffffff;">TAG</span>,
					// e.getMessage(), e);
					// ignore?
				}
			}
		} catch (ClientProtocolException e) {
			// Log.e(<span style="background-color: #ffffff;">TAG</span>,
			// e.getMessage(), e);
			return new OutputHolder(e, input.getResponseListener());
		} catch (IOException e) {
			// Log.e(<span style="background-color: #ffffff;">TAG</span>,
			// e.getMessage(), e);
			return new OutputHolder(e, input.getResponseListener());
		} catch(Exception e){
			return new OutputHolder(e, input.getResponseListener());
		}
		return new OutputHolder(entity, input.getResponseListener());
	}

	@Override
	protected void onPreExecute() {

		Log.i(TAG, "AsyncHttpSender.onPreExecute()");
		if (input.getpContext() != null) {
//			this.mPD = ProgressDialog.show(input.getpContext(),
//					input.getpTitle(), input.getpMessage(), true,
//					input.ispCancelable());
			this.mPD =DialogUtil.createLoadingDialog(input.getpContext(),input.getpMessage().toString(),input.ispCancelable());
			this.mPD.show();
			
			if (input.ispCancelable()) {
				this.mPD.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(final DialogInterface pDialogInterface) {
						pDialogInterface.dismiss();
					}
				});
			}
		}

		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(OutputHolder result) {
		Log.i(TAG, "AsyncHttpSender.onPostExecute()");
		super.onPostExecute(result);

		if(this.mPD!=null)
			this.mPD.dismiss();
		
		if (isCancelled()) {
			Log.i(TAG, "AsyncHttpSender.onPostExecute(): isCancelled() is true");
			return; // Canceled, do nothing
		}
		
		AsyncResponseListener listener = result.getResponseListener();
		HttpEntity response = result.getResponse();
		Throwable exception = result.getException();
		if (response != null) {
			Log.i(TAG, "AsyncHttpSender.onResponseReceived(response)");
			listener.onResponseReceived(response);
		} else {
			Log.i(TAG, "AsyncHttpSender.onResponseReceived(exception)");
			listener.onResponseReceived(exception);
		}

	}

	@Override
	protected void onCancelled() {
		Log.i(TAG, "AsyncHttpSender.onCancelled()");
		super.onCancelled();
		// this.isCancelled = true;
	}
}