package com.fbzj.track.service.impl;

import java.util.Date;
import java.util.List;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.service.DataApi;

/**
 * 数据API实现类
 * @author afeey
 *
 */
public class DataApiImpl implements DataApi {

	public Token accessToken(String account, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public String address(Token token, String account, String imei, MapTypeEnum mapType, Double lng, Double lat) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean blacklist(Token token, String account, String cardNo, String driveCardno) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> monitor(Token token, String account, String target, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> tracking(Token token, String account, List<String> imeis, MapTypeEnum mapType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Track> history(Token token, String account, String imei, MapTypeEnum mapType, Date beginTime,
			Date endTime, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Device> devinfo(Token token, String account, String target) {
		// TODO Auto-generated method stub
		return null;
	}

}
