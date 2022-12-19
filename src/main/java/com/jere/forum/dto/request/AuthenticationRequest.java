package com.jere.forum.dto.request;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

  @Email(message = "The email has invalid format.")
  private String email;

  @Length(min = 8, max = 16, message = "The password must be between 8 and 16 characters.")
  private String password;

}
