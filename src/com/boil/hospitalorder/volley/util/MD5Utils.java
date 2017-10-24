package com.boil.hospitalorder.volley.util;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * 
 * MD5 工具类。
 * 
 * @author ChenYong
 * @date 2016-09-08
 *
 */
public class MD5Utils {
	/**
	 * 
	 * 采用 MD5 对字符串加密。
	 * 
	 * @param s 被加密的字符串
	 * @return 加密后的字符串
	 * 
	 */
	public static String md5(String s) {
		if (s == null) {
			return "";
		}
		
		try {
			// MD5 缓冲区
			StringBuilder md5Sb = new StringBuilder();
			// 获取 MD5 消息摘要
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// MD5 摘要
			byte[] md5Digest;
			
			messageDigest.update(s.getBytes());
			md5Digest = messageDigest.digest();
			
			// 转换为 16 进制
		    for (int i = 0; i < md5Digest.length; i++) {  
		        String hexStr = Integer.toHexString(0xFF & md5Digest[i]);  
		        
		        if (hexStr.length() == 1) {  
		        	md5Sb.append('0');  
		        }
		        
		        md5Sb.append(hexStr);  
		    }
			
			return md5Sb.toString().toUpperCase(Locale.getDefault());
		} catch (Exception e) {
			return "";
		}
	}
}