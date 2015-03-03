package com.artreset.app.sample.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.artreset.model.Todo;

/**
 * @author Taehyun Jung
 */
public class TodoTest {

    private String TITLE = "title";
    private String DESCRIPTION = "description";
    
    @Test
    public void 주어진_필수정보로_필수정보가_세팅된_객체가_생성되는지_테스트() {
        Todo built = Todo.getBuilder(TITLE).build();

        assertNull(built.getId());
        assertNull(built.getCreationTime());
        assertNull(built.getDescription());
        assertNull(built.getModificationTime());
        assertEquals(TITLE, built.getTitle());
        assertEquals(0L, built.getVersion());
    }

    @Test
    public void 주어진_모든정보로_주어진_정보가_세팅된_객체가_생성되는지_테스트() {
        Todo built = Todo.getBuilder(TITLE)
                .description(DESCRIPTION)
                .build();

        assertNull(built.getId());
        assertNull(built.getCreationTime());
        assertEquals(DESCRIPTION, built.getDescription());
        assertNull(built.getModificationTime());
        assertEquals(TITLE, built.getTitle());
        assertEquals(0L, built.getVersion());
    }

    @Test
    public void prePersist_호출시_생성시간과_수정시간이_세팅되는지_테스트() {
        Todo todo = new Todo();
        todo.prePersist();

        assertNull(todo.getId());
        assertNotNull(todo.getCreationTime());
        assertNull(todo.getDescription());
        assertNotNull(todo.getModificationTime());
        assertNull(todo.getTitle());
        assertEquals(0L, todo.getVersion());
        assertEquals(todo.getCreationTime(), todo.getModificationTime());
    }

    @Test
    public void preUpdate_호출시_수정시간이_변경되는지_테스트() {
        Todo todo = new Todo();
        todo.prePersist();

        pause(1000);

        todo.preUpdate();

        assertNull(todo.getId());
        assertNotNull(todo.getCreationTime());
        assertNull(todo.getDescription());
        assertNotNull(todo.getModificationTime());
        assertNull(todo.getTitle());
        assertEquals(0L, todo.getVersion());
        assertTrue(todo.getModificationTime().isAfter(todo.getCreationTime()));
    }

    private void pause(long timeInMillis) {
        try {
            Thread.currentThread().sleep(timeInMillis);
        }
        catch (InterruptedException e) {
            //Do Nothing
        }
    }
}
