package com.jere.forum.service.abstraction;

import com.jere.forum.dto.request.UpdateUserRequest;

public interface IUpdateUserService {

	void update(Integer id, UpdateUserRequest updateUserRequest);
	
}
