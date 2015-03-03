/**
 * 
 */
package com.artreset.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import sun.awt.AppContext;
import sun.awt.SunToolkit;

/**
 * @author Taehyun Jung
 *
 */
@Component
@SuppressWarnings("restriction")
public class ContextConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextConfiguration.class);

	/** 
	 * IIO
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		LOGGER.debug("ContextRefreshedEvent Handler Listener..");
		//refresh awt context
		if(AppContext.getAppContext() == null){
			LOGGER.debug("AppContext.getAppContext() is null. AppContext Initialize..");
			SunToolkit.createNewAppContext();
		}
	}

}
