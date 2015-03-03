package com.artreset.app.artist.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.artreset.model.Artist;
import com.artreset.model.SocialMediaService;
import com.artreset.model.User;

/**
 * @author Taehyun Jung
 */
public class ArtistTest {

	private static final String EMAIL = "john.smith@gmail.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String PASSWORD = "password";
    private static final SocialMediaService SIGN_IN_PROVIDER = SocialMediaService.TWITTER;
    
    private static final String NICKNAME = "test Nicname";
    private static final String DESCRIPTION = "test description";
    private static final String SHORTDESCRIPTION = "test short description";
    
    @Test
    public void 주어진_필수정보로_필수정보가_세팅된_객체가_생성되는지_테스트() {
    	User user = User.getBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();
    	
        Artist artist = Artist.getBuilder(user)
        		.nickname(NICKNAME)
        		.build();

        assertNull(artist.getId());
        assertNull(artist.getCreationTime());
        assertNull(artist.getModificationTime());
        assertNull(artist.getAvatar());
        assertNull(artist.getPageCover());
        assertEquals(NICKNAME, artist.getNickName());
        assertNull(artist.getDescription());
        assertNull(artist.getShortDescription());
        assertEquals(0L, artist.getVersion());
    }

    @Test
    public void 주어진_모든정보로_주어진_정보가_세팅된_객체가_생성되는지_테스트() {
    	User user = User.getBuilder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .build();
    	
        Artist artist = Artist.getBuilder(user)
        		.nickname(NICKNAME)
        		.description(DESCRIPTION)
        		.shortDescription(SHORTDESCRIPTION)
        		.build();

        assertNull(artist.getId());
        assertNull(artist.getCreationTime());
        assertNull(artist.getModificationTime());
        assertNull(artist.getAvatar());
        assertNull(artist.getPageCover());
        assertEquals(NICKNAME, artist.getNickName());
        assertEquals(DESCRIPTION, artist.getDescription());
        assertEquals(SHORTDESCRIPTION, artist.getShortDescription());
        assertEquals(0L, artist.getVersion());
    }

    @Test
    public void prePersist_호출시_생성시간과_수정시간이_세팅되는지_테스트() {
        Artist artist = new Artist();
        artist.prePersist();

        assertNull(artist.getId());
        assertNotNull(artist.getCreationTime());
        assertNotNull(artist.getModificationTime());
        assertNull(artist.getAvatar());
        assertNull(artist.getPageCover());
        assertNull(artist.getNickName());
        assertNull(artist.getDescription());
        assertNull(artist.getShortDescription());
        assertEquals(0L, artist.getVersion());
    }

    @Test
    public void preUpdate_호출시_수정시간이_변경되는지_테스트() {
    	Artist artist = new Artist();
        artist.prePersist();

        pause(1000);

        artist.preUpdate();

        assertNull(artist.getId());
        assertNotNull(artist.getCreationTime());
        assertNotNull(artist.getModificationTime());
        assertNull(artist.getAvatar());
        assertNull(artist.getPageCover());
        assertNull(artist.getNickName());
        assertNull(artist.getDescription());
        assertNull(artist.getShortDescription());
        assertEquals(0L, artist.getVersion());
        assertTrue(artist.getModificationTime().isAfter(artist.getCreationTime()));
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
