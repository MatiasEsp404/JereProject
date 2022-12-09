package com.jere.forum.bigtest.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jere.forum.bigtest.util.BigTest;
import com.jere.forum.model.UserEntity;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ListActiveUsersIntegrationTest extends BigTest {

  @Test
  public void shouldReturnListOfUsersWhenUserHasAdminRole() throws Exception {
    UserEntity randomUser = getRandomUser();

    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationTokenForAdminUser()))
        .andExpect(jsonPath("$.users[*].id")
            .value(Matchers.hasItem(randomUser.getId().intValue())))
        .andExpect(jsonPath("$.users[*].firstName")
            .value(Matchers.hasItem(randomUser.getFirstName())))
        .andExpect(jsonPath("$.users[*].lastName")
            .value(Matchers.hasItem(randomUser.getLastName())))
        .andExpect(jsonPath("$.users[*].email")
            .value(Matchers.hasItem(randomUser.getEmail())))
        .andExpect(jsonPath("$.users[*].role")
            .value(Matchers.hasItem(randomUser.getRole().getName())))
        .andExpect(status().isOk());

    cleanUsersData(randomUser);
  }

}
