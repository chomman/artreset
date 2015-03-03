package com.artreset.admin.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Web Application Context Config Class
 * 
 * @author Taehyun Jung
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { 
		"com.artreset.common.controller",
		"com.artreset.security.controller", 
		"com.artreset.user.controller",
		"com.artreset.admin.app" 
		})
public class WebAppContext extends WebMvcConfigurerAdapter {

	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

		Properties exceptionMappings = new Properties();

		exceptionMappings.put("com.artreset.admin.app.category.exception.CategoryNotFoundException", "error/404");
		exceptionMappings.put("org.springframework.web.servlet.PageNotFound", "error/404");
		exceptionMappings.put("java.lang.Exception", "error/error");
		exceptionMappings.put("java.lang.RuntimeException", "error/error");

		exceptionResolver.setExceptionMappings(exceptionMappings);

		Properties statusCodes = new Properties();

		statusCodes.put("error/404", "404");
		statusCodes.put("error/error", "500");

		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}
	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		configurer
			.mediaType("html", MediaType.TEXT_HTML )
			.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public ViewResolver viewResolver() {

		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setViewResolvers(viewResolvers());
		contentNegotiatingViewResolver.setDefaultViews(defaultView());

		return contentNegotiatingViewResolver;
	}

	private List<View> defaultView() {
		return Arrays.<View> asList(new MappingJackson2JsonView());
	}

	private List<ViewResolver> viewResolvers() {
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		jspViewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(jspViewResolver);

		return viewResolvers;
	}
}
