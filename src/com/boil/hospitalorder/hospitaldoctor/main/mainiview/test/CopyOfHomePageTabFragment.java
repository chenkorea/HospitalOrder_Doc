package com.boil.hospitalorder.hospitaldoctor.main.mainiview.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseFragment;
import com.boil.hospitalorder.utils.MyListView;
import com.boil.hospitalorder.utils.MyScrollView;
import com.boil.hospitalorder.utils.MyScrollView.OnScrollListener;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;

/**
 * 查询页面
 * 
 * @author ct
 * 
 */
@SuppressLint("NewApi")
public class CopyOfHomePageTabFragment extends BaseFragment implements
		OnScrollListener {

	FindTabFragmentToActivity findTabFragmentToActivity;
	private MyScrollView myScrollView;
	private int searchLayoutTop;

	private int searchLayoutTop2;
	private int searchLayoutTop3;
	private MyListView listview;
	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	LinearLayout search01, search02, search03, search04, search05, search06;
	RelativeLayout rlayout;

	private TextView tv_title;
	private TextView tv_title2;
	private TextView tv_title3;
	private TestAdapter adapter;

	private TestAdapter adapter2;

	private GridView gridView;

	private MyListView listview2;

	private GridViewAdapter adapter3;
	VolleyClient volleyClient;

	public interface FindTabFragmentToActivity {
		public void toActivity();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home_page2, container,
				false);
		volleyClient = new VolleyClient(getActivity());
		volleyClient.setActivity(getActivity());
		initData();
		initView(view);
		initEvent();
		queryCooperHosList(2);
		return view;
	}

	private void initData() {

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		queryCooperHosList(1);
		
	}

	private void initView(View view) {
		listview = (MyListView) view.findViewById(R.id.listview);
		listview2 = (MyListView) view.findViewById(R.id.listview2);
		gridView = (GridView) view.findViewById(R.id.grid);
		tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
		tv_title3 = (TextView) view.findViewById(R.id.tv_title3);
		tv_title = (TextView)view.findViewById(R.id.tv_title);
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("条目" + i);
		}
		/*for (int i = 0; i < 20; i++) {
			list2.add("条目" + i);
		}*/
		
		
		rlayout = (RelativeLayout) view.findViewById(R.id.sty_lay);
		myScrollView = (MyScrollView) view.findViewById(R.id.myScrollView);
		search01 = (LinearLayout) view.findViewById(R.id.search01);
		search02 = (LinearLayout) view.findViewById(R.id.search02);
		search03 = (LinearLayout) view.findViewById(R.id.search03);
		search04 = (LinearLayout) view.findViewById(R.id.search04);
		search05 = (LinearLayout) view.findViewById(R.id.search05);
		search06 = (LinearLayout) view.findViewById(R.id.search06);
		myScrollView.setOnScrollListener(this);
		setGridView();

	}

	/**
	 * 查询合作医院列表
	 */
	
	private void queryCooperHosList(final int type){
		if(isFirst) {
			ViewTreeObserver vto1 = listview2.getViewTreeObserver();
			if(!vto1.isAlive()) {
				vto1.addOnGlobalLayoutListener(new MyOnGlobal(vto1, 2));
			}
			isFirst = false;
		}
		
		String url = "http://58.42.232.110:8086/hsptapp/hpin/listhspt/1001.html";
		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("state", "1");
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null, new VolleyListener<JSONObject>() {
			
			
			@Override
			public void success(JSONObject response) {
				
				try {
					
					String resultCode=response.getString("resultCode");
					if("1".equals(resultCode)){
						String result=response.getString("t");
						List<CooperationHosVo> vosTemp = JSONArray.parseArray(result, CooperationHosVo.class);
//						updateAdapter(vosTemp, loadType);
						if(type == 1) {
							height2 = getHeightLv2();
							adapter = new TestAdapter(list2, getActivity());
							listview.setAdapter(adapter);
						} else {
							height1 = getHeightLv1();
							adapter2 = new TestAdapter(list, getActivity());
							listview2.setAdapter(adapter2);
						}
						
					}else if("2".equals(resultCode)){
						
						Utils.showToastBGNew(getActivity(), "暂无合作医院数据");
					}else if("3".equals(resultCode)){
						Utils.showToastBGNew(getActivity(), "用户未登录");
						
					}else {
						Utils.showToastBGNew(getActivity(), "查询合作医院数据失败");
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				
			}
			
			@Override
			public void error(VolleyError error) {
				
				Utils.showToastBGNew(getActivity(), "查询合作医院数据失败");
			}
		});
		
	}

	 /**设置GirdView参数，绑定数据*/
   private void setGridView() {
       int size = list.size();
       int length = 150;
       DisplayMetrics dm = new DisplayMetrics();
       getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
       float density = dm.density;
       int gridviewWidth = (int) (size * (length) * density);
       int itemWidth = (int) (length * density);

       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
       gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
       gridView.setColumnWidth(itemWidth); // 设置列表项宽
       gridView.setHorizontalSpacing(5); // 设置列表项水平间距
       gridView.setStretchMode(GridView.NO_STRETCH);
       gridView.setNumColumns(size); // 设置列数量=列表集合数
       gridView.setBackgroundColor(getResources().getColor(R.color.actionsheet_blue));

       adapter3 = new GridViewAdapter(getActivity(),
               list);
       gridView.setAdapter(adapter3);
   }
	
	private void initEvent() {
		
		
	}
	
	boolean isFirst = true;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		 ViewTreeObserver vto = rlayout.getViewTreeObserver();
		    vto.addOnGlobalLayoutListener(new MyOnGlobal(vto, 1));
		    
		    ViewTreeObserver vto2 = listview.getViewTreeObserver();
		    vto2.addOnGlobalLayoutListener(new MyOnGlobal(vto2, 2));
		    
		    ViewTreeObserver vto3 = gridView.getViewTreeObserver();
		    vto3.addOnGlobalLayoutListener(new MyOnGlobal(vto3, 3));
	
		
	}
	
	public int getHeightLv1() {
	    if (adapter == null) { 
	        return 0; 
	    } 
		View listItem = adapter.getView(0, null, listview); 
		listItem.measure(0, 0); 
		return listItem.getMeasuredHeight() * list2.size() + Utils.dip2px(getActivity(), 0.5f) * list.size() + 1; 
	}
	int height2 = 0;
	int height1 = 0;
	public int getHeightLv2() {
	    if (adapter3 == null) { 
	        return 0; 
	    } 
		View listItem = adapter3.getView(0, null, gridView); 
		listItem.measure(0, 0); 
		return listItem.getMeasuredHeight(); 
	}

	class MyOnGlobal implements OnGlobalLayoutListener{
		
		private ViewTreeObserver obser;
		private int flag;
		public MyOnGlobal(ViewTreeObserver obser, int flag) {
			this.obser = obser;
			this.flag = flag;
		}

		@Override
		public void onGlobalLayout() {
			if(flag == 1) {
				 searchLayoutTop = rlayout.getBottom()- Utils.dip2px(getActivity(), 30);// 获取searchLayout的顶部位置 
		            // 成功调用一次后，移除 Hook 方法，防止被反复调用
		            // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
		            // 使用新方法 removeOnGlobalLayoutListener() 代替
				 if(searchLayoutTop != 0) {
					 obser.removeOnGlobalLayoutListener(this);
				 }
				  
			} else if(flag == 2) {
				searchLayoutTop2 = searchLayoutTop + height2 + Utils.dip2px(getActivity(), 40) ;
				 if(list.size() != 0) {
					 obser.removeOnGlobalLayoutListener(this);
				 } 
			} else if(flag == 3) {
				searchLayoutTop3 = searchLayoutTop2 + height1;
				 if(list2.size() != 0) {
					 obser.removeOnGlobalLayoutListener(this);
				 }
			}
			 Utils.showToastBGNew(getActivity(),searchLayoutTop  +"  " + getHeightLv2() +" 11 "+ getHeightLv1());
			
		}
	}
	
	boolean isFinish = false;
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		findTabFragmentToActivity = (FindTabFragmentToActivity) activity;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onScroll(int scrollY) {

		if(scrollY >= searchLayoutTop3) {
			search01.removeAllViews();
			search05.removeAllViews();
			if (tv_title3.getParent()!=search06) {
				search04.removeView(tv_title3);
				search06.addView(tv_title3);
			}
		} else {
			if (tv_title3.getParent()!=search04) {
				search06.removeView(tv_title3);
        		if(search04.getChildCount() <= 0) {
        			search04.addView(tv_title3);
        		}
			}
			
			if(scrollY >= searchLayoutTop2) {
				search01.removeAllViews();
				if (tv_title2.getParent()!=search05) {
					search03.removeView(tv_title2);
					search05.addView(tv_title2);
				}
			} else {
				if (tv_title2.getParent()!=search03) {
	        		search05.removeView(tv_title2);
	        		if(search03.getChildCount() <= 0) {
	        			search03.addView(tv_title2);
	        		}
				}
				
				if(scrollY >= searchLayoutTop){  
					if (tv_title.getParent()!=search01) {
						search02.removeView(tv_title);
						search01.addView(tv_title);
					}
		        }else{
		        	if (tv_title.getParent()!=search02) {
		        		search01.removeView(tv_title);
		        		if(search02.getChildCount() <= 0) {
		        			search02.addView(tv_title);
		        		}
					}
		        }
			}
			
		}
	}
	
	/**GirdView 数据适配器*/
    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<String> list;
        public GridViewAdapter(Context _context, List<String> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.gridview_home_item, null);
            TextView tvCity = (TextView) convertView.findViewById(R.id.tv);

            tvCity.setText(list.get(position));
            return convertView;
        }
    }

}
