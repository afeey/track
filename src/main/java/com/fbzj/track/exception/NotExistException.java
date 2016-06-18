package com.fbzj.track.exception;

/**
 * 不存在异常
 * 
 * @author afeey
 *
 */
public class NotExistException extends ApiException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -831875931239985767L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            消息内容
	 */
	public NotExistException(int errorCode, String message) {
		super(errorCode, message);
	}
}
