package com.fbzj.track;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 轨迹同步
 * @author afeey
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main( String[] args )
    {
    	logger.info("服务启动");
    	@SuppressWarnings({ "resource", "unused" })
		ApplicationContext ac = new ClassPathXmlApplicationContext("config/spring-context.xml");
    }
}
