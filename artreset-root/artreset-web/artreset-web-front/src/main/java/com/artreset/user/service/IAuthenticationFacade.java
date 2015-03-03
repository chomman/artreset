/**
 * 
 */
package com.artreset.user.service;

import org.springframework.security.core.Authentication;

/**
 * @author Taehyun Jung
 *
 */
public interface IAuthenticationFacade {
	Authentication getAuthentication();
}
