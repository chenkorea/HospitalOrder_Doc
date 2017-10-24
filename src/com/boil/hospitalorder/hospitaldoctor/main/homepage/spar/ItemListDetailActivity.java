package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineLisResVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.ExamineListVo;
import com.boil.hospitalorder.utils.CHScrollView;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalorder.utils.ViewHolder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 检验结果 Activity。
 * 
 * @author ChenYong
 * @date 2016-03-01
 * 
 */
public class ItemListDetailActivity extends BaseBackActivity {
	/** 此 Activity 的上下文 */
	private ItemListDetailActivity context = ItemListDetailActivity.this;
	/** Activity 的标题 */
	@ViewInject(R.id.addresstoptitle)
	private TextView tvTitle;
	/** 返回按钮 ImageView */
	@ViewInject(R.id.back)
	private ImageView backBtn;
	/** 保存按钮 TextView */
	@ViewInject(R.id.bt_save)
	private TextView saveBtn;
	/** 表格 LinearLayout */
	@ViewInject(R.id.ll_table)
	private LinearLayout llTable;
	/** 表格头 */
	@ViewInject(R.id.ch_table_header)
	private CHScrollView tableHeader;
	/** 表格体 */
	@ViewInject(R.id.lv_table_body)
	private ListView tableBody;
	
	/** 检验结果 List */
	private List<ExamineLisResVo> vrVos;
	/** 检验结果 Adapter */
	private VerifyResultAdapter vrAdapter;
	
	private ExamineListVo examineListVo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_result_view);

		// 开启注解
		ViewUtils.inject(context);
		initData();
		initView();
		initEvent();
		
	}

	/**
	 * 
	 * 初始化数据。
	 * 
	 */
	private void initData() {
		Intent intent = getIntent();
		examineListVo = (ExamineListVo) intent.getSerializableExtra("CheckItem");
		vrVos=HealthDetailActivity.healthDetailActivity.getTempVrVos();
		vrAdapter = new VerifyResultAdapter(vrVos);
		tableBody.setAdapter(vrAdapter);
	}

	/**
	 * 
	 * 初始化视图组件。
	 * 
	 */
	private void initView() {
		// 点击返回上一个 Activity
		Utils.backClick(this, backBtn);
		// 设置标题
		tvTitle.setText(examineListVo.getProjectName() + "详细");
		// 隐藏保存按钮
		saveBtn.setVisibility(View.INVISIBLE);
		// 添加头滑动事件
		mHScrollViews.add(tableHeader);
	}

	/**
	 * 
	 * 初始化事件。
	 * 
	 */
	private void initEvent() {

	}
	
	
	public void setVrVos(List<ExamineLisResVo> vrVos) {
		this.vrVos = vrVos;
	}

	/**
	 * 
	 * 添加滑动元素。
	 * 
	 * @param chScrollView
	 * 
	 */
	private void addCHScrollView(final CHScrollView chScrollView) {
		if (!mHScrollViews.isEmpty()) {
			CHScrollView scrollView = mHScrollViews.get(mHScrollViews.size() - 1);
			final int scrollX = scrollView.getScrollX();
			
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				tableBody.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						chScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		
		mHScrollViews.add(chScrollView);
	}
	

	/**
	 * 
	 * 检验结果的 Adapter。
	 * 
	 * @author ChenYong
	 * @date 2016-03-02
	 * 
	 */
	private class VerifyResultAdapter extends BaseAdapter {
		/** 检验结果 List */
		private List<ExamineLisResVo> vrVos;

		/**
		 * 
		 * 有参构造器。
		 * 
		 * @param vrVos 检验结果 List
		 * 
		 */
		public VerifyResultAdapter(List<ExamineLisResVo> vrVos) {
			this.vrVos = vrVos;
		}

		@Override
		public int getCount() {
			return vrVos.size();
		}

		@Override
		public Object getItem(int position) {
			return vrVos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.verify_result_item, null);
			}

			CHScrollView chTableBody = ViewHolder.get(convertView, R.id.ch_table_body);
			TextView tvResName = ViewHolder.get(convertView, R.id.tv_res_name);
			TextView tvResResult = ViewHolder.get(convertView, R.id.tv_res_result);
			TextView tvResResultArrows = ViewHolder.get(convertView, R.id.tv_verify_result_arrows);
			TextView tvResRanges = ViewHolder.get(convertView, R.id.tv_res_ranges);
			TextView tvResDhc = ViewHolder.get(convertView, R.id.tv_res_dhc);

			if ((vrVos != null) && !vrVos.isEmpty()) {
				ExamineLisResVo vrVo = vrVos.get(position);
				tvResName.setText(vrVo.getJyResname());
				tvResResult.setText(vrVo.getResult());
				tvResRanges.setText(vrVo.getReference());
				tvResDhc.setText(vrVo.getResDhc());
				
				if ("1".equals(vrVo.getFlag())) {
					tvResResultArrows.setText("↓");
				} else if ("2".equals(vrVo.getFlag())) {
					tvResResultArrows.setText("↑");
				} else {
					tvResResultArrows.setText("");
				}
				addCHScrollView(chTableBody);
			}

			return convertView;
		}
	}
}