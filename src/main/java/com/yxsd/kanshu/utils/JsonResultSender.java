package com.yxsd.kanshu.utils;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;

/**
 * JSON格式的返回结果处理类.
 * 
 * @author yangming
 * 
 */
public class JsonResultSender extends ResultSender {

	/**
	 * 返回结果.
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void send(HttpServletResponse response) throws Exception {
		success(response);
	}

	/**
	 * 返回成功结果.
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void success(HttpServletResponse response) throws Exception {
		put("statusCode", SUCCESS_CODE);
		put("errorCode", SUCCESS_CODE);
		put("currentDate", DateUtil.getDateTime(new Date()));
		putSuccessStatus();
		sendResult(model, response);
	}

	/**
	 * 返回失败结果.
	 *
	 * @param response
	 * @throws Exception
	 */
	public void fail(int errorCode, String message, HttpServletResponse response) throws Exception {
		putErrorInfo("statusCode", FAIL_CODE);
		putErrorInfo("errorCode", errorCode);
		putErrorStatus();
		sendResult(error, response);
	}

    /**
     * 返回失败结果和数据
     *
     * @param response
     * @throws Exception
     */
    public void failWithInfo(int errorCode, String message, HttpServletResponse response) throws Exception {
        putErrorInfo("statusCode", FAIL_CODE);
        putErrorInfo("errorCode", errorCode);
        put("currentDate", DateUtil.getDateTime(new Date()));
        putErrorStatus();
        sendResult(model, response);
    }




    public void multiActionPartFail(HttpServletResponse response) throws Exception {
		putErrorInfo("statusCode", SUCCESS_CODE);
		putErrorInfo("errorCode", SUCCESS_CODE);
		putErrorStatus();
		sendResult(error, response);
	}

	public void sendResult(Map model, HttpServletResponse response) throws Exception {
		String result = "";
		String resultContentJson = ""; 
		if (!CollectionUtils.isEmpty(ebookFiterFields)) {
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter();
			spp.setExcludes(ebookFiterFields);
			result = JSON.toJSONString(model, spp);
		} else {
			result = JSON.toJSONString(model);
		}
		
		resultContentJson = dealWithResultContentJson(model);
		setResultContent(resultContentJson);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-Length", String.valueOf( result.getBytes("utf-8").length));
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 
	 * @Title: dealWithResultContentJson
	 * @Description: TODO处理优化api客户端ddclick存储内容去掉作者简介authorIntroduce与目录catalog；
	 * 优化mobileBook显示内容(productId,name,title,desc,price,iosprice)
	 * @param model
	 * @return
	 */
	private String dealWithResultContentJson(Map<String, String> model) {
		if (model.containsKey("authorIntroduce")) {
			model.remove("authorIntroduce");
		}
		if (model.containsKey("catalog")) {
			model.remove("catalog");
		}
		SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
		Set<String> ebookReturnSet = new HashSet<String>();
		ebookReturnSet.add("productId");
		ebookReturnSet.add("bookName");
		ebookReturnSet.add("author");
		ebookReturnSet.add("price");
		ebookReturnSet.add("publisher");
		ebookReturnSet.add("publishDate");
		simplePropertyPreFilter.setIncludes(ebookReturnSet);
		if (CollectionUtils.isNotEmpty(ebookFiterFields) || CollectionUtils.isNotEmpty(ebookHtmlFilterFields) ||
				CollectionUtils.isNotEmpty(ebookLineFilterFields)) {
			if (CollectionUtils.isNotEmpty(ebookFiterFields)) {
				simplePropertyPreFilter.setExcludes(ebookFiterFields);
			}
			if (CollectionUtils.isNotEmpty(ebookHtmlFilterFields)) {
				simplePropertyPreFilter.setHtmlFilterFields(ebookHtmlFilterFields);
			}
			if (CollectionUtils.isNotEmpty(ebookLineFilterFields)) {
				simplePropertyPreFilter.setLineFilterFields(ebookLineFilterFields);
			}
		}
		String json = JSON.toJSONString(model, simplePropertyPreFilter);
		return json;
	}
	
}
