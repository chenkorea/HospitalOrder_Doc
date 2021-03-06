package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.PatientMedicalRecordVo;

public class HosIncomeClassifyAdapter extends BaseAdapter {
    Context context;
    List<PatientMedicalRecordVo> list;
    public HosIncomeClassifyAdapter(Context _context, List<PatientMedicalRecordVo> _list) {
        this.list = _list;
        this.context = _context;
    }

    public List<PatientMedicalRecordVo> getList() {
		return list;
	}

	public void setList(List<PatientMedicalRecordVo> list) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHolder viewHolder;
        if(convertView == null) {
        	viewHolder = new ViewHolder();
        	convertView = layoutInflater.inflate(R.layout.hos_income_classify_listview_item, null);
        	viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_title);
        	convertView.setTag(viewHolder);
        } else {
        	viewHolder = (ViewHolder) convertView.getTag();
        }
      
        viewHolder.tv1.setText(list.get(position).getMdesc());
        return convertView;
    }
    
    class ViewHolder{
    	TextView tv1;
    }
}