package com.jere.forum.service;

import com.jere.forum.config.exception.runtime.EntityNotFoundException;
import com.jere.forum.config.exception.runtime.InvalidCredentialsException;
import com.jere.forum.config.exception.runtime.UserAlreadyExistException;
import com.jere.forum.config.security.common.Role;
import com.jere.forum.config.security.util.JwtUtils;
import com.jere.forum.dto.request.AuthenticationRequest;
import com.jere.forum.dto.request.RegisterRequest;
import com.jere.forum.dto.response.AuthenticationResponse;
import com.jere.forum.dto.response.RegisterResponse;
import com.jere.forum.mapper.UserMapper;
import com.jere.forum.model.RoleEntity;
import com.jere.forum.model.UserEntity;
import com.jere.forum.repository.IRoleRepository;
import com.jere.forum.repository.IUserRepository;
import com.jere.forum.service.abstraction.IAuthenticationService;
import com.jere.forum.service.abstraction.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IRegisterService, IAuthenticationService {


  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private AuthenticationManager authManager;

  @Override
  public RegisterResponse register(RegisterRequest registerRequest) {
    if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
      throw new UserAlreadyExistException("Email is already in use.");
    }

    RoleEntity userRoleEntity = roleRepository.findByName(Role.USER.getFullRoleName());
    if (userRoleEntity == null) {
      throw new EntityNotFoundException("Missing record in role table.");
    }

    UserEntity userEntity = userMapper.toUserEntity(registerRequest);
    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    userEntity.setSoftDeleted(false);
    userEntity.setRole(userRoleEntity);
    userEntity = userRepository.save(userEntity);

    // TODO Se debería enviar un email de bienvenida

    RegisterResponse registerResponse = userMapper.toRegisterResponse(userEntity);
    registerResponse.setToken(jwtUtils.generateToken(userEntity));
    return registerResponse;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    Authentication authentication;
    try {
      authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
          authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    } catch (Exception e) {
      throw new InvalidCredentialsException("Invalid email or password.");
    }

    String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
    return new AuthenticationResponse(jwt);
  }

}
