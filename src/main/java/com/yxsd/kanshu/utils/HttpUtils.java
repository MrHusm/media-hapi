package com.yxsd.kanshu.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * http相关工具方法.
 * 
 * @author dangdang
 */
public abstract class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/** 代理名称. */
	private static final String PROXY_NAME = ConfigPropertieUtils.getString("proxy.name");

	/** 代理端口. */
	private static final String PROXY_PORT = ConfigPropertieUtils.getString("proxy.port");

	/** 代理用户 */
	private static final String PROXY_USERNAME = ConfigPropertieUtils.getString("proxy.userName");

	/** 代理端口. */
	private static final String PROXY_PASSWORD = ConfigPropertieUtils.getString("proxy.password");

	/** get请求. **/
	private static final String GET_METHOD = "get";

	/** post请求. **/
	private static final String POST_METHOD = "post";

	/** 是否使用代理. */
	private static final boolean IS_WITH_PROXY = isWithProxy();
	
	public static final String UTF8 = "UTF-8";
	
	public static final String GBK = "GBK";

	/** httpclient对应CONNECTION_TIMEOUT对应的值 */
	private static final Integer CONNECTION_TIMEOUT = ConfigPropertieUtils.getInteger(
			"httpclient.connection.timeout", 10000);

	/** httpclient对应SO_TIMEOUT对应的值 */
	private static final Integer SO_TIMEOUT = ConfigPropertieUtils.getInteger(
			"httpclient.so.timeout", 30000);

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	private HttpUtils() {
	}

	private static void setProxy(DefaultHttpClient httpclient) {
		httpclient.getCredentialsProvider().setCredentials(
				AuthScope.ANY,
				new NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME,
						"dangdang.com"));

		List<String> authpref = new ArrayList<String>();
		authpref.add(AuthPolicy.NTLM);
		httpclient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF,
				authpref);
		HttpHost proxy = new HttpHost(PROXY_NAME, 8080);

		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	private static void setProxy(HttpClient httpClient) {
		httpClient.getHostConfiguration().setProxy(PROXY_NAME,
				Integer.valueOf(PROXY_PORT));
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setProxyCredentials(
				org.apache.commons.httpclient.auth.AuthScope.ANY,
				new org.apache.commons.httpclient.NTCredentials(PROXY_USERNAME,
						PROXY_PASSWORD, PROXY_NAME, "dangdang.com"));
	}

	public static byte[] getBytes(final String urlStr) {

		return getBytes(urlStr, IS_WITH_PROXY);
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port,
					url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy,
			boolean simulateBrowser) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port,
					url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (simulateBrowser) {
				httpget.setHeader("Accept",
						"Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Accept-Charset",
						"GB2312,utf-8;q=0.7,*;q=0.7");
				httpget.setHeader("Accept-Encoding", "gzip, deflate");
				httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
				httpget.setHeader("Connection", "keep-alive");
				// httpget.setHeader("Cookie", cookie);
				String host = "";
				if (urlStr.toLowerCase().startsWith("http://")) {
					host = urlStr.substring(7);
				} else {
					host = urlStr;
				}
				int index = host.indexOf("/");
				if (index > 0) {
					host = host.substring(0, index);
				}
				httpget.setHeader("Host", host);
				// httpget.setHeader("refer",
				// "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
				httpget.setHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			}

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			return new String(bytes);
		}
		return "";
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url, String charset) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	/**
	 * 获取url内容.
	 * 
	 * @param url
	 * @param charset
	 * @param simulateBrowser
	 *            是否设置相关参数模拟浏览器.
	 * @return
	 */
	public static String getContent(final String url, String charset,
			boolean simulateBrowser) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, simulateBrowser);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	public static String getContentByPost(final String url, byte[] data) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(
				new ByteArrayInputStream(data));
		method.setRequestEntity(requestEntity);
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams()
				.setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}
	
	public static String getContentByPost(final String url, String data, Map<String, String> headerMap) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(
				new StringInputStream(data));
		method.setRequestEntity(requestEntity);
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			method.setRequestHeader(entry.getKey(), entry.getValue());
		}
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams()
				.setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy,
			int connectionTimeout, int soTimeout) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}
	public static String getContentTypeSetHeader(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy,
			int connectionTimeout, int soTimeout) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

