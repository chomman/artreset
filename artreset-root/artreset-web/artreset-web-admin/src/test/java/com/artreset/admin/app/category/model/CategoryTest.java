/**
 * 
 */
package com.artreset.admin.app.category.model;

import org.junit.Test;

import com.artreset.model.Category;

import static junit.framework.Assert.*;

/**
 * @author Taehyun Jung
 *
 */
public class CategoryTest {
	
	private String NAME = "name";
	private String DESCRIPTION = "description";
	
	private Long PARENT_ID = 1111L;
	private String PARENT_NAME = "parent_name";
	private String PARENT_DESCRIPTION = "parent_description";
	
	@Test
	public void 주어진_필수정보로_필수정보가_세팅된_객체가_생성되는지_테스트(){
		Category build = Category.getBuilder(NAME).build();
		
		assertNull(build.getId());
		assertNull(build.getDescription());
		assertNull(build.getCreationTime());
		assertNull(build.getModificationTime());
		assertEquals(0L, build.getVersion());
		assertEquals(NAME, build.getName());
		assertNull(build.getParentCategory());
	}
	
	@Test
	public void 주어진_모든정보로_주어진_정보가_세팅된_객체가_생성되는지_테스트(){
		Category parentCategory = new CategoryBuilder()
				.id(PARENT_ID)
				.name(PARENT_NAME)
				.build();
		
		Category build = Category.getBuilder(NAME)
				.description(DESCRIPTION)
				.parentCategory(parentCategory)
				.build();
		
		assertNull(build.getId());
		assertNull(build.getCreationTime());
		assertNull(build.getModificationTime());
		assertEquals(0L, build.getVersion());
		assertEquals(NAME, build.getName());
		assertEquals(DESCRIPTION, build.getDescription());
		assertEquals(parentCategory, build.getParentCategory());
	}
	
	@Test
	public void prePersist_호출시_생성시간과_수정시간이_세팅되는지_테스트(){
		Category category = new Category();
		category.prePersist();
		
		assertNull(category.getId());
		assertNull(category.getName());
		assertNull(category.getDescription());
		assertNotNull(category.getCreationTime());
		assertNotNull(category.getModificationTime());
		assertEquals(0L, category.getVersion());
		assertEquals(category.getCreationTime(), category.getModificationTime());
		assertNull(category.getParentCategory());
	}
	
	@Test
	public void preUpdate_호출시_수정시간이_변경되는지_테스트(){
		Category category = new Category();
		category.prePersist();
		
		pause(1000);
		
		category.preUpdate();
		
		assertNull(category.getId());
		assertNull(category.getName());
		assertNull(category.getDescription());
		assertNotNull(category.getCreationTime());
		assertNotNull(category.getModificationTime());
		assertEquals(0L, category.getVersion());
		assertTrue(category.getModificationTime().isAfter(category.getCreationTime()));
		assertNull(category.getParentCategory());
	}
	
	private void pause(long timeInMills){
		try {
			Thread.currentThread().sleep(timeInMills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
