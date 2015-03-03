package com.artreset.common.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 일반 Controller 클래스가 상속받아야 하는 Base Controller Class <br>
 * 여러 Controller에서 사용되는 공통 메서드는 이 클래스에 정의한다.
 * 
 * @author Taehyun Jung
 */
@Controller
public class BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	/**
	 * Redirect 화면경로를 생성한다.
	 * 
	 * @param requestMapping
	 * @return String redirect될 경로
	 */
	protected String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }

}
