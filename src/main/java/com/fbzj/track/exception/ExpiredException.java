package com.fbzj.track.exception;

/**
 * 过期异常
 * 
 * @author afeey
 *
 */
public class ExpiredException extends ApiException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8382175360299473399L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param message
	 *            消息内容
	 */
	public ExpiredException(int errorCode, String message) {
		super(errorCode, message);
	}
}
