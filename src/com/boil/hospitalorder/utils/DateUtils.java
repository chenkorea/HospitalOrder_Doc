package com.boil.hospitalorder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * 日期工具类。
 * 
 * @author ChenYong
 * @date 2015-12-31
 *
 */
public class DateUtils {
	/**
	 * 
	 * 将日期按格式转换为日期字符串。
	 * 
	 * @param date 日期
	 * @param format 格式
	 * @return 日期字符串
	 * 
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		
		return sdf.format(date);
	}
	
	/**
	 * 
	 * 将日期毫秒数按格式转换为日期字符串。
	 * 
	 * @param millisecond 日期毫秒数
	 * @param format 格式
	 * @return 日期字符串
	 * 
	 */
	public static String format(long millisecond, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		
		return sdf.format(millisecond);
	}
	
	/**
	 * 
	 * 将日期字符串按格式转换为日期。
	 * 
	 * @param source 日期字符串
	 * @param format 格式
	 * @return 日期
	 * 
	 */
	public static Date parse(String source, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 获取 before 的前多少天的日期字符串。
	 * 
	 * @param before 日期源
	 * @param days 前多少天
	 * @param format 格式
	 * @return before 的前多少天的日期字符串
	 * 
	 */
	public static String formatBeforeDate(Date before, int days, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(before);
		cal.add(Calendar.DATE, -days);
		Date beforeDate = cal.getTime();

		return format(beforeDate, format);
	}
}