/**
 * 
 */
package com.artreset.admin.app.category.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.artreset.admin.app.category.dto.CategoryDTOBuilder;
import com.artreset.admin.app.category.model.CategoryBuilder;
import com.artreset.admin.common.controller.ErrorController;
import com.artreset.app.category.dto.CategoryDTO;
import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.app.category.service.CategoryService;
import com.artreset.model.Category;
import com.artreset.util.TestUtil;

/**
 * @author Taehyun Jung
 */
@RunWith(MockitoJUnitRunner.class)
public class StandaloneCategoryControllerTest {
	
	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
	
	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    
    private MockMvc mockMvc;
    
    @Mock
    private CategoryService categoryServiceMock;
    
    @Before
    public void setUp() {
    	mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryServiceMock))
    			.setHandlerExceptionResolvers(exceptionResolver())
    			.setValidator(validator())
    			.setViewResolvers(resolvers())
    			.build();
    }

	private HandlerExceptionResolver exceptionResolver() {
    	SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
    	
    	Properties exceptionMappings = new Properties();
    	exceptionMappings.put("com.artreset.app.category.exception.CategoryNotFoundException", "error/404");
    	exceptionMappings.put("java.lang.Exception", "error/error");
    	exceptionMappings.put("java.lang.RuntimeException", "error/error");
    	exceptionResolver.setExceptionMappings(exceptionMappings);
    	
    	Properties statusCodes = new Properties();
    	statusCodes.put("error/404", "404");
    	statusCodes.put("error/error", "500");
    	exceptionResolver.setStatusCodes(statusCodes);
    	
    	return exceptionResolver;
    }
	
	private Validator validator() {
		return new LocalValidatorFactoryBean();
	}
	
	private ViewResolver resolvers() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setViewClass(JstlView.class);		
		viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		
		return viewResolver;
	}

    private MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
	
	@Test
	public void showAddCategoryForm_호출해_Category_폼객체를_생성하고_화면에_렌더링하는지_테스트() throws Exception {
		mockMvc.perform(get("/category/add"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/views/category/add.jsp"))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("id", nullValue())))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("parentId", nullValue())))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("name", isEmptyOrNullString())))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("description", isEmptyOrNullString())))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("parentId", nullValue())));
				;
		verify(categoryServiceMock, times(1)).findAll();
	}
	
	@Test
	public void add_호출시_빈폼객체를_넘겨주고_필수항목_name에_요효성에러를출력하는지_테스트() throws Exception {
		CategoryDTO formObject = new CategoryDTO();
		mockMvc.perform(post("/category/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
				.sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
			)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(CategoryController.VIEW_CATEGORY_ADD))
				.andExpect(forwardedUrl("/WEB-INF/views/category/add.jsp"))
				.andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "name"))
			;
		
		verifyZeroInteractions(categoryServiceMock);
	}
	
	 @Test
	 public void add_호출시_name과description_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
		 String name = TestUtil.createStringWithLength(Category.MAX_LENGTH_NAME + 1);
		 String description = TestUtil.createStringWithLength(Category.MAX_LENGTH_DESCRIPTION + 1);
		 
		 CategoryDTO formObject = new CategoryDTOBuilder()
		 		.name(name)
		 		.description(description)
		 		.build();
		 
		mockMvc.perform(post("/category/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
				.sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
			)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name(CategoryController.VIEW_CATEGORY_ADD))
				.andExpect(forwardedUrl("/WEB-INF/views/category/add.jsp"))
				.andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "name"))
				.andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "description"))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("id", nullValue())))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("name", is(name))))
				.andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("description", is(description))))
			;
		
		verifyZeroInteractions(categoryServiceMock);
	 }
	 
	 
	 @Test
    public void add_호출후_새로운항목이추가되고_상세화면으로렌더링되는지_테스트() throws Exception {
        CategoryDTO formObject = new CategoryDTOBuilder()
		        .name("name")
                .description("description")
                .build();

        Category added = new CategoryBuilder()
                .id(1L)
                .name(formObject.getName())
                .description(formObject.getDescription())
                .build();

        when(categoryServiceMock.add(formObject)).thenReturn(added);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(CategoryController.REQUEST_MAPPING_CATEGORY_VIEW);

        mockMvc.perform(post("/category/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
        )
        		.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(model().attribute(CategoryController.PARAMETER_CATEGORY_ID, is("1")))
                //.andExpect(flash().attribute(CategoryController.FEEDBACK_MESSAGE_KEY_CATEGORY_ADDED, is("카테고리: name가(이) 추가되었습니다.")))
        ;

        verify(categoryServiceMock, times(1)).add(formObject);
        verifyNoMoreInteractions(categoryServiceMock);
    }
	 
	 
	 @Test
	    public void findAll_호출시_목록화면이렌더링되고_모든Category항목이_모델에_존재하는지_테스트() throws Exception {
	        Category first = new CategoryBuilder()
	                .id(1L)
	                .name("Foo")
	                .description("Lorem ipsum")
	                .build();

	        Category second = new CategoryBuilder()
	                .id(2L)
	                .name("Bar")
	                .description("Lorem ipsum")
	                .build();

	        when(categoryServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

	        mockMvc.perform(get("/category"))
	        		.andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name(CategoryController.VIEW_CATEGORY_LIST))
	                .andExpect(forwardedUrl("/WEB-INF/views/category/list.jsp"))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY_LIST, hasSize(2)))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY_LIST, hasItem(
	                        allOf(
	                                hasProperty("id", is(1L)),
	                                hasProperty("name", is("Foo")),
	                                hasProperty("description", is("Lorem ipsum"))
	                        )
	                )))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY_LIST, hasItem(
	                        allOf(
	                                hasProperty("id", is(2L)),
	                                hasProperty("name", is("Bar")),
	                                hasProperty("description", is("Lorem ipsum"))
	                        )
	                )));

	        verify(categoryServiceMock, times(1)).findAll();
	        verifyNoMoreInteractions(categoryServiceMock);
	    }
	 


	    @Test
	    public void findById_호출시_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
	        when(categoryServiceMock.findById(1L)).thenThrow(new CategoryNotFoundException(""));

	        mockMvc.perform(get("/category/{id}", 1L))
	        		.andDo(print())
	                .andExpect(status().isNotFound())
	                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
	                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

	        verify(categoryServiceMock, times(1)).findById(1L);
	        verifyZeroInteractions(categoryServiceMock);
	    }
	 
	 @Test
	    public void deleteById_호출시_주어진아이디로해당항목을찾고_해당항목을삭제후_목록화면을렌더링하는지_테스트() throws Exception {
	        Category deleted = new CategoryBuilder()
	                .id(1L)
	                .name("Foo")
	                .description("Bar")
	                .build();

	        when(categoryServiceMock.deleteById(1L)).thenReturn(deleted);

	        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(CategoryController.REQUEST_MAPPING_CATEGORY_LIST);

	        mockMvc.perform(get("/category/delete/{id}", 1L))
	        		.andDo(print())
	                .andExpect(status().isMovedTemporarily())
	                .andExpect(view().name(expectedRedirectViewPath))
	                //.andExpect(flash().attribute(CategoryController.FLASH_MESSAGE_KEY_FEEDBACK, is("Category entry: Foo was deleted.")))
	                ;

	        verify(categoryServiceMock, times(1)).deleteById(1L);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }

	    @Test
	    public void deleteById_호출시_주어진아이디의항목이없으면_404화면을렌더링하는지_테스트() throws Exception {
	        when(categoryServiceMock.deleteById(1L)).thenThrow(new CategoryNotFoundException(""));

	        mockMvc.perform(get("/category/delete/{id}", 1L))
	        		.andDo(print())
	                .andExpect(status().isNotFound())
	                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
	                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

	        verify(categoryServiceMock, times(1)).deleteById(1L);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }
	    @Test
	    public void showUpdateCategoryForm_호출되어_주어진아이디로해당항목을찾고_폼객체생성후_업데이트화면으로렌더링되는지_테스트() throws Exception {
	        Category updated = new CategoryBuilder()
	                .id(1L)
	                .name("Foo")
	                .description("Lorem ipsum")
	                .build();

	        when(categoryServiceMock.findById(1L)).thenReturn(updated);

	        mockMvc.perform(get("/category/update/{id}", 1L))
	        		.andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name(CategoryController.VIEW_CATEGORY_UPDATE))
	                .andExpect(forwardedUrl("/WEB-INF/views/category/update.jsp"))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("id", is(1L))))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("description", is("Lorem ipsum"))))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("name", is("Foo"))));

	        verify(categoryServiceMock, times(1)).findById(1L);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }

	    @Test
	    public void showUpdateCategoryForm_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
	        when(categoryServiceMock.findById(1L)).thenThrow(new CategoryNotFoundException(""));

	        mockMvc.perform(get("/category/update/{id}", 1L))
	        		.andDo(print())
	                .andExpect(status().isNotFound())
	                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
	                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

	        verify(categoryServiceMock, times(1)).findById(1L);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }

	    @Test
	    public void update__빈폼객를_넘겨주고_필수항목_name이_유효성에러를_반환하는지_테스트() throws Exception {
	        CategoryDTO formObject = new CategoryDTOBuilder()
	                .id(1L)
	                .build();

	        mockMvc.perform(post("/category/update")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	                .sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
	        )
	        		.andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name(CategoryController.VIEW_CATEGORY_UPDATE))
	                .andExpect(forwardedUrl("/WEB-INF/views/category/update.jsp"))
	                .andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "name"))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("id", is(1L))))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("description", isEmptyOrNullString())))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("name", isEmptyOrNullString())));

	        verifyZeroInteractions(categoryServiceMock);
	    }

	    @Test
	    public void update_name_description_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
	        String name = TestUtil.createStringWithLength(Category.MAX_LENGTH_NAME + 1);
	        String description = TestUtil.createStringWithLength(Category.MAX_LENGTH_DESCRIPTION + 1);

	        CategoryDTO formObject = new CategoryDTOBuilder()
	                .id(1L)
	                .name(name)
	                .description(description)
	                .build();

	        mockMvc.perform(post("/category/update")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	                .sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
	        )
	        		.andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name(CategoryController.VIEW_CATEGORY_UPDATE))
	                .andExpect(forwardedUrl("/WEB-INF/views/category/update.jsp"))
	                .andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "name"))
	                .andExpect(model().attributeHasFieldErrors(CategoryController.MODEL_ATTRIBUTE_CATEGORY, "description"))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("id", is(1L))))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("description", is(description))))
	                .andExpect(model().attribute(CategoryController.MODEL_ATTRIBUTE_CATEGORY, hasProperty("name", is(name))));

	        verifyZeroInteractions(categoryServiceMock);
	    }

	    @Test
	    public void update_호출시_주어진아이디로해당항목을찾고_해당항목을수정후_목록화면을렌더링하는지_테스트() throws Exception {
	        CategoryDTO formObject = new CategoryDTOBuilder()
	                .id(1L)
	                .name("name")
	                .description("description")
	                .build();

	        Category updated = new CategoryBuilder()
	                .id(1L)
	                .name("name")
	                .description("description")
	                .build();

	        when(categoryServiceMock.update(formObject)).thenReturn(updated);

	        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(CategoryController.REQUEST_MAPPING_CATEGORY_VIEW);

	        mockMvc.perform(post("/category/update")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	                .sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
	        )
	        		.andDo(print())
	                .andExpect(status().isMovedTemporarily())
	                .andExpect(view().name(expectedRedirectViewPath))
	                .andExpect(model().attribute(CategoryController.PARAMETER_CATEGORY_ID, is("1")))
	                //.andExpect(flash().attribute(CategoryController.FLASH_MESSAGE_KEY_FEEDBACK, is("할일: name 이 수정되었습니다.")))
	        ;

	        verify(categoryServiceMock, times(1)).update(formObject);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }

	    @Test
	    public void update_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
	        CategoryDTO formObject = new CategoryDTOBuilder()
	                .id(1L)
	                .name("name")
	                .description("description")
	                .build();

	        when(categoryServiceMock.update(formObject)).thenThrow(new CategoryNotFoundException(""));

	        mockMvc.perform(post("/category/update")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
	                .sessionAttr(CategoryController.MODEL_ATTRIBUTE_CATEGORY, formObject)
	        )
	        		.andDo(print())
	                .andExpect(status().isNotFound())
	                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
	                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

	        verify(categoryServiceMock, times(1)).update(formObject);
	        verifyNoMoreInteractions(categoryServiceMock);
	    }
	 

}
