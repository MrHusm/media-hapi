package com.yxsd.kanshu.test;

import java.io.File;


import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TestUpload {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		HttpClient httpclient = new DefaultHttpClient();  
		
		HttpPost httppost = new HttpPost("http://10.255.223.117/media-hapi/media/api.go?action=uploadImageToCdn");  
		  
		FileBody bin = new FileBody(new File("C:\\Users\\Administrator\\Desktop\\0.jpg"));  
		    
		FileBody bin2 = new FileBody(new File("C:\\Users\\Administrator\\Desktop\\0.jpg"));  
		  
		StringBody token = new StringBody("4590a3b12ec0c6be1197a5f432c7ca6f");  
		StringBody code = new StringBody("bar");  

		MultipartEntity reqEntity = new MultipartEntity();  
		  
		reqEntity.addPart("file1", bin);//file1为请求后台的File upload;属性      
		 reqEntity.addPart("file2", bin2);//file2为请求后台的File upload;属性  
		 reqEntity.addPart("token", token);//filename1为请求后台的普通参数;属性     
		 reqEntity.addPart("code", code);//filename1为请求后台的普通参数;属性     
		 
		httppost.setEntity(reqEntity);  
		  
		
		try {
			 HttpResponse response = httpclient.execute(httppost);  
	         
	         
	         int statusCode = response.getStatusLine().getStatusCode();  
			if (statusCode != HttpStatus.SC_OK) {
			} else {
				 HttpEntity resEntity = response.getEntity();  
                 
                 
	                System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据  
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
		}
	}
}
