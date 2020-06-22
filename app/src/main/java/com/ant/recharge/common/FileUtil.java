package com.ant.recharge.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * 文件工具类
 */
public class FileUtil {

//	/**
//	 * @Title: checkMd5
//	 * @Description: 计算文件MD5
//	 * @Auther: LiuZuo liuzuo@neusoft.com
//	 * @Data: 2015年3月16日上午9:00:15
//	 * @param file
//	 * @param md5
//	 * @return
//	 */
//	public static boolean checkMd5(File file, String md5) {
//		return (Md5Util.md5File(file).equals(md5));
//	}

	/**
	 * @Title: deleteAllFiles
	 * @Description: 删除root文件夹下所有文件
	 * @Auther: LiuZuo liuzuo@neusoft.com
	 * @Data: 2015年3月16日上午9:00:43
	 * @param root
	 */
	public static void deleteAllFiles(File root) {
		if (root.exists() && root.isDirectory()) {
			File files[] = root.listFiles();
			if (files != null)
				for (File f : files) {
					if (f.isDirectory()) { // 判断是否为文件夹
						deleteAllFiles(f);
						try {
							f.delete();
						} catch (Exception e) {
						}
					} else {
						if (f.exists()) { // 判断是否存在
							deleteAllFiles(f);
							try {
								f.delete();
							} catch (Exception e) {
							}
						}
					}
				}
		} else {
			if (root.exists())
				root.delete();
		}
	}

	/**
	 * @Title: writeToNextLine
	 * @Description: 按行写入文件
	 * @Auther: LiuZuo liuzuo@neusoft.com
	 * @Data: 2015年3月16日上午9:01:36
	 * @param logPath
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static boolean writeToNextLine(File logPath, String json)
			throws Exception {
		boolean res = false;

		try {
			FileOutputStream os = new FileOutputStream(logPath, true);

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os,
					"UTF-8");
			BufferedWriter buffer = new BufferedWriter(outputStreamWriter);
			buffer.write(json + "\n");
			buffer.flush();
			buffer.close();

			res = true;
		} catch (Exception e) {
			throw e;
		}

		return res;
	}
}
