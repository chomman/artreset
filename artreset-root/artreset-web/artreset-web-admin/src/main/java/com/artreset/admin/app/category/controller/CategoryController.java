/**
 * 
 */
package com.artreset.admin.app.category.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artreset.app.category.dto.CategoryDTO;
import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.app.category.service.CategoryService;
import com.artreset.common.controller.BaseController;
import com.artreset.model.Category;
import com.artreset.util.MessageUtil;

/**
 * @author Taehyun Jung
 *
 */
@Controller
@SessionAttributes("category")
public class CategoryController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	protected static final String FEEDBACK_MESSAGE_KEY_CATEGORY_ADDED = "feedback.message.category.added";
	protected static final String FEEDBACK_MESSAGE_KEY_CATEGORY_DELETED = "feedback.message.category.deleted";
	protected static final String FEEDBACK_MESSAGE_KEY_CATEGORY_UPDATED = "feedback.message.category.updated";
	
	protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
	
	protected static final String MODEL_ATTRIBUTE_CATEGORY = "category";
	protected static final String MODEL_ATTRIBUTE_CATEGORY_LIST = "categories";
	protected static final String MODEL_ATTRIBUTE_CATEGORY_SELECTOR = "categorySelector";
	
	protected static final String PARAMETER_CATEGORY_ID = "id";
	
	protected static final String REQUEST_MAPPING_CATEGORY_LIST = "/category";
	protected static final String REQUEST_MAPPING_CATEGORY_VIEW = "/category/{id}";
	
	protected static final String VIEW_CATEGORY_ADD = "category/add";
	protected static final String VIEW_CATEGORY_VIEW = "category/view";
	protected static final String VIEW_CATEGORY_LIST = "category/list";
	protected static final String VIEW_CATEGORY_UPDATE = "category/update";
	
	private final CategoryService service;
	
	@Autowired
    protected ApplicationContext context;

	@Autowired
	public CategoryController(CategoryService service) {
		this.service = service;
	}
	
	@RequestMapping(value = "/category/add", method = RequestMethod.GET)
	public String showAddCategoryForm(Model model) {
		LOGGER.debug("Rendering add category entry form");
		
		List<Category> categorySelector = service.findAll();
		model.addAttribute(MODEL_ATTRIBUTE_CATEGORY_SELECTOR, categorySelector);
		
		CategoryDTO formObject = new CategoryDTO();
		model.addAttribute(MODEL_ATTRIBUTE_CATEGORY, formObject);
		
		return VIEW_CATEGORY_ADD;
	}
	
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CATEGORY) CategoryDTO dto,BindingResult result, RedirectAttributes attributes) throws CategoryNotFoundException {
		LOGGER.debug("add category entry with information: {}", dto);
		
		if(result.hasErrors()){
			LOGGER.debug("Add category form was submitted with binding errors. Rendering form view. {}", result);
			return VIEW_CATEGORY_ADD;
		}
		
		Category added = service.add(dto);
		LOGGER.debug("Added a category entry with information: {}", added);
		
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORY_ADDED, added.getName());
		attributes.addAttribute(PARAMETER_CATEGORY_ID, added.getId());
		
		return createRedirectViewPath(REQUEST_MAPPING_CATEGORY_VIEW);
	}

    @RequestMapping(value = "/category/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes attributes) throws CategoryNotFoundException {
        LOGGER.debug("Deleting a category entry with id: {}", id);

        Category deleted = service.deleteById(id);
        LOGGER.debug("Deleted category entry with information: {}", deleted);

        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORY_DELETED, deleted.getName());

        return createRedirectViewPath(REQUEST_MAPPING_CATEGORY_LIST);
    }

    @RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
    public String showUpdateCategoryForm(@PathVariable("id") Long id, Model model) throws CategoryNotFoundException {
        LOGGER.debug("Rendering update category entry form for category entry with id: {}", id);

        Category updated = service.findById(id);
        LOGGER.debug("Rendering update category form for category with information: {}", updated);

        CategoryDTO formObject = constructFormObjectForUpdateForm(updated);
        model.addAttribute(MODEL_ATTRIBUTE_CATEGORY, formObject);

        return VIEW_CATEGORY_UPDATE;
    }

    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CATEGORY) CategoryDTO dto, BindingResult result, RedirectAttributes attributes) throws CategoryNotFoundException {
        LOGGER.debug("Updating a category entry with information: {}", dto);

        if (result.hasErrors()) {
            LOGGER.debug("Update category entry form was submitted with validation errors. Rendering form view.");
            return VIEW_CATEGORY_UPDATE;
        }

        Category updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CATEGORY_UPDATED, updated.getName());
        attributes.addAttribute(PARAMETER_CATEGORY_ID, updated.getId());

        return createRedirectViewPath(REQUEST_MAPPING_CATEGORY_VIEW);
    }
	
	@RequestMapping(value = REQUEST_MAPPING_CATEGORY_VIEW, method = RequestMethod.GET)
	public String findById(@PathVariable("id") Long id, Model model) throws CategoryNotFoundException {
		LOGGER.debug("Rendering category page for category entry with id: {}", id);
		
		Category found = service.findById(id);
		LOGGER.debug("Found category entry with information: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_CATEGORY, found);
		
		return VIEW_CATEGORY_VIEW;
	}
	
	@RequestMapping(value = REQUEST_MAPPING_CATEGORY_LIST, method = RequestMethod.GET)
	public String findAll(Model model) {
		LOGGER.debug("Rendering category list.");
		
		List<Category> found = service.findAll();
		LOGGER.debug("Found {} category entries: {}", found.size());
		model.addAttribute(MODEL_ATTRIBUTE_CATEGORY_LIST, found);
		
		return VIEW_CATEGORY_LIST;
	}
	
	

	private CategoryDTO constructFormObjectForUpdateForm(Category updated) {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(updated.getId());
        dto.setDescription(updated.getDescription());
        dto.setName(updated.getName());

        return dto;
    }
	
    protected void addFeedbackMessage(RedirectAttributes attributes, String messageCode, Object... messageParameters) {
        LOGGER.debug("Adding feedback message with code: {} and params: {}", messageCode, messageParameters);
        String localizedFeedbackMessage = MessageUtil.getMessage(messageCode, messageParameters);
        LOGGER.debug("Localized message is: {}", localizedFeedbackMessage);
        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedFeedbackMessage);
    }

    /*protected String getMessage(String messageCode, Object... messageParameters) {
        Locale current = LocaleContextHolder.getLocale();
        LOGGER.debug("Current locale is {}", current);
        //return messageSource.getMessage(messageCode, messageParameters, current);
        return context.getMessage(messageCode, messageParameters, current);
    }*/
	
}
