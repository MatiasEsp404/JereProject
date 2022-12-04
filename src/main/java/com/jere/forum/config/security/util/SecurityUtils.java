package com.jere.forum.config.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jere.forum.config.security.common.Role;
import com.jere.forum.repository.IUserRepository;

@Component
public class SecurityUtils {

	@Autowired
	private IUserRepository userRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public UserDetails getUserAuthenticated() {
		Object principal = getAuthentication().getPrincipal();
		String username = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername()
				: principal.toString();

		return userRepository.findByEmail(username);
	}

	public boolean hasAdminRole() {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(grantedAuthority -> Role.ADMIN.getFullRoleName().equals(grantedAuthority.getAuthority()));
	}

}
