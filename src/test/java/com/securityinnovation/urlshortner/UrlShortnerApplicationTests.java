package com.securityinnovation.urlshortner;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityinnovation.urlshortner.enums.messages.user.UserMessage;
import com.securityinnovation.urlshortner.payload.request.UserLoginRequest;
import com.securityinnovation.urlshortner.payload.response.ApiResponse;
import com.securityinnovation.urlshortner.payload.response.UserAuthenticationResponse;
import com.securityinnovation.urlshortner.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortnerApplicationTests {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  AuthService authService;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void contextLoads() {
  }

  @Test
  void signInTest() throws Exception {
    UserLoginRequest loginRequest = buildLoginRequest();
    UserAuthenticationResponse authenticationResponse = new UserAuthenticationResponse("sdfsdfsd", "sdfasdfsdafsd");
    when(authService.signInUser(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()))
      .thenReturn(authenticationResponse);

    mockMvc
      .perform(post("/api/v1/auth/signIn")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value(UserMessage.LOGIN_SUCCESSFUL.getMessage()))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(content()
        .json(objectMapper.writeValueAsString(
          new ApiResponse(Boolean.TRUE, UserMessage.LOGIN_SUCCESSFUL, authenticationResponse))));
  }

  UserLoginRequest buildLoginRequest() {
    UserLoginRequest request = new UserLoginRequest();
    request.setUsernameOrEmail("Tanay");
    request.setPassword("Tanay");
    return request;
  }

}
