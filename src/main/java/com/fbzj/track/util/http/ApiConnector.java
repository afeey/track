package com.fbzj.track.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API连接
 * @author afeey
 *
 */
public final class ApiConnector {
	private static final Logger logger = LoggerFactory.getLogger(ApiConnector.class);

	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpclient = null;

	private static Header DEFAULT_HEADER = new BasicHeader(HttpHeaders.USER_AGENT, "SCS");

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 800;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 400;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 30000;

	static {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).build();

		connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

		connManager.setDefaultSocketConfig(socketConfig);
		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

		httpclient = HttpClients.custom().setConnectionManager(connManager)
				.setRetryHandler(DefaultHttpRequestRetryHandler.INSTANCE)
				.setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
				.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE).build();
	}

	/**
	 * post请求
	 * @param url url
	 * @param pairs 名称值对列表
	 * @param encoding 编码
	 * @return 返回字符串
	 */
	public static String post(String url, List<NameValuePair> pairs, String encoding) {
		HttpPost post = new HttpPost(url.trim());
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONNECT_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);
			post.addHeader(DEFAULT_HEADER);

			if (pairs != null && pairs.size() > 0) {
				post.setEntity(new UrlEncodedFormEntity(pairs, encoding));
			}

			logger.debug("[HttpUtils Post] begin invoke url:{} , params:{}", url, pairs);
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(post);
			long s2 = System.currentTimeMillis() - s1;
			
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					post.abort();
					logger.error("[HttpUtils Post] error, url : {}  , params : {},  status :{}", url, pairs, statusCode);
					return "";
				}

				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						String str = EntityUtils.toString(entity, encoding);
						logger.debug("[HttpUtils Post]Debug response, url : {}  , params : {}, response string : {} ,time : {}",
								url, pairs, str, s2);
						return str;
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
			logger.error("[HttpUtils Post] error, url : {}  , params : {}, response string : {} ,error : {}", url, pairs, "", e.getMessage(), e);
		} finally {
			post.releaseConnection();
		}
		return "";
	}

	/**
	 * 
	 * @param url
	 * @param pairs
	 * @return
	 */
	public static String post(String url, List<NameValuePair> pairs) {
		HttpPost post = new HttpPost(url.trim());
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONNECT_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);
			post.addHeader(DEFAULT_HEADER);

			if (pairs != null && pairs.size() > 0) {
				post.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));
			}

			logger.info("[HttpUtils Post] begin invoke url:{} , params:{}", url, pairs);
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(post);
			long s2 = System.currentTimeMillis();
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					post.abort();
					logger.error("[HttpUtils Post] error, url : {}  , params : {},  status :{}", url, pairs,
							statusCode);
					return "";
				}

				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						String str = EntityUtils.toString(entity, Consts.UTF_8);
						logger.info(
								"[HttpUtils Post]Debug response, url : {}  , params : {}, response string : {} ,time : {}",
								url, pairs, str, s2 - s1);
						return str;
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
			logger.error("[HttpUtils Post] error, url : {}  , params : {}, response string : {} ,error : {}", url,
					pairs, "", e.getMessage(), e);
		} finally {
			post.releaseConnection();
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	public static String get(String url, List<NameValuePair> pairs, String encode) {
		String responseString = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT).build();

		StringBuilder sb = new StringBuilder();
		sb.append(url.trim());
		int i = 0;
		if (pairs != null && pairs.size() > 0) {
			for (NameValuePair entry : pairs) {
				if (i == 0 && !url.contains("?")) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(entry.getName());
				sb.append("=");
				String value = entry.getValue();
				try {
					sb.append(URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.warn("encode http get params error, value is {}", value, e);
					sb.append(URLEncoder.encode(value));
				}
				i++;
			}
		}

		logger.info("[HttpUtils Get] begin invoke:" + url.toString());
		HttpGet get = new HttpGet(url.toString());
		get.setConfig(requestConfig);
		get.addHeader(DEFAULT_HEADER);

		try {
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(get);
			long s2 = System.currentTimeMillis();
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					get.abort();
					logger.error("[HttpUtils Get] error, url : {}  , params : {},  status :{}", url, pairs, statusCode);
					return "";
				}

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
				logger.error("[HttpUtils Get]get response error, url:{}", url.toString(), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.info("[HttpUtils Get]Debug url:{} , response string :{},time={}", url.toString(), responseString,
					s2 - s1);
		} catch (Exception e) {
			logger.error("[HttpUtils Get] error, url : {}  , params : {}, response string : {} ,error : {}", url, pairs,
					"", e.getMessage(), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}

	@SuppressWarnings("deprecation")
	public static String get(String url, List<NameValuePair> pairs) {
		String responseString = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_TIMEOUT).build();

		StringBuilder sb = new StringBuilder();
		sb.append(url.trim());
		int i = 0;
		if (pairs != null && pairs.size() > 0) {
			for (NameValuePair entry : pairs) {
				if (i == 0 && !url.contains("?")) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(entry.getName());
				sb.append("=");
				String value = entry.getValue();
				try {
					sb.append(URLEncoder.encode(value, Consts.UTF_8.name()));
				} catch (UnsupportedEncodingException e) {
					logger.warn("encode http get params error, value is {}", value, e);
					sb.append(URLEncoder.encode(value));
				}
				i++;
			}
		}

		logger.debug("[HttpUtils Get] begin invoke:{}", url.toString());
		HttpGet get = new HttpGet(url.toString());
		get.setConfig(requestConfig);
		get.addHeader(DEFAULT_HEADER);

		try {
			long s1 = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(get);
			long s2 = System.currentTimeMillis();
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					get.abort();
					logger.error("[HttpUtils Get] error, url : {}  , params : {},  status :{}", url, pairs, statusCode);
					return "";
				}
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, Consts.UTF_8);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				logger.error("[HttpUtils Get]get response error, url:{}", url.toString(), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.debug("[HttpUtils Get]Debug url:{} , response string :{},time={}", url.toString(), responseString,
					s2 - s1);
		} catch (Exception e) {
			logger.error("[HttpUtils PostJson] error, url : {}  , params : {}, response string : {} ,error : {}", url,
					pairs, "", e.getMessage(), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}
	
}