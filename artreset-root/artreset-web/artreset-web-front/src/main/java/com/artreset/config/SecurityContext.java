package com.artreset.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.artreset.security.service.RepositoryUserDetailsService;
import com.artreset.security.service.SimpleSocialUserDetailsService;
import com.artreset.user.repository.UserRepository;

/**
 * Artreset Security Context Config Class
 * 
 * @author Taehyun Jung
 */
//@Configuration
//@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void configure(WebSecurity web) throws Exception {
		web
        		//.expressionHandler(methodSecurityExpressionHandler())
        
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                    .antMatchers(
                    		"/static/**", 
                    		"/assets/**"
                    		);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.csrf()
        		.disable()
        		//.and()
                //Configures form login
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login/authenticate")
                    .failureUrl("/login?error=bad_credentials")
                //Configures the logout function
                .and()
                    .logout()
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                //Configures url based authorization
                .and()
                    .authorizeRequests()
                        //Anyone can access the urls
                        .antMatchers(
                        		"/",
                                "/auth/**",
                                "/login",
                                "/signin/**",
                                "/signup/**",
                                "/user/register/**",
                                "/todo", "/todo/**",
                                "/image", "/image/**",
                                "/artist", "artist/**",
                                "/art", "art/**"
                        ).permitAll()
                        .antMatchers("artist/add", "artist/update", "artist/delete").hasRole("USER")
                        .antMatchers("art/add", "art/update", "art/delete").hasRole("USER")
                        //The rest of the our application is protected.
                        .antMatchers("/**").hasRole("USER")
                //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
                .and()
                    .apply(new SpringSocialConfigurer());
    }

    /**
     * Configures the authentication manager bean which processes authentication requests.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
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
