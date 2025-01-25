package com.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DashboardSecurityConfig {

	// create user to access application

	@Bean
	public InMemoryUserDetailsManager inMemoryUsers() {

		UserDetails u1 = User.withDefaultPasswordEncoder()
					.username("nihit")
					.password("Nihit@123")
					.build();

		UserDetails u2 = User.withDefaultPasswordEncoder()
					.username("divisha")
					.password("divisha@123")
					.build();
		
		UserDetails u3 = User.withDefaultPasswordEncoder()
					.username("rithanya")
					.password("Rithanya@123")
					.build();
		
		return new InMemoryUserDetailsManager(u1,u2,u3);
	}

	// cusomize permission
	@Bean // create object in container and we can modify this object in further process
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((req) -> req.requestMatchers("/").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());

		return http.build();
	}

}
