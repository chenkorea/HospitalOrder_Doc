package com.boil.hospitalorder.hospitaldoctor.my.spar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.adapter.CTDiningGridViewAdapter;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodCartVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.RefreshRecordVo;
import com.boil.hospitalorder.hospitaldoctor.my.modal.StaffDiningVo;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.LoadingUtils;
import com.boil.hospitalorder.utils.NumberUtils;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StaffDiningOrderActivity extends BaseBackActivity {

	public static final String IMAGE_URL="http://58.42.232.110:8086/hsptapp-web";
	
	private StaffDiningOrderActivity context = StaffDiningOrderActivity.this;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.bt_save)
	private TextView bt_save;

	private CTDiningGridViewAdapter adapter;

	/** 支付 */
	@ViewInject(R.id.btn_bespoke_net)
	private BootstrapButton btnPay;
	/** 订餐车 */
	@ViewInject(R.id.get_captcha_btn)
	private BootstrapButton btnFoodCart;

	@ViewInject(R.id.gridview)
	private PullToRefreshGridView gridView;
	/** 类别布局 */
	@ViewInject(R.id.ll_sub_filter)
	private LinearLayout ll_filter;
	/** 菜类 */
	@ViewInject(R.id.tv_food_type)
	private TextView tv_food_type;
	/** 口味 */
	@ViewInject(R.id.tv_taste)
	private TextView tv_taste;
	/** 排序 */
	@ViewInject(R.id.tv_food_order)
	private TextView tv_food_order;
	/** 过滤菜单 */
	@ViewInject(R.id.list_filter)
	private ListView listFilter;
	/** 透明背景层 */
	@ViewInject(R.id.v_order_trans)
	private View transView;
	/** 菜类 */
//	private List<FoodFilterVo> filterVos1 = new ArrayList<FoodFilterVo>();
//	/** 口味 */
//	private List<FoodFilterVo> filterVos2 = new ArrayList<FoodFilterVo>();
	/** 排序 */
	private List<FoodVo> filterVos3 = new ArrayList<FoodVo>();

	private FilterAdapter filterAdapter;

	private int countClickFilter1 = 0;
	private int countClickFilter2 = 0;
	private int countClickFilter3 = 0;
	/** 当前点击的过滤项目
	 * 1-菜别
	 * 2-口味
	 * 3-排序
	 *  */
	private int currentFilter = 0;
	private FoodVo currentType=null;
	private FoodVo currentTaste=null;
	private FoodVo currentOrder=null;
	private int currentPosition1=0;
	private int currentPosition2=0;
	private int currentPosition3=0;

	//用来接收上个页面
	/** 菜的类别 */
//	private List<FoodVo> foodTypeVos = new ArrayList<FoodVo>();
//	/** 口味 */
//	private List<FoodVo> foodTasteVos = new ArrayList<FoodVo>();
	
	
	/**菜單*/
	private List<StaffDiningVo> vos = new ArrayList<StaffDiningVo>();
