package com.jere.forum.service;

import com.jere.forum.config.exception.runtime.EntityNotFoundException;
import com.jere.forum.config.security.util.SecurityUtils;
import com.jere.forum.dto.request.UpdateUserRequest;
import com.jere.forum.dto.response.ListUsersResponse;
import com.jere.forum.dto.response.UserResponse;
import com.jere.forum.mapper.UserMapper;
import com.jere.forum.mapper.updater.UserUpdater;
import com.jere.forum.model.UserEntity;
import com.jere.forum.repository.IUserRepository;
import com.jere.forum.service.abstraction.IDeleteUserService;
import com.jere.forum.service.abstraction.IGetUserService;
import com.jere.forum.service.abstraction.IUpdateUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService
    implements UserDetailsService, IGetUserService, IDeleteUserService, IUpdateUserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private SecurityUtils securityUtils;

  @Autowired
  private UserUpdater userUpdater;

  @Override
  public ListUsersResponse listActiveUsers() {
    List<UserEntity> listUserEntities = userRepository.findAllActiveUsers();
    ListUsersResponse listUsersResponse = new ListUsersResponse();
    listUsersResponse.setUsers(userMapper.toListUserResponse(listUserEntities));
    return listUsersResponse;
  }

  @Override
  public UserResponse getUserAuthenticated() {
    return userMapper.toUserResponse((UserEntity) securityUtils.getUserAuthenticated());
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return getUser(email);
  }

  private UserEntity getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found.");
    }
    return userEntity;
  }

  @Override
  public void delete(Integer id) {
    UserEntity userEntity = findBy(id);
    userEntity.setSoftDeleted(true);
    userRepository.save(userEntity);
  }

  private UserEntity findBy(Integer id) {
    Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
    if (!optionalUserEntity.isPresent()
        || Boolean.TRUE.equals(optionalUserEntity.get().getSoftDeleted())) {
      throw new EntityNotFoundException("User not found.");
    }
    return optionalUserEntity.get();
  }

  @Override
  public void update(Integer id, UpdateUserRequest updateUserRequest) {
    UserEntity userEntity = findBy(id);
    UserEntity userUpdated = userUpdater.update(updateUserRequest, userEntity);
    userRepository.save(userUpdated);
  }

}
