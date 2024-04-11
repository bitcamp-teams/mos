package com.mos.global.auth.handler.request;

import com.mos.global.auth.exception.LoginApiException;
import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.GithubLoginResponseHandler;
import com.mos.global.auth.handler.response.GoogleLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.GITHUB;
import static com.mos.global.auth.handler.LoginApiProvider.GOOGLE;


public class GoogleLoginRequestHandler implements LoginRequestHandler {

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    String userInfo = webClient.get()
        .uri("https://www.googleapis.com/oauth2/v2/userinfo")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new GoogleLoginResponseHandler(userInfo);
  }

  private String getBearerToken(String code) {
    String token = new RequestAuthCode(GOOGLE, code).getAccessToken();
    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
