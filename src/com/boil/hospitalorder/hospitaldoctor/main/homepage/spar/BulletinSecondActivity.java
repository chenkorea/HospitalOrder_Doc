package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragmentActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ChannelItem;
import com.boil.hospitalorder.utils.BaseTools;
import com.boil.hospitalorder.utils.ColumnHorizontalScrollView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BulletinSecondActivity extends BaseFragmentActivity {
	
	private BulletinSecondActivity context = BulletinSecondActivity.this;
	private ViewPager mViewPager;
	private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
	
	/** Item宽度 */
	private int mItemWidth = 0;
	private int mScreenWidth = 0;
	/** 当前选中的栏目*/
	private int columnSelectIndex = 0;
	/** 左阴影部分*/
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	RelativeLayout rl_column;
	
	/** 用户选择的新闻分类列表*/
	private ArrayList<ChannelItem> userChannelList=new ArrayList<ChannelItem>();
	private ImageView mTabLine;
	
	List<TextView> listTv = new ArrayList<TextView>();
	private LinearLayout loadView;
	private LinearLayout lay_all;
	
	/** 返回上一个 Activity 的按钮 */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView tvTitle;
	/** 保存按钮 */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	
	private String flag, hid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulletin_second_notice_main);
		volleyClient.setActivity(this);
		ViewUtils.inject(this);
		mScreenWidth = BaseTools.getWindowsWidth(this);
		initLayout();	
	}
	
	private void querySubList(String flag) {
//		http://localhost:8080/hsptapp/doctor/notice/lshsptnotice/401.html
		String hosIp = configSP.getString(Constants.HOSPITAL_LOGIN_ADD, "");
		String url = hosIp+"/doctor/notice/lshsptnotice/401.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
//		params.put("uid", "-1");
//		if("3".equals(flag)) {
//			params.put("spid", Constants.ANNOUNCEMENT_SPID);//2代表订阅，3代表公告，4代表通知
//		} else if("4".equals(flag)){
//			params.put("spid", Constants.NOTICE_SPID);
//			params.put("hid", hid);
//		}

		userChannelList.clear();
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						
						try {
							String resultCode = response.getString("resultCode");
							if ("1".equals(resultCode)) {
								lay_all.setVisibility(View.VISIBLE);
								String result = response.getString("t");
								JSONArray admArray = new JSONArray(result);
								for (int j = 0; j < admArray.length(); j++) {
									JSONObject subObj = admArray.getJSONObject(j);
									String subID = subObj.getString("id");
									String isSub = subObj.getString("isSub");
									String name = subObj.getString("name");
									ChannelItem itemVo = new ChannelItem(subID, name, 1, isSub);
									userChannelList.add(itemVo);
								}
								setChangelView();

							} else if("2".equals(resultCode)){
								LoadingUtils.showLoadMsg(loadView, "暂无公告数据");
							} else {
								LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						LoadingUtils.showLoadMsg(loadView, "查询订阅数据失败");
					}
				});

	}
	
	/**
	 * 初始化控件
	 */
	public void initLayout(){
		Utils.backClick(context, backBtn);
		saveBtn.setVisibility(View.INVISIBLE);
		
		flag = getIntent().getStringExtra("flag");
		hid = getIntent().getStringExtra("CoopHosId");
		if("3".equals(flag)) {
			tvTitle.setText("公告栏");
		} else if("4".equals(flag)) {
			tvTitle.setText("院内通知");
		}
		
		lay_all = (LinearLayout) findViewById(R.id.lay_all);
		lay_all.setVisibility(View.GONE);
		loadView = (LinearLayout) findViewById(R.id.loading_view_952658);
		mColumnHorizontalScrollView =  (ColumnHorizontalScrollView)findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
		rl_column = (RelativeLayout) findViewById(R.id.rl_column);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		shade_left = (ImageView) findViewById(R.id.shade_left);
		shade_right = (ImageView) findViewById(R.id.shade_right);
		mTabLine = (ImageView) findViewById(R.id.iv_tabline);
		
		querySubList(flag);
	}
	
	/** 
	 *  当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		initTabColumn();
		initFragment();
	}
	
	/** 
	 *  初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		if(Utils.isValidate(userChannelList)) {
			int count =  userChannelList.size();
			initTabLine(count);
			listTv.clear();
			mColumnHorizontalScrollView.setParam(context, mScreenWidth, mRadioGroup_content, shade_left, shade_right,null, rl_column);
			for(int i = 0; i< count; i++){
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth , LayoutParams.WRAP_CONTENT);
//			params.leftMargin = 5;
//			params.rightMargin = 5;
				
//			TextView localTextView = (TextView) mInflater.inflate(R.layout.column_radio_item, null);
				TextView columnTextView = new TextView(context);
				columnTextView.setTextAppearance(context, R.style.top_category_scroll_view_item_text);
//			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
//			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
				columnTextView.setGravity(Gravity.CENTER);
//			columnTextView.setPadding(5, 5, 5, 5);
				columnTextView.setId(i);
				columnTextView.setAlpha(0.8f);
				columnTextView.setText(userChannelList.get(i).getName());
				columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
				if(columnSelectIndex == i){
					columnTextView.setSelected(true);
					columnTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
				}
				columnTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						for(int i = 0;i < mRadioGroup_content.getChildCount();i++){
							View localView = mRadioGroup_content.getChildAt(i);
							if (localView != v){
								localView.setSelected(false);
								((TextView)localView).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
								
							}else{
								localView.setSelected(true);
								mViewPager.setCurrentItem(i);
								((TextView)localView).setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
							}
						}
					}
				});
				mRadioGroup_content.addView(columnTextView, i ,params);
				listTv.add(columnTextView);
			}
		}
	}

	/** 
	 * 初始化Fragment
	 **/
	private void initFragment() {
		mFragments.clear();//清空
		if(Utils.isValidate(userChannelList)) {
			int count =  userChannelList.size();
			for(int i = 0; i< count;i++){
				Bundle data = new Bundle();
				data.putSerializable("chnnelItem", userChannelList.get(i));
				BullettinListFragment f1 = new BullettinListFragment();
				mFragments.add(f1);
				f1.setArguments(data);
			}
			NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
			mViewPager.setOffscreenPageLimit(3);
			mViewPager.setAdapter(mAdapetr);
			mViewPager.setOnPageChangeListener(pageListener);
		}
	}
	
	/** 
	 *  选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
//		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
//		}
		View checkView1 = mRadioGroup_content.getChildAt(tab_postion);
		int k = checkView1.getMeasuredWidth();
		int l = checkView1.getLeft();
		int i2 = l + k / 2 - mScreenWidth / 2;
		// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
		mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
		// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
		// mItemWidth , 0);
		//判断是否选中
		for (int j = 0; j <  mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}
	
	/** 
	 *  ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener= new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int arg2) {
			RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) mTabLine
					.getLayoutParams();
			lp.leftMargin = (int) ((position + positionOffset) * mItemWidth);
			mTabLine.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			if(listTv != null && listTv.size() > 0) {
				listTv.get(position).setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
			}
			if(position == 0 && position < listTv.size()) {
				listTv.get(position + 1).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			} else if(position == listTv.size()-1) {
				listTv.get(position - 1).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			} else {
				listTv.get(position + 1).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
				listTv.get(position - 1).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			}
			selectTab(position);
		}
	};
	
	private void initTabLine(int count) {

		mItemWidth = mScreenWidth / count;
		LayoutParams lp = mTabLine.getLayoutParams();
		lp.width = mItemWidth;
		mTabLine.setLayoutParams(lp);
	}
	
	class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragments;
		private FragmentManager fm;

		public NewsFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		public NewsFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void setFragments(ArrayList<Fragment> fragments) {
			if (this.fragments != null) {
				FragmentTransaction ft = fm.beginTransaction();
				for (Fragment f : this.fragments) {
					ft.remove(f);
				}
				ft.commit();
				ft = null;
				fm.executePendingTransactions();
			}
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			Object obj = super.instantiateItem(container, position);
			return obj;
		}

	}
}
