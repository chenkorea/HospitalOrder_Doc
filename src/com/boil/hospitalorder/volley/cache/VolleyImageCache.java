package com.boil.hospitalorder.volley.cache;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.boil.hospitalorder.volley.util.AndroidUtils;
import com.boil.hospitalorder.volley.util.MyDiskLruCache;

/**
 * 
 * Volley 图片缓存。
 * 
 * @author ChenYong
 * @date 2016-09-07
 *
 */
public class VolleyImageCache implements ImageCache {
	/** 图片磁盘缓存区 */
	private MyDiskLruCache bitmapDiskLruCache;

	/**
	 * 
	 * 有参构造器。
	 * 
	 * @param bitmapDiskLruCache 图片磁盘缓存区
	 * 
	 */
	public VolleyImageCache(MyDiskLruCache bitmapDiskLruCache) {
		this.bitmapDiskLruCache = bitmapDiskLruCache;
	}

	/**
	 * 
	 * 从缓存中获取图片。
	 * 
	 * @param url 图片路径
	 * @return 图片
	 * 
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return AndroidUtils.inputStream2Bitmap(bitmapDiskLruCache.get(url));
	}

	/**
	 * 
	 * 缓存图片。
	 * 
	 * @param url 图片路径
	 * @param bitmap 图片
	 * 
	 */
	@Override
	public void putBitmap(String url, Bitmap bm) {
		if ((url == null) || "".equals(url) || (bm == null)){
			return;
		}
		
		bitmapDiskLruCache.put(url, AndroidUtils.bitmap2InputStream(bm, Bitmap.CompressFormat.PNG, 100));
	}
}