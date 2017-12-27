package com.yxsd.kanshu.intecepter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * request装饰器，用于复合接口 Description: All Rights Reserved.
 * 
 * @version 1.0 2014-11-13 下午06:27:06 by 王冠华（wangguanhua@dangdang.com）创建
 */
public class MultiActionRequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String> params;

	public MultiActionRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public MultiActionRequestWrapper(HttpServletRequest request, Map<String, String> paramMap) {
		super(request);
		params = paramMap;
	}

	public String getParameter(String name) {
		if (params != null && params.containsKey(name) && StringUtils.isNotBlank(name)) {
			return params.get(name);
		}
		return null;
	};

	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		Set<Entry<String, String>> set = params.entrySet();
		for (Entry<String, String> entry : set) {
			if (StringUtils.isNotBlank(entry.getKey())) {
				paramMap.put(entry.getKey(), new String[] { entry.getValue() });
			}
		}
		return paramMap;
	}

	public String[] getParameterValues(String name) {
		return new String[] { params.get(name) };
	}
}
