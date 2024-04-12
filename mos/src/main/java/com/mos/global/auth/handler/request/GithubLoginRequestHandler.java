package com.mos.global.auth.handler.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mos.global.auth.exception.LoginApiException;
import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.OAuthRequestParam;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.GithubLoginResponseHandler;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

import static com.mos.global.auth.handler.LoginApiProvider.GITHUB;


public class GithubLoginRequestHandler implements LoginRequestHandler {

  private String token;

  @Override
  public String logout(WebClient webClient, String token) {
    return webClient.post()
        .uri(OAuthRequestParam.KAKAO_LOGOUT_API.getParam())
        .header("Authorization", getBearerToken(token))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    String userInfo = webClient.get()
        .uri("https://api.github.com/user/emails")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new GithubLoginResponseHandler(userInfo, token);
  }

  private String getBearerToken(String code) {
    token = new RequestAuthCode(GITHUB, code).getAccessToken();
    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
