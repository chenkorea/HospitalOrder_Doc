package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientInfoVo;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientObj;

public class MomentScanPatientAdapter extends BaseAdapter {
    Context context;
    List<PatientInfoVo> list;
    public MomentScanPatientAdapter(Context _context, List<PatientInfoVo> _list) {
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
        	convertView = layoutInflater.inflate(R.layout.gridview_home_item, null);
        	viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv);
        	viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv2);
        	convertView.setTag(viewHolder);
        } else {
        	viewHolder = (ViewHolder) convertView.getTag();
        }
      
        viewHolder.tv1.setText(list.get(position).getAdmId());
        viewHolder.tvName.setText(list.get(position).getName());
        
        return convertView;
    }
    
    class ViewHolder{
    	TextView tv1,tvName;
    }
}