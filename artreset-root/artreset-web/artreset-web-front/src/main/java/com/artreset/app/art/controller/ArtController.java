/**
 * 
 */
package com.artreset.app.art.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artreset.app.art.dto.ArtDTO;
import com.artreset.app.art.dto.ProductDTO;
import com.artreset.app.art.exception.ArtNotFoundException;
import com.artreset.app.art.exception.ProductNotFoundException;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.Product;
import com.artreset.app.art.service.ArtService;
import com.artreset.app.artist.controller.ArtistController;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.service.ArtistService;
import com.artreset.app.category.service.CategoryService;
import com.artreset.common.controller.BaseController;
import com.artreset.image.service.ImageService;
import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
@Controller
@SessionAttributes("art")
public class ArtController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistController.class);
    
    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
    private static final String FEEDBACK_MESSAGE_KEY_ART_ADDED = "feedback.message.art.added";
    private static final String FEEDBACK_MESSAGE_KEY_ART_UPDATED = "feedback.message.art.updated";
    private static final String FEEDBACK_MESSAGE_KEY_ART_DELETED = "feedback.message.art.deleted";
    private static final String FEEDBACK_MESSAGE_KEY_ART_PRODUCED = "feedback.message.art.produced";

    public static final String MODEL_ATTRIBUTE_ART = "art";
    public static final String MODEL_ATTRIBUTE_ART_LIST = "arts";
    public static final String MODEL_ATTRIBUTE_ART_ID = "artId";
    public static final String MODEL_ATTRIBUTE_CATEGORIES = "categories";
    private static final String MODEL_ATTRIBUTE_PRODUCT = "product";

	public static final String VIEW_ART_ADD = "art/add";
	public static final String VIEW_ART_UPDATE = "art/update";
	public static final String VIEW_ART_VIEW = "art/view";
	public static final String VIEW_ART_LIST = "art/list";
	public static final String VIEW_ART_EXIST = "art/exist";
	private static final String VIEW_ART_PRODUCE = "art/produce";

	public static final String PARAMETER_ART_ID = "id";

	public static final String REQUEST_MAPPING_ART_LIST = "/art";
	public static final String REQUEST_MAPPING_ART_VIEW = "/art/{id}";
	public static final String REQUEST_MAPPING_ART_EXIST = "/art/exist";

	public static final String REQUEST_MAPPING_CREATE_ARTIST = "/artist/add";






	
	private final ArtService service;
    private final ArtistService artistService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final MessageSource messageSource;

	@Autowired
	public ArtController(MessageSource messageSource, ArtService service, ArtistService artistService, CategoryService categoryService, ImageService imageService) {
		this.messageSource = messageSource;
		this.service = service;
		this.artistService = artistService;
		this.categoryService = categoryService;
		this.imageService = imageService;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/add", method = RequestMethod.GET)
	public String showAddArtForm(Model model, Principal principal) {
		LOGGER.debug("Rendering add art form");
		
		try {
			Artist artist = getUserArtistInformation(principal);
		} catch (ArtistNotFoundException e) {
			LOGGER.debug("Artist not found for current user.");
			createRedirectViewPath(REQUEST_MAPPING_CREATE_ARTIST);
		}
		
		List<Category> categories = categoryService.findAll();
		LOGGER.debug("categories: {}", categories);
		//model.addAttribute(MODEL_ATTRIBUTE_CATEGORIES, categories);
		
		ArtDTO formObject = new ArtDTO();
		formObject.setCategories(categories);
		model.addAttribute(MODEL_ATTRIBUTE_ART, formObject);
		
		return VIEW_ART_ADD;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ART) ArtDTO dto, BindingResult result, RedirectAttributes attributes, Principal principal) throws ArtistNotFoundException {
		LOGGER.debug("Adding a new art entry with information: {}", dto);
		
		if(dto.getAssential() == null || 0 == dto.getAssential().getSize()){
			LOGGER.debug("Assential image is null. ");
			result.rejectValue("assential", "NotNull.art.assential");
		}
		
		if(result.hasErrors()){
			LOGGER.debug("Add art form was submitted with binding errors. Rendering form view. errors: {}", result.getFieldErrors());
			return VIEW_ART_ADD;
		}
		
		Image assentialImage = imageService.create(dto.getAssential());
		dto.setAssentialImage(assentialImage);
		
		if(dto.getDetails() != null && !dto.getDetails().isEmpty()) {
			Set<Image> details = new HashSet<Image>();
			for (MultipartFile detail : dto.getDetails()) {
				if(detail != null && detail.getSize() > 0){
					Image detailImage = imageService.create(detail);
					details.add(detailImage);
				}
			}
			dto.setDetailImages(details);
		}
		
		dto.setArtist(getUserArtistInformation(principal));
		
		// TODO 넘겨받은 dto에 categories가 비어있을 수 있기 때문에 DB에서 조회하는 것으로 변경해야 하는지 고민 중...
		if(dto.getCategories() != null && dto.getCategoryId() != null) {
			for (Category category : dto.getCategories()) {
				if(dto.getCategoryId() == category.getId()){
					dto.setCategory(category);
				}
			}
		}
		
		Art added = service.add(dto);
		LOGGER.debug("Added a art entry with information: {}", added);
		
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ART_ADDED, added.getTitle());
        attributes.addAttribute(PARAMETER_ART_ID, added.getId());	
        
		return createRedirectViewPath(REQUEST_MAPPING_ART_VIEW);
	}

	@RequestMapping(value = REQUEST_MAPPING_ART_LIST, method = RequestMethod.GET)
	public String findAll(Model model) {
		LOGGER.debug("Rendering art list.");
		
		List<Art> arts = service.findAll();
		LOGGER.debug("Found {} arts.", arts.size());
		
		model.addAttribute(MODEL_ATTRIBUTE_ART_LIST, arts);
		return VIEW_ART_LIST;
	}
	
	@RequestMapping(value = REQUEST_MAPPING_ART_VIEW, method = RequestMethod.GET)
	public String findById(@PathVariable("id") Long id, Model model) throws ArtNotFoundException {
		LOGGER.debug("Rendering art page for art entry with id: {}", id);
		
		Art found = service.findById(id);
		LOGGER.debug("Found art entry with information: {}", found);
		
		List<Art> arts = service.findAll();
		LOGGER.debug("Found {} arts.", arts.size());
		
		arts.remove(found);
		LOGGER.debug("Art List has {} arts after remove target art.", arts.size());
		
		try {
			Product product = service.getProductById(id);
			model.addAttribute(MODEL_ATTRIBUTE_PRODUCT, product);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			model.addAttribute(MODEL_ATTRIBUTE_PRODUCT, null);
		}
		
		model.addAttribute(MODEL_ATTRIBUTE_ART, found);
		model.addAttribute(MODEL_ATTRIBUTE_ART_LIST, arts);
		
		return VIEW_ART_VIEW;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/update/{id}", method = RequestMethod.GET)
	public String showUpdateArtForm(@PathVariable("id") Long id, Model model) throws ArtNotFoundException {
		LOGGER.debug("Rendering updae art form for art id: {}", id);
		
		Art updated = service.findById(id);
		LOGGER.debug("Found Art entry with information: {}", updated);
		
		List<Category> categories = categoryService.findAll();
		LOGGER.debug("categories: {}", categories);
		//model.addAttribute(MODEL_ATTRIBUTE_CATEGORIES, categories);
		
		ArtDTO formObject = constructFormObjectForUpdateForm(updated);
		formObject.setCategories(categories);
		model.addAttribute(MODEL_ATTRIBUTE_ART, formObject);
		
		
		return VIEW_ART_UPDATE;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute(MODEL_ATTRIBUTE_ART) ArtDTO dto, BindingResult result, RedirectAttributes attributes) throws ArtNotFoundException {
		LOGGER.debug("Updating a art entry with information: {}", dto);
		
		if(result.hasErrors()) {
			LOGGER.debug("Update art entry form was submitted with validation errors. Rendering update form view.");
			return VIEW_ART_UPDATE;
		}
		
		if(dto.getAssential() != null || 0 > dto.getAssential().getSize()){
			Image assentialImage = imageService.create(dto.getAssential());
			dto.setAssentialImage(assentialImage);			
		}
		
		if(dto.getDetails() != null && !dto.getDetails().isEmpty()) {
			Set<Image> details = new HashSet<Image>();
			for (MultipartFile detail : dto.getDetails()) {
				if(detail != null && detail.getSize() > 0){
					Image detailImage = imageService.create(detail);
					details.add(detailImage);
				}
			}
			dto.setDetailImages(details);
		}
		
		// TODO 넘겨받은 dto에 categories가 비어있을 수 있기 때문에 DB에서 조회하는 것으로 변경해야 하는지 고민 중...
		if(dto.getCategories() != null && dto.getCategoryId() != null) {
			for (Category category : dto.getCategories()) {
				if(dto.getCategoryId() == category.getId()){
					dto.setCategory(category);
				}
			}
		}
		
		Art updated = service.update(dto);
		LOGGER.debug("Updated the information of art entry to: {}", updated);
		
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ART_UPDATED, updated.getTitle());
		attributes.addAttribute(PARAMETER_ART_ID, updated.getId());
		
		return createRedirectViewPath(REQUEST_MAPPING_ART_VIEW);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) throws ArtNotFoundException {
		LOGGER.debug("Deleting a art entry with id: {}", id);
		
		Art deleted = service.deleteById(id);
		LOGGER.debug("Deleted a art entry with information: {}", deleted);
		
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ART_DELETED, deleted.getTitle());
		return createRedirectViewPath(REQUEST_MAPPING_ART_LIST);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/produce/{id}")
	public String showProduceArtForm(@PathVariable("id") Long id, Model model) throws ArtNotFoundException {
		LOGGER.debug("Rendering produce art form for art id: {}", id);
		
		Art art = service.findById(id);
		LOGGER.debug("Found Art entry with information: {}", art);
		
		ProductDTO formObject = constructFormObjectForProduceForm(art);
		model.addAttribute(MODEL_ATTRIBUTE_PRODUCT, formObject);
		
		return VIEW_ART_PRODUCE;
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/art/produce", method = RequestMethod.POST)
	public String produce(@Valid @ModelAttribute(MODEL_ATTRIBUTE_PRODUCT) ProductDTO dto, BindingResult result, RedirectAttributes attributes) throws ArtNotFoundException {
		LOGGER.debug("Produce an art with id: {}", dto.getId());
		
		Art art = service.findById(dto.getId());
		LOGGER.debug("Found Art entry with information: {}", art);
		dto.setArt(art);
		
		Product product = service.produce(dto);
		LOGGER.debug("Found Art entry with information: {}", product);
		
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_ART_PRODUCED, product.getArt().getTitle());
		attributes.addAttribute(PARAMETER_ART_ID, product.getArt().getId());
		return createRedirectViewPath(REQUEST_MAPPING_ART_VIEW);
	}
	
    /*@RequestMapping(value = "/art/upload", method = RequestMethod.GET)
    public @ResponseBody Map list(Principal principal) {
        LOGGER.debug("uploadGet called");
        List<Image> list = service.getDetailImages();        
        
        for(Image image : list) {
            image.setUrl("/image/picture/"+image.getId());
            image.setThumbnailUrl("/image/thumbnail/"+image.getId());
            image.setDeleteUrl("/image/delete/"+image.getId());
            image.setDeleteType("DELETE");
        }
        
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        LOGGER.debug("Returning: {}", files);
        return files;
    }*/
	
	
	
	
	
	private ProductDTO constructFormObjectForProduceForm(Art art) {
		ProductDTO dto = new ProductDTO();
		dto.setId(art.getId());
		dto.setArt(art);
		
		return dto;
	}

	private ArtDTO constructFormObjectForUpdateForm(Art updated) {
		ArtDTO dto = new ArtDTO();
		
		dto.setId(updated.getId());
		dto.setTitle(updated.getTitle());
		dto.setDescription(updated.getDescription());
		dto.setWidth(updated.getWidth());
		dto.setLength(updated.getLength());
		dto.setHeight(updated.getHeight());
		dto.setArtist(updated.getArtist());
		dto.setCategory(updated.getCategory());
		dto.setAssentialImage(updated.getAssentialImage());
		dto.setDetailImages(updated.getDetailImages());
		
		return dto;
	}

	/**
	 * 현재 사용자의 Artist 정보를 조회한다.
	 * @param principal
	 * @return
	 * @throws ArtistNotFoundException 
	 */
	private Artist getUserArtistInformation(Principal principal) throws ArtistNotFoundException {
		Artist artist = artistService.findByEmail(principal.getName());
		return artist;
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
