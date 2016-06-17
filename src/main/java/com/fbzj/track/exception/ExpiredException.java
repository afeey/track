package com.fbzj.track.exception;

/**
 * 过期异常
 * @author afeey
 *
 */
public class ExpiredException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8382175360299473399L;

	/**
	 * 构造函数
	 * @param message 消息内容
	 */
	public ExpiredException(String message) {
        super(message);
    }
}
