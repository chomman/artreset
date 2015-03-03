package com.artreset.app.sample.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.artreset.app.sample.dto.TodoDTO;
import com.artreset.app.sample.dto.TodoDTOBuilder;
import com.artreset.app.sample.exception.TodoNotFoundException;
import com.artreset.app.sample.model.TodoBuilder;
import com.artreset.app.sample.service.TodoService;
import com.artreset.common.controller.ErrorController;
import com.artreset.config.UnitTestContext;
import com.artreset.config.WebAppContext;
import com.artreset.model.Todo;
import com.artreset.util.TestUtil;

/**
 * @author Taehyun Jung
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UnitTestContext.class, WebAppContext.class})
//@ContextConfiguration(locations = {"classpath:testContext.xml", "classpath:exampleApplicationContext-web.xml"})
@WebAppConfiguration
public class WebApplicationContextTodoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TodoService todoServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(todoServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void showAddTodoForm_호출해_TODO_폼객체를_생성하고_화면에_렌더링하는지_테스트() throws Exception {
        mockMvc.perform(get("/todo/add"))
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_ADD))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/add.jsp"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", nullValue())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", isEmptyOrNullString())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", isEmptyOrNullString())));

        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void add_빈폼객를_넘겨주고_필수항목_title이_유효성에러를_반환하는지_테스트() throws Exception {
        TodoDTO formObject = new TodoDTO();
        mockMvc.perform(post("/todo/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_ADD))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/add.jsp"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", nullValue())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", isEmptyOrNullString())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", isEmptyOrNullString())));

        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void addDescription_Title_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
        String title = TestUtil.createStringWithLength(Todo.MAX_LENGTH_TITLE + 1);
        String description = TestUtil.createStringWithLength(Todo.MAX_LENGTH_DESCRIPTION + 1);

        TodoDTO formObject =  new TodoDTOBuilder()
                .description(description)
                .title(title)
                .build();

        mockMvc.perform(post("/todo/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_ADD))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/add.jsp"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "description"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", nullValue())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is(description))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is(title))));

        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void add_호출후_새로운항목이추가되고_상세화면으로렌더링되는지_테스트() throws Exception {
        TodoDTO formObject = new TodoDTOBuilder()
                .description("description")
                .title("title")
                .build();

        Todo added = new TodoBuilder()
                .id(1L)
                .description(formObject.getDescription())
                .title(formObject.getTitle())
                .build();

        when(todoServiceMock.add(formObject)).thenReturn(added);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_VIEW);

        mockMvc.perform(post("/todo/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(redirectedUrl("/todo/1"))
                .andExpect(model().attribute(TodoController.PARAMETER_TODO_ID, is("1")))
                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("할일: title 이 추가되었습니다.")));

        verify(todoServiceMock, times(1)).add(formObject);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void deleteById_호출시_주어진아이디로해당항목을찾고_해당항목을삭제후_목록화면을렌더링하는지_테스트() throws Exception {
        Todo deleted = new TodoBuilder()
                .id(1L)
                .description("Bar")
                .title("Foo")
                .build();

        when(todoServiceMock.deleteById(1L)).thenReturn(deleted);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_LIST);

        mockMvc.perform(get("/todo/delete/{id}", 1L))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("Todo entry: Foo was deleted.")));

        verify(todoServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void deleteById_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        when(todoServiceMock.deleteById(1L)).thenThrow(new TodoNotFoundException(""));

        mockMvc.perform(get("/todo/delete/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(todoServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void findAll_호출시_목록화면이렌더링되고_모든TODO항목이_모델에_존재하는지_테스트() throws Exception {
        Todo first = new TodoBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .title("Foo")
                .build();

        Todo second = new TodoBuilder()
                .id(2L)
                .description("Lorem ipsum")
                .title("Bar")
                .build();

        when(todoServiceMock.findAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_LIST))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/list.jsp"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasSize(2)))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("title", is("Foo"))
                        )
                )))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("title", is("Bar"))
                        )
                )));

        verify(todoServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void findById_호출시_주어진아이디로해당항목을찾고_상세화면으로렌더링되는지_테스트() throws Exception {
        Todo found = new TodoBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .title("Foo")
                .build();

        when(todoServiceMock.findById(1L)).thenReturn(found);

        mockMvc.perform(get("/todo/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_VIEW))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/view.jsp"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is("Lorem ipsum"))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is("Foo"))));

        verify(todoServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void findById_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        when(todoServiceMock.findById(1L)).thenThrow(new TodoNotFoundException(""));

        mockMvc.perform(get("/todo/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(todoServiceMock, times(1)).findById(1L);
        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void showUpdateTodoForm_호출되어_주어진아이디로해당항목을찾고_폼객체생성후_업데이트화면으로렌더링되는지_테스트() throws Exception {
        Todo updated = new TodoBuilder()
                .id(1L)
                .description("Lorem ipsum")
                .title("Foo")
                .build();

        when(todoServiceMock.findById(1L)).thenReturn(updated);

        mockMvc.perform(get("/todo/update/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/update.jsp"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is("Lorem ipsum"))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is("Foo"))));

        verify(todoServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void showUpdateTodoForm_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        when(todoServiceMock.findById(1L)).thenThrow(new TodoNotFoundException(""));

        mockMvc.perform(get("/todo/update/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(todoServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void update_EmptyTodoEntry_빈폼객를_넘겨주고_필수항목_title이_유효성에러를_반환하는지_테스트() throws Exception {
        TodoDTO formObject = new TodoDTOBuilder()
                .id(1L)
                .build();

        mockMvc.perform(post("/todo/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/update.jsp"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", isEmptyOrNullString())))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", isEmptyOrNullString())));

        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void update_title_description_길이가_너무길때_유효성에러를출력하는지_테스트() throws Exception {
        String title = TestUtil.createStringWithLength(Todo.MAX_LENGTH_TITLE + 1);
        String description = TestUtil.createStringWithLength(Todo.MAX_LENGTH_DESCRIPTION + 1);

        TodoDTO formObject = new TodoDTOBuilder()
                .id(1L)
                .description(description)
                .title(title)
                .build();

        mockMvc.perform(post("/todo/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isOk())
                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
                .andExpect(forwardedUrl("/WEB-INF/views/sample/todo/update.jsp"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "description"))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is(description))))
                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is(title))));

        verifyZeroInteractions(todoServiceMock);
    }

    @Test
    public void update_호출시_주어진아이디로해당항목을찾고_해당항목을수정후_목록화면을렌더링하는지_테스트() throws Exception {
        TodoDTO formObject = new TodoDTOBuilder()
                .id(1L)
                .description("description")
                .title("title")
                .build();

        Todo updated = new TodoBuilder()
                .id(1L)
                .description("description")
                .title("title")
                .build();

        when(todoServiceMock.update(formObject)).thenReturn(updated);

        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_VIEW);

        mockMvc.perform(post("/todo/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name(expectedRedirectViewPath))
                .andExpect(model().attribute(TodoController.PARAMETER_TODO_ID, is("1")))
                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("할일: title 이 수정되었습니다.")));

        verify(todoServiceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(todoServiceMock);
    }

    @Test
    public void update_주어진아이디의항목이없을경우_404화면으로렌더링되는지_테스트() throws Exception {
        TodoDTO formObject = new TodoDTOBuilder()
                .id(1L)
                .description("description")
                .title("title")
                .build();

        when(todoServiceMock.update(formObject)).thenThrow(new TodoNotFoundException(""));

        mockMvc.perform(post("/todo/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(TestUtil.convertObjectToFormUrlEncodedBytes(formObject))
                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, formObject)
        )
                .andExpect(status().isNotFound())
                .andExpect(view().name(ErrorController.VIEW_NOT_FOUND))
                .andExpect(forwardedUrl("/WEB-INF/views/error/404.jsp"));

        verify(todoServiceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(todoServiceMock);
    }
}
