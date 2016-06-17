package com.fbzj.track.service;

import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;

import java.util.List;
import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.exception.AccessTokenException;

import java.util.Date;

/**
 * 数据API
 */
public interface DataApi {

	/**
	 * 获取一个访问令牌
	 * account  经销商账号
	 * password 密码
	 * @throws AccessTokenException 访问令牌异常
	 */
	Token accessToken(String account, String password) throws AccessTokenException;

	/**
	 * 获取一个账户名下所有设备最新位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * target 平台账号
	 * mapType 地图类型
	 * 
	 * 
	 */
	List<Track> monitor(Token token, String account, String target, MapTypeEnum mapType);

	/**
	 * 获取单个/批量设备最新位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * mapType 地图类型
	 * 
	 */
	List<Track> tracking(Token token, String account, List<String> imeis, MapTypeEnum mapType);

	/**
	 * 获取设备历史轨迹位置信息
	 * token 访问令牌
	 * account 经销商账号
	 * mapType 地图类型
	 * begeinTime 开始时间
	 * endTime 结束时间
	 * limit 请求数据数量(一次最大1000条)
	 */
	List<Track> history(Token token, String account, String imei, MapTypeEnum mapType, Date beginTime, Date endTime, int limit);

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
	String address(Token token, String account, String imei, MapTypeEnum mapType, Double lng, Double lat);

	/**
	 * 查询账号下所有设备的相关信息
	 * token 访问令牌
	 * account 经销商账号
	 * target 平台账号
	 */
	List<Device> devinfo(Token token, String account, String target);

	/**
	 * 查询是否为黑名单
	 * token 访问令牌
	 * account 经销商账号
	 * cardNo 身份证号查询
	 * driveCardno 驾照号
	 */
	Boolean blacklist(Token token, String account, String cardNo, String driveCardno);

}
