package com.boil.hospitalorder.hospitaldoctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragmentActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter.BasePopAdapter;
import com.boil.hospitalorder.hospitaldoctor.main.mainiview.DocAndPatientCircleTabFragment;
import com.boil.hospitalorder.hospitaldoctor.main.mainiview.HomePageTabFragment;
import com.boil.hospitalorder.hospitaldoctor.main.mainiview.HomePageTabFragment.FindTabFragmentToActivity;
import com.boil.hospitalorder.hospitaldoctor.main.mainiview.MeTabSecondFragment;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment.ViewPagerAdapter;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginDeptVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.model.LoginInfoVo;
import com.boil.hospitalorder.hospitaldoctor.userlogin.register.spar.PerfectUserInfoCommitActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseFragmentActivity implements
		FindTabFragmentToActivity {
	
	//我擦，被你看到了
	/** 标题党 */
	private TextView tvTitle;
	public static ViewPager viewPager;
	private ViewPagerAdapter viewPagerAapter;

	private LinearLayout mHomeLinearLayout;
	private LinearLayout mFindLinearLayout;
	private LinearLayout mMeLinearLayout;

	private TextView mHomeTextView;
	private TextView mFindTextView;
	private TextView mMeTextView;
	private TextView tv_surgery;

	/**
	 * image
	 * */
	private TextView mHomeImageView;
	private TextView mFindImageView;
	private TextView mMeImageView;

	private ImageView mTabLine;

	private int widthScreen1_4;

	private RelativeLayout include_actionbar;

	private HomePageTabFragment homeTabFragment;
	private MeTabSecondFragment meTabFragment;
	private DocAndPatientCircleTabFragment circleFragment;
	
	private View v_order_trans;
	
	PopupWindow popWin;
	private LoginInfoVo infoVo;
	
	public static LoginDeptVo deptVo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//是否需要跳转到个人信息页面
		String isInfo=getIntent().getStringExtra("IsInfo");
		if("1".equals(isInfo)){
			Intent intent = new Intent(MainActivity.this, PerfectUserInfoCommitActivity.class);
			MyApplication.getInstance().addActivity(this);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
		
		deptVo=(LoginDeptVo) getIntent().getSerializableExtra("DeptVo");

		initView();
		initTabLine();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void initTabLine() {
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		widthScreen1_4 = displayMetrics.widthPixels / 3;

		LayoutParams lp = mTabLine.getLayoutParams();
		lp.width = widthScreen1_4;
		mTabLine.setLayoutParams(lp);
	}

	@SuppressWarnings("deprecation")
	private void initView() {

		tvTitle = (TextView) findViewById(R.id.tv_title);
		include_actionbar = (RelativeLayout) findViewById(R.id.include_actionbar);

		viewPager = (ViewPager) findViewById(R.id.id_viewpager);

		//text
		mHomeTextView = (TextView) findViewById(R.id.tv_home);//首页
		mFindTextView = (TextView) findViewById(R.id.tv_circle);
		mMeTextView = (TextView) findViewById(R.id.tv_me);
		tv_surgery = (TextView) findViewById(R.id.tv_surgery);
		v_order_trans = findViewById(R.id.v_order_trans);
		tv_surgery.setVisibility(View.VISIBLE);

		//image
		mHomeImageView = (TextView) findViewById(R.id.iv_home);
		mFindImageView = (TextView) findViewById(R.id.iv_circle);
		mMeImageView = (TextView) findViewById(R.id.iv_me);
		mHomeImageView.setTypeface(iconFont);
		mFindImageView.setTypeface(iconFont);
		mMeImageView.setTypeface(iconFont);
		
		mTabLine = (ImageView) findViewById(R.id.iv_tabline);

//		tvTitle.setText("首页");
		tvTitle.setText(deptVo.getName());
		
//		tv_surgery.setText(deptVo.getName());

		List<Fragment> list = new ArrayList<Fragment>();
		/*
		 * if (mainTabFragment == null) { mainTabFragment = new
		 * MainTabFragment(); }
		 */
		if (homeTabFragment == null) {
			homeTabFragment = new HomePageTabFragment();
		}
		if (meTabFragment == null) {
			meTabFragment = new MeTabSecondFragment();
		}
		if (circleFragment == null) {
			circleFragment = new DocAndPatientCircleTabFragment();
		}

		list.add(homeTabFragment);
		list.add(circleFragment);
		list.add(meTabFragment);

		viewPagerAapter = new ViewPagerAdapter(getSupportFragmentManager(),list);
		// 加上Fragment缓存
		viewPager.setOffscreenPageLimit(3);
		viewPager.setAdapter(viewPagerAapter);
		// viewPager.fakeDragBy(10);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextViewColor();
				resetImageViewSrc();
				switch (position) {
				case 0:
					tvTitle.setText(deptVo.getName());
					mHomeTextView.setTextColor(Color.parseColor("#008000"));
					mHomeImageView.setTextColor(Color.parseColor("#008000"));
//					mChatImageView.setImageResource(R.drawable.chat);
					include_actionbar.setVisibility(View.VISIBLE);
					tv_surgery.setVisibility(View.VISIBLE);
					break;
				case 1:
					tvTitle.setText("医院圈");
					mFindTextView.setTextColor(Color.parseColor("#008000"));
					mFindImageView.setTextColor(Color.parseColor("#008000"));
					include_actionbar.setVisibility(View.VISIBLE);
					tv_surgery.setVisibility(View.INVISIBLE);
					break;
				case 2:
					mMeTextView.setTextColor(Color.parseColor("#008000"));
					mMeImageView.setTextColor(Color.parseColor("#008000"));
					include_actionbar.setVisibility(View.GONE);
					tv_surgery.setVisibility(View.INVISIBLE);
					break;
				}
			}
			
		

			private void resetImageViewSrc() {
				mHomeImageView.setTextColor(Color.parseColor("#8F8F8F"));
				mFindImageView.setTextColor(Color.parseColor("#8F8F8F"));
				mMeImageView.setTextColor(Color.parseColor("#8F8F8F"));
			}

		
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffPx) {
				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
						.getLayoutParams();
				lp.leftMargin = (int) ((position + positionOffset) * widthScreen1_4);
				mTabLine.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		mHomeLinearLayout = (LinearLayout) findViewById(R.id.ll_home);
		mFindLinearLayout = (LinearLayout) findViewById(R.id.ll_circle);
		mMeLinearLayout = (LinearLayout) findViewById(R.id.ll_me);

		BottomLayoutListener listener = new BottomLayoutListener();
		mHomeLinearLayout.setOnClickListener(listener);
		mFindLinearLayout.setOnClickListener(listener);
		mMeLinearLayout.setOnClickListener(listener);
		tv_surgery.setOnClickListener(listener);

	}

	private void resetTextViewColor() {
		mHomeTextView.setTextColor(Color.parseColor("#A6A6A6"));
		mFindTextView.setTextColor(Color.parseColor("#A6A6A6"));
		mMeTextView.setTextColor(Color.parseColor("#A6A6A6"));
	}
	
	
	private void queryDept(){
		
		//http://localhost:8080/hsptapp/doctor/medlogin/initlogin/201.html?uname=20001&pwd=123456
		String url = "http://58.42.232.110:8086/hsptapp/doctor/medlogin/initlogin/201.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", configSP.getString(Constants.LOGIN_INFO_ID, ""));
		params.put("pwd", configSP.getString(Constants.LOGIN_INFO_PWD, ""));

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode = response.getString("resultCode");
							

							if ("1".equals(resultCode)) {
								
								String result=response.getString("t");
								infoVo=com.alibaba.fastjson.JSONObject.parseObject(result, LoginInfoVo.class);
								
								if(infoVo.getDepts().size()>0){
									
									getPop();
									
								}else {
									
									Utils.showToastBGNew(MainActivity.this, "查不到科室数据");
								}
								
							}else {
								Utils.showToastBGNew(MainActivity.this, "查不到科室数据");
							}
						} catch (JSONException e) {

							e.printStackTrace();
							Utils.showToastBGNew(MainActivity.this, "查询科室数据失败");
						}

					}

					@Override
					public void error(VolleyError error) {

						Utils.showToastBGNew(MainActivity.this, "查询科室数据失败");
					}
				});
	
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	class BottomLayoutListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_home:
				viewPager.setCurrentItem(0, true);
				include_actionbar.setVisibility(View.VISIBLE);
				tv_surgery.setVisibility(View.VISIBLE);
				break;
			case R.id.ll_circle:
				viewPager.setCurrentItem(1, true);
				include_actionbar.setVisibility(View.VISIBLE);
				tv_surgery.setVisibility(View.INVISIBLE);
				break;
			case R.id.ll_me:
				viewPager.setCurrentItem(2, true);
				include_actionbar.setVisibility(View.GONE);
				tv_surgery.setVisibility(View.INVISIBLE);
				break;
			case R.id.tv_surgery:
				queryDept();
