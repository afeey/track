package com.fbzj.track.exception;

/**
 * 没有权限异常
 * @author afeey
 *
 */
public class NoPermissionException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5179199289025605468L;

	/**
	 * 构造函数
	 * @param message 消息内容
	 */
	public NoPermissionException(String message) {
        super(message);
    }
}
