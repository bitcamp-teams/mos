package com.mos.global.auth.handler.request;

import com.mos.global.auth.handler.LoginRequestHandler;
import com.mos.global.auth.handler.RequestAuthCode;
import com.mos.global.auth.handler.response.LoginResponseHandler;
import com.mos.global.auth.handler.response.NaverLoginResponseHandler;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;

import static com.mos.global.auth.handler.LoginApiProvider.NAVER;

@Getter
public class NaverLoginRequestHandler implements LoginRequestHandler {

  private String token;

  @Override
  public LoginResponseHandler getUserInfo(WebClient webClient, String code) {
    String userInfo = webClient.get()
        .uri("https://openapi.naver.com/v1/nid/me")
        .header("Authorization", getBearerToken(code))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    return new NaverLoginResponseHandler(userInfo, token);
  }

  private String getBearerToken(String code) {
    token = new RequestAuthCode(NAVER, code).getAccessToken();
    if (token.startsWith("Bearer"))
      return token;
    else
      return "Bearer " + token;
  }

}
