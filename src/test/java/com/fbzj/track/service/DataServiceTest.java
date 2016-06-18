package com.fbzj.track.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fbzj.track.BaseTest;
import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.model.Device;
import com.fbzj.track.model.Track;

public class DataServiceTest extends BaseTest {

	@Autowired
	@Qualifier("dataService")
	private DataService dataService;

	@Test
	public void testGetLastTrack() throws Exception {
		List<Track> tracks = dataService.getLastTrack(MapTypeEnum.Wgs84);
		assertTrue(tracks.size() > 0);
	}

	@Test
	public void testGetDeviceLastTrack() throws Exception {
		List<String> imeis = new ArrayList<>();
		imeis.add("868120140229813");

		List<Track> tracks = dataService.getDeviceLastTrack(imeis, MapTypeEnum.Wgs84);
		assertTrue(tracks.size() > 0);
	}

	@Test
	public void testGetDeviceHistoryTrack() throws Exception {

		String imei = "868120140229813";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = sdf.parse("2016-6-17 00:00:00");
		Date end = sdf.parse("2016-6-18 00:00:00");
		int limit = 1000;

		List<Track> tracks = dataService.getDeviceHistoryTrack(imei, MapTypeEnum.Wgs84, start, end, limit);
		assertTrue(tracks.size() > 0);
	}

	@Test
	public void testGetAddress() throws Exception {
		double lng = 118.737919;
		double lat = 32.165250;

		String address = dataService.getAddress(MapTypeEnum.Wgs84, lng, lat);
		assertTrue(address.length() > 0);
	}

	@Test
	public void testGetDeviceList() throws Exception {
		List<Device> devices = dataService.getDeviceList();
		assertTrue(devices.size() > 0);
	}

	@Test
	public void testInBalckList() throws Exception {
		String cardNo = "320123198702163055";
		String driveCardNo = "";
		boolean inBlack = dataService.inBalckList(cardNo, driveCardNo);
		assertFalse(inBlack);
	}

}
