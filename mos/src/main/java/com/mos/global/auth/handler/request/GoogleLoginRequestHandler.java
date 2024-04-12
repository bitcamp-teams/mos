package com.mos.global.auth.handler.request;

import com.mos.global.auth.exception.LoginApiException;
import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.OAuthRequestParam;
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
        .uri("https://www.googleapis.com/oauth2/v2/userinfo")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new GoogleLoginResponseHandler(userInfo, token);
  }

  private String getBearerToken(String code) {
    token = new RequestAuthCode(GOOGLE, code).getAccessToken();
    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
