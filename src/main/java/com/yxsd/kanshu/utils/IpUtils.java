package com.yxsd.kanshu.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * IP工具类.
 */
public abstract class IpUtils {
	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
	public static final String IP_REGEXP_1 = "(\\d{1,3}\\.){3}\\d{1,3}";
	public static final String IP_REGEXP_2 = "(\\d{1,3}\\.){1}((\\d{1,3}|\\*)\\.){2}(\\d{1,3}|\\*)";
	public static final String IP_REGEXP_3 = "(\\d{1,3}\\.){3}\\d{1,3}\\s*-\\s*(\\d{1,3}\\.){3}\\d{1,3}";
	public static final String IP_REGEXP_4 = "(\\d{1,3}\\.){3}\\d{1,3}\\s*,\\s*(\\d{1,3}\\.){3}\\d{1,3}";

	public static final String IP_REGEXP_5 = 
		"((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";
	public static final String IP_REGEXP_6 = 
		"((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]|\\*)\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]|\\*)";

	private IpUtils() {
		
	}

	/**
	 * 将整数表示的ip地址转换为字符串表示.
	 *
	 * @param ip 32位整数表示的ip地址
	 * @return 点分式表示的ip地址
	 */
	public static final String long2Ip(final long ip) {
		final long[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		final StringBuilder ipAddress = new StringBuilder();
		for (int i = 0; i < mask.length; i++) {
			ipAddress.insert(0, (ip & mask[i]) >> (i * 8));
			if (i < mask.length - 1) {
				ipAddress.insert(0, ".");
			}
		}
		return ipAddress.toString();
	}

	/**
	 * 比较IP地址.
	 *
	 * @param startIp
	 * @param endIp
	 * @return startIp小于或等于endIp时返回True，否则返回False
	 * @throws Exception
	 */
	public static final boolean compareIP(final String startIp, final String endIp)
			throws Exception {
		if (!Pattern.matches(IP_REGEXP_5, startIp)
				|| !Pattern.matches(IP_REGEXP_5, endIp)) {
			throw new Exception("ip地址格式不正确");
		}
		final String[] ipSplitArray1 = startIp.trim().split("\\.");
		final String[] ipSplitArray2 = endIp.trim().split("\\.");
		if ((Integer.parseInt(ipSplitArray2[0]) > Integer
				.parseInt(ipSplitArray1[0]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) > Integer
						.parseInt(ipSplitArray1[1]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) == Integer
						.parseInt(ipSplitArray1[1]))
				&& (Integer.parseInt(ipSplitArray2[2]) > Integer
						.parseInt(ipSplitArray1[2]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) == Integer
						.parseInt(ipSplitArray1[1]))
				&& (Integer.parseInt(ipSplitArray2[2]) == Integer
						.parseInt(ipSplitArray1[2]))
				&& (Integer.parseInt(ipSplitArray2[3]) >= Integer
						.parseInt(ipSplitArray1[3]))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）.
	 */
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		if(ipString!=null){
			final String[] arr = ipString.split(",");
			for (final String str : arr) {
				if (!"unknown".equalsIgnoreCase(str)) {
					ipString = str;
					break;
				}
			}
		}
		return ipString;
	}

	/**
	 * 比较IP地址是否相同.
	 *
	 * @param startIp 要查看的IP地址
	 * @param endIp 表示的ip地址范围
	 * @return 相同返回True，否则返回False
	 * @throws Exception
	 */
	public static final boolean equalsIP(final String startIp, final String endIp)
			throws Exception {
		if (!Pattern.matches(IP_REGEXP_5, startIp)
				|| !Pattern.matches(IP_REGEXP_6, endIp)) {
			throw new Exception("ip地址格式不正确");
		}
		final String[] ipSplitArray1 = startIp.trim().split("\\.");
		final String[] ipSplitArray2 = endIp.trim().split("\\.");
		if ("*".equals(ipSplitArray2[0])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& "*".equals(ipSplitArray2[1])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& "*".equals(ipSplitArray2[2])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& ipSplitArray1[2].equals(ipSplitArray2[2])
				&& "*".equals(ipSplitArray2[3])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& ipSplitArray1[2].equals(ipSplitArray2[2])
				&& ipSplitArray1[3].equals(ipSplitArray2[3])) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是有效的IP地址.
	 */
	public static final boolean isIP(final String ipStr) {
		if (ipStr == null) {
			return false;
		}
		return Pattern.matches(IP_REGEXP_5, ipStr);
	}

	/**
	 * 判断IP表达式中是否带“*”号.
	 */
	public static final boolean isHaveAsteriskStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf("*") != -1;
	}

	/**
	 * 判断IP表达式中是否带“-”号.
	 */
	public static final boolean isHaveMinusStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf("-") != -1;
	}

	/**
	 * 判断IP表达式中是否带“,”号.
	 */
	public static final boolean isHaveCommaStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf(",") != -1;
	}



	public static String getRemoteHost(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		logger.info("access--ip-info : " + JSON.toJSONString(ip));
		if(StringUtils.isNotBlank(ip)){
			ip = ip.split(",")[0];
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}

	public static boolean checkForIp(HttpServletRequest request,String whiteIp,String actionName,Object... params){
		if(StringUtils.isBlank(whiteIp)){
			return true;
		}
		String ip = null;
		try {
			ip = getRemoteHost(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(ip)){
			ip = "unknown";
		}
		logger.info("access--info : ip == " + JSON.toJSONString(ip) + ",actionName == " + JSON.toJSONString(actionName) + ",params == " + JSON.toJSONString(params));
		for(String ipStr : whiteIp.split(",")){
			if(ip.startsWith(ipStr)){
				return true;
			}
		}
		return false;
	}
}
