package com.jere.forum.mapper;

import com.jere.forum.dto.request.RegisterRequest;
import com.jere.forum.dto.response.RegisterResponse;
import com.jere.forum.dto.response.UserResponse;
import com.jere.forum.model.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserEntity toUserEntity(RegisterRequest registerRequest) {
    return UserEntity.builder().firstName(registerRequest.getFirstName())
        .lastName(registerRequest.getLastName()).email(registerRequest.getEmail())
        .password(registerRequest.getPassword()).build();
  }

  public RegisterResponse toRegisterResponse(UserEntity userEntity) {
    return RegisterResponse.builder().id(userEntity.getId()).firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
  }

  public List<UserResponse> toListUserResponse(List<UserEntity> listUserEntities) {
    List<UserResponse> userResponses = new ArrayList<>();
    for (UserEntity userEntity : listUserEntities) {
      userResponses.add(toUserResponse(userEntity));
    }
    return userResponses;
  }

  public UserResponse toUserResponse(UserEntity userEntity) {
    return UserResponse.builder().id(userEntity.getId()).firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName()).email(userEntity.getEmail())
        .role(userEntity.getRole().getName()).build();
  }

}
