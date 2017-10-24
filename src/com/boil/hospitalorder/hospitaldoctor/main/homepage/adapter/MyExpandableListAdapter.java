package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientCaseRecordVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.spar.SubscribeArticleDetailActivity;
import com.boil.hospitalorder.utils.RevealLayout;

/**
 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
 * photo is displayed as an image. This adapter supports clearing the list of
 * photos and adding a new photo.
 * 
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;

	private Context context;
	
	private String admno = "";

	private List<PatientCaseRecordVo> list = new ArrayList<PatientCaseRecordVo>();

	public MyExpandableListAdapter() {
	}

	public MyExpandableListAdapter(Context context) {
		this.context = context;
	}

	public MyExpandableListAdapter(Context context, List<PatientCaseRecordVo> list) {
		this.context = context;
		this.list = list;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public MyExpandableListAdapter(Context context, String admno,
			List<PatientCaseRecordVo> list) {
		super();
		this.context = context;
		this.admno = admno;
		this.list = list;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Object getChild(int groupPosition, int childPosition) {

		if (list.get(groupPosition).getChildVos() == null) {
			return "";
		}
		return list.get(groupPosition).getChildVos().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return list.get(groupPosition).getChildVos().size();
	}

//	public TextView getGenericView() {
//		// Layout parameters for the ExpandableListView
//		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, 64);
//		
//		TextView textView = new TextView(context);
//		textView.setLayoutParams(lp);
//		// Center the text vertically
//		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//		// Set the text starting position
//		textView.setPadding(36, 0, 0, 0);
//		textView.setTextColor(Color.BLACK);
//		return textView;
//	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
//		TextView textView = getGenericView();
		
		View v;
		if (convertView == null) {
			v = mInflater.inflate(R.layout.child_list_item, null);
		} else {
			v = convertView;
		}
		RevealLayout rel_child = (RevealLayout) v.findViewById(R.id.rel_child);
		TextView textView = (TextView) v.findViewById(R.id.textView1);
		textView.setText(list.get(groupPosition).getChildVos().get(childPosition).getTitle());
		
		rel_child.setOnClickListener(new ChildListener(childPosition, groupPosition));
		
		return v;
	}

	class ChildListener implements View.OnClickListener{

		private int childPosition;
		private int groupPosition;
		
		public ChildListener(int childPosition, int groupPosition) {
			this.childPosition = childPosition;
			this.groupPosition = groupPosition;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, SubscribeArticleDetailActivity.class);
			intent.putExtra("tid", list.get(groupPosition).getId());
			intent.putExtra("admno", admno);
			intent .putExtra("flag", "4");
			intent.putExtra("mrId", list.get(groupPosition).getChildVos().get(childPosition).getId());
			context.startActivity(intent);
			((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
	}
	
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	public int getGroupCount() {
		return list.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * create group view and bind data to view
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			v = mInflater.inflate(R.layout.list_item, null);
		} else {
			v = convertView;
		}
		TextView textView = (TextView) v.findViewById(R.id.textView);
		ImageView img_indicate = (ImageView) v.findViewById(R.id.img_indicate);
		if (!isExpanded) {
			img_indicate.setBackgroundResource(R.drawable.login_more);
		} else {
			img_indicate.setBackgroundResource(R.drawable.login_more_up);
		}
		textView.setText(list.get(groupPosition).getTdesc().toString());
		if(list.get(groupPosition).getChildVos().size() == 0) {
			v.setOnClickListener(new MyListener(groupPosition));
			img_indicate.setVisibility(View.GONE);
		} else {
			img_indicate.setVisibility(View.VISIBLE);
		}
		return v;
	}
	
	class MyListener implements View.OnClickListener{

		public int position;
		
		public MyListener(int position){
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, SubscribeArticleDetailActivity.class);
			intent.putExtra("tid", list.get(position).getId());
			intent.putExtra("admno", admno);
			intent .putExtra("flag", "4");
			intent.putExtra("mrId", "");
			context.startActivity(intent);
			((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
	}	

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

}
