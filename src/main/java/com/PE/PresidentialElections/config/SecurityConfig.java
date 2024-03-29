package com.PE.PresidentialElections.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/styles/**", "/images/**").permitAll()
						.requestMatchers("/", "/registerPage",
								"/registerPageError", "/register/save",
								"/login", "/loginPage", "startApp")
						.permitAll()
						.requestMatchers("/PresidentialElections").authenticated()
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/").permitAll()
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/PresidentialElections")
						.failureUrl("/loginPage?error=true"))
				.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}
}