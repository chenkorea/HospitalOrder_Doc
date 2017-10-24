package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import org.ksoap2.serialization.SoapObject;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.generally.http.DownLoadFile;
import com.boil.hospitalorder.generally.util.CommonDialog;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.LoginActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar.WelcomActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.PerfectUserInfoCommitActivity;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.UpdPasswdActivity;
import com.boil.hospitalorder.hospitaldoctor.version.VersionInfo;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.CustomDialog;
import com.boil.hospitalorder.utils.DataCleanManager;
import com.boil.hospitalorder.utils.RevealLayout;
import com.boil.hospitalorder.utils.Utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetActivity extends BaseBackActivity {
	/** 当前 Activity 的上下文 */
	private SetActivity context = SetActivity.this;
	/** Activity 的标题 */
	private TextView addreTitle;
	/** 返回上一个 Activity 的按钮 */
	private ImageView backBtn;
	/** 保存按钮 */
	private TextView saveBtn;
	/** 关于我们 */
	private TextView mAboutTextView;
	/** 账号设置 */
//	private TextView mUserSetTextView;
	/** 欢迎页 */
	private TextView mWelcomeTextView;
	/** 密码修改 */
//	private TextView tv_changePwd;
	/** 退出登录的 RevealLayout */
	private RevealLayout rlLogout;
	/** 退出登录的按钮 */
	private BootstrapButton mLogoutButton;

	private TextView tvCacheSize;

	/** 清除缓存 */
	private LinearLayout layClearCache;

	private TextView tv_check_version_num;
	private LinearLayout lay_version;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);

		initView();
		initEvent();
	}

	@Override
	protected void onResume() {
		showOrHideLogoutBtn();

		super.onResume();
	}

	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		addreTitle = (TextView) findViewById(R.id.addresstoptitle);
		backBtn = (ImageView) findViewById(R.id.back);
		saveBtn = (TextView) findViewById(R.id.bt_save);
		saveBtn.setVisibility(View.INVISIBLE);
//		mUserSetTextView = (TextView) findViewById(R.id.tv_user_set);
//		tv_changePwd = (TextView) findViewById(R.id.tv_changePwd);
		mWelcomeTextView = (TextView) findViewById(R.id.tv_welcome);
		mAboutTextView = (TextView) findViewById(R.id.tv_about_us);
		rlLogout = (RevealLayout) findViewById(R.id.rl_logout);
		mLogoutButton = (BootstrapButton) findViewById(R.id.btn_logout);

		tv_check_version_num = (TextView) findViewById(R.id.tv_check_version_num);
		lay_version = (LinearLayout) findViewById(R.id.lay_version);

		layClearCache = (LinearLayout) findViewById(R.id.lay_clearCache);
		tvCacheSize = (TextView) findViewById(R.id.tv_cache_size);

		tv_check_version_num.setText("当前版本:v" + Utils.getAppVersion(context));

		// 设置 Activity 的标题
		addreTitle.setText("设置");
		// 返回上一个 Activity
		Utils.backClick(this, backBtn);

		String tempCache = getCacheSize();
		tvCacheSize.setText(tempCache);
		showOrHideLogoutBtn();
