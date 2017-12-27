package com.yxsd.kanshu.processor;

import com.yxsd.kanshu.exception.ApiException;
import com.yxsd.kanshu.utils.ResultSender;
import com.yxsd.kanshu.utils.ResultSenderFactory;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: Api接口的基类
 * All Rights Reserved.
 * @version 1.0  2017年12月25日  by  hushengmeng 创建
 */
public abstract class ApiProcessor {	
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 处理API请求, 在Controller中调用.
	 */
	public abstract void handle(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender, String action) throws Exception;
	
	/**
	 * 子类实现的方法, 处理相应的业务逻辑.
	 */
	protected abstract void process(HttpServletRequest request, 
			HttpServletResponse response, ResultSender sender) throws Exception;
		
	public String getParameter(HttpServletRequest request, String key, String alternative, String defaultValue) {
		String value = request.getParameter(key);
		return StringUtils.isNotBlank(value) ? value : get(request, alternative, defaultValue);
	}
	
	/**
	 * 获取当前会话的token.
	 */
	protected String getToken(HttpServletRequest request) {
		return request.getSession().getId() + RandomStringUtils.randomAlphabetic(6);
	}	
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String get(HttpServletRequest request, String key, String defaultValue) {
		if ("deviceSerialNo".equals(key) || "deviceSn".equals(key)) {
			if (StringUtils.isNotBlank(request.getParameter("deviceSn"))) {
				return request.getParameter("deviceSn");
			} else if (StringUtils.isNotBlank(request.getParameter("deviceSerialNo"))) {
				return request.getParameter("deviceSerialNo");
			} else if (StringUtils.isNotBlank(request.getParameter("macAddr"))) {
				return request.getParameter("macAddr");
			} else {
				return defaultValue;
			}
		}
		return StringUtils.isBlank(request.getParameter(key)) ? defaultValue : request.getParameter(key);
	}
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws ApiException 
	 */
	protected String getRequiredParam(HttpServletRequest request, String key) throws ApiException {
		String param = request.getParameter(key);
		if (StringUtils.isBlank(param)) {
			throw ApiException.invalidParams(key);
		}
		return param;
	}	
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 * @throws ApiException 
	 */
	protected int getInt(HttpServletRequest request, String key) throws ApiException {
		String value = request.getParameter(key);
		if (StringUtils.isNotBlank(value)) {
			try {
				return Integer.valueOf(value);
			} catch (Exception e) {
				throw ApiException.invalidParams();
			}
		}
		throw ApiException.invalidParams();
	}
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 * @throws ApiException 
	 */
	protected int getInt(HttpServletRequest request, String key, int defaultValue) throws ApiException {
		String value = request.getParameter(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			throw ApiException.invalidParams();
		}
	}
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 * @throws ApiException 
	 */
	protected long getLong(HttpServletRequest request, String key) throws ApiException {
		String value = request.getParameter(key);
		if (StringUtils.isBlank(value)) {
			throw ApiException.invalidParams();
		}
		try {
			return Long.valueOf(value);
		} catch (Exception e) {
			throw ApiException.invalidParams();
		}
	}
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 * @throws ApiException 
	 */
	protected boolean getBoolean(HttpServletRequest request, String key) throws ApiException {
		return "true".equalsIgnoreCase(request.getParameter(key));
	}
	
	
	/**
	 * 获取参数.
	 * @param request
	 * @param key
	 * @param value
	 * @return
	 */
	protected String getAttribute(HttpServletRequest request, String key, String value) {
		return ObjectUtils.toString(request.getAttribute(key), value);
	}
	
	/**
	 * 获取API返回结果处理类.
	 * @param request
	 * @param tpl
	 * @return
	 */
	protected ResultSender getSender(HttpServletRequest request, String tpl) {
		return ResultSenderFactory.getSender(request, tpl);
	}
	
	/**
	 * 检查必选参数不能为空.
	 * @param params 参数值
	 * @throws ApiException 参数无效异常
	 */
	protected static boolean checkParams(String... params) throws ApiException {
		for (String param : params) {
			if (StringUtils.isBlank(param)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Title: checkParamsNotAllAreEmpty
	 * @Description: 检查是否所有传递进来的参数都为空
	 * @param params
	 * @throws ApiException
	 */
	protected static boolean checkParamsNotAllAreEmpty(String... params) throws ApiException{
		for (String param : params) {
			if (StringUtils.isNotBlank(param)) {
				return true;
			}
		}
		return false;
	}

    /**
     * @Title: isDigit
     * @Description: 检查传递过来的参数是否为大于0
     * @param param
     */
    protected static boolean isDigit(String  param){

        boolean flag = false;
        Pattern pattern = Pattern.compile("^([0-9])+(\\.[0-9]+)?$");
        Matcher matcher = pattern.matcher(param);
        flag = matcher.matches();
        return flag;

    }



}
