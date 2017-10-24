package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.MainActivity;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientMessageTipVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientObj;
import com.boil.hospitalorder.utils.BaseBackActivity;
import com.boil.hospitalsecond.docpatientcircle.model.TopicTypeVo;
import com.boil.hospitalsecond.docpatientcircle.spar.MessagePlatformDetailActivity;

public class PatientTipsAdapter extends BaseAdapter {
    Context context;
    List<TopicTypeVo> list;
    public PatientTipsAdapter(Context _context, List<TopicTypeVo> _list) {
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
        ViewHolder viewHolder;
        if(convertView == null) {
        	viewHolder = new ViewHolder();
        	convertView = layoutInflater.inflate(R.layout.nation_list_view_item, null);
        	viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_nation);
        	convertView.setTag(viewHolder);
        } else {
        	viewHolder = (ViewHolder) convertView.getTag();
        }
      
        viewHolder.tv1.setText(list.get(position).getContent());
        
        convertView.setOnClickListener(new MyListener(position));
        return convertView;
    }
    
    
    class MyListener implements View.OnClickListener{
		private int position;
		
		public MyListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(context, MessagePlatformDetailActivity.class);
			intent.putExtra("TopicVo", (Serializable)list.get(position));
			context.startActivity(intent);
			((MainActivity) context).overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		}
	}
    class ViewHolder{
    	TextView tv1;
    }
}