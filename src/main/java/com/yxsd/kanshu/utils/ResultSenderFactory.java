package com.yxsd.kanshu.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * API返回结果的处理类的工厂.
 * 
 * @author yangming
 *
 */
public abstract class ResultSenderFactory {

	private ResultSenderFactory() {
	}

	public static ResultSender getSender(HttpServletRequest request, String tpl) {
		return new JsonResultSender();
	}
}
