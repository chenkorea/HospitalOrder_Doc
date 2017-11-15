package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import org.apache.commons.lang.StringUtils;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
@SuppressLint("SetJavaScriptEnabled")
public class SubscribeArticleDetailActivity extends BaseBackActivity {

	private SubscribeArticleDetailActivity context = SubscribeArticleDetailActivity.this;
	
	@ViewInject(R.id.webview)
	private WebView webView;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addresstoptitle;

	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	
	@ViewInject(R.id.progressBar1)
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_info_view);
		ViewUtils.inject(this);
		initData();
	}

	private void initData() {
		
		Utils.backClick(this, backBtn);
		String flag = getIntent().getStringExtra("flag");//4为电子病历详细
		addresstoptitle.setText("详细");
		bt_save.setVisibility(View.INVISIBLE);
		
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
		if(StringUtils.isNotBlank(flag) && "4".equals(flag)) {
			String tid = getIntent().getStringExtra("tid");
			String admno = getIntent().getStringExtra("admno");
			String mrId = getIntent().getStringExtra("mrId");
			String urlStr = "";
			String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
			
			if(StringUtils.isNotBlank(mrId)) {
				urlStr = hosIp +"/doctor/emr/lkemrcontent/603.html?tid=" + tid + "&admno=" + admno + "&mrId=" + mrId;
			} else {
				urlStr = hosIp +"/doctor/emr/lkemrcontent/603.html?tid=" + tid + "&admno=" + admno;
			}
			webView.loadUrl(urlStr);
		} else {
			String articleId = getIntent().getStringExtra("id");
			String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
			
			webView.loadUrl(hosIp +"/doctor/notice/lknoticedetail/404.html?adtid=" + articleId);
		}
	}
	
	class WebChromeClientc extends WebChromeClient {
		public void onProgressChanged(WebView view, int progress) {
//			context.setTitle("Loading...");
//			context.setProgress(progress * 100);
			if (progress == 100)
				context.setTitle(R.string.app_name);
			progressBar.setProgress(progress);
			if(progressBar.getProgress() == 100) {
				progressBar.setVisibility(View.GONE);
			}
		}
		
		  @Override  
          public void onReceivedTitle(WebView view, String title) {  
              super.onReceivedTitle(view, title);  
              
              addresstoptitle.setText(title);
          }  
	}
}
