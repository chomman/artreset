/**
 * 
 */
package com.artreset.admin.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Taehyun Jung
 *
 */
@Component
public class MessageUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);
	
	private static MessageSource messageSource;
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		LOGGER.debug("____________________________MessageUtil AutoWired component scanned");
		MessageUtil.messageSource = messageSource;
	}

	public static String getMessage(String messageCode, Object... messageParameters){
		String message = null;
		
		try {
			Locale current = LocaleContextHolder.getLocale();
			LOGGER.debug("Current locale is {}", current);
			message = messageSource.getMessage(messageCode, messageParameters, current);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return message;
	}
}
