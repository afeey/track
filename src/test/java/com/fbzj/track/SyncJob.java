package com.fbzj.track;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncJob {
	private static fi
	
	@Scheduled(cron = "0/1 * * * * ?")
    public void syncData() {  
        System.out.println("任务进行中。。。");
    }  
}
