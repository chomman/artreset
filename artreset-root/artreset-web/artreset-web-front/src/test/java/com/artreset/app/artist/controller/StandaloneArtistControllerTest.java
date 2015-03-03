package com.artreset.app.artist.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.ArtBuilder;
import com.artreset.app.art.service.ArtService;
import com.artreset.app.artist.dto.ArtistDTO;
import com.artreset.app.artist.dto.ArtistDTOBuilder;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.model.ArtistBuilder;
import com.artreset.app.artist.service.ArtistService;
import com.artreset.common.controller.ErrorController;
import com.artreset.image.model.ImageBuilder;
import com.artreset.image.service.ImageService;
import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.util.TestUtil;

/**
 * @author Taehyun Jung
 */
@RunWith(MockitoJUnitRunner.class)
public class StandaloneArtistControllerTest {

	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    
	private static final String EMAIL = "a@a.com";

    private MockMvc mockMvc;

    @Mock
    private ArtistService artistServiceMock;
    
    @Mock
    private ArtService artServiceMock;
    
    @Mock
    private ImageService imageServiceMock;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ArtistController(messageSource(), artistServiceMock, artServiceMock, imageServiceMock))
                .setHandlerExceptionResolvers(exceptionResolver())
                .setValidator(validator())
                .setViewResolvers(viewResolver())
                .build();
    }

    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.artreset.app.artist.exception.ArtistNotFoundException", "error/404");
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
    public void showAddArtistForm_호출시_ArtistNotFoundException_발생하지_않는경우_ARTIST_EXIST_화면을_렌더링하는지_테스트() throws Exception {
    	Principal principal = Mockito.mock(Principal.class);
    	
    	Artist model = new ArtistBuilder()
    			.id(1L)
    			.build();
    	
    	when(principal.getName()).thenReturn(EMAIL);
    	when(artistServiceMock.findByEmail(principal.getName())).thenReturn(model);
    	
        mockMvc.perform(get("/artist/add")
        			.principal(principal)
        		)
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_EXIST))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/exist.jsp"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, is(model)));
        
        verify(artistServiceMock, times(1)).findByEmail(principal.getName());
        verifyZeroInteractions(artistServiceMock);
    }
	
	@Test
	@SuppressWarnings("unchecked")
	public void showAddArtistForm_호출해_ARTIST_폼객체를_생성하고_화면에_렌더링하는지_테스트() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		
		when(artistServiceMock.findByEmail(principal.getName())).thenThrow(ArtistNotFoundException.class);
		
		mockMvc.perform(get("/artist/add")
					.principal(principal)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(ArtistController.VIEW_ARTIST_ADD))
				.andExpect(forwardedUrl("/WEB-INF/views/artist/add.jsp"))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("id", nullValue())))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("nickName", isEmptyOrNullString())))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("description", isEmptyOrNullString())))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("shortDescription", isEmptyOrNullString())))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("avatar", is(nullValue()))))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("pageCover", is(nullValue()))))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("avatarId", is(nullValue()))))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("pageCoverId", is(nullValue()))))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("avatarThumbnailUrl", isEmptyOrNullString())))
				.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("pageCoverThumbnailurl", isEmptyOrNullString())))
				;
		
		verify(artistServiceMock, times(1)).findByEmail(principal.getName());
		verifyZeroInteractions(artistServiceMock);
	}

    @Test
    public void add_호출시_nickNamee과description과shortDescription_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
        String nickname = TestUtil.createStringWithLength(Artist.MAX_LENGTH_NICKNAME + 1);
        String description = TestUtil.createStringWithLength(Artist.MAX_LENGTH_DESCRIPTION + 1);
        String shortDescription = TestUtil.createStringWithLength(Artist.MAX_LENGTH_SHORT_DESCRIPTION + 1);

        ArtistDTO formObject =  new ArtistDTOBuilder()
        		.nickName(nickname)
                .description(description)
                .shortDescription(shortDescription)
                .build();
        
        mockMvc.perform(post("/artist/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(ArtistController.MODEL_ATTRIBUTE_ARTIST, formObject)
        )
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_ADD))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/add.jsp"))
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "nickName"))
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "description"))
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "shortDescription"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("id", nullValue())))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("nickName", is(nickname))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("description", is(description))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("shortDescription", is(shortDescription))))
        
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "nickName", "description", "shortDescription"))
        ;

        verifyZeroInteractions(artistServiceMock);
    }

    @Test
    public void add_호출후_새로운항목이추가되고_상세화면으로렌더링되는지_테스트() throws Exception {
        ArtistDTO formObject = new ArtistDTOBuilder()
                .description("description")
                .nickName("nickName")
                .build();

        Artist added = new ArtistBuilder()
                .id(1L)
                .description(formObject.getDescription())
                .nickName(formObject.getNickName())
                .build();

        when(artistServiceMock.add(formObject)).thenReturn(added);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(ArtistController.REQUEST_MAPPING_ARTIST_VIEW);

        mockMvc.perform(post("/artist/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(ArtistController.MODEL_ATTRIBUTE_ARTIST, formObject)
        )
        		.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(model().attribute(ArtistController.PARAMETER_ARTIST_ID, is("1")))
                .andExpect(flash().attribute(ArtistController.FLASH_MESSAGE_KEY_FEEDBACK, is("Artist entry: nickName was added.")));

        verify(artistServiceMock, times(1)).add(formObject);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void deleteById_호출시_주어진아이디로해당항목을찾고_해당항목을삭제후_목록화면을렌더링하는지_테스트() throws Exception {
        Artist deleted = new ArtistBuilder()
                .id(1L)
                .description("Bar")
                .nickName("Foo")
                .build();

        when(artistServiceMock.deleteById(1L)).thenReturn(deleted);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(ArtistController.REQUEST_MAPPING_ARTIST_LIST);

        mockMvc.perform(get("/artist/delete/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(flash().attribute(ArtistController.FLASH_MESSAGE_KEY_FEEDBACK, is("Artist entry: Foo was deleted.")));

        verify(artistServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void deleteById_호출시_주어진아이디의항목이없으면_404화면을렌더링하는지_테스트() throws Exception {
        when(artistServiceMock.deleteById(1L)).thenThrow(new ArtistNotFoundException(""));

        mockMvc.perform(get("/artist/delete/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(artistServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void findAll_호출시_목록화면이렌더링되고_모든ARTIST항목이_모델에_존재하는지_테스트() throws Exception {
        Artist first = new ArtistBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .nickName("Foo")
                .build();

        Artist second = new ArtistBuilder()
                .id(2L)
                .description("Lorem ipsum")
                .nickName("Bar")
                .build();

        when(artistServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/artist"))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_LIST))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/list.jsp"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST_LIST, hasSize(2)))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST_LIST, hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("nickName", is("Foo"))
                        )
                )))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST_LIST, hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("nickName", is("Bar"))
                        )
                )));

        verify(artistServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void findById_호출시_주어진아이디로해당항목을찾고_상세화면으로렌더링되는지_테스트() throws Exception {
        Artist found = new ArtistBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .nickName("Foo")
                .build();
        
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

        when(artistServiceMock.findById(1L)).thenReturn(found);
        when(artServiceMock.findAll()).thenReturn(Arrays.<Art> asList(first, second));

        mockMvc.perform(get("/artist/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_VIEW))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/view.jsp"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("id", is(1L))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("description", is("Lorem ipsum"))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("nickName", is("Foo"))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ART_LIST, hasItem(
    					allOf (
    							hasProperty("id", is(1L)),
    							hasProperty("title", is("first")),
    							hasProperty("description", is("first description"))
    					)
    			)))
    			.andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ART_LIST, hasItem(
    					allOf (
    							hasProperty("id", is(1L)),
    							hasProperty("title", is("second")),
    							hasProperty("description", is("second description"))
    							)
    					)))
    			;

        verify(artistServiceMock, times(1)).findById(1L);
        verify(artServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void findById_호출시_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        when(artistServiceMock.findById(1L)).thenThrow(new ArtistNotFoundException(""));

        mockMvc.perform(get("/artist/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(artistServiceMock, times(1)).findById(1L);
        verifyZeroInteractions(artistServiceMock);
    }

    @Test
    public void showUpdateArtistForm_호출되어_주어진아이디로해당항목을찾고_폼객체생성후_업데이트화면으로렌더링되는지_테스트() throws Exception {
        Artist updated = new ArtistBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .nickName("Foo")
                .build();

        when(artistServiceMock.findById(1L)).thenReturn(updated);

        mockMvc.perform(get("/artist/update/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_UPDATE))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/update.jsp"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("id", is(1L))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("description", is("Lorem ipsum"))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("nickName", is("Foo"))));

        verify(artistServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void showUpdateArtistForm_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        when(artistServiceMock.findById(1L)).thenThrow(new ArtistNotFoundException(""));

        mockMvc.perform(get("/artist/update/{id}", 1L))
        		.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(artistServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void update_nickName_description_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
        String nickName = TestUtil.createStringWithLength(Artist.MAX_LENGTH_NICKNAME + 1);
        String description = TestUtil.createStringWithLength(Artist.MAX_LENGTH_DESCRIPTION + 1);

        ArtistDTO formObject = new ArtistDTOBuilder()
                .id(1L)
                .description(description)
                .nickName(nickName)
                .build();

        mockMvc.perform(post("/artist/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(ArtistController.MODEL_ATTRIBUTE_ARTIST, formObject)
        )
        		.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(ArtistController.VIEW_ARTIST_UPDATE))
                .andExpect(forwardedUrl("/WEB-INF/views/artist/update.jsp"))
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "nickName"))
                .andExpect(model().attributeHasFieldErrors(ArtistController.MODEL_ATTRIBUTE_ARTIST, "description"))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("id", is(1L))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("description", is(description))))
                .andExpect(model().attribute(ArtistController.MODEL_ATTRIBUTE_ARTIST, hasProperty("nickName", is(nickName))));

        verifyZeroInteractions(artistServiceMock);
    }

    @Test
    public void update_호출시_주어진아이디로해당항목을찾고_해당항목을수정후_목록화면을렌더링하는지_테스트() throws Exception {
        ArtistDTO formObject = new ArtistDTOBuilder()
                .id(1L)
                .description("description")
                .nickName("nickName")
                .build();

        Artist updated = new ArtistBuilder()
                .id(1L)
                .description("description")
                .nickName("nickName")
                .build();

        when(artistServiceMock.update(formObject)).thenReturn(updated);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(ArtistController.REQUEST_MAPPING_ARTIST_VIEW);

        mockMvc.perform(post("/artist/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(ArtistController.MODEL_ATTRIBUTE_ARTIST, formObject)
        )
        		.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(model().attribute(ArtistController.PARAMETER_ARTIST_ID, is("1")))
                .andExpect(flash().attribute(ArtistController.FLASH_MESSAGE_KEY_FEEDBACK, is("Artist entry: nickName was updated.")));

        verify(artistServiceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(artistServiceMock);
    }

    @Test
    public void update_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        ArtistDTO formObject = new ArtistDTOBuilder()
                .id(1L)
                .description("description")
                .nickName("nickName")
                .build();

        when(artistServiceMock.update(formObject)).thenThrow(new ArtistNotFoundException(""));

        mockMvc.perform(post("/artist/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(ArtistController.MODEL_ATTRIBUTE_ARTIST, formObject)
        )
        		.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(artistServiceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(artistServiceMock);
    }
}
