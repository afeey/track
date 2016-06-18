package com.fbzj.track.enums;

/**
 * 地图类型枚举
 * @author afeey
 *
 */
public enum MapTypeEnum {

	/**
	 * Wgs84坐标系
	 */
	Wgs84(""),

	/**
	 * 百度坐标系
	 */
	Baidu("BAIDU"),

	/**
	 * google坐标系
	 */
	Google("GOOGLE");
	
	private final String value;

	MapTypeEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

}
