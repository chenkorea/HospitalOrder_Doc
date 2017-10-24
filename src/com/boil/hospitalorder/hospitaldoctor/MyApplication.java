package com.boil.hospitalorder.hospitaldoctor;


import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {

	
	public Boolean isDownloading = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		DisplayImageOptions defaultOptions = new DisplayImageOptions
				.Builder()	
				.showImageForEmptyUri(R.drawable.head_default_yixin) 
				.showImageOnFail(R.drawable.head_default_yixin) 
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(200)
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
	private List<Activity> mList = new LinkedList<Activity>();
	private static MyApplication instance;

	public synchronized static MyApplication getInstance() { 
        if (null == instance) { 
            instance = new MyApplication(); 
        } 
        return instance; 
    } 
    // add Activity  
    public void addActivity(Activity activity) { 
        mList.add(activity); 
    } 
 
    public void exit() { 
        try { 
            for (Activity activity : mList) { 
                if (activity != null) 
                    activity.finish(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            System.exit(0); 
        } 
    } 
	
}