//		httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=GBK");    
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, GBK);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");  
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
			
//			method.setQueryString(queryString);
//			method.setQueryString(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				System.out.println(method.getResponseBodyAsString());
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}
	//Inner class for UTF-8 support     
	public static class GBKPostMethod extends PostMethod{     
	    public GBKPostMethod(String url){     
	    super(url);     
	    }     
	    @Override     
	    public String getRequestCharSet() {     
	        //return super.getRequestCharSet();     
	        return "GBK";     
	    }  
	}  
	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy,
			int connectionTimeout, int soTimeout, boolean isReturnError) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
				if(isReturnError){
					InputStream stream = method.getResponseBodyAsStream();
					byte[] bytes = IOUtils.toByteArray(stream);
					return EncodingUtil.getString(bytes,
							method.getResponseCharSet());
				}
				
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}
	
	/**
	 * 通过http方式访问url调用接口.
	 * 
	 * @param url
	 *            接口地址
	 * @param methodStr
	 *            get或post方式
	 * @param paramsMap
	 *            参数列表
	 * @param encode
	 *            参数编码
	 * @param useProxy
	 *            是否使用代理
	 * @return
	 */
	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}
	public static String getContentTypeSetHeader(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy) {
		return getContentTypeSetHeader(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}
	
	public static String getContent(final String url, String methodStr,
			Map<String, String> paramsMap, String encode, boolean useProxy, boolean returnError) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT, returnError);
	}


	public static String getContentWithOutFormEncode(final String url,
			String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy, int connectionTimeout, int soTimeout) {
		
		final HttpClient httpClient = new HttpClient();

		// 设置连接一个url的连接等待超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(connectionTimeout);
		// 设置读取数据的超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(soTimeout);

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		method = new GetMethod(url);

		StringBuffer queryString = new StringBuffer();
		Iterator it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			queryString.append(m.getKey()).append("=").append(m.getValue())
					.append("&");
		}

		method.setQueryString(queryString.toString().substring(0,
				queryString.length() - 1));

		try {
			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes,
						method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * Form- without urlencoding.
	 * 
	 * @param url
	 * @param methodStr
	 * @param paramsMap
	 * @param encode
	 * @param useProxy
	 * @return
	 */
	public static String getContentWithOutFormEncode(final String url,
                                                     String methodStr,
                                                     Map<String, String> paramsMap,
                                                     String encode,
                                                     boolean useProxy) {
       return getContentWithOutFormEncode(url, methodStr, paramsMap, encode,
    		   useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

	/**
	 * 从参数map中构建NameValuePair数组.
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static NameValuePair[] getParamsFromMap(
			Map<String, String> paramsMap) {
		NameValuePair[] params = new NameValuePair[paramsMap.size()];
		int pos = 0;
		Iterator<String> iter = paramsMap.keySet().iterator();
		while (iter.hasNext()) {
			String paramName = iter.next();
			String paramValue = paramsMap.get(paramName);
			params[pos++] = new NameValuePair(paramName, paramValue);
		}

		return params;
	}

	/**
	 * 对Url发起请求.
	 * 
	 * @param url
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void requestUrl(final String url) throws IOException {
		final HttpClient httpClient = new HttpClient();
		final GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}

	/**
	 * 是否使用代理.
	 */
	private static boolean isWithProxy() {
		return StringUtils.isNotBlank(PROXY_NAME)
				&& StringUtils.isNotBlank(PROXY_PORT);
	}
	
	/**
	 * 
	 * @Description:发送类型为multipart/form-data的表单的请求
	 * @author wangdingtai
	 * @time:2015年10月19日 下午3:19:05
	 * @param url
	 * @param params
	 * @param fileMineType
	 * @param encode
	 * @param retryCount
	 * @return
	 */
	public static String getContentByMultiPost(String url, Map<String, Object> params, String fileMineType, Charset encode, Integer retryCount) {
		int i = 0;
		retryCount = (retryCount == null ? 0 : retryCount);
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseMsg = null;
		while (true) {
			try {
				httpClient = new DefaultHttpClient();
				HttpParams httpParams = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						CONNECTION_TIMEOUT); //连接超时  
				HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);//读取数据超时
				logger.info("getContentByMultiPost url:{}", url);
				httpPost = new HttpPost(url);
				//初始化请求参数
				logger.info("getContentByMultiPost params:{}", params);
				MultipartEntity entity = new MultipartEntity();
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, Object>> set = params.entrySet();
					Iterator<Entry<String, Object>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, Object> entry = it.next();
						AbstractContentBody ctxBody = null;
						if (entry.getValue() instanceof File) {
							ctxBody = new FileBody((File) entry.getValue(),
									fileMineType);
						} else {
							ctxBody = new StringBody(entry.getValue()
									.toString(), encode);
						}
						entity.addPart(entry.getKey(), ctxBody);
					}
				}
				httpPost.setEntity(entity);
				//发送请求
				HttpResponse response = httpClient.execute(httpPost);
				Integer statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("getContentByMultiPost fail statusCode:{}",
							statusCode);
					i++;
					if (i > retryCount) {
						break;
					}
				} else {
					HttpEntity respEntity = response.getEntity();
					if (respEntity != null) {
						responseMsg = EntityUtils.toString(respEntity);
						if (StringUtils.isEmpty(responseMsg)) {
							logger.error("getContentByMultiPost responseMsg empty!");
						}else{
							logger.info("getContentByMultiPost responseMsg:{}", responseMsg);
						}
					}
					break;
				}
			} catch (Exception e) {
				logger.error("getContentByMultiPost exception=[{}]",
						ExceptionUtils.getFullStackTrace(e));
				i++;
				if (i > retryCount) {
					break;
				}
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return responseMsg;
	}
	public static  String methodPost(String url,List<NameValuePair>  data ){  
        
        String response= "";//要返回的response信息  
        HttpClient httpClient = new HttpClient();  
        PostMethod postMethod = new PostMethod(url);  
        // 将表单的值放入postMethod中  
        NameValuePair[] datas=
        (NameValuePair [])data.toArray(new NameValuePair[data.size()]);
        postMethod.setRequestBody(datas);  
        // 执行postMethod  
        int statusCode = 0;  
        try {  
            statusCode = httpClient.executeMethod(postMethod);  
        } catch (HttpException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发  
        // 301或者302  
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY  
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {  
            // 从头中取出转向的地址  
            Header locationHeader = postMethod.getResponseHeader("location");  
            String location = null;  
            if (locationHeader != null) {  
                location = locationHeader.getValue();  
                System.out.println("The page was redirected to:" + location);  
                response= methodPost(location,data);//用跳转后的页面重新请求。  
            } else {  
                System.err.println("Location field value is null.");  
            }  
        } else {  
            System.out.println(postMethod.getStatusLine());  
  
            try {  
                response= postMethod.getResponseBodyAsString();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            postMethod.releaseConnection();  
        }  
        return response;  
    }  
	
	
	/**
	 *   
	 * @Title: postFile 
	 * @Description: 由于httpclient版本变化更新上传文件方法
	 * @param url
	 * @param params
	 * @param fileMineType
	 * @param encode
	 * @param retryCount
	 * @return 
	 * @author wangdingtai
	 */
    public static String postFile(String url, Map<String, Object> params, Charset encode) {  
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        CloseableHttpResponse response = null;
        try {  
            HttpPost httpPost = new HttpPost(url);  
            // 以浏览器兼容模式运行，防止文件名乱码。  
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE) ;
            logger.info("postFile url:{}", url);
            logger.info("postFile params:{}", params);
            // 参数
            if (params != null && !params.isEmpty()) {
				Set<Entry<String, Object>> set = params.entrySet();
				Iterator<Entry<String, Object>> it = set.iterator();
				while (it.hasNext()) {
					Entry<String, Object> entry = it.next();
					AbstractContentBody ctxBody = null;
					if (entry.getValue() instanceof File) {
						ctxBody = new FileBody((File) entry.getValue());
					} else {
						ctxBody = new StringBody(entry.getValue().toString(), ContentType.DEFAULT_TEXT);
					}
					multipartEntityBuilder.addPart(entry.getKey(), ctxBody);
				}
			}
            httpPost.setEntity(multipartEntityBuilder.setCharset(encode).build());  
            // 发起请求 并返回请求的响应  
            response = httpClient.execute(httpPost);  
            // 响应状态  
            Integer statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
            	logger.error("postFile statusCode:{}", statusCode);
            }else{
            	// 获取响应对象  
                HttpEntity resEntity = response.getEntity();  
                if (resEntity != null) {  
                    result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                    // 响应内容  
                    logger.info("postFile response result:" + result); 
                }  
                // 销毁  
                EntityUtils.consume(resEntity);
            }
        }catch (Exception e){
        	logger.error("response error:" + ExceptionUtils.getFullStackTrace(e));
        } finally {
            try {
            	response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
        
        return result;
    }  
	
	/**
	 * Description: 提交post请求
	 * @Version1.0 2016-2-29 下午5:21:44 by 李超（lichaosz@dangdang.com）创建
	 * @param url
	 * @param parameters
	 * @param charSet
	 * @return
	 */
	public static String postRequest(String url,Map<String,String> parameters,String charSet){
		if(StringUtils.isBlank(charSet)){
			charSet = "utf-8";
		}
		PostMethod post = getPostMethod(url,charSet);
		String result = null;
		try {
			HttpClient client = getHttpClient();
			if(null!=parameters && parameters.size()>0){
				int paraSize = parameters.size();
				NameValuePair[] requestBody = new NameValuePair[paraSize];
				int i = 0;
				for(String key:parameters.keySet()){
					NameValuePair uidValue = new NameValuePair(key,parameters.get(key));
					requestBody[i] = uidValue;
					i++;
				}
				post.setRequestBody(requestBody);    
			}
			int returnCode = client.executeMethod(post);
			
			if(returnCode==200){
				result = getResult(post, charSet);
			}
		} catch (Exception e) {
			logger.error("HttpUtil uploadEcard error.",e);
		} finally {
			post.releaseConnection(); 
		}
		return result;
	}
    private static PostMethod getPostMethod(String url,String encode) {
        PostMethod postMethod = new PostMethod(url);
        postMethod.setParameter("Connection", "Keep-Alive");
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        postMethod.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded; charset="+encode);
        return postMethod;
    }
	private static String getResult(HttpMethod method,String charSet) throws Exception{
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),charSet));
		String tmp = "";
		while ((tmp = reader.readLine()) != null) {
			result += tmp + "\r\n";
		}
		reader = null;
		
		return result;
	}
	public static HttpResponse getContentByPostJson(String json,String url) {
		HttpPost httpPost = null;
		org.apache.http.client.HttpClient httpClient = null;
		HttpResponse response = null;
		try {
			httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					CONNECTION_TIMEOUT); //连接超时
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);//读取数据超时
			httpPost = new HttpPost(url);
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			StringEntity se = new StringEntity(json,"UTF-8");
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			se.setContentEncoding("UTF-8");
			httpPost.setEntity(se);
			//发送请求
			response = httpClient.execute(httpPost);
		} catch (Exception e) {
			logger.error("getContentByMultiPost exception=[{}]",
					ExceptionUtils.getFullStackTrace(e));
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return response;
	}
    private static HttpClient getHttpClient() throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(20000);
        return httpClient;
    }	
	public static void main(final String[] args) throws Exception {}
}
