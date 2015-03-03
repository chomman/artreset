/**
 * 
 */
package com.artreset.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.artreset.app.category.service.CategoryService;
import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 *
 */
@Component
@Configurable
public class CategorySelectTag extends TagSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategorySelectTag.class);
	
	private CategoryService categoryService;
	
	private String name;
	
	private String selectedValue;
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	
	public CategorySelectTag() {
	}

	@Autowired
	public CategorySelectTag(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@Override
	public int doStartTag() throws JspException {
		
		List<Category> categories = categoryService.findAll();
		
		StringBuffer selectTag = new StringBuffer("<select name=\"");
		selectTag.append(name).append("\"");
		
		for (Category category : categories) {
			selectTag.append("<option value=\"")
					.append(category.getId()).append("\">")
					.append(category.getName())
					.append("</option>");
		}
		
		JspWriter out = pageContext.getOut();
		try {
			selectTag.deleteCharAt(selectTag.lastIndexOf("/"));
			selectTag.append("</select>");
			LOGGER.debug("address: {}", selectTag);
			out.println(selectTag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	/*private StringBuffer getParentTownName(Town parent) {
		StringBuffer address = new StringBuffer();
		if(parent.getParentTown() != null){
			address = getParentTownName(parent.getParentTown());
		}
		
		return address.append(parent.getName()).append("/");
	}*/

}
