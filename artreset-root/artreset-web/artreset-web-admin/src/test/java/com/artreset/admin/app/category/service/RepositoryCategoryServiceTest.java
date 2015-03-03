/**
 * RepositoryCategoryService 클래스를 테스트 
 */
package com.artreset.admin.app.category.service;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.artreset.admin.app.category.dto.CategoryDTOBuilder;
import com.artreset.admin.app.category.model.CategoryBuilder;
import com.artreset.app.category.dto.CategoryDTO;
import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.app.category.repository.CategoryRepository;
import com.artreset.app.category.service.RepositoryCategoryService;
import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 *
 */
public class RepositoryCategoryServiceTest {
	
	public static final Long ID = 1L;
	public static String NAME = "name";
	public static String DESCRIPTION = "description";
	
	private Long PARENT_ID = 1111L;
	private String PARENT_NAME = "parent_name";
	private String PARENT_DESCRIPTION = "parent_description";
	
	private RepositoryCategoryService service;
	
	private CategoryRepository repositoryMock;
	
	@Before
	public void setUp(){
		repositoryMock = mock(CategoryRepository.class);
		service = new RepositoryCategoryService(repositoryMock);
	}
	
	@Test
	public void add_호출후_새로운_Category_항목이_저장되는지_테스트() throws CategoryNotFoundException{
		CategoryDTO dto = new CategoryDTOBuilder()
					.name(NAME)
					.description(DESCRIPTION)
					.build();
		
		service.add(dto);
		
		ArgumentCaptor<Category> categoryArgument = ArgumentCaptor.forClass(Category.class);
		verify(repositoryMock, times(1)).save(categoryArgument.capture());
		verifyNoMoreInteractions(repositoryMock);
		
		Category model = categoryArgument.getValue();
		
		assertNull(model.getId());
		assertThat(model.getName(), is(dto.getName()));
		assertThat(model.getDescription(), is(dto.getDescription()));
	}
	
	@Test
	public void update_호출시_Category_항목을찾고_수정되는지_테스트() throws CategoryNotFoundException {
		CategoryDTO dto = new CategoryDTOBuilder()
				.id(ID)
				.name(NAME)
				.description(DESCRIPTION)
				.parentId(PARENT_ID)
				.build();
		
		Category parentCategory = new CategoryBuilder()
				.id(PARENT_ID)
				.name(PARENT_NAME)
				.description(PARENT_DESCRIPTION)
				.build();
		
		Category model = new CategoryBuilder()
				.id(ID)
				.name(NAME)
				.description(DESCRIPTION)
				.parent(parentCategory)
				.build();
		
		when(repositoryMock.findOne(dto.getId())).thenReturn(model);
		when(repositoryMock.findOne(dto.getParentId())).thenReturn(parentCategory);
		
		Category actual = service.update(dto);
		
		verify(repositoryMock, times(1)).findOne(dto.getId());
		verify(repositoryMock, times(1)).findOne(dto.getParentId());
		verifyNoMoreInteractions(repositoryMock);
		
		assertThat(model.getId(), is(dto.getId()));
		assertThat(model.getName(), is(dto.getName()));
		assertThat(model.getDescription(), is(dto.getDescription()));
	}
	
	@Test(expected = CategoryNotFoundException.class)
	public void update_호출시_해당_id_항목이없는경우_CategoryNotFoundException_발생시키는지_테스트() throws CategoryNotFoundException{
		CategoryDTO dto = new CategoryDTOBuilder()
				.id(ID)
				.name(NAME)
				.description(DESCRIPTION)
				.build();
		
		when(repositoryMock.findOne(dto.getId())).thenReturn(null);
		
		service.update(dto);
		
		verify(repositoryMock, times(1)).findOne(dto.getId());
		verifyNoMoreInteractions(repositoryMock);
	}
	
	@Test
	public void deleteById_호출시_id로_Category_항목을찾고_삭제후_해당항목을_반환하는지_테스트() throws CategoryNotFoundException{
		Category model = new CategoryBuilder()
					.id(ID)
					.name(NAME)
					.description(DESCRIPTION)
					.build();
		
		when(repositoryMock.findOne(ID)).thenReturn(model);
		
		Category actual = service.deleteById(ID);
		
		verify(repositoryMock, times(1)).findOne(ID);
		verify(repositoryMock, times(1)).delete(model);
		verifyNoMoreInteractions(repositoryMock);
		
		assertThat(actual, is(model));
	}
	
	@Test(expected = CategoryNotFoundException.class)
	public void deleteById_호출시_해당_id_항목이없는경우_CategoryNotFoundException_발생시키는지_테스트() throws CategoryNotFoundException{
		when(repositoryMock.findOne(ID)).thenReturn(null);
		
		Category actual = service.deleteById(ID);
		
		verify(repositoryMock, times(1)).findOne(ID);
		verifyNoMoreInteractions(repositoryMock);
	}
	
	@Test
	public void findAll_호출시_모든_Category_항목을_반환하는지_테스트(){
		List<Category> models = new ArrayList<Category>();
		when(repositoryMock.findAll()).thenReturn(models);
		
		List<Category> actual = service.findAll();
		
		verify(repositoryMock, times(1)).findAll();
		verifyZeroInteractions(repositoryMock);
		
		assertThat(actual, is(models));
	}
	
	@Test(expected = CategoryNotFoundException.class)
	public void findById_호출시_해당_id_없을때_CategoryNotFoundException이_발생하는지_테스트() throws CategoryNotFoundException {
		when(repositoryMock.findOne(ID)).thenReturn(null);
		
		service.findById(ID);
		
		verify(repositoryMock, times(1)).findOne(ID);
		verifyZeroInteractions(repositoryMock);
	}
	
	

}
