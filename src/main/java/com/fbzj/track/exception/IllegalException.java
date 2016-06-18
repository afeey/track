package com.fbzj.track.exception;

/**
 * 非法异常
 * 
 * @author afeey
 *
 */
public class IllegalException extends ApiException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5179199289025605468L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            消息内容
	 */
	public IllegalException(int errorCode, String message) {
		super(errorCode, message);
	}
}
