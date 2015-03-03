package com.artreset.security.social;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.artreset.model.Role;
import com.artreset.model.SocialMediaService;

public class ArtresetSocialUserDetails extends SocialUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private Role role;
	private SocialMediaService socialSignInProvider;
	
	public ArtresetSocialUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities){
		super(username, password, authorities);
	}
	
	public static class Builder {
		private Long id;
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		private Role role;
		private SocialMediaService socialSignInProvider;
		private Set<GrantedAuthority> authorities;
		
		public Builder(){
			this.authorities = new HashSet<>();
		}
		
		public Builder id(Long id){
			this.id = id;
			return this;
		}
		
		public Builder firstName(String firstName){
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName){
			this.lastName = lastName;
			return this;
		}
		
		public Builder role(Role role){
			this.role = role;
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);
			return this;
		}
		
		public Builder socialSignInProvider(SocialMediaService socialSignInProvider){
			this.socialSignInProvider = socialSignInProvider;
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);
			return this;
		}
		
		public Builder username(String username){
			this.username = username;
			return this;
		}	
		
		public ArtresetSocialUserDetails build(){
			ArtresetSocialUserDetails user = new ArtresetSocialUserDetails(username, password, authorities);
			
			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.role = role;
			user.socialSignInProvider = socialSignInProvider;
			
			return user;
		}
	}
	

}
