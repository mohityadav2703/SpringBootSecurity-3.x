package com.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.app.security.service.CustomerService;

import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private CustomerService custService;

	
	/**
	 * this method is used to encrypt the password
	 */
	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * this method is used to load the data from the DB table
	 * it take our original password and encrypt them and set them to authProvider
	 * and send to the Authentication manager to validate them.
	 */
	@Bean
	public DaoAuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(pwdEncoder());
		
		authProvider.setUserDetailsService(custService);
		return authProvider;
	}

	/**
	 * this method is used to validate the user credentials, is given
	 * credentials are match or not
	 * @param config
	 * @return
	 */
	
	@Bean
	@SneakyThrows
	public AuthenticationManager authManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	/**
	 * this method is used to customize security, means for which request security
	 * is required and for which request security is not required
	 */
	@Bean
	@SneakyThrows	// to handle checked exception
	public SecurityFilterChain security(HttpSecurity http){
		
		http.authorizeHttpRequests((req)-> {
			req.requestMatchers("/register","/login").permitAll()
				.anyRequest().authenticated();
		});
		return http.csrf().disable().build();
	}

}
