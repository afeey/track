package com.fbzj.track.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.exception.AccessTokenException;
import com.fbzj.track.exception.ExpiredException;
import com.fbzj.track.exception.NoPermissionException;
import com.fbzj.track.model.Token;
import com.fbzj.track.model.Track;
import com.fbzj.track.service.impl.DataApi;

public class DataApiTest {

	private final String account = "18551698583"; // 账号
	private final String pwd = "123456"; // 密码
	private Token token = new Token("20007337056910146620701832b6d614271bf2a77a623f520fbf8b6be600010010018010", 7200);

	@Test
	public void testAccessToken() {
		Token token = null;
		try {
			token = DataApi.accessToken(account, pwd);
			assertNotNull(token);
			System.out.println("访问令牌：" + token.getAccessToken() + "  有效时间：" + token.getExpiresIn());
		} catch (AccessTokenException e) {
			assertEquals("账号或密码错误", e.getMessage());
		} catch (ExpiredException e) {
			assertEquals("令牌已过期", e.getMessage());
		}
	}

	@Test
	public void testMonitor() {
		try {
			List<Track> tracks = DataApi.monitor(token, account, account, MapTypeEnum.Wgs84);
			assertTrue(tracks.size() > 0);
		} catch (NoPermissionException e) {
			assertEquals("没有权限", e.getMessage());
		}
	}

	@Test
	public void testTracking() {
		fail("Not yet implemented");
	}

	@Test
	public void testHistory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddress() {
		fail("Not yet implemented");
	}

	@Test
	public void testDevinfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testBlacklist() {
		fail("Not yet implemented");
	}

}
