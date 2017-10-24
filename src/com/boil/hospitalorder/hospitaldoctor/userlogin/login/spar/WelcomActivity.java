package com.boil.hospitalorder.hospitaldoctor.userlogin.login.spar;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment.Fragment1;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment.Fragment2;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment.Fragment3;
import com.boil.hospitalorder.hospitaldoctor.userlogin.login.fragment.ViewPagerAdapter;
import com.boil.hospitalorder.utils.Utils;

/**
 * 欢迎页
 * 
 * @author mengjiyong
 *
 */
public class WelcomActivity extends FragmentActivity {
	private ViewPager mViewPager;
	public SharedPreferences sp;
	public boolean isSet = false;
	
	private Fragment1 mFragment1;
	private Fragment2 mFragment2;
	private Fragment3 mFragment3;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();
	private PagerAdapter mPgAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
		initView();
	}
	private void initView() {
		isSet = getIntent().getBooleanExtra("Set", false);
		sp=Utils.getSharedPreferences(this);
		mFragment1 = new Fragment1();
		mFragment2 = new Fragment2();
		mFragment3 = new Fragment3();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		mViewPager.setAdapter(mPgAdapter);
		
	}
}
