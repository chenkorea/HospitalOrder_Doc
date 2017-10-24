package com.boil.hospitalorder.hospitaldoctor.main.homepage.adapter;

import java.util.List;

import com.boil.hospitalorder.hospitaldoctor.R;
import com.boil.hospitalorder.hospitaldoctor.main.homepage.model.CheckResultVo;
import com.boil.hospitalorder.utils.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckResultAdapter extends BaseAdapter {

	private List<CheckResultVo> checkResultVos;

	public CheckResultAdapter(List<CheckResultVo> checkResultVos) {
		this.checkResultVos = checkResultVos;
	}

	@Override
	public int getCount() {
		return checkResultVos.size();
	}

	@Override
	public Object getItem(int position) {
		return checkResultVos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_result_view_item, null);
		}

		TextView tvItem = ViewHolder.get(convertView, R.id.tv_item);
		TextView tvIsCheck = ViewHolder.get(convertView, R.id.tv_is_check);
		TextView tvDate = ViewHolder.get(convertView, R.id.tv_date);

		if ((checkResultVos != null) && !checkResultVos.isEmpty()) {
			CheckResultVo checkResultVo = checkResultVos.get(position);

			tvItem.setText(checkResultVo.getDirection());

			if (checkResultVo.getReportDate() != null) {
				String date = checkResultVo.getReportDate();
				String year = date.substring(0, 4);
				String month = date.substring(4, 6);
				String day = date.substring(6, 8);
				tvDate.setText(String.format("于%s检查", year + "-" + month + "-" + day));
			} else {
				tvDate.setText("");
			}

			tvIsCheck.setText("已检查");

		}

		return convertView;
	}
}