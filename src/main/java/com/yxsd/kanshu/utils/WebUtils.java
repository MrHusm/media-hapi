package com.yxsd.kanshu.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * web工具类 Description: All Rights Reserved.
 * 
 * @version 1.0 2015年12月10日 下午8:16:55 by 王冠华（wangguanhua@dangdang.com）创建
 */
public class WebUtils {

	public static final String COOKIE_SESSIONID = "sessionID";
	
	public static final String COOKIE_FROM_PLATFORM = "MDD_fromPlatform";
	
	public static final String COOKIE_CHANNELID = "MDD_channelId";
	
	
	public static Map<String, Object> getRequestParamsMap(
			HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		if (params == null || params.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			if (values != null) {
				result.put(key, values[0]);
			}
		}
		return result;
	}

	/**
	 * 从cookie里面取值 Description:
	 * 
	 * @Version1.0 2015年12月11日 下午7:15:07 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static Cookie getCookie(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	
	/**
	 * 从cookie里面取值 Description:
	 * 
	 * @Version1.0 2015年12月11日 下午7:15:07 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static String getCookieValue(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
