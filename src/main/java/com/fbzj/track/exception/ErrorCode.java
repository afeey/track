package com.fbzj.track.exception;

/**
 * DataAPI异常代码
 * 
 * @author afeey
 *
 */
public interface ErrorCode {

	/**
	 * 请求成功
	 */
	int REQUEST_SUCCESS = 0;

	/**
	 * 系统错误
	 */
	int SYS_ERROR = 10001;

	/**
	 * 请求的接口不存在
	 */
	int INTERFACE_NOT_EXIST = 10002;

	/**
	 * 请求频率超过上限
	 */
	int REQUEST_FREQUENCY_OVER_LIMIT = 10003;

	/**
	 * access_token不存在
	 */
	int ACCESS_TOKEN_NOT_EXIST = 10004;

	/**
	 * access_token错误
	 */
	int ACCESS_TOKEN_ERROR = 10005;

	/**
	 * access_token已过期
	 */
	int ACCESS_TOKEN_EXPIRED = 10006;

	/**
	 * 账号或密码错误
	 */
	int ACCOUNT_PASSWORD_ERROR = 20001;

	/**
	 * 缺失必选参数
	 */
	int MISS_NEED_PARAMETER = 20002;

	/**
	 * 参数值非法
	 */
	int PARAMETER_VALUE_ILLEGAL = 20003;

	/**
	 * 监控账号不存在
	 */
	int ACCOUNT_NOT_EXIST = 20004;

	/**
	 * 无权查看该账号信息
	 */
	int NO_RIGHT_VIEW_ACCOUNT = 20005;

	/**
	 * 账号名下设备数量超过上限
	 */
	int ACCOUNT_DEVICE_OVER_NUMBER = 20006;

	/**
	 * IMEI不存在
	 */
	int IMEI_NOT_EXIST = 20007;

	/**
	 * 无权查看该设备信息
	 */
	int NO_RIGHT_VIEW_DEVICE = 20008;

	/**
	 * 请求的设备数量超过上限
	 */
	int RQUEST_DEVICE_OVER_NUMBER = 20009;

	/**
	 * 该设备已过期
	 */
	int DEVICE_EXPIRED = 20010;

	/**
	 * 地图类型错误
	 */
	int MAP_TYPE_ERROR = 20011;

	/**
	 * 非法经纬度
	 */
	int ILLEGAL_LNG_LAT = 20012;
}
