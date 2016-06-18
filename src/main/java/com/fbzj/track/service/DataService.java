package com.fbzj.track.service;

import java.util.Date;
import java.util.List;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Track;

public interface DataService {

	/**
	 * 获取账号下所有设备最新的位置
	 * 
	 * @return 轨迹集合
	 * @throws Exception
	 *             异常
	 */
	List<Track> getLastTrack(MapTypeEnum mapType) throws Exception;

	/**
	 * 获取设备最新位置信息
	 * 
	 * @param imeis
	 *            设备集合
	 * @param mapType
	 *            地图类型
	 * @return 轨迹集合
	 * @throws Exception
	 *             异常
	 */
	List<Track> getDeviceLastTrack(List<String> imeis, MapTypeEnum mapType) throws Exception;

	/**
	 * 获取设备历史轨迹
	 * 
	 * @param imei
	 *            IMEI
	 * @param mapType
	 *            地图类型
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param limit
	 *            请求数据数量(一次最大1000条)
	 * @return 轨迹集合
	 * @throws Exception
	 *             异常
	 */
	List<Track> getDeviceHistoryTrack(String imei, MapTypeEnum mapType, Date start, Date end, int limit)
			throws Exception;

	/**
	 * 根据经纬度获取地址
	 * 
	 * @param mapType
	 *            坐标地图类型
	 * @param lng
	 *            经度
	 * @param lat
	 *            维度
	 * @return 地址
	 * @throws Exception
	 *             异常
	 */
	String getAddress(MapTypeEnum mapType, Double lng, Double lat) throws Exception;

	/**
	 * 获取账号下的所有设备
	 * 
	 * @return 设备集合
	 * @throws Exception 异常
	 */
	List<Device> getDeviceList() throws Exception;

	/**
	 * 检查是否在黑名单中
	 * @param cardNo 身份证号
	 * @param driveCardNo 驾驶证号
	 * @return true黑名单 false非黑名单
	 * @throws Exception 异常
	 */
	boolean inBalckList(String cardNo, String driveCardNo) throws Exception;

}