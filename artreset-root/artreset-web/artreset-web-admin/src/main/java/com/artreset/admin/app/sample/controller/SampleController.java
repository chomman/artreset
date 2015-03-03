package com.artreset.admin.app.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.artreset.common.controller.BaseController;

/**
 * Artreset Admin Project의 Application 개발 샘플로 제작한 Controller<br>
 * 
 * @author Taehyun Jung
 */
@Controller
@SessionAttributes("sample")
public class SampleController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showAddTodoForm(Model model) {
        LOGGER.debug("Admin Sample Page rendering..");

        return "sample/hello";
    }

}
