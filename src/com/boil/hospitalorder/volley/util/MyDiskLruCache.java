package com.boil.hospitalorder.volley.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.Locale;

import android.content.Context;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

/**
 * 
 * 我的磁盘缓存工具类。
 * 
 * @author ChenYong
 * @date 2016-09-08
 *
 */
public class MyDiskLruCache {
	/** 标记 */
	private static String TAG = "MyDiskLruCache";
	/** 缓存的最大值，默认 30MB（单位：byte） */
	private long maxSize = (30L * 1024L * 1024L);
	/** 磁盘缓存区 */
	private DiskLruCache diskLruCache;
	
	/**
	 * 
	 * 有参构造器，缓存的最大值，默认 30MB（单位：byte）。
	 * 
	 * @param context 上下文
	 * @param cacheDir 缓存目录
	 * 
	 */
	public MyDiskLruCache(Context context, String cacheDir) {
		try {
			this.diskLruCache = DiskLruCache.open(AndroidUtils.getSysDiskCacheDir(context, cacheDir), //
					AndroidUtils.getAppVersion(context), //
					1, //
					this.maxSize);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param context 上下文
	 * @param cacheDir 缓存目录
	 * @param maxSize 缓存的最大值（单位：byte）
	 * 
	 */
	public MyDiskLruCache(Context context, String cacheDir, long maxSize) {
		try {
			this.maxSize = maxSize;
			this.diskLruCache = DiskLruCache.open(AndroidUtils.getSysDiskCacheDir(context, cacheDir), //
					AndroidUtils.getAppVersion(context), //
					1, //
					this.maxSize);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 缓存数据。
	 * 
	 * @param key 键，如：图片路径等
	 * @param is 输入流
	 * @return 缓存是否成功：
	 * <ol>
	 * 	<li>true-成功；</li>
	 * 	<li>false-失败。</li>
	 * </ol>
	 * 
	 */
	public synchronized boolean put(String key, InputStream is) {
		// 缓存输入流
		BufferedInputStream bis = null; 
		// 缓存输出流
		BufferedOutputStream bos = null;
		// DiskLruCache 编辑器
		DiskLruCache.Editor editor = null;
		
		// 缓存操作
		try {
			editor = diskLruCache.edit(MD5Utils.md5(key).toLowerCase(Locale.getDefault()));
			
			// 缓冲区
			byte[] buffer = new byte[1024];
			// 缓冲区标志，读取的字节数
			int bufferFlag = 0;
			
			// 构造缓存输入流
			bis = new BufferedInputStream(is);
			// 构造缓冲输出流
			bos = new BufferedOutputStream(editor.newOutputStream(0));
			
			// 写入缓存
	        while ((bufferFlag = bis.read(buffer)) != -1) {
	            bos.write(buffer, 0, bufferFlag);  
	        }
	        
	        // 提交，将数据存入磁盘中
	        editor.commit();
	        
	        // 缓存成功
	        return true;
			
			// 终止缓存操作
		} catch (Exception e1) {
			Log.e(TAG, e1.getMessage(), e1);
			
			try {
				editor.abort();
			} catch (Exception e2) {
				Log.e(TAG, e2.getMessage(), e2);
			}
			
			// 缓存失败
			return false;
			
			// 释放资源
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				
				if (bis != null) {
					bis.close();
				}
				
				flush();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 
	 * 读取缓存数据。
	 * 
	 * @param key 键，如：图片路径等
	 * 
	 */
	public InputStream get(String key) {
		try {
			if ((key != null) && !"".equals(key) && (diskLruCache != null)) {
				// 获取缓存映像
				DiskLruCache.Snapshot snapshot = diskLruCache.get(MD5Utils.md5(key).toLowerCase(Locale.getDefault()));
				
				if (snapshot != null) {
					return snapshot.getInputStream(0);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * 
	 * 获取已缓存的数据大小（单位：byte）。
	 * 
	 */
	public long size() {
		if (diskLruCache == null) {
			return 0L;
		}
		
		return diskLruCache.size();
	}
	
	/**
	 * 
	 * 获取缓存的最大值（单位：byte）。
	 * 
	 */
	public long getMaxSize() {
		if (diskLruCache == null) {
			return 0L;
		}
		
		return diskLruCache.getMaxSize();
	}
	
	/**
	 * 
	 * 移除缓存。
	 * 
	 * @param key 键，如：图片路径等
	 * 
	 */
	public void remove(String key) {
		try {
			if ((key != null) && !"".equals(key) && (diskLruCache != null)) {
				diskLruCache.remove(MD5Utils.md5(key).toLowerCase(Locale.getDefault()));
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 清空缓存。
	 * 
	 */
	public void clear() {
		try {
			if (diskLruCache != null) {
				diskLruCache.delete();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 刷新缓存。
	 * 
	 */
	public void flush() {
		try {
			if (diskLruCache != null) {
				diskLruCache.flush();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * 关闭缓存。
	 * 
	 */
	public void close() {
		try {
			if (diskLruCache != null) {
				diskLruCache.close();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
}