package com.artreset.admin.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Artreset Application Context Class 
 * 
 * @author Taehyun Jung
 */
@Configuration
@ComponentScan(basePackages = {
        "com.artreset.user.service",
        "com.artreset.app.category.service",
        "com.artreset.admin.util"
})
@Import({WebAppContext.class, PersistenceContext.class, SecurityContext.class})
@PropertySource("classpath:application.properties")
public class ArtresetApplicationContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
    private static final String BASE_ENCODING = "UTF-8";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setDefaultEncoding(BASE_ENCODING);
        messageSource.setCacheSeconds(600);
        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        //messageSource.setBasenames(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
