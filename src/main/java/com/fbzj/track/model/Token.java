package com.fbzj.track.model;

import java.util.Date;

/**
 * 令牌
 */
public class Token {

	/**
	 * 访问令牌
	 */
	private String accessToken;

	/**
	 * 有效期，单位秒
	 */
	private int expiresIn;
	
	/**
	 * 创建时间
	 */
	private Date time;

	public Token(){
		this.accessToken = "";
		this.expiresIn = 0;
		this.time = new Date();
	}
	
	public Token(String accessToken,int expiresIn ){
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.time = new Date();
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
