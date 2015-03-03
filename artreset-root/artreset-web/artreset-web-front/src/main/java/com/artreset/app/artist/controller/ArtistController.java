/**
 * 
 */
package com.artreset.app.artist.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artreset.app.art.exception.ArtNotFoundException;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.service.ArtService;
import com.artreset.app.artist.dto.ArtistDTO;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.service.ArtistService;
import com.artreset.common.controller.BaseController;
import com.artreset.image.service.ImageService;
import com.artreset.model.Artist;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
@Controller
@SessionAttributes("artist")
public class ArtistController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistController.class);
    
    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
    private static final String FEEDBACK_MESSAGE_KEY_ARTIST_ADDED = "feedback.message.artist.added";
    private static final String FEEDBACK_MESSAGE_KEY_ARTIST_UPDATED = "feedback.message.artist.updated";
    private static final String FEEDBACK_MESSAGE_KEY_ARTIST_DELETED = "feedback.message.artist.deleted";

    public static final String MODEL_ATTRIBUTE_ARTIST = "artist";
    public static final String MODEL_ATTRIBUTE_ARTIST_LIST = "artists";
    public static final String MODEL_ATTRIBUTE_ARTIST_ID = "artistId";
    public static final String MODEL_ATTRIBUTE_ART_LIST = "arts";

	public static final String VIEW_ARTIST_ADD = "artist/add";
	public static final String VIEW_ARTIST_UPDATE = "artist/update";
	public static final String VIEW_ARTIST_VIEW = "artist/view";
	public static final String VIEW_ARTIST_LIST = "artist/list";
	public static final String VIEW_ARTIST_EXIST = "artist/exist";

	public static final String PARAMETER_ARTIST_ID = "id";

	public static final String REQUEST_MAPPING_ARTIST_LIST = "/artist";
	public static final String REQUEST_MAPPING_ARTIST_VIEW = "/artist/{id}";
	public static final String REQUEST_MAPPING_ARTIST_EXIST = "/artist/exist";



	
	
    private final ArtistService service;
    private final ArtService artService;
    private final ImageService imageService;
    private final MessageSource messageSource;

	@Autowired
	public ArtistController(MessageSource messageSource, ArtistService service, ArtService artService, ImageService imageService) {
		this.messageSource = messageSource;
		this.service = service;
		this.artService = artService;
		this.imageService = imageService;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/artist/add", method = RequestMethod.GET)
	public String showAddArtistForm(Model model, Principal principal) {
		LOGGER.debug("Rendering add artist form");
		
		Artist artist = null;
		
		try {
			LOGGER.debug("Login user's name(email): {}", principal.getName());
			artist = service.findByEmail(principal.getName());
		} catch (ArtistNotFoundException e) {
			ArtistDTO formObject = new ArtistDTO();
			model.addAttribute(MODEL_ATTRIBUTE_ARTIST, formObject);
			return VIEW_ARTIST_ADD;
		}
		
		model.addAttribute(MODEL_ATTRIBUTE_ARTIST, artist);
		
		return VIEW_ARTIST_EXIST;
	}
    
	@Secured("ROLE_USER")
	@RequestMapping(value = "/artist/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ARTIST) ArtistDTO dto, BindingResult result, RedirectAttributes attributes) {
		LOGGER.debug("Adding a artist entry with information: {}", dto);
		
		if(result.hasErrors()){
			LOGGER.debug("Add artist form has error: {}", result.getAllErrors());
			return VIEW_ARTIST_ADD;
		}
		
		if(dto.getAvatar() != null && dto.getAvatar().getSize() > 0){
			LOGGER.debug("create avatar");
			Image avatarImage = imageService.create(dto.getAvatar());
			dto.setAvatarId(avatarImage.getId());
		}
		
		if(dto.getPageCover() != null && dto.getPageCover().getSize() > 0){
			LOGGER.debug("create pageCover");
			Image pageCoverImage = imageService.create(dto.getPageCover());
			dto.setPageCoverId(pageCoverImage.getId());
		}
		
		Artist added = service.add(dto);
		LOGGER.debug("Added a artist entry with information: {}", added);
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ARTIST_ADDED, added.getNickName());
		attributes.addAttribute(PARAMETER_ARTIST_ID, added.getId());
		
		return createRedirectViewPath(REQUEST_MAPPING_ARTIST_VIEW);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/artist/delete/{id}", method = RequestMethod.GET)
	public String deleteBy(@PathVariable("id") Long id, RedirectAttributes attributes) throws ArtistNotFoundException {
		LOGGER.debug("Deleting a artist entry with id: {}", id);
		
		Artist deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);
		
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ARTIST_DELETED, deleted.getNickName());
		return createRedirectViewPath(REQUEST_MAPPING_ARTIST_LIST);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/artist/update/{id}", method = RequestMethod.GET)
	public String showUpdateArtistForm(@PathVariable("id") Long id, Model model) throws ArtistNotFoundException {
		LOGGER.debug("Rendering update artist entry form for to-do entry with id: {}", id);

        Artist updated = service.findById(id);
        LOGGER.debug("Rendering update artist form for artist with information: {}", updated);

        ArtistDTO formObject = constructFormObjectForUpdateForm(updated);
        model.addAttribute(MODEL_ATTRIBUTE_ARTIST, formObject);

        return VIEW_ARTIST_UPDATE;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value="/artist/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ARTIST) ArtistDTO dto, BindingResult result, RedirectAttributes attributes) throws ArtistNotFoundException {
		LOGGER.debug("Updating a artist entry with information: {}", dto);
		
		if(result.hasErrors()){
			LOGGER.debug("Update artist entry form was submitted with validation errors.");
			return VIEW_ARTIST_UPDATE;
		}
		
		if(dto.getAvatar() != null && dto.getAvatar().getSize() > 0){
			LOGGER.debug("Create avatar");
			Image avatarImage = imageService.create(dto.getAvatar());
			LOGGER.debug("Updated avatarImage: {}", avatarImage);
			dto.setAvatarId(avatarImage.getId());
		}
		
		if(dto.getPageCover() != null && dto.getPageCover().getSize() > 0){
			LOGGER.debug("Create pageCover");
			Image pageCoverImage = imageService.create(dto.getPageCover());
			dto.setPageCoverId(pageCoverImage.getId());
		}
		
		Artist updated = service.update(dto);
		LOGGER.debug("Updated the information of a artist entry to: {}", updated);
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ARTIST_UPDATED, updated.getNickName());
        attributes.addAttribute(PARAMETER_ARTIST_ID, updated.getId());
		
		return createRedirectViewPath(REQUEST_MAPPING_ARTIST_VIEW);
	}
	
	private ArtistDTO constructFormObjectForUpdateForm(Artist updated) {
        ArtistDTO dto = new ArtistDTO();

        dto.setId(updated.getId());
        dto.setNickName(updated.getNickName());
        dto.setDescription(updated.getDescription());
        dto.setShortDescription(updated.getShortDescription());
        dto.setAvatarThumbnailUrl( updated.getAvatar() != null ? updated.getAvatar().getThumbnailUrl() : null);
        dto.setPageCoverThumbnailurl( updated.getPageCover() != null ? updated.getPageCover().getThumbnailUrl() : null);

        return dto;
    }
	
	@RequestMapping(value = REQUEST_MAPPING_ARTIST_VIEW, method = RequestMethod.GET)
	public String findById(@PathVariable("id") Long id, Model model) throws ArtistNotFoundException, ArtNotFoundException {
		LOGGER.debug("Rendering artist page for artist entry with id: {}", id);
		Artist artist = service.findById(id);
		LOGGER.debug("Found artist entry with information: {}", artist);
		
		List<Art> arts = artService.findByArtist(artist);
		LOGGER.debug("Found {} arts entry", arts.size());
		
		model.addAttribute(MODEL_ATTRIBUTE_ARTIST, artist);
		model.addAttribute(MODEL_ATTRIBUTE_ART_LIST, arts);
		
		return VIEW_ARTIST_VIEW;		
	}
	
	@RequestMapping(value = REQUEST_MAPPING_ARTIST_LIST, method = RequestMethod.GET)
	public String findAll(Model model) {
		LOGGER.debug("Rendering Artist List.");
		List<Artist> aritstList = service.findAll();
		LOGGER.debug("Found {} artist entries.", aritstList.size());
		model.addAttribute(MODEL_ATTRIBUTE_ARTIST_LIST, aritstList);
		
		return VIEW_ARTIST_LIST;
	}
	
	private void addFeedbackMessage(RedirectAttributes attributes, String messageCode, Object... messageParameters) {
        LOGGER.debug("Adding feedback message with code: {} and params: {}", messageCode, messageParameters);
        String localizedFeedbackMessage = getMessage(messageCode, messageParameters);
        LOGGER.debug("Localized message is: {}", localizedFeedbackMessage);
        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedFeedbackMessage);
    }

    private String getMessage(String messageCode, Object... messageParameters) {
        Locale current = LocaleContextHolder.getLocale();
        LOGGER.debug("Current locale is {}", current);
        return messageSource.getMessage(messageCode, messageParameters, current);
    }
    

}
