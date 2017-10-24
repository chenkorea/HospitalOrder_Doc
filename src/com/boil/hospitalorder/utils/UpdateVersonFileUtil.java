package com.boil.hospitalorder.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class UpdateVersonFileUtil {
	
	public static File updateDir = null;
	public static File updateFile = null;
	/***********保存升级APK的目录***********/
	public static final String boilCooperation = "yiyiupdate";
	
	public static boolean isCreateFileSucess;

	/** 
	* 方法描述：createFile方法
	* @param   String app_name
	* @return 
	* @see UpdateVersonFileUtil
	*/
	public static void createFile(String app_name,String fileType) {
		
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())) {
			isCreateFileSucess = true;
			
			updateDir = new File(Environment.getExternalStorageDirectory()+ "/" + boilCooperation +"/");
			updateFile = new File(updateDir + "/" + app_name + "." + fileType);

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		}else{
			isCreateFileSucess = false;
		}
	}
}