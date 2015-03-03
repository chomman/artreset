/**
 * 
 */
package com.artreset.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.artreset.security.handler.ArtresetAccessDeniedHandler;
import com.artreset.security.service.RepositoryUserDetailsService;
import com.artreset.security.service.SimpleSocialUserDetailsService;
import com.artreset.user.repository.UserRepository;

/**
 * @author Taehyun Jung
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RememberMeServices rememberMeServices;
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		web
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                    .antMatchers(
                    		"/static/**", 
                    		"/assets/**"
                    		);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");*/
    	
    	 auth
         .userDetailsService(userDetailsService())
         .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.exceptionHandling()
        		.authenticationEntryPoint(authenticationEntryPoint())
		        .accessDeniedHandler(accessDeniedHandler())
            .and()
            	.formLogin()
            	.loginPage("/login")
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/login?error=bad_credentials")
            .and()
	            .logout()
		            .deleteCookies("JSESSIONID")
		            .logoutUrl("/logout")
		            .logoutSuccessUrl("/login")
		    .and()
            	.authorizeRequests()
                	.anyRequest()
                	.permitAll()
            .and()
                .apply(new SpringSocialConfigurer())
	        .and()
	        	.csrf()
	        	.disable()
        	//.and()
	        	.rememberMe()
	        	.rememberMeServices(rememberMeServices)
	        	.key("artreset remember me")
	        	//.tokenRepository(tokenRepository())
	        	//.tokenValiditySeconds(1209600)
					//.disable()
					/*.sessionManagement()
				        .sessionFixation().changeSessionId()
				        .invalidSessionUrl("/spring/error?error_code=1")
				        .sessionAuthenticationErrorUrl("/spring/error?error_code=2")
				        .maximumSessions(1)
				        .expiredUrl("/spring/error?error_code=3")
				        .maxSessionsPreventsLogin(true)*/
            ;
    }
    
	/**
	 * @return
	 */
    @Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new ArtresetAccessDeniedHandler();
	}

	/**
	 * @return
	 */
    @Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		LoginUrlAuthenticationEntryPoint lap = new LoginUrlAuthenticationEntryPoint("/login");
		return lap;
	}

	/**
     * This is used to hash the password of the user.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * This bean is used to load the user specific data when social sign in is used.
     */
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }

    /**
     * This bean is load the user specific data when form login is used.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(userRepository);
    }
    
	
}