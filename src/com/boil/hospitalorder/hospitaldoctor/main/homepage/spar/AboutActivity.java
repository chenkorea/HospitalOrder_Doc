package com.boil.hospitalorder.hospitaldoctor.main.homepage.spar;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.base.BaseBackActivity;
import com.boil.hospitalorder.utils.Utils;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 关于我们界面
 * @author mengjiyong
 *
 */
public class AboutActivity extends BaseBackActivity{

	private TextView addreTitle;
	private ImageView backBtn;
	private TextView saveBtn;
	
	private TextView mVersionTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}
	private void initView() {
		addreTitle = (TextView) findViewById(R.id.addresstoptitle);
		addreTitle.setText("关于我们");
		backBtn = (ImageView) findViewById(R.id.back);
		saveBtn=(TextView) findViewById(R.id.bt_save);
		saveBtn.setVisibility(View.INVISIBLE);
		Utils.backClick(this, backBtn);
		
		mVersionTextView=(TextView) findViewById(R.id.tv_current_versions);
		mVersionTextView.setText("当前版本v"+Utils.getAppVersion(this));
	}
}
