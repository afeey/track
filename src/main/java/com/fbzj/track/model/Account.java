package com.fbzj.track.model;

/**
 * 账号
 */
public class Account {

	/**
	 * id
	 */
	private String id;

	/**
	 * 登录名
	 */
	private String name;

	private String password;
	
	/**
	 * 构造函数
	 * @param name 账号名
	 * @param password 密码
	 */
	public Account(String name, String password) {
		this.name = name;
		this.password = password;
	}

	/**
	 * 显示名
	 */
	private String showName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

}
