package com.yxsd.kanshu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
 
/**
 * @Description: gzip压缩工具包
 */
 public class GZIPUtil {
		/**
		 * 
		 * @Title: compress
		 * @Description: 数据压缩
		 * @param data
		 * @return
		 * @throws Exception
		 */
		public static  byte[] compress(byte[] data) throws Exception {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 压缩
			GZIPOutputStream gos = new GZIPOutputStream(baos);

			gos.write(data, 0, data.length);

			gos.finish();

			byte[] output = baos.toByteArray();

			baos.flush();
			baos.close();

			return output;
		}
		/**
		 * @Title: unCompress
		 * @Description: 数据解压
		 * @param str
		 * @return
		 * @throws IOException
		 */
		public static String unCompress(byte[] str) throws IOException {
	        if (null == str || str.length <= 0) {
	            return str.toString();
	        }
	        // 创建一个新的 byte 数组输出流
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
	        ByteArrayInputStream in = new ByteArrayInputStream(str);
	        // 使用默认缓冲区大小创建新的输入流
	        GZIPInputStream gzip = new GZIPInputStream(in);
	        byte[] buffer = new byte[256];
	        int n = 0;
	        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
	            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
	            out.write(buffer, 0, n);
	        }
	        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
	        return out.toString("utf-8");
	    }
		public static void main(String[] args) throws Exception {
			String json = "hsahfkjhaskhkjsaffffffffffhkjh手机话费就撒谎会计法哈萨克交话费卡健身房卡萨交话费恐惧啊很舒服夸奖哈送咖啡哈市空间" +
					"hsahfkjhaskhkjsaffffffffffhkjh手机话费就撒谎会计法哈萨克交话费卡健身房卡萨交话费恐惧啊很舒服夸奖哈送咖啡哈市空间撒" +
					"hsahfkjhaskhkjsaffffffffffhkjh手机话费就撒谎会计法哈萨克交话费卡健身房卡萨交话费恐惧啊很舒服夸奖哈送咖啡哈市空间撒" +
					"hsahfkjhaskhkjsaffffffffffhkjh手机话费就撒谎会计法哈萨克交话费卡健身房卡萨交话费恐惧啊很舒服夸奖哈送咖啡哈市空间撒" +
					"hsahfkjhaskhkjsaffffffffffhkjh手机话费就撒谎会计法哈萨克交话费卡健身房卡萨交话费恐惧啊很舒服夸奖哈送咖啡哈市空间撒撒";
			byte[] oo = compress(json.getBytes("utf-8"));
			String result = unCompress(oo);
			System.out.println(result);
		}
 }

