package com.fbzj.track.exception;

/**
 * 访问异常
 * 
 * @author afeey
 *
 */
public class AccessException extends ApiException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7489336279278502382L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            消息内容
	 */
	public AccessException(int errorCode, String message) {
		super(errorCode, message);
	}
}
