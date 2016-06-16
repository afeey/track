package com.fbzj.track.model;

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
	private Integer expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

}
