/**
 * Index page Controller
 */
package com.artreset.common.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Index page Controller
 * 
 * @author Taehyun Jung
 */
@Controller
public class HomeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    protected static final String VIEW_NAME_HOMEPAGE = "index";

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showHomePage(Locale loc) {
        LOGGER.debug("Rendering homepage. Ur locale is {}", loc);
        return VIEW_NAME_HOMEPAGE;
    }
}