//	/**用来进行菜单过滤的list*/
	private List<StaffDiningVo> tempOrder = new ArrayList<StaffDiningVo>();
	
	/**订餐车*/
	public static List<FoodCartVo> cartVos=new ArrayList<FoodCartVo>();

	private String teenId;
	
	private String hid;
	/**==================ct=======================*/
	List<FoodVo> foodTypeVos;
	List<FoodVo> foodTasteVos;
	RefreshRecordVo pageNumVo = null;
	@ViewInject(R.id.loading_view_952658)
	private LinearLayout loadView;
	private boolean isFirst = true;
	private AlertDialog payDialog;
	private FoodCartVo dialogCartVo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colligate_service_dining_order_main);
		ViewUtils.inject(this);
		initView();
		initData();
		initEvent();
	}

	public void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("职工菜单");
		bt_save.setVisibility(View.INVISIBLE);

		ll_filter.setVisibility(View.GONE);
		volleyClient.setActivity(context);
		initGridView();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initGridView() {
		gridView.setMode(Mode.BOTH);  
        gridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");  
        gridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");  
        gridView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载"); 
        gridView.getLoadingLayoutProxy(false, true).setLoadingDrawable(getResources().getDrawable(R.drawable.rightrotation));
        gridView.getLoadingLayoutProxy(true, false).setLoadingDrawable(getResources().getDrawable(R.drawable.rightrotation));
       
        gridView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				queryFoodData2(0);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pullUpToRe();
				
			}
		});
        
        adapter = new CTDiningGridViewAdapter(context, iconFont2);
		gridView.setAdapter(adapter);
	}
	
	private void pullUpToRe() {
		if(pageNumVo == null || pageNumVo.getCurrentNum() == pageNumVo.getTotalPageNum()) {
			Utils.showToastBGNew(context, "没有更多的菜了");
			gridView.onRefreshComplete();
			return;
		}
		queryFoodData2(1);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		calculateTotal();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		cartVos.clear();
	}
	
	public void initData() {

		teenId=getIntent().getStringExtra("TeenId");
		hid=getIntent().getStringExtra("hid");
		foodTypeVos = (List<FoodVo>) getIntent().getSerializableExtra("FoodType");
		foodTasteVos = (List<FoodVo>) getIntent().getSerializableExtra("FoodTaste");

		if(foodTypeVos!=null&&foodTypeVos.size()>0){
			
			//菜的类别
//			for (FoodVo vo : foodTypeVos) {
//				filterVos1.add(new FoodFilterVo(vo.getDictName(), false));
//			}
			//默认选中第一项
			foodTypeVos.get(0).setIsSelect(true);
			currentType=foodTypeVos.get(0);
			
		}
		
		
		if(foodTasteVos!=null&&foodTasteVos.size()>0){
			
			//口味
//			for (FoodVo vo : foodTasteVos) {
//				filterVos2.add(new FoodFilterVo(vo.getDictName(), false));
//			}
			//默认选择第一项
			foodTasteVos.get(0).setIsSelect(true);
			currentTaste=foodTasteVos.get(0);
		}
		
		

		filterVos3.add(new FoodVo("","默认排序", true));
		filterVos3.add(new FoodVo("0","时间排序", false));
		filterVos3.add(new FoodVo("1","价格排序", false));
		currentOrder=filterVos3.get(0);

		filterAdapter = new FilterAdapter(foodTypeVos);
		listFilter.setAdapter(filterAdapter);
		
		queryFoodData2(0);

		/*
		String[] imageUrls = new String[] { "http://img0.imgtn.bdimg.com/it/u=616698954,2396417076&fm=21&gp=0.jpg",
				"http://img1.imgtn.bdimg.com/it/u=1657246505,1863652736&fm=21&gp=0.jpg",
				"http://img3.imgtn.bdimg.com/it/u=350917429,3461714318&fm=21&gp=0.jpg",
				"http://img1.imgtn.bdimg.com/it/u=4258946031,1986240104&fm=21&gp=0.jpg",
				"http://img0.imgtn.bdimg.com/it/u=3791269712,2928549338&fm=21&gp=0.jpg",
				"http://img0.imgtn.bdimg.com/it/u=3189013388,3512083562&fm=11&gp=0.jpg" };
		String[] diningNames = new String[] { "辣子鸡丁", "莴笋炒肉", "糖醋排骨", "鱼香肉丝", "酱爆龙虾", "干锅牛肉" };

		List<StaffDiningVo> vos = new ArrayList<StaffDiningVo>();
		StaffDiningVo vo = null;
		for (int i = 0; i < 6; i++) {
			vo = new StaffDiningVo(i + "", imageUrls[i], diningNames[i], "45");
			vos.add(vo);
		}

		adapter = new CTDiningGridViewAdapter(vos, context, iconFont2);
		gridView.setAdapter(adapter);*/
	}

	/**
	 * 初始化操作
	 */
	private void clearData(){
		ll_filter.setVisibility(View.GONE);
		transView.setVisibility(View.GONE);
		countClickFilter1 = 0;
		countClickFilter2 = 0;
		countClickFilter3 = 0;
	}
	/**
	 * 控制过滤列表的隐藏和显示
	 * @param list
	 * @param filter
	 */
	private void setFilterData(List<FoodVo> list, int filter) {

		//重复点击 隐藏选择菜单
		if (filter == 1) {
			countClickFilter1++;
			countClickFilter2 = 0;
			countClickFilter3 = 0;
			if (countClickFilter1 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter1 = 0;
				return;
			}
		}
		if (filter == 2) {
			countClickFilter2++;
			countClickFilter1 = 0;
			countClickFilter3 = 0;
			if (countClickFilter2 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter2 = 0;
				return;
			}
		}
		if (filter == 3) {
			countClickFilter3++;
			countClickFilter1 = 0;
			countClickFilter2 = 0;
			if (countClickFilter3 == 2) {
				ll_filter.setVisibility(View.GONE);
				transView.setVisibility(View.GONE);
				countClickFilter3 = 0;
				return;
			}
		}

		currentFilter = filter;

		ll_filter.setVisibility(View.VISIBLE);
		transView.setVisibility(View.VISIBLE);

		filterAdapter.setList(list);
		listFilter.setAdapter(filterAdapter);
		filterAdapter.notifyDataSetChanged();
		
		//listview 滑动到指定位置
		if(filter==1){
			
			listFilter.setSelection(currentPosition1);
		}
		if(filter==2){
			
			listFilter.setSelection(currentPosition2);
		}
		if(filter==3){
			
			listFilter.setSelection(currentPosition3);
		}

	}
	
	/**
	 * 添加订餐车
	 */
	private void addFoodCart(int position){
		
		boolean isExist=false;
		
		StaffDiningVo vo=tempOrder.get(position);
		
		String id=vo.getId();
		String foodName=vo.getDishName();
		String foodDesc=vo.getDishDesc();
		String foodPrice=vo.getDishPrice();
		String foodImage=vo.getDishLogoPath();
		String makeTime=vo.getMakeTime();
		
		
		if(cartVos.size()>0){
		
			
			for(FoodCartVo cartVo:cartVos){
				
				//如果该菜已经在购物车，则数量+1
				if(cartVo.getId().equals(id)){
					
					int num=Integer.valueOf(cartVo.getNum())+1;
					cartVo.setNum(num+"");
					isExist=true;
					break;
				}
				
			}
			//购物车不存在该菜   直接添加
			if(!isExist){
				
				cartVos.add(new FoodCartVo(id, foodName, foodDesc, foodPrice, foodImage, true, "1","",makeTime));
			}
			
			
		//订餐车为空，直接添加
		}else {
			
			cartVos.add(new FoodCartVo(id, foodName, foodDesc, foodPrice, foodImage, true, "1","",makeTime));
			
		}
		
		calculateTotal();
		
	}
	
	/**
	 * 计算总价格
	 */
	private void calculateTotal(){
		//计算总价格和数量并更新到界面
		
		//每一种菜的数量
		int num = 0;
		//总价格
		double price = 0;
		
		List<FoodCartVo> temp=new ArrayList<FoodCartVo>();
		
		for (FoodCartVo cartVo : cartVos) {

			if(cartVo.isChecked()&&!"0".equals(cartVo.getNum())){
				
				num = num + Integer.valueOf(cartVo.getNum());
				price = price + Integer.valueOf(cartVo.getNum()) * Double.valueOf(cartVo.getFoodPrice());
			}else {
				
				temp.add(cartVo);
			}
		}
		
		//删除取消选择的菜
		cartVos.removeAll(temp);


		String priceStr = String.format("总价格%s", NumberUtils.formatPrice(price));
		String numStr = String.format("订餐车(%s)", num + "");

		btnPay.setText(priceStr);
		btnFoodCart.setText(numStr);
	}
	
	private void initEvent() {

		// 支付
		btnPay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 订餐车
		btnFoodCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(cartVos.size()==0){
					Utils.showToastBGNew(context, "订餐车为空");
				}else {
					
					
					Intent intent = new Intent(context, FoodCartActivity.class);
					intent.putExtra("teenId", teenId);
					intent.putExtra("hid", hid);
					startActivity(intent);
					overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
				
			}
		});
		
		// 菜别
		tv_food_type.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setFilterData(foodTypeVos, 1);
			}
		});
		// 口味
		tv_taste.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFilterData(foodTasteVos, 2);
			}
		});
		// 排序
		tv_food_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setFilterData(filterVos3, 3);
			}
		});

		transView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearData();
			}
		});

		listFilter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				initFilterData(arg2);
				clearData();
				queryFoodData2(0);
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				addFoodCart(arg2);
				showAddDialog(arg2);
				
			}
		});
	}
	
	/**
	 * 根据菜单列表菜的id获取订餐车相对应的菜
	 * @param id
	 * @return
	 */
	private FoodCartVo getCartVo(String id) {

		for (FoodCartVo vo : cartVos) {
			if (id.equals(vo.getId())) {
				return vo;
			}
		}
		return null;
	}
	
	private void showAddDialog(final int position){

		
		if (payDialog == null) {
			payDialog = new AlertDialog.Builder(context).create();
			
			payDialog.show();
			
		} else {
			payDialog.show();
		}
		
		Window window = payDialog.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		LayoutParams params = window.getAttributes();
		params.width = Utils.getScreenWidth(context)-Utils.dip2px(context, 26);
		window.setAttributes(params);
		window.setContentView(R.layout.layout_food_choose);
		
		ImageView imageView=(ImageView) window.findViewById(R.id.iv_cart_img);
		TextView name=(TextView) window.findViewById(R.id.tv_cart_name);
		TextView price=(TextView) window.findViewById(R.id.tv_cart_price);
		TextView desc=(TextView) window.findViewById(R.id.tv_desc);
		final EditText remark=(EditText) window.findViewById(R.id.et_remark);
		AmountView amountView=(AmountView) window.findViewById(R.id.amountView);
		BootstrapButton sure=(BootstrapButton) window.findViewById(R.id.btn_sure);
		amountView.setGoods_storage(Integer.MAX_VALUE);
		amountView.setZeroFlag(true);
		
		//设置该菜的信息
		final StaffDiningVo diningVo=tempOrder.get(position);
		ImageLoader.getInstance().displayImage(StaffDiningOrderActivity.IMAGE_URL+diningVo.getDishLogoPath(), imageView);
		name.setText(diningVo.getDishName());
		price.setText("￥"+diningVo.getDishPrice());
		desc.setText(diningVo.getDishDesc());
		
		
		dialogCartVo=getCartVo(diningVo.getId());
		if(dialogCartVo!=null){
			
			amountView.setGoods_value(dialogCartVo.getNum());
			remark.setText(dialogCartVo.getRemark());
		}
		
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//保存该菜的备注
				if(dialogCartVo!=null){
					
					dialogCartVo.setRemark(remark.getText().toString());
				}
				
				payDialog.dismiss();
			}
		});
		
		amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
			@Override
			public void onAmountChange(View view, int amount) {
				
				dialogCartVo=getCartVo(diningVo.getId());
				if(dialogCartVo!=null){
					dialogCartVo.setNum(amount+"");
					calculateTotal();
				}else {
					addFoodCart(position);
				}
				
			}
		});
	
	}

	private void initFilterData(int position){
		
		
		if (currentFilter == 1) {

			FoodVo filterVo = foodTypeVos.get(position);
			
			//设置为已选择项   以便改变背景颜色
			for (FoodVo vo : foodTypeVos) {

				if (vo == filterVo) {
					vo.setIsSelect(true);
				} else {
					vo.setIsSelect(false);
				}
			}
			//记录当前位置
			currentPosition1=position;
			//记录当前菜别
			currentType=filterVo;
			//显示当前菜别
			tv_food_type.setText(filterVo.getDictName());

		}
		if (currentFilter == 2) {

			FoodVo filterVo = foodTasteVos.get(position);
			for (FoodVo vo : foodTasteVos) {

				if (vo == filterVo) {

					vo.setIsSelect(true);
				} else {
					vo.setIsSelect(false);
				}

			}
			currentPosition2=position;
			currentTaste=filterVo;
			tv_taste.setText(filterVo.getDictName());

		}
		if (currentFilter == 3) {

			FoodVo filterVo = filterVos3.get(position);
			for (FoodVo vo : filterVos3) {

				if (vo == filterVo) {

					vo.setIsSelect(true);
				} else {
					vo.setIsSelect(false);
				}

			}
			currentPosition3=position;
			currentOrder=filterVo;
			tv_food_order.setText(filterVo.getDictName());

		}

		if (filterAdapter != null) {

			filterAdapter.notifyDataSetChanged();
			
			
		}
	}
	
	/**
	 * 过滤菜单
	 */
