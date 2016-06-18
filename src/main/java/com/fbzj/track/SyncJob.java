package com.fbzj.track;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbzj.track.enums.MapTypeEnum;
import com.fbzj.track.service.DataService;

/**
 * 同步工作类
 * 
 * @author afeey
 *
 */
@Component
public class SyncJob {

	private static final Logger logger = LoggerFactory.getLogger(SyncJob.class);

	@Autowired
	@Qualifier("dataService")
	private DataService dataService;
	private List<String> imeis;

	public SyncJob() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		imeis = new ArrayList<String>();
		imeis.add("868120140229813");
		imeis.add("000000000000001");
	}

	@Scheduled(cron = "0/2 * * * * ?")
	public void syncData() {
		try {
			long s1 = System.currentTimeMillis();
			dataService.getDeviceLastTrack(imeis, MapTypeEnum.Wgs84);
			long s2 = System.currentTimeMillis();
			logger.debug("{}-{}: excute use {} ms", Thread.currentThread().getId(), Thread.currentThread().getName(), s2 - s1);
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
		}

	}
}
