package com.ant.recharge.common;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 统一Log方法 根据Profile.DEBUGABLE输出日志到logcat并写入sidebug文件夹
 */
public class LogUtil {
	private final static String LogDir = "sidebug";
	private final static String LogFileFormate = "yyyyMMdd";
	private final static String LogFormate = "HHmmss";

	public static void i(Object o, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg);
			Log.i(o.getClass().getName(), msg);
		}
	}

	public static void i(Object o, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg, tr);
			Log.i(o.getClass().getName(), msg, tr);
		}
	}

	public static void i(String classname, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg);
			Log.i(classname, msg);
		}
	}

	public static void i(String classname, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg, tr);
			Log.i(classname, msg, tr);
		}
	}

	public static void w(Object o, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg);
			Log.w(o.getClass().getName(), msg);
		}
	}

	public static void w(Object o, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg, tr);
			Log.w(o.getClass().getName(), msg, tr);
		}
	}

	public static void w(String classname, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg);
			Log.w(classname, msg);
		}
	}

	public static void w(String classname, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg, tr);
			Log.w(classname, msg, tr);
		}
	}

	public static void v(Object o, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg);
			Log.v(o.getClass().getName(), msg);
		}
	}

	public static void v(Object o, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg, tr);
			Log.v(o.getClass().getName(), msg, tr);
		}
	}

	public static void v(String classname, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg);
			Log.v(classname, msg);
		}
	}

	public static void v(String classname, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg, tr);
			Log.v(classname, msg, tr);
		}
	}

	public static void e(Object o, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg);
			Log.e(o.getClass().getName(), msg);
		}
	}

	public static void e(Object o, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg, tr);
			Log.e(o.getClass().getName(), msg, tr);
		}
	}

	public static void e(String classname, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg);
			Log.e(classname, msg);
		}
	}

	public static void e(String classname, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg, tr);
			Log.e(classname, msg, tr);
		}
	}

	public static void d(Object o, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg);
			Log.d(o.getClass().getName(), msg);
		}
	}

	public static void d(Object o, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(o.getClass().getSimpleName(), msg, tr);
			Log.d(o.getClass().getName(), msg, tr);
		}
	}

	public static void d(String classname, String msg) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg);
			Log.d(classname, msg);
		}
	}

	public static void d(String classname, String msg, Throwable tr) {
		if (Profile.DEBUGABLE) {
			writeLog(classname, msg, tr);
			Log.d(classname, msg, tr);
		}
	}

	private static File getLogPath() {
		File dir = new File(Environment.getExternalStorageDirectory(), LogDir);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(dir, new SimpleDateFormat(LogFileFormate,
				Locale.getDefault()).format(Calendar.getInstance(
				Locale.getDefault()).getTime()));

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				LogUtil.d(LogUtil.class.getSimpleName(), e.getMessage());
			}
		}

		return file;
	}

	public static boolean writeLog(String classname, String msg) {
		boolean res = false;

		try {

			String time = new SimpleDateFormat(LogFormate, Locale.getDefault())
					.format(Calendar.getInstance(Locale.getDefault()).getTime());

			String log = time + "|" + classname + "|" + msg;

			res = FileUtil.writeToNextLine(getLogPath(), log);

		} catch (Exception e) {
		}

		return res;
	}

	public static boolean writeLog(String classname, String msg, Throwable tr) {
		boolean res = false;

		try {

			String time = new SimpleDateFormat(LogFormate, Locale.getDefault())
					.format(Calendar.getInstance(Locale.getDefault()).getTime());

			String log = time + "|" + classname + "|" + msg + "\n"
					+ Log.getStackTraceString(tr);

			res = FileUtil.writeToNextLine(getLogPath(), log);

		} catch (Exception e) {
		}

		return res;
	}
}
