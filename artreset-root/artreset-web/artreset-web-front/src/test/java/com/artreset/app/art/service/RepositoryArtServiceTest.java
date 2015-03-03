/**
 * 
 */
package com.artreset.app.art.service;

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

import com.artreset.app.art.dto.ArtDTO;
import com.artreset.app.art.dto.ArtDTOBuilder;
import com.artreset.app.art.exception.ArtNotFoundException;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.ArtBuilder;
import com.artreset.app.art.repository.ArtRepository;
import com.artreset.app.art.repository.ProductRepository;
import com.artreset.app.artist.repository.ArtistRepository;

/**
 * @author Taehyun Jung
 *
 */
public class RepositoryArtServiceTest {
	
	private static final String TITLE = "title";

	private static final String DESCRIPTION = "description";;

	private RepositoryArtService service;
	
	private ArtRepository repositoryMock;
	private ArtistRepository artistRepositoryMock;
	private ProductRepository productRepositoryMock;
	
	@Before
	public void setUp() {
		repositoryMock = mock(ArtRepository.class);
		service = new RepositoryArtService(repositoryMock, artistRepositoryMock, productRepositoryMock);
	}
	
	@Test
	public void add_호출후_새로운_ART_항목이_저장되는지_테스트() {
		ArtDTO dto = new ArtDTOBuilder()
				.title(TITLE)
				.description(DESCRIPTION)
				.build();
		
		service.add(dto);
		
		ArgumentCaptor<Art> artArgumentCaptor = ArgumentCaptor.forClass(Art.class);
		
		verify(repositoryMock, times(1)).save(artArgumentCaptor.capture());
		verifyNoMoreInteractions(repositoryMock);
		
		Art model = artArgumentCaptor.getValue();
		
		assertNull(model.getId());
		assertThat(model.getTitle(), is(dto.getTitle()));
		assertThat(model.getDescription(), is(dto.getDescription()));
	}
	
	@Test
	public void findAll_호출시_모든_Art_항목을_반환하는지_테스트() {
		List<Art> models = new ArrayList<Art>();
		when(repositoryMock.findAll()).thenReturn(models);
		
		List<Art> actual = service.findAll();
		
		verify(repositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(repositoryMock);
		
		assertThat(actual, is(models));
	}
	
	@Test
	public void findById_호출시_해당_Art_항목을_반환하는지_테스트() throws ArtNotFoundException {
		Art model = new ArtBuilder()
				.id(1L)
				.title("title")
				.description("description")
				.build();
		
		when(repositoryMock.findOne(1L)).thenReturn(model);
		
		Art actual = service.findById(1L);
		
		verify(repositoryMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(repositoryMock);
		
		assertThat(actual, is(model));
	}
	
	@Test(expected = ArtNotFoundException.class)
	public void findById_ArtNotFoundException_반환하는지_테스트() throws ArtNotFoundException {
		when(repositoryMock.findOne(1L)).thenReturn(null);
		
		service.findById(1L);
		
		verify(repositoryMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(repositoryMock);
	}
	
	@Test
	public void update_호출시_ART_항목을찿고_수정되는지_테스트() throws ArtNotFoundException {
		ArtDTO dto = new ArtDTOBuilder()
				.id(1L)
				.title("title updated")
				.description("description updated")
				.build();
		
		Art model = new ArtBuilder()
				.id(1L)
				.title("title")
				.description("description")
				.build();
		
		when(repositoryMock.findOne(dto.getId())).thenReturn(model);
		
		Art actual = service.update(dto);
		
		verify(repositoryMock, times(1)).findOne(dto.getId());
		verifyNoMoreInteractions(repositoryMock);
		
		assertThat(actual, is(model));
	}

    @Test(expected = ArtNotFoundException.class)
    public void update_호출시_해당_id_항목이없는경우_TodoNotFoundException_발생시키는지_테스트() throws ArtNotFoundException {
    	ArtDTO dto = new ArtDTOBuilder()
				.id(1L)
				.title("title updated")
				.description("description updated")
				.build();

    	when(repositoryMock.findOne(dto.getId())).thenReturn(null);

        service.update(dto);

        verify(repositoryMock, times(1)).findOne(dto.getId());
        verifyNoMoreInteractions(repositoryMock);
    }
	

}
