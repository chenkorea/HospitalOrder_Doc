package com.boil.hospitalorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 日期工具类
 * 
 * @author ChenYong
 * @date 2015-08-28
 *
 */
public class DateUtil {
	/**
	 * 
	 * 将日期按格式转换为日期字符串
	 * 
	 * @param date 日期
	 * @param format 格式
	 * @return 日期字符串
	 * 
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(date);
	}
	
	/**
	 * 
	 * 将日期毫秒数按格式转换为日期字符串
	 * 
	 * @param millisecond 日期毫秒数
	 * @param format 格式
	 * @return 日期字符串
	 * 
	 */
	public static String format(long millisecond, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(millisecond);
	}
	
	/**
	 * 
	 * 将日期字符串按格式转换为日期
	 * 
	 * @param source 日期字符串
	 * @param format 格式
	 * @return 日期
	 * 
	 */
	public static Date parse(String source, String format) throws RuntimeException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			return sdf.parse(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}