package com.artreset.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Artreset Application Context Class 
 * 
 * @author Taehyun Jung
 */
@Configuration
@ComponentScan(basePackages = {
        "com.artreset.config",
        "com.artreset.tag",
        "com.artreset.user",
        "com.artreset.security",
        "com.artreset.app.category.service",
        "com.artreset.image.service",
        "com.artreset.app.artist.service",
        "com.artreset.app.art.service",
        "com.artreset.app.sample.service"
})
@Import({WebAppContext.class, PersistenceContext.class, /*SecurityContext.class*/MethodSecurityConfig.class, SocialContext.class})
@PropertySource("classpath:application.properties")
public class ArtresetApplicationContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
	//private static final String[] MESSAGE_SOURCE_BASE_NAME = {"i18n/messages-common", "i18n/messages-todo"};
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