/*	private void switchFilterData() {
		
		
		tempOrder.clear();
	
		if(currentFilter==3){
			
			//默认排序
			if(currentPosition3==0){
				
				queryOrderData("");
				
			}else {
				
				queryOrderData((currentPosition3-1)+"");
				
			}
		}else {
			
			
			doFilter();
		}
		
		
	}*/
	
	
	
	/*private void doFilter(){
		
		//不需要过滤
		if(currentPosition1==0&&currentPosition2==0){
			
			tempOrder.addAll(vos);
		
			
		//过滤口味
		}else if(currentPosition1==0){
			
			for(StaffDiningVo vo:vos){
				
				if(vo.getTaste().equals(filterVos2.get(currentPosition2).getName())){
					
					tempOrder.add(vo);
				}
				
				
			}
			
		//过滤菜别
		}else if(currentPosition2==0){
			
			for(StaffDiningVo vo:vos){
				
				if(vo.getCookType().equals(filterVos1.get(currentPosition1).getName())){
					
					tempOrder.add(vo);
				}
				
				
			}
			
		
		//过滤菜别和口味
		}else {
			for(StaffDiningVo vo:vos){
				
				if(vo.getCookType().equals(filterVos1.get(currentPosition1).getName())&&vo.getTaste().equals(filterVos2.get(currentPosition2).getName())){
					
					tempOrder.add(vo);
				}
				
				
			}
			
		}
		
		adapter.setVos(tempOrder);
		adapter.notifyDataSetChanged();
		
	}*/
	
	
