package com.boil.hospitalorder.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * Fast Json 工具类。
 * 
 * @author ChenYong
 * @date 2016-09-21
 *
 */
public final class FastJsonUtils {
	/**
	 * 
	 * 将实例转换为 Json 字符串。
	 * 
	 * @param bean 实例
	 * @return Json 字符串
	 * 
	 */
	public static String bean2Json(Object bean) {
		if (bean == null) {
			return "";
		}
		
		return JSON.toJSONString(bean);
	}
	
	/**
	 * 
	 * 将 Json 字符串转换为实例。
	 * 
	 * @param json Json 字符串
	 * @param clazz 实例的类类型
	 * @return 实例
	 * 
	 */
	public static <T> T json2Bean(String json, Class<T> clazz) {
		if (StringUtils.isBlank(json) || (clazz == null)) {
			return null;
		}
		
		return JSON.parseObject(json, clazz);
	}
	
	/**
	 * 
	 * 将 Json 字符串转换为 <code>List</code>。
	 * 
	 * @param json Json 字符串
	 * @param clazz 实例的类类型
	 * @return <code>List</code>
	 * 
	 */
	public static <T> List<T> json2List(String json, Class<T> clazz) {
		if (StringUtils.isBlank(json) || (clazz == null)) {
			return new ArrayList<T>();
		}
		
		return JSON.parseArray(json, clazz);
	}
	
	/**
	 * 
	 * 将 Json 字符串转换为 <code>Set</code>。
	 * 
	 * @param json Json 字符串
	 * @param clazz 实例的类类型
	 * @return <code>Set</code>
	 * 
	 */
	public static <T> Set<T> json2Set(String json, Class<T> clazz) {
		return new HashSet<T>(json2List(json, clazz));
	}
	
	/**
	 * 
	 * 将 Json 字符串转换为 <code>Map</code>。
	 * 
	 * @param json Json 字符串
	 * @param kClazz 键的类类型
	 * @param vClazz 值的类类型
	 * @return <code>Map</code>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> json2Map(String json, Class<K> kClazz, Class<V> vClazz) {
		if (StringUtils.isBlank(json) ||//
				(kClazz == null) || //
				(vClazz == null)) {
			return new HashMap<K, V>();
		}
		
		return (Map<K, V>) JSON.parse(json);
	}
}