//				getPop();
				break;
			}
		}
	}
	int barHeight = 0;
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus) {
			barHeight = include_actionbar.getBottom();
			barHeight += Utils.getStatusBarHeight(this);
		}
	}
	View view;
	ListView lv_surgery;
//	List<BasePopVo> listPop = new ArrayList<BasePopVo>();;
	BasePopAdapter popAdapter;
	public void getPop() {
		v_order_trans.setVisibility(View.VISIBLE);
		if(popWin == null) {
//			listPop.add(new BasePopVo("1", "外科一"));
//			listPop.add(new BasePopVo("2", "外科二"));
			
			view = LayoutInflater.from(this).inflate(R.layout.base_popwindow_dialog, null);
			lv_surgery = (ListView) view.findViewById(R.id.list_order_hos);
			popAdapter = new BasePopAdapter(this);
			lv_surgery.setAdapter(popAdapter);
			
			if(infoVo.getDepts().size()>4){
				
				popWin = new PopupWindow(view,LayoutParams.MATCH_PARENT, Utils.dip2px(MainActivity.this, 210));
			}else {
				
				popWin = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			}
			popWin.setBackgroundDrawable(new BitmapDrawable());
			popWin.setAnimationStyle(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
			popWin.setOutsideTouchable(true);
			popWin.setFocusable(true);
			popWin.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss() {
					v_order_trans.setVisibility(View.GONE);
				}
			});
			lv_surgery.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					deptVo.setHisId(infoVo.getDepts().get(arg2).getHisId());
					deptVo.setName(infoVo.getDepts().get(arg2).getName());
					deptVo.setSupervise(infoVo.getDepts().get(arg2).getSupervise());
					
					tvTitle.setText(deptVo.getName());
					popWin.dismiss();
				}
			});
		}
		popAdapter.setList(infoVo.getDepts());
		popWin.showAtLocation(include_actionbar, Gravity.TOP|Gravity.RIGHT, 0, barHeight);
		popWin.update();
	}

	@Override
	public void toActivity() {

	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	           MyApplication.getInstance().exit();
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
