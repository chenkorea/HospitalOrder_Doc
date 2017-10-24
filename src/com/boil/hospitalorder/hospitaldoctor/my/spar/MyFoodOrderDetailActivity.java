package com.boil.hospitalorder.hospitaldoctor.my.spar;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.my.adapter.FoodOrderDetailAdapter;
import com.boil.hospitalorder.hospitaldoctor.my.modal.FoodOrderVo;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalorder.utils.RevealLayout;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.volley.http.VolleyClient.VolleyListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyFoodOrderDetailActivity extends BaseBackActivity {

	private MyFoodOrderDetailActivity context = MyFoodOrderDetailActivity.this;

	@ViewInject(R.id.back)
	private ImageView backBtn;

	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;

	@ViewInject(R.id.bt_save)
	private TextView bt_save;

	@ViewInject(R.id.list_food_order_detail)
	private ListView listOrder;
	@ViewInject(R.id.lay)
	private RevealLayout lay;
	@ViewInject(R.id.btn_cancel)
	private BootstrapButton btn_cancel;
	
	private FoodOrderVo vo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_order_detail_activity);
		ViewUtils.inject(this);
		initView();
		initData();
		initEvent();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		addreTitle.setText("订单详细");
		bt_save.setVisibility(View.INVISIBLE);
		volleyClient.setActivity(context);
	}

	private void initData() {
		vo=(FoodOrderVo) getIntent().getSerializableExtra("FoodOrderVo");
		if("103".equals(vo.getOrderStatusId())){
			lay.setVisibility(View.VISIBLE);
		}else {
			lay.setVisibility(View.GONE);
			
		}
		
		
		FoodOrderDetailAdapter adapter=new FoodOrderDetailAdapter(context, vo.getCookbookCountVos());
		listOrder.setAdapter(adapter);
	}

	private void initEvent() {
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				cancelOrder();
				
			}
		});
	}
	
	/**
	 * 取消订单
	 */
	private void cancelOrder(){
		


		// http://192.168.1.250:8080/hsptapp-web/mobile/canteen/dish/order/status/update.json?id=2&orderStatusId=104

		String url = Constants.WEB_DINNER_URL + "/canteen/dish/order/status/update.json";

		// 请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("id",vo.getId());
		params.put("canteenId",vo.getCanteenId());
		params.put("hospitalId",vo.getHospitalId());
		params.put("orderStatusId", "107");

		volleyClient.sendJsonObjectRequest(Request.Method.GET, url, null, params, true, null,
				new VolleyListener<JSONObject>() {

					@Override
					public void success(JSONObject response) {
						try {
							String resultCode = response.getString("resultCode");
							String resultMsg = response.getString("resultMsg");
							
							if ("0".equals(resultCode)) {

								Utils.showToastBGNew(context, "取消成功");
								MyFoodOrderActivity.update=1;
								
								finish();
							} else {
								Utils.showToastBGNew(context, resultMsg);
							}

						} catch (Exception e) {
							e.printStackTrace();
							
							Utils.showToastBGNew(context, "取消失败");
						}
					}

					@Override
					public void error(VolleyError error) {
						Utils.showToastBGNew(context, "取消失败");
					}
				});
	}
}