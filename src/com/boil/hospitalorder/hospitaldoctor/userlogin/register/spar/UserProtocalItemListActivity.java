package com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("SetJavaScriptEnabled")
public class UserProtocalItemListActivity extends BaseBackActivity {

	private UserProtocalItemListActivity context = UserProtocalItemListActivity.this;
	
	@ViewInject(R.id.webview)
	private WebView webView;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addresstoptitle;
	
	/** 标题上的保存按钮 */
	@ViewInject(R.id.bt_save)
	private TextView titleSaveBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_info_view);
		ViewUtils.inject(this);
		initData();
	}

	private void initData() {
		
		titleSaveBtn.setVisibility(View.INVISIBLE);
		Utils.backClick(this, backBtn);
		String rsId = getIntent().getStringExtra("rsId");
		String flag = getIntent().getStringExtra("flag");
		String tempTitle = "";
		if("0".equals(flag)) {
			tempTitle = "用户检查协议与注意事项";
		} else if("1".equals(flag)) {
			tempTitle = "用户注册协议";
		} else if("2".equals(flag)){
			tempTitle = "贵州银行协议";
		} else if("3".equals(flag)){
			tempTitle = "退款须知";
		}else if ("4".equals(flag)) {
			tempTitle = "用户使用协议";
		}
		addresstoptitle.setText(tempTitle);
		
		// 设置响应JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// 支持 JavaScript
		// 是否支持缩放
		webView.getSettings().setSupportZoom(false);
		webView.getSettings().setBuiltInZoomControls(false);
		// 自适应屏幕，居中显示
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		
		webView.setWebChromeClient(new WebChromeClientc());
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String strUrl) {
				view.loadUrl(strUrl);
				// tvUrl.setText(strUrl);
				return false;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String strUrl) {
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}
		});
		
		//http://www.cdfortis.com/ad/20150609-04.html
		if("0".equals(flag)) {
			webView.loadUrl("http://58.42.232.110:8086/agreement/findItem?rsId=" + rsId);
		} else if("1".equals(flag)) {
			webView.loadUrl("http://58.42.232.110:8085/ZlyyAppointment/reg.html");
		} else if("2".equals(flag)){
			webView.loadUrl("http://58.42.232.110:8085/ZlyyAppointment/gzbankagreement.html");
		} else if ("3".equals(flag)) {
			webView.loadUrl("http://58.42.232.110:8085/ZlyyAppointment/ReturnInfo.html");
		} else if ("4".equals(flag)) {
			
			webView.loadUrl("http://58.42.232.110:8086/hsptapp/page/doctorAgreement.jsp");
		}
	}
	
	class WebChromeClientc extends WebChromeClient {
		public void onProgressChanged(WebView view, int progress) {
			context.setTitle("Loading...");
			context.setProgress(progress * 100);
			if (progress == 100)
				context.setTitle(R.string.app_name);
		}
		
		  @Override  
          public void onReceivedTitle(WebView view, String title) {  
              super.onReceivedTitle(view, title);  
//              addresstoptitle.setText(title);  
          }  
	}
}
