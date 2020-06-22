package com.ant.recharge.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NetworkUtil {

	public static boolean isOpenNetwork(Context context) {
		if(context == null){
			return false;
		}
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}

	public static void networkStatePrompt(Context context) {
		if (!isOpenNetwork(context)) {
			Toast.makeText(context, "您还没有连接网络，请检查您的网络设置", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static int getCurrentNetType(Context context) {
		int type = -1;

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			type = -1;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = 99;
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {

			switch (info.getSubtype()) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
				type = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				type = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				type = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				type = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_IDEN:
				type = 2;
				break;
			case 16:// GSM
				type = 2;
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				type = 3;
				break;
			case TelephonyManager.NETWORK_TYPE_LTE:
				type = 4;
				break;
			default:
				type = 1;
				break;

			}
		}
		return type;
	}

	public static String getCarrier(Context context) {
		String carrier = "";

		String imsi = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

		if (imsi != null) {

			if (imsi.startsWith("46000") || imsi.startsWith("46002")
					|| imsi.equals("46007")) {
				carrier = "移动";
			} else if (imsi.startsWith("46001")) {
				carrier = "联通";
			} else if (imsi.startsWith("46003")) {
				carrier = "电信";
			}
		}

		return carrier;
	}

	@Deprecated
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

//	public static String getIMEIOrUuid(Context context) {
//		TelephonyManager telephonyManager = (TelephonyManager) context
//				.getSystemService(Context.TELEPHONY_SERVICE);
//		String ret = telephonyManager.getDeviceId();
//		if (ret == null || ret.isEmpty() || ret.equals("")) {
//			ret = getSharedPreferences(context).getString(
//					Crc32Util.Crc32(DEVICEID), "");
//
//			if (ret == null || ret.isEmpty() || ret.equals("")) {
//				ret = UUID.randomUUID().toString().replace("_", "");
//
//				getSharedPreferences(context).edit()
//						.putString(Crc32Util.Crc32(DEVICEID), ret).commit();
//			}
//
//		}
//		return ret;
//	}
//
//	private final static String DEVICEID = "DEVICEID";
//
//	private static SharedPreferences getSharedPreferences(Context context) {
//		return context.getSharedPreferences(BaseConstants.SP_DEVICE,
//				Context.MODE_PRIVATE);
//	}
}
