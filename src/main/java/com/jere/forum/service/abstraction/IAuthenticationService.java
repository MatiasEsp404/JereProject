package com.jere.forum.service.abstraction;

import com.jere.forum.dto.request.AuthenticationRequest;
import com.jere.forum.dto.response.AuthenticationResponse;

public interface IAuthenticationService {

	AuthenticationResponse login(AuthenticationRequest authenticationRequest);
	
}
