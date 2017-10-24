package com.boil.hospitalorder.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

public class AssetsOperateUtils {

	public static String FILE_NAME = "1.html";
	
	public static String PAKEAGE_NAME = "com.boil.content";
	
	public static String SAVE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PAKEAGE_NAME;
	
	public byte[] stringToBytes(String result) {
		byte[] bytes = new byte[100 * 1024];
		bytes = result.getBytes();
		return bytes;
	}
	
	public static boolean writeToFile(String is) throws FileNotFoundException {
		File file = new File(SAVE_PATH);
		if(!file.exists()) {
			file.mkdirs();
		}
		File f = new File(file.getPath(), FILE_NAME); 
		if (f.exists()) {
			f.delete();
		}
		FileWriter fw;
		try {
			fw = new FileWriter(f.getName());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(is);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	           
	    System.out.println("Done.");
		
//		FileOutputStream out = new FileOutputStream(f);  
//		
////		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	    byte[] buffer = new byte[1024];
//	    int len = -1;
//	    try {
//	      if (is != null) {
//	    	  System.out.println("available===>" + is.available());
//	        while ((len = is.read(buffer)) != -1) {
//	        	out.write(buffer, 0, len);
//	        }
//	      }
//	      // 4.关闭流
//	      out.flush();
//	      out.close();
//	      is.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
		return true;
	}
	
	
	public static void createDir() {
		
		File file = new File(SAVE_PATH);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	public void createFile(String path, String fileName) {
		
		
	}
	
}
