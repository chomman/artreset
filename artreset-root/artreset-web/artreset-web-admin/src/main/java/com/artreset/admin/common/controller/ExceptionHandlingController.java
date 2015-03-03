/**
 * 
 */
package com.artreset.admin.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.common.controller.BaseController;

/**
 * @author Taehyun Jung
 *
 */
@Controller
public class ExceptionHandlingController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);
	
	@ExceptionHandler({CategoryNotFoundException.class})
	public String pageNotFoundExceptionHandler(){
		LOGGER.debug("______________________________________404 Page Not Found..");
		return "error/404";
	}

}
