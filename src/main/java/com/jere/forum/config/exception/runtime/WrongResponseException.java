package com.jere.forum.config.exception.runtime;

@SuppressWarnings("serial")
public class WrongResponseException extends RuntimeException{

	  public WrongResponseException(String message) {
	    super(message);
	  }
	  
	}
