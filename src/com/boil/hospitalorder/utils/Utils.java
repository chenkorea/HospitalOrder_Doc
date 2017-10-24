package com.boil.hospitalorder.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.beardedhen.androidbootstrap.utils.ImageUtils;
import com.boil.hospitalorder.hospitaldoctor.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 主工具类。
 * 
 */
public class Utils {
	/**
	 * 显示无操作提示框
	 * 
	 * @param context
	 * @param msg
	 */
	@SuppressLint("NewApi")
	public static void showAlertDialog(Context context, String msg) {
		Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("提示");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

	@SuppressLint("ShowToast")
	public static Toast getToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		LinearLayout layout = (LinearLayout) toast.getView();
		TextView view = (TextView) layout.getChildAt(0);
		view.setText(text);
		view.setTextSize(14);
		
		toast.setView(layout);
		return toast;
	}

	public static void installAPK(Context context, String path) {
		// url_of_apk_file
		Uri uri = Uri.parse(path);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.setData(uri);
		it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		it.setClassName("com.android.packageinstaller",
				"com.android.packageinstaller.PackageInstallerActivity");
		it.setDataAndType(uri,
				 "application/vnd.android.package-archive");
		context.startActivity(it);
	}
	
	public static String getDownPath() {
		String path = "";
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			path = android.os.Environment.getExternalStorageDirectory()
					.getPath();
			return path + "/yiyi/";
		}
		return path;
	}
	
	
	 public static String open(String strUrl, String postData) {  
	        byte[] data = null;  
	        InputStream is = null;  
	        OutputStream os = null;  
	        ByteArrayOutputStream baos = null;  
	        String result = "";
	        HttpURLConnection conn = null;  
	        try {// 为了测试取消连接  
	                // Thread.sleep(5000);  
	                // http联网可以用httpClient或java.net.url  
	            URL url = new URL(strUrl);  
	            conn = (HttpURLConnection) url.openConnection();  
	            conn.setDoInput(true);  
	            conn.setDoOutput(true);  
	            conn.setConnectTimeout(1000 * 30);  
	            conn.setReadTimeout(1000 * 30);  
	            conn.setRequestMethod("GET");  
	            int responseCode = conn.getResponseCode();  
	            if (responseCode == 200) {  
	                is = conn.getInputStream();  
	                baos = new ByteArrayOutputStream();  
	                byte[] buffer = new byte[1024 * 8];  
	                int size = 0;  
	                while ((size = is.read(buffer)) >= 1) {  
	                    baos.write(buffer, 0, size);  
	                }  
	                data = baos.toByteArray();  
	                result = new String(data,"UTF-8");
	                System.out.println(result.toString());
	            }  
	        } catch (Exception e) {  
//	            ExceptionUtil.handle(e);  
	        } finally {  
	            try {  
	                if (is != null) {is.close();}  
	                if (os != null) {os.close();}  
	                if (baos != null) {baos.close();}  
	                conn = null;  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }  
	
	/**
	 * 显示无操作提示框
	 * 
	 * @param context
	 * @param msg
	 */
	@SuppressLint("ResourceAsColor")
	public static void showToastChangeBG(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.room_item_press);
		view.setBackgroundColor(R.color.toast_bg);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	/**
	 * 显示新版无操作提示框
	 * 
	 * @param context
	 * @param msg
	 */
	@SuppressLint("InflateParams")
	public static void showToastBGNew(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.book_reading_seekbar_toast, null);
		TextView chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
		chapterNameTV.setText(msg);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.BOTTOM, 0, formatDipToPx(context, 70));
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(view);
		toast.show();
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	public static String convertMD5(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 检查网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		return false;
	}

	public static void callContact(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
		context.startActivity(intent);
	}

	public static String convertToDetailStrByDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date);
		}
	}

	public static String convertToStrByDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date);
		}
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}

	public static String getAppVersion(Context context) {
		String versionName = "";
		//int versioncode;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			//versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	public static void backClick(final Activity activity, ImageView backBtn) {
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.finish();
			}
		});
	}

	/**
	 * 获取几天级之前几天的日期字符串
	 * 
	 * @param days
	 * @return
	 */
	public static String getDaysBefore(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);
		Date d = cal.getTime();

		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		return sp.format(d);
	}

	/**
	 * 获取今天日期
	 * 
	 * @return
	 */
	public static String getDaysToday() {
		Date d = new Date();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		return sp.format(d);
	}

	/**
	 * 获取json格式
	 * 
	 * @param json
	 * @return
	 */
	public static String getJsonStr(String json) {
		json = json.substring(1, json.length() - 1);

		return json;
	}

	/**
	 * 保存系统偏好设置
	 * 
	 * @param name
	 * @param value
	 * @param context
	 */
	@SuppressWarnings("static-access")
	public static void savePref(String name, String value, Context context) {
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 加载未切割的图片
	 * 
	 * @param imageView
	 * @param path
	 * @param context
	 */
	public static void loadNetNoCutImage(ImageView imageView, String path, Context context) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		// 加载网络图片
		bitmapUtils.display(imageView, path, new BitmapDisplayConfig(), new BitmapLoadCallBack<View>() {
					@Override
					public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
						Bitmap bitmap = toRoundCorner(arg2, 5.0f);
						((ImageView) arg0).setImageBitmap(bitmap);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
						((ImageView) arg0).setBackgroundResource(R.drawable.head_default_yixin);
					}
				});
	}

	/**
	 * 图片加上圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getBitMapFromView(ImageView imageview) {
		return ((BitmapDrawable) imageview.getDrawable()).getBitmap();

	}

	/**
	 * 把一个字符串中的大写转为小写，小写转换为大写：1
	 * 
	 * @param str
	 * @return
	 */
	public static String exChange(String str) {
		StringBuffer sb = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append(Character.toLowerCase(c));
				} else if (Character.isLowerCase(c)) {
					sb.append(Character.toUpperCase(c));
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 把一个字符串中的大写转为小写，小写转换为大写：2
	 * 
	 * @param str
	 * @return
	 */
	public static String exChange2(String str) {
		for (int i = 0; i < str.length(); i++) {
			// 如果是小写
			if (str.substring(i, i + 1).equals(str.substring(i, i + 1).toLowerCase(Locale.getDefault()))) {
				str.substring(i, i + 1).toUpperCase(Locale.getDefault());
			} else {
				str.substring(i, i + 1).toLowerCase(Locale.getDefault());
			}
		}
		return str;
	}

	/**
	 * 小写转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String exChangeToUp(String str) {
		StringBuffer sb = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (Character.isLowerCase(c)) {
					sb.append(Character.toUpperCase(c));
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 空串转换
	 * 
	 * @param nullStr
	 * @return
	 */
	public static String nullStrToStr(String nullStr) {
		if (nullStr == null) {
			nullStr = "0";
		} else {
			nullStr = nullStr.trim();
			if ("".equals(nullStr)) {
				nullStr = "0";
			} else if ("null".equals(nullStr)) {
				nullStr = "0";
			}
		}
		return nullStr;
	}

	/**
	 * 关键信息替换
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String hiddenNum(String str, int start, int end) {
		int count = end - start + 1;
		String oldStr = str.substring(start, end);
		String newStr = "";
		for (int i = 0; i < count; i++) {
			newStr += "*";
		}
		str = str.replace(oldStr, newStr);
		return str;
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * 加载未切割的图片
	 * 
	 * @param imageView
	 * @param path
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void loadNetNoAnyCutImage(ImageView imageView, String path, Context context) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		// 加载网络图片
		bitmapUtils.display(imageView, path, new BitmapDisplayConfig(),
				new BitmapLoadCallBack<View>() {
					@Override
					public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
						if (arg2 == null) {
							((ImageView) arg0).setBackgroundResource(R.drawable.head_default_yixin);
							return;
						}
						((ImageView) arg0).setImageBitmap(arg2);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
						((ImageView) arg0).setBackgroundResource(R.drawable.head_default_yixin);
					}
				});
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 加载切割的图片
	 * 
	 * @param imageView
	 * @param path
	 * @param context
	 */
	public static void loadNetCutImage(ImageView imageView, String path, Context context, final int width) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		// 加载网络图片
		bitmapUtils.display(imageView, path, new BitmapDisplayConfig(),
				new BitmapLoadCallBack<View>() {
					@Override
					public void onLoadCompleted(View arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
						if (arg2 == null) {
							// ((ImageView)
							// arg0).setBackgroundResource(R.drawable.head_default_yixin);
							return;
						}

						Bitmap bitmap = ImageUtils.getCircleBitmap(arg2, width, width);
						((ImageView) arg0).setImageBitmap(bitmap);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						((ImageView) arg0).setBackgroundResource(R.drawable.head_default_yixin);
					}
				});
	}

	/**
	 * 
	 * 验证手机号格式是否正确。
	 * 
	 * @param mobile
	 *            手机号
	 * @return <ol>
	 *         <li>true-正确；</li>
	 *         <li>false-错误。</li>
	 *         </ol>
	 * 
	 */
	public static boolean isMobileNO(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}

		return mobile.matches(Constants.PHONE_RE);
	}

	public static boolean isPasswordStrOk(String password) {
		// String regex = "^(\\w*(?=\\w*\\d)(?=\\w*[A-Za-z])\\w*){6,15}$";
		// String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
		Pattern p = Pattern.compile(Constants.PASSWD_RE);
		Matcher m = p.matcher(password);
		if (m.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isPasswordDigitStrOk(String password) {
		// String regex = "^(\\w*(?=\\w*\\d)(?=\\w*[A-Za-z])\\w*){6,15}$";
		// String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
		Pattern p = Pattern.compile(Constants.PASSWD_RE2);
		Matcher m = p.matcher(password);
		if (m.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isPasswordDigitStrAndOk(String password) {
		// String regex = "^(\\w*(?=\\w*\\d)(?=\\w*[A-Za-z])\\w*){6,15}$";
		// String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
		Pattern p = Pattern.compile(Constants.PASSWD_RE3);
		Matcher m = p.matcher(password);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	public static boolean isPasswordDigitOk(String password) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(password);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isPasswordCharOk(String password) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isNum = pattern.matcher(password);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 获取偏好。
	 * 
	 * @param context 上下文
	 * @return 偏好
	 * 
	 */
	@SuppressWarnings("static-access")
	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences("config", context.MODE_PRIVATE);
	}

	/**
	 * 把dip单位转成px单位
	 * 
	 * @param context context对象
	 * @param dip dip数值
	 * @return
	 */
	public static int formatDipToPx(Context context, int dip) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return (int) Math.ceil(dip * dm.density);
	}

	/**
	 * 把px单位转成dip单位
	 * 
	 * @param context context对象
	 * @param px px数值
	 * @return
	 */
	public static int formatPxToDip(Context context, int px) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return (int) Math.ceil(((px * 160) / dm.densityDpi));
	}

	public static List<String> getDayAndWeek() {
		String mMonth = "";
		String mDay = "";
		String mWay = "";
		String mYear = "";
		Calendar c = null;
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < 8; i++) {
			c = Calendar.getInstance();
			c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
			c.roll(Calendar.DAY_OF_YEAR, i);
			mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
			mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
			mWay = getWeek(c);
			mYear = String.valueOf(c.get(Calendar.YEAR));
			String temp = mMonth + "/" + mDay + "," + mWay;
			if (mMonth.length() == 1) {
				mMonth = "0" + mMonth;
			}
			if (mDay.length() == 1) {
				mDay = "0" + mDay;
			}
			temp += "," + mYear + "-" + mMonth + "-" + mDay;
			list.add(temp);
		}
		return list;
	}

	static String getWeek(Calendar c) {
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "周日";
		} else if ("2".equals(mWay)) {
			mWay = "周一";
		} else if ("3".equals(mWay)) {
			mWay = "周二";
		} else if ("4".equals(mWay)) {
			mWay = "周三";
		} else if ("5".equals(mWay)) {
			mWay = "周四";
		} else if ("6".equals(mWay)) {
			mWay = "周五";
		} else if ("7".equals(mWay)) {
			mWay = "周六";
		}
		return mWay;
	}

	/**
	 * 
	 * 清空 Config 偏好中保存的登录成功后的注册用户信息。<br>
	 * <b style="color: red;">
	 * 	注意：<br>
	 * 	只有当用户登录时，输入的登录手机号或密码错误时，才应该清空登录密码。
	 * </b>
	 * 
	 * @param context 上下文
	 * @param isClearLoginAccount 是否清空登录密码：
	 * <ol>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ol>
	 * 
	 */
	/*public static void clearLoginUserInfo(Context context, boolean isClearLoginPasswd) {
		SharedPreferences configSP = getSharedPreferences(context);
		Editor editor = configSP.edit();
		// 保存非登陆标志
		editor.putBoolean(Constants.LOGIN_FLAG, false);
		// 非黑名单
		editor.putBoolean(Constants.BLACK_FLAG, false);
		editor.remove(Constants.LOGIN_USER_ID);
		editor.remove(Constants.LOGIN_NAME);
		editor.remove(Constants.LOGIN_ID_NUMBER);
		
		if (isClearLoginPasswd) {
			// editor.remove(Constants.LOGIN_PHONE);
			editor.remove(Constants.LOGIN_PASSWD);
		}
		
		editor.commit();
	}*/
	
	/**
	 * 清除保存的登录信息
	 */
	public static void clearLoginInfo(Context context){
		SharedPreferences configSP = getSharedPreferences(context);
		Editor editor = configSP.edit();
		// 保存非登陆标志
		editor.putBoolean(Constants.LOGIN_FLAG, false);
		editor.remove(Constants.LOGIN_INFO_ID);
		editor.remove(Constants.LOGIN_INFO_PWD);
		editor.remove(Constants.LOGIN_INFO_HISID);
		editor.remove(Constants.LOGIN_INFO_ISDEFAULT);
		editor.remove(Constants.LOGIN_INFO_DEP_NAME);
		editor.remove(Constants.LOGIN_INFO_SUPERVISE);
		editor.remove(Constants.LATEST_VISIT_PATIENT_JSON);
		editor.commit();
		
	}
	
	/**
	 * 清除保存的用户信息
	 */
	public static void clearUserInfo(Context context){
		SharedPreferences configSP = getSharedPreferences(context);
		Editor editor = configSP.edit();
		editor.remove(Constants.USER_ID);
		editor.remove(Constants.USER_ID_NUMBER);
		editor.remove(Constants.USER_NAME);
		editor.remove(Constants.USER_PHONE);
		editor.commit();
	}
	
	/**
	 * 
	 * 判断注册用户是否完善了个人信息。
	 * 
	 * @param context 上下文
	 * @return 注册用户是否完善了个人信息：
	 * <ol>
	 * 	<li>true-是；</li>
	 * 	<li>false-否。</li>
	 * </ol>
	 * 
	 */
	/*public static boolean isPerfectUserInfo(Context context) {
		SharedPreferences configSP = getSharedPreferences(context);
		String loginPhone = configSP.getString(Constants.LOGIN_PHONE, "");
		String loginName = configSP.getString(Constants.LOGIN_NAME, "");
		String loginIdNumber = configSP.getString(Constants.LOGIN_ID_NUMBER, "");
		
		if (StringUtils.isNotEmpty(loginPhone) && //
				StringUtils.isNotEmpty(loginName) && //
				StringUtils.isNotEmpty(loginIdNumber)) {
			return true;
		}
		
		return false;
	}*/
	
	/**
	 * 
	 * 获取页数。
	 * 
	 * @param recordCount 记录数
	 * @param pageSize 页码
	 * @return 页数：
	 * <ol>
	 * 	<li>记录数或者页码小于等于 0，返回 0；</li>
	 * 	<li>记录数大于 0，并且页码大于 0，除以页码，返回页数</li>
	 * </ol>
	 * 
	 */
	public static int getPageNum(int recordCount, int pageSize) {
		if ((recordCount <= 0) || (pageSize <= 0)) {
			return 0;
		} else {
			if (recordCount <= pageSize) {
				return 1;
			}
			
			if ((recordCount % pageSize) == 0) {
				return recordCount / pageSize;
			} else {
				return (recordCount / pageSize) + 1;
			}
		}
	}
	
	/**
	 * 
	 * 点击确认的监听器接口。
	 * 
	 * @author ChenYong
	 * @date 2016-04-29
	 *
	 */
	public interface OnConfirmClickListener {
		/**
		 * 
		 * 点击确认时执行。
		 * 
		 */
		public void onConfirmClickListener();
	}
	
	/**
	 * 
	 * 点击确认和取消的监听器接口。
	 * 
	 * @author ChenYong
	 * @date 2016-04-29
	 *
	 */
	public interface OnClickConfirmListener2 {
		/**
		 * 
		 * 点击确认时执行。
		 * 
		 */
		public void onClickConfirmListener();
		public void onClickCancelListener();
	}
	
	/**
	 * 
	 * 显示确认对话框。
	 * 
	 * @param context 上下文
	 * @param title 标题
	 * @param listener 点击确认的监听器，点击“确定”按钮时执行
	 * 
	 */
	public static void showConfirmDialog(Context context, String title, final Utils.OnConfirmClickListener listener) {
		AlertDialog dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		dialog.setTitle(null);
		dialog.setMessage(title);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onConfirmClickListener();

				dialog.dismiss();
			}
		});

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	

	public static boolean isValidate(List list) {
		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isValidateStr(String str) {
		if(str != null && str.length() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 显示确认对话框。
	 * 
	 * @param context 上下文
	 * @param title 标题
	 * @param listener 点击确认的监听器，点击“确定”按钮时执行
	 * 
	 */
	public static void showConfirmDialog2(Context context, String title, String buSureText, String cancleText, final Utils.OnClickConfirmListener2 listener) {
		AlertDialog dialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
		dialog.setTitle("提示");
		dialog.setMessage(title);
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, buSureText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onClickConfirmListener();
				dialog.dismiss();
			}
		});

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, cancleText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onClickCancelListener();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public static void getViewFocus(View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static void changeViewFocus(View view, boolean isFocus) {
		if (isFocus) {
			getViewFocus(view);
		} else {
			view.setFocusable(isFocus);
			view.setFocusableInTouchMode(isFocus);
		}
	}

	/**
	 * 获取Mac地址
	 * @return
	 */
	public static String getMacAddress() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}
	
	public static String getCurrentYearMon() {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date());// 月份是从0开始的，所以11表示12月
		Date now = ca.getTime();
		ca.add(Calendar.MONTH, -1); // 月份减1
		Date lastMonth = ca.getTime(); // 结果
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		System.out.println(sf.format(now));
		System.out.println(sf.format(lastMonth));
		return sf.format(lastMonth);
	}
	
	public static String getFormerDay(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(new Date());  
	    calendar.add(Calendar.DAY_OF_MONTH, -1);  
	   
	    return format.format(calendar.getTime());
	}
	
}