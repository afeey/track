package com.fbzj.track.util.http;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class HttpClientUtilsTest {

	@Test
	public void testGet() {

		String url = "http://api.gpsoo.net/1/auth/access_token";
		String encode = "UTF-8";

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("account", "testacc"));
		pairs.add(new BasicNameValuePair("time", "1366786321"));
		pairs.add(new BasicNameValuePair("signature", "e9b39daacc811af7109ef1e11a6583bd"));

		String rpString = HttpClientUtils.get(url, pairs, encode);

		System.out.println("return string : " + rpString);

		assertTrue(rpString.length() > 0);
	}

}
