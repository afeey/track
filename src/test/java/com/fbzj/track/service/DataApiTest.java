package com.fbzj.track.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fbzj.track.exception.AccessTokenException;
import com.fbzj.track.model.Token;
import com.fbzj.track.service.impl.DataApiImpl;

public class DataApiTest {

	private final String account = "18551698583"; // 账号
	private final String pwd = "123456"; // 密码

	@Test
	public void testAccessToken() {
		DataApi dataApi = new DataApiImpl();
		Token token = null;
		try {
			token = dataApi.accessToken(account, pwd);
			assertNotNull(token);
			System.out.println("访问令牌：" + token.getAccessToken() + "  有效时间：" + token.getExpiresIn());
		} catch (AccessTokenException e) {
			assertEquals("账号或密码错误", e.getMessage());
		}
	}

	@Test
	public void testMonitor() {
		fail("Not yet implemented");
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
