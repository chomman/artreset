package com.artreset.user.controller;

import com.artreset.model.SocialMediaService;
import com.artreset.model.User;
import com.artreset.security.util.SecurityUtil;
import com.artreset.user.dto.RegistrationForm;
import com.artreset.user.exception.DuplicateEmailException;
import com.artreset.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * @author Taehyun Jung
 */
@Controller
@SessionAttributes("user")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String MODEL_NAME_REGISTRATION_DTO = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registrationForm";

    private UserService service;

    @Autowired
    public RegistrationController(UserService service) {
        this.service = service;
    }

    /**
     * 사용자 등록
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        LOGGER.debug("Rendering registration page.");

        Connection<?> connection = ProviderSignInUtils.getConnection(request);

        RegistrationForm registration = createRegistrationDTO(connection);
        LOGGER.debug("Rendering registration form with information: {}", registration);

        model.addAttribute(MODEL_NAME_REGISTRATION_DTO, registration);

        return VIEW_NAME_REGISTRATION_PAGE;
    }

    /**
     * 사용자 등록 폼에 사용될 폼 객체를 생성한다
     * @param connection
     * @return  사용자가 SNS 사용자이면 해당 SNS에서 제공하는 정보로 채워진 폼 객체를 반환한다.<br>
     * 			그렇지 않은 일반 사용자라면 빈 객체를 반환한다.
     */
    private RegistrationForm createRegistrationDTO(Connection<?> connection) {
        RegistrationForm dto = new RegistrationForm();

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            dto.setEmail(socialMediaProfile.getEmail());
            dto.setFirstName(socialMediaProfile.getFirstName());
            dto.setLastName(socialMediaProfile.getLastName());

            ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
        }

        return dto;
    }

    /**
     * 사용자 등록 처리
     */
    @RequestMapping(value ="/user/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
                                      BindingResult result,
                                      WebRequest request) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("No validation errors found. Continuing registration process.");

        User registered = createUserAccount(userAccountData, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("Registered user account with information: {}", registered);

        //Logs the user in.
        SecurityUtil.logInUser(registered);
        LOGGER.debug("User {} has been signed in", registered);
        //If the user is signing in by using a social provider, this method call stores
        //the connection to the UserConnection table. Otherwise, this method does not
        //do anything.
        ProviderSignInUtils.handlePostSignUp(registered.getEmail(), request);

        return "redirect:/";
    }

    /**
     * 서비스를 호출해 사용자를 생성한다. 만약 중복된 이메일이 DB에 존재하면 BindingResult에 에러메세지를 추가한다.
     */
    private User createUserAccount(RegistrationForm userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        User registered = null;

        try {
            registered = service.registerNewUserAccount(userAccountData);
        }
        catch (DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationForm.FIELD_NAME_EMAIL,
                    userAccountData.getEmail(),
                    ERROR_CODE_EMAIL_EXIST,
                    result);
        }

        return registered;
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue,  String errorCode, BindingResult result) {
        LOGGER.debug(
                "Adding field error object's: {} field: {} with error code: {}",
                objectName,
                //fieldName,
                errorCode
        );
        
        FieldError error = new FieldError(
                objectName,
                fieldName,
                fieldValue,
                false,
                new String[]{errorCode},
                new Object[]{},
                errorCode
        );

        result.addError(error);
        LOGGER.debug("Added field error: {} to binding result: {}", error, result);
    }
}
