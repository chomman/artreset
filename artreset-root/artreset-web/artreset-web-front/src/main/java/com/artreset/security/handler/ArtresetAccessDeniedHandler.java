/**
 * 
 */
package com.artreset.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * @author Taehyun Jung
 *
 */
public class ArtresetAccessDeniedHandler extends AccessDeniedHandlerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtresetAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		LOGGER.debug("Access Denied Handler: {}", exception.getMessage());
		setErrorPage("/security/accessdenied");
		super.handle(request, response, exception);
	}

	@Override
	public void setErrorPage(String errorPage) {
		super.setErrorPage(errorPage);
	}
}
