package com.jere.forum.config.exception.runtime;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException(String message) {
    super(message);
  }

}
