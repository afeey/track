package com.fbzj.track.service.impl;

import java.io.IOException;
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
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.service.DataApi;
import com.fbzj.track.util.http.HttpClientUtils;

/**
 * 数据API实现类
 * 
 * @author afeey
 */
public class DataApiImpl implements DataApi {

	private static final Logger logger = LoggerFactory.getLogger(DataApiImpl.class);

	public Token accessToken(String account, String password) throws AccessTokenException {
		Token token = null;
		
		String url = "http://api.gpsoo.net/1/auth/access_token";
		long time = System.currentTimeMillis() / 1000L;
		String signature = DigestUtils.md5Hex(DigestUtils.md5Hex(password)  + time) ;

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
			}else{
				throw new AccessTokenException(msg);
			}
		
		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}
		
		logger.debug(json);
		
		return token;
	}

	public String address(Token token, String account, String imei, MapTypeEnum mapType, Double lng, Double lat) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean blacklist(Token token, String account, String cardNo, String driveCardno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> monitor(Token token, String account, String target, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> tracking(Token token, String account, List<String> imeis, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> history(Token token, String account, String imei, MapTypeEnum mapType, Date beginTime,
			Date endTime, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Device> devinfo(Token token, String account, String target) {
		// TODO Auto-generated method stub
		return null;
	}

}
