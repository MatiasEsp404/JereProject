package com.jere.forum.controller;

import com.jere.forum.config.security.constants.Paths;
import com.jere.forum.dto.request.UpdateUserRequest;
import com.jere.forum.dto.response.ListUsersResponse;
import com.jere.forum.service.abstraction.IDeleteUserService;
import com.jere.forum.service.abstraction.IGetUserService;
import com.jere.forum.service.abstraction.IUpdateUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Paths.USERS)
public class UserController {

  @Autowired
  private IGetUserService getUserService;

  @Autowired
  private IDeleteUserService deleteUserService;

  @Autowired
  private IUpdateUserService updateUserService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListUsersResponse> listActiveUsers() {
    return ResponseEntity.ok().body(getUserService.listActiveUsers());
  }

  @DeleteMapping(value = Paths.ID, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    deleteUserService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping(value = Paths.ID, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(@PathVariable Integer id,
      @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    updateUserService.update(id, updateUserRequest);
    return ResponseEntity.noContent().build();
  }

}
