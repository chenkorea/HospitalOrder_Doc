package com.boil.hospitalsecond.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

public class BaseTools {
	
	/** 得到屏幕宽度 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
