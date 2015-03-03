package com.artreset.admin.config;

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

import com.artreset.admin.security.service.RepositoryUserDetailsService;
import com.artreset.admin.user.repository.AdminUserRepository;

/**
 * Artreset Security Context Config Class
 * 
 * @author Taehyun Jung
 */
@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminUserRepository adminUserRepository;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		//.csrf()
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
                                "/todo", "/todo/**"
                        ).permitAll()
                        //The rest of the our application is protected.
                        .antMatchers("/**").hasRole("USER");
                //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
                //.and()
                //    .apply(new SpringSocialConfigurer());
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
     * This bean is load the user specific data when form login is used.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(adminUserRepository);
    }
}
