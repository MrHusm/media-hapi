package com.yxsd.kanshu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	protected static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 
	 * Description: 使用文件通道方式复制文件夹
	 * 
	 * @Version1.0 2015年9月8日 上午10:50:00 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param srcDir
	 * @param destDir
	 * @throws IOException
	 */
	public static void directoryChannelCopy(File srcDir, File destDir) throws IOException {
		// 如果不是文件夹直接拷贝
		if (!srcDir.isDirectory()) {
			fileChannelCopy(srcDir, destDir);
			return;
		}
		// recurse
		File[] files = srcDir.listFiles();
		if (files == null) { // null if security restricted
			throw new IOException("Failed to list contents of " + srcDir);
		}
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException("Destination '" + destDir + "' exists but is not a directory");
			}
		} else {
			if (destDir.mkdirs() == false) {
				throw new IOException("Destination '" + destDir + "' directory cannot be created");
			}
		}
		if (destDir.canWrite() == false) {
			throw new IOException("Destination '" + destDir + "' cannot be written to");
		}
		for (File file : files) {
			File copiedFile = new File(destDir, file.getName());
			if (file.isDirectory()) {
				directoryChannelCopy(file, copiedFile);
			} else {
				fileChannelCopy(file, copiedFile);
			}
		}

	}

	/**
	 * 
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            复制到的新文件
	 */
	public static void fileChannelCopy(File srcFile, File destFile) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(srcFile);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}

			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			fo = new FileOutputStream(destFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			log.error("复制文件异常", e);
			// 复制失败
			String msg = "文件" + srcFile.getAbsolutePath() + "复制到" + destFile.getAbsolutePath() + "失败";
			log.error(msg);
			e.printStackTrace();

		} finally {
			try {
				if (fi != null) {
					fi.close();
				}
				if (in != null) {
					in.close();
				}
				if (fo != null) {
					fo.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
