package com.fbzj.track.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设备
 */
public class Device {

	/**
	 * imei
	 */
	private String imei;

	/**
	 * 设备名称
	 */
	private String name;

	/**
	 * 车牌号
	 */
	private String number;

	/**
	 * 设备电话号
	 */
	private String phone;

	/**
	 * 分组ID
	 */
	private int groupId;

	/**
	 * 分组名称
	 */
	private String groupName;

	/**
	 * 设备类型
	 */
	private String devType;

	/**
	 * 开通时间
	 */
	private Date inTime;

	/**
	 * 到期时间
	 */
	private Date outTime;

	/**
	 * 速度， 为-9表示未启用
	 */
	private String speed;

	/**
	 * 是否支持电子围栏
	 */
	private Boolean efenceSupport;

	/**
	 * 构造函数
	 */
	public Device() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.imei = "";
		this.name = "";
		this.number = "";
		this.phone = "";
		this.groupId = 0;
		this.groupName = "";
		this.devType = "";
		this.inTime = date;
		this.outTime = date;
		this.speed = "";
		this.efenceSupport = false;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public Boolean getEfenceSupport() {
		return efenceSupport;
	}

	public void setEfenceSupport(Boolean efenceSupport) {
		this.efenceSupport = efenceSupport;
	}

}
