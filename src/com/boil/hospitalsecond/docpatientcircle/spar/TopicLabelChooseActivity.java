package com.boil.hospitalsecond.docpatientcircle.spar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalsecond.tool.ctgridview.Tag;
import com.boil.hospitalsecond.tool.ctgridview.TagListView;
import com.boil.hospitalsecond.tool.ctgridview.TagListView.OnTagClickListener;
import com.boil.hospitalsecond.tool.ctgridview.TagView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 我的话题 Activity。
 * 
 * @author ChenYong(修改)
 * @date 2016-12-21
 *
 */
public class TopicLabelChooseActivity extends BaseBackActivity {
	/** 上下文 */
	private TopicLabelChooseActivity context = TopicLabelChooseActivity.this;
	@ViewInject(R.id.back)
	private ImageView backBtn;
	@ViewInject(R.id.addresstoptitle)
	private TextView addreTitle;
	@ViewInject(R.id.bt_save)
	private TextView bt_save;
	@ViewInject(R.id.tagview)
	private TagListView mTagListView;
	@ViewInject(R.id.tag_title)
	private TextView tag_title;
	
	private List<Tag> mTags = new ArrayList<Tag>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.topic_label_choose);
		
		ViewUtils.inject(this);
		
		volleyClient.setActivity(this);

		initView();
		initData();
	}

	private void initView() {
		Utils.backClick(this, backBtn);
		
		addreTitle.setText("我的话题");
		bt_save.setVisibility(View.INVISIBLE);
		
		mTagListView.setTagViewTextColorRes(getResources().getColor(R.color.green));
		
		mTagListView.setOnTagClickListener(new OnTagClickListener() {
			@Override
			public void onTagClick(TagView tagView, Tag tag) {
				Intent intent = new Intent(context, TopicLabelListActivity.class);
				intent.putExtra("tag", (Serializable) tag);
				
				startActivity(intent);
				
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		mTags = (List<Tag>) getIntent().getExtras().getSerializable("tags");
		
		if (Utils.isValidate(mTags)) {
			mTagListView.setTags(mTags);
		} else {
			tag_title.setText("暂无热门分类标签");
		}
	}
}