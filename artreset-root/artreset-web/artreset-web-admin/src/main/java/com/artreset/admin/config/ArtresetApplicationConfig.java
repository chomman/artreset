package com.artreset.admin.config;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Artreset Applicatoin Config Class
 * 
 * @author Taehyun Jung
 */
public class ArtresetApplicationConfig implements WebApplicationInitializer {
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";
    private static final String BASE_ENCODING = "UTF-8";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // When not use XML Settings
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ArtresetApplicationContext.class);

        // When use XML Settings
        //XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        //rootContext.setConfigLocation("classpath:exampleApplicationContext.xml");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(BASE_ENCODING);
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        // Spring Security Settings
        //FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        //security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        // Sitemesh Settings
        FilterRegistration.Dynamic sitemesh = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
        sitemesh.addMappingForUrlPatterns(dispatcherTypes, true, "*.jsp");

        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}


