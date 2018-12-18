package com.uxpsystems.assignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static String REALM="MY_TEST_REALM";
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("pk").password("abc123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dk").password("abc123").roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http.csrf().disable()
	  	.authorizeRequests()
	  	.antMatchers("/user").access("hasRole('ADMIN')")
	  	.antMatchers("/user/{id}").access("hasRole('ADMIN') or hasRole('USER')")
	  	.antMatchers("/users").access("hasRole('ADMIN') or hasRole('USER')")
	  	.antMatchers("/update").access("hasRole('ADMIN')")
	  	.antMatchers("/delete/{id}").access("hasRole('ADMIN')")
		.and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint());
 	}
	
	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
