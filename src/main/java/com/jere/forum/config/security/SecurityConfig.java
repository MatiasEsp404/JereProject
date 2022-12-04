package com.jere.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jere.forum.config.security.common.Paths;
import com.jere.forum.config.security.common.Role;
import com.jere.forum.config.security.filter.CustomAccessDeniedHandler;
import com.jere.forum.config.security.filter.CustomAuthenticationEntryPoint;
import com.jere.forum.config.security.filter.JwtRequestFilter;


@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ID_PATH = "{id:^\\d+$}";
	private static final String PAGE_QUERY_PARAM = "?page={page:[\\d+]}&size={size:[\\d+]}";

	private static final String CATEGORIES_URL = Paths.CATEGORIES;
	private static final String CATEGORIES_ID_URL = Paths.CATEGORIES + ID_PATH;
	private static final String CATEGORIES_PAGING_URL = Paths.CATEGORIES + PAGE_QUERY_PARAM;

	private static final String QUESTIONS_URL = Paths.QUESTIONS;
	private static final String QUESTIONS_ID_URL = Paths.QUESTIONS + ID_PATH;
	private static final String QUESTIONS_PAGING_URL = Paths.QUESTIONS + PAGE_QUERY_PARAM;

	private static final String RESPONSES_URL = Paths.RESPONSES;
	private static final String RESPONSES_ID_URL = Paths.RESPONSES + ID_PATH;
	private static final String RESPONSES_PAGING_URL = Paths.RESPONSES + PAGE_QUERY_PARAM;

	private static final String AUTH_REGISTER_URL = Paths.AUTH + "/register";
	private static final String AUTH_LOGIN_URL = Paths.AUTH + "/login";
	private static final String AUTH_ME_URL = Paths.AUTH + "/me";

	private static final String USERS_URL = Paths.USERS;

	private static final String QUESTIONADOS_NEXT_QUESTION_URL = Paths.QUESTIONADOS
			+ Paths.NEXT_QUESTION;

	private static final String QUESTIONADOS_CHECK_RESPONSE_URL = Paths.QUESTIONADOS
			+ Paths.CHECK_RESPONSE;

	private static final String DOCUMENTATION_PATHS = "/api/docs/**";

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
		managerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()

				.antMatchers(DOCUMENTATION_PATHS)
				.permitAll()

				.antMatchers(HttpMethod.POST, CATEGORIES_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, CATEGORIES_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, CATEGORIES_ID_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, CATEGORIES_PAGING_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.PUT, CATEGORIES_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.PATCH, CATEGORIES_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.DELETE, CATEGORIES_ID_URL).hasAnyRole(Role.ADMIN.name())

				.antMatchers(HttpMethod.POST, QUESTIONS_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, QUESTIONS_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, QUESTIONS_ID_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, QUESTIONS_PAGING_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.PUT, QUESTIONS_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.PATCH, QUESTIONS_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.DELETE, QUESTIONS_ID_URL).hasAnyRole(Role.ADMIN.name())

				.antMatchers(HttpMethod.POST, RESPONSES_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, RESPONSES_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, RESPONSES_ID_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, RESPONSES_PAGING_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.PUT, RESPONSES_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.PATCH, RESPONSES_ID_URL).hasAnyRole(Role.ADMIN.name())
				.antMatchers(HttpMethod.DELETE, RESPONSES_ID_URL).hasAnyRole(Role.ADMIN.name())

				.antMatchers(HttpMethod.GET, QUESTIONADOS_NEXT_QUESTION_URL).permitAll()
				.antMatchers(HttpMethod.POST, QUESTIONADOS_CHECK_RESPONSE_URL).permitAll()

				.antMatchers(HttpMethod.POST, AUTH_REGISTER_URL).permitAll()
				.antMatchers(HttpMethod.POST, AUTH_LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.GET, AUTH_ME_URL).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
				.antMatchers(HttpMethod.GET, USERS_URL).hasAnyRole(Role.ADMIN.name())

				.anyRequest().authenticated().and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());
	}

}