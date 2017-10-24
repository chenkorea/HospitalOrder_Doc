package com.boil.hospitalorder.volley.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * 系统工具类。
 * 
 * @author ChenYong
 * @date 2016-09-09
 *
 */
public class AndroidUtils {
	/** 标记 */
	private static String TAG = "AndroidUtils";
	
	/**
	 * 
	 * 获取 App 的版本号。
	 * 
	 * @param context 上下文
	 * @return App 的版本号
	 * 
	 */
	public static int getAppVersion(Context context) {
		if (context == null) {
			return 1;
		}
		
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			return info.versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage(), e);
			
			return 1;
		}
	}
	
	/**
	 * 
	 * 获取系统提供缓存下的目录。
	 * 
	 * @param context 上下文
	 * @param cacheDirName 缓存目录的名称
	 * @return 系统提供缓存下的目录
	 * 
	 */
	public static File getSysDiskCacheDir(Context context, String cacheDirName) {
		if ((context == null) || (cacheDirName == null) || "".equals(cacheDirName)) {
			return null;
		}
		
		// 系统提供缓存下的路径
		String cachePath = null;
		// 系统提供缓存下的目录
		File cacheDir = null;
		
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) //
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		
		cacheDir = new File(cachePath + File.separator + cacheDirName);
		
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		
		return cacheDir;
	}
	
	/**
	 * 
	 * Bitmap 转成 InputStream。
	 * 
	 * @param bm Bitmap
	 * @param format 压缩格式
	 * @param quality 压缩质量（100表示不压缩）
	 * @return InputStream
	 * 
	 */
	public static InputStream bitmap2InputStream(Bitmap bm, Bitmap.CompressFormat format, int quality) {
		if ((bm == null) || (format == null)) {
			return null;
		}
		
		// 字节输出流
		ByteArrayOutputStream baos = null;
		// 字节输入流
		ByteArrayInputStream bais = null;
		
		try {
			baos = new ByteArrayOutputStream();
			
			// 100 表示不压缩
			bm.compress(format, quality, baos);
			
			bais = new ByteArrayInputStream(baos.toByteArray());
			
			return bais;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			
			return null;
			
			// 释放资源
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
				
				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 
	 * InputStream 转成 Bitmap。
	 * 
	 * @param is InputStream
	 * @return Bitmap
	 * 
	 */
	public static Bitmap inputStream2Bitmap(InputStream is) {
		if (is == null) {
			return null;
		}
		
		try {
			Bitmap bm = BitmapFactory.decodeStream(is);
			
			return bm;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			
			return null;
			
			// 释放资源
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
    }
}