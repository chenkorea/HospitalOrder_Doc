package com.boil.hospitalorder.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 
 * ViewHolder 工具类。
 * 
 */
public class ViewHolder {
	/**
	 * 
	 * 获取 Item 视图中的 ID 视图。
	 * 
	 * @param view Item 视图
	 * @param id ID 视图
	 * @return Item 视图中的 ID 视图
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}

		View childView = viewHolder.get(id);

		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}

		return (T) childView;
	}
}