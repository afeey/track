package com.fbzj.track.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.fbzj.track.BaseTest;
import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.service.impl.DataApi;

public class DataApiTest extends BaseTest {

	@Value("${gm.account}")
	private String account; // 账号
	@Value("${gm.password}")
	private String pwd; // 密码
	private String accessToken = "20007337056910146623746632415ea10b1f6113c6c63d665ba7cfc0c500010010018010";

	@Test
	public void testAccessToken() {
		Token token = null;
		try {
			token = DataApi.accessToken(account, pwd);
			assertNotNull(token);
			System.out.println("accessToken ：" + token.getAccessToken() + " , expiresIn ：" + token.getExpiresIn()
					+ " , time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(token.getTime()));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testMonitor() {
		try {
			List<Track> tracks = DataApi.monitor(accessToken, account, account, MapTypeEnum.Wgs84);
			assertTrue(tracks.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testTracking() {
		List<String> imeis = new ArrayList<String>();
		imeis.add("868120140229813");
		try {
			List<Track> tracks = DataApi.tracking(accessToken, account, imeis, MapTypeEnum.Wgs84);
			assertTrue(tracks.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testHistory() throws ParseException {

		String imei = "868120140229813";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = sdf.parse("2016-6-17 00:00:00");
		Date endTime = sdf.parse("2016-6-18 00:00:00");

		try {
			List<Track> tracks = DataApi.history(accessToken, account, imei, MapTypeEnum.Wgs84, beginTime, endTime, 1000);
			assertTrue(tracks.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAddress() {
		double lng = 118.737919;
		double lat = 32.165250;

		try {
			String address = DataApi.address(accessToken, account, MapTypeEnum.Baidu, lng, lat); //江苏省南京市浦口区天华北路.离艺馨童画教育(东北)约285米
			assertTrue(address.length() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDevinfo() {
		try {
			List<Device> devices = DataApi.devinfo(accessToken, account, account);
			assertTrue(devices.size() > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBlacklist() {
		String cardNo = "320123198702163055";			//身份证号
		String driveCardNo = "";	    				//驾驶证号
		try {
			boolean black = DataApi.blacklist(accessToken, account, cardNo, driveCardNo);
			assertFalse(black);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
