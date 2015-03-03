package com.artreset.config;

import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.artreset.app.art.service.ArtService;
import com.artreset.app.artist.service.ArtistService;
import com.artreset.app.category.service.CategoryService;
import com.artreset.app.sample.service.TodoService;
import com.artreset.image.service.ImageService;
import com.artreset.user.service.UserService;

/**
 * @author Taehyun Jung
 */
@Configuration
public class UnitTestContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
    
    @Bean
    public CategoryService categoryService() {
    	return mock(CategoryService.class);
    }
    
    @Bean
    public TodoService todoService() {
        return Mockito.mock(TodoService.class);
    }
    
    @Bean
    public ImageService imageService() {
    	return Mockito.mock(ImageService.class);
    }
    
    @Bean
    public ArtistService artistService() {
    	return Mockito.mock(ArtistService.class);
    }
    
    @Bean
    public ArtService artService() {
    	return Mockito.mock(ArtService.class);
    }
}
