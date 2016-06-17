package com.fbzj.track.exception;

/**
 * 访问令牌异常
 * @author afeey
 *
 */
public class AccessTokenException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7489336279278502382L;

	/**
	 * 构造函数
	 * @param message 消息内容
	 */
	public AccessTokenException(String message) {
        super(message);
    }
}
