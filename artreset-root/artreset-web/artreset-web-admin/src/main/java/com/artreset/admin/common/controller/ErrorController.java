package com.artreset.admin.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.artreset.common.controller.BaseController;

/**
 * 에러를 처리하는 Controller
 * 
 * @author Taehyun Jung
 */
@Controller
public class ErrorController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    public static final String VIEW_INTERNAL_SERVER_ERROR = "error/error";
    public static final String VIEW_NOT_FOUND = "error/404";

    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public String show404Page() {
        LOGGER.debug("Rendering 404 page");
        return VIEW_NOT_FOUND;
    }

    @RequestMapping(value = "/error/error", method = RequestMethod.GET)
    public String showInternalServerErrorPage() {
        LOGGER.debug("Rendering internal server error page");
        return VIEW_INTERNAL_SERVER_ERROR;
    }
}