/*	*//**
	 * 排序
	 * @param order 0-按时间(降序)  1-按价格(升序)
	 *//*
	private void queryOrderData(String order){
		
		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/dish/list.json

		String url =  Constants.WEB_DINNER_URL + "/canteen/dish/list.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNum", "1");
		params.put("canteenId", teenId);
		params.put("hospitalId", hid);
		params.put("orderBy", order);

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						
						try {
							String resultCode = response.getString("resultCode");
							if ("0".equals(resultCode)) {

								String results = response.getString("results");
								JSONArray dataArray = new JSONArray(results);

								vos = com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4),StaffDiningVo.class);

								if (vos.size() > 0) {
									
									doFilter();
									
								} else {
									Utils.showToastBGNew(context, "暂无该食堂菜单数据");
								}

							} else {

								Utils.showToastBGNew(context, "查询菜单数据失败");

							}

						} catch (Exception e) {
							e.printStackTrace();
							Utils.showToastBGNew(context, "查询菜单数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询菜单数据失败");
					}
				});
		
	}*/
	
	
	
	

/*	private void queryFoodData(){
		
		
		String json="{\"resultCode\":0,\"resultMsg\":\"成功\",\"result\":null,\"results\":[1,1,1,1,[{\"id\":2,\"webUserId\":2,\"dishName\":\"宫爆鸡丁\",\"dishType\":\"川菜\",\"effect\":\"滋补养生\",\"taste\":\"辣\",\"cookType\":\"爆炒\",\"dishLogoPath\":\"/upload/pic/dish/1C592E45460E4A47A1BC9DA2106119D7.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/16084BB06972426EAC7A9694322E4BB3.jpg\",\"/upload/pic/dish/A37B2FC9757C431F94B9697700270460.jpg\"],\"dishPrice\":20.9,\"dishDesc\":\"超级好吃，不是很辣。\",\"remark\":\"支持外送。\",\"createTime\":\"2016-10-25\",\"updateTime\":\"2016-11-04\"},{\"id\":1,\"webUserId\":2,\"dishName\":\"爆炒小鸡\",\"dishType\":\"东北菜\",\"effect\":\"增强抵抗力\",\"taste\":\"辣\",\"cookType\":\"爆炒\",\"dishLogoPath\":\"/upload/pic/dish/1C592E45460E4A47A1BC9DA2106119D7.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/A37B2FC9757C431F94B9697700270460.jpg\"],\"dishPrice\":30.5,\"dishDesc\":\"非常好吃，超级辣。\",\"remark\":\"我们的特色菜。\",\"createTime\":\"2016-10-25\",\"updateTime\":\"2016-11-03\"},{\"id\":3,\"webUserId\":2,\"dishName\":\"青椒肉丝盖饭\",\"dishType\":\"其他\",\"effect\":\"滋补养生\",\"taste\":\"清淡\",\"cookType\":\"炒菜\",\"dishLogoPath\":\"/upload/pic/dish/D80FC7CDB3134DBCB854923C977306B6.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/096AC2131BA14DE995B5C706632582BD.jpg\",\"/upload/pic/dish/3981F112040F4775BD341DF7DFF638A3.jpg\"],\"dishPrice\":15.0,\"dishDesc\":\"这是盖饭，一个人吃一盘就足够了，非常好吃，有利于养胃，不辣，很清淡。\",\"remark\":\"非常好吃。\",\"createTime\":\"2016-10-25\",\"updateTime\":\"2016-11-02\"},{\"id\":5,\"webUserId\":2,\"dishName\":\"干锅鸡\",\"dishType\":\"贵州菜\",\"effect\":\"抗衰老\",\"taste\":\"辣\",\"cookType\":\"干锅\",\"dishLogoPath\":\"/upload/pic/dish/C0CBC931FAA24476BDB18597458BBE40.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/9EA453DA93FB42B685880E5D6910DD3F.jpg\",\"/upload/pic/dish/86606DCD559E44D0AC911402FA2E7710.jpg\"],\"dishPrice\":50.0,\"dishDesc\":\"配菜：白萝卜，大白菜，土豆，豆腐，小青菜，西兰花等。\",\"remark\":\"2-4人用餐。\",\"createTime\":\"2016-10-26\",\"updateTime\":\"2016-11-01\"},{\"id\":4,\"webUserId\":2,\"dishName\":\"青椒肉丝\",\"dishType\":\"湘菜\",\"effect\":\"促进消化\",\"taste\":\"清淡\",\"cookType\":\"炒菜\",\"dishLogoPath\":\"/upload/pic/dish/D80FC7CDB3134DBCB854923C977306B6.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/3981F112040F4775BD341DF7DFF638A3.jpg\"],\"dishPrice\":15.0,\"dishDesc\":\"这是盖饭，一个人吃一盘就足够了，非常好吃，有利于养胃，不辣，很清淡。\",\"remark\":\"外送，非常好吃。\",\"createTime\":\"2016-10-25\",\"updateTime\":\"2016-11-01\"},{\"id\":6,\"webUserId\":2,\"dishName\":\"肉末粉\",\"dishType\":\"贵州菜\",\"effect\":\"滋补养生\",\"taste\":\"清淡\",\"cookType\":\"肉末粉\",\"dishLogoPath\":\"/upload/pic/dish/8AAA3A61FF524AF4A8775D4B7A1F4DCA.jpg\",\"dishPicPaths\":[\"/upload/pic/dish/1DA5A18C77D9437D80B8225F43C7ED7E.jpg\"],\"dishPrice\":7.0,\"dishDesc\":\"好吃的肉末粉，贵阳特色菜。\",\"remark\":\"量多多，支持外送哦。\",\"createTime\":\"2016-10-28\",\"updateTime\":\"2016-11-01\"},{\"id\":7,\"webUserId\":2,\"dishName\":\"肉末面\",\"dishType\":\"贵州菜\",\"effect\":\"疾病防治\",\"taste\":\"咸鲜\",\"cookType\":\"肉末面\",\"dishLogoPath\":\"/upload/pic/dish/1F9CA1378EF04A559AE9F8F5A662793B.jpeg\",\"dishPicPaths\":[\"/upload/pic/dish/DAA65C6276DE43E2B0416338BA3E0CD1.jpeg\"],\"dishPrice\":7.0,\"dishDesc\":\"好吃得很！\",\"remark\":\"支持外送。\",\"createTime\":\"2016-10-31\",\"updateTime\":\"2016-11-01\"}]]}";
		
		try {
			JSONObject response=new JSONObject(json);
			String results=response.getString("results");
			JSONArray dataArray=new JSONArray(results);
			
			vos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4), StaffDiningVo.class);
			
			adapter = new CTDiningGridViewAdapter(vos, context, iconFont2);
			gridView.setAdapter(adapter);
		} catch (Exception e) {
		}
		
		

		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/dish/list.json

		String url =  Constants.WEB_DINNER_URL + "/canteen/dish/list.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNum", "1");
		params.put("canteenId", teenId);
		params.put("hospitalId", hid);
		
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {

						try {
							String resultCode=response.getString("resultCode");
							if("0".equals(resultCode)){
								
								String results=response.getString("results");
								JSONArray dataArray=new JSONArray(results);
								//总页数，上一页，当前页，下一页。
								
								vos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4), StaffDiningVo.class);
								
								if(vos.size()>0){
									
									tempOrder.addAll(vos);
									adapter = new CTDiningGridViewAdapter(vos, context, iconFont2);
									gridView.setAdapter(adapter);
								}else {
									Utils.showToastBGNew(context, "暂无该食堂菜单数据");
								}
								
							}else {
								
								Utils.showToastBGNew(context, "查询菜单数据失败");
								
							}
							
							
						} catch (Exception e) {
							e.printStackTrace();
							Utils.showToastBGNew(context, "查询菜单数据失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "查询菜单数据失败");
					}
				});
		
	
	}*/
	
	/**
	 * 0表示下拉刷新，1表示上拉加载
	 */
	private void queryFoodData2(final int flag){
		
		String url =  Constants.WEB_DINNER_URL + "/canteen/dish/list.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		int pageNum;
		if(flag == 0) {
			pageNum = 1;
		} else {
			if(pageNumVo == null) {
				pageNum = 1;
			} else {
				pageNum = pageNumVo.getNextNum();
			}
		}
		params.put("pageNum", String.valueOf(pageNum));
		params.put("canteenId", teenId);
		params.put("hospitalId", hid);
		//过滤条件 菜类，口味，默认排序
		params.put("tasteId", currentTaste == null || currentTaste.getDictName().equals("口味") ? "" : currentTaste.getId());
		params.put("cookTypeId", currentType == null || currentType.getDictName().equals("菜类")? "" : currentType.getId());
		params.put("orderBy", currentOrder.getId().isEmpty() ? "" : currentOrder.getId());
		
		loadView.setVisibility(View.GONE);
		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, isFirst, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						if(flag == 0) {
							tempOrder.clear();
						}
						if(isFirst) {
							isFirst = false;
						}
						List<StaffDiningVo> vos = new ArrayList<StaffDiningVo>();
						try {
							String resultCode=response.getString("resultCode");
							if("0".equals(resultCode)){
								
								String results=response.getString("results");
								JSONArray dataArray=new JSONArray(results);
								pageNumVo = new RefreshRecordVo(Integer.parseInt(dataArray.getString(0)), Integer.parseInt(dataArray.getString(1)), Integer.parseInt(dataArray.getString(2)), Integer.parseInt(dataArray.getString(3)));
								vos=com.alibaba.fastjson.JSONArray.parseArray(dataArray.getString(4), StaffDiningVo.class);
								
								if(vos.size() == 0){
									if(pageNumVo == null || pageNumVo.getCurrentNum() == 1) {
										LoadingUtils.showLoadMsg(loadView, "暂无数据");
									} else {
										Utils.showToastBGNew(context, "没有更多数据了");
									}
								}
								
							}else {
								if((pageNumVo == null || pageNumVo.getCurrentNum() == 1) && tempOrder.size() == 0) {
									LoadingUtils.showLoadMsg(loadView, "查询菜单数据失败");
								} else {
									Utils.showToastBGNew(context, "查询更多菜单失败");
								}
							}
							
							updateAdapter(flag, vos);
							
						} catch (Exception e) {
							e.printStackTrace();
							if(pageNumVo == null || pageNumVo.getCurrentNum() == 1 && tempOrder.size() == 0) {
								LoadingUtils.showLoadMsg(loadView, "查询菜单数据失败");
							} else {
								Utils.showToastBGNew(context, "查询更多菜单失败");
							}
							gridView.onRefreshComplete();
						}
						
					}

					@Override
					public void error(VolleyError error) {
						if(pageNumVo == null || pageNumVo.getCurrentNum() == 1 && tempOrder.size() == 0) {
							LoadingUtils.showLoadMsg(loadView, "查询菜单数据失败");
						} else {
							Utils.showToastBGNew(context, "查询菜单数据失败");
						}
						gridView.onRefreshComplete();
					}
				});
		
	
	}
	
	private void updateAdapter(int type, List<StaffDiningVo> tempVos) {
		//刷新操作
		if(type == 0) {
			tempOrder = tempVos;
		} else if(type == 1) {//上拉加载
			tempOrder.addAll(tempVos);
		}
		adapter.setVos(tempOrder);
		adapter.notifyDataSetChanged();
		gridView.onRefreshComplete();
	}
	
	private class FilterAdapter extends BaseAdapter {

		private List<FoodVo> list;

		public FilterAdapter(List<FoodVo> list) {
			super();
			this.list = list;
		}

		public void setList(List<FoodVo> list) {
			this.list = list;
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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.nation_list_view_item, null);
			}

			TextView tvNation = ViewHolder.get(convertView, R.id.tv_nation);
			tvNation.setTextColor(Color.BLACK);
			tvNation.setText(list.get(position).getDictName());

			if (list.get(position).getIsSelect()) {

				convertView.setBackgroundColor(getResources().getColor(R.color.listview_divider));

			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}

			return convertView;

		}

	}
}
