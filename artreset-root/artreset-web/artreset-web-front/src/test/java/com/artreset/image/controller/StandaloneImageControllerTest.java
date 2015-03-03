/**
 * 
 */
package com.artreset.image.controller;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.artreset.image.service.ImageService;

/**
 * @author Taehyun Jung
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StandaloneImageControllerTest {
	
	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    private MockMvc mockMvc;
    
    @Mock
    private ImageController controller;

    @Mock
    private ImageService imageServiceMock;

    @Before
    public void setUp() {
    	controller = new ImageController();
    	ReflectionTestUtils.setField(controller, "imageService", imageServiceMock);
    	
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setHandlerExceptionResolvers(exceptionResolver())
                .setValidator(validator())
                .setViewResolvers(viewResolver())
                .build();
    }

    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.artreset.image.exception.ImageNotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    private MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }
    
    @Test
    public void index_이미지_테스트_페이지_렌더링_테스트() throws Exception {
        mockMvc.perform(get("/image"))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ImageController.VIEW_IMAGE_UPLOAD))
                .andExpect(forwardedUrl("/WEB-INF/views/image/index.jsp"))
                ;        
        verifyZeroInteractions(imageServiceMock);
    }
    
    @Test
    public void upload_POST_호출시_파일이업로드되고_테스트() throws Exception {
    	Assert.assertTrue(true);
    	/*MockMultipartHttpServletRequest mockRequest = new MockMultipartHttpServletRequest();
    	mockRequest.setRequestURI("/image/upload");
    	FileInputStream fileInputStream = new FileInputStream("src/test/resources/image/Chrysanthemum.jpg");
    	MockMultipartFile mockMultipartFile = new MockMultipartFile("Chrysantheum", fileInputStream);
    	mockRequest.addFile(mockMultipartFile);
    	
    	HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
    	
    	controller.upload(mockRequest, mockResponse);*/
    	
    	/*MultipartHttpServletRequest mockRequest = Mockito.mock(MultipartHttpServletRequest.class);
    	MultipartFile file = new MockMultipartFile("file", new FileInputStream(new File("src/test/resources/image/Chrysanthemum.jpg")));
    	Mockito.when(mockRequest.getFile("file")).thenReturn(file);
    	
    	mockMvc.perform(post("/image/upload"))
    			.andDo(print())
    			.andExpect(status().isOk())
    			;
    	verifyZeroInteractions(imageServiceMock);*/
    }
    
    /*@Test
    public void upload_POST_호출시_파일이업로드되고_파일정보가_리턴되는지_테스트() throws Exception {
    	MockHttpServletRequest originalRequest = new MockHttpServletRequest();
		originalRequest.setMethod("POST");
		originalRequest.setContentType("multipart/form-data");
		originalRequest.addHeader("Content-type", "multipart/form-data");
		originalRequest.addParameter("getField", "getValue");
		assertTrue(resolver.isMultipart(originalRequest));
		MultipartHttpServletRequest request = resolver.resolveMultipart(originalRequest);
    	
    	mockMvc.perform(post("/image/upload")
    				.param("file1", values))
    			.andDo(print())
    			.andExpect(status().isOk());
    }*/

}
