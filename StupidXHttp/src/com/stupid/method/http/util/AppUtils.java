package com.stupid.method.http.util;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

/**
 * AppUtils
 * <ul>
 * <li>{@link AppUtils#isNamedProcess(Context, String)}</li>
 * </ul>
 * 
 * @about 在这个 <a href="http://www.trinea.cn" target="_blank"> 哥们</a> 的基础上进行添加封装
 */
public class AppUtils {

	public static final String NETWORK_TYPE_2G = "2g";

	public static final String NETWORK_TYPE_3G = "eg";

	public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

	public static final String NETWORK_TYPE_UNKNOWN = "unknown";

	public static final String NETWORK_TYPE_WAP = "wap";

	public static final String NETWORK_TYPE_WIFI = "wifi";

	/**
	 * @return 返回唯一的设备ID，用于GSM和CDMA手机，MEID或ESN的IMEI。如果设备标识不可用，则返回空。
	 * 
	 * 
	 */
	public static String getDeviceID(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * Get network type
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager == null ? null
				: connectivityManager.getActiveNetworkInfo();
		return networkInfo == null ? -1 : networkInfo.getType();
	}

	/**
	 * 获得网络名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetworkTypeName(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo;
		String type = NETWORK_TYPE_DISCONNECT;
		if (manager == null
				|| (networkInfo = manager.getActiveNetworkInfo()) == null) {
			return type;
		}

		if (networkInfo.isConnected()) {
			String typeName = networkInfo.getTypeName();
			if ("WIFI".equalsIgnoreCase(typeName)) {
				type = NETWORK_TYPE_WIFI;
			} else if ("MOBILE".equalsIgnoreCase(typeName)) {
				String proxyHost = android.net.Proxy.getDefaultHost();
				type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G
						: NETWORK_TYPE_2G)
						: NETWORK_TYPE_WAP;
			} else {
				type = NETWORK_TYPE_UNKNOWN;
			}
		}
		return type;
	}

	/**
	 * Gets the Package Name
	 * 
	 * @return
	 */
	public static String getPackageName(Context context) {
		PackageManager p = context.getPackageManager();
		// GetPackageName () is your current class package name, 0 stands for is
		// to get version information
		PackageInfo packInfo;
		try {
			packInfo = p.getPackageInfo(context.getPackageName(), 0);
			return packInfo.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获得app版本号
	 * 
	 * @Description: TODO
	 * @return
	 */
	public static int getVersionCode(Context context) {
		PackageManager p = context.getPackageManager();
		// GetPackageName () is your current class package name, 0 stands for is
		// to get version information
		PackageInfo packInfo;
		try {
			packInfo = p.getPackageInfo(context.getPackageName(), 0);

			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获得版本号
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageManager p = context.getPackageManager();
		// GetPackageName () is your current class package name, 0 stands for is
		// to get version information
		PackageInfo packInfo;
		try {
			packInfo = p.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 网络判断
	 * 
	 * @return
	 */
	public static boolean hasInternet(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	/**
	 * whether application is in background
	 * <ul>
	 * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
	 * </ul>
	 * 
	 * @param context
	 * @return if application is in background return true, otherwise return
	 *         false
	 */
	public static boolean isApplicationInBackground(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList != null && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
			if (topActivity != null
					&& !topActivity.getPackageName().equals(
							context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Whether is fast mobile network
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager == null) {
			return false;
		}

		switch (telephonyManager.getNetworkType()) {

		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_EHRPD:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
		case TelephonyManager.NETWORK_TYPE_HSPAP:
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true;
		case TelephonyManager.NETWORK_TYPE_IDEN:
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_GPRS:
		default:
			return false;

		}
	}

	/**
	 * whether this process is named with processName
	 * 
	 * @param context
	 * @param processName
	 * @return <ul>
	 *         return whether this process is named with processName
	 *         <li>if context is null, return false</li>
	 *         <li>if {@link ActivityManager#getRunningAppProcesses()} is null,
	 *         return false</li>
	 *         <li>if one process of
	 *         {@link ActivityManager#getRunningAppProcesses()} is equal to
	 *         processName, return true, otherwise return false</li>
	 *         </ul>
	 */
	public static boolean isNamedProcess(Context context, String processName) {
		if (context == null) {
			return false;
		}

		int pid = android.os.Process.myPid();
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfoList = manager
				.getRunningAppProcesses();
		if (ListUtils.isEmpty(processInfoList)) {
			return false;
		}

		for (RunningAppProcessInfo processInfo : processInfoList) {
			if (processInfo != null
					&& processInfo.pid == pid
					&& ObjectUtils.isEquals(processName,
							processInfo.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断当前线程是否是主线程
	 */
	public static boolean isUIThread() {

		return Looper.getMainLooper().getThread().getId() == Thread
				.currentThread().getId();

	}

	/**
	 * 判断WiFi已连接并可用
	 * 
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI")
							&& info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private AppUtils() {
		throw new AssertionError();
	}

	public static float dp2Px(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float px2Dp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取当前设备的电话号码 <BR>
	 * 
	 */
	public String getNativePhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String NativePhoneNumber = "NaN";
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * 获取手机服务商信息</br> </br>
	 * 
	 * 需要加入权限:
	 * 
	 * &lt;uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/&gt;
	 */
	public String getProvidersName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String ProvidersName = "未知的运营商";
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}

	/**
	 * 获得屏幕亮度信息
	 * 
	 * 0最暗<br>
	 * 
	 * 1最亮<br>
	 * 
	 * -1 是系统默认亮度
	 * **/
	public static float getBrightness(Activity activity) {

		return activity.getWindow().getAttributes().screenBrightness;
	}

	/**
	 * This can be used to override the user's preferred brightness of the
	 * screen. A value of less than 0, the default, means to use the preferred
	 * screen brightness. 0 to 1 adjusts the brightness from dark to full
	 * bright. <br>
	 * 0最暗<br>
	 * 
	 * 1最亮<br>
	 * 
	 * -1 是系统默认亮度
	 */
	public static boolean getBrightness(final Activity activity,
			float brightness) {
		final WindowManager.LayoutParams layoutParams = activity.getWindow()
				.getAttributes();
		layoutParams.screenBrightness = brightness;

		if (isUIThread())
			activity.getWindow().setAttributes(layoutParams);
		else
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					activity.getWindow().setAttributes(layoutParams);

				}
			});

		return false;

	}
}
