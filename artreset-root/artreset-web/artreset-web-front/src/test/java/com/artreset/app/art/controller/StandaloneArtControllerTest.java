/**
 * 
 */
package com.artreset.app.art.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.artreset.app.art.dto.ArtDTO;
import com.artreset.app.art.dto.ArtDTOBuilder;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.ArtBuilder;
import com.artreset.app.art.service.ArtService;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.model.ArtistBuilder;
import com.artreset.app.artist.service.ArtistService;
import com.artreset.app.category.service.CategoryService;
import com.artreset.common.controller.ErrorController;
import com.artreset.image.model.ImageBuilder;
import com.artreset.image.service.ImageService;
import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.util.TestUtil;

/**
 * @author Taehyun Jung
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StandaloneArtControllerTest {
	
	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    
	private static final String USER_EMAIL = "a@a.com";
	
	private MockMvc mockMvc;
	
	@Mock
	public ArtService artServiceMock;
	
	@Mock
	public ArtistService artistServiceMock;
	
	@Mock
	public CategoryService categoryServiceMock;
	
	@Mock
	public ImageService imageServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ArtController(messageSource(), artServiceMock
						, artistServiceMock, categoryServiceMock, imageServiceMock))
				.setHandlerExceptionResolvers(exceptionResolver())
                .setValidator(validator())
                .setViewResolvers(viewResolver())
                .build();
	}

    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.artreset.app.art.exception.ArtNotFoundException", "error/404");
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
    public void showAddArtForm_호출시_Art_폼객체를_생성하고_화면에_렌더링하는지_테스트() throws Exception {
    	Principal principalMock = mock(Principal.class);
    	
    	when(principalMock.getName()).thenReturn(USER_EMAIL);
    	when(artistServiceMock.findByEmail(principalMock.getName())).thenReturn(new Artist());
    	
    	mockMvc.perform(get("/art/add")
    				.principal(principalMock)
    			)
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(forwardedUrl("/WEB-INF/views/art/add.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("title", isEmptyOrNullString())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("description", isEmptyOrNullString())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("width", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("length", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("height", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("artist", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("assential", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("details", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("categoryId", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("categories", instanceOf(java.util.List.class))))
    			;
    	
    	verify(artistServiceMock, times(1)).findByEmail(principalMock.getName());
    	verify(categoryServiceMock, times(1)).findAll();
    	verifyZeroInteractions(artServiceMock);
    }
    
    @Test
    public void add_호출해_빈폼객체를넘겨주고_필수항목_title과_assential에서_유효성에러를_출력하는지_테스트() throws Exception {
    	ArtDTO formObject = new ArtDTO();
    	
    	mockMvc.perform(post("/art/add")
    				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    				.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
    				.sessionAttr(ArtController.MODEL_ATTRIBUTE_ART, formObject)
    			)
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_ADD))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/add.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("title", isEmptyOrNullString())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("description", isEmptyOrNullString())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("width", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("length", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("height", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("artist", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("assential", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("details", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("categoryId", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("categories", nullValue()))) // DB에서 조회하는 것으로 해야 하나?
    			
    			.andExpect(model().attributeHasFieldErrors(ArtController.MODEL_ATTRIBUTE_ART, "title", "assential"))
    			;
    	
    	verifyZeroInteractions(artServiceMock);
    }
    
    @Test
    public void add_호출시_title과description_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
    	String title = TestUtil.createStringWithLength(Art.MAX_LENGTH_TITLE + 1);
    	String description = TestUtil.createStringWithLength(Art.MAX_LENGTH_DESCRIPTION + 1);
    	
    	ArtDTO formObject = new ArtDTOBuilder()
    			.title(title)
    			.description(description)
    			.build();
    	
    	mockMvc.perform(post("/art/add")
	    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	    			.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	    			.sessionAttr(ArtController.MODEL_ATTRIBUTE_ART, formObject)
    			)
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_ADD))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/add.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("title", is(title))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("description", is(description))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("width", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("length", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("height", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("artist", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("category", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("assentialImage", nullValue())))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("details", nullValue())))
    			
    			.andExpect(model().attributeHasFieldErrors(ArtController.MODEL_ATTRIBUTE_ART, "title", "description"))
    			;
    	
    	verifyZeroInteractions(artServiceMock);
    }
    
    @Test
    public void add_호출후_새로운항목이추가되고_상세화면으로렌더링되는지_테스트() throws Exception {
    	MockMultipartFile assentialMockFile = new MockMultipartFile("assential", "original.jpg", null, "bar".getBytes());
    	
    	ArtDTO formObject = new ArtDTOBuilder()
				.title("title")
				.description("description")
				.assential(assentialMockFile)
				.build();
    	
    	Art added = new ArtBuilder()
    			.id(1L)
    			.title(formObject.getTitle())
    			.description(formObject.getDescription())
    			.build();
    	
    	Principal principalMock = mock(Principal.class);
    	
    	when(principalMock.getName()).thenReturn(USER_EMAIL);
    	when(artistServiceMock.findByEmail(principalMock.getName())).thenReturn(new Artist());
    	
    	//HashMap<String, String> contentTypeParams = new HashMap<String, String>();
    	//contentTypeParams.put("title", "title");
    	MediaType mediaType = new MediaType("multipart", "form-data");
    	
    	when(artServiceMock.add(formObject)).thenReturn(added);
    	
    	String expectedRedirectViewPath = TestUtil.createRedirectViewPath(ArtController.REQUEST_MAPPING_ART_VIEW);
    	
    	mockMvc.perform(
    			post("/art/add")
    			//fileUpload("/art/add").file(assentialMockFile)
    				.principal(principalMock)
	    			.contentType(mediaType)
	    			.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	    			.sessionAttr(ArtController.MODEL_ATTRIBUTE_ART, formObject)
    			)
    			.andDo(print())
    			//.andExpect(model().attributeHasNoErrors("title", "assential", "description"))
    			.andExpect(status().isMovedTemporarily())
    			.andExpect(view().name(expectedRedirectViewPath))
    			.andExpect(model().attribute(ArtController.PARAMETER_ART_ID, is("1")))
    			.andExpect(flash().attribute(ArtController.FLASH_MESSAGE_KEY_FEEDBACK, is("Art entry: title was added.")))
    			;
    	
    	verify(artServiceMock, times(1)).add(formObject);
    	verify(artistServiceMock, times(1)).findByEmail(principalMock.getName());
    	verify(imageServiceMock, times(1)).create(formObject.getAssential());
    	verifyNoMoreInteractions(artistServiceMock, artServiceMock, imageServiceMock, categoryServiceMock);
    }
    
    @Test
    public void findAll_호출시_목록화면이렌더링되고_모든Art항목이_모델에_존재하는지_테스트() throws Exception {
    	
    	Art first = new ArtBuilder()
    			.id(1L)
    			.artist(new ArtistBuilder().id(1L).build())
    			.category(new Category())
    			.title("first")
    			.description("first description")
    			.assentialImage(new ImageBuilder().id(1L).build())
    			.build();
    	
    	Art second = new ArtBuilder()
		    	.id(1L)
		    	.artist(new ArtistBuilder().id(2L).build())
		    	.category(new Category())
		    	.title("second")
		    	.description("second description")
		    	.assentialImage(new ImageBuilder().id(2L).build())
		    	.build();
    	
    	when(artServiceMock.findAll()).thenReturn(Arrays.<Art> asList(first, second));
    	
    	mockMvc.perform(get(ArtController.REQUEST_MAPPING_ART_LIST)
    			)
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_LIST))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/list.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART_LIST, hasSize(2)))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART_LIST, hasItem(
    					allOf (
    							hasProperty("id", is(1L)),
    							hasProperty("title", is("first")),
    							hasProperty("description", is("first description"))
    					)
    			)))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART_LIST, hasItem(
    					allOf (
    							hasProperty("id", is(1L)),
    							hasProperty("title", is("second")),
    							hasProperty("description", is("second description"))
    							)
    					)))
    			;
    	
    	verify(artServiceMock, times(1)).findAll();
    	verifyNoMoreInteractions(artServiceMock, artistServiceMock, imageServiceMock, categoryServiceMock);
    	
    }
    
    @Test
    public void findById_호출시_주어진아이디로해당항목을찾고_상세화면으로렌더링되는지_테스트() throws Exception {
    	Art found = new ArtBuilder()
    			.id(1L)
    			.title("title")
    			.description("description")
    			.build();
    	
    	when(artServiceMock.findById(1L)).thenReturn(found);
    	
    	mockMvc.perform(get("/art/{id}", 1L))
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_VIEW))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/view.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", is(found.getId()))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("title", is(found.getTitle()))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("description", is(found.getDescription()))))
    			;
    	
    	verify(artServiceMock, times(1)).findById(1L);
    	verify(artServiceMock, times(1)).findAll();
    	verifyNoMoreInteractions(artServiceMock);
    }
    
    @Test
    public void showUpdateArtForm_호출되어_주어진아이디로해당항목을찾고_폼객체생성후_업데이트화면으로렌더링되는지_테스트() throws Exception {
    	Art updated = new ArtBuilder()
    			.id(1L)
    			.title("title")
    			.description("description")
    			.width(10F)
    			.length(10F)
    			.height(10F)
    			.build();
    	
    	when(artServiceMock.findById(1L)).thenReturn(updated);
    	
    	mockMvc.perform(get("/art/update/{id}", 1L))
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_UPDATE))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/update.jsp"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", is(1L))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("title", is("title"))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("description", is("description"))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("width", is(10F))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("length", is(10F))))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("height", is(10F))))
    			;
    	
    	verify(artServiceMock, times(1)).findById(1L);
    	verifyNoMoreInteractions(artServiceMock);
    }
    
    @Test
    public void update_빈폼객를_넘겨주고_필수항목_title이_유효성에러를_반환하는지_테스트() throws Exception {
    	ArtDTO formObject = new ArtDTOBuilder().id(1L).build();
    	
    	mockMvc.perform(post("/art/update")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
    			.sessionAttr(ArtController.MODEL_ATTRIBUTE_ART, formObject)
    	)
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(view().name(ArtController.VIEW_ART_UPDATE))
    			.andExpect(forwardedUrl("/WEB-INF/views/art/update.jsp"))
    			.andExpect(model().attributeHasFieldErrors(ArtController.MODEL_ATTRIBUTE_ART, "title"))
    			.andExpect(model().attribute(ArtController.MODEL_ATTRIBUTE_ART, hasProperty("id", is(1L))));
    			;
    }
    
    @Test
    public void update_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
    	ArtDTO formObject = new ArtDTOBuilder()
    			.id(1L)
    			.title("title")
    			.description("description")
    			.build();
    	
    	when(artServiceMock.update(formObject)).thenThrow(new ArtistNotFoundException(""));
    	
    	mockMvc.perform(post("/art/update")
    			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
    			.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
    			.sessionAttr(ArtController.MODEL_ATTRIBUTE_ART, formObject)
    	)
    			.andDo(print())
    			.andExpect(status().isNotFound())
    			.andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
    			.andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"))
    			;
    	
    	verify(artServiceMock, times(1)).update(formObject);
    	verifyNoMoreInteractions(artServiceMock);
    }
}
