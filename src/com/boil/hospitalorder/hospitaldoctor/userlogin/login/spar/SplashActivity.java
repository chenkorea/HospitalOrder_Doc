package com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.Utils;

public class SplashActivity extends Activity {

	private boolean first = true;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sp=Utils.getSharedPreferences(this);
		first=sp.getBoolean("isFirst", true);
		
		  new Handler(new Handler.Callback() {
	            //处理接收到的消息的方法
	            @Override
	            public boolean handleMessage(Message msg) {
	            	if (first) {
						Intent intent = new Intent(SplashActivity.this,WelcomActivity.class); 
						startActivity(intent);
						
					} else {
						gotoMainPage();
					}
	            	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					SplashActivity.this.finish();
					return false;
	            }
	        }).sendEmptyMessageDelayed(0,2000);
		
	}
	
	private void gotoMainPage() {
		// 偏好设置
		// 设置 Web Service 的命名空间
	/*	Utils.savePref("WS_NAMESPACE", "http://webser.data.zlyy", this);
		// 设置 Web Service 的请求地址
		Utils.savePref("WS_URL", "http://yyghw.gzszlyy.cn:8086/ZlyyData/services/ZlyyData", this);
		*/
		Intent intent = new Intent(this, LoginActivity.class);
//		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
