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

import com.fbzj.track.enums.DeviceInfoEnum;
import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.exception.AccessException;
import com.fbzj.track.exception.ExpiredException;
import com.fbzj.track.exception.IllegalException;
import com.fbzj.track.exception.NotExistException;
import com.fbzj.track.exception.ApiException;
import com.fbzj.track.exception.ErrorCode;
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
	private static final String domain = "http://api.gpsoo.net"; // 域名

	/**
	 * 获取一个访问令牌
	 * 
	 * @param account
	 *            经销商账号
	 * @param password
	 *            密码
	 * @return 访问令牌
	 * @throws ApiException
	 *             异常
	 */
	public static Token accessToken(String account, String password) throws ApiException {
		Token token = null;

		String url = domain + "/1/auth/access_token";
		long time = System.currentTimeMillis() / 1000L;
		String signature = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + time);

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("time", "" + time));
		pairs.add(new BasicNameValuePair("signature", signature));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {
				String accessToken = root.get("access_token").asText();
				int expiresIn = root.get("expires_in").asInt();
				token = new Token(accessToken, expiresIn);
			} else {
				exception(ret, msg);
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
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param target
	 *            平台账号
	 * @param mapType
	 *            地图类型
	 * @return 返回轨迹列表
	 * @throws ApiException
	 *             异常
	 */
	public static List<Track> monitor(String accessToken, String account, String target, MapTypeEnum mapType)
			throws ApiException {
		List<Track> tracks = new ArrayList<Track>();

		String url = domain + "/1/account/monitor";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("target", target));
		pairs.add(new BasicNameValuePair("map_type", "" + mapType.getValue()));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {

				// 解析封装Track
				JsonNode data = root.get("data");
				for (JsonNode node : data) {

					Track track = new Track();
					track.setImei(node.get("imei").asText());
					track.setDeviceInfo(DeviceInfoEnum.values()[node.get("device_info").asInt()]);
					track.setGpsTime(new Date(node.get("gps_time").asLong()));
					track.setSysTime(new Date(node.get("sys_time").asLong()));
					track.setHeartTime(new Date(node.get("heart_time").asLong()));
					track.setServerTime(new Date(node.get("server_time").asLong()));
					track.setLng(node.get("lng").asDouble());
					track.setLat(node.get("lat").asDouble());
					track.setCourse(node.get("course").asInt());
					track.setSpeed(node.get("speed").asInt());
					track.setAcc(node.get("acc").asText());
					track.setAccSeconds(node.get("acc_seconds").asInt());
					track.setSeconds(node.get("seconds").asInt());
					tracks.add(track);

				}
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return tracks;
	}

	/**
	 * 获取单个(批量)设备最新位置信息
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param imeis
	 *            imei列表（一次最多100个IMEI）
	 * @param mapType
	 *            地图类型
	 * @return 轨迹列表
	 * @throws ApiException
	 *             异常
	 */
	public static List<Track> tracking(String accessToken, String account, List<String> imeis, MapTypeEnum mapType)
			throws ApiException {
		List<Track> tracks = new ArrayList<Track>();

		String url = domain + "/1/devices/tracking";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("imeis", imeis.toString().replaceAll("\\s|\\[|\\]", "")));
		pairs.add(new BasicNameValuePair("map_type", "" + mapType.getValue()));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.post(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {

				// 解析封装Track
				JsonNode data = root.get("data");
				for (JsonNode node : data) {

					Track track = new Track();
					track.setImei(node.get("imei").asText());
					track.setDeviceInfo(DeviceInfoEnum.values()[node.get("device_info").asInt()]);
					track.setGpsTime(new Date(node.get("gps_time").asLong()));
					track.setSysTime(new Date(node.get("sys_time").asLong()));
					track.setHeartTime(new Date(node.get("heart_time").asLong()));
					track.setServerTime(new Date(node.get("server_time").asLong()));
					track.setLng(node.get("lng").asDouble());
					track.setLat(node.get("lat").asDouble());
					track.setCourse(node.get("course").asInt());
					track.setSpeed(node.get("speed").asInt());
					track.setAcc(node.get("acc").asText());
					track.setAccSeconds(node.get("acc_seconds").asInt());
					tracks.add(track);
				}
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return tracks;
	}

	/**
	 * 获取设备历史轨迹位置信息
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param imei
	 *            IMEI
	 * @param mapType
	 *            地图类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param limit
	 *            请求数据数量(一次最大1000条)
	 * @return 轨迹列表
	 * @throws ApiException
	 *             异常
	 */
	public static List<Track> history(String accessToken, String account, String imei, MapTypeEnum mapType, Date beginTime,
			Date endTime, int limit) throws ApiException {
		List<Track> tracks = new ArrayList<Track>();

		String url = domain + "/1/devices/history";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("imei", imei));
		pairs.add(new BasicNameValuePair("map_type", "" + mapType.getValue()));
		pairs.add(new BasicNameValuePair("begin_time", "" + beginTime.getTime() / 1000L));
		pairs.add(new BasicNameValuePair("end_time", "" + endTime.getTime() / 1000L));
		pairs.add(new BasicNameValuePair("limit", "" + limit));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {

				// 解析封装Track
				JsonNode data = root.get("data");
				for (JsonNode node : data) {
					Track track = new Track();
					track.setImei(imei);
					track.setGpsTime(new Date(node.get("gps_time").asLong()));
					track.setLng(node.get("lng").asDouble());
					track.setLat(node.get("lat").asDouble());
					track.setCourse(node.get("course").asInt());
					track.setSpeed(node.get("speed").asInt());
					tracks.add(track);
				}
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return tracks;
	}

	/**
	 * 根据经纬度得到中文地址
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param mapType
	 *            地图类型
	 * @param lng
	 *            经度
	 * @param lat
	 *            维度
	 * @return 地址
	 * @throws ApiException
	 *             异常
	 */
	public static String address(String accessToken, String account, MapTypeEnum mapType, Double lng, Double lat)
			throws ApiException {
		String address = "";
		String url = domain + "/1/tool/address";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("lng", "" + lng));
		pairs.add(new BasicNameValuePair("lat", "" + lat));
		pairs.add(new BasicNameValuePair("map_type", mapType.getValue()));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {
				address = root.get("address").asText();
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return address;
	}

	/**
	 * 查询账号下所有设备的相关信息
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param target
	 *            平台账号
	 * @return 设备信息
	 * @throws ApiException
	 *             异常
	 */
	public static List<Device> devinfo(String accessToken, String account, String target) throws ApiException {
		List<Device> devices = new ArrayList<Device>();

		String url = domain + "/1/account/devinfo";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("target", target));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (ret == 0) {

				// 解析封装Track
				JsonNode data = root.get("data");
				for (JsonNode node : data) {
					Device device = new Device();
					device.setImei(node.get("imei").asText());
					device.setName(node.get("name").asText());
					device.setNumber(node.get("number").asText());
					device.setPhone(node.get("phone").asText());
					device.setGroupId(node.get("group_id").asInt());
					device.setGroupName(node.get("group_name").asText());
					device.setDevType(node.get("dev_type").asText());
					device.setInTime(new Date(node.get("in_time").asLong()));
					device.setOutTime(new Date(node.get("out_time").asLong()));
					devices.add(device);
				}
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return devices;
	}

	/**
	 * 查询是否为黑名单 (ret返回值不正常，与文档描述不一致，目前msg无内容认为调用成功，返回data结果)
	 * 
	 * @param accessToken
	 *            访问令牌
	 * @param account
	 *            经销商账号
	 * @param cardNo
	 *            身份证号
	 * @param driveCardno
	 *            驾驶证号
	 * @return true黑名单 false非黑名单
	 * @throws ApiException
	 *             异常
	 */
	public static Boolean blacklist(String accessToken, String account, String cardNo, String driveCardNo)
			throws ApiException {

		boolean inBlack = false;

		String url = domain + "/1/tool/blacklist";
		long time = System.currentTimeMillis() / 1000L;

		// 参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("access_token", accessToken));
		pairs.add(new BasicNameValuePair("account", account));
		pairs.add(new BasicNameValuePair("cardno", cardNo));
		pairs.add(new BasicNameValuePair("drive_cardno", driveCardNo));
		pairs.add(new BasicNameValuePair("time", "" + time));

		String json = HttpClientUtils.get(url, pairs, "UTF-8");

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(json);
			int ret = root.get("ret").asInt();
			String msg = root.get("msg").asText();
			if (msg == "") {
				inBlack = root.get("data").asBoolean();
			} else {
				throw exception(ret, msg);
			}

		} catch (JsonProcessingException e) {
			logger.error(e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		logger.debug("return json : {} ", json);

		return inBlack;
	}

	/**
	 * 封装异常
	 * 
	 * @param errorCode
	 *            异常代码
	 * @param message
	 *            消息内容
	 */
	private static ApiException exception(int errorCode, String message) {
		ApiException apiException = null;
		switch (errorCode) {
		case ErrorCode.ACCESS_TOKEN_ERROR:
		case ErrorCode.ACCOUNT_PASSWORD_ERROR:
			apiException = new AccessException(errorCode, message);
			break;
		case ErrorCode.ACCESS_TOKEN_EXPIRED:
		case ErrorCode.DEVICE_EXPIRED:
			apiException = new ExpiredException(errorCode, message);
			break;
		case ErrorCode.INTERFACE_NOT_EXIST:
		case ErrorCode.ACCESS_TOKEN_NOT_EXIST:
		case ErrorCode.ACCOUNT_NOT_EXIST:
		case ErrorCode.IMEI_NOT_EXIST:
			apiException = new NotExistException(errorCode, message);
			break;
		case ErrorCode.REQUEST_FREQUENCY_OVER_LIMIT:
		case ErrorCode.MISS_NEED_PARAMETER:
		case ErrorCode.PARAMETER_VALUE_ILLEGAL:
		case ErrorCode.NO_RIGHT_VIEW_ACCOUNT:
		case ErrorCode.ACCOUNT_DEVICE_OVER_NUMBER:
		case ErrorCode.NO_RIGHT_VIEW_DEVICE:
		case ErrorCode.RQUEST_DEVICE_OVER_NUMBER:
		case ErrorCode.ILLEGAL_LNG_LAT:
			apiException = new IllegalException(errorCode, message);
			break;
		default:
			apiException = new ApiException(errorCode, message);
			break;
		}

		return apiException;
	}

}
