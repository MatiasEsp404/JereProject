package com.jere.forum.service.abstraction;

import com.jere.forum.dto.response.ListUsersResponse;
import com.jere.forum.dto.response.UserResponse;

public interface IGetUserService {
	
	ListUsersResponse listActiveUsers();
	UserResponse getUserAuthenticated();
	
}
