/**
 * 
 */
package com.artreset.app.artist.service;


import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.artreset.app.artist.dto.ArtistDTO;
import com.artreset.app.artist.dto.ArtistDTOBuilder;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.model.ArtistBuilder;
import com.artreset.app.artist.repository.ArtistRepository;
import com.artreset.image.model.ImageBuilder;
import com.artreset.image.repository.ImageRepository;
import com.artreset.model.Artist;
import com.artreset.model.Image;
import com.artreset.model.User;
import com.artreset.user.repository.UserRepository;

/**
 * @author Taehyun Jung
 *
 */
public class RepositoryArtistServiceTest {
	
	public static final Long ID = 1L;
	public static final Long AVATAR_ID = 1L;
	private static final String NICKNAME = "nickname";
	private static final String TEST_USER_EMAIL = "a@a.com";
	private static final String FIRSTNAME = "firstname";
	private static final String LASTNAME = "lastname";
	private static final String DESCRIPTION = "description";
	private static final String SHORTDESCRIPTION = "shortDescription";
	private static final String NICKNAME_UPDATED = "nickname updated";
	private static final String DESCRIPTION_UPDATED = "description updated";
	private static final String SHORTDESCRIPTION_UPDATED = "shortDescription updated";
	
	private RepositoryArtistService service;
	private ArtistRepository artistRepositoryMock;
	private UserRepository userRepositoryMock;
	private ImageRepository imageRepositoryMock;
	
	@Before
	public void setup() {
		artistRepositoryMock = mock(ArtistRepository.class);
		userRepositoryMock = mock(UserRepository.class);
		imageRepositoryMock = mock(ImageRepository.class);
		
		service = new RepositoryArtistService(artistRepositoryMock, 
				userRepositoryMock, imageRepositoryMock);
	}

	@Test
	public void add_호출후_새로운_Artist_항목이_저장되는지_테스트() {
		ArtistDTO dto = new ArtistDTOBuilder()
				.nickName(NICKNAME)
				.avatarId(AVATAR_ID)
				.build();
		
		User testUser = User.getBuilder()
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.email(TEST_USER_EMAIL)
				.build();
		
		Image avatar = new ImageBuilder().id(ID).build();
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(securityContext.getAuthentication().getName()).thenReturn(TEST_USER_EMAIL);
		SecurityContextHolder.setContext(securityContext);
		
		when(userRepositoryMock.findByEmail(TEST_USER_EMAIL)).thenReturn(testUser);
		when(imageRepositoryMock.findOne(dto.getAvatarId())).thenReturn(avatar);
		
		service.add(dto);
		
		ArgumentCaptor<Artist> artistArgument = ArgumentCaptor.forClass(Artist.class);
		
		verify(userRepositoryMock, times(1)).findByEmail(TEST_USER_EMAIL);
		verify(imageRepositoryMock, times(1)).findOne(dto.getAvatarId());
		verify(artistRepositoryMock, times(1)).save(artistArgument.capture());
		
		verifyNoMoreInteractions(userRepositoryMock, imageRepositoryMock, artistRepositoryMock);
		
		Artist model = artistArgument.getValue();
		
		assertNull(model.getId());
		assertThat(model.getNickName(), is(dto.getNickName()));
		assertThat(model.getAvatar().getId(), is(dto.getAvatarId()));
	}
	
	@Test
	public void update_호출시_Artist_항목찾고_수정되는지_테스트() throws ArtistNotFoundException {
		ArtistDTO dto = new ArtistDTOBuilder()
				.id(ID)
				.nickName(NICKNAME_UPDATED)
				.description(DESCRIPTION_UPDATED)
				.shortDescription(SHORTDESCRIPTION_UPDATED)
				.build();
		
		Artist model = new ArtistBuilder()
				.id(ID)
				.nickName(NICKNAME)
				.description(DESCRIPTION)
				.shortDescription(SHORTDESCRIPTION)
				.build();
		
		when(artistRepositoryMock.findOne(ID)).thenReturn(model);
		
		Artist actual = service.update(dto);
		
		verify(artistRepositoryMock, times(1)).findOne(dto.getId());
		verifyNoMoreInteractions(artistRepositoryMock);
		
		assertThat(actual.getId(), is(model.getId()));
		assertThat(actual.getNickName(), is(model.getNickName()));
		assertThat(actual.getShortDescription(), is(model.getShortDescription()));
	}
	
	@Test(expected = ArtistNotFoundException.class)
	public void update_호출시_해당_id_항목이없는경우_ArtistNotFoundException을_발생하는지_테스트() throws ArtistNotFoundException {
		ArtistDTO dto = new ArtistDTOBuilder()
		.id(ID)
		.nickName(NICKNAME_UPDATED)
		.description(DESCRIPTION_UPDATED)
		.shortDescription(SHORTDESCRIPTION_UPDATED)
		.build();
		
		when(artistRepositoryMock.findOne(dto.getId())).thenReturn(null);
		
		service.update(dto);
		
		verify(artistRepositoryMock, times(1)).findOne(dto.getId());
		verifyNoMoreInteractions(artistRepositoryMock);
	}
	
	@Test
    public void deleteById_호출시_id로_Artist_항목을찾고_삭제후_해당항목을_반환하는지_테스트() throws ArtistNotFoundException {
        Artist model = new ArtistBuilder()
                .id(ID)
                .nickName(NICKNAME)
                .description(DESCRIPTION)
                .shortDescription(SHORTDESCRIPTION)
                .build();

        when(artistRepositoryMock.findOne(ID)).thenReturn(model);

        Artist actual = service.deleteById(ID);

        verify(artistRepositoryMock, times(1)).findOne(ID);
        verify(artistRepositoryMock, times(1)).delete(model);
        verifyNoMoreInteractions(artistRepositoryMock);

        assertThat(actual, is(model));
    }
	
	@Test(expected = ArtistNotFoundException.class)
    public void deleteById_호출시_해당_id_항목이없는경우_TodoNotFoundException_발생시키는지_테스트() throws ArtistNotFoundException {
        when(artistRepositoryMock.findOne(ID)).thenReturn(null);

        service.deleteById(ID);

        verify(artistRepositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(artistRepositoryMock);
    }
	
	@Test
	public void findAll_호출시_모든_Artist_목록을_반환하는지_테스트(){
		List<Artist> models = new ArrayList<Artist>();
		when(artistRepositoryMock.findAll()).thenReturn(models);
		
		List<Artist> actual = service.findAll();
		
		verify(artistRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(artistRepositoryMock);
		
		assertThat(actual, is(models));
	}
	
	@Test
	public void findById_호출시_주어진아이디로해당항목을찾고_해당항목을반환하는지_테스트() throws ArtistNotFoundException {
		Artist model = new ArtistBuilder()
					.id(ID)
					.nickName(NICKNAME)
					.description(DESCRIPTION)
					.build();
		
		when(artistRepositoryMock.findOne(ID)).thenReturn(model);
		
		Artist actual = service.findById(ID);
		
		verify(artistRepositoryMock, times(1)).findOne(ID);
		verifyNoMoreInteractions(artistRepositoryMock);
		
		assertThat(actual, is(model));
	}
	
	@Test(expected = ArtistNotFoundException.class)
	public void findById_호출시_존재하지않는아이디인경우_ArtistNotFoundException을_발생하는지_테스트() throws ArtistNotFoundException {
		when(artistRepositoryMock.findOne(ID)).thenReturn(null);
		
		service.findById(ID);
		
		verify(artistRepositoryMock, times(1)).findOne(ID);
		verifyNoMoreInteractions(artistRepositoryMock);
	}

}
