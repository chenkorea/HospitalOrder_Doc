package com.boil.hospitalsecond.docpatientcircle.adapter;

import java.util.ArrayList;
import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.utils.CLRoundImageView;
import com.boil.hospitalorder.utils.Constants;
import com.boil.hospitalsecond.docpatientcircle.model.MedicinersReplyMessage;
import com.boil.hospitalsecond.docpatientcircle.spar.MessagePlatformDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * 回复详细 Adapter。
 * 
 * @author ct, ChenYong(修改)
 * @date 2016-12-20
 *
 */
public class MessagePlatformDetailAdapter extends BaseAdapter {
	private ViewHolder holder;
	private List<MedicinersReplyMessage> list = new ArrayList<MedicinersReplyMessage>();
	private MessagePlatformDetailActivity context;
	private Typeface iconFont;
	private String userId;

	public MessagePlatformDetailAdapter(MessagePlatformDetailActivity context, Typeface iconFont, String userId) {
		this.iconFont = iconFont;
		this.context = context;
		this.userId = userId;
	}

	public MessagePlatformDetailAdapter(MessagePlatformDetailActivity context, List<MedicinersReplyMessage> list, String userId) {
		this.context = context;
		this.list = list;
		this.userId = userId;
	}

	public List<MedicinersReplyMessage> getList() {
		return list;
	}

	public void setList(List<MedicinersReplyMessage> list) {
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.message_platform_detail_listview_item, null);
			holder.img = (CLRoundImageView) convertView.findViewById(R.id.head);
			holder.tv_back_content = (TextView) convertView.findViewById(R.id.tv_back_content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
			holder.iv_agree = (TextView) convertView.findViewById(R.id.iv_agree);
			holder.tv_agree_count = (TextView) convertView.findViewById(R.id.tv_agree_count);
			holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (list.get(position).getIsPostive() == 1) {
			holder.iv_agree.setText(context.getResources().getString(R.string.fa_thumbs_up));
		} else {
			holder.iv_agree.setText(context.getResources().getString(R.string.fa_thumbs_o_up));
		}
		
		// 只能删除自己的评论
		if (userId.equals(String.valueOf(list.get(position).getMediciners().getId()))) {
			holder.tv_delete.setVisibility(View.VISIBLE);
		} else {
			holder.tv_delete.setVisibility(View.GONE);
		}

		holder.iv_agree.setTypeface(iconFont);
		ImageLoader.getInstance().displayImage(Constants.IMAGE_STR + list.get(position).getMediciners().getPicture(), holder.img);
		holder.tv_back_content.setText(list.get(position).getContent());
		holder.tv_time.setText(list.get(position).getCreatetime());
		holder.user_name.setText(list.get(position).getMediciners().getName());
		holder.tv_agree_count.setText(String.valueOf(list.get(position).getPostive()));
		
		holder.img.setOnClickListener(new MyListener(position, 0));
		holder.iv_agree.setOnClickListener(new MyListener(position, 1));
		holder.tv_delete.setOnClickListener(new MyListener(position, 2));

		return convertView;
	}

	private static class ViewHolder {
		TextView tv_back_content, tv_time, user_name, iv_agree, tv_agree_count, tv_delete;
		CLRoundImageView img;
	}

	class MyListener implements View.OnClickListener {
		private int position;
		private int flag;

		public MyListener(int position, int flag) {
			this.position = position;
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {
			if (flag == 0) {
				/*  
				if (list.get(position).isIntent()) {
					Intent intent = new Intent();
					intent.setClass(context, DoctorDetailInfoNewActivity.class);
					intent.putExtra("HosDoctorVo", (Serializable) list.get(position));
					context.startActivity(intent);
					context.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				}
				*/
			} else if (flag == 1) {
				if (list.get(position).getIsPostive() == 0) {
					context.addAgree(String.valueOf(list.get(position).getId()), position);
				}
			} else if (flag == 2) {
				context.removelmr(String.valueOf(list.get(position).getId()), position);
			}
		}
	}
}