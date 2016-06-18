package com.fbzj.track.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient工具类
 * @author afeey
 *
 */
public class HttpClientUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * GET请求
	 * @param url url
	 * @param pairs 名称值对列表
	 * @param encode 编码格式
	 * @return 返回字符串
	 */
	@SuppressWarnings("deprecation")
	public static String get(String url, List<NameValuePair> pairs, String encode) {
		String responseString = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder sb = new StringBuilder();
		sb.append(url.trim());
		
		// 拼接Get请求参数，参数编码
		if (pairs != null && pairs.size() > 0) {
			for (int i=0; i< pairs.size();i++) {
				NameValuePair entry = pairs.get(i);
				if (i == 0 && !url.contains("?")) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(entry.getName());
				sb.append("=");
				String value = entry.getValue();
				try {
					sb.append(URLEncoder.encode(value, encode));
				} catch (UnsupportedEncodingException e) {
					logger.warn("encode http get params error, value is {}", value, e);
					sb.append(URLEncoder.encode(value));
				}
			}
			url = sb.toString();
		}

		logger.debug("[HttpUtils Get] begin invoke : " + url.toString());
		HttpGet get = new HttpGet(url.toString());

		try {
			
			// 执行请求
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(get);
			long s2 = System.currentTimeMillis();
			
			try {
				// 判断状态
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					get.abort();
					logger.error("[HttpUtils Get] error, url : {}  , params : {},  status :{}", url, pairs, statusCode);
					return responseString;
				}

				// 获取返回
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, encode);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				logger.error("[HttpUtils Get] get response error, url:{}", url.toString(), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.debug("[HttpUtils Get] debug, url : {} , response string :{} , time = {}ms", url, responseString, s2 - s1);
		} catch (Exception e) {
			logger.error("[HttpUtils Get] error, url : {} , params : {}, response string : {} ,error : {}", url, pairs, responseString, e.getMessage(), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}

	/**
	 * POST请求
	 * @param url url
	 * @param pairs 名称值对列表
	 * @param encode 编码格式
	 * @return 返回字符串
	 */
	public static String post(String url, List<NameValuePair> pairs, String encode) {
		String responseString = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(url.trim());
		try {
			// 设置参数，进行编码
			if (pairs != null && pairs.size() > 0) {
				post.setEntity(new UrlEncodedFormEntity(pairs, encode));
			}

			// 执行请求
			logger.debug("[HttpUtils Post] begin invoke url : {} , params:{}", url, pairs);
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(post);
			long s2 = System.currentTimeMillis() - s1;
			
			try {
				
				// 判断返回状态
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					post.abort();
					logger.error("[HttpUtils Post] error, url : {}  , params : {},  status :{}", url, pairs, statusCode);
					return responseString;
				}

				// 获取返回值
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, encode);
						logger.debug("[HttpUtils Post] debug response, url : {}  , params : {}, response string : {} , time : {}", url, pairs, responseString , s2);
						return responseString;
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (Exception e) {
			logger.error("[HttpUtils Post] error, url : {}  , params : {}, response string : {} , error : {}", url, pairs, responseString, e.getMessage(), e);
		} finally {
			post.releaseConnection();
		}
		return responseString;
	}
}
