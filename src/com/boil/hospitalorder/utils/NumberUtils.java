package com.boil.hospitalorder.utils;

import java.text.DecimalFormat;

public class NumberUtils {
	
	public static String formatPrice(double price) {
		DecimalFormat df=new DecimalFormat("0.00");
		String format = "￥" + df.format(price);
		return format;
	}
}
