/**
 * 
 */
package com.artreset.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.artreset.model.User;
import com.artreset.security.dto.ExampleUserDetails;


/**
 * @author Taehyun Jung
 *
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFacade.class);
	
	public static AuthenticationFacade getContext(){
		AuthenticationFacade facade = new AuthenticationFacade();
		return facade;
	}

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Object getPrincipal() {
		return getAuthentication().getPrincipal();
	}
	
	public User getUser() {
		ExampleUserDetails details = (ExampleUserDetails) getPrincipal();
		LOGGER.debug("user principal: {}", details);
		User user = User.getBuilder()
				.email(details.getUsername())
				.firstName(details.getFirstName())
				.lastName(details.getLastName())
				.build();
		return user;
	}

}
