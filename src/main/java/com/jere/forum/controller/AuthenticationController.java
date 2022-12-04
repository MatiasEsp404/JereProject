package com.jere.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jere.forum.config.security.common.Paths;
import com.jere.forum.dto.request.AuthenticationRequest;
import com.jere.forum.dto.request.RegisterRequest;
import com.jere.forum.dto.response.AuthenticationResponse;
import com.jere.forum.dto.response.RegisterResponse;
import com.jere.forum.dto.response.UserResponse;
import com.jere.forum.service.abstraction.IAuthenticationService;
import com.jere.forum.service.abstraction.IGetUserService;
import com.jere.forum.service.abstraction.IRegisterService;

@RestController
@RequestMapping(path = Paths.AUTH)
public class AuthenticationController {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private IAuthenticationService authService;

	@Autowired
	private IGetUserService getUserService;

	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(registerService.register(registerRequest));
	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> login(
			@Valid @RequestBody AuthenticationRequest authenticationRequest) {
		return ResponseEntity.ok().body(authService.login(authenticationRequest));
	}

	@GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getUser() {
		return ResponseEntity.ok().body(getUserService.getUserAuthenticated());
	}

}