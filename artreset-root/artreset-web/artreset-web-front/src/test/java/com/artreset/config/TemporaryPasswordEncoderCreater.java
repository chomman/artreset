/**
 * 
 */
package com.artreset.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Taehyun Jung
 *
 */
public class TemporaryPasswordEncoderCreater {
	
	public static void main(String[] args){
		String userPassword = "a";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		String encodedPassword = passwordEncoder.encode(userPassword);
		System.out.println(encodedPassword);
	}

}
