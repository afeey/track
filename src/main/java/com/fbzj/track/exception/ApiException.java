package com.fbzj.track.exception;

public class ApiException extends Exception implements ErrorCode {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1102180882102959348L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 错误代码
	 */
	private int code;
	
	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            消息内容
	 */
	public ApiException(int errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
