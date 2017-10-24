package com.boil.hospitalsecond.docpatientcircle.adapter;

import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.Utils;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessagePlatformDeleteAdapter extends BaseAdapter implements OnClickListener {

	private ViewHolder holder;
	private List<TopicTypeVo> list = new ArrayList<TopicTypeVo>();
	private Context context;
	
	private InnerItemOnclickListener mListener;  
	private Typeface iconFont;

	public MessagePlatformDeleteAdapter(Context context, List<TopicTypeVo> list, Typeface iconFont) {
		this.list = list;
		this.context = context;
		this.iconFont = iconFont;
	}

	public MessagePlatformDeleteAdapter(Context context, Typeface iconFont) {
		super();
		this.context = context;
		this.iconFont = iconFont;
	}


	public List<TopicTypeVo> getList() {
		return list;
	}



	public void setList(List<TopicTypeVo> list) {
		this.list = list;
	}



	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.message_platform_listview_item_delete, null);
			holder.tv_message_title = (TextView) convertView.findViewById(R.id.tv_message_title);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_last_name = (TextView) convertView.findViewById(R.id.tv_last_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			holder.iv_chat = (TextView) convertView.findViewById(R.id.iv_chat);
			holder.iv_chat_delete = (TextView) convertView.findViewById(R.id.iv_chat_delete);
			holder.tv_count_delete = (TextView) convertView.findViewById(R.id.tv_count_delete);
			holder.lay_delete = (LinearLayout) convertView.findViewById(R.id.lay_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_chat.setTypeface(iconFont);
		holder.iv_chat_delete.setTypeface(iconFont);
		holder.tv_message_title.setText(String.format("#%s#", list.get(position).getTitle()));
		holder.tv_content.setText(String.format("%s", list.get(position).getContent()));
		
		holder.tv_last_name.setText(list.get(position).getUserName());
		holder.tv_time.setText(list.get(position).getCreatetime());
		holder.tv_count.setText(list.get(position).getReplyNum());
		holder.lay_delete.setOnClickListener(this);
		holder.lay_delete.setTag(position);
//		convertView.setOnClickListener(new MyListener(position));
		return convertView;
	}
	
	class MyDeleteListener implements View.OnClickListener{
		private int position;
		
		public MyDeleteListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View arg0) {
			Utils.showToastBGNew(context, position+"OK");
//			Intent intent = new Intent(context, MessagePlatformDetailActivity.class);
//			context.startActivity(intent);
//			((BaseBackActivity) context).overridePendingTransition(android.R.anim.slide_in_left,
//					android.R.anim.slide_out_right);
		}
	}
	
	private static class ViewHolder {
		TextView tv_message_title, tv_content,tv_last_name,tv_time,tv_count,iv_chat,iv_chat_delete,tv_count_delete;
		LinearLayout lay_delete;
	}
	
    public interface InnerItemOnclickListener {  
        void itemClick(View v);  
    }  
  
    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){  
        this.mListener=listener;  
    } 
    
	@Override
	public void onClick(View v) {
		mListener.itemClick(v);  
	}

}
