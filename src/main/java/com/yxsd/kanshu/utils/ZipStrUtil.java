package com.yxsd.kanshu.utils;

import org.apache.commons.net.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * Description: 对字符串的压缩及解压
 * All Rights Reserved.
 * @version 1.0  2017年9月20日 下午3:26:10  by 孙亮  (sunliang01@dangdang.com) 创建
 */
public class ZipStrUtil {


    /**
     * 字符串的压缩
     *
     * @param str
     *            待压缩的字符串
     * @return    返回压缩后的字符串
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
//        System.out.println("String length : " + str.length());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String outStr = new String(Base64.encodeBase64(out.toByteArray()));
//        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
    }

    /**
     * 字符串的解压
     *
     * @param str
     *            对字符串解压
     * @return    返回解压缩后的字符串
     * @throws IOException
     */
    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
//        System.out.println("Input String length : " + str.length());
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(str));
        GZIPInputStream gis = new GZIPInputStream(in);
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        int n;
        while ((n = gis.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        String outStr = new String(out.toByteArray());
//        System.out.println("Output String lengh : " + outStr.length());
        return outStr;
    }

    public static void main(String[] args) throws IOException {
        // 字符串超过一定的长度
        String str = "ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" +
                "LLppppppppppppppppppppppppppppppppppppppppp===========================------------------------------iiiiiiiiiiiiiiiiiiiiiii";
        System.out.println("\n原始的字符串为------->" + str);
        float len0=str.length();
        System.out.println("原始的字符串长度为------->"+len0);

        String ys = compress(str);
        System.out.println("\n压缩后的字符串为----->" + ys);
        float len1=ys.length();
        System.out.println("压缩后的字符串长度为----->" + len1);

//        String jy = unCompress(ys);
        String jy = decompress(ys);
        System.out.println("\n解压缩后的字符串为--->" + jy);
        System.out.println("解压缩后的字符串长度为--->"+jy.length());

        System.out.println("\n压缩比例为"+len1/len0);

        //判断
        if(str.equals(jy)){
            System.out.println("先压缩再解压以后字符串和原来的是一模一样的");
        }
    }
}
