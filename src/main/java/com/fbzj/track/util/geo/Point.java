package com.fbzj.track.util.geo;

import java.util.Date;

/**
 * 经纬度
 * 
 * @author afeey
 *
 */
public class Point {

	/**
	 * 经度
	 */
	private double lng;

	/**
	 * 维度
	 */
	private double lat;

	/**
	 * 定位时间
	 */
	private Date time;

	public Point() {
		this.lng = 0d;
		this.lat = 0d;
		this.time = new Date();
	}
	
	public Point(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
		this.time = new Date();
	}

	public Point(double lng, double lat, Date time) {
		this.lng = lng;
		this.lat = lat;
		this.time = time;
	}

	/**
	 * 获取经度
	 * 
	 * @return 经度
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * 设置经度
	 * 
	 * @param lng
	 *            经度
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * 获取维度
	 * 
	 * @return 维度
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * 设置维度
	 * 
	 * @param lat
	 *            维度
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * 获取定位时间
	 * 
	 * @return 定位时间
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 设置定位时间
	 * 
	 * @param time
	 *            定位时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}
