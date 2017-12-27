package com.yxsd.kanshu.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base64调整.
 *
 */
public abstract class Base64AlterUtils {
	private static char getChar(int a) {
		if (a >= 0 && a <= 12) {
			return (char) ('a' + a);
		} else if (a >= 13 && a <= 25) {
			return (char) ('N' + a - 13);
		} else if (a == 26) {
			return (char) ('!');
		} else if (a >= 27 && a <= 39) {
			return (char) ('A' + a - 27);
		} else if (a == 40) {
			return (char) ('-');
		} else if (a >= 41 && a <= 53) {
			return (char) ('n' + a - 41);
		} else if (a >= 54 && a <= 62) {
			return (char) ('0' + a - 54);
		} 
		return '9';
	}

	private static int getByte(int b) {
		if (b >= 97 && b <= 109) {
			return (b - 97);
		} else if (b >= 78 && b <= 90) {
			return (b - 65);
		} else if (b == 33) {
			return 26;
		} else if (b >= 65 && b <= 77) {
			return (b - 38);
		} else if (b == 45) {
			return 40;
		} else if (b >= 110 && b <= 122) {
			return (b - 69);
		} else if (b >= 48 && b <= 56) {
			return (b + 6);
		} else if (b == 57) {
			return (b + 6);
		}
		return -1;
	}
	
    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(getChar(b1 >>> 2));
                sb.append(getChar((b1 & 0x3) << 4));
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(getChar(b1 >>> 2));
                sb.append(getChar(((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)));
                sb.append(getChar((b2 & 0x0f) << 2));
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(getChar(b1 >>> 2));
            sb.append(getChar(((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)));
            sb.append(getChar(((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)));
            sb.append(getChar(b3 & 0x3f));
        }
        return sb.toString();
    }

    public static byte[] convert(List<Byte> list) {
    	byte[] ret = new byte[list.size()];
    	for (int i = 0; i < list.size(); i++) {
    		ret[i] = list.get(i);
    	}
    	return ret;
    }
    
    public static byte[] decode(String str) throws UnsupportedEncodingException {
    	List<Byte> list = new ArrayList<Byte>(); 
        byte[] data = str.getBytes("UTF-8");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            do {
                b1 = getByte(data[i++]);
            } while (i < len && b1 == -1);
            if (b1 == -1) {
            	break;
            }
           
            do {
                b2 = getByte(data[i++]);
            } while (i < len && b2 == -1);
            if (b2 == -1) {
            	break;
            }
            list.add((byte) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
           
            do {
                b3 = data[i++];
                if (b3 == 61) {
                	return convert(list);
                }
                b3 = getByte(b3);
            } while (i < len && b3 == -1);
            if (b3 == -1) {
            	break;
            }
            list.add((byte) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
           
            do {
                b4 = data[i++];
                if (b4 == 61) {
                	return convert(list);
                }
                b4 = getByte(b4);
            } while (i < len && b4 == -1);
            if (b4 == -1) {
            	break;
            }
            list.add((byte) (((b3 & 0x03) << 6) | b4));
        }
        return convert(list);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "由于系统服务都是以动态链接库(DLL)a形式实现的，abc098-0987986564765gfhdfgh它们把可执行程序";
        System.out.println("加密前：" + s);
        String x = encode(s.getBytes());
        System.out.println("加密后：" + x);
        String x1 = new String(decode(x), "UTF-8");
        System.out.println("解密后：" + x1);
    }
}