//		getVersionMsg();
	}

	private String getCacheSize() {

		// File file = context.getExternalCacheDir();
		//
		// String sizeStr = "0k";
		// try {
		// sizeStr = DataCleanManager.getCacheSize(file);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		String one_cacheData = configSP.getString("one_department", "");
		String two_cacheData = configSP.getString("two_department", "");

		String allStr = one_cacheData + two_cacheData;
		String finalStr = DataCleanManager.getStrKBLen(allStr);

		return finalStr;
	}

	/**
	 * 
	 * 初始化事件。
	 * 
	 */
	@SuppressLint("CommitPrefEdits")
	private void initEvent() {
		// 关于我们
		mAboutTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this,
						AboutActivity.class);
				startActivity(intent);

				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});

		/*
		// 密码修改
		tv_changePwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 未登录
				if (isLogin()) {
					Intent intent = new Intent(SetActivity.this,
							UpdPasswdActivity.class);
					startActivity(intent);

					// 已登录
				} else {
					toLogin();
				}

				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});

		// 账号设置
		mUserSetTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 已登录
				if (isLogin()) {
					Intent intent = new Intent(SetActivity.this,
							PerfectUserInfoActivity.class);
					startActivity(intent);

					// 未登录
				} else {
					toLogin();
				}

				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});*/

		// 欢饮页
		mWelcomeTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this,WelcomActivity.class);
				intent.putExtra("Set", true);
				startActivity(intent);
			}
		});

		// 退出登录
		mLogoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utils.clearLoginInfo(context);
				Utils.clearUserInfo(context);
				
				Intent intent = new Intent(context, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		// 清除科室缓存.
		layClearCache.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Editor editor = configSP.edit();
				// 缓存数据
				editor.putString("one_department", "");

				editor.putString("two_department", "");
				editor.commit();
				tvCacheSize.setText("0K");
				Utils.showToastBGNew(context, "缓存清除成功！");
			}
		});

		lay_version.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				getVersionMsg();
			}
		});
	}

	private void initVersionDialog(final String fileName, float oldVersion, float newVersion, String type) {
		CustomDialog dialog;
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(context);
		customBuilder.setTitle("软件升级");
		
		if (type.equals("update")) {
			customBuilder.setMessage("当前版本:v" + oldVersion + "\n发现新版本v" + newVersion + "\n是否升级?");
			customBuilder.setPositiveButton("以后再说",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
				}
			});

			customBuilder.setNegativeButton("马上升级",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.dismiss();
					beginUpdate(fileName);
				}
			});
			dialog = customBuilder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		} else {
			customBuilder.setMessage("当前已是最新版本，无需更新！");
			customBuilder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog = customBuilder.create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
		
	}

	/*
	private void getVersionMsg() {
		// 获取 Web Service 的请求方法名称
		String wsMethod = "QueryApkInfo";
		// 指定 WebService 的命名空间和调用的方法名
		SoapObject request = new SoapObject(Constants.WS_NAMESPACE_3, wsMethod);

		// 设置参数
		request.addProperty("TradeID", 60074);

		WsAsyncHttpClient httpClient = new WsAsyncHttpClient(request,
				Constants.WS_URL_3);
		httpClient.sendRequest(context, null, true,
				new WsAbstractAsyncResponseListener() {
					@Override
					protected void onBefore() {

					}

					@Override
					protected void onSuccess(SoapObject response) {
						if (response != null) {
							SoapObject result = (SoapObject) response
									.getProperty(0);
							VersionInfo versionVo = Soap2Utils.parseBean(result,
									VersionInfo.class);

							if (versionVo.getResultCode() == 1) {
								downLoad(versionVo);
							} else {
								Utils.showToastBGNew(context, "查询版本信息为空");
							}
						}
					}

					@Override
					protected void onFailure(Exception e) {
						Utils.showToastBGNew(context, "查询版本信息失败");
					}
				});
	}*/

	private void showMyDialog(int max) {
		progressDialog.setMax(max);
	}

	private void setProgressValue(int value) {
		//dialog.incrementProgressBy(int)来设置进度。
		progressDialog.incrementProgressBy(value);
	}

	private void stop(String info) {
		DownLoadFile.downFlag = true;
		progressDialog.dismiss();
		CommonDialog.getToast(context, info).show();
	}

	private void openFile(String filePath) {
		progressDialog.dismiss();
		Utils.installAPK(context, filePath);
	}

	private void downLoad(VersionInfo obj) {
		try {
			float newVersion = Float.parseFloat(obj.getApkVersion());
			float oldVersion = Float.parseFloat(Utils.getAppVersion(context));
			if (obj.getApkUrl().equals("")) {
				initVersionDialog(obj.getApkUrl(), oldVersion, 0, "no");
				return;
			}
			if (newVersion <= oldVersion) {
				initVersionDialog(obj.getApkUrl(), oldVersion, 0, "no");
			} else {
				initVersionDialog(obj.getApkUrl(), oldVersion, newVersion, "update");
			}
		} catch (Exception e) {
			Utils.showToastBGNew(context, "获取版本号出错！");
			return;
		}
	}
	
	private void beginUpdate(String appUrl) {
		progressDialog = CommonDialog.getProgress(context, "", 100);
		progressDialog.setButton("取 消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				DownLoadFile.downFlag = false;
			}
		});
		progressDialog.setProgress(0);
		progressDialog.show();

		fileDownLoad(appUrl, Utils.getDownPath(), myHandler);
	}

	private void fileDownLoad(final String url, final String path, final Handler handler) {
		new Thread(new Runnable() {
			public void run() {
				String fileType = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase();
				DownLoadFile.starDownLoad(url, fileType, path, handler);
			}
		}).start();
	}

	public Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case DownLoadFile.SET_MAX_PROGRESS:
				showMyDialog((Integer) msg.obj);
				break;
			case DownLoadFile.SET_VALUE_PROGRESS:
				setProgressValue((Integer) msg.obj);
				break;
			case DownLoadFile.DOWNLOAD_SUCCESS:
				openFile((String) msg.obj);
//				flag = true;
				break;
			case DownLoadFile.DOWNLOAD_STOP:
				stop((String) msg.obj);
				break;
			default:
				break;
			}

		}
	};
	
	/**
	 * 
	 * 显示/隐藏退出登录的按钮。
	 * 
	 */
	private void showOrHideLogoutBtn() {
		// 如果用户已登录，显示退出登录的按钮
		if (isLogin()) {
			rlLogout.setVisibility(View.VISIBLE);

			// 如果用户未登录，隐藏退出登录的按钮
		} else {
			rlLogout.setVisibility(View.GONE);
		}
	}
}