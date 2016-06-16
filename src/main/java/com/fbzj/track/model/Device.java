package com.fbzj.track.model;

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
	 * 为-9表示未启用
	 */
	private String sudu;

	/**
	 * 是否支持电子围栏
	 */
	private Boolean efenceSupport;

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

	public String getSudu() {
		return sudu;
	}

	public void setSudu(String sudu) {
		this.sudu = sudu;
	}

	public Boolean getEfenceSupport() {
		return efenceSupport;
	}

	public void setEfenceSupport(Boolean efenceSupport) {
		this.efenceSupport = efenceSupport;
	}

}
