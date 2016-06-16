package com.fbzj.track.model;

import com.fbzj.track.enums.DeviceInfoEnum;
import java.util.Date;

/**
 * 轨迹
 */
public class Track {

	/**
	 * imei
	 */
	private String imei;

	/**
	 * 设备状态信息
	 */
	private DeviceInfoEnum deviceInfo;

	/**
	 * GPS定位时间(如果设备过期，值为1970-01-01 00:00:00)
	 */
	private Date gpsTime;

	/**
	 * 系统时间(如果设备过期，值为1970-01-01 00:00:00)
	 */
	private Date sysTime;

	/**
	 * 心跳时间(如果设备过期，值为1970-01-01 00:00:00)
	 */
	private Date heartTime;

	/**
	 * 服务器时间(如果设备过期，值为1970-01-01 00:00:00)
	 */
	private Date serverTime;

	/**
	 * 经度（如果设备过期，值为0）
	 */
	private Double lng;

	/**
	 * 维度（如果设备过期，值为0）
	 */
	private Double lat;

	/**
	 * 航向
	 * 1.正北方向为0度，顺时针方向增大。最大值360度
	 * 2.如果设备过期，值为0
	 */
	private float course;

	/**
	 * 速度
	 * 1.单位:km/h
	 * 2.如果设备过期，值为-1，如果设备还未上线值为-9
	 */
	private float speed;

	/**
	 * ACC等信息 (如果设备过期，值为空字符串)
	 */
	private String status;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public DeviceInfoEnum getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfoEnum deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Date getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public Date getSysTime() {
		return sysTime;
	}

	public void setSysTime(Date sysTime) {
		this.sysTime = sysTime;
	}

	public Date getHeartTime() {
		return heartTime;
	}

	public void setHeartTime(Date heartTime) {
		this.heartTime = heartTime;
	}

	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public float getCourse() {
		return course;
	}

	public void setCourse(float course) {
		this.course = course;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
