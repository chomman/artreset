/**
 * 
 */
package com.artreset.app.art.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
public class ArtTest {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "test description";
    private static final Float WIDTH = 30F;
    private static final Float LENGTH = 40F;
    private static final Float HEIGHT = 50F;
    
    @Test
    public void 주어진_필수정보로_필수정보가_세팅된_객체가_생성되는지_테스트() {
        Art art = Art.getBuilder(TITLE)
        		.build();

        assertNull(art.getId());
        assertNull(art.getCreationTime());
        assertNull(art.getModificationTime());
        assertEquals(TITLE, art.getTitle());
        assertNull(art.getDescription());
        assertNull(art.getArtist());
        assertNull(art.getCategory());
        assertNull(art.getAssentialImage());
        assertNull(art.getDetailImages());
        assertNull(art.getWidth());
        assertNull(art.getLength());
        assertNull(art.getHeight());
    }

    @Test
    public void 주어진_모든정보로_주어진_정보가_세팅된_객체가_생성되는지_테스트() {
    	Art art = Art.getBuilder(TITLE)
    			.description(DESCRIPTION)
    			.artist(new Artist())
    			.category(new Category())
    			.assentialImage(new Image())
    			.detailImages(new HashSet<Image>())
    			.width(WIDTH)
    			.length(LENGTH)
    			.height(HEIGHT)
        		.build();

        assertNull(art.getId());
        assertNull(art.getCreationTime());
        assertNull(art.getModificationTime());
        assertEquals(TITLE, art.getTitle());
        assertEquals(DESCRIPTION, art.getDescription());
        assertNotNull(art.getArtist());
        assertNotNull(art.getCategory());
        assertNotNull(art.getAssentialImage());
        assertNotNull(art.getDetailImages());
        assertEquals(WIDTH, art.getWidth());
        assertEquals(LENGTH, art.getLength());
        assertEquals(HEIGHT, art.getHeight());
    }

    @Test
    public void prePersist_호출시_생성시간과_수정시간이_세팅되는지_테스트() {
    	Art art = Art.getBuilder(TITLE)
        		.build();
    	
    	art.prePersist();

        assertNull(art.getId());
        assertNotNull(art.getCreationTime());
        assertNotNull(art.getModificationTime());
        assertEquals(art.getCreationTime(), art.getModificationTime());
        assertEquals(TITLE, art.getTitle());
        assertNull(art.getDescription());
        assertNull(art.getArtist());
        assertNull(art.getCategory());
        assertNull(art.getAssentialImage());
        assertNull(art.getDetailImages());
        assertNull(art.getWidth());
        assertNull(art.getLength());
        assertNull(art.getHeight());
    }

    @Test
    public void preUpdate_호출시_수정시간이_변경되는지_테스트() {
    	Art art = Art.getBuilder(TITLE)
        		.build();
    	
    	art.prePersist();

        pause(1000);

        art.preUpdate();

        assertNull(art.getId());
        assertNotNull(art.getCreationTime());
        assertNotNull(art.getModificationTime());
        assertTrue(art.getModificationTime().isAfter(art.getCreationTime()));
        assertEquals(TITLE, art.getTitle());
        assertNull(art.getDescription());
        assertNull(art.getArtist());
        assertNull(art.getCategory());
        assertNull(art.getAssentialImage());
        assertNull(art.getDetailImages());
        assertNull(art.getWidth());
        assertNull(art.getLength());
        assertNull(art.getHeight());
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
