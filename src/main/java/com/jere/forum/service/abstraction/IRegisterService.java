package com.jere.forum.service.abstraction;

import com.jere.forum.dto.request.RegisterRequest;
import com.jere.forum.dto.response.RegisterResponse;

public interface IRegisterService {

  RegisterResponse register(RegisterRequest registerRequest);

}
