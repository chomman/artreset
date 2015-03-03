package com.artreset.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 회원등록 라우터 역할을 하는 클래스
 * 
 * @author Taehyun Jung
 */
@Controller
public class SignUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    /**
     * 실제 회원등록 URL로 Redirect시킨다. SNS 연동을 위해서이며 SocialAuthenticationFilter 클래스를 통과시킨다.
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage() {
        LOGGER.debug("Redirecting request to registration page.");

        return "redirect:/user/register";
    }

}
