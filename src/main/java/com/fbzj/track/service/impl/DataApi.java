package com.fbzj.track.service.impl;

import java.io.IOException;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.exception.AccessTokenException;
import com.fbzj.track.exception.ExpiredException;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.util.http.HttpClientUtils;

/**
 * 数据API实现类
 * 
 * @author afeey
 */
public class DataApi {

	private static final Logger logger = LoggerFactory.getLogger(DataApi.class);
	private static final String domain = "http://api.gpsoo.net"; //域名
	
	
	/**
	 * 获取一个访问令牌
	 * account  经销商账号
	 * password 密码
	 * @throws AccessTokenException 访问令牌异常
	 * @throws ExpiredException 
	 */
	public static Token accessToken(String account, String password) throws AccessTokenException, ExpiredException {
		Token token = null;
		
		String url = domain + "/1/auth/access_token";
		long time = System.currentTimeMillis() / 1000L;
		String signature = DigestUtils.md5Hex(DigestUtils.md5Hex(password)  + time) ;
		
		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("time", "" + time));
		pairs.add(new BasicNameValuePair("signature", signature));
		
		String json = HttpClientUtils.get(url, pairs, "UTF-8");
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			String ret = root.get("ret").asText();
			String msg = root.get("msg").asText();
			if(ret=="0"){
				String accessToken = root.get("access_token").asText();
				int expiresIn = Integer.valueOf(root.get("expires_in").asText());
				token = new Token(accessToken,expiresIn);
			}else if(ret =="20001"){
				throw new AccessTokenException(msg);
			}else if(ret == "10006 "){
				throw new ExpiredException(msg);
			}
		
		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}
		
		logger.debug("return json : {} ", json);
		
		return token;
	}

	/**
	 * 获取一个账户名下所有设备最新位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * target 平台账号
	 * mapType 地图类型
	 * 
	 * 
	 */
	public static List<Track> monitor(Token token, String account, String target, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取单个/批量设备最新位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * mapType 地图类型
	 * 
	 */
	public static List<Track> tracking(Token token, String account, List<String> imeis, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取设备历史轨迹位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * mapType 地图类型
	 * begeinTime 开始时间
	 * endTime 结束时间
	 * limit 请求数据数量(一次最大1000条)
	 */
	public static List<Track> history(Token token, String account, String imei, MapTypeEnum mapType, Date beginTime,
			Date endTime, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据经纬度得到中文地址
	 * token 访问令牌
	 * account 经销商账号
	 * imei IMEI
	 * mapType 地图类型
	 * lng 经度
	 * lat 维度
	 * 
	 */
	public static String address(Token token, String account, String imei, MapTypeEnum mapType, Double lng, Double lat) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询账号下所有设备的相关信息
	 * token 访问令牌
	 * account 经销商账号
	 * target 平台账号
	 */
	public static List<Device> devinfo(Token token, String account, String target) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询是否为黑名单
	 * token 访问令牌
	 * account 经销商账号
	 * cardNo 身份证号查询
	 * driveCardno 驾照号
	 */
	public static Boolean blacklist(Token token, String account, String cardNo, String driveCardno) {
		// TODO Auto-generated method stub
		return null;
	}

}
