package com.fbzj.track.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.exception.ApiException;
import com.fbzj.track.exception.ErrorCode;
import com.fbzj.track.exception.ExpiredException;
import com.fbzj.track.model.Account;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.service.DataService;

/**
 * 数据服务实现类
 * @author afeey
 *
 */
@Service
public class DataServiceImpl implements DataService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

	private Account account; // 账号
	private Token token; // 令牌

	public DataServiceImpl(){
		account = null;
		token = null;
	}
	
	public DataServiceImpl(String ac, String pwd) {
		this.account = new Account(ac, pwd);
		this.token = null;
	}

	/**
	 * 连接
	 * 
	 * @throws ApiException
	 *             异常
	 */
	private void connect(Account account) throws Exception {
		if(account == null){
			throw new Exception("未设置account");
		}
		token = DataApi.accessToken(account.getName(), account.getPassword());
		logger.info("request accessToken");
	}

	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#getLastTrack(com.fbzj.track.enums.MapTypeEnum)
	 */
	@Override
	public List<Track> getLastTrack(MapTypeEnum mapType) throws Exception {
		if (token == null) {
			connect(account);
		}

		List<Track> tracks = new ArrayList<Track>();
		try {
			tracks = DataApi.monitor(token.getAccessToken(), account.getName(), account.getName(), mapType);
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				tracks = DataApi.monitor(token.getAccessToken(), account.getName(), account.getName(), mapType);
			} else {
				throw e;
			}
		}
		return tracks;
	}

	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#getDeviceLastTrack(java.util.List, com.fbzj.track.enums.MapTypeEnum)
	 */
	@Override
	public List<Track> getDeviceLastTrack(List<String> imeis, MapTypeEnum mapType) throws Exception {
		if (token == null) {
			connect(account);
		}

		List<Track> tracks = new ArrayList<Track>();
		try {
			tracks = DataApi.tracking(token.getAccessToken(), account.getName(), imeis, mapType);
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				tracks = DataApi.tracking(token.getAccessToken(), account.getName(), imeis, mapType);
			} else {
				throw e;
			}
		}
		return tracks;
	}

	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#getDeviceHistoryTrack(java.lang.String, com.fbzj.track.enums.MapTypeEnum, java.util.Date, java.util.Date, int)
	 */
	@Override
	public List<Track> getDeviceHistoryTrack(String imei, MapTypeEnum mapType, Date start, Date end, int limit)
			throws Exception {
		if (token == null) {
			connect(account);
		}

		List<Track> tracks = new ArrayList<Track>();
		try {
			tracks = DataApi.history(token.getAccessToken(), account.getName(), imei, mapType, start, end, limit);
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				tracks = DataApi.history(token.getAccessToken(), account.getName(), imei, mapType, start, end, limit);
			} else {
				throw e;
			}
		}
		return tracks;
	}

	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#getAddress(com.fbzj.track.enums.MapTypeEnum, java.lang.Double, java.lang.Double)
	 */
	@Override
	public String getAddress(MapTypeEnum mapType, Double lng, Double lat) throws Exception {
		if (token == null) {
			connect(account);
		}

		String address = "";
		try {
			address = DataApi.address(token.getAccessToken(), account.getName(), mapType, lng, lat);
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				address = DataApi.address(token.getAccessToken(), account.getName(), mapType, lng, lat);
			} else {
				throw e;
			}
		}
		return address;
	}

	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#getDeviceList()
	 */
	@Override
	public List<Device> getDeviceList() throws Exception {
		if (token == null) {
			connect(account);
		}

		List<Device> devices = new ArrayList<Device>();
		try {
			devices = DataApi.devinfo(token.getAccessToken(), account.getName(), account.getName());
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				devices = DataApi.devinfo(token.getAccessToken(), account.getName(), account.getName());
			} else {
				throw e;
			}
		}
		return devices;
	}
	
	/* (non-Javadoc)
	 * @see com.fbzj.track.service.impl.DataService#inBalckList(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean inBalckList(String cardNo,String driveCardNo) throws Exception{
		if (token == null) {
			connect(account);
		}

		boolean inBlack = false;
		try {
			inBlack = DataApi.blacklist(token.getAccessToken(), account.getName(), cardNo, driveCardNo);
		} catch (ExpiredException e) {
			if (e.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
				connect(this.account);
				inBlack = DataApi.blacklist(token.getAccessToken(), account.getName(), cardNo, driveCardNo);
			} else {
				throw e;
			}
		}
		return inBlack;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
