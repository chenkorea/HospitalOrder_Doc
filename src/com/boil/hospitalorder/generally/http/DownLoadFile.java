package com.boil.hospitalorder.generally.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class DownLoadFile {

	public static boolean downFlag = true;
	public static final int DOWNLOAD_STOP = 0;
	public static final int SET_MAX_PROGRESS = 1;
	public static final int SET_VALUE_PROGRESS = 2;
	public static final int DOWNLOAD_SUCCESS = 3;

	public static void starDownLoad(final String url, final String fileType, final String path,
			final Handler handler) {
		URL url2 = null;
		HttpURLConnection con = null;

		try {
			url2 = new URL(url);

			con = (HttpURLConnection) url2.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Encoding", "utf-8");

			int max_value = con.getContentLength();

			if (max_value <= 0) {
				Message message = new Message();
				message.obj = "获取文件大小失败！";
				message.what = DOWNLOAD_STOP;
				handler.sendMessage(message);
				con.disconnect();
				return;
			} else {
				Message message = new Message();
				message.obj = max_value;
				message.what = SET_MAX_PROGRESS;
				handler.sendMessage(message);
			}
			Thread.sleep(500);
			String filePath = path;
			if (filePath.equals("")) {
				Message message = new Message();
				message.obj = "没有存储卡！";
				message.what = DOWNLOAD_STOP;
				handler.sendMessage(message);
				con.disconnect();
				return;
			}
			InputStream in = con.getInputStream();

			File wallpaperDirectory = new File(filePath);
			if (!wallpaperDirectory.exists()) {
				wallpaperDirectory.mkdirs();
			}

			filePath = filePath + "/downLoad." + fileType;
			// FileOutputStream out = context.openFileOutput(filePath,
			// Context.MODE_PRIVATE);
			File fileOut = new File(filePath);
			FileOutputStream out = new FileOutputStream(fileOut);

			byte[] bytes = new byte[1024 * 10];
			int c = in.read(bytes);

			while (c > 0) {
				Thread.sleep(100);
				if (downFlag) {
					out.write(bytes, 0, c);
					c = in.read(bytes, 0, bytes.length);
					Message message = new Message();
					message.obj = c;
					message.what = SET_VALUE_PROGRESS;
					handler.sendMessage(message);
				} else {
					break;
				}
			}
			in.close();
			out.close();
			if (downFlag) {
				Message message = new Message();
				message.obj = filePath;
				message.what = DOWNLOAD_SUCCESS;
				handler.sendMessage(message);
			} else {
				Message message = new Message();
				message.obj = "文件下载已停止！";
				message.what = DOWNLOAD_STOP;
				handler.sendMessage(message);
			}

		} catch (Exception e) {
			Message message = new Message();
			message.obj = "错误：\n" + e.getMessage();
			message.what = DOWNLOAD_STOP;
			handler.sendMessage(message);
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
}
